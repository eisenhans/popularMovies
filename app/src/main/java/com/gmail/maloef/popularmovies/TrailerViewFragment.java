package com.gmail.maloef.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gmail.maloef.popularmovies.domain.Trailer;

import java.util.ArrayList;

/**
 * Created by Markus on 20.11.2015.
 */
public class TrailerViewFragment extends Fragment {
    private static final String LOG_TAG = TrailerViewFragment.class.getSimpleName();

    private TrailerAdapter trailerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        final ListView trailerView = (ListView) rootView.findViewById(R.id.trailerView);

        trailerAdapter = new TrailerAdapter(getActivity(), new ArrayList<Trailer>());
        trailerView.setAdapter(trailerAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateTrailers();
    }

    private void updateTrailers() {
        trailerAdapter.clear();

    }
}
