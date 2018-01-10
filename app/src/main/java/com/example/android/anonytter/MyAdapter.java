package com.example.android.anonytter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by ritik on 12/25/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ListViewHolder> {

    private  ArrayList<Posts> tweetList = new ArrayList<Posts>();
    Activity activity;
    int resource;


    public MyAdapter(ArrayList<Posts> tweetList, Activity activity, int resource) {
        this.tweetList = tweetList;
        this.activity = activity;
        this.resource = resource;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ListViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view


        View itemLayoutView = activity.getLayoutInflater()
                .inflate(resource,parent,false);

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
        Glide.with(activity).load(post.getImageuri()).into(holder.imageView);




    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ListViewHolder extends RecyclerView.ViewHolder {

        protected TextView ListTweet;
        protected TextView name;
        protected TextView time;
        protected ImageView imageView;


        public ListViewHolder(View itemView) {
            super(itemView);
            ListTweet = (TextView) itemView.findViewById(R.id.list);
            name = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time);
            imageView = (ImageView) itemView.findViewById(R.id.view_image);

        }
    }


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tweetList.size();
    }
}

