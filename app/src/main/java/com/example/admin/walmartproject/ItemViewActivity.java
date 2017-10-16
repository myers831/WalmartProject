package com.example.admin.walmartproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.walmartproject.model.Search.Item;

import java.text.DecimalFormat;


public class ItemViewActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView ivSelectedItemImage;
    TextView tvSelectedItemName, tvSelectedItemPrice, tvSelectedItemModelNumber, tvSelectedItemDescription, tvSelectedItemShortDescription, tvSelectedItemCustomerRating, tvSelectedItemStock, tvSelectedItemAvalibleOnline;
    DecimalFormat df = new DecimalFormat("#.00");

    private static final String TAG = "itemViewActivity";
    Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        ivSelectedItemImage = findViewById(R.id.ivSelectedItemImage);
        tvSelectedItemName = findViewById(R.id.tvSelectedItemName);
        tvSelectedItemPrice = findViewById(R.id.tvSelectedItemPrice);
        tvSelectedItemModelNumber = findViewById(R.id.tvSelectedItemModelNumber);
        tvSelectedItemDescription = findViewById(R.id.tvSelectedItemDescription);
        tvSelectedItemCustomerRating = findViewById(R.id.tvSelectedItemCustomerRating);
        tvSelectedItemStock = findViewById(R.id.tvSelectedItemStock);
        tvSelectedItemAvalibleOnline = findViewById(R.id.tvSelectedItemAvalibleOnline);
        tvSelectedItemShortDescription = findViewById(R.id.tvSelectedItemShortDescription);

        item = (Item) getIntent().getSerializableExtra("item");


        Glide.with(this).load(item.getLargeImage()).into(ivSelectedItemImage);
        tvSelectedItemName.setText(item.getName());
        tvSelectedItemPrice.setText("$" + df.format(item.getSalePrice()));
        tvSelectedItemModelNumber.setText("Model Number: " + item.getModelNumber());
        tvSelectedItemStock.setText("In Stock: " + item.getStock());
        tvSelectedItemAvalibleOnline.setText("Online Availability: " + String.valueOf(item.getAvailableOnline()));
        tvSelectedItemCustomerRating.setText("Customer Rating: " + item.getCustomerRating());
        tvSelectedItemShortDescription.setText(item.getShortDescription());
        tvSelectedItemDescription.setText(item.getLongDescription());

        Log.d(TAG, "onCreate: " + item.getName());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
