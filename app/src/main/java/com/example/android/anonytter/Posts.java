package com.example.android.anonytter;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ritik on 12/27/2017.
 */

public class Posts {

    public String author;
    public String message;
    public String time;
    public String imageuri;

    public Posts() {
        // Default constructor required for calls to DataSnapshot.getValue(Posts.class)
    }

    public Posts(String author, String message,String time,String imageuri) {
        this.author = author;
        this.message = message;
        this.time = time;
        this.imageuri = imageuri;
    }


    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getImageuri() {
        return imageuri;
    }
}

