package com.maohx2.ina.Battle;

/**
 * Created by ina on 2017/09/21.
 */

public class BattleEnemy extends BattleUnit {

    int position_x;
    int position_y;
    int radius;
    int uiid;

    @Override
    public void init() {
        super.init();
        position_x = 0;
        position_y = 0;
        radius = 0;
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
