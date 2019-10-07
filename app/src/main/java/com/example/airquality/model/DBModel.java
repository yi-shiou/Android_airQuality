package com.example.airquality.model;

import android.content.Context;

import androidx.annotation.NonNull;

public class DBModel implements IDBModel {
    @NonNull
    private final MyDBHelper myDBHelper;
    public AirTable airTable = null;

    public DBModel(Context context){
        myDBHelper = new MyDBHelper(context);
    }
    @NonNull
    @Override
    public MyDBHelper getMyDBHelper() {
        return myDBHelper;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
