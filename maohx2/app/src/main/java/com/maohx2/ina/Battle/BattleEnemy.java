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
    int attackCount;
    int attack_frame;
    int specialActionCount;

    float[] actionRate = new float[BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal()];
    int[] alimentTime = new int[BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal()];
    BattleBaseUnitData.SpecialAction specialAction;
    int specialActionPeriod;
    int specialActionWidth;

    protected BattleBaseUnitData battleBaseUnitDataForRock;

    public BattleEnemy(Graphic _graphic){
        super(_graphic);
        position_x = 0;
        position_y = 0;
        radius = 0;
        specialActionCount = 0;
        specialActionFlag = false;
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

        specialAction = getSpecialAction();
        actionRate = getActionRate();
        specialActionPeriod = getSpecialActionPeriod();
        specialActionWidth = getSpecialActionWidth();
        alimentTime = getAlimentTime();

        if (attack_frame > 0 ) {
            attackCount = rnd.nextInt((int) (getAttackFrame() / 2));
        } else {
            attackCount = 0;
        }
    }

    @Override
    public int update(){

        super.update();

        //時間経過
        attackCount++;
        specialActionCount++;
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


        if(specialActionCount == specialActionWidth){
            specialActionFlag = false;
        }

        if(specialActionCount == specialActionPeriod){
            specialActionCount = 0;
            specialActionFlag = true;
        }

        //attackFlameに達したらUnitを対象として攻撃
        if(attackCount == attack_frame){
            attackCount = 0;
            return attack;
        }

        return 0;
    }

    @Override
    public void draw(){

        //graphic.bookingDrawText(String.valueOf(hit_point),(int)position_x,(int)position_y);

        if(specialActionFlag == true) {

            switch (specialAction) {
                case BARRIER:
                    graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(),(int)position_x,(int)position_y,1.0f,1.0f,0,254,false);
                    paint.setARGB(100,0,0,255);
                    graphic.bookingDrawCircle((int)position_x, (int)position_y, (int)radius, paint);
                    break;
                case COUNTER:
                    graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(),(int)position_x,(int)position_y,1.0f,1.0f,0,254,false);
                    paint.setARGB(100,255,100,0);
                    graphic.bookingDrawCircle((int)position_x, (int)position_y, (int)radius, paint);
                    break;
                case STEALTH:
                    graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(),(int)position_x,(int)position_y,1.0f,1.0f,0,100,false);
                    break;
            }
        }else{
            graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(),(int)position_x,(int)position_y,1.0f,1.0f,0,254,false);
        }

        //HP表示
        if (hit_point > 0) {
            paint.setARGB(255, 0, 255, 0);
            graphic.bookingDrawRect((int) (position_x - radius * 0.8), (int) (position_y + radius * 0.8), (int) (((double) position_x - (double) radius * 0.8 + (double) radius * 1.6 * ((double) hit_point / (double) max_hit_point))), (int) (position_y + radius * 0.9), paint);
        } else {
            if (unitKind == Constants.UnitKind.ROCK) {
                //オーバーキルゲージの表示
                paint.setARGB(255, 255, 0, 0);
                graphic.bookingDrawRect((int) (position_x - radius * 0.8), (int) (position_y + radius * 0.8), (int) (((double) position_x - (double) radius * 0.8 + (double) radius * 1.6 * ((double) -hit_point / (double) max_hit_point))), (int) (position_y + radius * 0.9), paint);
            }
        }

        for(int i = 0; i < BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal(); i++) {
            if (actionRate[i] > 0) {
                if(BattleBaseUnitData.ActionID.toEnum(i) == BattleBaseUnitData.ActionID.POISON){graphic.bookingDrawBitmapData(graphic.searchBitmap("Z2"),(int)(position_x+radius*0.8)-120, (int)(position_y+radius*0.6));}
                if(BattleBaseUnitData.ActionID.toEnum(i) == BattleBaseUnitData.ActionID.PARALYSIS){graphic.bookingDrawBitmapData(graphic.searchBitmap("A6"),(int)(position_x+radius*0.8)-90, (int)(position_y+radius*0.6));}
                if(BattleBaseUnitData.ActionID.toEnum(i) == BattleBaseUnitData.ActionID.STOP){graphic.bookingDrawBitmapData(graphic.searchBitmap("Z6"),(int)(position_x+radius*0.8)-60, (int)(position_y+radius*0.6));}
                if(BattleBaseUnitData.ActionID.toEnum(i) == BattleBaseUnitData.ActionID.BLINDNESS){graphic.bookingDrawBitmapData(graphic.searchBitmap("A1"),(int)(position_x+radius*0.8)-30, (int)(position_y+radius*0.6));}
                if(BattleBaseUnitData.ActionID.toEnum(i) == BattleBaseUnitData.ActionID.CURSE){
                    paint.setARGB(255,100,50,50);
                    graphic.bookingDrawBitmapData(graphic.searchBitmap("A9"),(int)(position_x+radius*0.8), (int)(position_y+radius*0.6));
                    graphic.bookingDrawRect((int)(position_x+radius*0.9), (int)(position_y+radius*0.8), (int)(position_x+radius*1), (int)((position_y+radius*0.8-radius*1.6*((double)alimentCounts[BattleBaseUnitData.ActionID.CURSE.ordinal()-1]/(double)1000))),paint);
                }
            }
        }

        if (attack_frame > 0) {
            paint.setARGB(255, 255, 0, 0);
            graphic.bookingDrawRect((int) (position_x - radius * 0.8), (int) (position_y + radius * 0.9), (int) (((double) position_x - (double) radius * 0.8 + (double) radius * 1.6 * ((double) attackCount / (double) attack_frame))), (int) (position_y + radius * 1.0), paint);
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
        return attackCount;
    }
    @Override
    public void setWaitFrame(int _wait_frame) {
        attackCount = _wait_frame;
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
    public int[] getAlimentTime() { return battleDungeonUnitData.getAlimentTime(); }
    public int getAlimentTime(ActionID _actionRateID) { return battleDungeonUnitData.getAlimentTime(_actionRateID); }

    public ActionID checkActionID() {

        double action_rnd = Math.random();

        double store_action_rate = 0;
        for (int i = 0; i < BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal(); i++) {
            store_action_rate += actionRate[i];
            if (store_action_rate >= action_rnd) {
                if(ActionID.toEnum(i) != null) {
                    return ActionID.toEnum(i);
                }
            }
        }

        return ActionID.NORMAL_ATTACK;
    }



    // Rock用
    public BattleBaseUnitData getBattleBaseUnitDataForRock() {
        return battleBaseUnitDataForRock;
    }

    public void setBattleBaseUnitDataForRock(BattleBaseUnitData _battleBaseUnitDataForRock) {
        battleBaseUnitDataForRock = _battleBaseUnitDataForRock;
    }
}
