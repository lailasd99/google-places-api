package com.example.google_places;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.google_places.adapters.CustomListAdapter;
import com.example.google_places.databinding.ActivityMainBinding;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding bind;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    CustomListAdapter adapter;

    String url = "https://maps.googleapis.com/maps/api/place/textsearch/"+
            "json?key=AIzaSyDSZhe2DqZpqWGz5hJ4rfHqvwiXkG1DVo4&query=";
    String urlQuery="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //setContentView(R.layout.activity_main);

        adapter = new CustomListAdapter(arraylist, MainActivity.this);
        bind.lvPlaces.setAdapter(adapter);
        bind.btnSearch.setOnClickListener(btnClick);
        bind.btnClear.setOnClickListener(btnClick);

        //show selected location in map

       /* bind.lvPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getBaseContext(), "location : " + arraylist.get(i), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                intent.putExtra("name", arraylist.get(i).get("name"));
                intent.putExtra("address", arraylist.get(i).get("address"));
                intent.putExtra("lat", arraylist.get(i).get("lat"));
                intent.putExtra("lng", arraylist.get(i).get("lng"));
                startActivity(intent);
            }
        });*/


    }

    Response.Listener<JSONObject> jsonArrayListener = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            try {
                String name = "", address ="", lat ="", lng ="";
                arraylist.clear();
                JSONArray places = response.getJSONArray("results");
                for(int i=0; i<places.length();i++){
                    JSONObject info = places.getJSONObject(i);
                    name = info.getString("name");
                    //Log.i("MainActivity", "NAAMAEE OF PLAACEEE : "+info.getString(name));
                    address = info.getString("formatted_address");
                    JSONObject geometry = info.getJSONObject("geometry");
                    JSONObject location = geometry.getJSONObject("location");
                    lat = location.getString("lat");
                    lng = location.getString("lng");

                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("name", name);
                    hashMap.put("address", address);
                    hashMap.put("lat", lat);
                    hashMap.put("lng", lng);
                    arraylist.add(hashMap);
                }
                adapter.notifyDataSetChanged();
            }catch(JSONException e){
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "erreur survenu"+ " parsing JSON", Toast.LENGTH_LONG).show();
            }
        }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getBaseContext(), "ne peut pas connecter au serveur", Toast.LENGTH_LONG).show();
        }
    };

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnSearch:
                    try{
                        urlQuery = url + URLEncoder.encode(
                                bind.edSearch.getText().toString(), "UTF-8");
                    }catch(UnsupportedEncodingException e){
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(urlQuery, null, jsonArrayListener, errorListener);
                    Volley.newRequestQueue(MainActivity.this).add(jsonObjectRequest);
                    break;

                case R.id.btnClear:
                    bind.edSearch.setText("");
                    arraylist.clear();
                    adapter.notifyDataSetChanged();
            }
        }
    };
}