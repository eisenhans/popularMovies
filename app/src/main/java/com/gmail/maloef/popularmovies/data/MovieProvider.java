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
         * Example: ...popularmovies/movies/123         */
        @InexactContentUri(
                path = Path.MOVIES + "/#",
                name = "FIND_BY_ID",
                type = "vnd.android.cursor.item/movie",
                whereColumn = MovieColumns._ID,
                pathSegment = 1)
        public static Uri findById(int id) {
            return MOVIES.buildUpon().appendPath(String.valueOf(id)).build();
        }
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
                name = "FIND_BY_MOVIE",
                type = "vnd.android.cursor.dir/trailer",
                whereColumn = TrailerColumns.MOVIE,
                pathSegment = 1,
                defaultSort = TrailerColumns._ID + " asc")
        public static final Uri findByMovie(int movie) {
            return Movie.findById(movie).buildUpon().appendPath(Path.TRAILERS).build();
        }

        /**
         * Finds a trailer by its key.
         *
         * Example: ...popularmovies/trailers/7GqClqvlObY
         */
        @InexactContentUri(
                path = Path.TRAILERS + "/*",
                name = "FIND_BY_KEY",
                type = "vnd.android.cursor.item/trailer",
                whereColumn = TrailerColumns.KEY,
                pathSegment = 1)
        public static Uri findByKey(String key) {
            return BASE_CONTENT_URI.buildUpon().appendPath(Path.TRAILERS).appendPath(key).build();
        }
    }
}
