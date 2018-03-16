package com.maohx2.ina.Battle;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;

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


    public BattleEnemy(Graphic _graphic){
        super(_graphic);
        paint.setARGB(255,0,255,0);
    }

    @Override
    public void init() {
        super.init();
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
    public void draw(){

        graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(),position_x,position_y);
        //graphic.bookingDrawCircle(position_x, position_y, radius);
        graphic.bookingDrawText(String.valueOf(hit_point),position_x,position_y);

        graphic.bookingDrawRect((int)(position_x-radius*0.8), (int)(position_y+radius*0.8), (int)(position_x+radius*0.8)*(hit_point/max_hit_point), (int)(position_y+radius*0.9),paint);

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
