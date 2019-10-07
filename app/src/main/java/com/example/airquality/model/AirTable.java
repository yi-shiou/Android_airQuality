package com.example.airquality.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AirTable {
    public static String TABLE_NAME = "item";
    public static final String KEY_ID = "_id";

    public static final String COLUMN_NAMES[] = {"SiteName", "County", "AQI", "Pollutant", "Status", "SO2", "CO", "CO_8hr", "O3", "O3_8hr", "PM10", "PM2.5", "NO2", "NOx", "NO", "WindSpeed", "WindDirec", "PublishTime", "PM2.5_AVG", "PM10_AVG", "SO2_AVG", "Longitude", "Latitude", "SiteId"};
    public static int COLUMN_NUM = 0;
    public static String CREATE_TABLE;

    public AirTable(String TABLE_NAME){
        COLUMN_NUM = COLUMN_NAMES.length;
//        CREATE_TABLE = "CREATE TABLE item (_id INTEGER PRIMARY KEY AUTOINCREMENT , `SiteName` TEXT , `County` TEXT , `AQI` TEXT , `Pollutant` TEXT , `Status` TEXT , `SO2` TEXT , `CO` TEXT , `CO_8hr` TEXT , `O3` TEXT , `O3_8hr` TEXT , `PM10` TEXT , `PM2.5` TEXT , `NO2` TEXT , `NOx` TEXT , `NO` TEXT , `WindSpeed` TEXT , `WindDirec` TEXT , `PublishTime` TEXT , `PM2.5_AVG` TEXT , `PM10_AVG` TEXT , `SO2_AVG` TEXT , `Longitude` TEXT , `Latitude` TEXT , `SiteId` TEXT)";
//        CREATE_TABLE = "CREATE TABLE item (`SiteName` TEXT PRIMARY KEY, `County` TEXT , `AQI` TEXT , `Pollutant` TEXT , `Status` TEXT , `SO2` TEXT , `CO` TEXT , `CO_8hr` TEXT , `O3` TEXT , `O3_8hr` TEXT , `PM10` TEXT , `PM2.5` TEXT , `NO2` TEXT , `NOx` TEXT , `NO` TEXT , `WindSpeed` TEXT , `WindDirec` TEXT , `PublishTime` TEXT , `PM2.5_AVG` TEXT , `PM10_AVG` TEXT , `SO2_AVG` TEXT , `Longitude` TEXT , `Latitude` TEXT , `SiteId` TEXT)";
        CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT";
        for (String s: COLUMN_NAMES) {
            CREATE_TABLE += " , `" + s + "` TEXT";
        }
        CREATE_TABLE += ")";
    }

    public List<String> printData(Cursor cursor){
        List<String> ls = new ArrayList<String>();

        while (cursor.moveToNext()) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < COLUMN_NUM; i++) {
                output.append("  ").append(cursor.getString(i));
            }
            ls.add(output.toString());
        }
        return ls;
    }
    public String getTitle(){
        String tital = "測站名稱  縣市  空氣品質指標  空氣污染指標物  狀態  SO2(ppb)  CO(ppm)  " +
                "CO_8hr  臭氧(ppb)  臭氧_8hr  PM10(μg/m3)  PM2.5(μg/m3) NO2(ppb) NOx(ppb) NO(ppb)  " +
                "風速(m/sec)  風向(degrees)  資料建置日期  細懸浮微粒移動平均  懸浮微粒移動平均值  二氧化硫移動平均值  經度  緯度  測站編號";
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < COLUMN_NUM; i++) {
            output.append("  ").append(COLUMN_NAMES[i]);
        }
        return output.toString();
    }

    public void addData(List<ContentValues> lcv,SQLiteDatabase db){
        for (int i = 0; i < lcv.size(); ++i)
        {
            db.insert(TABLE_NAME, null, lcv.get(i));
        }
    }
    public void updteDate(List<ContentValues> lcv,SQLiteDatabase db){
        for (int i = 0; i < lcv.size(); ++i)
        {
            db.update(TABLE_NAME, lcv.get(i),"`SiteName` = "+lcv.get(i).getAsString("SiteName"),null);
//            db.replace(TABLE_NAME,null,lcv.get(i));
        }
    }
    public boolean delete(int id,SQLiteDatabase db){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME,where,null) > 0 ;
    }
}
