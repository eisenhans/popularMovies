package com.gmail.maloef.popularmovies.fetch;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.gmail.maloef.popularmovies.Movie;
import com.gmail.maloef.popularmovies.MovieDataParser;
import com.gmail.maloef.popularmovies.PosterAdapter;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {
    private final String LOG_TAG = getClass().getName();

    private MovieDataParser parser = new MovieDataParser();

    private PosterAdapter posterAdapter;

    public FetchMoviesTask(PosterAdapter posterAdapter) {
        this.posterAdapter = posterAdapter;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String json = null;

        try {
            // example: http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=<apiKey>;
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                    .appendQueryParameter("sort_by", "popularity.desc")
                    .appendQueryParameter("api_key", ApiKeyHolder.API_KEY);
            Uri uri = uriBuilder.build();

            Log.i(LOG_TAG, "looking up movies from uri " + uri);
            URL url = new URL(uri.toString());

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
            json = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally{
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
        Log.i(LOG_TAG, "Looked up movies in web: " + json);
        try {
            return parser.getMovies(json, 20);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<Movie>();
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        Log.i(LOG_TAG, movies.size() + " movies found: " + movies);

        posterAdapter.clear();
        posterAdapter.addAll(movies);
    }
}