package com.example.admin.walmartproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.walmartproject.model.VOD.WalmartSales;

import java.text.DecimalFormat;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    EditText etSendQuery;
    TextView tvVODName, tvVODPrice;
    ImageView ivVODImage;
    DecimalFormat df = new DecimalFormat("#.00");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSendQuery = findViewById(R.id.etSendQuery);
        tvVODName = findViewById(R.id.tvVODName);
        tvVODPrice = findViewById(R.id.tvVODPrice);
        ivVODImage = findViewById(R.id.ivVODImage);

        RetrofitHelper2.getCall()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<WalmartSales>() {
                    public static final String TAG = "mainActivity";

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(@NonNull WalmartSales walmartSales) throws IllegalStateException {
                        tvVODName.setText(walmartSales.getName());
                        tvVODPrice.setText("$" + String.valueOf(df.format(walmartSales.getSalePrice())));
                        Glide.with(MainActivity.this).load(walmartSales.getMediumImage()).into(ivVODImage);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.toString() + " " +Thread.currentThread());
                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: ");
                    }
                });

    }

    public void sendQuery(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("query", etSendQuery.getText().toString());
        startActivity(intent);
    }
}
