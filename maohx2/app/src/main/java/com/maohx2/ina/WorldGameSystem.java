package com.maohx2.ina;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.DungeonSelectManager;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.itemshop.ItemShopAdmin;
import android.graphics.Paint;

/**
 * Created by ina on 2017/10/01.
 */

public class WorldGameSystem {

    SurfaceHolder holder;
    Paint paint = new Paint();
    Canvas canvas;
    TextBoxAdmin text_box_admin;
    //ListBoxAdmin list_box_admin;
    GeoSlotAdmin geo_slot_admin;
    GeoSlotAdminManager geo_slot_admin_manager;
    MyDatabaseAdmin databaseAdmin;
    Graphic graphic;

    DungeonSelectManager dungeonSelectManager;
    ItemShopAdmin itemShopAdmin;
    ItemDataAdminManager itemDataAdminManager;

    public void init(UserInterface map_user_interface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin) {
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;


        text_box_admin = new TextBoxAdmin(graphic);
        //list_box_admin = new ListBoxAdmin();
        geo_slot_admin_manager = new GeoSlotAdminManager();
        //geo_slot_admin = new GeoSlotAdmin();
        dungeonSelectManager = new DungeonSelectManager();

        itemDataAdminManager = new ItemDataAdminManager();
        itemShopAdmin = new ItemShopAdmin();


        text_box_admin.init(map_user_interface);
        //list_box_admin.init(map_user_interface, graphic);
        geo_slot_admin_manager.init(graphic, map_user_interface, databaseAdmin);
        //geo_slot_admin.init(map_user_interface, map_activity);
        dungeonSelectManager.init(graphic, map_user_interface, databaseAdmin);

        itemDataAdminManager.init(databaseAdmin);

        itemShopAdmin.init(graphic, map_user_interface, databaseAdmin, itemDataAdminManager);
        itemShopAdmin.makeAndOpenItemShop(ItemShopAdmin.ITEM_KIND.EXPEND, "debug");



        geo_slot_admin_manager.setActiveGeoSlotAdmin("Test");

        canvas = null;
    }


    public void updata() {
        //text_box_admin.update();
        //list_box_admin.update();
        //geo_slot_admin_manager.update();
        //map_user_interface.update();
        //dungeonSelectManager.update();
        itemShopAdmin.update();
    }


    public void draw() {

        //text_box_admin.draw();
        //list_box_admin.draw();
        //geo_slot_admin_manager.draw();

        //dungeonSelectManager.draw();
        itemShopAdmin.draw();

        graphic.draw();
    }
}

