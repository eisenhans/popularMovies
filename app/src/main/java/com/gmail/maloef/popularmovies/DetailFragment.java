package com.gmail.maloef.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.maloef.popularmovies.data.ContentValuesUtil;
import com.gmail.maloef.popularmovies.data.MovieColumns;
import com.gmail.maloef.popularmovies.data.MovieProvider;
import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.MovieDetails;
import com.gmail.maloef.popularmovies.domain.Review;
import com.gmail.maloef.popularmovies.domain.Trailer;
import com.gmail.maloef.popularmovies.fetch.MovieDetailsLoader;
import com.squareup.picasso.Picasso;

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<MovieDetails> {
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    private Movie movie;
    private MovieDetails movieDetails;
    private LinearLayout detailLinearLayout;

    private ShareActionProvider shareActionProvider;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        detailLinearLayout = (LinearLayout) rootView.findViewById(R.id.detail_linear_layout);

        Bundle arguments = getArguments();
        if (arguments != null) {
            movie = arguments.getParcelable("movie");

            ImageView imageView = (ImageView) rootView.findViewById(R.id.movie_poster_path);
            if (movie.getPosterUrl() == null) {
                imageView.setImageResource(R.drawable.dummy_poster);
            } else {
                Picasso.with(getContext()).load(movie.getPosterUrl()).into(imageView);
            }

            setText(rootView, R.id.movie_title, movie.title);
            setText(rootView, R.id.movie_release_year, "" + movie.getReleaseYear());
            setText(rootView, R.id.movie_original_title, movie.originalTitle);
            setText(rootView, R.id.movie_vote_average, movie.voteAverage + "/10");
            setText(rootView, R.id.movie_overview, movie.overview);

            final Button favoriteButton = (Button) rootView.findViewById(R.id.favorite_button);
            favoriteButton.setText(isFavorite(movie) ? "Remove from favorites" : "Add to favorites");

            favoriteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (isFavorite(movie)) {
                        removeFromFavorites(movie);
                        favoriteButton.setText("Add to favorites");
                        Toast.makeText(getContext(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                        Log.i(LOG_TAG, "removed " + movie.title + " from favorites");
                    } else {
                        addToFavorites(movie);
                        favoriteButton.setText("Remove from favorites");
                        Toast.makeText(getContext(), "Added to favorites", Toast.LENGTH_SHORT).show();
                        Log.i(LOG_TAG, "added " + movie.title + " to favorites");
                    }
                }
            });
        }
        return rootView;
    }

    private boolean isFavorite(Movie movie) {
        Cursor cursor = getContentResolver().query(
                MovieProvider.Movie.MOVIES,
                new String[]{"_id"},
                "movieId = ?",
                new String[]{String.valueOf(movie.movieId)},
                null);

        boolean favorite = cursor.moveToFirst();
        cursor.close();
        return favorite;
    }

    private void addToFavorites(Movie movie) {
        ContentValues contentValues = ContentValuesUtil.fromMovie(movie);
        Uri movieUri = getContentResolver().insert(MovieProvider.Movie.MOVIES, contentValues);
        Log.i(LOG_TAG, "inserted favorite movie " + movie.title + " (_id " + movie._id + "), uri: " + movieUri);

        int _id = Integer.valueOf(movieUri.getLastPathSegment());
        updateForeignKeys(_id);

        ContentValues[] trailerValues = ContentValuesUtil.fromTrailers(movieDetails.trailers);
        int insertedTrailers = getContentResolver().bulkInsert(MovieProvider.Trailer.TRAILERS, trailerValues);
        if (insertedTrailers != movieDetails.trailers.size()) {
            Log.w(LOG_TAG, "only " + insertedTrailers + " out of " + movieDetails.trailers.size() + " trailers were inserted");
        } else {
            Log.i(LOG_TAG, "inserted " + insertedTrailers + " trailers");
        }

        ContentValues[] reviewValues = ContentValuesUtil.fromReviews(movieDetails.reviews);
        int insertedReviews = getContentResolver().bulkInsert(MovieProvider.Review.REVIEWS, reviewValues);
        if (insertedReviews != movieDetails.reviews.size()) {
            Log.w(LOG_TAG, "only " + insertedReviews + " out of " + movieDetails.reviews.size() + " reviews were inserted");
        } else {
            Log.i(LOG_TAG, "inserted " + insertedReviews + " reviews");
        }
    }

    private void updateForeignKeys(int _id) {
        for (Trailer trailer : movieDetails.trailers) {
            trailer.movie = _id;
        }
        for (Review review : movieDetails.reviews) {
            review.movie = _id;
        }
    }

    private void removeFromFavorites(Movie movie) {
        Cursor cursor = getContentResolver().query(
                MovieProvider.Movie.MOVIES,
                new String[]{"_id"},
                "movieId = ?",
                new String[]{String.valueOf(movie.movieId)},
                null);

        if (cursor.moveToFirst()) {
            int _id = cursor.getInt(cursor.getColumnIndex(MovieColumns._ID));
            String _idString = String.valueOf(_id);

            getContentResolver().delete(MovieProvider.Trailer.TRAILERS, "movie = ?", new String[]{_idString});
            getContentResolver().delete(MovieProvider.Review.REVIEWS, "movie = ?", new String[]{_idString});
            getContentResolver().delete(MovieProvider.Movie.MOVIES, "_id = ?", new String[]{_idString});
        }
        cursor.close();
    }

    private ContentResolver getContentResolver() {
        return getContext().getContentResolver();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (movie == null) {
            Log.i(LOG_TAG, "no movie selected yet in twoPaneLayout");
            return;
        }
        if (movieDetails != null) {
            Log.i(LOG_TAG, "movieDetails for movie " + movie.title + " have already been initialized - won't do it again");
            return;
        }
        Loader<MovieDetails> loader = getLoaderManager().initLoader(0, null, this);
        Log.i(LOG_TAG, "created loader for movie " + movie.title + ": " + loader.getId());
    }

    @Override
    public Loader<MovieDetails> onCreateLoader(int id, Bundle args) {
        return new MovieDetailsLoader(getContext(), movie);
    }

    @Override
    public void onLoadFinished(Loader<MovieDetails> loader, MovieDetails  movieDetails) {
        Log.i(LOG_TAG, "showing details for movie " + movieDetails.movie.title + " with " +
                movieDetails.trailers.size() + " trailers and " + movieDetails.reviews.size() + " reviews");

        this.movieDetails = movieDetails;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < movieDetails.trailers.size(); i++) {
            if (i == 0) {
                View sectionHeader = inflater.inflate(R.layout.section_header, null);
                setText(sectionHeader, R.id.headerText, "Trailers:");

                detailLinearLayout.addView(sectionHeader);
            }
            final Trailer trailer = movieDetails.trailers.get(i);
            View trailerView = inflater.inflate(R.layout.list_item_trailer, null);
            detailLinearLayout.addView(trailerView);
            Log.i(LOG_TAG, "added trailer " + trailer.name + " to view " + trailerView);

            setText(trailerView, R.id.list_item_trailer_name, trailer.name);

            final ImageButton playButton = (ImageButton) trailerView.findViewById(R.id.list_item_trailer_button);

            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri trailerUri = buildYoutubeUri(trailer.key);
                    Intent playTrailerIntent = new Intent(Intent.ACTION_VIEW, trailerUri);
                    if (playTrailerIntent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivity(playTrailerIntent);
                    } else {
                        Log.i(LOG_TAG, "cannot start trailer - no receiving apps found");
                    }
                }
            });
        }

        for (int i = 0; i < movieDetails.reviews.size(); i++) {
            if (i == 0) {
                View sectionHeader = inflater.inflate(R.layout.section_header, null);
                setText(sectionHeader, R.id.headerText, "Reviews:");

                detailLinearLayout.addView(sectionHeader);
            }
            final Review review = movieDetails.reviews.get(i);
            View reviewView = inflater.inflate(R.layout.list_item_review, null);
            detailLinearLayout.addView(reviewView);
            Log.i(LOG_TAG, "added review by author " + review.author + " to view " + reviewView);

            CharSequence linkText = Html.fromHtml("<a href=\"" + review.url  + "\">" + review.author + "</a>");
            Log.i(LOG_TAG, "created review link: " + linkText);

            TextView urlTextView = (TextView) reviewView.findViewById( R.id.list_item_review_author);
            urlTextView.setText(linkText);
            urlTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        updateShareIntent();
    }

    @Override
    public void onLoaderReset(Loader<MovieDetails> loader) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detailfragment, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share_first_trailer);

        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        updateShareIntent();
    }

    private void updateShareIntent() {
        // If the ShareActionProvider has been created in onCreateOptionsMenu() and the movieDetails have
        // been initialized in onLoadFinished(), we can set the shareIntent.
        if (shareActionProvider != null && movieDetails != null && !movieDetails.trailers.isEmpty()) {
            String firstTrailerKey = movieDetails.trailers.get(0).key;
            shareActionProvider.setShareIntent(createShareFirstTrailerIntent(firstTrailerKey));
        }
    }

    private Intent createShareFirstTrailerIntent(String firstTrailerKey) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");

        String uriString = buildYoutubeUri(firstTrailerKey).toString();
        shareIntent.putExtra(Intent.EXTRA_TEXT, uriString);

        return shareIntent;
    }

    private Uri buildYoutubeUri(String trailerKey) {
        //https://www.youtube.com/watch?v=BOVriTeIypQ&feature=youtu.be
        Uri uri = new Uri.Builder()
                .scheme("https")
                .authority("www.youtube.com")
                .appendPath("watch")
                .appendQueryParameter("v", trailerKey).build();

        Log.i(LOG_TAG, "built uri " + uri);
        return uri;
    }

    private void setText(View rootView, int textViewId, CharSequence text) {
        TextView textView = (TextView) rootView.findViewById(textViewId);
        textView.setText(text);
    }
}
