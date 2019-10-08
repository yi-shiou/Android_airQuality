package com.example.airquality;

public interface IPresenter {
    //--- for database
    void refreshData();
    void deleteListViewElement(int position);

    void onCreate();
}
