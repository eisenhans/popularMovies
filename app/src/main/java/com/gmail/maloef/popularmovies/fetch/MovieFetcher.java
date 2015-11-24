package com.gmail.maloef.popularmovies.fetch;

import android.net.Uri;
import android.util.Log;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.MovieDetails;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;

import java.util.List;

/**
 * Created by Markus on 18.11.2015.
 */
public class MovieFetcher {
    private static final String LOG_TAG = MovieFetcher.class.getSimpleName();

    public static final int SORT_BY_POPULARITY = 0;
    public static final int SORT_BY_RELEASE_DATE = 1;
    public static final int SORT_BY_FAVORITES = 2;

    private final HttpUriRequester httpUriRequester;
    private JsonParser parser;

    public MovieFetcher(HttpUriRequester httpUriRequester, JsonParser parser) {
        this.httpUriRequester = httpUriRequester;
        this.parser = parser;
    }

    private String sortByParameter(int sortBy) {
        switch (sortBy) {
            case SORT_BY_POPULARITY: return "popularity";
            case SORT_BY_RELEASE_DATE: return "release_date";
            case SORT_BY_FAVORITES: throw new UnsupportedOperationException("not implemented yet");
            default: throw new IllegalArgumentException("sortBy: " + sortBy);
        }
    }

    public List<Movie> fetchMovies(int sortBy) {
        String sortByParameter = sortByParameter(sortBy);

        // example: http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=<apiKey>;
        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("sort_by", sortByParameter + ".desc")
                .appendQueryParameter("api_key", ApiKeyHolder.API_KEY).build();

        String json = httpUriRequester.sendGet(uri);
        List<Movie> movies = parser.getMovies(json);
        Log.i(LOG_TAG, "looked up " + movies.size() + " movies in web");

        return movies;
    }

    public MovieDetails fetchMovieDetails(Movie movie) {
        MovieDetails details = new MovieDetails(movie);

        details.trailers = fetchTrailers(movie.id);
        details.reviews = fetchReviews(movie.id);

        return details;
    }

    public List<Trailer> fetchTrailers(int movieId) {
        // example: http://api.themoviedb.org/3/movie/206647/videos?api_key=<apiKey>
        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("videos")
                .appendQueryParameter("api_key", ApiKeyHolder.API_KEY).build();

        String json = httpUriRequester.sendGet(uri);
        List<Trailer> trailers = parser.getTrailers(json);
        Log.i(LOG_TAG, "Looked up trailers in web: " + trailers);

        return trailers;
    }

    public List<Review> fetchReviews(int movieId) {
        // example: http://api.themoviedb.org/3/movie/206647/reviews?api_key=<apiKey>
        Uri uri = new Uri.Builder()
                .scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(String.valueOf(movieId))
                .appendPath("reviews")
                .appendQueryParameter("api_key", ApiKeyHolder.API_KEY).build();

        String json = httpUriRequester.sendGet(uri);
        List<Review> reviews = parser.getReviews(json);
        Log.i(LOG_TAG, "Looked up reviews in web: " + reviews);

        return reviews;
    }
}
