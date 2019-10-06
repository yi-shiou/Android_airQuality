package com.example.airquality.model;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonAnalysis {
//    private String json;

    public static final String COLUMN_NAMES[] = {"SiteName", "County", "AQI", "Pollutant", "Status", "SO2", "CO", "CO_8hr", "O3", "O3_8hr", "PM10", "PM2.5", "NO2", "NOx", "NO", "WindSpeed", "WindDirec", "PublishTime", "PM2.5_AVG", "PM10_AVG", "SO2_AVG", "Longitude", "Latitude", "SiteId"};

    public JsonAnalysis(){
//        json = "";
    }

//    public void setJson(String json){
//        this.json = json;
//    }



    public String getFirstElement(String json){
        String s = "";
        try {
                s = new JSONArray(json).getJSONObject(0).getString(COLUMN_NAMES[0]);
        } catch (JSONException e) {
        }
        return s;
    }

    public List<ContentValues> analyze(String json){
//    public List<ContentValues> analyze(String json){
        try {
            JSONArray jsonArray = new JSONArray(json);

            List<ContentValues> lcv = new ArrayList<ContentValues>();
            for (int i = 0; i < jsonArray.length(); ++i){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int size = jsonObject.length();

                ContentValues cv = new ContentValues();
                for (int j = 0; j < size; ++j){
                    cv.put("`"+COLUMN_NAMES[j]+"`",jsonObject.getString(COLUMN_NAMES[j]));
                }
                lcv.add(cv);
            }
            return lcv;
        } catch (JSONException e) {
            return null;
        }

    }

}
