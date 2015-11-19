package com.gmail.maloef.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 01.11.2015.
 */
public class MovieDataParser {
    private static String LOG_TAG = MovieDataParser.class.getSimpleName();

    public List<Movie> getMovies(String movieJsonString) {
        try {
            JSONObject jsonObject = new JSONObject(movieJsonString);
            JSONArray jsonMovies = (JSONArray) jsonObject.get("results");

            List<Movie> movies = new ArrayList<>(jsonMovies.length());
            for (int i = 0; i < jsonMovies.length(); i++) {
                Movie movie = new Movie();
                JSONObject jsonMovie = (JSONObject) jsonMovies.get(i);
                movie.id = jsonMovie.getInt("id");
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
            // TODO
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Trailer> getTrailers(String trailerJsonString) {
        //    "id":"5641eb2fc3a3685bdc002e0b",
//            "iso_639_1":"en",
//            "key":"BOVriTeIypQ",
//            "name":"Spectre Ultimate 007 Trailer 2015 HD",
//            "site":"YouTube",

        try {
            JSONObject jsonObject = new JSONObject(trailerJsonString);
            JSONArray jsonTrailers = (JSONArray) jsonObject.get("results");

            List<Trailer> trailers = new ArrayList<>(jsonTrailers.length());
            for (int i = 0; i < jsonTrailers.length(); i++) {
                JSONObject jsonTrailer = (JSONObject) jsonTrailers.get(i);

                String key = jsonTrailer.getString("key");
                String name = jsonTrailer.getString("name");

                trailers.add(new Trailer(key, name));
            }
            return trailers;
        } catch (JSONException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }
}
