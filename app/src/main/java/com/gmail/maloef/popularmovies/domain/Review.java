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
            Review review = new Review(source.readString(), source.readString(), source.readString());

            return review;
        }
        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String author;
    public String content;
    public String url;

    public Review(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public Spanned getHtmlLink() {
        return Html.fromHtml("<a href=\"" + url + "\">" + author + "</a>");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(url);
    }

    @Override
    public String toString() {
        return "Review{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
