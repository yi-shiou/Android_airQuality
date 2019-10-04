package com.example.airquality.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AirTable {
    public static String TABLE_NAME = "item";
    public static final String KEY_ID = "_id";

    public static final String COLUMN_NAMES[] = {"SiteName", "County", "AQI", "Pollutant", "Status", "SO2", "CO", "CO_8hr", "O3", "O3_8hr", "PM10", "PM2.5", "NO2", "NOx", "NO", "WindSpeed", "WindDirec", "PublishTime", "PM2.5_AVG", "PM10_AVG", "SO2_AVG", "Longitude", "Latitude", "SiteId"};
    public static int COLUMN_NUM = 0;
    public static String CREATE_TABLE;


    private SQLiteDatabase db;

    public AirTable(String TABLE_NAME,SQLiteDatabase db){
        this.db = db;

        COLUMN_NUM = COLUMN_NAMES.length;
        CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT";
        for (String s: COLUMN_NAMES) {
            CREATE_TABLE += "TEXT , " + s ;
        }
        CREATE_TABLE += ")";
    }

    public String printData(Cursor cursor){
        String output = "";
        for (int i = 0; i < COLUMN_NUM; i++) {
            output += "  " + cursor.getString(i);
        }
        return output;
    }


    public void addByJSON(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); ++i){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                List<String> ls = new ArrayList<String>();
                for (String col_name : COLUMN_NAMES){
                    ls.add(jsonObject.getString(col_name));
                }
                addData(ls);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void addData(List<String> ls){
        ContentValues cv = new ContentValues();
        for (int i = 0; i < ls.size(); ++i){
            cv.put(COLUMN_NAMES[i], ls.get(i));
        }
        db.insert(TABLE_NAME, null, cv);
    }

    public boolean delete(int id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME,where,null) > 0 ;
    }
}
