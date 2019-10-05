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
    public AirTable airTable = null;
    private Context context;

//    private static SQLiteDatabase database = null;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

        airTable = new AirTable(TABLE_NAME);
        this.context = context;
        this.getWritableDatabase().close();
    }

//    public static SQLiteDatabase getDatabase(Context context) {
//        if (database == null || !database.isOpen()) {
//            database = this.getWritableDatabase();
//        }
//        return database;
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(airTable.CREATE_TABLE);
//		final String SQL = "CREATE TABLE IF NOT EXISTS " + "MySample" + "( " +
//		                       "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
//				               "_TITLE VARCHAR(50), " +
//		                       "_CONTENT TEXT," +
//				               "_KIND VARCHAR(10)" +
//				           ");";
//        db.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + airTable.TABLE_NAME);
        onCreate(db);
    }

//    public void close(){
//        database.close();
//    }
    public void addByJSON(String json){
        airTable.addByJSON(json,getWritableDatabase());
    }
    public String queryItem(int id){
        String output = "get item ";
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME +
                        "WHERE " + airTable.KEY_ID + " = ? ",new String[] {""+id});
        if (cursor.moveToFirst()){
//            output += airTable.printData(cursor);
        }

        db.close();
        return output;
    }
    public List<String> queryAll(){
        List<String> ls = new ArrayList<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM item", null);
//                "SELECT COUNT(`SiteName`) FROM item", null);
//                "SELECT * FROM item WHERE SiteName = ?", new String[] {"二林"});
        if (cursor.moveToNext()) {
            ls.add(airTable.printData(cursor));
        }

        cursor.close();
        db.close();
        return ls;
    }
}
