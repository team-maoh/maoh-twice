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
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Touch.TouchState;


//ダンジョン選択画面
public class WorldActivity extends BaseActivity {

    //RelativeLayout layout;

    //by kmhanko
    WorldSurfaceView worldSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        worldSurfaceView = new WorldSurfaceView(this, backSurfaceView);
        layout.addView(worldSurfaceView);

    }

}


class WorldSurfaceView extends BaseSurfaceView {

    UserInterface map_user_interface;
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
        map_user_interface = new UserInterface(global_data.getGlobalConstants(), graphic);
        my_database_admin = new MyDatabaseAdmin(map_activity);
        world_game_system = new WorldGameSystem();

        my_database_admin.addMyDatabase("WorldDB", "LocalWorldImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("WorldDB"), "World");

        soundAdmin = new SoundAdmin(map_activity, my_database_admin);

        map_user_interface.init();
        world_game_system.init(map_user_interface, graphic, my_database_admin, soundAdmin, _map_activity);
    }

    @Override
    public void gameLoop() {
        map_user_interface.updateTouchState(touch_x, touch_y, touch_state);
        world_game_system.updata();
        world_game_system.draw();
/*

        if (touch_state == TouchState.DOWN) {
            thread = null;
            Intent intent = new Intent(map_activity, DungeonActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            map_activity.startActivity(intent);
        }
*/
    }

}
