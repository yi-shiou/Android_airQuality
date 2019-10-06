package com.example.airquality.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

public class GettingWebData {
    private static String AIR_PATH = "http://opendata.epa.gov.tw/webapi/Data/REWIQA/?$orderby=SiteName&$skip=0&$top=100&format=json";
    private static String DAILY_PATH = "https://tw.appledaily.com/index/dailyquote/";

    public static String getAirInfo_json(){
        String output = "";
        try {
            InputStream input = new URL(AIR_PATH).openStream();

            Reader reader = new InputStreamReader(input, "UTF-8");
            int data = reader.read();
            while (data != -1) {
                output += (char)data;
                data = reader.read();
            }

            if(output != "") {
//                    output = doc.text();
//                    output = "done";
            }else
                output = "Did not work!";

        } catch (Exception e) {
            output += "error";
        }
        return output;
    }

    public static String getDaily(){
            String output = "found nothing";
            try
            {
                Document doc = Jsoup.connect(DAILY_PATH).get();
                Element dqTag = doc.selectFirst("article.dphs");
                if (dqTag != null) {
                    output = dqTag.text();
                }
            } catch (IOException e){
            }
            return output;
    }
}
