package com.example.airquality;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;

import com.example.airquality.model.DBModel;
import com.example.airquality.model.GettingWebData;
import com.example.airquality.model.IDBModel;

import java.util.ArrayList;

public class Presenter implements IPresenter{
    private IView iView;
    private IDBModel idbModel;

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
        idbModel = new DBModel(context);
        iView.setLVAdapter(idbModel.getArrayAdapter());

        iView.setTitle(idbModel.getTitle());
    }
    public ArrayList<String> getArrayList(){
        return idbModel.getArrayList();
    }
    public ArrayAdapter<String> getArrayAdapter(){
        return idbModel.getArrayAdapter();
    }

    //--- for Air Info
    private void loadAirInfo(){
        new Thread(runnable_air).start();
    }
    private  void updateAirInfo(String s){
        idbModel.addByJSON(s);
        iView.dismissDialog();
        showAirInfo();
    }
    private void showAirInfo(){
        iView.setLVAdapter(idbModel.showAirInfo());
    }

    //--- for daily quote
    private void loadDailyQuote(){
        new Thread(runnable).start();
    }
    private void updateDailyQuote(String s){
        iView.showDailyQuote(s);
    }


    public void refreshData(){
        iView.switchOver();     //-- show ProgressDialog from refreshData to updateAirInfo
        loadAirInfo();
        loadDailyQuote();
    }
    public void onCreate() {
        showAirInfo();
        refreshData();
    }
    public void onPause() { }
    public void onResume() { }
    public void onDestroy() { }

}
