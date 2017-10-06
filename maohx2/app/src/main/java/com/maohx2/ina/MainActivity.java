package com.maohx2.ina;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
//import java.util.ArrayList;
import java.util.*;

import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends BaseActivity {
    CustomSurfaceView custom_surface_view;
    boolean game_system_flag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        custom_surface_view = new CustomSurfaceView(this);
        setContentView(custom_surface_view);
        //setImage();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!game_system_flag) {
            custom_surface_view.runGameSystem(custom_surface_view.getWidth(), custom_surface_view.getHeight());
            custom_surface_view.runThread();
            game_system_flag = true;
        }
    }
}


/*
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // GLSurfaceViewを設定します
        setContentView(new MyGLView(this));
    }

}
*/


class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {





    //画像読み込み
    Resources res = this.getContext().getResources();
    Bitmap neco = BitmapFactory.decodeResource(res, R.drawable.neco);
    Bitmap apple = BitmapFactory.decodeResource(res, R.drawable.apple);
    Bitmap banana = BitmapFactory.decodeResource(res, R.drawable.banana);
    //藤原追加
    Bitmap grape = BitmapFactory.decodeResource(res, R.drawable.grape);
    Bitmap watermelon = BitmapFactory.decodeResource(res, R.drawable.watermelon);

    Bitmap slime = BitmapFactory.decodeResource(res, R.drawable.slime);
    Paint paint = new Paint();
    Point display_size = new Point(0, 0);
    private SurfaceHolder holder;
    private Thread thread;
    Context context;

    int touch = -1;

    double x = 0, y = 0;

    GameSystem game_system;

    public CustomSurfaceView(Context m_context) {
        super(m_context);
        setZOrderOnTop(true);
        /// このビューの描画内容がウインドウの最前面に来るようにする。
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        context = m_context;
        }

    public void runGameSystem(int display_width,int display_height){
        display_size.x = display_width;
        display_size.y = display_height;
        game_system = new GameSystem();
        game_system.init(holder, neco, apple, banana, grape, watermelon, slime,(Activity)context,display_size);//GameSystem()の初期化 (= GameSystem.javaのinit()を実行)
    }

    /*public void runGetDisplaySize(){
        WindowManager window_manager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display = window_manager.getDefaultDisplay();
        display.getSize(display_size);
    }*/

    public void runThread(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // SurfaceViewが変化（画面の大きさ，ピクセルフォーマット）した時のイベントの処理を記述
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

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