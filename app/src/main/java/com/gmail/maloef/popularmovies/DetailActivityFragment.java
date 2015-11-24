package com.gmail.maloef.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.gmail.maloef.popularmovies.domain.MovieDetails;
import com.gmail.maloef.popularmovies.domain.Trailer;
import com.gmail.maloef.popularmovies.fetch.FetchMovieDetailsTask;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

/**
 * Created by Markus on 02.11.2015.
 */
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    private MovieDetails movieDetails;
    private LinearLayout detailLinearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra("movie")) {
            Movie movie = (Movie) intent.getParcelableExtra("movie");
            movieDetails = new MovieDetails(movie);

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

            detailLinearLayout = (LinearLayout) rootView.findViewById(R.id.detail_linear_layout);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        showMovieDetails();
    }

    private void showMovieDetails() {
        AsyncTask<Movie, Void, MovieDetails> asyncTask = new FetchMovieDetailsTask(movieDetails).execute(movieDetails.movie);
        Log.i(LOG_TAG, "task status: " + asyncTask.getStatus());
        try {
            movieDetails = asyncTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.i(LOG_TAG, "showing details for movie " + movieDetails.movie.title + " with " +
                movieDetails.trailers.size() + " trailers and " + movieDetails.reviews.size() + " reviews");

        for (int i = 0; i < movieDetails.trailers.size(); i++) {
            if (i == 0) {
                View sectionHeader = LayoutInflater.from(getContext()).inflate(R.layout.section_header, null);
                setText(sectionHeader, R.id.headerText, "Trailers:");

                detailLinearLayout.addView(sectionHeader);
            }
            final Trailer trailer = movieDetails.trailers.get(i);
            View trailerView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_trailer, null);
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

//        for (int i = 0; i < movieDetails.reviews.size(); i++) {
//            if (i == 0) {
//                View sectionHeader = LayoutInflater.from(getContext()).inflate(R.layout.section_header, null);
//                setText(sectionHeader, R.id.headerText, "Reviews:");
//
//                detailLinearLayout.addView(sectionHeader);
//            }
//            final Review review = movieDetails.reviews.get(i);
//            View reviewView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_review, null);
//            detailLinearLayout.addView(reviewView);
//            Log.i(LOG_TAG, "added review by author " + review.author + " to view " + reviewView);
//
//            setText(reviewView, R.id.list_item_review_author, review.author);
//        }
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

    private void setText(View rootView, int textViewId, String text) {
        TextView textView = (TextView) rootView.findViewById(textViewId);
        textView.setText(text);
    }
}
