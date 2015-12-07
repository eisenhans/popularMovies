package com.gmail.maloef.popularmovies.rest;

import android.util.Log;

import com.gmail.maloef.popularmovies.BuildConfig;
import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.MovieDetails;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieClient {
    private static final String LOG_TAG = MovieClient.class.getName();
    private static final String BASE_URL = "http://api.themoviedb.org";

    private MovieService movieService;

    public MovieClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(new MovieConverterFactory()).build();
        //Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        movieService = retrofit.create(MovieService.class);
    }

    public List<Movie> fetchMoviesByPopularity() {
        return fetchMovies("popularity");
    }

    public List<Movie> fetchMoviesByReleaseDate() {
        return fetchMovies("release_date");
    }

    private List<Movie> fetchMovies(String sortBy) {
        Call<List<Movie>> movieCall = movieService.loadMovies(sortBy + ".desc", BuildConfig.MOVIE_DB_API_KEY);
        try {
            Response<List<Movie>> response = movieCall.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "could not load movies: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public MovieDetails fetchMovieDetails(Movie movie) {
        MovieDetails details = new MovieDetails(movie);

        details.trailers = fetchTrailers(movie.movieId);
        details.reviews = fetchReviews(movie.movieId);

        return details;
    }

    List<Trailer> fetchTrailers(int movieId) {
        Call<List<Trailer>> trailerCall = movieService.loadTrailers(movieId, BuildConfig.MOVIE_DB_API_KEY);
        try {
            Response<List<Trailer>> response = trailerCall.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "could not load trailers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    List<Review> fetchReviews(int movieId) {
        Call<List<Review>> reviewCall = movieService.loadReviews(movieId, BuildConfig.MOVIE_DB_API_KEY);
        try {
            Response<List<Review>> response = reviewCall.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(LOG_TAG, "could not load reviews: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
