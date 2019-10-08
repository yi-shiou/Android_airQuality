package com.example.airquality.model;

import android.widget.ArrayAdapter;

public interface IDBModel {
    ArrayAdapter<String> getArrayAdapter();

    void addByJSON(String json);
    ArrayAdapter<String> showAirInfo();
    void deleteListViewElement(int position);
    String getTitle();
}
