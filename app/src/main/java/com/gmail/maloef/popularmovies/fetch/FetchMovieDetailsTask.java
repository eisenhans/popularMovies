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

    private MovieDetails movieDetails;

    public FetchMovieDetailsTask(MovieDetails movieDetails) {
        this.movieDetails = movieDetails;
    }

    @Override
    protected MovieDetails doInBackground(Movie... params) {
        Movie movie = params[0];
        HttpUriRequester httpUriRequester = new HttpUriRequester();
        JsonParser parser = new JsonParser();
        MovieFetcher fetcher = new MovieFetcher(httpUriRequester, parser);

        return fetcher.fetchMovieDetails(movie);
    }

    @Override
    protected void onPostExecute(MovieDetails movieDetails) {
        this.movieDetails.trailers = movieDetails.trailers;
        this.movieDetails.reviews = movieDetails.reviews;

        Log.i(LOG_TAG, "finished reading details for movie " + movieDetails.movie.title +
                ": " + movieDetails.trailers.size() + " trailers and " + movieDetails.reviews.size() + " reviews");
    }
}
