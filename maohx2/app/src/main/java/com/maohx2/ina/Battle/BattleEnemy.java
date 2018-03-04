package com.maohx2.ina.Battle;

import com.maohx2.ina.Constants;
import static com.maohx2.ina.Constants.UnitStatus.Status.*;

import java.util.Random;

/**
 * Created by ina on 2017/09/21.
 */

public class BattleEnemy extends BattleUnit {

    int position_x;
    int position_y;
    int radius;
    int uiid;
    int wait_frame;
    int attack_frame;
    boolean attack_flag;

    @Override
    public void init() {
        super.init();
        Random rnd = new Random();
        position_x = 0;
        position_y = 0;
        radius = 0;
    }

    /*
    public void initStatus(BattleDungeonUnitData _battleDungeonUnitData) {
        super.initStatus(_battleDungeonUnitData);
        Random rnd = new Random();
        wait_frame = rnd.nextInt(80);
        //by kmhanko
        attack_frame = _battleDungeonUnitData.getStatus(ATTACK_FRAME);
        //attack_frame = 100;//データベースからの読み込み、レベルによる補正などもあり
    }
    */

    @Override
    protected void statusInit() {
        super.statusInit();
        Random rnd = new Random();
        wait_frame = rnd.nextInt(80);
        //by kmhanko
        attack_frame = battleDungeonUnitData.getStatus(ATTACK_FRAME);
        //attack_frame = 100;//データベースからの読み込み、レベルによる補正などもあり
    }

    @Override
    public void update(){

        //時間経過
        wait_frame++;
        attack_flag = false;

        //attackFlameに達したらUnitを対象として攻撃
        if(wait_frame == attack_frame){
            wait_frame = 0;
            attack_unit_num = 0;
        }

    }

    @Override
    public int getPositionX() {
        return position_x;
    }

    @Override
    public void setPositionX(int _position_x) {
        position_x = _position_x;
    }

    @Override
    public int getPositionY() {
        return position_y;
    }

    @Override
    public void setPositionY(int _position_y) {
        position_y = _position_y;
    }

    @Override
    public int getRadius() {
        return radius;
    }

    @Override
    public void setRadius(int _radius) {
        radius = _radius;
    }

    @Override
    public int getUIID() {
        return uiid;
    }

    @Override
    public void setUIID(int _uiid) {
        uiid = _uiid;
    }

}
