package com.gmail.maloef.popularmovies.rest;

import android.util.Log;

import com.gmail.maloef.popularmovies.BuildConfig;
import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.MovieDetails;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieClient {
    private static final String LOG_TAG = MovieClient.class.getName();
    private static final String BASE_URL = "http://api.themoviedb.org";

    private MovieService movieService;

    public MovieClient() {
        //Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(new MovieConverterFactory()).build();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        movieService = retrofit.create(MovieService.class);
    }

    public List<Movie> fetchMoviesByPopularity() {
        return fetchMovies("popularity");
    }

    public List<Movie> fetchMoviesByReleaseDate() {
        return fetchMovies("release_date");
    }

    private List<Movie> fetchMovies(String sortBy) {
        Call<MovieDbResponse<Movie>> movieCall = movieService.loadMovies(sortBy + ".desc", BuildConfig.MOVIE_DB_API_KEY);
        try {
            Response<MovieDbResponse<Movie>> response = movieCall.execute();
            return response.body().results;
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
        Call<MovieDbResponse<Trailer>> trailerCall = movieService.loadTrailers(movieId, BuildConfig.MOVIE_DB_API_KEY);
        try {
            Response<MovieDbResponse<Trailer>> response = trailerCall.execute();
            return response.body().results;
        } catch (IOException e) {
            Log.e(LOG_TAG, "could not load trailers: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    List<Review> fetchReviews(int movieId) {
        Call<MovieDbResponse<Review>> reviewCall = movieService.loadReviews(movieId, BuildConfig.MOVIE_DB_API_KEY);
        try {
            Response<MovieDbResponse<Review>> response = reviewCall.execute();
            return response.body().results;
        } catch (IOException e) {
            Log.e(LOG_TAG, "could not load reviews: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
