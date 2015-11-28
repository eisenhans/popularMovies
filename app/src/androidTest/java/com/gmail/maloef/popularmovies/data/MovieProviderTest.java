package com.gmail.maloef.popularmovies.data;


import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.test.AndroidTestCase;
import android.util.Log;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;

public class MovieProviderTest extends AndroidTestCase {
    private static final String LOG_TAG = MovieProviderTest.class.getSimpleName();

    public void setUp() {
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.delete(MovieProvider.Trailer.TRAILERS, null, null);
        contentResolver.delete(MovieProvider.Review.REVIEWS, null, null);
        contentResolver.delete(MovieProvider.Movie.MOVIES, null, null);
    }

    public void testProviderRegistered() throws PackageManager.NameNotFoundException {
        PackageManager pm = mContext.getPackageManager();

        String generatedProviderClassName = com.gmail.maloef.popularmovies.data.generated.MovieProvider.class.getName();

        ComponentName componentName = new ComponentName(mContext.getPackageName(), generatedProviderClassName);
        // Fetch the provider info using the component name from the PackageManager
        // This throws an exception if the provider isn't registered.
        ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

        // Make sure that the registered authority matches the authority from the Contract.
        assertEquals(MovieProvider.AUTHORITY, providerInfo.authority);
        assertEquals(generatedProviderClassName, providerInfo.name);
    }

    public void testInsertMovie() {
        insertMovie(123, "Youth");

        Cursor cursor = getContentResolver().query(MovieProvider.Movie.MOVIES, null, null, null, null);
        MovieCursor movieCursor = new MovieCursor(cursor);
        assertEquals(1, movieCursor.getCount());
        assertTrue(movieCursor.moveToFirst());

        Movie movie = movieCursor.getMovie();

        int id = cursor.getInt(cursor.getColumnIndex(MovieColumns._ID));

        int movieId = cursor.getInt(cursor.getColumnIndex(MovieColumns.MOVIE_ID));
        assertEquals(123, movieId);
        assertEquals(123, movie.movieId);

        String title = cursor.getString(cursor.getColumnIndex(MovieColumns.TITLE));
        assertEquals("Youth", title);
        assertEquals("Youth", movie.title);

        Cursor byIdCursor = getContentResolver().query(MovieProvider.Movie.findById(id), null, null, null, null);
        MovieCursor movieByIdCursor = new MovieCursor(byIdCursor);
        assertEquals(1, movieByIdCursor.getCount());
        assertTrue(movieByIdCursor.moveToFirst());

        Movie movieById = movieCursor.getMovie();
        Log.i(getClass().getSimpleName(), "Movie from db by _id: " + movieById);
        assertEquals(123, movieById.movieId);
        assertEquals("Youth", movieById.title);

        Cursor byMovieIdCursor = getContentResolver().query(
                MovieProvider.Movie.MOVIES,
                null,
                "movieId = ?",
                new String[]{String.valueOf(movie.movieId)},
                null);
        MovieCursor movieByMovieIdCursor = new MovieCursor(byMovieIdCursor);
        assertEquals(1, movieByMovieIdCursor.getCount());
        assertTrue(movieByMovieIdCursor.moveToFirst());

        Movie movieByMovieId = movieByMovieIdCursor.getMovie();
        Log.i(LOG_TAG, "Movie from db by movieId: " + movieByMovieId);
        assertEquals(123, movieByMovieId.movieId);
        assertEquals("Youth", movieByMovieId.title);

        movieCursor.close();
    }

    public void testFindMovieByMovieId() {
        insertMovie(123, "Youth");
        insertMovie(124, "Spectre");

        MovieCursor movieCursor = new MovieCursor(getContentResolver().query(
                MovieProvider.Movie.MOVIES,
                null,
                "movieId = ?",
                new String[]{String.valueOf(123)},
                null));
        assertEquals(1, movieCursor.getCount());
        movieCursor.moveToFirst();
        assertEquals("Youth", movieCursor.getMovie().title);
        movieCursor.close();
    }

    public void testInsertMovieWithTrailers() {
        insertMovie(123, "Youth");

        Cursor cursor = getContentResolver().query(MovieProvider.Movie.MOVIES, null, null, null, null);
        MovieCursor movieCursor = new MovieCursor(cursor);
        movieCursor.moveToFirst();
        int id = movieCursor.getMovie()._id;

        ContentValues trailerValues1 = new ContentValues();
        trailerValues1.put(TrailerColumns.KEY, "abc123");
        trailerValues1.put(TrailerColumns.NAME, "Youth official trailer");
        trailerValues1.put(TrailerColumns.MOVIE, id);
        getContentResolver().insert(MovieProvider.Trailer.TRAILERS, trailerValues1);

        ContentValues trailerValues2 = new ContentValues();
        trailerValues2.put(TrailerColumns.KEY, "woitw83489");
        trailerValues2.put(TrailerColumns.NAME, "Interview with director");
        trailerValues2.put(TrailerColumns.MOVIE, id);
        getContentResolver().insert(MovieProvider.Trailer.TRAILERS, trailerValues2);

        TrailerCursor trailerCursor = new TrailerCursor(getContentResolver().query(MovieProvider.Trailer.findByMovie(id), null, null, null, null));
        assertEquals(2, trailerCursor.getCount());

        trailerCursor.moveToFirst();
        Trailer trailer1 = trailerCursor.getTrailer();
        assertEquals("Youth official trailer", trailer1.name);

        trailerCursor.moveToNext();
        Trailer trailer2 = trailerCursor.getTrailer();
        assertEquals("Interview with director", trailer2.name);
    }

    public void testInsertMovieWithReview() {
        insertMovie(123, "Youth");

        Cursor cursor = getContentResolver().query(MovieProvider.Movie.MOVIES, null, null, null, null);
        MovieCursor movieCursor = new MovieCursor(cursor);
        movieCursor.moveToFirst();
        int id = movieCursor.getMovie()._id;

        ContentValues reviewValues = new ContentValues();
        reviewValues.put(ReviewColumns.AUTHOR, "Rüdiger Suchsland");
        reviewValues.put(ReviewColumns.URL, "http://abc.de");
        reviewValues.put(TrailerColumns.MOVIE, id);
        getContentResolver().insert(MovieProvider.Review.REVIEWS, reviewValues);

        ReviewCursor reviewCursor = new ReviewCursor(getContentResolver().query(MovieProvider.Review.findByMovie(id), null, null, null, null));
        assertEquals(1, reviewCursor.getCount());

        reviewCursor.moveToFirst();
        Review review = reviewCursor.getReview();
        assertEquals("Rüdiger Suchsland", review.author);
        assertEquals("http://abc.de", review.url);
        assertEquals(id, review.movie);

        ReviewCursor allReviewsCursor = new ReviewCursor(getContentResolver().query(MovieProvider.Review.REVIEWS, null, null, null, null));
        assertEquals(1, allReviewsCursor.getCount());
    }

    private ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }

    private void insertMovie(int movieId, String title) {
        ContentValues values = new ContentValues();
        values.put(MovieColumns.MOVIE_ID, movieId);
        values.put(MovieColumns.TITLE, title);

        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(MovieProvider.Movie.MOVIES, values);
    }
}
