package com.gmail.maloef.popularmovies.fetch;

import android.text.Spanned;

import com.gmail.maloef.popularmovies.BuildConfig;
import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)
public class MovieFetcherTest {

    private HttpUriRequester mockUriRequester = new HttpUriRequester();

    private JsonParser jsonParser = new JsonParser();

    @Test
    public void fetchMovies() {
        MovieFetcher fetcher = new MovieFetcher(mockUriRequester, jsonParser);

        List<Movie> movies = fetcher.fetchMoviesByPopularity();

        assertEquals(20, movies.size());

        Movie movie = movies.get(0);
        assertEquals("Ant-Man", movie.title);
    }

    @Test
    public void fetchTrailers() {
        MovieFetcher fetcher = new MovieFetcher(mockUriRequester, jsonParser);

        List<Trailer> trailers = fetcher.fetchTrailers(206647);
        assertEquals(3, trailers.size());

        assertEquals("Spectre Ultimate 007 Trailer", trailers.get(0).name);
    }

    @Test
    public void fetchReviews() {
        MovieFetcher fetcher = new MovieFetcher(mockUriRequester, jsonParser);

        List<Review> reviews = fetcher.fetchReviews(206647);
        assertEquals(3, reviews.size());

        assertEquals("cutprintchris", reviews.get(0).author);
        assertEquals("http://j.mp/1MPodnZ", reviews.get(0).url);

        Spanned htmlLink = reviews.get(0).getHtmlLink();
        assertEquals("cutprintchris", htmlLink.toString());
    }
}
