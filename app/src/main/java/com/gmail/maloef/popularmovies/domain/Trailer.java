package com.gmail.maloef.popularmovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Markus on 18.11.2015.
 */
public class Trailer implements Parcelable {

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            Trailer trailer = new Trailer();
            trailer._id = source.readInt();
            trailer.key = source.readString();
            trailer.name = source.readString();
            trailer.movie = source.readInt();

            return trailer;
        }
        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public int _id;
    public String key;
    public String name;
    public int movie;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(key);
        dest.writeString(name);
        dest.writeInt(movie);
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "_id='" + _id + '\'' +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", movie='" + movie + '\'' +
                '}';
    }
}
