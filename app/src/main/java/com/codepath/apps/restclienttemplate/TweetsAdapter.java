package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    // pass in context and list of tweets
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // inflate layout for each row
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    // bind values based on position of element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get data at position
        Tweet tweet = tweets.get(position);

        // bind tweet view view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    // clean elements from recycler
    public void clear () {
        tweets.clear();
        notifyDataSetChanged();
    }

    // add list of items, change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        notifyDataSetChanged();
    }

    // define viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        ImageView imageView;
        TextView dateAdded;
        TextView name;

        // for converting time to relativeTimeAgo
        private static final int SECOND_MILLIS = 1000;
        private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById((R.id.ivProfileImage));
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            imageView = itemView.findViewById(R.id.imageView);
            dateAdded = itemView.findViewById(R.id.dateAdded);
            name = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(this);
        }

        public void bind(Tweet tweet)  {
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            Glide.with(context).load(tweet.user.profileImageUrl).circleCrop().into(ivProfileImage);

            if (tweet.imUrl != null)
                Glide.with(context).load(tweet.imUrl).into(imageView);
            else
                imageView.setVisibility(View.GONE);

            dateAdded.setText(getRelativeTimeAgo(tweet.createdAt));

            name.setText(tweet.user.name);
        }

        private String getRelativeTimeAgo(String createdAt) {
            String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
            simpleDateFormat.setLenient(true);

            try {
                long time = simpleDateFormat.parse(createdAt).getTime();
                long now = System.currentTimeMillis();

                final long diff = now - time;
                if (diff < MINUTE_MILLIS) {
                    return "just now";
                } else if (diff < 2 * MINUTE_MILLIS) {
                    return "a minute ago";
                } else if (diff < 50 * MINUTE_MILLIS) {
                    return diff / MINUTE_MILLIS + " m";
                } else if (diff < 90 * MINUTE_MILLIS) {
                    return "an hour ago";
                } else if (diff < 24 * HOUR_MILLIS) {
                    return diff / HOUR_MILLIS + " h";
                } else if (diff < 48 * HOUR_MILLIS) {
                    return "yesterday";
                } else {
                    return diff / DAY_MILLIS + " d";
                }
            } catch (ParseException e) {
                Log.i("hi","relativeTimeAgo failed");
            }
            return "";
        }

        @Override
        public void onClick(View view) {
            // get position
            int position = getAdapterPosition();

            // make sure position is valid
            if (position != RecyclerView.NO_POSITION) {
                // get tweet at position
                Tweet tweet = tweets.get(position);
                Log.i("ABCDS","tweet: "+ tweet.body);

                // create intent for new activity
                Intent intent = new Intent(context,DetailActivity.class);

                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));

                // show activity
                context.startActivity(intent);
            }
        }
    }
}
