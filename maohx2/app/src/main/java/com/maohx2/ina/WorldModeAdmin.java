package com.maohx2.ina;

import com.maohx2.ina.Constants.Mode.ACTIVATE;
import com.maohx2.kmhanko.GeoPresent.GeoPresentManager;
import com.maohx2.kmhanko.dungeonselect.DungeonSelectManager;
import com.maohx2.kmhanko.geonode.GeoSlotAdminManager;
import com.maohx2.kmhanko.itemshop.ItemShopAdmin;

/**
 * Created by ina on 2018/02/04.
 */

public class WorldModeAdmin {
//TODO いなと相談
    GeoSlotAdminManager geoSlotAdminManager;
    DungeonSelectManager dungeonSelectManager;
    ItemShopAdmin itemShopAdmin;
    GeoPresentManager geoPresentManager;

    // *** takano ***

    private ACTIVATE worldMap;
    private ACTIVATE geoSlotMap;
    private ACTIVATE shop;
    private ACTIVATE present;

/*
    public WorldModeAdmin(GeoSlotAdminManager _geoSlotAdminManager, DungeonSelectManager _dungeonSelectManager, ItemShopAdmin _itemShopAdmin, GeoPresentManager _geoPresentManager) {
        geoSlotAdminManager = _geoSlotAdminManager;
        dungeonSelectManager = _dungeonSelectManager;
        itemShopAdmin = _itemShopAdmin;
        geoPresentManager = _geoPresentManager;
    }
*/

    public void initWorld() {
        worldMap = ACTIVATE.ACTIVE;
        geoSlotMap = ACTIVATE.STOP;
        shop = ACTIVATE.STOP;
        present = ACTIVATE.STOP;
    }

    // ** setter **
    public void setWorldMap(ACTIVATE _f) {
        worldMap = _f;
    }
    public void setGeoSlotMap(ACTIVATE _f) {
        geoSlotMap = _f;
    }
    public void setShop(ACTIVATE _f) {
        shop = _f;
    }
    public void setPresent(ACTIVATE _f) {
        present = _f;
    }

    // ** getter **
    public ACTIVATE getWorldMap() {
        return worldMap;
    }
    public ACTIVATE getGetSlotMap() {
        return geoSlotMap;
    }
    public ACTIVATE getShop() {
        return shop;
    }
    public ACTIVATE getPresent() {
        return present;
    }

    public boolean getIsDraw(ACTIVATE _act) {
        if (_act == ACTIVATE.DRAW_ONLY || _act == ACTIVATE.ACTIVE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getIsUpdate(ACTIVATE _act) {
        if (_act == ACTIVATE.ACTIVE) {
            return true;
        } else {
            return false;
        }
    }

    // *** takano ここまで ***

}
