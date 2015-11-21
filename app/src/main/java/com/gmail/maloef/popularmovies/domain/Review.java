package com.gmail.maloef.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Markus on 19.11.2015.
 */
public class Review implements Parcelable {

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            Review review = new Review(source.readString(), source.readString());

            return review;
        }
        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String author;
    public String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }

    @Override
    public String toString() {
        return "Review{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
