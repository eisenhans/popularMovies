package com.gmail.maloef.popularmovies;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.fetch.FetchMoviesTask;

import java.util.ArrayList;

public class MovieViewFragment extends Fragment {
    private static final String LOG_TAG = MovieViewFragment.class.getSimpleName();
    private static final String MOVIES_KEY = "movies";
    private static final String MOVIE_SELECTION_KEY = "movieSelection";

    public interface Callback {
        void onMovieSelected(Movie movie);
    }

    private ArrayList<Movie> movies = new ArrayList<>();
    private String currentMovieSelection;

    private PosterAdapter posterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (savedInstanceState != null) {
            currentMovieSelection = savedInstanceState.getString(MOVIE_SELECTION_KEY);
            movies = savedInstanceState.getParcelableArrayList(MOVIES_KEY);
            Log.i(LOG_TAG, "restored " + movies.size() + " movies from bundle");
        }

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final GridView movieView = (GridView) rootView.findViewById(R.id.movieGridView);

        posterAdapter = new PosterAdapter(getActivity(), movies);
        movieView.setAdapter(posterAdapter);

        movieView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = posterAdapter.getItem(position);

                ((Callback) getActivity()).onMovieSelected(movie);
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
        String newMovieSelection = movieSelection();
        if (movies.isEmpty() || !newMovieSelection.equals(currentMovieSelection)) {
            if (movies.isEmpty()) {
                Log.i(LOG_TAG, "loading movies because none are loaded");
            } else {
                Log.i(LOG_TAG, "reloading movies because movieSelection changed from " + currentMovieSelection + " to " + newMovieSelection);
            }
            new FetchMoviesTask(getActivity(), posterAdapter).execute(newMovieSelection);
            currentMovieSelection = newMovieSelection;
        } else {
            Log.i(LOG_TAG, "no need to reload movies: " + movies.size() + " movies loaded, and movieSelection " + newMovieSelection + " unchanged");
        }
    }

    private String movieSelection() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String movieSelection = prefs.getString(getString(R.string.movie_selection_key), getString(R.string.movie_selection_entry_value_most_popular_first));

        return movieSelection;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(MOVIE_SELECTION_KEY, currentMovieSelection);
        outState.putParcelableArrayList(MOVIES_KEY, movies);

        Log.i(LOG_TAG, "saved " + movies.size() + " movies to bundle");
    }
}
