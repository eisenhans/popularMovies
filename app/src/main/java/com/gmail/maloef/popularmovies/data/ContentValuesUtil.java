package com.gmail.maloef.popularmovies.data;

import android.content.ContentValues;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;

import java.util.List;

public class ContentValuesUtil {

    public static ContentValues fromMovie(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put("movieId", movie.movieId);
        cv.put("title", movie.title);
        cv.put("originalTitle", movie.originalTitle);
        cv.put("releaseDate", movie.releaseDate);
        cv.put("posterPath", movie.posterPath);
        cv.put("overview", movie.overview);
        cv.put("voteAverage", movie.voteAverage);

        return cv;
    }

    public static ContentValues[] fromTrailers(List<Trailer> trailers) {
        ContentValues[] values = new ContentValues[trailers.size()];
        for (int i = 0; i < trailers.size(); i++) {
            values[i] = fromTrailer(trailers.get(i));
        }
        return values;
    }

    public static ContentValues fromTrailer(Trailer trailer) {
        ContentValues cv = new ContentValues();
        cv.put("key", trailer.key);
        cv.put("name", trailer.name);
        cv.put("movie", trailer.movie);

        return cv;
    }

    public static ContentValues[] fromReviews(List<Review> reviews) {
        ContentValues[] values = new ContentValues[reviews.size()];
        for (int i = 0; i < reviews.size(); i++) {
            values[i] = fromReview(reviews.get(i));
        }
        return values;
    }

    public static ContentValues fromReview(Review review) {
        ContentValues cv = new ContentValues();
        cv.put("author", review.author);
        cv.put("url", review.url);
        cv.put("movie", review.movie);

        return cv;
    }
}
