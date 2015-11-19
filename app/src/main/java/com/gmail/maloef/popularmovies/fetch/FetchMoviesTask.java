package com.gmail.maloef.popularmovies.fetch;

import android.os.AsyncTask;
import android.util.Log;

import com.gmail.maloef.popularmovies.Movie;
import com.gmail.maloef.popularmovies.MovieDataParser;
import com.gmail.maloef.popularmovies.PosterAdapter;

import java.util.List;

public class FetchMoviesTask extends AsyncTask<Integer, Void, List<Movie>> {
    private final String LOG_TAG = getClass().getName();

    private PosterAdapter posterAdapter;

    public FetchMoviesTask(PosterAdapter posterAdapter) {
        this.posterAdapter = posterAdapter;
    }

    @Override
    protected List<Movie> doInBackground(Integer... params) {
        int sortBy = MovieFetcher.SORT_BY_POPULARITY;
        if (params.length > 0) {
            sortBy = params[0];
        }
        MovieDataParser parser = new MovieDataParser();
        MovieFetcher fetcher = new MovieFetcher(parser);

        return fetcher.fetchMovies(sortBy);
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        Log.i(LOG_TAG, movies.size() + " movies found: " + movies);

        posterAdapter.clear();
        posterAdapter.addAll(movies);
    }
}