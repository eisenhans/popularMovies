package com.gmail.maloef.popularmovies.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    List<Object> movies = new ArrayList<>();

    public MovieResponse() {

    }

    public static MovieResponse parseJson(String response) {
        Gson gson = new GsonBuilder().create();
        MovieResponse movieResponse = gson.fromJson(response, MovieResponse.class);

        return movieResponse;
    }
}
