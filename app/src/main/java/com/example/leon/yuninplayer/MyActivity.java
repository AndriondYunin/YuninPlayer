package com.example.leon.yuninplayer;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MyActivity extends Activity implements Runnable{
    public String ip;
    public String port;
    public String inStr;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ip = (String) super.getIntent().getStringExtra("ip");
        port = (String) super.getIntent().getStringExtra("port");
        setContentView(new MySurfaceView(this));
        Thread t = new Thread(this);
        t.start();
    }

    //---------------------------------------------------------------------------
    private Socket listerSocket = null;
    BufferedWriter writer = null;
    BufferedReader bufReader = null;

    public void socketConnectThread()
    {
        new Thread() {
            OutputStream outputStream = null;
            InputStream inputStream = null;
            public void run()
            {
                try {
                    listerSocket = new Socket(ip,Integer.parseInt(port));
                    outputStream = listerSocket.getOutputStream();
                    outputStream.write("hello,andriond".getBytes());
                    outputStream.flush();
                    listerSocket.shutdownOutput();
                    //输入流
                    inputStream = listerSocket.getInputStream();
                    InputStreamReader reader = new InputStreamReader(inputStream);
                    bufReader = new BufferedReader(reader);
                    inStr = bufReader.readLine();
                    bufReader.close();
                    reader.close();
                    outputStream.close();
                    listerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void run() {

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
                    p.setTextSize(50);
                    if (inStr != null)
                    {
                        canvas.drawText("服务器返回: " + inStr , x, ((y++)%36+1)*50, p);
                    }
                    // 睡眠时间为1秒
                    socketConnectThread();
                    Thread.sleep(1000);
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
