package com.maohx2.ina;

/**
 * Created by ina on 2017/10/22.
 */

import android.app.Application;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Draw.BitmapDataAdmin;
import com.maohx2.ina.ItemData.EquipmentInventrySaver;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.ItemDataAdminManager;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.Saver.ExpendItemInventrySaver;
import com.maohx2.kmhanko.Saver.GeoInventrySaver;
import com.maohx2.kmhanko.Saver.GeoPresentSaver;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

//by kmhanko
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.Saver.PlayerStatusSaver;

import static com.maohx2.ina.ItemData.ItemDataAdmin.graphic;

//アイテムのアイコン画像、プレイヤーのステータス、アイテムのデータ、をグローバルで持つ予定
public class GlobalData extends Application {

    BitmapDataAdmin g_bitmap_data_admin;
    MyDatabaseAdmin g_my_database_admin;
    GlobalConstants g_constants;

    //by kmhanko
    PlayerStatus playerStatus;
    PlayerStatusSaver playerStatusSaver;

    EquipmentInventrySaver equipmentInventrySaver;
    InventryS equipmentInventry;

    GeoInventrySaver geoInventrySaver;
    InventryS geoInventry;

    ExpendItemInventrySaver expendItemInventrySaver;
    InventryS expendItemInventry;

    ItemDataAdminManager itemDataAdminManager;

    public void init(int disp_x, int disp_y) {
        g_my_database_admin = new MyDatabaseAdmin(this);
        g_my_database_admin.addMyDatabase("globalImageDB", "globalImage.db", 1, "r");
        g_bitmap_data_admin = new BitmapDataAdmin();
        g_bitmap_data_admin.init(this);
        g_bitmap_data_admin.loadGlobalImages(g_my_database_admin.getMyDatabase("globalImageDB"));
        g_constants = new GlobalConstants(disp_x, disp_y);

        //by kmhanko
        playerStatus = new PlayerStatus(g_my_database_admin);
        playerStatusSaver = new PlayerStatusSaver(g_my_database_admin, "PlayerStatusSave", "PlayerStatusSave.db", 1, "ns", playerStatus);
        playerStatusSaver.load();


        equipmentInventrySaver = new EquipmentInventrySaver(g_my_database_admin, "EquipmentInventrySave", "EquipmentInventrySave.db", 1, "ns");
        equipmentInventry = new InventryS(equipmentInventrySaver);

        geoInventrySaver = new GeoInventrySaver(g_my_database_admin, "GeoInventrySave", "GeoInventrySave.db", 1, "s", graphic);
        geoInventry = new InventryS(geoInventrySaver);

        expendItemInventrySaver = new ExpendItemInventrySaver(g_my_database_admin, "ExpendItemInventrySave", "ExpendItemInventrySave.db", 1, "s");
        expendItemInventry = new InventryS(expendItemInventrySaver);

        itemDataAdminManager = new ItemDataAdminManager();
    }

    //ゲッターとか
    public BitmapDataAdmin getGlobalBitmapDataAdmin() { return g_bitmap_data_admin;}
    public GlobalConstants getGlobalConstants() { return g_constants;}


    public EquipmentInventrySaver getEquipmentInventrySaver(){return equipmentInventrySaver;}
    public InventryS getEquipmentInventry(){return equipmentInventry;}

    public InventryS getGeoInventry() { return geoInventry; }
    public GeoInventrySaver getGeoInventrySaver() {return geoInventrySaver;}

    public ExpendItemInventrySaver getExpendItemInventrySaver() {return expendItemInventrySaver;}
    public InventryS getExpendItemInventry() {return expendItemInventry;}

    public ItemDataAdminManager getItemDataAdminManager(){return itemDataAdminManager;}

    // by kmhanko
    public PlayerStatus getPlayerStatus() { return playerStatus; }
    public PlayerStatusSaver getPlayerStatusSaver() { return playerStatusSaver; }
}