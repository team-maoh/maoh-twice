package com.maohx2.ina.Battle;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.maohx2.ina.UI.BattleUserInterface;

import static com.maohx2.ina.Constants.Touch.TouchState;
import static com.maohx2.ina.Constants.BattleUnit.*;

/**
 * Created by ina on 2017/09/21.
 */

public class BattleUnitAdmin {

    BattleUserInterface battle_user_interface;
    BattleUnit[] battle_units = new BattleUnit[BATTLE_UNIT_MAX];
    int battle_palette_mode;
    CalcUnitStatus calc_unit_status;

    Paint paint = new Paint();

    public void init(BattleUserInterface _bttle_user_interface) {

        battle_user_interface = _bttle_user_interface;

        battle_units[0] = new BattlePlayer();
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i] = new BattleEnemy();
        }

        calc_unit_status = new CalcUnitStatus();



        for (int i = 0; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].init();
        }

        calc_unit_status.init();

        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].setPositionX(200 + 200 * (i - 1));
            battle_units[i].setPositionY(300);
            battle_units[i].setRadius(50);
        }

        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].setUIID(battle_user_interface.setCircleTouchUI(battle_units[i].getPositionX(), battle_units[i].getPositionY(), battle_units[i].getRadius()));
        }
    }



    public void update() {

        double touch_x = battle_user_interface.getTouchX();
        double touch_y = battle_user_interface.getTouchY();
        TouchState touch_state = battle_user_interface.getTouchState();


        if (touch_state == TouchState.DOWN) {

            for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
                if (battle_units[i].isExist() == true) {
                    int id = battle_units[i].getUIID();

                    if (battle_user_interface.checkUI(id) == true) {
                        battle_units[0].setAttackUnitNum(i);
                    }
                }
            }
        }


        //HP更新
        if (touch_state == TouchState.DOWN) {
            for (int i = 0; i < BATTLE_UNIT_MAX; i++) {
                int attack_unit_num = battle_units[i].getAttackUnitNum();
                if (attack_unit_num != -1) {

                    BattleUnit attack_unit = battle_units[i];
                    BattleUnit attacked_unit = battle_units[attack_unit_num];

                    int new_hit_point = attacked_unit.getHitPoint() - attack_unit.getAttack();

                    if (new_hit_point > 0) {
                        attacked_unit.setHitPoint(new_hit_point);
                        System.out.println(battle_units[attack_unit_num].getHitPoint());
                    } else {
                        attacked_unit.setExist(false);
                        calc_unit_status.breakUnit(attack_unit, attacked_unit);
                    }
                    battle_units[i].setAttackUnitNum(-1);
                }
            }
        }
    }


    public void draw(Canvas canvas) {

        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isExist() == true) {
                canvas.drawCircle(battle_units[i].getPositionX(), battle_units[i].getPositionY(), 50.0f, paint);
            }
        }
    }


    public BattleUnitAdmin() {
    }

}
