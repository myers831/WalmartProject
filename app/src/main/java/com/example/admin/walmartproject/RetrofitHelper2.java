package com.example.admin.walmartproject;

import android.util.Log;

import com.example.admin.walmartproject.model.Search.Search;
import com.example.admin.walmartproject.model.VOD.WalmartSales;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 10/14/2017.
 */

public class RetrofitHelper2 {

    public static final String BASE_URL = "http://api.walmartlabs.com";

    static public Retrofit create(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    static public Observable<WalmartSales> getCall(){
        Retrofit retrofit = create();
        RequestService service = retrofit.create(RequestService.class);
        return service.responseService();
    }

    public interface RequestService{
        @GET("/v1/vod?format=json&apiKey=jy3vtxwdpxva9vs8jakbf2us")
        Observable<WalmartSales> responseService();

    }
}
