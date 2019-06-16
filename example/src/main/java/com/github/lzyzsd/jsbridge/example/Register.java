package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.example.util.ActivityManager;
import com.github.lzyzsd.jsbridge.example.util.Config;
import com.github.lzyzsd.jsbridge.example.util.SHA256;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Register extends Activity {

    EditText textView1;
    EditText textView2;
    Button button;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        setContentView(R.layout.register);
        textView1 = (EditText) findViewById(R.id.phone);
        textView2 = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        if (getPP().length() > 10) {
            login();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone = String.valueOf(textView1.getText());
                final String password = String.valueOf(textView2.getText());
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("phone", phone);
                editor.putString("password", SHA256.getSHA(phone + password));
                editor.apply();
                login2(phone, password);
            }
        });
        button = (Button) findViewById(R.id.register);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phone = String.valueOf(textView1.getText());
                final String password = String.valueOf(textView2.getText());
                String register = register("http://"+ Config.IP +":8080/user/register", phone, password);
                try {
                    JSONObject jsonObject = new JSONObject(register);
                    if (jsonObject.getString("code").equals("0")) {
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("phone", phone);
                        editor.putString("password", SHA256.getSHA(phone + password));
                        editor.apply();
                        Toast.makeText(Register.this, "注册成功", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Register.this,Info.class));
                        finish();
                    } else if (jsonObject.getString("code").equals("1")) {
                        Toast.makeText(Register.this, "此号已注册过", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void login2(final String ph, final String pa) {
        Log.e("passw", ph + " " + pa + " " + ph + SHA256.getSHA(ph + pa));
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                URLConnection urlConnection = new URL("http://"+Config.IP+":8080/user/info").openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);
                httpURLConnection.setRequestProperty("token", ph + SHA256.getSHA(ph + pa));
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
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            if (jsonObject.getString("code").equals("0")) {
                Toast.makeText(Register.this, "登录成功", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Register.this, AA.class));
                finish();
            } else if (jsonObject.getString("code").equals("1")) {
                Toast.makeText(Register.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(Register.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void login() {
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                URLConnection urlConnection = new URL("http://"+Config.IP+":8080/user/info").openConnection();
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
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            if (jsonObject.getString("code").equals("0")) {
                Toast.makeText(Register.this, "登录成功", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Register.this, AA.class));
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String register(final String url, final String phone, final String password) {
        final StringBuilder sb = new StringBuilder();
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                URLConnection urlConnection = new URL(url).openConnection();
                HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);
//                httpURLConnection.addRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
                httpURLConnection.addRequestProperty("Content-Type", "application/json");
                httpURLConnection.connect();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream()));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("phone", phone);
                jsonObject.put("password", password);
                bufferedWriter.write(jsonObject.toString());
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
        String phone = pref.getString("phone", "d");
        String password = pref.getString("password", "d");
        System.out.println(phone + password);
        Log.e("token", phone + password);
        return phone + password;
    }
}
