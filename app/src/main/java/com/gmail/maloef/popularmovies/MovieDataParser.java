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
    private static String LOG_TAG = MovieDataParser.class.getName();

    public List<Movie> getMovies(String movieJsonString, int howMany) throws JSONException {
        JSONObject jsonObject = new JSONObject(movieJsonString);
        JSONArray jsonMovies = (JSONArray) jsonObject.get("results");

        int count = jsonMovies.length();
        if (count > howMany) {
            count = howMany;
        }
        List<Movie> movies = new ArrayList<Movie>(count);
        for (int i = 0; i < count; i++) {
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
//
//        System.out.println("jsonObject: " + jsonObject);
//        System.out.println("list: " + list);
//
//        JSONObject day = list.getJSONObject(index);
//        JSONObject temp = (JSONObject) day.get("temp");
//        Double max = (Double) temp.get("max");

        return movies;
    }
}
