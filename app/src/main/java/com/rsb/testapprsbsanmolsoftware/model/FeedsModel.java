package com.rsb.testapprsbsanmolsoftware.model;

import com.google.gson.annotations.SerializedName;

//Created by Rahul S. Bhalerao on 20-07-2021
//Model class

public class FeedsModel {

    @SerializedName("id")
    public String id = "";

    @SerializedName("artistName")
    public String artistname = "";

    @SerializedName("name")
    public String songname = "";

    @SerializedName("imageurl")
    public String artistimage = "";

    @SerializedName("url")
    public String url = "";

    @SerializedName("genres")
    public String genres = "";

    @SerializedName("releaseDate")
    public String releaseDate = "";

    @SerializedName("copyright")
    public String copyright = "";

    public FeedsModel(String id, String artistname, String songname, String artistimage, String url, String genres, String releaseDate, String copyright) {
        this.id = id;
        this.artistname = artistname;
        this.songname = songname;
        this.artistimage = artistimage;
        this.url = url;
        this.genres = genres;
        this.releaseDate = releaseDate;
        this.copyright = copyright;
    }

    public FeedsModel() {
    }
}