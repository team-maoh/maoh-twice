package com.maohx2.ina.Battle;

import static com.maohx2.ina.Constants.UnitStatus.BonusStatus;
import static com.maohx2.ina.Constants.UnitStatus.Status;

/**
 * Created by ina on 2017/10/20.
 */

//そのダンジョン（周回回数やダンジョンレベルを考慮した）でのユニットの強さを保存したもので、これをユニットが持つ
//takano : 敵キャラクターの能力値の上昇を計算した結果のステータスを保存したもの。
public class BattleDungeonUnitData {

    String name;

    int draw_id;

    //by kmhanko status, bonus_statusがあるため不要と
    /*
    int hp;
    int attack;
    int defence;
    int luck;

    int bonus_hp;
    int bonus_attack;
    int bonus_defence;
    */

    int[] status = new int[Status.NUM_OF_STATUS.ordinal()];
    int[] bonus_status = new int[BonusStatus.NUM_OF_BONUS_STATUS.ordinal()];

    public void init(){}

    // *** setter & getter ***
    public String getName(){ return name; }
    public void setStatus(int[] _status){
        status = _status;
    }
    public void setBonusStatus(int[] _bonus_status){
        bonus_status = _bonus_status;
    }

    //by kmhanko
    public int getStatus(Status _status) { return status[_status.ordinal()]; }
    public int getBonusStatus(BonusStatus _bonusStatus) { return bonus_status[_bonusStatus.ordinal()]; }
    public void setName(String _name) { name = _name; }

    /*
    public void setHP(int _hp) { hp = _hp; }
    public void setAttack(int _attack) { attack = _attack; }
    public void setDefence(int _defence) { defence = _defence; }
    public void setLuck(int _luck) { luck = _luck; }
    public void setBonusHP(int _bonus_hp) { bonus_hp = _bonus_hp; }
    public void setBonusAttack(int _bonus_attack) { bonus_attack = _bonus_attack; }
    public void setBonusDefence(int _bonus_defence) { bonus_defence = _bonus_defence; }
    */


}
