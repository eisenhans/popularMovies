package com.gmail.maloef.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.gmail.maloef.popularmovies.fetch.FetchMoviesTask;

/**
 * Created by Markus on 15.10.2015.
 */
public class MovieViewFragment extends Fragment {

    private PosterAdapter posterAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final GridView movieView = (GridView) rootView.findViewById(R.id.movieview);

        posterAdapter = new PosterAdapter(getActivity());
        movieView.setAdapter(posterAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        new FetchMoviesTask(posterAdapter).execute();
    }
}
