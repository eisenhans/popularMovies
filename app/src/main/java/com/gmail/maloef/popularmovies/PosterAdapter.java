package com.gmail.maloef.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Markus on 15.10.2015.
 */
public class PosterAdapter extends BaseAdapter {
    private static final String LOG_TAG = PosterAdapter.class.getName();

    private Context context;
    private List<Movie> movies;

    public PosterAdapter(Context context) {
        this.context = context;
    }
    
    @Override
    public int getCount() {
        return posterIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
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
//        Log.i(LOG_TAG, "Loading image " + position + ", movies: " + movies);
        for (int i = 0; i < 3; i++) {
            if (movies != null) {
                Log.i(LOG_TAG, "Found movies after waiting " + i + " sec");
                break;
            }
            Log.i(LOG_TAG, "Try " + i + "/3 for position " + position);
            try {
                Thread.currentThread().sleep(i * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (movies == null || movies.size() <= position) {
            Log.i(LOG_TAG, "no movie found for position " + position + ", using no image");
//            imageView.setImageResource(posterIds[position]);
        } else {
            Movie movie = movies.get(position);
            Log.i(LOG_TAG, "Real poster found at position " + position + ", movie is " + movie);
            String url = "http://image.tmdb.org/t/p/w185/" + movie.posterPath;
            Picasso.with(context).load(url).into(imageView);
            Log.i(LOG_TAG, "Finished loading image " + position);
        }

//        if (position == 1) {
//            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg").into(imageView);
//        } else {
//            imageView.setImageResource(posterIds[position]);
//        }
    }

    public void setMovies(List<Movie> movies) {
        Log.i(LOG_TAG, "Setting " + movies.size() + " movies");
        this.movies = movies;
    }

    private Integer[] posterIds = {
            R.drawable.pic01, R.drawable.pic02, R.drawable.pic03, R.drawable.pic04,
            R.drawable.pic05, R.drawable.pic06, R.drawable.pic07, R.drawable.pic08};
//            R.drawable.pic09, R.drawable.pic10, R.drawable.pic11, R.drawable.pic12};
}
