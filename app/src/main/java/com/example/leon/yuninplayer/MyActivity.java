package com.example.leon.yuninplayer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {
    String ip;
    String port;
    TextView textView;
    TextView textView2;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        textView = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);

        ip = super.getIntent().getStringExtra("ip");
        port = super.getIntent().getStringExtra("port");
        textView.setText(ip);
        textView2.setText(port);
    }
}
