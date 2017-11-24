package com.maohx2.ina;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;



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


class BattleSurfaceView extends BaseSurfaceView{

    BattleUserInterface battle_user_interface;
    BattleGameSystem game_system;
    MyDatabaseAdmin my_database_admin;
    Graphic graphic;

    public BattleSurfaceView(Activity battle_activity) {
        super(battle_activity);


        my_database_admin = new MyDatabaseAdmin(battle_activity);
        my_database_admin.addMyDatabase("unit_data_DB", "unit_data.db", 1, "r");


        graphic = new Graphic(battle_activity, holder);

        battle_user_interface = new BattleUserInterface(global_data.getGlobalConstants());
        battle_user_interface.init();

        game_system = new BattleGameSystem();
        game_system.init(holder, graphic,  battle_user_interface, battle_activity);

    }

    @Override
    public void gameLoop() {
        battle_user_interface.updateTouchState(touch_x, touch_y, touch_state);
        game_system.updata();
        game_system.draw();
    }
}

