package com.maohx2.ina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.widget.RelativeLayout;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
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
    BitmapData srime;
    BitmapData bit_srime;
    StartGameSystem start_game_system;
    UserInterface start_user_interface;


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

        paint.setColor(Color.rgb(100,100,0));
        paint.setTextSize(30);
        srime = graphic.searchBitmap("スライム");
        bit_srime = graphic.processTrimmingBitmapData(srime,0,0,46,46);

        start_user_interface = new UserInterface(global_data.getGlobalConstants(), graphic);
        start_user_interface.init();

        start_game_system = new StartGameSystem();
        start_game_system.init(holder, graphic,  start_user_interface, start_activity, my_database_admin);








        //todo:こいつは一番下
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void gameLoop(){
        paint.setColor(Color.BLUE);

        //graphic.bookingDrawBitmapData(graphic.processBitmapData(test_bitmap_data,1,4,255),100,100);


        graphic.bookingDrawBitmapName("スライム",300,590);
        /*
        graphic.bookingDrawText("(300,590)",300,590,paint);
        graphic.bookingDrawBitmapData(bit_srime,640,100);
        graphic.bookingDrawText("(640,100)",640,100,paint);
        graphic.bookingDrawBitmapData(bit_srime,1300,390);
        graphic.bookingDrawText("(1300,390)",1300,390,paint);
        graphic.bookingDrawBitmapData(bit_srime,640,80);
        graphic.bookingDrawText("(640,80)",640,80,paint);
        graphic.bookingDrawBitmapName("スライム",640,120);
        graphic.bookingDrawText("(640,120)",640,120,paint);
        graphic.bookingDrawBitmapName("スライム",800,450);
        graphic.bookingDrawText("(800,450)",800,450,paint);

        graphic.bookingDrawCircle(640,100,10,paint);
        graphic.bookingDrawRect(300,590,310,600,paint);
        graphic.bookingDrawText("0",0,100,paint);
        graphic.bookingDrawText("100",100,100,paint);
        graphic.bookingDrawText("200",200,100,paint);
        graphic.bookingDrawText("300",300,100,paint);
        graphic.bookingDrawText("400",400,100,paint);
        graphic.bookingDrawText("500",500,100,paint);
        graphic.bookingDrawText("600",600,100,paint);
        graphic.bookingDrawText("700",700,100,paint);
        graphic.bookingDrawText("800",800,100,paint);
        graphic.bookingDrawText("900",900,100,paint);
        graphic.bookingDrawText("1000",1000,100,paint);
        graphic.bookingDrawText("1100",1100,100,paint);
        graphic.bookingDrawText("1200",1200,100,paint);
        graphic.bookingDrawText("1300",1300,100,paint);
        graphic.bookingDrawText("1400",1400,100,paint);
        graphic.bookingDrawText("1500",1500,100,paint);
        graphic.bookingDrawText("1600",1600,100,paint);
*/
        start_user_interface.updateTouchState(touch_x, touch_y, touch_state);

        start_game_system.updata();
        start_game_system.draw();



        if(touch_state == TouchState.DOWN){
            thread = null;
            Intent intent = new Intent(start_activity, DungeonActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            start_activity.startActivity(intent);
        }

    }
}