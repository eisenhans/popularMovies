package com.gmail.maloef.popularmovies.fetch;

import android.test.AndroidTestCase;
import android.text.Spanned;
import android.util.Log;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;

import java.util.List;

/**
 * Created by Markus on 18.11.2015.
 */
public class TestMovieFetcher extends AndroidTestCase {

    private HttpUriRequester mockUriRequester = new HttpUriRequester();

    private JsonParser jsonParser = new JsonParser();

    public void testFetchMovies() {
        MovieFetcher fetcher = new MovieFetcher(mockUriRequester, jsonParser);

        List<Movie> movies = fetcher.fetchMovies(MovieFetcher.SORT_BY_POPULARITY);

        assertEquals(20, movies.size());

        Movie movie = movies.get(0);
        assertEquals("Ant-Man", movie.title);
    }

    public void testFetchTrailers() {
        MovieFetcher fetcher = new MovieFetcher(mockUriRequester, jsonParser);

        List<Trailer> trailers = fetcher.fetchTrailers(206647);
        assertEquals(3, trailers.size());

        assertEquals("Spectre Ultimate 007 Trailer 2015 HD", trailers.get(0).name);
    }

    public void testFetchReviews() {
        MovieFetcher fetcher = new MovieFetcher(mockUriRequester, jsonParser);

        List<Review> reviews = fetcher.fetchReviews(206647);
        assertEquals(3, reviews.size());

        assertEquals("cutprintchris", reviews.get(0).author);
        Log.i(getClass().getSimpleName(), "content: " + reviews.get(0).content);
        assertTrue(reviews.get(0).content.contains("In hindsight my excitement for Spectre seems a bit foolish."));

        Spanned htmlLink = reviews.get(0).getHtmlLink();
        assertEquals("cutprintchris", htmlLink.toString());
    }


}
