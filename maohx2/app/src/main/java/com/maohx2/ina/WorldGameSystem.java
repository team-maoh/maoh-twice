package com.maohx2.ina;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.Text.TextBoxAdmin;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import android.graphics.Paint;

/**
 * Created by ina on 2017/10/01.
 */

public class WorldGameSystem {

    SurfaceHolder holder;
    Paint paint = new Paint();
    Canvas canvas;
    TextBoxAdmin text_box_admin;
    ListBoxAdmin list_box_admin;
    GeoSlotAdmin geo_slot_admin;
    GeoSlotAdminManager geo_slot_admin_manager;
    MyDatabaseAdmin my_data_base_admin;
    Graphic graphic;


    public void init(UserInterface map_user_interface, Graphic _graphic, MyDatabaseAdmin _my_data_base_admin) {
        graphic = _graphic;
        my_data_base_admin = _my_data_base_admin;


        text_box_admin = new TextBoxAdmin();
        list_box_admin = new ListBoxAdmin();
        geo_slot_admin_manager = new GeoSlotAdminManager();
        //geo_slot_admin = new GeoSlotAdmin();


        text_box_admin.init(map_user_interface);
        list_box_admin.init(map_user_interface, graphic);
        geo_slot_admin_manager.init(graphic, map_user_interface,my_data_base_admin);
        //geo_slot_admin.init(map_user_interface, map_activity);


        geo_slot_admin_manager.setActiveGeoSlotAdmin("Test");

        canvas = null;
    }


    public void updata() {
        text_box_admin.update();
        list_box_admin.update();
//        geo_slot_admin_manager.update();
//        map_user_interface.update();
    }


    public void draw() {

        //text_box_admin.draw();
        list_box_admin.draw();
//        geo_slot_admin_manager.draw();

        graphic.draw();
    }
}

