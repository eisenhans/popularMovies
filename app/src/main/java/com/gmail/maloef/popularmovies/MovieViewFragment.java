package com.gmail.maloef.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gmail.maloef.popularmovies.fetch.FetchMoviesTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 15.10.2015.
 */
public class MovieViewFragment extends Fragment {

    public static enum SortCriteria {
        Popularity,
        Release_Date;
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
        new FetchMoviesTask(posterAdapter).execute(sortCriteria().name());
    }

    private SortCriteria sortCriteria() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String location = prefs.getString(
//                getString(R.string.pref_location_key), getString(pref_location_default));
        return SortCriteria.Release_Date;
    }
}
