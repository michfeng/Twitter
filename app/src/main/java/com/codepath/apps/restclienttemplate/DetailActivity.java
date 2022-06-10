package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TwitterClient client;
    TweetsAdapter adapter;
    Tweet tweet;
    ImageView ivProfileImage;
    TextView tvBody;
    TextView tvScreenName;
    ImageView imageView;
    TextView dateAdded;
    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_detail);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        // change color of actionbar
        ActionBar actionBar = getSupportActionBar();
        int color = ContextCompat.getColor(this, R.color.twitter_blue);
        ColorDrawable colorDrawable = new ColorDrawable(color);
        actionBar.setBackgroundDrawable(colorDrawable);

        // connect fields to respective parts of activity
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        imageView = (ImageView) findViewById(R.id.imageView);
        name = (TextView) findViewById(R.id.name);

        // bind
        tvBody.setText(tweet.body);
        tvScreenName.setText(tweet.user.screenName);


        name.setText(tweet.user.name);
        Glide.with(this).load(tweet.user.profileImageUrl).circleCrop().into(ivProfileImage);

        if (tweet.imUrl != null)
            Glide.with(this).load(tweet.imUrl).into(imageView);
    }
}