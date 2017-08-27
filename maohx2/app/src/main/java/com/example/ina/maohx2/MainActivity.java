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

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new CustomSurfaceView(this));
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
    List<Float> pointArray = new ArrayList<Float>();


    public CustomSurfaceView(Context context) {
        super(context);


        holder = getHolder();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
        // SurfaceViewが変化（画面の大きさ，ピクセルフォーマット）した時のイベントの処理を記述
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // SurfaceViewが作成された時の処理（初期画面の描画等）を記述
        Canvas canvas = holder.lockCanvas();

        // この間にグラフィック描画のコードを記述する。
        canvas.drawBitmap(neco, 0, 0, paint);
        //paint.setColor(Color.GREEN);
        //canvas.drawRect(0, 0, 50, 50, paint);



        // この間にグラフィック描画のコードを記述する。

        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceViewが廃棄されたる時の処理を記述
    }

    /**描画スレッドを実行する。*/
    @Override
    public void run()
    {
        Canvas canvas = null;
        try{
            canvas = holder.lockCanvas(null);
            //キャンバスをロック
            synchronized(holder){
                //キャンバスに図形を描画
                canvas.drawColor(Color.WHITE);
                int pointCount = pointArray.size();
                for(int i = 0; i + 3 < pointCount; i += 2){
                    try{
                        canvas.drawLine(pointArray.get(i), pointArray.get(i + 1),
                                pointArray.get(i + 2), pointArray.get(i + 3), new Paint());
                    }catch(Exception err){

                    }
                }
            }
        }finally{
            if(canvas != null){
                holder.unlockCanvasAndPost(canvas);
                //ロックを解除して描画
            }
        }
    }



    /**スレッドを初期化して実行する。*/
    private void drawOnThread()
    {
        thread = new Thread(this);
        thread.start();
    }


    //なぞられた点を記録するリスト
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                pointArray.add(event.getX());
                pointArray.add(event.getY());
                drawOnThread();
                //描画
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                pointArray.clear();
                //1筆書きをクリア
                break;
        }

        //return super.onTouchEvent(event);
        return true;
    }


}