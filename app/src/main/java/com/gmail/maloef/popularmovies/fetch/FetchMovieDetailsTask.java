package com.gmail.maloef.popularmovies.fetch;

import android.os.AsyncTask;
import android.util.Log;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.MovieDetails;

/**
 * Created by Markus on 24.11.2015.
 */
public class FetchMovieDetailsTask extends AsyncTask<Movie, Void, MovieDetails> {
    private static final String LOG_TAG = FetchMovieDetailsTask.class.getSimpleName();

    public interface LoadFinishedListener {
        void onLoadFinished(MovieDetails movieDetails);
    }

    private LoadFinishedListener loadFinishedListener;

    public FetchMovieDetailsTask(LoadFinishedListener loadFinishedListener) {
        this.loadFinishedListener = loadFinishedListener;
    }

    @Override
    protected MovieDetails doInBackground(Movie... params) {
        Movie movie = params[0];
        HttpUriRequester httpUriRequester = new HttpUriRequester();
        JsonParser parser = new JsonParser();
        MovieFetcher fetcher = new MovieFetcher(httpUriRequester, parser);

        Log.i(LOG_TAG, "simulating slow internet connection, waiting 5 sec");
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(LOG_TAG, "waiting finished!");

        return fetcher.fetchMovieDetails(movie);
    }

    @Override
    protected void onPostExecute(MovieDetails movieDetails) {

        Log.i(LOG_TAG, "finished reading details for movie " + movieDetails.movie.title +
                ": " + movieDetails.trailers.size() + " trailers and " + movieDetails.reviews.size() + " reviews");

        if (loadFinishedListener != null) {
            loadFinishedListener.onLoadFinished(movieDetails);
        }
    }
}
