package com.example.airquality.model;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DBModel implements IDBModel {

    @NonNull
    private final MyDBHelper myDBHelper;

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    public DBModel(Context context){
        myDBHelper = new MyDBHelper(context);

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,arrayList);
    }

    @NonNull
    private ArrayList<String> getArrayList(){
        return arrayList;
    }
    @NonNull
    @Override
    public ArrayAdapter<String> getArrayAdapter(){
        return arrayAdapter;
    }

    @Override
    public void addByJSON(String json){
        myDBHelper.addByJSON(json);
    }

    @NonNull
    @Override
    public ArrayAdapter<String> showAirInfo(){
        List<String> ls =  myDBHelper.queryAll();
        arrayList.clear();
        arrayList.addAll(ls);
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
