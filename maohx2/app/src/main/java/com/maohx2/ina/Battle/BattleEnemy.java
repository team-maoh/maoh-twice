package com.maohx2.ina.Battle;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.effect.EffectAdmin;

import static com.maohx2.ina.Constants.BattleUnit.ATTACK_SCALE;
import static com.maohx2.ina.Constants.BattleUnit.NORMAL_SCALE;
import static com.maohx2.ina.Constants.UnitStatus.Status.*;

import static com.maohx2.ina.Battle.BattleBaseUnitData.SpecialAction;
import static com.maohx2.ina.Battle.BattleBaseUnitData.ActionID;

import java.util.Random;

/**
 * Created by ina on 2017/09/21.
 */


/*　やること
採掘ポイント数を増やす
ダンジョンの広さが広すぎる
海あたりからプレイヤーの火力がインフレする。ダメージ式を調整する必要
ジオの能力アップがややしょぼい

ダンジョンゲートやリタイアで選択肢を出す
森以外の飾りマス
装備画面における、ポーションの自動セット
装備スロットの操作性問題
主人公などの壁との当たり判定
画面に触れ続けているとボタンを押した判定になる問題
エフェクトの重さ改善
ジオマップで最初エフェクトが出ない
ジオマップの未開放感

ローグ
AIバグ
30Fにする
敵の強さ調整
必殺技エフェクト実装
必殺技実装
オープニング落下アニメ
エンディングシーン
時間回復アイテム
*/

public class BattleEnemy extends BattleUnit {

    double position_x;
    double position_y;
    double radius;
    private double scale;
    int uiid;
    int attackCount;
    int attack_frame;
    int next_attack_frame;
    int specialActionCount;

    float[] actionRate = new float[BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal()];
    int[] alimentTime = new int[BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal()];
    BattleBaseUnitData.SpecialAction specialAction;
    int specialActionPeriod;
    int specialActionWidth;
    boolean is_damaged;

    protected BattleBaseUnitData battleBaseUnitDataForRock;

    @Override
    public void clear() {
        super.clear();
        position_x = 0;
        position_y = 0;
        radius = 0.0;
        uiid = 0;
        attackCount = 0;
        attack_frame = 0;
        next_attack_frame = 0;
        specialActionCount = 0;
        specialActionFlag = false;
        scale = NORMAL_SCALE;
        float[] actionRate = new float[BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal()];
        int[] alimentTime = new int[BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal()];
        specialAction = SpecialAction.NONE;
        specialActionPeriod = 0;
        specialActionWidth = 0;
        is_damaged = false;
    }

    public BattleEnemy(Graphic _graphic, EffectAdmin _effectAdmin, EffectAdmin _backEnemyEffectAdmin){
        super(_graphic, _effectAdmin, _backEnemyEffectAdmin);
        clear();
    }

    //--エフェクト関係
    int damagedEffect;
    int attackEffect;

    int id;

    int width;
    int height;

    int damageEffectTime;

    float attackExtendX;
    float attackExtendY;

    float damagedExtendX;
    float damagedExtendY;

    final int damageEffectInterval = 8;

    protected void initEffect(int _id) {

        id = _id;
        damageEffectTime = 0;
        width = getBattleDungeonUnitData().getBitmapDate().getWidth();
        height = getBattleDungeonUnitData().getBitmapDate().getHeight();

        attackExtendX = (float)(width+height)/2.0f/(768.0f/4.0f*2.0f)*1.3f;
        attackExtendY = (float)(width+height)/2.0f/(768.0f/4.0f*2.0f)*1.3f;

        damagedExtendX = (float)(300.0f)/2.0f/(960.0f/5.0f*2.0f)*1.3f;
        damagedExtendY = (float)(300.0f)/2.0f/(960.0f/5.0f*2.0f)*1.3f;
        //effectAdmin.createEffect("enemy_damaged_effect" , "bomb_effect", 5, 2, damagedExtendX, damagedExtendY, 1, EffectAdmin.EXTEND_MODE.BEFORE);
        effectAdmin.createEffect("enemy_damaged_effect" , "bomb_effect", 5, 2, damagedExtendX, damagedExtendY, 1, EffectAdmin.EXTEND_MODE.AFTER);

        //backEnemyEffectAdmin.createEffect("enemy_attack_effect", "enemy_attack", 4, 2, attackExtendX, attackExtendY, 1, EffectAdmin.EXTEND_MODE.BEFORE);
        backEnemyEffectAdmin.createEffect("enemy_attack_effect", "enemy_attack", 4, 2, attackExtendX, attackExtendY, 1, EffectAdmin.EXTEND_MODE.AFTER);

    }
    protected void damagedEffectStart() {
        if (getUnitKind() != Constants.UnitKind.ENEMY) {
            return;
        }
        if (damageEffectTime >= damageEffectInterval) {
            effectAdmin.getEffect(damagedEffect).clear();
            //damagedEffect = effectAdmin.createEffect("enemy_damaged_effect", "bomb_effect", 5, 2, damagedExtendX, damagedExtendY, 1, EffectAdmin.EXTEND_MODE.BEFORE);
            damagedEffect = effectAdmin.createEffect("enemy_damaged_effect", "bomb_effect", 5, 2, damagedExtendX, damagedExtendY, 1, EffectAdmin.EXTEND_MODE.AFTER);
            effectAdmin.getEffect(damagedEffect).setPosition((int) position_x + rnd.nextInt((int)(width*scale) + 1) - (int)(width*scale)/2, (int) position_y + rnd.nextInt((int)(scale*height) + 1) - (int)(scale*height)/2);
            effectAdmin.getEffect(damagedEffect).start();
            damageEffectTime = 0;
        }
    }
    Random rnd = new Random();
    protected void attackEffectStart() {
        if (getUnitKind() != Constants.UnitKind.ENEMY) {
            return;
        }
        backEnemyEffectAdmin.getEffect(attackEffect).clear();
        //attackEffect = backEnemyEffectAdmin.createEffect("enemy_attack_effect", "enemy_attack", 4, 2, attackExtendX, attackExtendY,  1, EffectAdmin.EXTEND_MODE.BEFORE);
        attackEffect = backEnemyEffectAdmin.createEffect("enemy_attack_effect", "enemy_attack", 4, 2, attackExtendX, attackExtendY,  1, EffectAdmin.EXTEND_MODE.AFTER);
        backEnemyEffectAdmin.getEffect(attackEffect).setPosition((int) position_x, (int) position_y);
        backEnemyEffectAdmin.getEffect(attackEffect).start();
    }
    //エフェクト関係ここまで


