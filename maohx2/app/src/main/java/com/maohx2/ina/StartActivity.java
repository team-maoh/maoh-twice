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
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.CircleImagePlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.Saver.ExpendItemInventrySaver;
import com.maohx2.kmhanko.Saver.GeoInventrySaver;
import com.maohx2.kmhanko.Talking.TalkSaveDataAdmin;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.music.MusicAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;
import static com.maohx2.ina.Constants.Touch.TouchState;


//memo

//バグ
//発掘ジオの偏りバグ
//テキストを全部流す作業
//ゲームクリアまで行けるのか

//プレイyー半透明
//3つダンジョンクリアして魔王
//ダンジョンオールクリア

//改善点

//音量調節
//使用回数、色を変えるなどしてわかりやすくする
//ダンジョンでのポーション使用時、リタイア時、ゲームオーバー時などにメッセージを表示する
//階層移動時に効果音を鳴らす
//緑色のEが、背景の大陸の緑と同化してわかりにくい
//音楽のループタイミングがおかしいものがある(ワールドマップなど)

//タイトル画面など
public class StartActivity extends BaseActivity {

    boolean game_system_flag = false;
    StartSurfaceView start_surface_view;
    GlobalData global_data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        start_surface_view = new StartSurfaceView(this, backSurfaceView);
        //layout.addView(backSurfaceView);
        layout.addView(start_surface_view);
        
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
        start_surface_view = null;
        //start_surface_view.graphic.releaseBitmap();
    }

    @Override
    public String getActivityName() {
        return "StartActivity";
    }


    @Override
    public void finish() {
        super.finish();
        start_surface_view.my_database_admin.close();
        start_surface_view.release();
        start_surface_view = null;
    }
}


class StartSurfaceView extends BaseSurfaceView {

    StartActivity start_activity;
    MyDatabaseAdmin my_database_admin;
    Graphic graphic;
    StartGameSystem start_game_system;
    BattleUserInterface start_user_interface;

    boolean openingFlag;

    //PlateGroup<CircleImagePlate> image_list;

    //MusicAdmin musicAdmin;
    SoundAdmin soundAdmin;

    @Override
    public void release() {
        System.out.println("takanoRelease : StartSurfaceview");
        super.release();
        start_game_system.drawStop();
        start_game_system.updateStop();
        start_game_system.release();
        my_database_admin.release();
        start_user_interface.release();
        graphic.releaseBitmap();
    }

    public StartSurfaceView(StartActivity _start_activity, BackSurfaceView _backSurfaceView) {
        super(_start_activity, _backSurfaceView);
        start_activity = _start_activity;

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {}

    public void runGameSystem() {

        graphic = new Graphic(start_activity, holder);
        my_database_admin = new MyDatabaseAdmin(start_activity);

        my_database_admin.addMyDatabase("StartDB", "LocalStartImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("StartDB"), "Start");

        soundAdmin = new SoundAdmin(start_activity, my_database_admin);
        soundAdmin.loadSoundPack("opening");

        //MusicAdmin musicAdmin = new MusicAdmin(currentActivity, my_database_admin);
        //musicAdmin.setTableName("music_pack");
        //musicAdmin.loadMusic("title", true);


        start_user_interface = new BattleUserInterface(global_data.getGlobalConstants(), graphic);
        start_user_interface.init();




        activityChange = new ActivityChange(this, currentActivity);


        global_data.getEquipmentInventrySaver().init(graphic);
        global_data.getEquipmentInventry().init(start_user_interface, graphic, 1000,100,1400,508, 10);
        global_data.getEquipmentInventry().load();
        global_data.getEquipmentInventrySaver().setInventry(global_data.getEquipmentInventry());

        global_data.getGeoInventrySaver().init(graphic);

        GeoInventrySaver geoInventrySaver = global_data.getGeoInventrySaver();
        InventryS geoInventry = global_data.getGeoInventry();
        ExpendItemInventrySaver expendItemInventrySaver = global_data.getExpendItemInventrySaver();
        InventryS expendItemInventry = global_data.getExpendItemInventry();




        global_data.getItemDataAdminManager().init(my_database_admin, graphic);


        geoInventry.init(start_user_interface, graphic,1000,100,1400,508, 10);
        geoInventry.load();
        geoInventrySaver.setInventry(geoInventry);

        expendItemInventry.init(start_user_interface, graphic,200,100,600,508, 10);
        expendItemInventrySaver.init(global_data.getItemDataAdminManager());
        expendItemInventry.load();
        expendItemInventrySaver.setInventry(expendItemInventry);


        start_game_system = new StartGameSystem();
        start_game_system.init(holder, graphic, start_user_interface, start_activity, my_database_admin);

        /*
        musicAdmin = new MusicAdmin(currentActivity);
        my_database_admin.addMyDatabase("musicDB", "musicDB.db", 1, "r");
        musicAdmin.setTableName("music_pack");

        musicAdmin.setDatabase(my_database_admin.getMyDatabase("musicDB"));

        musicAdmin.loadMusic("title", true);
        */

        //global_data.getPlayerStatus().setTutorialInDungeon(1);

        TalkSaveDataAdmin talkSaveDataAdmin = new TalkSaveDataAdmin(my_database_admin);
        talkSaveDataAdmin.load();
        openingFlag = !talkSaveDataAdmin.getTalkFlagByName("Opening_in_dungeon"); //openingFlag = true ならOpeningを実行

        //デバッグ用
        //openingFlag = false;


        //todo:こいつは一番下
        thread = new Thread(this);
        thread.start();


    }

    public void drawBackGround(){
        ImageContext backImageContext = graphic.makeImageContext(graphic.searchBitmap("e51-0"),0,0,true);
        backSurfaceView.drawBackGround(backImageContext);
    }

    int downCount = 0;
    int touchWaitcount = 0;

    @Override
    public void gameLoop(){

        start_user_interface.updateTouchState(touch_x, touch_y, touch_state);

       if(touch_state == TouchState.DOWN && touchWaitcount > 15){
            downCount++;
           touchWaitcount = 0;
       }
        touchWaitcount++;

       //デバッグ用OP切り替えは216行目へ移動した

        switch (downCount) {
            case 0:
                start_game_system.openingUpdate();
                //start_game_system.openingdraw();
                break;
            case 1:
                start_game_system.updata();
                start_game_system.draw();
                break;
            case 2:
                soundAdmin.play("opening01");
                //初めてゲームを開始下ならDungeonへ。そうでないならワールドへ。
                if (!openingFlag) {
                    activityChange.toWorldActivity();
                } else {
                    activityChange.toWorldActivity();
                    //activityChange.toDungeonActivity(Constants.DungeonKind.DUNGEON_KIND.OPENING);
                }
                break;
        }
        activityChange.toChangeActivity();
    }

    public void setDownCount(int _downCount) {
        downCount = _downCount;
    }
}