package com.example.airquality.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.airquality.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private ArrayList<String> list;
    public ListAdapter(Context context, ArrayList<String> itemList)
    {
        myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = itemList;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        if (convertView == null) {
//            v = myInflater.inflate(R.layout.list_item, null);
        }else {
//            v = convertView;
        }
//        ImageView imgView = (ImageView) v.findViewById(R.id.list_img);
//        TextView txtView = (TextView) v.findViewById(R.id.list_text);

//        imgView.setImageResource(Integer.valueOf(mItemList.get(position).get("Item icon").toString()));
//        txtView.setText("TAET");

        return v;
    }
}
