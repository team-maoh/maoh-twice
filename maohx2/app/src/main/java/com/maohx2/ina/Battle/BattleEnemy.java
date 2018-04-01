package com.maohx2.ina.Battle;

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
        wait_frame = rnd.nextInt(80);
        attack_frame = 100;//データベースからの読み込み、レベルによる補正などもあり

    }

    @Override
    public void update(){

        wait_frame++;
        attack_flag = false;
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
