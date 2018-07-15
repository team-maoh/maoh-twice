package com.maohx2.ina;

import com.maohx2.ina.Constants.GAMESYSTEN_MODE.WORLD_MODE;
import com.maohx2.ina.Constants.Mode.ACTIVATE;


/**
 * Created by ina on 2018/02/04.
 */

public class WorldModeAdmin {
    // *** takano ***

    WORLD_MODE mode;

    public WorldModeAdmin() {
        initWorld();
    }


    public void initWorld() {
        mode = WORLD_MODE.DUNGEON_SELECT;
    }


    public WORLD_MODE getMode() {
        return mode;
    }

    public void setMode(WORLD_MODE mode) {
        this.mode = mode;
    }

    public void release() {
        System.out.println("takanoRelease : WorldModeAdmin");
        mode = null;
    }

/*


    private ACTIVATE worldMap;
    private ACTIVATE geoSlotMap;
    private ACTIVATE shop;
    private ACTIVATE present;
    private ACTIVATE equip;


    public void initWorld() {
        worldMap = ACTIVATE.ACTIVE;
        geoSlotMap = ACTIVATE.STOP;
        shop = ACTIVATE.STOP;
        present = ACTIVATE.STOP;
        equip = ACTIVATE.STOP;
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
    public void setEquip(ACTIVATE _f) {
        equip = _f;
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
    public ACTIVATE getEquip() {
        return equip;
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
*/


    // *** takano ここまで ***

}
