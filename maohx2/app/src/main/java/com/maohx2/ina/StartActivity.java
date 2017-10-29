package com.maohx2.ina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Touch.TouchState;


//タイトル画面など
public class StartActivity extends Activity {

    RelativeLayout layout;
    boolean game_system_flag = false;
    StartSurfaceView start_surface_view;
    GlobalData global_data;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        global_data = (GlobalData) this.getApplication();
        global_data.init();

        start_surface_view = new StartSurfaceView(this);
        layout = new RelativeLayout(this);
        layout.addView(start_surface_view);
        setContentView(layout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!game_system_flag) {
            start_surface_view.runGameSystem(start_surface_view.getWidth(), start_surface_view.getHeight());
            game_system_flag = true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("call_destoroy");
    }
}


class StartSurfaceView extends BaseSurfaceView implements SurfaceHolder.Callback, Runnable {

    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;

    double touch_x = 0;
    double touch_y = 0;

    TouchState touch_state = TouchState.AWAY;

    Activity start_activity;
    MyDatabaseAdmin my_database_admin;
    Graphic graphic;


    public StartSurfaceView(Activity _start_activity) {
        super(_start_activity);
        setZOrderOnTop(true);
        start_activity = _start_activity;
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}

    public void runGameSystem(int display_width,int display_height) {
        graphic = new Graphic(display_width, display_height);
        my_database_admin = new MyDatabaseAdmin(start_activity);
        my_database_admin.addMyDatabase("local_image_DB", "local_image.db", 1, "r");
        graphic.init(start_activity, holder, my_database_admin.getMyDatabase("local_image_DB"));

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }


    @Override
    public void run() {

        while (thread != null) {
/*
            Canvas canvas = null;
            canvas = holder.lockCanvas(null);
            if(canvas != null) {

                canvas.drawColor(Color.WHITE);

                Paint paint = new Paint();
                paint.setColor(Color.BLUE);

                canvas.drawCircle(640,400,50,paint);
                holder.unlockCanvasAndPost(canvas);
            }
*/


            graphic.drawBooking("ゴキ",640,390);
            graphic.draw();

            if(touch_state == TouchState.DOWN){
                thread = null;
                Intent intent = new Intent(start_activity, WorldActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                start_activity.startActivity(intent);
            }
        }
    }


    //なぞられた点を記録するリスト
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_state = TouchState.DOWN;
                touch_x = event.getX();
                touch_y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_state = TouchState.MOVE;
                touch_x = event.getX();
                touch_y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touch_state = TouchState.UP;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }
}

