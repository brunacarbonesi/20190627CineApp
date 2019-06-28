package com.bcarbonesi.retroftiresquestapi.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LogPrinter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;

import com.bcarbonesi.retroftiresquestapi.R;
import com.bcarbonesi.retroftiresquestapi.adapter.MoviesAdapter;
import com.bcarbonesi.retroftiresquestapi.model.Movie;
import com.bcarbonesi.retroftiresquestapi.model.MovieApiService;
import com.bcarbonesi.retroftiresquestapi.model.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    //public static final String BASE_URL = "https://www.myapifilms.com/";

    public static final String BASE_URL = "http://api.themoviedb.org/3/";

    private static Retrofit retrofit = null;

    private RecyclerView recyclerView = null;

    private static final String API_KEY = "28462c086aa50bfdb7f5d824170f5df7";

    private RecyclerView.LayoutManager mLayoutManager;
    private Context mcontext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        connectAndGetApiData();
    }


    private void connectAndGetApiData()
    {
        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

    MovieApiService movieApiService = retrofit.create(MovieApiService.class);

    Call<MovieResponse> call = movieApiService.getTopRatedMovies(API_KEY);

    call.enqueue(new Callback<MovieResponse>()
    {
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response)
        {
            int numberOfColumns = 3;

            List<Movie> movies = response.body().getResults();

            MoviesAdapter adapter =  new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());

            recyclerView.setAdapter(adapter);

            // Define a layout for RecyclerView
            mLayoutManager = new GridLayoutManager(mcontext,3);
            recyclerView.setLayoutManager(mLayoutManager);


            if (movies == null) {
                Log.d("Lista movies:", "vazia");
            } else {
                Log.d("Lista movies:", "preenchida");
            }

            if (response.body() == null) {
                Log.d("Response Body:", "vazio");
            } else {
                Log.d("Response Body:", "preenchido");
            }

            if(response.code() == 200){
                Log.d("RESPONSE CODE:", "ok!");
            }
            else {
                Log.d("RESPONSE CODE:", "Error");
            }
        }

        public void onFailure(Call<MovieResponse> call, Throwable throwable)
            {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    // MENU NA ACTION BAR ///////////////////////////////////////////////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    // COMPLETED (7) Override onOptionsItemSelected to handle clicks on the refresh button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            //mForecastAdapter.setWeatherData(null);
            //loadWeatherData();
            return true;
        }
        else if (id == R.id.action_app_info) {
            return true;
        }
        else if (id == R.id.action_developer_info) {

        }
        else if (id == R.id.action_search) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void animateIntent(View view)
    {
        Intent intent = new Intent(this, DetailActivity.class);
        String transitionName = getString(R.string.transition_name);

        View viewStart = findViewById(R.id.recycler_view);

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, viewStart, transitionName);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}

