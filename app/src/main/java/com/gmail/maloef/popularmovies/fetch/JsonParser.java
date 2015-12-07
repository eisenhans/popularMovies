package com.gmail.maloef.popularmovies.fetch;

import android.util.Log;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 01.11.2015.
 */
public class JsonParser {
    private static String LOG_TAG = JsonParser.class.getSimpleName();

    public List<Movie> getMovies(String movieJsonString) {
        System.out.println("movieJsonString: " + movieJsonString);
        if (movieJsonString == null) {
            return new ArrayList<>();
        }
        try {
            JSONObject jsonObject = new JSONObject(movieJsonString);
            JSONArray jsonMovies = (JSONArray) jsonObject.get("results");

            List<Movie> movies = new ArrayList<>(jsonMovies.length());
            for (int i = 0; i < jsonMovies.length(); i++) {
                Movie movie = new Movie();
                JSONObject jsonMovie = (JSONObject) jsonMovies.get(i);
                movie.movieId = jsonMovie.getInt("id");
                movie.title = jsonMovie.getString("title");
                movie.originalTitle = jsonMovie.getString("original_title");
                movie.releaseDate = jsonMovie.getString("release_date");
                movie.voteAverage = jsonMovie.getString("vote_average");
                movie.overview = jsonMovie.getString("overview");
                movie.posterPath = jsonMovie.getString("poster_path");

                movies.add(movie);
            }
            Log.i(LOG_TAG, "Parsed " + movies.size() + " movies: " + movies);
            return movies;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "could not read movies from string " + movieJsonString + "\nerror: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Trailer> getTrailers(String trailerJsonString) {
        //    "id":"5641eb2fc3a3685bdc002e0b",
//            "iso_639_1":"en",
//            "key":"BOVriTeIypQ",
//            "name":"Spectre Ultimate 007 Trailer 2015 HD",
//            "site":"YouTube",
        if (trailerJsonString == null) {
            return new ArrayList<>();
        }
        try {
            JSONObject jsonObject = new JSONObject(trailerJsonString);
            JSONArray jsonTrailers = (JSONArray) jsonObject.get("results");

            List<Trailer> trailers = new ArrayList<>(jsonTrailers.length());
            for (int i = 0; i < jsonTrailers.length(); i++) {
                JSONObject jsonTrailer = (JSONObject) jsonTrailers.get(i);

                Trailer trailer = new Trailer();
                trailer.key = jsonTrailer.getString("key");
                trailer.name = jsonTrailer.getString("name");
                trailers.add(trailer);
            }
            return trailers;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "could not read trailers from string " + trailerJsonString + "\nerror: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Review> getReviews(String reviewJsonString) {
        if (reviewJsonString == null) {
            return new ArrayList<>();
        }
        try {
            JSONObject jsonObject = new JSONObject(reviewJsonString);

            JSONArray jsonReviews = (JSONArray) jsonObject.get("results");

            List<Review> reviews = new ArrayList<>(jsonReviews.length());
            for (int i = 0; i < jsonReviews.length(); i++) {
                JSONObject jsonReview = (JSONObject) jsonReviews.get(i);

                Review review = new Review();
                review.author = jsonReview.getString("author");
                review.url = jsonReview.getString("url");
                reviews.add(review);
            }
            return reviews;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "could not read reviews from string " + reviewJsonString + "\nerror: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
