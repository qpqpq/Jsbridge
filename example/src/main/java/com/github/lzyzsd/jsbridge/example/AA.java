package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.github.lzyzsd.jsbridge.example.util.ActivityManager;
import com.github.lzyzsd.jsbridge.example.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import static com.github.lzyzsd.jsbridge.example.Tab1.get;
import static com.github.lzyzsd.jsbridge.example.Tab1.getLast;
import static com.github.lzyzsd.jsbridge.example.Tab1.getPP;
import static com.github.lzyzsd.jsbridge.example.Tab1.tixing;
import static com.github.lzyzsd.jsbridge.example.Tab1.yao;

public class AA extends Activity implements View.OnClickListener {

    ViewPager viewPager;
    Button bt1;
    Button bt2;
    Button bt3;
    PagerAdapter adapter;
    List<View> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aa);
        initViews();//初始化控件
        initDatas();//初始化数据
        initEvents();//初始化事件
    }

    private void initEvents() {
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //获取ViewPager的当前Tab
                int currentItem = viewPager.getCurrentItem();
                //将所以的ImageButton设置成灰色
//                resetImgs();
                //将当前Tab对应的ImageButton设置成绿色
                switch (currentItem) {
                    case 0:
//                        mWeixinImg.setImageResource(R.mipmap.tab_weixin_pressed);
                        break;
                    case 1:
//                        mFrdImg.setImageResource(R.mipmap.tab_find_frd_pressed);
                        break;
                    case 2:
//                        mAddressImg.setImageResource(R.mipmap.tab_address_pressed);
                        break;
                    case 3:
//                        mSettingImg.setImageResource(R.mipmap.tab_settings_pressed);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDatas() {
        //初始化ViewPager的适配器
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = list.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(list.get(position));
            }
        };
        //设置ViewPager的适配器
        viewPager.setAdapter(adapter);
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        bt1 = (Button) findViewById(R.id.tsy);
        bt2 = (Button) findViewById(R.id.ttx);
        bt3 = (Button) findViewById(R.id.twode);


        //获取到四个Tab
        LayoutInflater inflater = LayoutInflater.from(this);
        View tab1 = inflater.inflate(R.layout.tab1, null);
        View tab2 = inflater.inflate(R.layout.tab2, null);
        View tab3 = inflater.inflate(R.layout.tab3, null);
        //---------------------
        Button xueyaguanli = (Button) tab1.findViewById(R.id.txueyaguanli);
        Button xuetangguanli = (Button) tab1.findViewById(R.id.txuetangguanli);
        Button tizhongguanli = (Button) tab1.findViewById(R.id.ttizhongguanli);
        Button yundongguanli = (Button) tab1.findViewById(R.id.tyundongguanli);
        Button yongyaoguanli = (Button) tab1.findViewById(R.id.tyongyaoguanli);
        Button tixingshezhi = (Button) tab1.findViewById(R.id.ttixingshezhi);
        TextView tianqi = (TextView) tab1.findViewById(R.id.ttianqi);
        TextView pm = (TextView) tab1.findViewById(R.id.tpm);
        TextView kongqizhiliang = (TextView) tab1.findViewById(R.id.tkongqizhiliang);
        TextView tizhong = (TextView) tab1.findViewById(R.id.ttizhong);
        TextView xuetang = (TextView) tab1.findViewById(R.id.txuetang);
        TextView xueya = (TextView) tab1.findViewById(R.id.txueya);
        TextView tixingshixiang = (TextView) tab1.findViewById(R.id.ttixingshixiang);
        TextView fuyaotixing = (TextView) tab1.findViewById(R.id.tfuyaotixing);
        xueyaguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AA.this, Pressure.class);
                startActivity(intent);
                finish();
            }
        });
        xuetangguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AA.this, Sugar.class);
                startActivity(intent);
                finish();
            }
        });
        yongyaoguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AA.this, Yao.class);
                startActivity(intent);
                finish();
            }
        });
        tizhongguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AA.this, Weight.class);
                startActivity(intent);
                finish();
            }
        });
        yundongguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AA.this, Exercise.class);
                startActivity(intent);
            }
        });
        tixingshezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AA.this, Tixing.class);
                startActivity(intent);
                finish();
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
        String s3 = Tab1.getLast2(pressure);
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
            String ss = (String) new JSONObject(state).getJSONArray("data").get(0);
            Log.e("tixing",tixing(ss));
            tixingshixiang.setText(tixing(ss));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //---------------------
        BridgeWebView webView = (BridgeWebView) tab2.findViewById(R.id.twebViews);
        webView.setDefaultHandler(new DefaultHandler());

        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                return true;
            }
        });

        webView.loadUrl("http://"+Config.IP3+"/#/message");

        webView.registerHandler("save", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {

                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                        String k = it.next();
                        editor.putString(k, jsonObject.getString(k));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editor.apply();
                function.onCallBack(data + "自定义");
            }

        });
        webView.registerHandler("get", new BridgeHandler() {

            @Override
            public void handler(String data, CallBackFunction function) {
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                String name = pref.getString("name", "default1");
                String phone = pref.getString("phone", "default2");
                String password = pref.getString("password", "default2");
                function.onCallBack(name + " " + phone + " " + password);
            }

        });
        //---------------------
        Button xinxi = (Button) tab3.findViewById(R.id.txinxi);
        TextView id = (TextView) tab3.findViewById(R.id.tid);
        Button dangan = (Button) tab3.findViewById(R.id.tdangan);
        Button tuichu = (Button) tab3.findViewById(R.id.ttuichu);
        id.setText(Tab1.getPP(AA.this).substring(0, 11));
        xinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AA.this, Info.class);
                startActivity(intent);
            }
        });
        dangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AA.this, Dangan.class);
                startActivity(intent);
            }
        });
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = AA.this.getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("phone","");
                editor.putString("password", "");
                editor.apply();
                Intent intent = new Intent(AA.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
        //---------------------
        //将四个Tab添加到集合中
        list.add(tab1);
        list.add(tab2);
        list.add(tab3);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tsy:
                //设置viewPager的当前Tab
                viewPager.setCurrentItem(0);
                //将当前Tab对应的ImageButton设置成绿色
//                mWeixinImg.setImageResource(R.mipmap.tab_weixin_pressed);
                break;
            case R.id.ttx:
                viewPager.setCurrentItem(1);
//                mFrdImg.setImageResource(R.mipmap.tab_find_frd_pressed);
                break;
            case R.id.twode:
                viewPager.setCurrentItem(2);
//                mAddressImg.setImageResource(R.mipmap.tab_address_pressed);
                break;
        }
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
                httpURLConnection.setRequestProperty("token", getPP(AA.this));
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
}
