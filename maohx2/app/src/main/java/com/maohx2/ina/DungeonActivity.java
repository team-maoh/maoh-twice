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
        sound_admin = new SoundAdmin(dungeon_activity);


        dungeon_user_interface.init();



        my_database_admin.addMyDatabase("DungeonDB", "LocalDungeonImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("DungeonDB"), "Dungeon");

        my_database_admin.addMyDatabase("BattleDB", "LocalBattleImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("BattleDB"), "Battle");

        Intent intent = currentActivity.getIntent();
        DUNGEON_KIND dungeon_kind = (DUNGEON_KIND)intent.getSerializableExtra("DungeonKind");

        //by kmhanko TODO GOKI固定にしてあります。外しても動くようにする必要あり(画像が見つからなくて落ちます)
        //dungeon_kind = DUNGEON_KIND.GOKI;

        //魔王と戦闘であるかどうか
        boolean maohFlag = false;

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
            case MAOH:
                my_database_admin.addMyDatabase("GokiDB", "LocalGokiImage.db", 1, "r");
                graphic.loadLocalImages(my_database_admin.getMyDatabase("GokiDB"), "Goki");
                maohFlag = true;
                break;

        }



        my_database_admin.addMyDatabase("soundDB", "soundDB.db", 1, "r");//データベースのコピーしMySQLiteのdbを扱いやすいMyDataBase型にしている
        sound_admin.setDatabase(my_database_admin.getMyDatabase("soundDB"));//扱いやすいやつをセットしている
        sound_admin.loadSoundPack("sound_pack_map");


        battle_user_interface = new BattleUserInterface(global_data.getGlobalConstants(), graphic);
        battle_user_interface.init();

        global_data.getEquipmentInventry().init(battle_user_interface, graphic, 1000,100,1400,508, 10);
        global_data.getGeoInventry().init(battle_user_interface, graphic,1000,100,1400,508, 10);
        global_data.getExpendItemInventry().init(battle_user_interface, graphic,1000,100,1400,508, 10);

        game_system.init(dungeon_user_interface, graphic, sound_admin, my_database_admin, battle_user_interface, dungeon_activity, my_database_admin, activityChange,1, maohFlag);//GameSystem()の初期化 (= GameSystem.javaのinit()を実行)
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
        game_system.update();
        /*
        if(back_ground_flag == false){
            drawBackGround();
            back_ground_flag = true;
        }
        */
        game_system.draw();

//        if(touch_state == TouchState.DOWN) {
//            thread = null;
//            Intent intent = new Intent(dungeon_activity, BattleActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//            dungeon_activity.startActivity(intent);
//        }
    }
}