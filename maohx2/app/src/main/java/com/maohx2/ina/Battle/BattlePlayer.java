package com.maohx2.ina.Battle;

import com.maohx2.ina.Draw.Graphic;

/**
 * Created by ina on 2017/09/21.
 */

public class BattlePlayer extends BattleUnit {

    BattlePlayer(Graphic _graphic){
        super(_graphic);
    }

    @Override
    public double getPositionX() {
        return -510;
    }

    @Override
    public void setPositionX(double _position_x) {}

    @Override
    public double getPositionY() {
        return -510;
    }

    @Override
    public void setPositionY(double _position_y) {}

    @Override
    public double getRadius() {
        return -510;
    }

    @Override
    public void setRadius(double _radius) {}

    @Override
    public int getUIID(){return  -510; }

    @Override
    public void setUIID(int _uiid){}

}
