package com.maohx2.ina;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

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

        while (thread != null) {
        gameLoop();
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
