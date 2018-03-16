package com.maohx2.ina.Battle;


//by kmhanko
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

    int[] dbStatus = new int[DbStatusID.DbStatusNum.ordinal()];


    int status[] = new int[NUM_OF_STATUS.ordinal()];
    int bonus_status[] = new int[NUM_OF_BONUS_STATUS.ordinal()];



    public void init(){}

    // *** setter & getter ***
    public String getName(){return name;}
    public  void setName(String _name){name = _name;}

    public BitmapData getBitmapData(){return bitmap_data;}
    public void setBitmapData(BitmapData _bitmap_data){bitmap_data = _bitmap_data;}

    public int getRadius(){return radius;}
    public void setRadius(int _radius){radius = _radius;}




    //by kmhanko
    public void setDbStatus(DbStatusID _dbStatusID, int _dbStatus) {
        dbStatus[_dbStatusID.ordinal()] = _dbStatus;
    }

    public int getDbStatus(DbStatusID _dbStatusID) {
        return dbStatus[_dbStatusID.ordinal()];
    }

    //by kmhanko
    public int[] getStatus(int repeat_count) {

        status[ATTACK_FRAME.ordinal()] = dbStatus[DbStatusID.AttackFlame.ordinal()];
        status[HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialHP.ordinal()] + (dbStatus[DbStatusID.DeltaHP.ordinal()] * Math.pow(2, repeat_count)));
        status[ATTACK.ordinal()] = (int)(dbStatus[DbStatusID.InitialAttack.ordinal()] + dbStatus[DbStatusID.DeltaAttack.ordinal()] * Math.pow(2,repeat_count));
        status[DEFENSE.ordinal()] = (int)(dbStatus[DbStatusID.InitialDefence.ordinal()] + dbStatus[DbStatusID.DeltaDefence.ordinal()] * Math.pow(2,repeat_count));
        status[LUCK.ordinal()] = (int)(dbStatus[DbStatusID.InitialLuck.ordinal()] + dbStatus[DbStatusID.DeltaLuck.ordinal()] * Math.pow(2,repeat_count));
        status[SPEED.ordinal()] = (int)(dbStatus[DbStatusID.InitialSpeed.ordinal()] + dbStatus[DbStatusID.DeltaSpeed.ordinal()] * Math.pow(2,repeat_count));

        return status;
    }

    //プレイヤーの成長するステータス量を、initialX + deltaX * 2^repeat_countで算出する関数
    public int[] getBonusStatus(int repeat_count) {

        bonus_status[BONUS_HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusHP.ordinal()] + (dbStatus[DbStatusID.DeltaBonusHP.ordinal()] * Math.pow(2, repeat_count)));
        bonus_status[BONUS_ATTACK.ordinal()] = (int)(dbStatus[DbStatusID.InitialBonusAttack.ordinal()] + dbStatus[DbStatusID.DeltaBonusAttack.ordinal()] * Math.pow(2,repeat_count));
        bonus_status[BONUS_DEFENSE.ordinal()] = (int)(dbStatus[DbStatusID.InitialBonusDefence.ordinal()] + dbStatus[DbStatusID.DeltaBonusDefence.ordinal()] * Math.pow(2,repeat_count));
        bonus_status[BONUS_SPEED.ordinal()] = (int)(dbStatus[DbStatusID.InitialBonusSpeed.ordinal()] + dbStatus[DbStatusID.DeltaBonusSpeed.ordinal()] * Math.pow(2,repeat_count));

        return bonus_status;
    }


}
