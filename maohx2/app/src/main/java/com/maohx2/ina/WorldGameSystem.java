package com.maohx2.ina;

import android.app.Activity;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.GeoPresent.GeoPresentManager;
import com.maohx2.kmhanko.PlayerStatus.*;
import com.maohx2.kmhanko.Saver.ExpendItemInventrySaver;
import com.maohx2.kmhanko.Saver.GeoInventrySaver;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
import com.maohx2.kmhanko.Saver.GeoPresentSaver;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.DungeonSelectManager;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.itemdata.GeoObjectData;
import com.maohx2.kmhanko.itemdata.GeoObjectDataAdmin;
import com.maohx2.kmhanko.itemdata.GeoObjectDataCreater;
import com.maohx2.kmhanko.itemshop.ItemShopAdmin;
import com.maohx2.kmhanko.effect.*;
import com.maohx2.kmhanko.sound.SoundAdmin;


import java.util.ArrayList;
import java.util.List;


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
    GeoPresentManager geoPresentManager;

    EffectAdmin effectAdmin;
    SoundAdmin soundAdmin;

    UserInterface map_user_interface;

    WorldModeAdmin worldModeAdmin;

    WorldActivity worldActivity;

    PlayerStatus playerStatus;

    GeoInventrySaver geoInventrySaver;
    ExpendItemInventrySaver expendItemInventrySaver;
    GeoSlotSaver geoSlotSaver;
    GeoPresentSaver geoPresentSaver;

    //TODO いな依頼:引数にUI,Graphicが入って居るためGlobalDataに設置できない
    InventryS geoInventry;
    InventryS expendItemInventry;

    //TODO いな依頼　Inventryのupdateを呼ばないと真っ黒。あとアクティブ関係

    ActivityChange activityChange;

    public void init(UserInterface _map_user_interface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, SoundAdmin _soundAdmin, WorldActivity _worldActivity, ActivityChange _activityChange) {
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        soundAdmin = _soundAdmin;
        map_user_interface = _map_user_interface;

        worldActivity = _worldActivity;
        activityChange = _activityChange;

        GlobalData globalData = (GlobalData) worldActivity.getApplication();
        playerStatus = globalData.getPlayerStatus();
        //GeoInventry = globalData.getGeoInventry();

        GeoObjectDataCreater.setGraphic(graphic);//TODO ゲーム開始時に呼ぶ


        worldModeAdmin = new WorldModeAdmin();
        worldModeAdmin.initWorld();

        soundAdmin.loadSoundPack("basic");

        text_box_admin = new TextBoxAdmin(graphic);
        text_box_admin.init(map_user_interface);

        text_box_admin.setTextBoxExists(0,false);
        text_box_admin.setTextBoxExists(1,false);


        itemDataAdminManager = new ItemDataAdminManager();
        itemShopAdmin = new ItemShopAdmin();

        effectAdmin = new EffectAdmin(graphic, databaseAdmin, soundAdmin);

        itemDataAdminManager.init(databaseAdmin, graphic);

        geoInventrySaver = new GeoInventrySaver(
                databaseAdmin,
                "GeoInventrySave",
                "GeoInventrySave.db",
                1, "ns", graphic
        );

        expendItemInventrySaver = new ExpendItemInventrySaver(
                databaseAdmin,
                "ExpendItemInventrySave",
                "ExpendItemInventrySave.db",
                1, "s", itemDataAdminManager
        );

        geoSlotSaver = new GeoSlotSaver(
                databaseAdmin,
                "GeoSlotSave",
                "GeoSlotSave.db",
                1, "ns", graphic
        );

        //TODO いな依頼:Globalに入れる
        //TODO InventrySはSaverを持たせたもの
        geoInventry = new InventryS(map_user_interface, graphic, geoInventrySaver);
        geoInventry.load();

        expendItemInventry = new InventryS(map_user_interface, graphic, expendItemInventrySaver);
        expendItemInventry.load();

        geoInventrySaver.setInventry(geoInventry);
        expendItemInventrySaver.setInventry(expendItemInventry);

        //TODO いな依頼:interfaceはあとで変更できないとまずい場合があるかもしれない

        geoSlotAdminManager = new GeoSlotAdminManager(graphic, map_user_interface, worldModeAdmin, databaseAdmin, text_box_admin, playerStatus, geoInventry, geoSlotSaver);
        dungeonSelectManager = new DungeonSelectManager(graphic, map_user_interface, worldModeAdmin, databaseAdmin, geoSlotAdminManager, activityChange);

        geoSlotAdminManager.loadGeoSlot();

        itemShopAdmin.init(graphic, map_user_interface, worldModeAdmin, databaseAdmin, text_box_admin, itemDataAdminManager, expendItemInventry, geoInventry);
        itemShopAdmin.makeAndOpenItemShop(ItemShopAdmin.ITEM_KIND.EXPEND, "debug");

        canvas = null;


        // 仮。適当にGeo入れる GEO1が上がる能力は単一
        for (int i = 0; i < 8; i++) {
            geoInventry.addItemData(GeoObjectDataCreater.getGeoObjectData(100));
        }


        geoPresentManager = new GeoPresentManager(
                graphic,
                map_user_interface,
                worldModeAdmin,
                databaseAdmin,
                text_box_admin,
                geoInventry,
                expendItemInventry,
                itemDataAdminManager.getExpendItemDataAdmin(),
                playerStatus
        );


        GeoPresentSaver.setGeoPresentManager(geoPresentManager);
        geoPresentSaver = new GeoPresentSaver(
                databaseAdmin,
                "GeoPresentSave",
                "GeoPresentSave.db",
                1, "ns"
        );

        geoPresentManager.setGeoPresentSaver(geoPresentSaver);




    }


    public void updata() {

        /*
        if (map_user_interface.getTouchState() == Constants.Touch.TouchState.DOWN) {
            List<BitmapData> testBitmapData = new ArrayList<BitmapData>();
            BitmapData _bitmapData = graphic.searchBitmap("打撃01");
            for (int i = 0; i < 9; i ++ ) {
                testBitmapData.add(graphic.processTrimmingBitmapData(_bitmapData, 120 * i, 0, 120, 120));
                //testBitmapData.add(_bitmapData);
            }
            List<String> testSoundName = new ArrayList<String>();
            testSoundName.add("bosu");
            int testID = effectAdmin.createEffect("test2", testBitmapData, testSoundName);
            effectAdmin.getEffect(testID).setPosition((int)map_user_interface.getTouchX(),(int)map_user_interface.getTouchY());
            effectAdmin.getEffect(testID).start();
        }
        */

        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getGetSlotMap())) {
            geoSlotAdminManager.update();
        }
        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getWorldMap())) {
            dungeonSelectManager.update();
        }
        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getShop())) {
            itemShopAdmin.update();
        }
        if (worldModeAdmin.getIsUpdate(worldModeAdmin.getPresent())) {
            geoPresentManager.update();
        }

        text_box_admin.update();
        effectAdmin.update();
    }


    public void draw() {

        //graphic.bookingDrawBitmapData(graphic.searchBitmap("杖"),300,590);

        if (worldModeAdmin.getIsDraw(worldModeAdmin.getGetSlotMap())) {
            geoSlotAdminManager.draw();
        }
        if (worldModeAdmin.getIsDraw(worldModeAdmin.getWorldMap())) {
            dungeonSelectManager.draw();
        }
        if (worldModeAdmin.getIsDraw(worldModeAdmin.getShop())) {
            itemShopAdmin.draw();
        }
        if (worldModeAdmin.getIsDraw(worldModeAdmin.getPresent())) {
            geoPresentManager.draw();
        }

        text_box_admin.draw();
        effectAdmin.draw();

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


