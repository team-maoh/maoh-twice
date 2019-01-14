package com.maohx2.ina;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.maohx2.ina.Draw.ImageContext;

/**
 * Created by ina on 2018/05/06.
 */

public class BackSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    Paint paint = new Paint();
    SurfaceHolder holder;

    public BackSurfaceView(Activity _currentActivity) {
        super(_currentActivity);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        //paint.setColor(Color.BLUE);

        //音量調整ボタンを使用できるようにする
        _currentActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}


    @Override
    public void run() {}

    public void gameLoop(){}

    public void stopThread(){}

    public void drawBackGround(ImageContext drawImageContext){

        Canvas canvas = null;
        canvas = holder.lockCanvas();

        if(canvas != null) {
            canvas.drawBitmap(drawImageContext.getBitmapData().getBitmap(), drawImageContext.getMatrix(), paint);
            holder.unlockCanvasAndPost(canvas);
        }
    }
}
