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


class WorldSurfaceView extends BaseSurfaceView {

    UserInterface map_user_interface;
    WorldGameSystem world_game_system;
    Activity map_activity;
    MyDatabaseAdmin my_database_admin;
    Graphic graphic;

    public WorldSurfaceView(Activity _map_activity) {
        super(_map_activity);
        map_activity = _map_activity;

        graphic = new Graphic(map_activity, holder);
        map_user_interface = new UserInterface(global_data.getGlobalConstants(), graphic);
        my_database_admin = new MyDatabaseAdmin(map_activity);
        world_game_system = new WorldGameSystem();

        my_database_admin.addMyDatabase("WorldDB", "LocalWorldImage.db", 1, "r");
        graphic.loadLocalImages(my_database_admin.getMyDatabase("WorldDB"), "World");

        my_database_admin.addMyDatabase("GeoSlotDB", "GeoSlotMapDB.db", 1, "r");//データベースのコピーしMySQLiteのdbを扱いやすいMyDataBase型にしている

        map_user_interface.init();
        world_game_system.init(map_user_interface, graphic, my_database_admin);
    }

    @Override
    public void gameLoop() {
        map_user_interface.updateTouchState(touch_x, touch_y, touch_state);
        world_game_system.updata();
        graphic.bookingDrawBitmapName("キノコの森",300,590);
        world_game_system.draw();
/*
        if (touch_state == TouchState.DOWN) {
            /* by kmhanko
            thread = null;
            Intent intent = new Intent(map_activity, DungeonActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            map_activity.startActivity(intent);
            */
        }
        */
    }
}
