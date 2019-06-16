package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.github.lzyzsd.jsbridge.example.util.ActivityManager;
import com.github.lzyzsd.jsbridge.example.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class Dangan extends Activity {
    BridgeWebView webView;
    ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        setContentView(R.layout.dangan);
        webView = (BridgeWebView) findViewById(R.id.webViewd);
        button = (ImageButton) findViewById(R.id.backd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

        webView.loadUrl("http://"+ Config.IP3 +"/#/archives?" + getPP());

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