package com.example.leon.yuninplayer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MyActivity extends Activity {
    public String ip;
    public String port;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ip = (String) super.getIntent().getStringExtra("ip");
        port = (String) super.getIntent().getStringExtra("port");
        setContentView(new MySurfaceView(this));
    }

    //---------------------------------------------------------------------------
    private Socket listerSocket = null;
    BufferedWriter writer = null;
    BufferedReader reader = null;

    public void socketConnectThread()
    {
        new Thread() {
            OutputStream os = null;
            public void run()
            {
                try {
                    listerSocket = new Socket(ip,Integer.parseInt(port));
                    os = listerSocket.getOutputStream();
                    os.write(ip.getBytes());
                    os.flush();
                    listerSocket.shutdownOutput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //--------------------------------------------------------------------------------------

    class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable
    {
        private SurfaceHolder holder;
        private boolean isRunning = false;
        Canvas canvas;
        public MySurfaceView(Context context) {
            super(context);
            holder = this.getHolder();
            holder.addCallback(this);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            isRunning = true;
            Thread thread = new Thread(this);
            thread.start();
            socketConnectThread();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }

        @Override
        public void run() {
            int count = 0;
            int x = 0,y = 0;
            while (isRunning)
            {
                try {
                    canvas = holder.lockCanvas();
                    //draw something
                    canvas.drawColor(Color.BLACK);
                    Paint p = new Paint();
                    p.setColor(Color.CYAN);
                    p.setTextSize(100);
                    canvas.drawText("定时器: " + (count++) + "秒", x, ((y++)%18+1)*100, p);
                    // 睡眠时间为1秒
                    Thread.sleep(1000);
                    socketConnectThread();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != canvas) {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }
}
