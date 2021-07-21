package com.rsb.testapprsbsanmolsoftware.network.api;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyClient {
    private final String BASE_URL = "https://rss.itunes.apple.com/api/v1/us/";
    private static MyClient myClient;
    private final Retrofit retrofit;

    private MyClient() {
        Log.v("API: ", "" + BASE_URL);
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized MyClient getInstance() {
        if (myClient == null) {
            myClient = new MyClient();
        }
        return myClient;
    }

    public Api getMyApi() {
        return retrofit.create(Api.class);
    }
}