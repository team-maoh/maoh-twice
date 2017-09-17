package com.example.ina.maohx2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
//import java.util.ArrayList;
import java.util.*;

import android.view.MotionEvent;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomSurfaceView(this));

        //setImage();
    }
}

class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //画像読み込み
    Resources res = this.getContext().getResources();
    Bitmap neco = BitmapFactory.decodeResource(res, R.drawable.neco);
    Bitmap apple = BitmapFactory.decodeResource(res, R.drawable.apple);
    Bitmap slime = BitmapFactory.decodeResource(res, R.drawable.slime);
    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;

    int touch = -1;

    double x = 0, y = 0;

    GameSystem game_system;

    public CustomSurfaceView(Context context) {
        super(context);
        setZOrderOnTop(true);
        /// このビューの描画内容がウインドウの最前面に来るようにする。
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);

        game_system = new GameSystem();
        game_system.init(holder, neco, apple, slime);//GameSystem()の初期化 (= GameSystem.javaのinit()を実行)

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // SurfaceViewが変化（画面の大きさ，ピクセルフォーマット）した時のイベントの処理を記述
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceViewが廃棄されたる時の処理を記述
    }

    /*
     * 描画スレッドを実行する。
     */
    @Override
    public void run() {
        while (thread!=null) {

            game_system.update(x, y, touch);
            game_system.draw(x, y, touch);
        }
    }

    /*
     * スレッドを初期化して実行する。
     */
/*
    private void drawOnThread() {
        thread = new Thread(this);
        thread.start();
    }
*/

    //目標地点を記録するリスト
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            //タッチした座標を目標座標として格納
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                //drawOnThread();

                touch = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                //描画
                touch = 1;
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                touch = 2;
                break;
            case MotionEvent.ACTION_CANCEL:
                x = event.getX();
                y = event.getY();
                touch = 3;
                break;
        }

        //return super.onTouchEvent(event);
        return true;
    }
}