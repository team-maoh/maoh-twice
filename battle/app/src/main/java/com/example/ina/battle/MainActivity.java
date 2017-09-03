package com.example.ina.battle;

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
//import java.util.ArrayList;
import java.util.*;
import android.view.MotionEvent;
import android.util.Log;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomSurfaceView(this));

    }
}


class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //test
    //画像読み込み
    //Resources res = this.getContext().getResources();
    //Bitmap neco = BitmapFactory.decodeResource(res, R.drawable.neco);
    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;
    //List<Float> pointArray = new ArrayList<Float>();

    double x = 0;
    double y = 0;


    public CustomSurfaceView(Context context) {
        super(context);

        setZOrderOnTop(true);
        /// このビューの描画内容がウインドウの最前面に来るようにする。
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
        // SurfaceViewが変化（画面の大きさ，ピクセルフォーマット）した時のイベントの処理を記述
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
        // SurfaceViewが作成された時の処理（初期画面の描画等）を記述
        //Canvas canvas = holder.lockCanvas();

        // この間にグラフィック描画のコードを記述する。
        //canvas.drawBitmap(neco, 300, 100, paint);
        //holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceViewが廃棄されたる時の処理を記述
    }

    /**描画スレッドを実行する。*/
    @Override
    public void run(){

        Canvas canvas = holder.lockCanvas();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        canvas.drawCircle(1000, 600, 100.0f, paint);
        holder.unlockCanvasAndPost(canvas);

    }



    /**スレッドを初期化して実行する。*/
    private void drawOnThread()
    {
        thread = new Thread(this);
        thread.start();
    }


    //なぞられた点を記録するリスト
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                drawOnThread();
                //描画
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                //1筆書きをクリア
                break;
        }

        //return super.onTouchEvent(event);
        return true;
    }


}