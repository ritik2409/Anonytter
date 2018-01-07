package com.example.android.anonytter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ritik on 12/25/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ListViewHolder> {

    private  ArrayList<Posts> tweetList = new ArrayList<Posts>();


    public MyAdapter(ArrayList<Posts> tweetList) {
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

        Posts post = tweetList.get(position);

        holder.ListTweet.setText(post.getMessage());
        holder.name.setText(post.getAuthor());
        holder.time.setText(post.getTime());


    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ListViewHolder extends RecyclerView.ViewHolder {

        protected TextView ListTweet;
        protected TextView name;
        protected TextView time;


        public ListViewHolder(View itemView) {
            super(itemView);
            ListTweet = (TextView) itemView.findViewById(R.id.list);
            name = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tweetList.size();
    }
}