    @Override
    protected void statusInit() {
        super.statusInit();
        attack_frame = battleDungeonUnitData.getStatus(ATTACK_FRAME);
        next_attack_frame = attack_frame + rnd.nextInt(2*attack_frame);

        specialAction = getSpecialAction();
        actionRate = getActionRate();
        specialActionPeriod = getSpecialActionPeriod();
        specialActionWidth = getSpecialActionWidth();
        alimentTime = getAlimentTime();

        attackCount = -15;
    }

    @Override
    public int update(){

        super.update();

        backEnemyEffectAdmin.getEffect(attackEffect).setPosition((int) position_x, (int) position_y);
        if (damageEffectTime < damageEffectInterval) {
            damageEffectTime++;
        }

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
        if(attackCount == next_attack_frame){
            scale = ATTACK_SCALE;
            attackCount = 0;
            next_attack_frame = attack_frame + rnd.nextInt(2*attack_frame);
            //敵の攻撃エフェクト
            this.attackEffectStart();
            return attack;
        }

        if(attackCount == Math.min(attack_frame/2, 5)){
            scale = NORMAL_SCALE;
        }

        return 0;
    }

    @Override
    public void draw(){

        //graphic.bookingDrawText(String.valueOf(hit_point),(int)position_x,(int)position_y);

        if (getUnitKind() == Constants.UnitKind.ENEMY) {

            if (specialActionFlag == true) {

                switch (specialAction) {
                    case BARRIER:
                        graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(), (int) position_x, (int) position_y, (float) scale, (float) scale, 0, 255, false);
                        paint.setARGB(100, 0, 0, 255);
                        graphic.bookingDrawCircle((int) position_x, (int) position_y, (int) radius, paint);
                        break;
                    case COUNTER:
                        graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(), (int) position_x, (int) position_y, (float) scale, (float) scale, 0, 255, false);
                        paint.setARGB(100, 255, 100, 0);
                        graphic.bookingDrawCircle((int) position_x, (int) position_y, (int) radius, paint);
                        break;
                    case STEALTH:
                        graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(), (int) position_x, (int) position_y, (float) scale, (float) scale, 0, 100, false);
                        break;
                }
            } else {
                graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(), (int) position_x, (int) position_y, (float) scale, (float) scale, 0, 255, false);
            }
        }
        if (getUnitKind() == Constants.UnitKind.ROCK) {
            graphic.bookingDrawBitmapData(battleDungeonUnitData.getBitmapDate(), (int) position_x, (int) position_y, (float) 1.0, (float) 1.0, 0, 255, false);
        }

        //HP表示
        if (hit_point > 0) {
            paint.setARGB(255, 0, 255, 0);
            graphic.bookingDrawRect((int) (position_x - radius * 0.8), (int) (position_y + radius * 1.1), (int) (((double) position_x - (double) radius * 0.8 + (double) radius * 1.6 * ((double) hit_point / (double) max_hit_point))), (int) (position_y + radius * 1.2), paint);
        } else {
            if (unitKind == Constants.UnitKind.ROCK) {
                //オーバーキルゲージの表示
                paint.setARGB(255, 255, 0, 0);
                graphic.bookingDrawRect((int) (position_x - radius * 0.8), (int) (position_y + radius * 1.1), (int) (((double) position_x - (double) radius * 0.8 + (double) radius * 1.6 * ((double) -hit_point / (double) max_hit_point))), (int) (position_y + radius * 1.2), paint);
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
            if(attackCount >= 0){ graphic.bookingDrawRect((int) (position_x - radius * 0.8), (int) (position_y + radius * 1.2), (int) (((double) position_x - (double) radius * 0.8 + (double) radius * 1.6 * ((double) attackCount / (double) next_attack_frame))), (int) (position_y + radius * 1.3), paint);}
            else{graphic.bookingDrawRect((int) (position_x - radius * 0.8), (int) (position_y + radius * 1.2), (int) (((double) position_x - (double) radius * 0.8 + (double) radius * 1.6 * ((double)0 / (double) next_attack_frame))), (int) (position_y + radius * 1.3), paint);}
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
        return scale*radius;
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
    @Override
    public void setDamagedFlag(boolean _flag){
        is_damaged = _flag;
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
