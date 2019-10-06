package com.example.airquality;

import android.widget.ArrayAdapter;

public interface IView {

    void showDailyQuote(String s);
    void setTextView(String s);

    // function about dialog
    void showProgressDialog();
    void showAlertDialog();
    void dismissDialog();
    void switchOver();//show the loading dialog

    // for database
    void setLVAdapter(ArrayAdapter adapter);
}