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
//import java.util.ArrayList;
import java.util.*;
import android.view.MotionEvent;

import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Graphic graphic = new Graphic(this);
        setContentView(graphic);
        graphic.SetImage("neco",0,0);
        //TextView tv = new TextView(this);
        //ReturnPosition return_position = new ReturnPosition();
        //tv.setText(return_position.GetX() + "," + return_position.GetY());
        //setContentView(tv, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

    }
}



class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //commit確認欄(後で消します)
    //senda-commit0827


    //画像読み込み
    Resources res = this.getContext().getResources();
    Bitmap neco = BitmapFactory.decodeResource(res, R.drawable.neco);
    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;

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
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceViewが廃棄されたる時の処理を記述
    }

    /**描画スレッドを実行する。*/
    @Override
    public void run(){
        /*
        Canvas canvas = null;

        try{
            canvas = holder.lockCanvas(null);
            //キャンバスをロック
            synchronized(holder){
                //キャンバスに図形を描画
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(neco, (int)x, (int)y, paint);
            }
        }finally{
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);
                //ロックを解除して描画
            }
        }
        */
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
                break;
        }

        //return super.onTouchEvent(event);
        return true;
    }


}
