package com.maohx2.ina.Battle;


//by kmhanko
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.Item.EQUIPMENT_KIND;
import com.maohx2.ina.Draw.BitmapData;

import static com.maohx2.ina.Constants.UnitStatus.BonusStatus.*;
import static com.maohx2.ina.Constants.UnitStatus.Status.*;

/**
 * Created by ina on 2017/10/20.
 */


/* MEMO

 *** made by ina ***
BattleUnitDataAdmin : DBから読み出しを行い、データをそのまま格納する
└管理 BattleBaseUnitData 敵キャラクターの計算を行う関数を持つ

BattleDungeonUnitData : 敵キャラクターのステータス計算を行った後のデータを保持

BattleUnitAdmin : BattleUnitらを管理するクラス
└管理 BattleUnit : ステータスや状態異常などを一括管理するための基底クラス。'現在の'HPなどを保持 (記載順序入れ替え by kmhanko)
     └継承 BattleEnemy : 敵に関するUnitデータ。位置情報を持つ。BattleDungeonUnitDataを保持する
     └継承 BattlePlayer : 自分に関するUnitデータ。位置情報を持たない。

CalcUnitStatus : 戦闘におけるステータス計算を行う関数を保持するクラス

 */



//データベースからのデータを保存
public class BattleBaseUnitData {

    String name;
    BitmapData bitmap_data;
    int radius;

    int power;

    //by kmhank　ドロップアイテム種類
    EQUIPMENT_KIND[] dropItemEquipmentKind = new EQUIPMENT_KIND[Constants.Item.DROP_NUM];
    String[] dropItemName = new String[Constants.Item.DROP_NUM];
    double[] dropItemRate = new double[Constants.Item.DROP_NUM];
    Constants.Item.ITEM_KIND[] dropItemKind = new Constants.Item.ITEM_KIND[Constants.Item.DROP_NUM];

    //配列変数の要素番号との対応のためのenum型
    public enum DbStatusID {
        AttackFlame,
        InitialHP,
        InitialAttack,
        InitialDefence,
        InitialLuck,
        InitialSpeed,
        DeltaHP,
        DeltaAttack,
        DeltaDefence,
        DeltaLuck,
        DeltaSpeed,
        InitialBonusHP,
        InitialBonusAttack,
        InitialBonusDefence,
        InitialBonusSpeed,
        DeltaBonusHP,
        DeltaBonusAttack,
        DeltaBonusDefence,
        DeltaBonusSpeed,
        DbStatusNum
    }

    public enum ActionID {
        NORMAL_ATTACK,
        POISON,
        PARALYSIS,
        STOP,
        BLINDNESS,
        CURSE,
        ACTION_ID_NUM;

        //enumへのキャスト用
        public static ActionID toEnum(int x) {
            for(ActionID type : ActionID.values()) {
                if (type.ordinal() == x) {
                    return type;
                }
            }
            return null;
        }
    }

    int[] dbStatus = new int[DbStatusID.DbStatusNum.ordinal()];


    int status[] = new int[NUM_OF_STATUS.ordinal()];
    int bonus_status[] = new int[NUM_OF_BONUS_STATUS.ordinal()];

    float[] actionRate = new float[ActionID.ACTION_ID_NUM.ordinal()];
    int[] alimentTime = new int[ActionID.ACTION_ID_NUM.ordinal()];

    public enum SpecialAction {
        NONE,
        BARRIER,
        COUNTER,
        STEALTH,
        SPECIAL_ACTION_NUM
    }

    SpecialAction specialAction;
    int specialActionPeriod;
    int specialActionWidth;


    public void init(){}

    //by kmhanko

    // *** setter & getter ***
    public int[] getStatus(int repeat_count, double mag) {
        status[ATTACK_FRAME.ordinal()] = dbStatus[DbStatusID.AttackFlame.ordinal()];
        /*
        if (repeat_count <= 0) {
            status[HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialHP.ordinal()]);
            status[ATTACK.ordinal()] = (int) (dbStatus[DbStatusID.InitialAttack.ordinal()]);
            status[DEFENSE.ordinal()] = (int) (dbStatus[DbStatusID.InitialDefence.ordinal()]);
            status[LUCK.ordinal()] = (int) (dbStatus[DbStatusID.InitialLuck.ordinal()]);
            status[SPEED.ordinal()] = (int) (dbStatus[DbStatusID.InitialSpeed.ordinal()]);

        } else {
            status[HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialHP.ordinal()] + (dbStatus[DbStatusID.DeltaHP.ordinal()] * Math.pow(2, repeat_count)));
            status[ATTACK.ordinal()] = (int) (dbStatus[DbStatusID.InitialAttack.ordinal()] + dbStatus[DbStatusID.DeltaAttack.ordinal()] * Math.pow(2, repeat_count));
            status[DEFENSE.ordinal()] = (int) (dbStatus[DbStatusID.InitialDefence.ordinal()] + dbStatus[DbStatusID.DeltaDefence.ordinal()] * Math.pow(2, repeat_count));
            status[LUCK.ordinal()] = (int) (dbStatus[DbStatusID.InitialLuck.ordinal()] + dbStatus[DbStatusID.DeltaLuck.ordinal()] * Math.pow(2, repeat_count));
            status[SPEED.ordinal()] = (int) (dbStatus[DbStatusID.InitialSpeed.ordinal()] + dbStatus[DbStatusID.DeltaSpeed.ordinal()] * Math.pow(2, repeat_count));
        }*/

        status[HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialHP.ordinal()] * Math.pow(mag, repeat_count));
        status[ATTACK.ordinal()] = (int) (dbStatus[DbStatusID.InitialAttack.ordinal()] * Math.pow(mag, repeat_count));
        status[DEFENSE.ordinal()] = (int) (dbStatus[DbStatusID.InitialDefence.ordinal()] * Math.pow(mag, repeat_count));
        status[LUCK.ordinal()] = (int) (dbStatus[DbStatusID.InitialLuck.ordinal()] * Math.pow(mag, repeat_count));
        status[SPEED.ordinal()] = (int) (dbStatus[DbStatusID.InitialSpeed.ordinal()]);

