/*
package com.maohx2.ina.Battle;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;

import static com.maohx2.ina.Constants.UnitStatus.Status.*;

import java.util.Random;
*/
/**
 * Created by ina on 2017/09/21.
 */
/*
public class BattleRock extends BattleUnit {

    double position_x;
    double position_y;
    double radius;
    int uiid;

    public BattleRock(Graphic _graphic){
        super(_graphic);
        position_x = 0;
        position_y = 0;
        radius = 0;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public void draw(){

        graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(),(int)position_x,(int)position_y);
        graphic.bookingDrawText(String.valueOf(hit_point),(int)position_x,(int)position_y);

        paint.setARGB(255,0,255,0);
        graphic.bookingDrawRect((int)(position_x-radius*0.8), (int)(position_y+radius*0.8), (int)(((double)position_x-(double)radius*0.8+(double)radius*1.6*((double)hit_point/(double)max_hit_point))), (int)(position_y+radius*0.9),paint);

    }

    @Override
    public double getPositionX() {
        return position_x;
    }

    @Override
    public void setPositionX(double _position_x) {
        position_x = _position_x;
    }

    @Override
    public double getPositionY() {
        return position_y;
    }

    @Override
    public void setPositionY(double _position_y) {position_y = _position_y;}

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public void setRadius(double _radius) {
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

    @Override
    public int getWaitFrame() {
        return -1;
    }
    @Override
    public void setWaitFrame(int _wait_frame) {}
    @Override
    public double getAttackFrame() {
        return -1;
    }
    @Override
    public void setAttackFrame(int _attack_frame) {}

}
*/