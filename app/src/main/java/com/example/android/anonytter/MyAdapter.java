package com.example.android.anonytter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ritik on 12/25/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ListViewHolder> {

     ArrayList<String> tweetList;

    public MyAdapter(ArrayList<String> tweetList) {
        this.tweetList = tweetList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_list,parent,false);

        // create ViewHolder

        ListViewHolder viewHolder = new ListViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)

    public void onBindViewHolder(MyAdapter.ListViewHolder holder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        holder.ListTweetNumberView.setText(tweetList.get(position).toString());


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ListViewHolder extends RecyclerView.ViewHolder {

        protected TextView ListTweetNumberView;


        public ListViewHolder(View itemView) {
            super(itemView);
            ListTweetNumberView = (TextView) itemView.findViewById(R.id.list);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tweetList.size();
    }
}

