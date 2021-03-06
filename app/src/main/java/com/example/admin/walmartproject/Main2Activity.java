package com.example.admin.walmartproject;

import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.admin.walmartproject.model.Search.Item;
import com.example.admin.walmartproject.model.Search.Search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Main2Activity extends AppCompatActivity {

    RecyclerView rvItems;
    RecycleViewAdapter  recycleViewAdapter;
    RecyclerView.LayoutManager layoutManager;
    int pageCall = 1;
    EndlessRecyclerViewScrollListener scrollListener;


    private static final String TAG = "main2Activity";
    Map<String, String> query = new ArrayMap<>();
    List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rvItems = findViewById(R.id.rvItems);
        layoutManager = new LinearLayoutManager(this);

        scrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //loadNextDataFromApi(page);
                pageCall += 25;
                Log.d(TAG, "onLoadMore: check pageCall = " + String.valueOf(pageCall));
                makeCall(pageCall);
            }
        };

        //"/v1/search?query=" + getIntent().getStringExtra("query") + "&format=json&apiKey=jy3vtxwdpxva9vs8jakbf2us";

        rvItems.addOnScrollListener(scrollListener);

        makeCall(pageCall);
    }

    public void makeCall(int pageCallUpdate){

        query.put("query", getIntent().getStringExtra("query"));
        query.put("format", "json");
        query.put("apiKey", "jy3vtxwdpxva9vs8jakbf2us");
        query.put("numItems", "25");
        query.put("start", String.valueOf(pageCallUpdate));

        RetrofitHelper.getCall(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Search>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(@NonNull Search search) {

                        for(Item i: search.getItems()){
                            itemList.add(i);
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        recycleViewAdapter = new RecycleViewAdapter(itemList);
                        rvItems.setLayoutManager(layoutManager);
                        rvItems.setAdapter(recycleViewAdapter);
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

}
