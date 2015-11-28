package com.gmail.maloef.popularmovies.data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(authority = MovieProvider.AUTHORITY, database = MovieDatabase.class)
public class MovieProvider {

    public static final String AUTHORITY = "com.gmail.maloef.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path{
        String MOVIES = "movies";
        String TRAILERS = "trailers";
        String REVIEWS = "reviews";
    }

    @TableEndpoint(table = MovieDatabase.MOVIE)
    public static class Movie {

        /**
         * Finds all movies.
         *
         * Example: ...popularmovies/movies
         */
        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/movie",
                defaultSort = MovieColumns.RELEASE_DATE + " desc")
        public static final Uri MOVIES = BASE_CONTENT_URI.buildUpon().appendPath(Path.MOVIES).build();

        /**
         * Finds one movie by its database _id.
         *
         * Example: ...popularmovies/movies/123
         */
        @InexactContentUri(
                path = Path.MOVIES + "/#",
                name = "MOVIE_BY_ID",
                type = "vnd.android.cursor.item/movie",
                whereColumn = MovieColumns._ID,
                pathSegment = 1)
        public static Uri findById(int id) {
            return MOVIES.buildUpon().appendPath(String.valueOf(id)).build();
        }

//        /**
//         * Finds one movie by its movieId (i.e. the id used by openMovieDb)
//         *
//         * Example: ...popularmovies/movies/byMovieId/7890
//         */
//        @InexactContentUri(
//                path = Path.MOVIES + "/byMovieId/#",
//                name = "MOVIE_BY_MOVIE_ID",
//                type = "vnd.android.cursor.item/movie",
//                whereColumn = MovieColumns.MOVIE_ID,
//                pathSegment = 2)
//        public static Uri findByMovieId(int movieId) {
//            return MOVIES.buildUpon().appendPath("byMovieId").appendPath(String.valueOf(movieId)).build();
//        }
    }

    @TableEndpoint(table = MovieDatabase.TRAILER)
    public static class Trailer {

        /**
         * Finds all trailers.
         *
         * Example: ...popularmovies/trailers
         */
        @ContentUri(
                path = Path.TRAILERS,
                type = "vnd.android.cursor.dir/trailer",
                defaultSort = TrailerColumns._ID + " desc")
        public static final Uri TRAILERS = BASE_CONTENT_URI.buildUpon().appendPath(Path.TRAILERS).build();

        /**
         * Finds the list of trailers for one movie by the movie's database _id.
         *
         * Example: ...popularmovies/movies/123/trailers
         */
        @InexactContentUri(
                path = Path.MOVIES + "/#/" + Path.TRAILERS,
                name = "TRAILERS_BY_MOVIE",
                type = "vnd.android.cursor.dir/trailer",
                whereColumn = TrailerColumns.MOVIE,
                pathSegment = 1,
                defaultSort = TrailerColumns._ID + " asc")
        public static final Uri findByMovie(int movie) {
            return Movie.findById(movie).buildUpon().appendPath(Path.TRAILERS).build();
        }

//        /**
//         * Finds a trailer by its key.
//         *
//         * Example: ...popularmovies/trailers/7GqClqvlObY
//         */
//        @InexactContentUri(
//                path = Path.TRAILERS + "/*",
//                name = "FIND_TRAILER_BY_KEY",
//                type = "vnd.android.cursor.item/trailer",
//                whereColumn = TrailerColumns.KEY,
//                pathSegment = 1)
//        public static Uri findByKey(String key) {
//            return BASE_CONTENT_URI.buildUpon().appendPath(Path.TRAILERS).appendPath(key).build();
//        }
    }

    @TableEndpoint(table = MovieDatabase.REVIEW)
    public static class Review {

        /**
         * Finds all reviews.
         * <p/>
         * Example: ...popularmovies/reviews
         */
        @ContentUri(
                path = Path.REVIEWS,
                type = "vnd.android.cursor.dir/review",
                defaultSort = ReviewColumns._ID + " desc")
        public static final Uri REVIEWS = BASE_CONTENT_URI.buildUpon().appendPath(Path.REVIEWS).build();

        /**
         * Finds the list of reviews for one movie by the movie's database _id.
         *
         * Example: ...popularmovies/movies/123/reviews
         */
        @InexactContentUri(
                path = Path.MOVIES + "/#/" + Path.REVIEWS,
                name = "REVIEWS_BY_MOVIE",
                type = "vnd.android.cursor.dir/review",
                whereColumn = ReviewColumns.MOVIE,
                pathSegment = 1,
                defaultSort = ReviewColumns._ID + " asc")
        public static final Uri findByMovie(int movie) {
            return Movie.findById(movie).buildUpon().appendPath(Path.REVIEWS).build();
        }
    }
}
