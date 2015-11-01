package com.gmail.maloef.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Markus on 15.10.2015.
 */
public class PosterAdapter extends BaseAdapter {

    private Context context;

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

        imageView.setImageResource(posterIds[position]);
        return imageView;
    }

    private Integer[] posterIds = {
            R.drawable.pic01, R.drawable.pic02, R.drawable.pic03, R.drawable.pic04,
            R.drawable.pic05, R.drawable.pic06, R.drawable.pic07, R.drawable.pic08};
//            R.drawable.pic09, R.drawable.pic10, R.drawable.pic11, R.drawable.pic12};
}
