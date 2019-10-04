package com.example.airquality.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "airQuality.db";
    public static final String TABLE_NAME = "item";
    public static final int VERSION = 1;

    private static MyDBHelper myDBHelper = null;
    private AirTable airTable = null;

    private static SQLiteDatabase database = null;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        database = getDatabase(context);
        airTable = new AirTable(TABLE_NAME,database);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper(context).getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AirTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AirTable.TABLE_NAME);
        onCreate(db);
    }

    public void close(){
        database.close();
    }
    public void addByJSON(String json){
        airTable.addByJSON(json);
    }
    public String queryItem(){
        String output = "";
        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery()

        db.close();
        return output;
    }
    public List<String> queryAll(){
        List<String> ls = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        while (cursor.moveToNext()){
            ls.add(airTable.printData(cursor));
        }

        db.close();
        return ls;
    }
}
