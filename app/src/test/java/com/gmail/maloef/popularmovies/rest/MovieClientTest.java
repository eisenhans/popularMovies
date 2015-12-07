package com.gmail.maloef.popularmovies.rest;

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
public class MovieClientTest {
    private static final String LOG_TAG = MovieClientTest.class.getName();

    MovieClient movieClient = new MovieClient();

    @Test
    public void fetchMovies() {
        List<Movie> movies = movieClient.fetchMoviesByPopularity();
        System.out.println("movies: " + movies);

        assertEquals(20, movies.size());

        Movie movie = movies.get(0);
        assertEquals("Ant-Man", movie.title);
    }

    @Test
    public void fetchTrailers() {
        List<Trailer> trailers = movieClient.fetchTrailers(206647);
        assertEquals(3, trailers.size());

        assertEquals("Spectre Ultimate 007 Teaser", trailers.get(0).name);
    }

    @Test
    public void fetchReviews() {
        List<Review> reviews = movieClient.fetchReviews(206647);
        assertEquals(3, reviews.size());

        assertEquals("cutprintchris", reviews.get(0).author);
        assertEquals("http://j.mp/1MPodnZ", reviews.get(0).url);

        Spanned htmlLink = reviews.get(0).getHtmlLink();
        assertEquals("cutprintchris", htmlLink.toString());
    }

}
