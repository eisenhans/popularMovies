package com.gmail.maloef.popularmovies;

import android.content.Intent;
import android.net.Uri;
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
import com.gmail.maloef.popularmovies.domain.Trailer;
import com.squareup.picasso.Picasso;

/**
 * Created by Markus on 02.11.2015.
 */
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = DetailActivityFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra("movie")) {
            Movie movie = (Movie) intent.getParcelableExtra("movie");

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

            LinearLayout detailLinearLayout = (LinearLayout) rootView.findViewById(R.id.detail_linear_layout);
            for (int i = 0; i < movie.getTrailers().size(); i++) {
                if (i == 0) {
                    View sectionHeader = LayoutInflater.from(getContext()).inflate(R.layout.section_header, null);
                    setText(sectionHeader, R.id.headerText, "Trailers:");

                    detailLinearLayout.addView(sectionHeader);
                }
                final Trailer trailer = movie.getTrailers().get(i);
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
        }
        return rootView;
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
