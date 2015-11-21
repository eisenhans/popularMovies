package com.gmail.maloef.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Markus on 01.11.2015.
 */
public class Movie implements Parcelable {

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
            source.readTypedList(movie.trailers, Trailer.CREATOR);
            source.readTypedList(movie.reviews, Review.CREATOR);

            return movie;
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Integer id;
    public String title;
    public String originalTitle;
    public String overview;
    public String releaseDate;
    public String voteAverage;
    public String posterPath;

    private List<Trailer> trailers;
    private List<Review> reviews;

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

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

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
        dest.writeTypedList(trailers);
        dest.writeTypedList(reviews);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "posterPath='" + posterPath + '\'' +
                ", voteAverage='" + voteAverage + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", overview='" + overview + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
