package com.gmail.maloef.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.gmail.maloef.popularmovies.domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Markus on 15.10.2015.
 */
public class PosterAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = PosterAdapter.class.getName();

    public PosterAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(getContext());
            //imageView.setLayoutParams(new GridView.LayoutParams(400, 400));
//            imageView.setLayoutParams(new GridView.LayoutParams(600, 600));
            imageView.setAdjustViewBounds(true); // whole pic visible, looks good (with CENTER_CROP)
//            imageView.setAdjustViewBounds(false); // pic visible in whole length (very long), cropped width (narrow stripe) (with CENTER or CENTER_CROP)
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); // good together with adjustViewBounds = true
//            imageView.setScaleType(ImageView.ScaleType.CENTER); // pic visible in whole length (very long), cropped width (narrow stripe)
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // can look weird: lots of blank space
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER); // can look weird: lots of blank space
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        loadImageIntoView(position, imageView);
        return imageView;
    }

    private void loadImageIntoView(int position, ImageView imageView) {
        Movie movie = getItem(position);
        if (movie == null) {
            Log.w(LOG_TAG, "no movie found for position " + position);
            return;
        }
        Picasso.with(getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.dummy_poster) // shown while loading
                .error(R.drawable.dummy_poster)
                .into(imageView);
    }
}
