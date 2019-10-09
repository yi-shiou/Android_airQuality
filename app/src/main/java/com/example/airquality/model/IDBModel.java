package com.example.airquality.model;

public interface IDBModel {
    MyListAdapter getArrayAdapter();

    void addByJSON(String json);
    MyListAdapter showAirInfo();
    void deleteListViewElement(int position);
    String getTitle();
}
