package com.example.google_places.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.google_places.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomListAdapter extends BaseAdapter {

    ArrayList<HashMap<String, String>> arrayList;
    AppCompatActivity appCompatActivity;
    LayoutInflater layoutInflater;

    public CustomListAdapter(ArrayList<HashMap<String, String>> arrayList,
                             AppCompatActivity appCompatActivity) {
        this.arrayList = arrayList;
        this.appCompatActivity = appCompatActivity;
        layoutInflater = (LayoutInflater) this.appCompatActivity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        v = layoutInflater.inflate(R.layout.listview_item, viewGroup, false);

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap = arrayList.get(i);

        //binding data JSON dengan view
        ((TextView)v.findViewById(R.id.txName)).setText(
                hashMap.get("name"));
        ((TextView)v.findViewById(R.id.txAddress)).setText(
                hashMap.get("address"));
        ((TextView)v.findViewById(R.id.txLat)).setText(
                hashMap.get("lat"));
        ((TextView)v.findViewById(R.id.txLong)).setText(
                hashMap.get("lng"));
        return v;
    }
}
