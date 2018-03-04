package com.maohx2.ina.Battle;


//by kmhanko
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
     └継承 BattleUnit : 自分に関するUnitデータ。位置情報を持たない。

CalcUnitStatus : 戦闘におけるステータス計算を行う関数を保持するクラス

 */



//データベースからのデータを保存
public class BattleBaseUnitData {

    String name;
    int draw_id;

    /*
    int initial_hp;
    int initial_attack;
    int initial_defence;
    int initial_luck;
    int delta_hp;
    int delta_attack;
    int delta_defence;
    int delta_luck;

    int attack_frame;

    int initial_bonus_hp;
    int initial_bonus_attack;
    int initial_bonus_defence;
    int delta_bonus_hp;
    int delta_bonus_attack;
    int delta_bonus_defence;
    */

    //配列変数の要素番号との対応のためのenum型
    public enum DbStatusID {
        InitialHP,
        InitialAttack,
        InitialDefence,
        InitialLuck,
        DeltaHP,
        DeltaAttack,
        DeltaDefence,
        DeltaLuck,
        AttackFlame,
        InitialBonusHP,
        InitialBonusAttack,
        InitialBonusDefence,
        DeltaBonusHP,
        DeltaBonusAttack,
        DeltaBonusDefence,
        DbStatusNum
    }

    int[] dbStatus = new int[DbStatusID.DbStatusNum.ordinal()];


    int status[] = new int[NUM_OF_STATUS.ordinal()];
    int bonus_status[] = new int[NUM_OF_BONUS_STATUS.ordinal()];



    public void init(){

        /*
        name = "黒丸";
        draw_id = 0;
        initial_hp = 1000;
        initial_attack = 10;
        initial_defence = 3;
        initial_luck = 10;
        delta_hp = 500;
        delta_attack = 20;
        delta_defence = 3;
        delta_luck = 5;

        attack_frame = 100;

        initial_bonus_hp = 10;
        initial_bonus_attack = 2;
        initial_bonus_defence = 1;

        delta_bonus_hp = 10;
        delta_bonus_attack = 3;
        delta_bonus_defence = 1;

        for (int i = 0; i < 5; i++) {
            status[i] = 0;
        }
        */
    }

    // *** setter & getter ***
    public String getName(){return name;}
    public  void setName(String _name){name = _name;}

    //by kmhanko
    public void setDbStatus(DbStatusID _dbStatusID, int _dbStatus) {
        dbStatus[_dbStatusID.ordinal()] = _dbStatus;
    }
    public int getDbStatus(DbStatusID _dbStatusID) {
        return dbStatus[_dbStatusID.ordinal()];
    }


    //ステータスを、initialX + deltaX * 2^repeat_countで算出する関数
    /*
    public int[] getStatus(int repeat_count) {

        status[HP.ordinal()] = (int) (initial_hp + (delta_hp * Math.pow(2, repeat_count)));
        status[ATTACK.ordinal()] = (int)(initial_attack + delta_attack * Math.pow(2,repeat_count));
        status[DEFENSE.ordinal()] = (int)(initial_defence + delta_defence * Math.pow(2,repeat_count));
        status[LUCK.ordinal()] = (int)(initial_luck + delta_luck * Math.pow(2,repeat_count));
        status[ATTACK_FRAME.ordinal()] = attack_frame;

        return status;
    }
    */
    //by kmhanko
    public int[] getStatus(int repeat_count) {

        status[HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialHP.ordinal()] + (dbStatus[DbStatusID.DeltaHP.ordinal()] * Math.pow(2, repeat_count)));
        status[ATTACK.ordinal()] = (int)(dbStatus[DbStatusID.InitialAttack.ordinal()] + dbStatus[DbStatusID.DeltaAttack.ordinal()] * Math.pow(2,repeat_count));
        status[DEFENSE.ordinal()] = (int)(dbStatus[DbStatusID.InitialDefence.ordinal()] + dbStatus[DbStatusID.DeltaDefence.ordinal()] * Math.pow(2,repeat_count));
        status[LUCK.ordinal()] = (int)(dbStatus[DbStatusID.InitialLuck.ordinal()] + dbStatus[DbStatusID.DeltaLuck.ordinal()] * Math.pow(2,repeat_count));
        status[ATTACK_FRAME.ordinal()] = dbStatus[DbStatusID.AttackFlame.ordinal()];

        return status;
    }

    //プレイヤーの成長するステータス量を、initialX + deltaX * 2^repeat_countで算出する関数
    public int[] getBonusStatus(int repeat_count) {

        bonus_status[BONUS_HP.ordinal()] = (int) (dbStatus[DbStatusID.InitialBonusHP.ordinal()] + (dbStatus[DbStatusID.DeltaBonusHP.ordinal()] * Math.pow(2, repeat_count)));
        bonus_status[BONUS_ATTACK.ordinal()] = (int)(dbStatus[DbStatusID.InitialBonusAttack.ordinal()] + dbStatus[DbStatusID.DeltaBonusAttack.ordinal()] * Math.pow(2,repeat_count));
        bonus_status[BONUS_DEFENSE.ordinal()] = (int)(dbStatus[DbStatusID.InitialBonusDefence.ordinal()] + dbStatus[DbStatusID.DeltaBonusDefence.ordinal()] * Math.pow(2,repeat_count));

        return bonus_status;
    }


}
