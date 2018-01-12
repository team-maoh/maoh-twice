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
    GeoSlotAdminManager geoSlotAdminManager;
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
        geoSlotAdminManager = new GeoSlotAdminManager(graphic, map_user_interface, databaseAdmin);
        //geo_slot_admin = new GeoSlotAdmin();
        dungeonSelectManager = new DungeonSelectManager();

        itemDataAdminManager = new ItemDataAdminManager();
        itemShopAdmin = new ItemShopAdmin();


        text_box_admin.init(map_user_interface);
        dungeonSelectManager.init(graphic, map_user_interface, databaseAdmin);

        itemDataAdminManager.init(databaseAdmin);

        itemShopAdmin.init(graphic, map_user_interface, databaseAdmin, text_box_admin, itemDataAdminManager);
        itemShopAdmin.makeAndOpenItemShop(ItemShopAdmin.ITEM_KIND.GEO_OBJECT, "debug");



        geoSlotAdminManager.setActiveGeoSlotAdmin("森");

        canvas = null;
    }


    public void updata() {
        geoSlotAdminManager.update();
        //map_user_interface.update();
        //dungeonSelectManager.update();
        //itemShopAdmin.update();
        //text_box_admin.update();
    }


    public void draw() {

        geoSlotAdminManager.draw();

        //dungeonSelectManager.draw();
        //itemShopAdmin.draw();
        //text_box_admin.draw();

        graphic.draw();
    }
}

/*
memo作業内容

WorldGameSystemの中身を整理した
いなに画面切り替えオーダー
いなにボタンオーダー(ListBoxがButtonの配列をもち、それぞれの位置が決められる。それが画像だったりテキストだったりできて、
ListBox<T extends Button>としてButtonを継承したButtonを作れるようにする)
Shop,Present,GeoSlotあたりはいなのItemBag待ち
いなのボタンができたらメニューを並べる
いなの画面遷移ができたらメニューから遷移できるようにし、GeoWorldMapからGeoSlotMapに遷移できるようにする。
GeoSlotMapのここに置けないとか、一定の条件を満たした場合に解放されるやつ。
いなのセーブ機能

 */


