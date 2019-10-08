package com.example.airquality.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GettingWebData {
    private static final String AIR_PATH = "http://opendata.epa.gov.tw/webapi/Data/REWIQA/?$orderby=SiteName&$skip=0&$top=100&format=json";
    private static final String DAILY_PATH = "https://tw.appledaily.com/index/dailyquote/";
    //--- to get daily quote in DAILY_PATH
    private static final String REGEX = "<article class=\"dphs\">\\s*<p>(.*)</p>\\s*<h1>(.*)<time";

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
        } catch (Exception e) {
        }
        return output;
    }

    public static String getDaily(){
        String output = "found nothing";
        try
        {
            URL u = new URL(DAILY_PATH);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(9000);   //设置连接主机超时（单位：毫秒）
            c.setReadTimeout(9000);      //设置从主机读取数据超时（单位：毫秒）
            c.setRequestProperty("User-Agent","Mozilla/5.0");
            c.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(),"utf-8"));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            output = getDailyFromContent(sb.toString());
        } catch (IOException e){
        }
        return output;
    }
    private static String getDailyFromContent(String content){
        String output = "Using regex";
        try {
            Matcher matcher = Pattern.compile(REGEX).matcher(content);
            if (matcher.find()){
                output = matcher.group(1) + "\nBy " + matcher.group(2);
            }else
                output = "not found";
        }catch (Exception e){
        }
        return output;
    }
}
