package com.gmail.maloef.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Markus on 01.11.2015.
 */
public class Movie implements Parcelable {

    public Integer id;
    public String title;
    public String originalTitle;
    public String overview;
    public String releaseDate;
    public String voteAverage;
    public String posterPath;

    public Integer getReleaseYear() {
        if (releaseDate == null || releaseDate.length() == 0) {
            return null;
        }
        String[] yearMonthDay = releaseDate.split("-");
        return Integer.valueOf(yearMonthDay[0]);
    }

    public String getPosterUrl() {
        return "http://image.tmdb.org/t/p/w185/" + posterPath;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            Movie movie = new Movie();
            movie.id = source.readInt();
            movie.title = source.readString();
            movie.originalTitle = source.readString();
            movie.overview = source.readString();
            movie.releaseDate = source.readString();
            movie.voteAverage = source.readString();
            movie.posterPath = source.readString();

            return movie;
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(posterPath);
    }

    public String toString() {
        return "movie [id:" + id + ", title:" + title + ", overview:" + overview + ", releaseDate:" + releaseDate + ", voteAverage:" + voteAverage + ", posterPath:" + posterPath + "]";
    }
}