        return status;
    }

    //プレイヤーの成長するステータス量を、initialX + deltaX * 2^repeat_countで算出する関数
    public int[] getBonusStatus(int repeat_count, double mag) {
        /*
        if (repeat_count <= 0) {
            bonus_status[BONUS_HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusHP.ordinal()]);
            bonus_status[BONUS_ATTACK.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusAttack.ordinal()]);
            bonus_status[BONUS_DEFENSE.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusDefence.ordinal()]);
            bonus_status[BONUS_SPEED.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusSpeed.ordinal()]);
        } else {
            bonus_status[BONUS_HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusHP.ordinal()] + (dbStatus[DbStatusID.DeltaBonusHP.ordinal()] * Math.pow(2, repeat_count)));
            bonus_status[BONUS_ATTACK.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusAttack.ordinal()] + dbStatus[DbStatusID.DeltaBonusAttack.ordinal()] * Math.pow(2, repeat_count));
            bonus_status[BONUS_DEFENSE.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusDefence.ordinal()] + dbStatus[DbStatusID.DeltaBonusDefence.ordinal()] * Math.pow(2, repeat_count));
            bonus_status[BONUS_SPEED.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusSpeed.ordinal()] + dbStatus[DbStatusID.DeltaBonusSpeed.ordinal()] * Math.pow(2, repeat_count));
        }
        */
        bonus_status[BONUS_HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusHP.ordinal()] * Math.pow(mag, repeat_count));
        bonus_status[BONUS_ATTACK.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusAttack.ordinal()] * Math.pow(mag, repeat_count));
        bonus_status[BONUS_DEFENSE.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusDefence.ordinal()] * Math.pow(mag, repeat_count));
        //bonus_status[BONUS_SPEED.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusSpeed.ordinal()] * Math.pow(mag, repeat_count));


        return bonus_status;
    }

    //by kmhanko
    public void setDropItemEquipmentKind(int i, EQUIPMENT_KIND _bufEK) {
        dropItemEquipmentKind[i] = _bufEK;
    }
    public void setDropItemName(int i, String _dropItemName) {
        dropItemName[i] = _dropItemName;
    }
    public void setDropItemRate(int i, double _dropItemRate) {
        dropItemRate[i] = _dropItemRate;
    }
    public void setDropItemKind(int i, Constants.Item.ITEM_KIND _dropItemKind) { dropItemKind[i] = _dropItemKind; }

    public float[] getActionRate() { return actionRate; }
    public void setActionRate(ActionID _alimentID, float _actionRate) { actionRate[_alimentID.ordinal()] = _actionRate; }
    public float getActionRate(ActionID _alimentID) { return actionRate[_alimentID.ordinal()]; }

    public int[] getAlimentTime() { return alimentTime; }
    public void setAlimentTime(ActionID _alimentID, int _alimentTime) { alimentTime[_alimentID.ordinal()] = _alimentTime; }
    public int getAlimentTime(ActionID _alimentID) { return alimentTime[_alimentID.ordinal()]; }


    public EQUIPMENT_KIND[] getDropItemEquipmentKinds() {
        return dropItemEquipmentKind;
    }
    public String[] getDropItemNames() {
        return dropItemName;
    }
    public double[] getDropItemRate() {
        return dropItemRate;
    }
    public Constants.Item.ITEM_KIND[] getDropItemKinds() {
        return dropItemKind;
    }

    public void setSpecialAction(String name) {
        specialAction = SpecialAction.STEALTH;
        if (name == null) {
            specialAction = SpecialAction.NONE;
            return;
        }

        if (name.equals("barrier")) {
            specialAction = SpecialAction.BARRIER;
            return;
        }
        if (name.equals("counter")) {
            specialAction = SpecialAction.COUNTER;
            return;
        }
        if (name.equals("stealth")) {
            specialAction = SpecialAction.STEALTH;
            return;
        }
    }

    public String getName(){return name;}
    public  void setName(String _name){name = _name;}

    public BitmapData getBitmapData(){return bitmap_data;}
    public void setBitmapData(BitmapData _bitmap_data){bitmap_data = _bitmap_data;}

    public int getRadius(){return radius;}
    public void setRadius(int _radius){radius = _radius;}

    public void setDbStatus(DbStatusID _dbStatusID, int _dbStatus) { dbStatus[_dbStatusID.ordinal()] = _dbStatus; }

    public int getDbStatus(DbStatusID _dbStatusID) {
        return dbStatus[_dbStatusID.ordinal()];
    }

    public void setSpecialAction(SpecialAction _specialAction) { specialAction = _specialAction; }
    public SpecialAction getSpecialAction() { return specialAction; }
    public int getSpecialActionWidth() { return specialActionWidth; }
    public int getSpecialActionPeriod() { return specialActionPeriod; }
    public void setSpecialActionWidth(int _specialActionWidth) { specialActionWidth = _specialActionWidth; }
    public void setSpecialActionPeriod(int _specialActionPeriod) { specialActionPeriod = _specialActionPeriod; }

    public void setPower(int _power) { power = _power; }

    public int getPower() { return power; }


    public void release() {
        System.out.println("takanoRelease : BattleBaseUnitData");
        bitmap_data = null;
        dropItemEquipmentKind = null;
        dropItemName = null;
        dropItemRate = null;
        dropItemKind = null;
    }

}
