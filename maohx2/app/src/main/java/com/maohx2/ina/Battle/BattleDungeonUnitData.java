package com.maohx2.ina.Battle;

import com.maohx2.ina.Constants;

import static com.maohx2.ina.Constants.UnitStatus.BonusStatus.*;
import static com.maohx2.ina.Constants.UnitStatus.Status.*;

/**
 * Created by ina on 2017/10/20.
 */

//そのダンジョン（周回回数やダンジョンレベルを考慮した）でのユニットの強さを保存したもので、これをユニットが持つ
public class BattleDungeonUnitData {

    String name;
/*    int draw_id;
    int hp;
    int attack;
    int defence;
    int luck;
    int bonus_hp;
    int bonus_attack;
    int bonus_defence;
  */
    int[] status = new int[NUM_OF_STATUS.ordinal()];
    int[] bonus_status = new int[NUM_OF_BONUS_STATUS.ordinal()];


    public void init(){}

    public  String getName(){return  name;}

    public void setStatus(int[] _status){
        status = _status;
    }

    public void setBonusStatus(int[] _bonus_status){
        bonus_status = _bonus_status;
    }

}
