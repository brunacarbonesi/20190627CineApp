package com.bcarbonesi.retroftiresquestapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcarbonesi.retroftiresquestapi.R;
import com.bcarbonesi.retroftiresquestapi.activity.DetailActivity;
import com.bcarbonesi.retroftiresquestapi.activity.MainActivity;
import com.bcarbonesi.retroftiresquestapi.model.Movie;
import com.bcarbonesi.retroftiresquestapi.model.MovieResponse;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import static android.provider.Settings.System.getString;
import static com.bcarbonesi.retroftiresquestapi.R.id.recycler_view;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private  String movieImage;
    private int rowLayout;
    private Context context;

    public static final String IMAGE_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342//";

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
        this.movieImage = movieImage;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{


        LinearLayout moviesLayout;
        TextView movieTitle;
        //TextView data;
        //TextView movieDescription;
        //TextView rating;
        ImageView movieImage;



        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            moviesLayout = itemView.findViewById(R.id.movies_layout);

            movieImage = itemView.findViewById(R.id.movie_image);
                movieImage.setTransitionName(String.valueOf(R.string.transition_name));
            movieTitle = itemView.findViewById(R.id.title);
            //data = itemView.findViewById(R.id.date);
            //movieDescription = itemView.findViewById(R.id.description);
            //rating = itemView.findViewById(R.id.rating);

        }
    }

    @NonNull
    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, final int i)
    {

        final String image_url = IMAGE_URL_BASE_PATH + movies.get(i).getPosterPath();

        Picasso.with(context)
                .load(image_url)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .into(movieViewHolder.movieImage);
        //movieViewHolder.movieTitle.setText(movies.get(i).getTitle());
        //movieViewHolder.data.setText(movies.get(i).getReleaseDate());
        //movieViewHolder.movieDescription.setText(movies.get(i).getOverview());
        //movieViewHolder.rating.setText(movies.get(i).getVoteAverage().toString());

        movieViewHolder.movieImage.setTransitionName("transitionName");

        Log.d("transition name",movieViewHolder.movieImage.getTransitionName());

        movieViewHolder.moviesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Log.d("Imagem:", "onClick: clicked on " + movies.get(i));


                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("movie_title", movies.get(i).getTitle());
                intent.putExtra("image_url", image_url);
                intent.putExtra("movie_description", movies.get(i).getOverview());
                intent.putExtra("rating", movies.get(i).getVoteAverage().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}


