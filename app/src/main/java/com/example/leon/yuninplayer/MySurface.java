package com.example.leon.yuninplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback ,Runnable{
    private SurfaceHolder holder;
    private boolean isRunning = true;
    private Canvas canvas = null;

    public MySurface(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
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
