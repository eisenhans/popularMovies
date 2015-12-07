package com.gmail.maloef.popularmovies.rest;

import com.gmail.maloef.popularmovies.BuildConfig;
import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)
public class GsonParseTest {

    private Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    @Test
    public void parseMovieResponse() {
        String json = "{\"page\":1,\"results\":[{\"adult\":false,\"backdrop_path\":\"/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg\"," +
                "\"genre_ids\":[878,28,12],\"id\":102899,\"original_language\":\"en\",\"original_title\":\"Ant-Man\"," +
                "\"overview\":\"Armed with the astonishing ability to shrink in scale but increase in strength...\"," +
                "\"release_date\":\"2015-07-17\",\"poster_path\":\"/D6e8RJf2qUstnfkTslTXNTUAlT.jpg\",\"popularity\":59.795173," +
                "\"title\":\"Ant-Man\",\"video\":false,\"vote_average\":6.9,\"vote_count\":1833}]}";

        Type movieResponseType = new TypeToken<MovieDbResponse<Movie>>() {}.getType();
        MovieDbResponse<Movie> movieResponse = gson.fromJson(json, movieResponseType);

        List<Movie> results = movieResponse.results;
        assertEquals(1, results.size());
        Movie movie = results.get(0);

        assertEquals("Ant-Man", movie.title);
        assertEquals("Ant-Man", movie.originalTitle);
    }

    @Test
    public void parseTrailerResponse() {
        String json = "{\"id\":206647,\"results\":[{\"id\":\"5641eb2fc3a3685bdc002e0b\",\"iso_639_1\":\"en\",\"key\":\"BOVriTeIypQ\"," +
                "\"name\":\"Spectre Ultimate 007 Teaser\",\"site\":\"YouTube\",\"size\":1080,\"type\":\"Teaser\"}," +
                "{\"id\":\"56437778c3a36870e30027df\",\"iso_639_1\":\"en\",\"key\":\"7GqClqvlObY\",\"name\":\"Trailer #2\"," +
                "\"site\":\"YouTube\",\"size\":1080,\"type\":\"Trailer\"},{\"id\":\"56437756c3a36870ec0023f7\",\"iso_639_1\":\"en\"," +
                "\"key\":\"xf8wVezS3JY\",\"name\":\"#1 Movie in the world!\",\"site\":\"YouTube\",\"size\":1080,\"type\":\"Clip\"}]}";

        Type trailerResponseType = new TypeToken<MovieDbResponse<Trailer>>() {}.getType();
        MovieDbResponse<Trailer> trailerResponse = gson.fromJson(json, trailerResponseType);

        List<Trailer> results = trailerResponse.results;
        assertEquals(3, results.size());

        Trailer trailer = results.get(0);
        assertEquals("Spectre Ultimate 007 Teaser", trailer.name);
        assertEquals("BOVriTeIypQ", trailer.key);
    }

    @Test
    public void parseReviewResponse() {
        String json = "{\"id\":206647,\"page\":1,\"results\":[{\"id\":\"5649fed29251413ad500b5ac\",\"author\":\"Andres Gomez\"," +
                "\"content\":\"...\",\"url\":\"http://j.mp/1kZCv8N\"}],\"total_pages\":1,\"total_results\":1}";

        Type reviewResponseType = new TypeToken<MovieDbResponse<Review>>() {}.getType();
        MovieDbResponse<Review> reviewResponse = gson.fromJson(json, reviewResponseType);

        List<Review> results = reviewResponse.results;
        assertEquals(1, results.size());

        Review review = results.get(0);
        assertEquals("Andres Gomez", review.author);
        assertEquals("http://j.mp/1kZCv8N", review.url);
    }
}
