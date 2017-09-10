package com.example.ina.maohx2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.method.Touch;
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

public class Graphic{

    enum Mode {
        EMPTY,
        IMAGE_AT,
        DRAW,
    }

    Context context;
    static Mode mode = Mode.EMPTY;
    static LAYER layer = LAYER.NOTHING;
    static String name = "";
    static float x = 0;
    static float y = 0;

    Graphic(Context _context){
        context = _context;
    }

    SurfaceView setMoveImage(String _name) {
        mode = Mode.DRAW;
        name = _name;
        return new GraphicSurfaceView(context);
    }

    SurfaceView getImageAt(String _name,float _x,float _y){
        mode = Mode.IMAGE_AT;
        name = _name;
        x = _x;
        y = _y;
        return new GraphicSurfaceView(context);
    }

    SurfaceView getImageAt(String _name,float _x,float _y, LAYER _layer){
        mode = Mode.IMAGE_AT;
        name = _name;
        x = _x;
        y = _y;
        layer = _layer;
        return new GraphicSurfaceView(context);
    }

    public Mode getMode(){
        return mode;
    }

    public String getName(){
        return name;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public LAYER getLayer(){
        return layer;
    }

}

class GraphicSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    //画像読み込み
    Resources res = this.getContext().getResources();
    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;
    //private Canvas canvas;
    Bitmap imageMap;
    Graphic graphic;

    float x = 0;
    float y = 0;

    public GraphicSurfaceView(Context context) {
        super(context);

        /// このビューの描画内容がウインドウの最前面に来るようにする。
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        graphic = new Graphic(context);

        setZ((float)(graphic.getLayer().ordinal()));
    }

    @Override
    public void surfaceChanged (SurfaceHolder holder, int format, int width, int height) {
        // SurfaceViewが変化（画面の大きさ，ピクセルフォーマット）した時のイベントの処理を記述
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        switch (graphic.getMode()){
            case EMPTY:
                break;
            case IMAGE_AT:
                thread = new Thread(this);
                SetImage(graphic.getName(),graphic.getX(),graphic.getY());
                thread.start();
                break;
            case DRAW:
                thread = new Thread(this);
                SetImage(graphic.getName(),0,0);
                thread.start();
                break;

        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // SurfaceViewが廃棄されたる時の処理を記述
    }

    /**描画スレッドを実行する。*/
    @Override
    public void run(){
        Canvas canvas = null;

        try{
            canvas = holder.lockCanvas(null);
            //キャンバスをロック

            synchronized(holder){
                //キャンバスに図形を描画
                canvas.drawColor(Color.argb(100,100,0,0));
                canvas.drawBitmap(imageMap, (int)x, (int)y, paint);
            }
        }finally{
            if(canvas != null) {
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
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

    public SurfaceView SetImage(String name, float _x, float _y){
        x = _x;
        y = _y;
        int resId = getResources().getIdentifier(name,"drawable",this.getContext().getPackageName());
        imageMap = BitmapFactory.decodeResource(res, resId);
        //drawOnThread();
        return this;
    }

}