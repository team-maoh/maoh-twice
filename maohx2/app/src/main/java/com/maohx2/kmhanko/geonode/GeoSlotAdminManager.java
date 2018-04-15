package com.maohx2.kmhanko.geonode;

/**
 * Created by user on 2017/10/27.
 */

import android.graphics.Canvas;
import java.util.ArrayList;
import java.util.List;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.kmhanko.Saver.GeoSlotSaver;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.kmhanko.Arrange.InventryS;
import com.maohx2.kmhanko.itemdata.GeoObjectData;

//GeoSlotAdminの実体を持つクラス
//GeoSlotMapButtonの実体も持つ。
public class GeoSlotAdminManager {
    static final String DB_NAME_GEOSLOTMAP = "GeoSlotMapDB";
    static final String DB_ASSET_GEOSLOTMAP = "GeoSlotMapDB.db";

    static final String DB_NAME_GEOSLOTEVENT = "GeoSlotEventDB";
    static final String DB_ASSET_GEOSLOTEVENT = "GeoSlotEvent.db";

    public final int GEO_SLOT_ADMIN_MAX = 16;
    List<GeoSlotAdmin> geoSlotAdmins = new ArrayList<GeoSlotAdmin>(GEO_SLOT_ADMIN_MAX);
    GeoSlotAdmin activeGeoSlotAdmin = null;

    MyDatabaseAdmin databaseAdmin;
    Graphic graphic;
    UserInterface userInterface;
    TextBoxAdmin textBoxAdmin;
    WorldModeAdmin worldModeAdmin;

    PlayerStatus playerStatus;
    InventryS geoInventry;

    GeoSlotSaver geoSlotSaver;

    boolean is_load_database;

    public GeoSlotAdminManager(Graphic _graphic, UserInterface _userInterface, WorldModeAdmin _worldModeAdmin, MyDatabaseAdmin _databaseAdmin, TextBoxAdmin _textBoxAdmin, PlayerStatus _playerStatus, InventryS _geoInventry, GeoSlotSaver _geoSlotSaver) {
        graphic = _graphic;
        userInterface = _userInterface;
        databaseAdmin = _databaseAdmin;
        textBoxAdmin = _textBoxAdmin;
        worldModeAdmin = _worldModeAdmin;
        playerStatus = _playerStatus;
        geoInventry = _geoInventry;
        geoSlotSaver = _geoSlotSaver;
        addDatabase();
        this.loadGeoSlotDatabase();

        geoSlotSaver.setGeoSlotAdminManager(this);
    }

    public void update() {
        geoInventry.updata();
        if (activeGeoSlotAdmin != null) {
            activeGeoSlotAdmin.update();
        }
    }

    public void draw() {
        geoInventry.draw();
        if (activeGeoSlotAdmin != null) {
            activeGeoSlotAdmin.draw();
        }
    }

    public void setActiveGeoSlotAdmin(String name) {
        for(int i = 0; i < geoSlotAdmins.size(); i++) {
            if (geoSlotAdmins.get(i) != null) {
                if (geoSlotAdmins.get(i).getName().equals(name)) {
                    activeGeoSlotAdmin = geoSlotAdmins.get(i);
                    return;
                }
            }
        }
        throw new Error("☆タカノ:GeoSlotAdminManager#setActiveGeoSlotAdmin : There is no GeoSlotAdmin you request by name : "+name);
    }

    public void setNullToActiveGeoSlotAdmin() {
        activeGeoSlotAdmin = null;
    }
    public void closeGeoSlotAdmin() {
        activeGeoSlotAdmin = null;
    }

    public void addDatabase() {
        databaseAdmin.addMyDatabase(DB_NAME_GEOSLOTMAP, DB_ASSET_GEOSLOTMAP, 1, "r");
        databaseAdmin.addMyDatabase(DB_NAME_GEOSLOTEVENT, DB_ASSET_GEOSLOTEVENT, 1, "r");
    }

    public void loadGeoSlotDatabase() {
        if (is_load_database) {
            return;
        }
        is_load_database = true;

        MyDatabase geoSlotMapDB = databaseAdmin.getMyDatabase(DB_NAME_GEOSLOTMAP);
        MyDatabase geoSlotEventDB = databaseAdmin.getMyDatabase(DB_NAME_GEOSLOTEVENT);

        List<String> t_names = geoSlotMapDB.getTables();

        GeoSlotAdmin.setGeoSlotMapDB(geoSlotMapDB);
        GeoSlotAdmin.setGeoSlotEventDB(geoSlotEventDB);

        for(int i = 0; i < t_names.size(); i++) {
            GeoSlotAdmin new_geo_slot_admin = new GeoSlotAdmin(graphic, userInterface, worldModeAdmin, textBoxAdmin, this);
            new_geo_slot_admin.loadDatabase(t_names.get(i));
            geoSlotAdmins.add(new_geo_slot_admin);
        }

    }

    public void addToInventry(GeoObjectData geoObjectData) {
        geoInventry.addItemData(geoObjectData);
    }

    public void deleteFromInventry(GeoObjectData geoObjectData) {
        geoInventry.subItemData(geoObjectData);
    }

    public void calcPlayerStatus() {
        playerStatus.initGeoStatus();
        for(int i = 0; i < geoSlotAdmins.size(); i++) {
            if (geoSlotAdmins.get(i) != null) {
                geoSlotAdmins.get(i).calcGeoSlot();
                GeoCalcSaverAdmin geoCSA = geoSlotAdmins.get(i).getGeoCalcSaverAdmin();
                if (geoCSA != null) {
                    playerStatus.calcGeoStatus(geoCSA);
                }
            }
        }
    }

    public void saveGeoInventry() {
        geoInventry.save();
    }

    public void saveGeoSlot() {
        geoSlotSaver.save();
    }

    public void loadGeoSlot() {
        geoSlotSaver.load();
    }

    public List<String> getGeoSlotAdminNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < geoSlotAdmins.size(); i++) {
            names.add(geoSlotAdmins.get(i).getName());
        }
        return names;
    }

    public List<GeoSlotAdmin> getGeoSlotAdmins() {
        return geoSlotAdmins;
    }
}

/*
1/12
どうしたい？
→置けない判定
→一定の条件を満たした場合に置けるようになるもの

→スロットの女神ガードの解放条件
・お金を支払う
・一定条件を満たすジオスロットを捧げる




 */
