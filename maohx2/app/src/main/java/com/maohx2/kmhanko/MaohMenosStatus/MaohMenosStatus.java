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
        geoHP += geoCalcSaverAdmin.getParam("HP");
        geoAttack += geoCalcSaverAdmin.getParam("Attack");
        geoDefence += geoCalcSaverAdmin.getParam("Defence");
        geoLuck += geoCalcSaverAdmin.getParam("Luck");
    }

    public int[] getMenosStatuses() {
        return new int[] {
                geoHP,
                geoAttack,
                geoDefence,
                geoLuck
        };
    }

}
