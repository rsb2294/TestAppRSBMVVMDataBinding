package com.rsb.testapprsbsanmolsoftware;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rsb.testapprsbsanmolsoftware.adapter.FeedsRVAdapter;
import com.rsb.testapprsbsanmolsoftware.model.FeedsViewModel;
import com.rsb.testapprsbsanmolsoftware.util.RVClickListener;

import java.util.ArrayList;

//Created by Rahul S. Bhalerao on 20-07-2021

public class MainActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener, RVClickListener {
    String[] feeds = {"Coming Soon", "Hot Tracks", "New Releases", "Top Albums", "Top Songs"};
    public RecyclerView recyclerview;
    private FeedsViewModel feedsViewModel;
    private FeedsRVAdapter adapter;
    FrameLayout frameLayout;
    ProgressDialog progressDialog;
    Spinner feedtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Getting the instance of Spinner
        feedtype = (Spinner) findViewById(R.id.spinner);
        feedtype.setOnItemSelectedListener(this);

        frameLayout = findViewById(R.id.framelayout);

        progressDialog = new ProgressDialog(this);

        //Creating the ArrayAdapter instance having the Feeds type list
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, feeds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        feedtype.setAdapter(adapter);

        //Getting the instance of RecyclerView
        recyclerview = (RecyclerView) findViewById(R.id.rv_feeddata);
        feedsViewModel = ViewModelProviders.of(this).get(FeedsViewModel.class);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        Toast.makeText(getApplicationContext(), feeds[i], Toast.LENGTH_LONG).show();
        progressDialog.setMessage("loading data...");
        progressDialog.show();
        String feed = feeds[i].replaceAll(" ", "-").toLowerCase();
        Log.v("Feed: ", "" + feed);

        // getting data from server and setting up recyclerview
        feedsViewModel.getMutableLiveData(feed).observe(this, new Observer<ArrayList<FeedsViewModel>>() {
            @Override
            public void onChanged(ArrayList<FeedsViewModel> feedsViewModels) {
                progressDialog.cancel();
                adapter = new FeedsRVAdapter(feedsViewModels, MainActivity.this);
                recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerview.setAdapter(adapter);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        frameLayout.setVisibility(View.GONE);
        feedtype.setVisibility(View.VISIBLE);
        recyclerview.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // Call to load fragment
    //getting viewModel  for passing data on the fragment
    public void frag(FeedsViewModel viewModel) {
        frameLayout.setVisibility(View.VISIBLE);
        feedtype.setVisibility(View.GONE);
        recyclerview.setVisibility(View.GONE);
        FeedFragment feedFragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("position", viewModel);
        feedFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.framelayout, feedFragment).addToBackStack(null).commit();
    }

    @Override
    public void click(View view) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStack();
        frameLayout.setVisibility(View.GONE);
        feedtype.setVisibility(View.VISIBLE);
        recyclerview.setVisibility(View.VISIBLE);

    }
}