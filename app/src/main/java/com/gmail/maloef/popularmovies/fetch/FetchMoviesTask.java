package com.gmail.maloef.popularmovies.fetch;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.gmail.maloef.popularmovies.PosterAdapter;
import com.gmail.maloef.popularmovies.data.MovieColumns;
import com.gmail.maloef.popularmovies.data.MovieCursor;
import com.gmail.maloef.popularmovies.data.MovieProvider;
import com.gmail.maloef.popularmovies.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
    private static final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

    private Context context;
    private PosterAdapter posterAdapter;

    public FetchMoviesTask(Context context, PosterAdapter posterAdapter) {
        this.context = context;
        this.posterAdapter = posterAdapter;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        if (params.length == 0) {
            throw new IllegalStateException("no movie selection");
        }
        String movieSelection = params[0];
        if (movieSelection.equals("favorites_only")) {
            return readMoviesFromContentResolver();
        }
        return readMoviesFromWeb(movieSelection);
    }

    private List<Movie> readMoviesFromContentResolver() {
        // TODO: problems if there are many favorite movies?
        Cursor cursor = context.getContentResolver().query(MovieProvider.Movie.MOVIES, null, null, null, MovieColumns.RELEASE_DATE + " desc");
        MovieCursor movieCursor = new MovieCursor(cursor);
        List<Movie> movies = new ArrayList<>(movieCursor.getCount());
        while (movieCursor.moveToNext()) {
            movies.add(movieCursor.getMovie());
        }
        return movies;
    }

    private List<Movie> readMoviesFromWeb(String movieSelection) {
        HttpUriRequester httpUriRequester = new HttpUriRequester();
        JsonParser parser = new JsonParser();
        MovieFetcher fetcher = new MovieFetcher(httpUriRequester, parser);

        if (movieSelection.equals("most_popular_first")) {
            return fetcher.fetchMoviesByPopularity();
        }
        if (movieSelection.equals("newest_first")) {
            return fetcher.fetchMoviesByReleaseDate();
        }
        throw new IllegalStateException("unexpected movie selection: " + movieSelection + " - expected most_popular_first or newest_first");
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        Log.i(LOG_TAG, movies.size() + " movies found: " + movies);

        posterAdapter.clear();
        posterAdapter.addAll(movies);
    }
}