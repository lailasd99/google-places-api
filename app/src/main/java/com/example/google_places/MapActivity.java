package com.example.google_places;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.maps.GoogleMap;

public class MapActivity extends AppCompatActivity {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String lat = intent.getStringExtra("lat");
        String lng = intent.getStringExtra("lng");

        Toast.makeText(this, "place : " + lat, Toast.LENGTH_SHORT).show();
    }

}

