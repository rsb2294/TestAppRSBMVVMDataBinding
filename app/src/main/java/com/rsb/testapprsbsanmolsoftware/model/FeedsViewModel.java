package com.rsb.testapprsbsanmolsoftware.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rsb.testapprsbsanmolsoftware.network.api.Api;
import com.rsb.testapprsbsanmolsoftware.network.api.MyClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Created by Rahul S. Bhalerao on 20-07-2021

public class FeedsViewModel extends ViewModel implements Serializable {
    public String id = "";
    public String artistname = "Unknown";
    public String songname = "Unknown";
    public String artistimage = "Unknown";
    public String releaseDate = "Unknown";
    public String genres = "Unknown";
    public String copyRights = "Unknown";
    public String url = "Unknown";
    public MutableLiveData<ArrayList<FeedsViewModel>> mutableLiveData = new MutableLiveData<>();
    private ArrayList<FeedsViewModel> arrayList;
    private ArrayList<FeedsModel> myList;


    public String getImageurl() {
        return artistimage;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadimage(ImageView imageView, String imageUrl) {
        LoadImage loadImage = new LoadImage(imageView);
        loadImage.execute(imageUrl);
    }


    public FeedsViewModel() {

    }

    //explicit constructor to check data fields for empty and set to viewModel fields
    public FeedsViewModel(FeedsModel feedsModel) {
        if (!feedsModel.id.isEmpty()) {
            this.id = feedsModel.id;
        }
        if (!feedsModel.artistname.isEmpty()) {
            this.artistname = feedsModel.artistname;
        }
        if (!feedsModel.songname.isEmpty()) {
            this.songname = feedsModel.songname;
        }
        if (!feedsModel.artistimage.isEmpty()) {
            this.artistimage = feedsModel.artistimage;
        }
        if (!feedsModel.url.isEmpty()) {
            this.url = feedsModel.url;
        }
        if (!feedsModel.genres.isEmpty()) {
            this.genres = feedsModel.genres;
        }
        if (!feedsModel.releaseDate.isEmpty()) {
            this.releaseDate = feedsModel.releaseDate;
        }
        if (!feedsModel.copyright.isEmpty()) {
            this.copyRights = feedsModel.copyright;
        }
    }

    //Created by Rahul S. Bhalerao on 20-07-2021
    //getting MutableLiveData from server
    public MutableLiveData<ArrayList<FeedsViewModel>> getMutableLiveData(String feed) {

        arrayList = new ArrayList<>();
        arrayList.clear();
        Api api = MyClient.getInstance().getMyApi();
        Call<JsonObject> call = api.getartistdata(feed);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                myList = new ArrayList<>();

                JsonObject jsonObject = response.body().getAsJsonObject("feed");
                JsonArray jsonArray = jsonObject.get("results").getAsJsonArray();


                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject object = jsonArray.get(i).getAsJsonObject();

                    if (object.has("id"))
                        id = object.get("id").toString();

                    if (object.has("artistName"))
                        artistname = object.get("artistName").toString();
                    else
                        artistname = "Unknown";

                    if (object.has("name"))
                        songname = object.get("name").toString();

                    if (object.has("artworkUrl100"))
                        artistimage = object.get("artworkUrl100").toString();

                    if (object.has("url"))
                        url = object.get("url").toString();

                    if (object.has("genres")) {
                        JsonArray jsonArray1 = object.get("genres").getAsJsonArray();
                        String genresArr = "";
                        for (int j = 0; j < jsonArray1.size(); j++) {
                            JsonObject jsonObject1 = jsonArray1.get(j).getAsJsonObject();
                            genresArr = jsonObject1.get("name") + " " + genresArr;
                        }
                        genres = genresArr.replaceAll(" ", ", ");
                    }

                    if (object.has("releaseDate"))
                        releaseDate = object.get("releaseDate").toString();

                    if (object.has("copyright"))
                        copyRights = object.get("copyright").toString();

//                    Log.v("Data", "id: " + id + " artistname: " + artistname
//                            + " songname: " + songname + " artistimage: "
//                            + artistimage + " genres: " + genres + " url:"
//                            + url + " releaseDate: " + releaseDate + " copyRights: " + copyRights);
                    if (artistname.equals("Unknown")) {
                        artistname = songname;
                    }
                    FeedsModel myk = new FeedsModel(id.substring(1, id.length() - 1),
                            artistname.substring(1, artistname.length() - 1),
                            songname.substring(1, songname.length() - 1),
                            artistimage, url, genres.replace("\"", ""), releaseDate.substring(1, releaseDate.length() - 1),
                            copyRights.substring(1, copyRights.length() - 1));

                    FeedsViewModel myListViewModel = new FeedsViewModel(myk);
                    arrayList.add(myListViewModel);
                    mutableLiveData.setValue(arrayList);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.v("Failuare", t.getMessage() + " ");
            }
        });

        return mutableLiveData;
    }

    //Created by Rahul S. Bhalerao on 21-07-2021
    //Image loading async through this class
    public static class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public LoadImage(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            String url = strings[0];
            try {
                url = url.substring(1, url.length() - 1);
                Log.v("ImageStr: ", url);
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}