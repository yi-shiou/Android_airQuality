package com.example.airquality;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView tv_dailyquote;

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        switchOver();
    }
    private void setupViews() {
        tv_dailyquote = (TextView) findViewById(R.id.tv_dailyquote);
    }
    //*********** for not MVP ****************

    private String path = "https://tw.appledaily.com/index/dailyquote/";
//    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";


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
            message.arg1 = Integer.parseInt("1");
            handler.sendMessage(message);

        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            show((String)msg.obj);
        }
    };
    private void show(String s){

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

//            list.clear();
            new Thread(runnable).start();  // 子线程

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
