package com.gmail.maloef.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gmail.maloef.popularmovies.domain.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            Movie movie = (Movie) getIntent().getParcelableExtra("movie");

            if (movie != null) {
                Bundle arguments = new Bundle();
                arguments.putParcelable("movie", movie);

                DetailFragment fragment = new DetailFragment();
                fragment.setArguments(arguments);

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container, fragment)
                        .commit();
            }
        }
    }

}
