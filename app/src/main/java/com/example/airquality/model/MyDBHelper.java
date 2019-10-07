package com.example.airquality.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "airQuality.db";
    public static final String TABLE_NAME = "item";
    public static final int VERSION = 1;

    private static MyDBHelper myDBHelper = null;
    public AirTable airTable = null;
    private Context context;
    private JsonAnalysis jsonAnalysis;

//    private static SQLiteDatabase database = null;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

        airTable = new AirTable(TABLE_NAME);
        this.context = context;
        this.getWritableDatabase().close();
        jsonAnalysis = new JsonAnalysis();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(airTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + airTable.TABLE_NAME);
        onCreate(db);
    }
    public String getTitle(){
        String tital = "測站名稱  縣市  空氣品質指標  空氣污染指標物  狀態  二氧化硫(ppb)  一氧化碳(ppm)  " +
                "一氧化碳8小時平均  臭氧(ppb)  臭氧8小時平均  懸浮微粒(μg/m3)        一氧化氮(ppb)  " +
                "風速(m/sec)  風向(degrees)  資料建置日期  細懸浮微粒移動平均  懸浮微粒移動平均值  二氧化硫移動平均值  經度  緯度  測站編號";

        return airTable.getTitle();
    }

    //--- check whether siteName exist in database
    private boolean isExist(String siteName){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(`SiteName`) FROM item WHERE SiteName = ?", new String[] {siteName});
        int result = 0;
        if (cursor.moveToFirst())
            result = cursor.getInt(0);
        cursor.close();
        db.close();
        return result > 0;
    }
    public void addByJSON(String json){
        if (isExist(jsonAnalysis.getFirstElement(json))){
            airTable.updteDate(jsonAnalysis.analyze(json), getWritableDatabase());
        }else {
            airTable.addData(jsonAnalysis.analyze(json), getWritableDatabase());
        }
    }

    public String queryItem(int id){
        String output = "get item ";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME +
                        "WHERE " + airTable.KEY_ID + " = ? ",new String[] {""+id});
        if (cursor.moveToFirst()){
            output += airTable.printData(cursor);
        }

        db.close();
        return output;
    }
    public List<String> queryAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM item", null);
        List<String> ls = airTable.printData(cursor);

        cursor.close();
        db.close();
        return ls;
    }
}
