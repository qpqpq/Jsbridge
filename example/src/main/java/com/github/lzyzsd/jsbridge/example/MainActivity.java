package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.example.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MainActivity extends Activity {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    TextView textView1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String s = get("https://www.tianqiapi.com/api/?version=v6");
        textView1 = (TextView) findViewById(R.id.tx1);
        textView2 = (TextView) findViewById(R.id.tx2);
        String ss = getYao();
        button1 = (Button) findViewById(R.id.bt1);
        button2 = (Button) findViewById(R.id.bt2);
        button3 = (Button) findViewById(R.id.bt3);
        button4 = (Button) findViewById(R.id.bt4);
        button5 = (Button) findViewById(R.id.bt5);
        button6 = (Button) findViewById(R.id.bt6);
        button7 = (Button) findViewById(R.id.bt7);
        button8 = (Button) findViewById(R.id.bt8);
        System.out.println(s);
        JSONObject jsonObject = null;
//        {"cityid":"101060101","date":"2019-05-21","week":"星期二","update_time":"13:00","city":"长春","cityEn":"changchun","country":"中国","countryEn":"China","wea":"阴","wea_img":"yin","tem":"11","win":"西北风","win_speed":"3级","win_meter":"小于12km\/h","humidity":"74%","visibility":"14.32km","pressure":"968","air":"28","air_pm25":"28","air_level":"优","air_tips":"空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！","alarm":{"alarm_type":"","alarm_level":"","alarm_content":""}}

        try {
            jsonObject = new JSONObject(s);
            textView1.setText("地区：" + jsonObject.getString("country") + " " + jsonObject.getString("city")
                    + "\n时间：" + jsonObject.getString("date") + " " + jsonObject.getString("week")
                    + "\n最后更新时间：" + jsonObject.getString("update_time")
                    + "\n天气：" + jsonObject.getString("wea") + " " + jsonObject.getString("win") + jsonObject.getString("win_speed")
                    + "\n空气质量：" + jsonObject.getString("air_level") + "\n\n");
            textView2.setText(ss);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Pressure.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Sugar.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Yao.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Weight.class);
                startActivity(intent);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Info.class);
                startActivity(intent);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Exercise.class);
                startActivity(intent);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Dangan.class);
                startActivity(intent);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Tixing.class);
                startActivity(intent);
            }
        });
    }

    public static String get(final String url) {
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                BufferedReader br = null;
                InputStreamReader isr = null;
                URLConnection conn;
                try {
                    URL geturl = new URL(url);
                    conn = geturl.openConnection();//创建连接
                    conn.connect();//get连接
                    isr = new InputStreamReader(conn.getInputStream());
                    br = new BufferedReader(isr);
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line);//获取输入流数据
                    }
                    System.out.println(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {//执行流的关闭
                    if (br != null) {
                        try {
                            if (br != null) {
                                br.close();
                            }
                            if (isr != null) {
                                isr.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return sb.toString();
            }
        });
        new Thread(task).start();
        String s = null;
        try {
            s = task.get();//异步获取返回值
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public String getYao() {
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                URLConnection urlConnection = new URL("http://"+ Config.IP +":8080/yao/get").openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setRequestProperty("token", getPP());
//                httpURLConnection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                httpURLConnection.addRequestProperty("Content-Type", "application/json");
                httpURLConnection.connect();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                bufferedWriter.flush();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String s = "";
                while ((s = bufferedReader.readLine()) != null) {
                    System.out.println(s);
                    sb.append(s);
                }
                return sb.toString();
            }
        });
        new Thread(task).start();
        String s = null;
        try {
            s = task.get();//异步获取返回值
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    String getPP() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String phone = pref.getString("phone", "default2");
        String password = pref.getString("password", "default2");
        System.out.println(phone + password);
        Log.e("token", phone + password);
        return phone + password;
    }
}
