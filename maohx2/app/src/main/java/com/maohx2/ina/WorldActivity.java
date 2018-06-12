package com.maohx2.ina;

/**
 * Created by ina on 2017/10/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Touch.TouchState;


//ダンジョン選択画面
public class WorldActivity extends BaseActivity {

    //RelativeLayout layout;

    //by kmhanko
    WorldSurfaceView worldSurfaceView;
    boolean game_system_flag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        worldSurfaceView = new WorldSurfaceView(this, backSurfaceView);
        layout.addView(worldSurfaceView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(!game_system_flag) {

            worldSurfaceView.runGameSystem();
            game_system_flag = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //worldSurfaceView.graphic.releaseBitmap();
    }

    @Override
    public void finish() {
        worldSurfaceView.my_database_admin.close();
        worldSurfaceView.world_game_system.drawStop();
        worldSurfaceView.graphic.releaseBitmap();
        worldSurfaceView = null;
    }

    @Override
    public String getActivityName() {
        return "WorldActivity";
    }

}


class WorldSurfaceView extends BaseSurfaceView {

    BattleUserInterface map_user_interface;
    WorldGameSystem world_game_system;
    WorldActivity map_activity;
    MyDatabaseAdmin my_database_admin;
    Graphic graphic;
    SoundAdmin soundAdmin;


    public WorldSurfaceView(WorldActivity _map_activity, BackSurfaceView _backSurfaceView) {
        super(_map_activity, _backSurfaceView);
        //super(_map_activity);
        map_activity = _map_activity;

        graphic = new Graphic(map_activity, holder);
        my_database_admin = new MyDatabaseAdmin(map_activity);

        my_database_admin.addMyDatabase("WorldDB", "LocalWorldImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("WorldDB"), "World");

        /*
        my_database_admin.addMyDatabase("ForestDB", "LocalForestImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("ForestDB"), "Forest");

        my_database_admin.addMyDatabase("DungeonDB", "LocalDungeonImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("DungeonDB"), "Dungeon");

        my_database_admin.addMyDatabase("BattleDB", "LocalBattleImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("BattleDB"), "Battle");
        */


        map_user_interface = new BattleUserInterface(global_data.getGlobalConstants(), graphic);
        world_game_system = new WorldGameSystem();


        soundAdmin = new SoundAdmin(map_activity, my_database_admin);

        map_user_interface.init();

        global_data.getEquipmentInventry().init(map_user_interface, graphic, 950,100,1150,708, 7);
        global_data.getExpendItemInventry().init(map_user_interface, graphic,450,100,850,708, 7);
        global_data.getGeoInventry().init(map_user_interface, graphic,1200,100,1600,700, 10);

        global_data.getEquipmentInventry().sortItemData();
        global_data.getEquipmentInventry().sortItemDatabyKind();
        global_data.getGeoInventry().sortItemData();
        global_data.getGeoInventry().sortItemDatabyKind();
        global_data.getExpendItemInventry().sortItemData();

        /*by kmhanko OPであるかどうかはworldGameSystemのinitで判定するよう変更
        openingFlag = true;
        if(global_data.getPlayerStatus().getTutorialInDungeon() == 0) {
            openingFlag = true;
        }
        */

        world_game_system.init(map_user_interface, graphic, my_database_admin, soundAdmin, _map_activity, activityChange);

        /*
        if(openingFlag == true){
            world_game_system.openningInit();
        }*/
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
    public void gameLoop() {

        map_user_interface.updateTouchState(touch_x, touch_y, touch_state);

        /*
        if(openingFlag == true) {
            world_game_system.openningUpdate();
            world_game_system.openningDraw();
        }else{
            world_game_system.update();
            world_game_system.draw();
        }
        */
        world_game_system.update();
        world_game_system.draw();

    }

    //public void setOpenningFlag(boolean _openningFlag) {openingFlag = _openningFlag;}

}
