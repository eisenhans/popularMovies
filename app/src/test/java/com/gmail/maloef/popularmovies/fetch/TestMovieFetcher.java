package com.gmail.maloef.popularmovies.fetch;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Trailer;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Markus on 18.11.2015.
 */
public class TestMovieFetcher /*extends AndroidTestCase*/ {

    private HttpUriRequester mockUriRequester = Mockito.mock(HttpUriRequester.class);
//    private HttpUriRequester mockUriRequester = new HttpUriRequester();

    private JsonParser jsonParser = new JsonParser();

    @Test
    public void testFetchMovies() {
        MovieFetcher fetcher = new MovieFetcher(mockUriRequester, jsonParser);

        List<Movie> movies = fetcher.fetchMovies(MovieFetcher.SORT_BY_POPULARITY);

        assertEquals(20, movies.size());

        Movie movie = movies.get(0);
        assertEquals("Spectre", movie.title);
    }

    @Test
    public void testFetchTrailer() {
        MovieFetcher fetcher = new MovieFetcher(mockUriRequester, jsonParser);

        List<Trailer> trailers = fetcher.fetchTrailers(206647);
        assertEquals(3, trailers.size());

        assertEquals("Spectre Ultimate 007 Trailer 2015 HD", trailers.get(0).name);
    }


}
