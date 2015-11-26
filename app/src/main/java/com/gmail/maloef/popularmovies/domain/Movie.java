package com.gmail.maloef.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Markus on 01.11.2015.
 */
public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            Movie movie = new Movie();
            movie._id = source.readInt();
            movie.movieId = source.readInt();
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

    public int _id;
    public int movieId;
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
        if (posterPath == null || posterPath.equals("null")) {
            return null;
        }
        // TODO: move this to properties file
        return "http://image.tmdb.org/t/p/w185/" + posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(movieId);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(voteAverage);
        dest.writeString(posterPath);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "_id='" + _id + '\'' +
                ", movieId=" + movieId +
                ", title='" + title + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                "posterPath='" + posterPath + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
