package com.github.lzyzsd.jsbridge.example;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.example.util.ActivityManager;

public class Tab3 extends Activity {
    TextView id;
    Button xinxi;
    Button dangan;
    Button tuichu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(this);
        setContentView(R.layout.tab3);
        xinxi = (Button) findViewById(R.id.txinxi);
        id = (TextView) findViewById(R.id.tid);
        dangan = (Button) findViewById(R.id.tdangan);
        tuichu= (Button) findViewById(R.id.ttuichu);
        id.setText(getPP().substring(0, 11));
        xinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tab3.this, Info.class);
                startActivity(intent);
            }
        });
        dangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tab3.this, Dangan.class);
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
                Intent intent = new Intent(Tab3.this, Register.class);
                startActivity(intent);
            }
        });
    }

    public static String getPP() {
        SharedPreferences pref = new Tab3().getSharedPreferences("data", MODE_PRIVATE);
        String phone = pref.getString("phone", "default2");
        return phone;
    }
}
