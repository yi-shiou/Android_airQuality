package com.example.airquality.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.airquality.R;

import java.util.List;

public class MyListAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<String> list;
    public MyListAdapter(Context context, List<String> list){
        myInflater = LayoutInflater.from(context);
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.indexOf(getItem(position));
    }

    private class ViewHolder{
        TextView value;
        ImageView icon;
        public ViewHolder(TextView value,ImageView icon){
            this.value = value;
            this.icon = icon;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = myInflater.inflate(R.layout.list_item,null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.list_text),
                    (ImageView) convertView.findViewById(R.id.list_img)
            );
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.value.setText(list.get(position));
        return convertView;
    }

}
