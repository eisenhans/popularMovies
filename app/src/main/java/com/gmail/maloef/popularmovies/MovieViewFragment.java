package com.gmail.maloef.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gmail.maloef.popularmovies.fetch.FetchMoviesTask;
import com.gmail.maloef.popularmovies.fetch.MovieFetcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 15.10.2015.
 */
public class MovieViewFragment extends Fragment {
    private static final String LOG_TAG = MovieViewFragment.class.getSimpleName();

    private PosterAdapter posterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final GridView movieView = (GridView) rootView.findViewById(R.id.movieGridView);

        List<Movie> movies = new ArrayList<Movie>();
        posterAdapter = new PosterAdapter(getActivity(), movies);
        movieView.setAdapter(posterAdapter);

        movieView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = posterAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        new FetchMoviesTask(posterAdapter).execute(sortCriteria());
    }

    private Integer sortCriteria() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortBy = prefs.getString(
                getString(R.string.sort_by_key), getString(R.string.sort_by_entry_value_popularity));

        // TODO: replace with constants from MovieFetcher
        if (sortBy.equals("popularity")) {
            return MovieFetcher.SORT_BY_POPULARITY;
        }
        if (sortBy.equals("release_date")) {
            return MovieFetcher.SORT_BY_RELEASE_DATE;
        }
        if (sortBy.equals("release_date")) {
            return MovieFetcher.SORT_BY_RELEASE_DATE;
        }
        Log.w(LOG_TAG, "unexpected sortBy preference: " +  sortBy + " - using SORT_BY_POPULARITY");
        return MovieFetcher.SORT_BY_POPULARITY;
    }
}
