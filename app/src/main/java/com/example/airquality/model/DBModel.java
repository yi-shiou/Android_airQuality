package com.example.airquality.model;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DBModel implements IDBModel {

    @NonNull
    private final MyDBHelper myDBHelper;

    private ArrayList<AirInfo> airInfos;
    private MyListAdapter adapter;

    public DBModel(Context context){
        myDBHelper = new MyDBHelper(context);

        airInfos = new ArrayList<>();
        adapter = new MyListAdapter(context,airInfos);
    }

    @NonNull
    private ArrayList<AirInfo> getArrayList(){
        return airInfos;
    }
    @NonNull
    @Override
    public MyListAdapter getArrayAdapter(){
        return adapter;
    }

    @Override
    public void addByJSON(String json){
        myDBHelper.addByJSON(json);
    }

    @NonNull
    @Override
    public MyListAdapter showAirInfo(){
        List<String> ls =  myDBHelper.queryAll();
        airInfos.clear();
        for (String s:ls) {
            airInfos.add(new AirInfo(s));
        }
        return getArrayAdapter();
    }

    @Override
    public void deleteListViewElement(int position){
        getArrayList().remove(position);
        getArrayAdapter().notifyDataSetChanged();
    }

    @Override
    public String getTitle() {
        return myDBHelper.getTitle();
    }
}
