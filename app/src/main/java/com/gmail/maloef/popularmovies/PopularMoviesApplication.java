package com.gmail.maloef.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

public class PopularMoviesApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
    }
}
