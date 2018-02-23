package com.maohx2.kmhanko.PlayerStatus;

import com.maohx2.kmhanko.geonode.GeoCalcSaverAdmin;

/**
 * Created by user on 2018/02/23.
 */

public class PlayerStatus {

    //Inventry

    //Status
    int hp;
    int attack;
    int defence;
    int luck;

    //Equip


    //Admin

    //GeoSlot計算用
    GeoCalcSaverAdmin geoCalcSaverAdmin;


    public PlayerStatus(GeoCalcSaverAdmin _geoCalcSaverAdmin) {
        geoCalcSaverAdmin = _geoCalcSaverAdmin;
    }

    public void calcStatus() {
        //初期化
        initStatus();


        //基礎値計算
        hp = 100;
        attack = 10;
        defence = 0;
        luck = 0;

        //武器値計算


        //GeoSlot計算
        geoCalcSaverAdmin.finalCalc();

        hp += geoCalcSaverAdmin.getGeoCalcSaver("HP").getParam();
        attack += geoCalcSaverAdmin.getGeoCalcSaver("Attack").getParam();
        defence += geoCalcSaverAdmin.getGeoCalcSaver("Defence").getParam();
        luck += geoCalcSaverAdmin.getGeoCalcSaver("Luck").getParam();
    }

    public void initStatus() {
        hp = 0;
        attack = 0;
        defence = 0;
        luck = 0;
    }


}
