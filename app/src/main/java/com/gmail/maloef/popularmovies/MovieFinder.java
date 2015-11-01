package com.gmail.maloef.popularmovies;

/**
 * Created by Markus on 01.11.2015.
 */
public class MovieFinder {

    private String apiKey = "removed";

    public void findByMostPopular() {
        String url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + apiKey;

    }

    public void findByHighestRated() {

    }
}
