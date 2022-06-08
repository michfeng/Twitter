package com.codepath.apps.restclienttemplate.models;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public List<String> imUrl;
    public String date;

    public Tweet () {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet ();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.imUrl = new ArrayList<>();
        tweet.date = jsonObject.getString("created_at");

        // if there are extended entities, which holds media for a tweet
        if (jsonObject.has("extended_entities")) {
            JSONObject ext_entities = jsonObject.getJSONObject("extended_entities");

            // checks whether there is media
            if (ext_entities.has("media")){
                JSONArray jArr = ext_entities.getJSONArray("media");
                tweet.imUrl.add(jArr.getJSONObject(0).getString("media_url"));
            }

            // note: imUrl is empty if there is no media, has one entry if there is
        }

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i< jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
