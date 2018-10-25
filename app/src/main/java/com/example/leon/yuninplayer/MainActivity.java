package com.example.leon.yuninplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editIP;
    EditText editPort;
    Button buttonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        editIP = findViewById(R.id.editIP);
        editPort = findViewById(R.id.editPort);
        buttonConnect = findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"开始连接...",Toast.LENGTH_SHORT).show();
                String ip = editIP.getText().toString();
                String port = editPort.getText().toString();
                if (TextUtils.isEmpty(ip))
                {
                    Toast.makeText(getApplicationContext(),"IP不能为空",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(port))
                {
                    Toast.makeText(getApplicationContext(),"PORT不能为空",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //启动另一个显示界面,并将参数传递过去.
                    Intent intent = new Intent(MainActivity.this,MyActivity.class);
                    intent.putExtra("ip",ip);
                    intent.putExtra("port",port);
                    startActivity(intent);
                }
            }
        });
    }
}
