package com.gmail.maloef.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;

/**
 * Created by Markus on 19.11.2015.
 */
public class Review implements Parcelable {

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel source) {
            Review review = new Review();
            review._id = source.readInt();
            review.author = source.readString();
            review.url = source.readString();
            review.movie = source.readInt();

            return review;
        }
        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int _id;
    public String author;
    public String url;
    public int movie;

    public Spanned getHtmlLink() {
        return Html.fromHtml("<a href=\"" + url + "\">" + author + "</a>");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeInt(movie);
    }

    @Override
    public String toString() {
        return "Review{" +
                "_id='" + _id + '\'' +
                "author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", movie='" + movie + '\'' +
                '}';
    }
}
