package com.maohx2.ina;

/**
 * Created by ina on 2017/10/15.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import static com.maohx2.ina.Constants.Touch.TouchState;

//ダンジョン選択画面
public class WorldActivity extends Activity {

    RelativeLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new RelativeLayout(this);
        layout.setBackgroundColor(Color.WHITE);
        layout.addView(new WorldSurfaceView(this));
        setContentView(layout);
    }




}


class WorldSurfaceView extends BaseSurfaceView implements SurfaceHolder.Callback, Runnable {

    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;

    double touch_x = 0;
    double touch_y = 0;

    TouchState touch_state = TouchState.AWAY;


    UserInterface map_user_interface;
    MapGameSystem map_game_system;
    Activity map_activity;

    MyDatabaseAdmin my_database_admin;

    public WorldSurfaceView(Activity _map_activity) {
        super(_map_activity);
        setZOrderOnTop(true);

        map_activity = _map_activity;

        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);


        my_database_admin = new MyDatabaseAdmin(map_activity);
        my_database_admin.addMyDatabase("GeoSlotDB", "GeoSlotMapDB.db", 1, "r");//データベースのコピーしMySQLiteのdbを扱いやすいMyDataBase型にしている

        map_user_interface = new UserInterface();
        map_user_interface.init();
        map_game_system = new MapGameSystem();
        map_game_system.init(holder, map_user_interface, map_activity, my_database_admin);
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

        while (true) {
            map_user_interface.updateTouchState(touch_x, touch_y, touch_state);
            map_game_system.updata();
            map_game_system.draw();

            if(touch_state == TouchState.DOWN) {
                Intent intent = new Intent(map_activity, DungeonActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                map_activity.startActivity(intent);
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