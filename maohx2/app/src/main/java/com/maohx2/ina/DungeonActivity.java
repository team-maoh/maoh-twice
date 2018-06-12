package com.maohx2.ina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.sound.SoundAdmin;
import com.maohx2.ina.Constants.DungeonKind.*;

import static com.maohx2.ina.Constants.DungeonKind.DUNGEON_KIND.CHESS;
import static com.maohx2.ina.Constants.Touch.TouchState;



public class DungeonActivity extends BaseActivity {

    DungeonSurfaceView dungeon_surface_view;
    boolean game_system_flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dungeon_surface_view = new DungeonSurfaceView(this, backSurfaceView);
        layout.addView(dungeon_surface_view);

    }

    //ここでメインループを止める
    @Override
    protected void onStop(){
        super.onStop();
        System.out.println("onStop!!");
    }

    //ここでメインループを再開する
    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(!game_system_flag) {

            dungeon_surface_view.runGameSystem();
            game_system_flag = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //dungeon_surface_view.graphic.releaseBitmap();
    }

    @Override
    public void finish() {
        dungeon_surface_view.my_database_admin.close();
        dungeon_surface_view = null;
    }

    @Override
    public String getActivityName() {
        return "WorldActivity";
    }
}

class DungeonSurfaceView extends BaseSurfaceView{

    DungeonUserInterface dungeon_user_interface;
    SoundAdmin sound_admin;
    MyDatabaseAdmin my_database_admin;
    DungeonGameSystem game_system;
    Graphic graphic;
    Activity dungeon_activity;
    BattleUserInterface battle_user_interface;


    public DungeonSurfaceView(Activity _dungeon_activity, BackSurfaceView _backSurfaceView) {
        super(_dungeon_activity, _backSurfaceView);
        dungeon_activity = _dungeon_activity;


        graphic = new Graphic(dungeon_activity, holder);
        dungeon_user_interface = new DungeonUserInterface(((GlobalData) dungeon_activity.getApplication()).getGlobalConstants(), graphic);
        my_database_admin = new MyDatabaseAdmin(dungeon_activity);
        game_system = new DungeonGameSystem();
        sound_admin = new SoundAdmin(dungeon_activity, my_database_admin);


        dungeon_user_interface.init();




        my_database_admin.addMyDatabase("DungeonDB", "LocalDungeonImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("DungeonDB"), "Dungeon");

        my_database_admin.addMyDatabase("BattleDB", "LocalBattleImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("BattleDB"), "Battle");

        Intent intent = currentActivity.getIntent();
        DUNGEON_KIND dungeon_kind = (DUNGEON_KIND)intent.getSerializableExtra("DungeonKind");

        switch (dungeon_kind){
            case CHESS:
                my_database_admin.addMyDatabase("ChessDB", "LocalChessImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("ChessDB"), "Chess");
                break;

            case DRAGON:
                my_database_admin.addMyDatabase("DragonDB", "LocalDragonImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("DragonDB"), "Dragon");
                break;

            case FOREST:
                my_database_admin.addMyDatabase("ForestDB", "LocalForestImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("ForestDB"), "Forest");
                break;

            case GOKI:
                my_database_admin.addMyDatabase("GokiDB", "LocalGokiImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("GokiDB"), "Goki");
                break;

            case HAUNTED:
                my_database_admin.addMyDatabase("HauntedDB", "LocalHauntedImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("HauntedDB"), "Haunted");
                break;
            case SEA:
                my_database_admin.addMyDatabase("SeaDB", "LocalSeaImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("SeaDB"), "Sea");
                break;
            case SWAMP:
                my_database_admin.addMyDatabase("SwampDB", "LocalSwampImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("SwampDB"), "Swamp");
                break;
            case LAVA:
                my_database_admin.addMyDatabase("LavaDB", "LocalLavaImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("LavaDB"), "Lava");
                break;
            case MAOH:
                my_database_admin.addMyDatabase("GokiDB", "LocalGokiImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("GokiDB"), "Goki");
                break;
            case OPENING:
                my_database_admin.addMyDatabase("ForestDB", "LocalForestImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("ForestDB"), "Forest");
                openingFlag = true;
                break;

        }

        sound_admin.loadSoundPack("basic");


        battle_user_interface = new BattleUserInterface(global_data.getGlobalConstants(), graphic);
        battle_user_interface.init();

        global_data.getEquipmentInventry().init(battle_user_interface, graphic, 1000,100,1400,508, 10);
        global_data.getGeoInventry().init(battle_user_interface, graphic,1200,100,1600,700, 10);
        global_data.getExpendItemInventry().init(battle_user_interface, graphic,200,100,600,508, 10);


        game_system.init(dungeon_user_interface, graphic, sound_admin, my_database_admin, battle_user_interface, dungeon_activity, my_database_admin, activityChange, dungeon_kind);//GameSystem()の初期化 (= GameSystem.javaのinit()を実行)

        if(openingFlag == true){
            game_system.openningInit();
        }

    }

    public void runGameSystem() {
        thread = new Thread(this);
        thread.start();
    }

    public void drawBackGround(){
        ImageContext backImageContext = graphic.makeImageContext(graphic.searchBitmap("e51-0"),0,0,true);
        backSurfaceView.drawBackGround(backImageContext);
    }


    @Override
    public void gameLoop(){

        dungeon_user_interface.updateTouchState(touch_x, touch_y, touch_state);
        battle_user_interface.updateTouchState(touch_x, touch_y, touch_state);

        if(openingFlag == true) {
            game_system.openningUpdate();
            game_system.openningDraw();
        }else{
            game_system.update();
            game_system.draw();
        }
    }
}