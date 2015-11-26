package com.gmail.maloef.popularmovies.data;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.gmail.maloef.popularmovies.domain.Movie;

public class MovieCursor extends CursorWrapper {

    public MovieCursor(Cursor cursor) {
        super(cursor);
    }

    public Movie getMovie() {
        Movie movie = new Movie();
        movie._id = getInt(getColumnIndex(MovieColumns._ID));
        movie.movieId = getInt(getColumnIndex(MovieColumns.MOVIE_ID));
        movie.title = getString(getColumnIndex(MovieColumns.TITLE));
        movie.originalTitle = getString(getColumnIndex(MovieColumns.ORIGINAL_TITLE));
        movie.overview = getString(getColumnIndex(MovieColumns.OVERVIEW));
        movie.releaseDate = getString(getColumnIndex(MovieColumns.RELEASE_DATE));
        movie.voteAverage = getString(getColumnIndex(MovieColumns.VOTE_AVERAGE));
        movie.posterPath = getString(getColumnIndex(MovieColumns.POSTER_PATH));

        return movie;
    }
}
