package com.gmail.maloef.popularmovies.fetch;

import android.test.AndroidTestCase;

import com.gmail.maloef.popularmovies.Movie;
import com.gmail.maloef.popularmovies.MovieDataParser;
import com.gmail.maloef.popularmovies.Trailer;

import java.util.List;

/**
 * Created by Markus on 18.11.2015.
 */
public class TestMovieFetcher extends AndroidTestCase {

    public void testFetchMovies() {
        MovieDataParser parser = new MovieDataParser();
        MovieFetcher fetcher = new MovieFetcher(parser);

        List<Movie> movies = fetcher.fetchMovies(MovieFetcher.SORT_BY_POPULARITY);

        assertEquals(20, movies.size());

        Movie movie = movies.get(0);
        assertEquals("Spectre", movie.title);
    }

    public void testFetchTrailer() {
        MovieDataParser parser = new MovieDataParser();
        MovieFetcher fetcher = new MovieFetcher(parser);

        List<Trailer> trailers = fetcher.fetchTrailers(206647);
        assertEquals(3, trailers.size());

        assertEquals("Spectre Ultimate 007 Trailer 2015 HD", trailers.get(0).name);
    }


}
