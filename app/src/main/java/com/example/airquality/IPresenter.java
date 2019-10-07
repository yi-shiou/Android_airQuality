package com.example.airquality;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public interface IPresenter {
    ArrayList<String> getArrayList();
    ArrayAdapter<String> getArrayAdapter();
    //--- for database
    void refreshData();

    void onCreate();
    void onPause();
    void onResume();
    void onDestroy();
}
