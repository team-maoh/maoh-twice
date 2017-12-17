package com.maohx2.ina;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.MapUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.DungeonSelectManager;
import com.maohx2.kmhanko.geonode.GeoWorldMap;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.itemshop.ItemShopAdmin;
import com.maohx2.kmhanko.GeoPresent.GeoPresentManager;
import android.graphics.Paint;

/**
 * Created by ina on 2017/10/01.
 */

public class WorldGameSystem {
    //*** 基本事項 ***
    Graphic graphic;
    MyDatabaseAdmin databaseAdmin;
    MapUserInterface mapUserInterface;

    // *** TextBox関係 ***
    TextBoxAdmin text_box_admin;

    // *** GeoSlot関係 ***
    GeoSlotAdminManager geoSlotAdminManager;
    GeoWorldMap geoWorldMap;

    // *** DungeonSelect関係
    DungeonSelectManager dungeonSelectManager;

    // *** ItemShop関係 ***
    ItemShopAdmin itemShopAdmin;
    ItemDataAdminManager itemDataAdminManager;

    // *** GeoPresent関係 ***
    GeoPresentManager geoPresentManager;

    public void init(UserInterface _mapUserInterface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin) {
        // *** 基本項目 ***
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        mapUserInterface = (MapUserInterface)_mapUserInterface;

        // *** TextBox関係 ***
        text_box_admin = new TextBoxAdmin(graphic);
        text_box_admin.init(mapUserInterface);

        // *** GeoSlot関係 ***
        geoSlotAdminManager = new GeoSlotAdminManager(graphic, mapUserInterface, databaseAdmin);
        geoWorldMap = new GeoWorldMap(graphic, mapUserInterface, databaseAdmin);

        // *** DungeonSelect関係
        dungeonSelectManager = new DungeonSelectManager();
        dungeonSelectManager.init(graphic, mapUserInterface, databaseAdmin);

        // *** ItemShop関係 ***
        itemDataAdminManager = new ItemDataAdminManager();
        itemShopAdmin = new ItemShopAdmin();
        itemDataAdminManager.init(databaseAdmin);
        itemShopAdmin.init(graphic, mapUserInterface, databaseAdmin, text_box_admin, itemDataAdminManager);

        // *** GeoPresent関係 ***
        geoPresentManager = new GeoPresentManager();
        geoPresentManager.init(mapUserInterface, graphic, databaseAdmin, text_box_admin);

        // *** デバッグ系処理 ***
        //itemShopAdmin.makeAndOpenItemShop(ItemShopAdmin.ITEM_KIND.GEO_OBJECT, "debug");
        //geoPresentManager.presentAndCheck(new GeoObjectData(50,0,0,0,1.5,1.0,1.0,1.0));
        //ListBox menuList = new ListBox();

        geoSlotAdminManager.setActiveGeoSlotAdmin("森");

    }


    public void updata() {
        //geo_slot_admin_manager.update();
        //map_user_interface.update();
        //dungeonSelectManager.update();
        //itemShopAdmin.update();
        //text_box_admin.update();
        //geoWorldMap.update();

        geoSlotAdminManager.update();
    }


    public void draw() {

       // geo_slot_admin_manager.draw();

        //dungeonSelectManager.draw();
        //itemShopAdmin.draw();
        //text_box_admin.draw();

        //geoWorldMap.draw();

        geoSlotAdminManager.draw();

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