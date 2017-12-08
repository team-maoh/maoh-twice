package com.maohx2.kmhanko.dungeonselect;

import java.util.List;
import java.util.ArrayList;

import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

/**
 * Created by user on 2017/10/08.
 */

//将来的には大幅に書き換えるか消えるかする

public class LoopSelectButtonAdmin {
    static final String DB_NAME = "loopselectDB";
    static final String DB_ASSET = "loopselectDB.db";

    String table_name = "loop_select_button";

    MyDatabase database;

    Graphic graphic;

    List<LoopSelectButton> loop_select_button = new ArrayList<LoopSelectButton>();

    UserInterface map_user_interface;
    //GameSystem game_system;

    public LoopSelectButtonAdmin() {
    }

    public void init(Graphic _graphic, UserInterface _map_user_interface, MyDatabaseAdmin databaseAdmin) {
        map_user_interface = _map_user_interface;
        graphic = _graphic;
        setDatabase(databaseAdmin);
        loadLoopSelectButton();
    }

    public void setDatabase(MyDatabaseAdmin databaseAdmin) {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }

    public void loadLoopSelectButton(){

        int size = database.getSize(table_name);

        List<String> name = database.getString(table_name, "name");
        List<Integer> x = database.getInt(table_name, "x");
        List<Integer> y = database.getInt(table_name, "y");
        List<Integer> size_x = database.getInt(table_name, "size_x");
        List<Integer> size_y = database.getInt(table_name, "size_y");
        List<Integer> color_r = database.getInt(table_name, "color_r");
        List<Integer> color_g = database.getInt(table_name, "color_g");
        List<Integer> color_b = database.getInt(table_name, "color_b");

        //staticなものを代入
        LoopSelectButton.setMapUserInterface(map_user_interface);
        //LoopSelectButton.setGameSystem(game_system);

        //インスタンス化
        for (int i = 0; i < size; i++) {
            loop_select_button.add(new LoopSelectButton());

            //初期化
            loop_select_button.get(i).setName(name.get(i));
            loop_select_button.get(i).setX(x.get(i));
            loop_select_button.get(i).setY(y.get(i));
            loop_select_button.get(i).setSizeX(size_x.get(i));
            loop_select_button.get(i).setSizeY(size_y.get(i));
            loop_select_button.get(i).setColorR(color_r.get(i));
            loop_select_button.get(i).setColorG(color_g.get(i));
            loop_select_button.get(i).setColorB(color_b.get(i));
            loop_select_button.get(i).loadTouchID();
        }
    }

    public void draw() {
        for (int i = 0; i<loop_select_button.size(); i++) {
            loop_select_button.get(i).draw();
        }
    }

    public void update() {
        for (int i = 0; i<loop_select_button.size(); i++) {
            loop_select_button.get(i).update();
        }
    }


}