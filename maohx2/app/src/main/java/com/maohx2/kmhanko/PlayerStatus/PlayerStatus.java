package com.maohx2.kmhanko.PlayerStatus;

import com.maohx2.ina.Battle.BattleDungeonUnitData;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.geonode.GeoCalcSaverAdmin;


/**
 * Created by user on 2018/02/23.
 */

public class PlayerStatus {

    //Inventry

    //Status
    int level;

    int hp;
    int attack;
    int defence;
    int luck;

    int geoHP;
    int geoAttack;
    int geoDefence;
    int geoLuck;

    //Equip




    public PlayerStatus(MyDatabaseAdmin _myDatabaseAdmin) {
        //TODO SAVEから読みだす
        initStatus();
        initGeoStatus();
        setTestStatus();
    }

    public void calcStatus() {
        //初期化
        initStatus();

        //デバッグ用初期値
        setTestStatus();

        //GeoSlot計算
        hp += geoHP;
        attack += geoAttack;
        defence += geoDefence;
        luck += geoLuck;
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

    public void initStatus() {
        hp = 0;
        attack = 0;
        defence = 0;
        luck = 0;
    }

    // デバッグ用
    public void setTestStatus() {
        hp = 100;
        attack = 10;
        defence = 5;
        luck = 5;
    }

    //DBからの読み出し

    public BattleDungeonUnitData makeBattleDungeonUnitData() {
        BattleDungeonUnitData battleDungeonUnitData = new BattleDungeonUnitData();
        battleDungeonUnitData.setName("Player");
        battleDungeonUnitData.setStatus(
                new int[] {
                    hp, attack, defence, luck, -1,
                }
        );
        return battleDungeonUnitData;
    }


}
