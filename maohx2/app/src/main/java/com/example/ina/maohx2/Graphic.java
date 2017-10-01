package com.example.ina.maohx2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ryomasenda on 2017/09/03.
 */


/*
public class Graphic{

    SurfaceView setImage(Context context){
        return new BaseSurfaceView(context);
    }

}
*/

class BitmapData{
    int id;
    Context context;
    float x;
    float y;
    String filename;
    Bitmap bitmap;
    static Resources res;

    public BitmapData(){
    }

    public void init(Context _context){
        context = _context;
        res = context.getResources();
    }

    void setBitmapData(float _x, float _y, String _filename){
        x = _x;
        y = _y;
        filename = _filename;
        int resId = res.getIdentifier(filename,"drawable",context.getPackageName());
        bitmap = BitmapFactory.decodeResource(res, resId);
    }

    Bitmap getBitmap(){
        return bitmap;
    }

}

public class Graphic extends SurfaceView implements SurfaceHolder.Callback {

    Paint paint = new Paint();
    private SurfaceHolder holder;
    //private Thread thread;
    BitmapData[] bitmapDatas = new BitmapData[1024];
    int bitmapMax = 0;

    public boolean created = false;

    float x = 0;
    float y = 0;

    public Graphic(Context context) {
        super(context);
        for(int i = 0; i<1024;i++){
            bitmapDatas[i] = new BitmapData();
        }

        bitmapDatas[0].init(context);
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
        //thread呼び出し
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceViewが廃棄されたる時の処理を記述
    }

    public void draw() {
        while (!created);
        Canvas canvas = null;
        canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        for(int i = 0; i<bitmapMax; i++){
            canvas.drawBitmap(bitmapDatas[i].getBitmap(), 100, 100, paint);
        }
        holder.unlockCanvasAndPost(canvas);
    }

    public void setImage(String name, float x, float y){
        bitmapDatas[bitmapMax].setBitmapData(x,y,name);
        bitmapMax = bitmapMax + 1;
    }

}
