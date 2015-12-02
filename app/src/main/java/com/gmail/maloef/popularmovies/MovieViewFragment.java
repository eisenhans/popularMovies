package com.gmail.maloef.popularmovies;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.fetch.FetchMoviesTask;

import java.util.ArrayList;
import java.util.List;

public class MovieViewFragment extends Fragment {
    private static final String LOG_TAG = MovieViewFragment.class.getSimpleName();

    public interface Callback {
        void onMovieSelected(Movie movie);
    }

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
        new FetchMoviesTask(getActivity(), posterAdapter).execute(movieSelection());
    }

    private String movieSelection() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String movieSelection = prefs.getString(getString(R.string.movie_selection_key), getString(R.string.movie_selection_entry_value_most_popular_first));

        return movieSelection;
    }
}
