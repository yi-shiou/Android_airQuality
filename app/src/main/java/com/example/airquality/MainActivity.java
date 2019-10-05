package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.airquality.model.AirTable;
import com.example.airquality.model.HttpAsyncTask;
import com.example.airquality.model.MyDBHelper;
import com.example.airquality.model.MyListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tv_dailyquote;
    private ListView listView;

    //for database
    private MyDBHelper myDBHelper;
    private AirTable airTable;

    private ArrayList<String> items;
    private MyListAdapter adapter;

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();


        //**** for database
        myDBHelper = new MyDBHelper(getApplicationContext());
//
        items = new ArrayList<String>();
        adapter = new MyListAdapter(getApplicationContext(),items);
        items.add("HIHI");
        listView.setAdapter(adapter);


        //--end dor database ****/
        switchOver();
    }
    private void setupViews() {
        tv_dailyquote = (TextView) findViewById(R.id.tv_dailyquote);
        listView = (ListView) findViewById(R.id.listView);
    }
    //*********** for not MVP ****************

    //--- for air
    private String airPATH = "http://opendata.epa.gov.tw/webapi/Data/REWIQA/?$orderby=SiteName&$skip=0&$top=3&format=json";
    Runnable runnable_air = new Runnable() {
        @Override
        public void run() {
            String output = "";
            try {
                InputStream input = new URL(airPATH).openStream();

                Reader reader = new InputStreamReader(input, "UTF-8");
                int data = reader.read();
                while (data != -1) {
                    output += (char)data;
                    data = reader.read();
                }

                if(output != "") {
//                    output = doc.text();
//                    output = "done";
                }else
                    output = "Did not work!";

            } catch (Exception e) {
                output += "error";
            }

            Message message = new Message();
            message.obj = output;
            message.what = 1;
            handler.sendMessage(message);

        }
    };
//    private




    //--- for daily
    private String path = "https://tw.appledaily.com/index/dailyquote/";

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            String output = "found nothing";
            try
            {
                Document doc = Jsoup.connect(path).get();
                Element dqTag = doc.selectFirst("article.dphs");
                if (dqTag != null) {
                    output = dqTag.text();
                }
            } catch (IOException e){
            }
            Message message = new Message();
            message.obj = output;
            message.what = 11;
            handler.sendMessage(message);

        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 11) {
                showDailyQuote((String)msg.obj);
            }else {
                showAirData((String)msg.obj);
            }
        }
    };
    private  void showAirData(String s){
//*/
        String tmp = "1";

        tmp = myDBHelper.queryAll().get(0);
//        items = new ArrayList<String>();
//        adapter.addToList(tmp);
//        listView.setAdapter(adapter);
//
//        items = new ArrayList<String>();
//        items.add(tmp);
//        adapter = new MyListAdapter(getApplicationContext(),items);
//        listView.setAdapter(adapter);

        showDailyQuote(tmp);
        dialog.dismiss();
    }
    private void showDailyQuote(String s){

        tv_dailyquote.setText(s);
        dialog.dismiss();
    }

    public void switchOver() {
        if (isNetworkAvailable(MainActivity.this)) {
            // 显示“正在加载”窗口
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在抓取数据...");
            dialog.setCancelable(false);
            dialog.show();

//            new Thread(runnable).start();
            new Thread(runnable_air).start();

        } else {
            // 弹出提示框
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("当前没有网络连接！")
                    .setPositiveButton("重试", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switchOver();
                        }
                    }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);  // 退出程序
                }
            }).show();
        }
    }

    // 判断是否有可用的网络连接
    public boolean isNetworkAvailable(MainActivity activity) {
        Context context = activity.getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        else {   // 获取所有NetworkInfo对象
            NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++)
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;  // 存在可用的网络连接
            }
        }
        return false;
    }


    //*********** for not MVP ****************/
}
