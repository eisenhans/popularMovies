package com.gmail.maloef.popularmovies.rest;

import android.util.Log;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;
import com.gmail.maloef.popularmovies.fetch.JsonParser;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import retrofit.Converter;

public class MovieConverterFactory extends Converter.Factory {
    private static final String LOG_TAG = MovieConverterFactory.class.getName();

    private static JsonParser jsonParser = new JsonParser();

    public static class MovieConverter implements Converter<ResponseBody, List<?>> {
        @Override
        public List<Movie> convert(ResponseBody body) throws IOException {
            List<Movie> movies = jsonParser.getMovies(body.string());
            return movies;
        }
    }

    public class TrailerConverter implements Converter<ResponseBody, List<?>> {
        @Override
        public List<Trailer> convert(ResponseBody body) throws IOException {
            List<Trailer> trailers = jsonParser.getTrailers(body.string());
            return trailers;
        }
    }

    public class ReviewConverter implements Converter<ResponseBody, List<?>> {
        @Override
        public List<Review> convert(ResponseBody body) throws IOException {
            List<Review> reviews = jsonParser.getReviews(body.string());
            return reviews;
        }
    }

    @Override
    public Converter<ResponseBody, List<?>> fromResponseBody(Type type, Annotation[] annotations) {
        if (type instanceof ParameterizedType) {
            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
            Log.i(LOG_TAG, "actual type of response object: " + actualType);

            if (actualType.equals(Movie.class)) {
                return new MovieConverter();
            }
            if (actualType.equals(Trailer.class)) {
                return new TrailerConverter();
            }
            if (actualType.equals(Review.class)) {
                return new ReviewConverter();
            }
        }
        return null;
    }
}
