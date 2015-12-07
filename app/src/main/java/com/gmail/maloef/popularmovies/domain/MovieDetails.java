package com.gmail.maloef.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 24.11.2015.
 */
public class MovieDetails implements Parcelable {
    private static final String LOG_TAG = MovieDetails.class.getSimpleName();

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            Movie movie = (Movie) source.readParcelable(getClass().getClassLoader());
            MovieDetails details = new MovieDetails(movie);

            source.readTypedList(details.trailers, Trailer.CREATOR);
            source.readTypedList(details.reviews, Review.CREATOR);

            return details;
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

    public final Movie movie;
    public List<Trailer> trailers = new ArrayList<>();
    public List<Review> reviews = new ArrayList<>();

    public MovieDetails(Movie movie) {
        this.movie = movie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(movie, flags);
        dest.writeTypedList(trailers);
        dest.writeTypedList(reviews);
    }
}
