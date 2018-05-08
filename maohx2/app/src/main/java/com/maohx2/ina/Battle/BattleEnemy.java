package com.maohx2.ina.Battle;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;

import static com.maohx2.ina.Constants.UnitStatus.Status.*;

import static com.maohx2.ina.Battle.BattleBaseUnitData.SpecialAction;
import static com.maohx2.ina.Battle.BattleBaseUnitData.ActionID;

import java.util.Random;

/**
 * Created by ina on 2017/09/21.
 */

public class BattleEnemy extends BattleUnit {

    double position_x;
    double position_y;
    double radius;
    int uiid;
    int wait_frame;
    int attack_frame;


    public BattleEnemy(Graphic _graphic){
        super(_graphic);
        position_x = 0;
        position_y = 0;
        radius = 0;
    }

    /*
    @Override
    public void init() {
        super.init();

    }
    */

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
        attack_frame = battleDungeonUnitData.getStatus(ATTACK_FRAME);
        if (attack_frame > 0 ) {
            wait_frame = rnd.nextInt((int) (getAttackFrame() / 2));
        } else {
            wait_frame = 0;
        }
    }

    @Override
    public int update(){


        //時間経過
        wait_frame++;
        //attack_flag = false;

        position_x += dx*speed;
        position_y += dy*speed;

        //とりあえず外には出ないようにする
        if (position_x < 0) {
            position_x = -position_x;
            dx = -dx;
        }
        if (position_y < 0) {
            position_y = -position_y;
            dy = -dy;
        }
        if (position_x > 1600) {
            position_x = 1600-(position_x-1600);
            dx = -dx;
        }
        if (position_y > 900) {
            position_y = 900-(position_y-900);
            dy = -dy;
        }

        move_num++;

        if(move_num == move_end) {
            //memo rnd.nextInt(x) = 0 ~ x-1


            //現在の位置から200~1400 - pの範囲で移動する。→これだと画面外に出る(positison_x = 1500, 乱数 = 0の時など)
            dx = rnd.nextInt(1200)+200 - position_x;
            dy = rnd.nextInt(500)+200 - position_y;


            dl = Math.sqrt(dx*dx + dy*dy);

            dx = dx / dl;
            dy = dy / dl;
            move_end = (int) (dl/speed);
            move_num = 0;
        }

        //attackFlameに達したらUnitを対象として攻撃
        if(wait_frame == attack_frame){
            wait_frame = 0;
            return attack;
        }


        return 0;
    }

    @Override
    public void draw(){

        if (unitKind == Constants.UnitKind.ENEMY) {
            graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(), (int) position_x, (int) position_y);
        }
        if (unitKind == Constants.UnitKind.ROCK) {
            graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(), (int) position_x, (int) position_y, 8.0f, 8.0f, 0.0f, 255, false);
        }

        //graphic.bookingDrawCircle(position_x, position_y, radius);
        graphic.bookingDrawText(String.valueOf(hit_point),(int)position_x,(int)position_y);

        paint.setARGB(255,0,255,0);
        graphic.bookingDrawRect((int)(position_x-radius*0.8), (int)(position_y+radius*0.8), (int)(((double)position_x-(double)radius*0.8+(double)radius*1.6*((double)hit_point/(double)max_hit_point))), (int)(position_y+radius*0.9),paint);

        if (attack_frame > 0) {
            paint.setARGB(255, 255, 0, 0);
            graphic.bookingDrawRect((int) (position_x - radius * 0.8), (int) (position_y + radius * 0.9), (int) (((double) position_x - (double) radius * 0.8 + (double) radius * 1.6 * ((double) wait_frame / (double) attack_frame))), (int) (position_y + radius * 1.0), paint);
        }

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
        return wait_frame;
    }
    @Override
    public void setWaitFrame(int _wait_frame) {
        wait_frame = _wait_frame;
    }
    @Override
    public double getAttackFrame() {
        return attack_frame;
    }
    @Override
    public void setAttackFrame(int _attack_frame) {
        attack_frame = _attack_frame;
    }



    // enemy用
    public SpecialAction getSpecialAction() { return battleDungeonUnitData.getSpecialAction(); }
    public int getSpecialActionWidth() { return battleDungeonUnitData.getSpecialActionWidth(); }
    public int getSpecialActionPeriod() { return battleDungeonUnitData.getSpecialActionPeriod(); }
    public float[] getActionRate() { return battleDungeonUnitData.getActionRate(); }
    public float getActionRate(ActionID _actionRateID) { return battleDungeonUnitData.getActionRate(_actionRateID); }

}
