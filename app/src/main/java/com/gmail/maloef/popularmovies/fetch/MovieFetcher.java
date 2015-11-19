package com.gmail.maloef.popularmovies.fetch;

import android.net.Uri;
import android.util.Log;

import com.gmail.maloef.popularmovies.Movie;
import com.gmail.maloef.popularmovies.MovieDataParser;
import com.gmail.maloef.popularmovies.Trailer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Markus on 18.11.2015.
 */
public class MovieFetcher {
    private static final String LOG_TAG = MovieFetcher.class.getSimpleName();

    public static final int SORT_BY_POPULARITY = 0;
    public static final int SORT_BY_RELEASE_DATE = 1;
    public static final int SORT_BY_FAVORITES = 2;

    private MovieDataParser parser;

    public MovieFetcher(MovieDataParser parser) {
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

        String json = readJson(uri);
        List<Movie> movies = parser.getMovies(json);
        Log.i(LOG_TAG, "looked up " + movies.size() + " movies in web");

        return movies;
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

        String json = readJson(uri);
        List<Trailer> trailers = parser.getTrailers(json);
        Log.i(LOG_TAG, "Looked up trailers in web: " + trailers);

        return trailers;
    }

    private String readJson(Uri uri) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(uri.toString());

            // TODO the following contains a lot of boilerplate code - there must be an easier way!?
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            return buffer.toString();
        } catch (IOException e) {

            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }
}
