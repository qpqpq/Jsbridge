package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.example.util.ActivityManager;
import com.github.lzyzsd.jsbridge.example.util.SHA256;

public class Me extends Activity {
    TextView id;
    Button xinxi;
    Button dangan;
    Button tuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        setContentView(R.layout.me);
        xinxi = (Button) findViewById(R.id.xinxi);
        id = (TextView) findViewById(R.id.id);
        dangan = (Button) findViewById(R.id.dangan);
        tuichu= (Button) findViewById(R.id.tuichu);
        id.setText(getPP().substring(0, 11));
        xinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Me.this, Info.class);
                startActivity(intent);
            }
        });
        dangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Me.this, Dangan.class);
                startActivity(intent);
            }
        });
        tuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                editor.putString("phone","");
                editor.putString("password", "");
                editor.apply();
                ActivityManager.getInstance().exit();
                Intent intent = new Intent(Me.this, Register.class);
                startActivity(intent);
            }
        });
    }

    String getPP() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String phone = pref.getString("phone", "default2");
        return phone;
    }
}
