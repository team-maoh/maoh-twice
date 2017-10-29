package com.maohx2.kmhanko.dungeonselect;

import java.util.List;
import java.util.ArrayList;

import android.graphics.Canvas;

import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;

/**
 * Created by user on 2017/10/08.
 */

public class DungeonSelectButtonAdmin {

    String table_name = "dungeon_select_button";

    MyDatabase database;

    //GameSystem game_system;

    List<DungeonSelectButton> dungeon_select_button = new ArrayList<DungeonSelectButton>();

    UserInterface map_user_interface;

    public DungeonSelectButtonAdmin() {
    }

    public void init(UserInterface _map_user_interface, MyDatabase _database) {
        //game_system = _game_system;
        map_user_interface = _map_user_interface;
        database = _database;

        loadDungeonSelectButton();
    }

    public void loadDungeonSelectButton(){

        int size = database.getSize(table_name);

        List<String> name = database.getString(table_name, "name");
        List<Integer> x = database.getInt(table_name, "x");
        List<Integer> y = database.getInt(table_name, "y");
        List<Integer> color_r = database.getInt(table_name, "color_r");
        List<Integer> color_g = database.getInt(table_name, "color_g");
        List<Integer> color_b = database.getInt(table_name, "color_b");

        //staticなものを代入
        DungeonSelectButton.setMapUserInterface(map_user_interface);
        //DungeonSelectButton.setGameSystem(game_system);

        //インスタンス化
 for (int i = 0; i < size; i++) {
            dungeon_select_button.add(new DungeonSelectButton());

            //初期化
            dungeon_select_button.get(i).setName(name.get(i));
            dungeon_select_button.get(i).setX(x.get(i));
            dungeon_select_button.get(i).setY(y.get(i));
            dungeon_select_button.get(i).setColorR(color_r.get(i));
            dungeon_select_button.get(i).setColorG(color_g.get(i));
            dungeon_select_button.get(i).setColorB(color_b.get(i));
            dungeon_select_button.get(i).loadTouchID();
        }
    }

    public void draw(Canvas canvas) {
        for (int i = 0; i<dungeon_select_button.size(); i++) {
            dungeon_select_button.get(i).draw(canvas);
        }
    }

    public void update() {
        for (int i = 0; i<dungeon_select_button.size(); i++) {
            dungeon_select_button.get(i).update();
        }
    }


}


//UserInterface使い方
//setCircleTouchUI,setBoxTouchUIでIDをもらう。checkUIで判定

//ボタン類とか、選択肢はい・いいえとはか勝手に作っていいのか、スクロールバーとかは作ってもらうのか。