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
            Trailer trailer = new Trailer(source.readString(), source.readString());

            return trailer;
        }
        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String key;
    public String name;

    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return "Trailer{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
