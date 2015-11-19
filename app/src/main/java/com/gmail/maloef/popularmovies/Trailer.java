package com.gmail.maloef.popularmovies;

/**
 * Created by Markus on 18.11.2015.
 */
public class Trailer {

    public String key;
    public String name;

    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}
