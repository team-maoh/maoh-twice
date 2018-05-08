package com.maohx2.kmhanko.PlayerStatus;

import com.maohx2.ina.Battle.BattleDungeonUnitData;
import com.maohx2.ina.Constants.UnitStatus.Status;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.geonode.GeoCalcSaverAdmin;


/**
 * Created by user on 2018/02/23.
 */

public class PlayerStatus {

    //Inventry

    //Status
    private int level;

    private int hp;
    private int attack;
    private int defence;
    private int luck;

    private int baseHp;
    private int baseAttack;
    private int baseDefence;
    private int baseLuck;

    private int geoHP;
    private int geoAttack;
    private int geoDefence;
    private int geoLuck;

    private int money;

    //Record

    private int maohWinCount;
    //TODO 今出てる魔王保存？

    private int clearCount; //制覇回数
    private int nowClearCount; //現在の制覇回数時間軸

    private int tutorialInDungeon; //by fuusya : ダンジョンでチュートリアルを表示中 = 0 (表示済み = 1)

    //Equip




    public PlayerStatus(MyDatabaseAdmin _myDatabaseAdmin) {
        initStatus();
        initGeoStatus();
    }

    public void calcStatus() {
        //計算
        hp = baseHp + geoHP;
        attack = baseAttack + geoAttack;
        defence = baseDefence + geoDefence;
        luck = baseLuck + geoLuck;
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
        level = 1;
        hp = 0;
        attack = 0;
        defence = 0;
        luck = 0;
        money = 0;
    }

    // デバッグ用
    public void setTestStatus() {
        level = 1;
        hp = 100;
        attack = 10;
        defence = 5;
        luck = 5;
        money = 10000;
    }

    //DBからの読み出し

    public BattleDungeonUnitData makeBattleDungeonUnitData() {
        BattleDungeonUnitData battleDungeonUnitData = new BattleDungeonUnitData();
        battleDungeonUnitData.setName("Player");

        int[] buf = new int[Status.NUM_OF_STATUS.ordinal()];
        for(int i = 0; i < buf.length ; i++) {
            buf[i] = 0;
        }

        buf[Status.HP.ordinal()] = hp;
        buf[Status.ATTACK.ordinal()] = attack;
        buf[Status.DEFENSE.ordinal()] = defence;
        buf[Status.LUCK.ordinal()] = luck;
        buf[Status.SPEED.ordinal()] = 0;
        buf[Status.ATTACK_FRAME.ordinal()] = -1;

        battleDungeonUnitData.setStatus(buf);
        return battleDungeonUnitData;
    }

    public int getHP() { return hp; }
    public int getAttack() { return attack; }
    public int getDefence() { return defence; }
    public int getLuck() { return luck; }
    public int getLevel() { return level; }
    public int getMoney() { return money; }
    public int getMaohWinCount() { return maohWinCount; }
    public int getClearCount() { return clearCount; }
    public int getNowClearCount() { return nowClearCount; }
    public int getTutorialInDungeon() {return tutorialInDungeon; }

    public void setHP(int x) { hp = x; }
    public void setAttack(int x) { attack = x; }
    public void setDefence(int x) { defence = x; }
    public void setLuck(int x) { luck = x; }
    public void setLevel(int x) { level = x; }
    public void setMoney(int x) { money = x; }
    public void setClearCount(int x) { clearCount = x; }
    public void setNowClearCount(int x) {  nowClearCount = x; }
    public void setTutorialInDungeon(int x){  tutorialInDungeon = x;}

    public void setBaseHP(int x) { baseHp = x; }
    public void setBaseAttack(int x) { baseAttack = x; }
    public void setBaseDefence(int x) { baseDefence = x; }
    public void setBaseLuck(int x) { baseLuck = x; }

    public void setMaohWinCount(int _maohWinCount) { maohWinCount = _maohWinCount; }

    public int addMoney(int _money) {
        money += _money;
        return money;
    }
    public int subMoney(int _money) {
        money -= _money;
        return money;
    }


    public Integer[] getSaveStatuses() {
        return new Integer[] {
                level,
                baseHp,
                baseAttack,
                baseDefence,
                baseLuck,
                money,
                maohWinCount,
                clearCount,
                nowClearCount,
                tutorialInDungeon
        };
    }


}
