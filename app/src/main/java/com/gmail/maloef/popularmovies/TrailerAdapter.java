package com.gmail.maloef.popularmovies;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.gmail.maloef.popularmovies.domain.Trailer;

import java.util.List;

/**
 * Created by Markus on 20.11.2015.
 */
public class TrailerAdapter extends ArrayAdapter<Trailer> {

    public TrailerAdapter(Context context, List<Trailer> trailers) {
        super(context, 0, trailers);
    }
}
