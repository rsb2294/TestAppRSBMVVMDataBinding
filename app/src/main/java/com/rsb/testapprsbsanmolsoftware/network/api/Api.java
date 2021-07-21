package com.rsb.testapprsbsanmolsoftware.network.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {
    @GET("apple-music/{feedType}/all/50/explicit.json")
    Call<JsonObject> getartistdata(@Path("feedType") String feedtype);
}