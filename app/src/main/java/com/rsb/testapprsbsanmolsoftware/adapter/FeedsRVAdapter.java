package com.rsb.testapprsbsanmolsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rsb.testapprsbsanmolsoftware.MainActivity;
import com.rsb.testapprsbsanmolsoftware.R;
import com.rsb.testapprsbsanmolsoftware.databinding.FeedsLayoutBinding;
import com.rsb.testapprsbsanmolsoftware.model.FeedsViewModel;

import java.util.ArrayList;

//Created by Rahul S. Bhalerao on 20-07-2021
//RecyclerView Adapter class

public class FeedsRVAdapter extends RecyclerView.Adapter<FeedsRVAdapter.ViewHolder> {
    private final ArrayList<FeedsViewModel> arrayList;
    private final Context context;
    private LayoutInflater layoutInflater;

    public FeedsRVAdapter(ArrayList<FeedsViewModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        FeedsLayoutBinding feedsLayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.feeds_layout, parent, false);
        return new ViewHolder(feedsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedsViewModel myListViewModel = arrayList.get(position);
        holder.feedsLayoutBinding.setFeedsmodel(myListViewModel);
        holder.feedsLayoutBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final FeedsLayoutBinding feedsLayoutBinding;

        public ViewHolder(@NonNull FeedsLayoutBinding feedsLayoutBinding) {
            super(feedsLayoutBinding.getRoot());
            this.feedsLayoutBinding = feedsLayoutBinding;
            //onclick for layout to show data on fragment
            feedsLayoutBinding.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Log.v("ArraylistAdapter: ", "id: "+arrayList.get(getAdapterPosition()).id+" artistname: "+arrayList.get(getAdapterPosition()).artistname
//                            +" songname: "+arrayList.get(getAdapterPosition()).songname+" artistimage: "
//                            +arrayList.get(getAdapterPosition()).artistimage+" genres: "+arrayList.get(getAdapterPosition()).genres+" url:"
//                            +arrayList.get(getAdapterPosition()).url+" releaseDate: "+arrayList.get(getAdapterPosition()).releaseDate+" copyRights: "+arrayList.get(getAdapterPosition()).copyRights);
                    MainActivity activity = (MainActivity) context;
                    activity.frag(arrayList.get(getAdapterPosition()));
                }
            });
        }

        public FeedsLayoutBinding getFeedsLayoutBinding() {
            return feedsLayoutBinding;
        }


    }


}