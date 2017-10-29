package com.maohx2.ina.Battle;


import static com.maohx2.ina.Constants.UnitStatus.Status.*;
import static com.maohx2.ina.Constants.UnitStatus.BonusStatus.*;

/**
 * Created by ina on 2017/10/20.
 */

//データベースからのデータを保存
public class BattleBaseUnitData {

    String name;
    int draw_id;
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

    int status[] = new int[NUM_OF_STATUS.ordinal()];
    int bonus_status[] = new int[NUM_OF_BONUS_STATUS.ordinal()];



    public void init(){

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
        delta_bonus_attack =3;
        delta_bonus_defence = 1;

        for (int i = 0; i < 5; i++) {
            status[i] = 0;
        }
    }

    public String getName(){return name;}

    public int[] getStatus(int repeat_count) {

        status[HP.ordinal()] = (int) (initial_hp + (delta_hp * Math.pow(2, repeat_count)));
        status[ATTACK.ordinal()] = (int)(initial_attack + delta_attack * Math.pow(2,repeat_count));
        status[DEFENSE.ordinal()] = (int)(initial_defence + delta_defence * Math.pow(2,repeat_count));
        status[LUCK.ordinal()] = (int)(initial_luck + delta_luck * Math.pow(2,repeat_count));
        status[ATTACK_FRAME.ordinal()] = attack_frame;

        return status;
    }

    public int[] getBonusStatus(int repeat_count) {

        bonus_status[BONUS_HP.ordinal()] = (int) (initial_bonus_hp + (delta_bonus_hp * Math.pow(2, repeat_count)));
        bonus_status[BONUS_ATTACK.ordinal()] = (int)(initial_bonus_attack + delta_bonus_attack * Math.pow(2,repeat_count));
        bonus_status[BONUS_DEFENSE.ordinal()] = (int)(initial_bonus_defence + delta_bonus_defence * Math.pow(2,repeat_count));

        return bonus_status;
    }


}
