package com.maohx2.kmhanko.dungeonselect;

import java.util.List;
import java.util.ArrayList;

import android.graphics.Canvas;

import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;


/**
 * Created by user on 2017/10/08.
 */

public class DungeonSelectButtonAdmin {
    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";

    String table_name = "dungeon_select_button";

    MyDatabase database;
    Graphic graphic;

    static DungeonSelectManager dungeonSelectManager;

    //GameSystem game_system;

    List<DungeonSelectButton> dungeon_select_button = new ArrayList<DungeonSelectButton>();

    UserInterface map_user_interface;

    public DungeonSelectButtonAdmin() {
    }

    public static void staticInit(DungeonSelectManager _dungeonSelectManager) {
        dungeonSelectManager = _dungeonSelectManager;
    }

    public void init(Graphic _graphic, UserInterface _map_user_interface, MyDatabaseAdmin databaseAdmin) {
        //game_system = _game_system;
        map_user_interface = _map_user_interface;
        graphic = _graphic;
        setDatabase(databaseAdmin);
        loadDungeonSelectButton();
    }

    private void setDatabase(MyDatabaseAdmin databaseAdmin) {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }

    private void loadDungeonSelectButton(){

        int size = database.getSize(table_name);

        List<String> name = database.getString(table_name, "name");
        List<String> image_name = database.getString(table_name, "image_name");
        List<Integer> x = database.getInt(table_name, "x");
        List<Integer> y = database.getInt(table_name, "y");

        //staticなものを代入
        DungeonSelectButton.staticInit(this, map_user_interface);
        //DungeonSelectButton.setGameSystem(game_system);

        //インスタンス化
        for (int i = 0; i < size; i++) {
            dungeon_select_button.add(new DungeonSelectButton());

            //初期化
            dungeon_select_button.get(i).init(graphic);
            dungeon_select_button.get(i).setName(name.get(i));
            dungeon_select_button.get(i).setImageName(image_name.get(i));
            dungeon_select_button.get(i).setX(x.get(i));
            dungeon_select_button.get(i).setY(y.get(i));
            dungeon_select_button.get(i).loadTouchID();

        }
    }

    public void draw() {
        for (int i = 0; i<dungeon_select_button.size(); i++) {
            dungeon_select_button.get(i).draw();
        }
    }

    public void update() {
        for (int i = 0; i<dungeon_select_button.size(); i++) {
            dungeon_select_button.get(i).update();
        }
    }

    public DungeonSelectManager getDungeonSelectManager() {
        return dungeonSelectManager;
    }


}


//UserInterface使い方
//setCircleTouchUI,setBoxTouchUIでIDをもらう。checkUIで判定

//ボタン類とか、選択肢はい・いいえとはか勝手に作っていいのか、スクロールバーとかは作ってもらうのか。