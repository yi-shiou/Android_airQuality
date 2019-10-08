package com.example.airquality;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IView{

    private  TextView tv_title;
    private TextView tv_dailyquote;
    private ListView listView;
    private  IPresenter iPresenter;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        iPresenter = new Presenter(this,getApplicationContext());
        iPresenter.onCreate();
    }

    private void setupViews() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_dailyquote = (TextView) findViewById(R.id.tv_dailyquote);
        listView = (ListView) findViewById(R.id.listView);
        Button bt_refresh = (Button) findViewById(R.id.bt_refresh);
        bt_refresh.setText("刷新");
        bt_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPresenter.refreshData();
            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            float x, y, upx, upy;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                final int action = event.getAction();
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        upx = event.getX();
                        upy = event.getY();
                        int position1 = ((ListView) view).pointToPosition((int) x, (int) y);
                        int position2 = ((ListView) view).pointToPosition((int) upx,(int) upy);

                        if (position1 == position2 && Math.abs(x - upx) > 10) {
                            View v = ((ListView) view).getChildAt(position1);
                            iPresenter.deleteListViewElement(position1);
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void showDailyQuote(@NonNull String s){
        tv_dailyquote.setText(s);
    }

    @Override
    public void setTitle(String s){
        tv_title.setText(s);
    }

    // @Override function about dialog
    @Override
    public void showProgressDialog(){
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在抓取数据...");
            dialog.setCancelable(false);
            dialog.show();
    }
    @Override
    public void showAlertDialog(){
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

    @Override
    public void dismissDialog(){
        dialog.dismiss();
    }
    @Override
    public void setLVAdapter(ArrayAdapter adapter){
        listView.setAdapter(adapter);
    }



    @Override
    public void switchOver() {
        if (isNetworkAvailable(MainActivity.this)) {
            showProgressDialog();
        } else {
            // 弹出提示框
             showAlertDialog();
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
}
