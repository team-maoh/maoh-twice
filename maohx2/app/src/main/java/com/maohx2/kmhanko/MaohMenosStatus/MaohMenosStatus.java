package com.maohx2.kmhanko.MaohMenosStatus;

import com.maohx2.kmhanko.geonode.GeoCalcSaverAdmin;

/**
 * Created by user on 2018/05/13.
 */

public class MaohMenosStatus {
    private int geoHP;
    private int geoAttack;
    private int geoDefence;
    private int geoLuck;

    public MaohMenosStatus() {
        initGeoStatus();
    }

    public void initGeoStatus() {
        geoHP = 0;
        geoAttack = 0;
        geoDefence = 0;
        geoLuck = 0;
    }
    public void calcGeoStatus(GeoCalcSaverAdmin geoCalcSaverAdmin) {
        initGeoStatus();
        geoHP += geoCalcSaverAdmin.getParam("HP")/25;
        geoAttack += geoCalcSaverAdmin.getParam("Attack")/25;
        geoDefence += geoCalcSaverAdmin.getParam("Defence")/25;
        geoLuck += geoCalcSaverAdmin.getParam("Luck")/25;
    }

    public int[] getMenosStatuses() {
        return new int[] {
                geoHP,
                geoAttack,
                geoDefence,
                geoLuck
        };
    }

    public int getGeoHP() {
        return geoHP;
    }
    public int getGeoAttack() {
        return geoAttack;
    }
    public int getGeoDefence() {
        return geoDefence;
    }
    public int getGeoLuck() {
        return geoLuck;
    }

    public void release() {}

}
