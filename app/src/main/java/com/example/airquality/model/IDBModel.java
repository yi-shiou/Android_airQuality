package com.example.airquality.model;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public interface IDBModel {

    ArrayList<String> getArrayList();
    ArrayAdapter<String> getArrayAdapter();

    void addByJSON(String json);
    ArrayAdapter<String> showAirInfo();
    String getTitle();
}
