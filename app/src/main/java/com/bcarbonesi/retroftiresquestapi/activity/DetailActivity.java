package com.bcarbonesi.retroftiresquestapi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcarbonesi.retroftiresquestapi.R;
import com.bcarbonesi.retroftiresquestapi.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");


        /*
        if(getIntent().hasExtra("movies_object")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            List<Movie> movieList = getIntent().getStringArrayExtra("movies_object");


            setImage(imageUrl, movieTitle);
        }*/


        if(getIntent().hasExtra("image_url")
                && getIntent().hasExtra("movie_title")
                && getIntent().hasExtra("movie_description")
                && getIntent().hasExtra("rating"))
        {
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String movieTitle = getIntent().getStringExtra("movie_title");
            String movieDescription = getIntent().getStringExtra("movie_description");
            String rating = getIntent().getStringExtra("rating");

            setImage(imageUrl, movieTitle, movieDescription, rating);
        }
    }


    private void setImage(String imageUrl, String movieTitle, String movieDescription, String rating){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.movie_title);
        name.setText(movieTitle);

        ImageView movieImage;
        movieImage = findViewById(R.id.movie_image);
            movieImage.setTransitionName("transitionName");

        Picasso.with(context)
                .load(imageUrl)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .into(movieImage);

        TextView movie_description = findViewById(R.id.movie_description);
        movie_description.setText(movieDescription);

        TextView rate = findViewById(R.id.rating);
        rate.setText(rating);
    }

}