package com.rsb.testapprsbsanmolsoftware;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.rsb.testapprsbsanmolsoftware.model.FeedsViewModel;

//Created by Rahul S. Bhalerao on 20-07-2021

public class FeedFragment extends Fragment {

    TextView artistName, songName, genres, releaseDate, copyRight;
    ImageView img;
    Button btn;
    String url;
    String TAG = "FeedFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        Bundle bundle = this.getArguments();
        FeedsViewModel viewModel;
        artistName = view.findViewById(R.id.tv_artistname);
        songName = view.findViewById(R.id.tv_songname);
        genres = view.findViewById(R.id.tv_genres);
        releaseDate = view.findViewById(R.id.tv_releaseDate);
        copyRight = view.findViewById(R.id.tv_copyright);
        img = view.findViewById(R.id.feed_image);
        btn = view.findViewById(R.id.bt_url);

        if (bundle != null) {
            viewModel = (FeedsViewModel) bundle.getSerializable("position");
            artistName.setText(viewModel.artistname);
            songName.setText(viewModel.songname);
            genres.setText(viewModel.genres);
            releaseDate.setText(viewModel.releaseDate);
            copyRight.setText(viewModel.copyRights);

            //Loading image async
            FeedsViewModel.LoadImage loadImage = new FeedsViewModel.LoadImage(img);
            loadImage.execute(viewModel.artistimage);
            url = viewModel.url;

            //button onClick to view url in default browser
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    url = url.substring(1, url.length() - 1);
                    Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
        return view;
    }
}