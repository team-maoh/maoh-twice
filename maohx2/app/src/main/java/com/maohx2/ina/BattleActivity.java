package com.maohx2.ina;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.ina.UI.MapUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Touch.TouchState;




/**
 * Created by ina on 2017/10/15.
 */
public class BattleActivity extends Activity {

    RelativeLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new RelativeLayout(this);
        layout.addView(new BattleSurfaceView(this));
        setContentView(layout);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("call_destoroy");
    }
}


class BattleSurfaceView extends BaseSurfaceView implements SurfaceHolder.Callback, Runnable {

    Paint paint = new Paint();
    private SurfaceHolder holder;
    private Thread thread;

    double touch_x = 0;
    double touch_y = 0;

    GlobalData global_data;
    TouchState touch_state = TouchState.AWAY;

    BattleUserInterface battle_user_interface;
    BattleGameSystem game_system;

    MyDatabaseAdmin my_database_admin;
    Graphic graphic;

    public BattleSurfaceView(Activity battle_activity) {
        super(battle_activity);
        setZOrderOnTop(true);

        holder = getHolder();
        holder.addCallback(this);

        global_data = (GlobalData) battle_activity.getApplication();

        //ここにユニットデータのロードが追加される！
        my_database_admin = new MyDatabaseAdmin(battle_activity);
        my_database_admin.addMyDatabase("local_image_DB", "local_image.db", 1, "r");

        graphic = new Graphic(1280, 800, global_data.getGlobalBitmapDataAdmin());
        graphic.init(battle_activity, holder, my_database_admin.getMyDatabase("local_image_DB"));



        paint.setColor(Color.BLUE);

        battle_user_interface = new BattleUserInterface();
        battle_user_interface.init();

        game_system = new BattleGameSystem();
        game_system.init(holder, graphic,  battle_user_interface, battle_activity);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }


    @Override
    public void run() {


        while (true) {
            battle_user_interface.updateTouchState(touch_x, touch_y, touch_state);
            game_system.updata();
            game_system.draw();
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

