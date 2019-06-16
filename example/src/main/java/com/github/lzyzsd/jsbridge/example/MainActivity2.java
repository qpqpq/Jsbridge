package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.example.util.ActivityManager;
import com.github.lzyzsd.jsbridge.example.util.Config;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MainActivity2 extends Activity {

    Button xueyaguanli;
    Button xuetangguanli;
    Button tizhongguanli;
    Button yundongguanli;
    Button yongyaoguanli;
    Button tixingshezhi;
    TextView tianqi;
    TextView pm;
    TextView kongqizhiliang;
    TextView xuetang;
    TextView tizhong;
    TextView xueya;
    Button wode;
    TextView fuyaotixing;
    TextView tixingshixiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        setContentView(R.layout.activity_main2);
        xueyaguanli = (Button) findViewById(R.id.xueyaguanli);
        xuetangguanli = (Button) findViewById(R.id.xuetangguanli);
        tizhongguanli = (Button) findViewById(R.id.tizhongguanli);
        yundongguanli = (Button) findViewById(R.id.yundongguanli);
        yongyaoguanli = (Button) findViewById(R.id.yongyaoguanli);
        tixingshezhi = (Button) findViewById(R.id.tixingshezhi);
        tianqi = (TextView) findViewById(R.id.tianqi);
        pm = (TextView) findViewById(R.id.pm);
        kongqizhiliang = (TextView) findViewById(R.id.kongqizhiliang);
        tizhong = (TextView) findViewById(R.id.tizhong);
        xuetang = (TextView) findViewById(R.id.xuetang);
        xueya = (TextView) findViewById(R.id.xueya);
        wode = (Button) findViewById(R.id.wode);
        tixingshixiang= (TextView) findViewById(R.id.tixingshixiang);
        fuyaotixing = (TextView) findViewById(R.id.fuyaotixing);
        xueyaguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Pressure.class);
                startActivity(intent);
            }
        });
        xuetangguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Sugar.class);
                startActivity(intent);
            }
        });
        yongyaoguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Yao.class);
                startActivity(intent);
            }
        });
        tizhongguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Weight.class);
                startActivity(intent);
            }
        });
        yundongguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Exercise.class);
                startActivity(intent);
            }
        });
        tixingshezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Tixing.class);
                startActivity(intent);
            }
        });
        wode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, Me.class);
                startActivity(intent);
            }
        });
        String s = get("https://www.tianqiapi.com/api/?version=v6");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            System.out.println(jsonObject);
            tianqi.setText("天气:" + jsonObject.getString("wea"));
            pm.setText("PM2.5:" + jsonObject.getString("air_pm25"));
            kongqizhiliang.setText("空气质量:" + jsonObject.getString("air_tips"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String yao = getA("yao");
        String pressure = getA("pressure");
        String sugar = getA("sugar");
        String weight = getA("weight");
        String s1 = getLast(sugar);
        String s2 = getLast(weight);
        String s3 = getLast2(pressure);
        String state = getA("state");
        xuetang.setText("血糖:" + s1);
        tizhong.setText("体重:" + s2);
        xueya.setText("血压:" + s3);
        StringBuilder builder = new StringBuilder();
        List<String> yao1 = yao(yao);
        for (String ss : yao1) {
            builder.append(ss + "\n");
        }
        fuyaotixing.setText(builder.toString());
        try {
            String ss= (String) new JSONObject(state).getJSONArray("data").get(0);
            tixingshixiang.setText(tixing(ss));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        System.out.println(ss+"---------------------------------------------------");
//        JSONObject jsonObject = null;
//        {"cityid":"101060101","date":"2019-05-21","week":"星期二","update_time":"13:00","city":"长春","cityEn":"changchun","country":"中国","countryEn":"China","wea":"阴","wea_img":"yin","tem":"11","win":"西北风","win_speed":"3级","win_meter":"小于12km\/h","humidity":"74%","visibility":"14.32km","pressure":"968","air":"28","air_pm25":"28","air_level":"优","air_tips":"空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！","alarm":{"alarm_type":"","alarm_level":"","alarm_content":""}}

        try {
            jsonObject = new JSONObject(s);
//            textView1.setText("地区：" + jsonObject.getString("country") + " " + jsonObject.getString("city")
//                    + "\n时间：" + jsonObject.getString("date") + " " + jsonObject.getString("week")
//                    + "\n最后更新时间：" + jsonObject.getString("update_time")
//                    + "\n天气：" + jsonObject.getString("wea") + " " + jsonObject.getString("win") + jsonObject.getString("win_speed")
//                    + "\n空气质量：" + jsonObject.getString("air_level") + "\n\n");
//            textView2.setText(ss);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity2.this, Pressure.class);
//                startActivity(intent);
//            }
//        });


    }

    private List<String> yao(String s) {
        List<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray data = jsonObject.getJSONArray("data");
            if (data.length() > 0) {
                for (int i = 0; i < data.length(); i++) {
                    try {
                        String name = data.getJSONObject(i).getString("name");
                        if (name != null) {
                            list.add(name);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String getLast(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray data = jsonObject.getJSONArray("data");
            if (data.length() > 0) {
                JSONObject jsonObject1 = data.getJSONObject(data.length() - 1);
                return (Math.round(Double.valueOf(jsonObject1.getString("num")) * 100) / 100.0) + "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getLast2(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray high = data.getJSONArray("high");
            JSONArray low = data.getJSONArray("low");
            double h = 0, l = 0;
            if (high.length() > 0) {
                JSONObject jsonObject1 = high.getJSONObject(high.length() - 1);
                h = Double.parseDouble(jsonObject1.getString("num"));
            }
            if (low.length() > 0) {
                JSONObject jsonObject1 = low.getJSONObject(low.length() - 1);
                l = Double.parseDouble(jsonObject1.getString("num"));
            }
            return (Math.round(h * 100) / 100.0) + "/" + (Math.round(l * 100) / 100.0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
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

    public String getA(final String url) {
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                URLConnection urlConnection = new URL("http://"+ Config.IP +":8080/" + url + "/get").openConnection();
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
                bufferedWriter.write("{\"timeType\":\"2\"}");
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

    StringBuilder tixing(String s) {
        StringBuilder stringBuilder=new StringBuilder();
        List<String> list = new ArrayList<>();
        list.add("血压(上午)");
        list.add("血压(下午)");
        list.add("血压(晚上)");
        list.add("血糖(早饭前)");
        list.add("血糖(早饭后)");
        list.add("血糖(午饭前)");
        list.add("血糖(午饭后)");
        list.add("血糖(晚饭前)");
        list.add("血糖(晚饭后)");
        list.add("血糖(睡前)");
        list.add("血糖(凌晨)");
        list.add("体重");
        for (int i = 0; i < list.size()&&i<s.length(); i++) {
            if(s.charAt(i)=='1'){
                stringBuilder.append(list.get(i)+"\n");
            }
        }
        return stringBuilder;
    }
}
