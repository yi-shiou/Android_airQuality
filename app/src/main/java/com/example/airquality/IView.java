package com.example.airquality;

import com.example.airquality.model.MyListAdapter;

public interface IView {

    void showDailyQuote(String s);
    void setTitle(String s);

    // function about dialog
    void showProgressDialog();
    void showAlertDialog();
    void dismissDialog();
    void switchOver();//show the loading dialog

    // for database
    void setLVAdapter(MyListAdapter adapter);
}