package com.gmail.maloef.popularmovies;

/**
 * Created by Markus on 01.11.2015.
 */
public class Movie {

    public Integer id;
    public String posterPath;
    public String title;
    public String originalTitle;
    public String overview;
    public String releaseDate;
    public String voteAverage;

    public String toString() {
        return "movie [id:" + id + ", title:" + title + ", overview:" + overview + ", releaseDate:" + releaseDate + ", voteAverage:" + voteAverage + ", posterPath:" + posterPath + "]";
    }
}
