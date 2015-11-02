package com.gmail.maloef.popularmovies;

/**
 * Created by Markus on 01.11.2015.
 */
public class Movie {

    public Integer id;
    public String posterPath;
    public String title;

    public String toString() {
        return "movie [id:" + id + ", title:" + title +", posterPath:" + posterPath + "]";
    }
}
