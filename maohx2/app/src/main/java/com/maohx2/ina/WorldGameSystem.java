package com.maohx2.ina;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.ListBoxAdmin;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.dungeonselect.DungeonSelectManager;
import com.maohx2.kmhanko.effect.EffectAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdmin;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
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

    EffectAdmin effectAdmin;
    SoundAdmin soundAdmin;

    UserInterface map_user_interface;

    public void init(UserInterface _map_user_interface, Graphic _graphic, MyDatabaseAdmin _databaseAdmin, SoundAdmin _soundAdmin) {
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
        soundAdmin = _soundAdmin;
        map_user_interface = _map_user_interface;

        soundAdmin.loadSoundPack("basic");

        text_box_admin = new TextBoxAdmin(graphic);
        text_box_admin.init(map_user_interface);

        text_box_admin.setTextBoxExists(0,false);
        text_box_admin.setTextBoxExists(1,false);

        //list_box_admin = new ListBoxAdmin();
        geoSlotAdminManager = new GeoSlotAdminManager(graphic, map_user_interface, databaseAdmin, text_box_admin);
        //geo_slot_admin = new GeoSlotAdmin();
        dungeonSelectManager = new DungeonSelectManager();

        itemDataAdminManager = new ItemDataAdminManager();
        itemShopAdmin = new ItemShopAdmin();

        effectAdmin = new EffectAdmin(graphic, databaseAdmin, soundAdmin);


        dungeonSelectManager.init(graphic, map_user_interface, databaseAdmin);

        itemDataAdminManager.init(databaseAdmin,graphic);

        itemShopAdmin.init(graphic, map_user_interface, databaseAdmin, text_box_admin, itemDataAdminManager);
        //itemShopAdmin.makeAndOpenItemShop(ItemShopAdmin.ITEM_KIND.GEO_OBJECT, "debug");

        geoSlotAdminManager.setActiveGeoSlotAdmin("森");

        canvas = null;
    }


    public void updata() {

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

        //geoSlotAdminManager.update();
        dungeonSelectManager.update();
        //itemShopAdmin.update();
        text_box_admin.update();
        effectAdmin.update();
    }


    public void draw() {

        //geoSlotAdminManager.draw();

        dungeonSelectManager.draw();
        //itemShopAdmin.draw();
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


