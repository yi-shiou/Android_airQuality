package com.example.airquality.model;

import androidx.annotation.NonNull;

public class AirInfo {
    public static final String COLUMN_NAMES[] = {"SiteName", "County", "AQI", "Pollutant", "Status", "SO2", "CO", "CO_8hr", "O3", "O3_8hr", "PM10", "PM2.5", "NO2", "NOx", "NO", "WindSpeed", "WindDirec", "PublishTime", "PM2.5_AVG", "PM10_AVG", "SO2_AVG", "Longitude", "Latitude", "SiteId"};

    @NonNull
    private String siteName;
//    private String detal;

    public AirInfo(String siteName){
        this.siteName = siteName;
    }

    @NonNull
    public String getSiteName() {
        return siteName;
    }
}
