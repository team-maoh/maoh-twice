package com.maohx2.kmhanko.geonode;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/17.
 */

public class GeoWorldMap {
    static final String DB_NAME = "dungeonselectDB";
    static final String DB_ASSET = "dungeonselectDB.db";
    static final String TABLE_NAME = "dungeon_select_button";

    MyDatabaseAdmin databaseAdmin;
    Graphic graphic;
    UserInterface userInterface;

    //TODO:ListBoxになる予定
    List<GeoSlotMapButton> geoSlotMapButton = new ArrayList<GeoSlotMapButton>();

    public GeoWorldMap(Graphic _graphic, UserInterface _userInterface, MyDatabaseAdmin _databaseAdmin) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;
        addDatabase();
        this.loadDungeonSelectDatabase();
    }

    public void update() {
        for (int i = 0; i < geoSlotMapButton.size(); i++) {
            geoSlotMapButton.get(i).update();
        }
    }

    public void draw() {
        for (int i = 0; i < geoSlotMapButton.size(); i++) {
            geoSlotMapButton.get(i).draw();
        }
    }


    public void addDatabase() {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
    }

    private void loadDungeonSelectDatabase(){
        MyDatabase database = databaseAdmin.getMyDatabase(DB_NAME);

        int size = database.getSize(TABLE_NAME);

        List<String> name = database.getString(TABLE_NAME, "name");
        List<String> image_name = database.getString(TABLE_NAME, "image_name");
        List<Integer> x = database.getInt(TABLE_NAME, "x");
        List<Integer> y = database.getInt(TABLE_NAME, "y");

        GeoSlotMapButton.staticInit(graphic,userInterface);

        //インスタンス
        for (int i = 0; i < size; i++) {
            geoSlotMapButton.add(new GeoSlotMapButton());

            //初期化
            geoSlotMapButton.get(i).setName(name.get(i));
            geoSlotMapButton.get(i).setImageName(image_name.get(i));
            geoSlotMapButton.get(i).setX(x.get(i));
            geoSlotMapButton.get(i).setY(y.get(i));
            geoSlotMapButton.get(i).loadTouchID();

        }
    }

}
