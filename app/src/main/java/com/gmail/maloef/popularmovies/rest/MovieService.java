package com.gmail.maloef.popularmovies.rest;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Example: http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=<apiKey>;
 */
public interface MovieService {

    @GET("/3/discover/movie")
    Call<List<Movie>> loadMovies(@Query("sort_by") String sortBy, @Query("api_key") String apiKey);

    @GET("/3/movie/{movieId}/videos")
    Call<List<Trailer>> loadTrailers(@Path("movieId") int movieId, @Query("api_key") String apiKey);

    @GET("/3/movie/{movieId}/reviews")
    Call<List<Review>> loadReviews(@Path("movieId") int movieId, @Query("api_key") String apiKey);
}
