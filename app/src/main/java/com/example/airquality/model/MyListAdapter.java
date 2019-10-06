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

public class MyListAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private ArrayList<String> list;
    public MyListAdapter(Context context, ArrayList<String> list){
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
//        return list.indexOf(getItem(position));
        return position;
    }

    static class ViewHolder{
        TextView value;
//        ImageView icon;
//        public ViewHolder(TextView value,ImageView icon){
//            this.value = value;
//            this.icon = icon;
//        }
        public ViewHolder(TextView value){
            this.value = value;
//            this.icon = null;
            value.setText("INIT");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //*/
        ViewHolder holder;
        if (convertView == null){
            convertView = myInflater.inflate(R.layout.list_item,null);
            holder = new ViewHolder(
                    (TextView) convertView.findViewById(R.id.list_text)
            );
//            holder = new ViewHolder(
//                    (TextView) convertView.findViewById(R.id.list_text),
//                    (ImageView) convertView.findViewById(R.id.list_img)
//            );
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //*/
        /*/
        View item = myInflater.inflate(R.layout.list_item, null);
        TextView textView = (TextView)item.findViewById(R.id.list_text);
        textView.setText("HELLO");
        return item;
        //*/
//        holder.value.setText(list.get(position));
        holder.value.setText("HAAAAAAAAAAAAA");
//        switch (position){
////            case 0:
////
////                break;
////            case 1:
////                break;
//            default:
//                holder.icon= null;
//                break;
//        }
        return convertView;
    }

}
