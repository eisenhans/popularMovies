package com.gmail.maloef.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by Markus on 15.10.2015.
 */
public class MovieViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        final GridView movieView = (GridView) rootView.findViewById(R.id.movieview);

//        String[] values = new String[]{"anton", "berta", "cäsar", "dietrich", "emil"};
//        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        movieView.setAdapter(new PosterAdapter(getActivity()));

        return rootView;
    }
}
