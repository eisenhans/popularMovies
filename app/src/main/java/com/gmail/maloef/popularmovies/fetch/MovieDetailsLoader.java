package com.gmail.maloef.popularmovies.fetch;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.MovieDetails;

/**
 * Created by Markus on 24.11.2015.
 */
public class MovieDetailsLoader extends AsyncTaskLoader<MovieDetails> {
    private static final String LOG_TAG = MovieDetailsLoader.class.getSimpleName();

    private final Movie movie;
    private MovieDetails movieDetails;

    public MovieDetailsLoader(Context context, Movie movie) {
        super(context);
        this.movie = movie;
    }

    @Override
    public MovieDetails loadInBackground() {
        HttpUriRequester httpUriRequester = new HttpUriRequester();
        JsonParser parser = new JsonParser();
        MovieFetcher fetcher = new MovieFetcher(httpUriRequester, parser);

//        Log.i(LOG_TAG, "simulating slow internet connection, waiting 5 sec");
//        try {
//            Thread.currentThread().sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.i(LOG_TAG, "waiting finished!");

        movieDetails = fetcher.fetchMovieDetails(movie);
        return movieDetails;
    }

    @Override
    protected void onStartLoading() {
        if (movieDetails == null) {
            forceLoad();
        }
    }
}
