package com.example.airquality;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.example.airquality.model.GettingWebData;
import com.example.airquality.model.MyDBHelper;

import java.util.ArrayList;
import java.util.List;

public class Presenter implements IPresenter{
    private IView iView;
    private MyDBHelper myDBHelper;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    private static Handler handler;

    public Presenter(IView view, Context context){
        iView = view;
        initDatabase(context);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 11) {
                    updateDailyQuote((String)msg.obj);
                }else {
                    updateAirInfo((String)msg.obj);
                }
            }
        };
    }

    Runnable runnable_air = new Runnable() {
        @Override
        public void run() {
            String output = GettingWebData.getAirInfo_json();

            Message message = new Message();
            message.obj = output;
            message.what = 1;
            handler.sendMessage(message);

        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String output = GettingWebData.getDaily();

            Message message = new Message();
            message.obj = output;
            message.what = 11;
            handler.sendMessage(message);

        }
    };
    //--- for database
    private void initDatabase(Context context){
        myDBHelper = new MyDBHelper(context);

        arrayList = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,arrayList);
        iView.setLVAdapter(arrayAdapter);


    }
    ArrayList<String> getArrayList(){
        return arrayList;
    }
    ArrayAdapter<String> getArrayAdapter(){
        return arrayAdapter;
    }

    //--- for Air Info
    private void loadAirInfo(){
        new Thread(runnable_air).start();
    }
    private  void updateAirInfo(String s){
        myDBHelper.addByJSON(s);
        List<String> ls =  myDBHelper.queryAll();
        arrayList.addAll(ls);
        iView.setLVAdapter(arrayAdapter);

        iView.dismissDialog();
    }

    //--- for daily quote
    private void loadDailyQuote(){
        new Thread(runnable).start();
    }
    private void updateDailyQuote(String s){
        iView.setTextView(s);
    }


    public void onCreate() {
        iView.switchOver();
        loadAirInfo();
        loadDailyQuote();
    }
    public void onPause() { }
    public void onResume() { }
    public void onDestroy() { }

}
