package com.maohx2.ina;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ina on 2017/09/20.
 */

public class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    Paint paint = new Paint();
    SurfaceHolder holder;
    Thread thread;

    double touch_x = 0;
    double touch_y = 0;

    Constants.Touch.TouchState touch_state = Constants.Touch.TouchState.AWAY;
    GlobalData global_data;

    long error = 0;
    int fps = 30;
    long idealSleep = (1000 << 16) / fps;
    long oldTime;
    long newTime = System.currentTimeMillis() << 16;

    public BaseSurfaceView(Activity activity) {
        super(activity);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        global_data = (GlobalData) activity.getApplication();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}


    @Override
    public void run() {

        long old_frame = System.currentTimeMillis();

        while (thread != null) {
            oldTime = newTime;
            gameLoop();
            newTime = System.currentTimeMillis() << 16;
            long sleepTime = idealSleep - (newTime - oldTime) - error; // 休止できる時間
            if (sleepTime < 0x20000) sleepTime = 0x20000; // 最低でも2msは休止
            oldTime = newTime;
            try {
                thread.sleep(sleepTime >> 16); // 休止
            }catch (InterruptedException e){
                System.out.println("%☆イナガキ：fps固定処理で例外が渡されました(スリープが負の数など)");
            }

            newTime = System.currentTimeMillis() << 16;
            error = newTime - oldTime - sleepTime; // 休止時間の誤差

            final long now_frame = System.currentTimeMillis();
            final double fps = 1000.0 / (now_frame - old_frame);
            old_frame = now_frame;
            System.out.println(fps);

        }
    }

    public void gameLoop(){}


    //なぞられた点を記録するリスト
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_state = Constants.Touch.TouchState.DOWN;
                touch_x = event.getX();
                touch_y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_state = Constants.Touch.TouchState.MOVE;
                touch_x = event.getX();
                touch_y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touch_state = Constants.Touch.TouchState.UP;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}
