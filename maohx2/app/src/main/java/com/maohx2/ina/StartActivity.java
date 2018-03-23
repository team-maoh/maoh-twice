package com.maohx2.ina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        start_surface_view = new StartSurfaceView(this);
        layout = new RelativeLayout(this);
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


class StartSurfaceView extends BaseSurfaceView {

    Activity start_activity;
    MyDatabaseAdmin my_database_admin;
    Graphic graphic;
    StartGameSystem start_game_system;
    BattleUserInterface start_user_interface;

    PlateGroup<CircleImagePlate> image_list;


    public StartSurfaceView(Activity _start_activity) {
        super(_start_activity);
        start_activity = _start_activity;
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


        //todo:こいつは一番下
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void gameLoop(){
        paint.setColor(Color.BLUE);


        if(touch_state == TouchState.DOWN){
            thread = null;
            Intent intent = new Intent(start_activity, WorldActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            start_activity.startActivity(intent);
        }
/*
        if(touch_state == TouchState.DOWN){
            thread = null;
            Intent intent = new Intent(start_activity, BattleActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            start_activity.startActivity(intent);
        }
*/
        start_user_interface.updateTouchState(touch_x, touch_y, touch_state);
        start_game_system.updata();
        start_game_system.draw();


    }
}