package com.example.airquality.model;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.airquality.R;
import com.example.airquality.model.AirInfo;

import java.util.ArrayList;

public class MyListAdapter extends android.widget.BaseAdapter{

    private ArrayList<AirInfo> listData;
    private LayoutInflater layoutInflater;
    public MyListAdapter(Context context, ArrayList<AirInfo> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);  //獲得LayoutInflater
    }
    @Override
    public int getCount() {
        return listData.size();
    }
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row, null); //LayoutInflater.inflate來載入xml文件布局檔
            holder = new ViewHolder();  //實體化holder
            holder.list_siteName = (TextView)convertView.findViewById(R.id.list_siteName);
            holder.textview = (TextView) convertView.findViewById(R.id.list_item);

            convertView.setTag(holder);  //把convertView塞入holder裡，下次convertView不是空的條件下 就可以直接把holder拿出來用
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AirInfo encapsulation = listData.get(position);

        holder.textview.setBackgroundColor(Color.parseColor("#f6565a"));  //給image設定顏色
//            convertView.setBackgroundColor(Color.WHITE);

        if (position%2==0){   //判斷是不是偶數列，是的話就把那列設定顏色
            convertView.setBackgroundColor(Color.parseColor("#0ce8e2"));
        }

        holder.list_siteName.setText(encapsulation.getSiteName());
        return convertView;
    }
    static class ViewHolder {  //用來裝在listview裡每一列要顯示的元件
        TextView list_siteName;
        TextView textview;
    }

}