package com.example.airquality.model;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InputStream;

public class HttpAsyncTask extends AsyncTask {

    private MyDBHelper myDBHelper;


    @Override
    protected Object doInBackground(Object[] strings) {
        return GET((String) strings[0]);
    }
//     onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(Object result) {
//        myDBHelper = new MyDBHelper(this,MyDBHelper.DATABASE_NAME,null,MyDBHelper.VERSION);
    }

    private  String GET(String url){
        String result = "";
        try {

            Document doc = Jsoup.connect(url).get();
            if(doc.text() != null)
                result = doc.text();
            else
                result = "Did not work!";

        } catch (Exception e) {
        }

        return result;
    }
}
