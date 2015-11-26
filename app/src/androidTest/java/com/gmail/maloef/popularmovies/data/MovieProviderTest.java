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
import com.gmail.maloef.popularmovies.domain.Trailer;

public class MovieProviderTest extends AndroidTestCase {

    public void setUp() {
        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.delete(MovieProvider.Trailer.TRAILERS, null, null);

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
        ContentValues values = new ContentValues();
        values.put(MovieColumns.MOVIE_ID, 123);
        values.put(MovieColumns.TITLE, "Youth");

        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(MovieProvider.Movie.MOVIES, values);

        Cursor cursor = contentResolver.query(MovieProvider.Movie.MOVIES, null, null, null, null);
        MovieCursor movieCursor = new MovieCursor(cursor);
        assertEquals(1, movieCursor.getCount());
        assertTrue(movieCursor.moveToFirst());

        com.gmail.maloef.popularmovies.domain.Movie movie = movieCursor.getMovie();

        int id = cursor.getInt(cursor.getColumnIndex(MovieColumns._ID));

        int movieId = cursor.getInt(cursor.getColumnIndex(MovieColumns.MOVIE_ID));
        assertEquals(123, movieId);
        assertEquals(123, movie.movieId);

        String title = cursor.getString(cursor.getColumnIndex(MovieColumns.TITLE));
        assertEquals("Youth", title);
        assertEquals("Youth", movie.title);

        Cursor byIdCursor = contentResolver.query(MovieProvider.Movie.findById(id), null, null, null, null);
        MovieCursor movieByIdCursor = new MovieCursor(byIdCursor);
        assertEquals(1, movieByIdCursor.getCount());
        assertTrue(movieByIdCursor.moveToFirst());

        Movie movieById = movieCursor.getMovie();
        Log.i(getClass().getSimpleName(), "Movie from db: " + movieById);
        assertEquals(123, movieById.movieId);
        assertEquals("Youth", movieById.title);
    }

    public void testInsertMovieWithTrailer() {
        ContentValues values = new ContentValues();
        values.put(MovieColumns.MOVIE_ID, 123);
        values.put(MovieColumns.TITLE, "Youth");

        ContentResolver contentResolver = mContext.getContentResolver();
        contentResolver.insert(MovieProvider.Movie.MOVIES, values);

        Cursor cursor = contentResolver.query(MovieProvider.Movie.MOVIES, null, null, null, null);
        MovieCursor movieCursor = new MovieCursor(cursor);
        movieCursor.moveToFirst();
        int id = movieCursor.getMovie()._id;

        ContentValues trailerValues1 = new ContentValues();
        trailerValues1.put(TrailerColumns.KEY, "abc123");
        trailerValues1.put(TrailerColumns.NAME, "Youth official trailer");
        trailerValues1.put(TrailerColumns.MOVIE, id);
        contentResolver.insert(MovieProvider.Trailer.TRAILERS, trailerValues1);

        ContentValues trailerValues2 = new ContentValues();
        trailerValues2.put(TrailerColumns.KEY, "woitw83489");
        trailerValues2.put(TrailerColumns.NAME, "Interview with director");
        trailerValues2.put(TrailerColumns.MOVIE, id);
        contentResolver.insert(MovieProvider.Trailer.TRAILERS, trailerValues2);

        TrailerCursor trailerCursor = new TrailerCursor(contentResolver.query(MovieProvider.Trailer.findByMovie(id), null, null, null, null));
        assertEquals(2, trailerCursor.getCount());

        trailerCursor.moveToFirst();
        Trailer trailer1 = trailerCursor.getTrailer();
        assertEquals("Youth official trailer", trailer1.name);

        trailerCursor.moveToNext();
        Trailer trailer2 = trailerCursor.getTrailer();
        assertEquals("Interview with director", trailer2.name);
    }

}
