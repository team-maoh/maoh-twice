package com.maohx2.ina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RelativeLayout;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.CircleImagePlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import static com.maohx2.ina.Constants.Touch.TouchState;


//タイトル画面など
public class StartActivity extends Activity {

    RelativeLayout layout;
    boolean game_system_flag = false;
    StartSurfaceView start_surface_view;
    GlobalData global_data;

    BackSurfaceView backSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backSurfaceView = new BackSurfaceView(this);
        start_surface_view = new StartSurfaceView(this, backSurfaceView);
        layout = new RelativeLayout(this);
        layout.addView(backSurfaceView);
        layout.addView(start_surface_view);
        setContentView(layout);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(!game_system_flag) {
            global_data = (GlobalData) this.getApplication();
            global_data.init(start_surface_view.getWidth(), start_surface_view.getHeight());
            start_surface_view.runGameSystem();
            game_system_flag = true;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("call_destoroy");
    }
}

class BackSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    Paint paint = new Paint();
    SurfaceHolder holder;

    public BackSurfaceView(Activity _currentActivity) {
        super(_currentActivity);
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        //paint.setColor(Color.BLUE);
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}


    @Override
    public void run() {}

    public void gameLoop(){}

    public void stopThread(){}

    public void drawBackGround(ImageContext drawImageContext){

        Canvas canvas = null;
        canvas = holder.lockCanvas();

        if(canvas != null) {
            //canvas.drawColor(Color.RED);
            canvas.drawBitmap(drawImageContext.getBitmapData().getBitmap(), drawImageContext.getMatrix(), paint);
            //canvas.drawBitmap();
            holder.unlockCanvasAndPost(canvas);
        }
    }

}


class StartSurfaceView extends BaseSurfaceView {

    Activity start_activity;
    MyDatabaseAdmin my_database_admin;
    Graphic graphic;
    StartGameSystem start_game_system;
    BattleUserInterface start_user_interface;

    PlateGroup<CircleImagePlate> image_list;
    BackSurfaceView backSurfaceView;


    public StartSurfaceView(Activity _start_activity, BackSurfaceView _backSurfaceView) {
        super(_start_activity);
        start_activity = _start_activity;
        backSurfaceView = _backSurfaceView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}

    public void runGameSystem() {

        graphic = new Graphic(start_activity, holder);
        my_database_admin = new MyDatabaseAdmin(start_activity);

        my_database_admin.addMyDatabase("StartDB", "LocalStartImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("StartDB"), "Start");

        paint.setColor(Color.rgb(100, 100, 0));
        paint.setTextSize(30);

        start_user_interface = new BattleUserInterface(global_data.getGlobalConstants(), graphic);
        start_user_interface.init();

        start_game_system = new StartGameSystem();
        start_game_system.init(holder, graphic, start_user_interface, start_activity, my_database_admin);

        ImageContext backImageContext = graphic.makeImageContext(graphic.searchBitmap("e51-0"),0,0,true);

        backSurfaceView.drawBackGround(backImageContext);

        //todo:こいつは一番下
        thread = new Thread(this);
        thread.start();

        activityChange = new ActivityChange(this, currentActivity);
    }


    @Override
    public void gameLoop(){
        paint.setColor(Color.BLUE);

        if(touch_state == TouchState.DOWN){

            //activityChange.toDungeonActivity();
            activityChange.toWorldActivity();
        }

        start_user_interface.updateTouchState(touch_x, touch_y, touch_state);
        start_game_system.updata();
        start_game_system.draw();
    }
}