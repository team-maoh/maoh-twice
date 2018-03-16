package com.maohx2.ina.Battle;

import android.app.Activity;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;

import static com.maohx2.ina.Constants.BattleUnit.BATTLE_UNIT_MAX;
import static com.maohx2.ina.Constants.Touch.TouchState;

import com.maohx2.ina.Battle.*;

/**
 * Created by ina on 2017/09/21.
 */

//BattleUnitを一括管理するクラス
public class BattleUnitAdmin {

    BattleUserInterface battle_user_interface;
    BattleUnit[] battle_units = new BattleUnit[BATTLE_UNIT_MAX];
    int battle_palette_mode;
    CalcUnitStatus calc_unit_status;
    Activity battle_activity;
    TouchMarker[] touch_markers = new TouchMarker[10000];

    //by kmhanko
    BattleUnitDataAdmin battleUnitDataAdmin;
    int count = 0;
    Paint paint = new Paint();

    Graphic graphic;



    //by kmhanko BattleUnitDataAdmin追加
    public void init(Graphic _graphic, BattleUserInterface _battle_user_interface, Activity _battle_activity, BattleUnitDataAdmin _battleUnitDataAdmin, PlayerStatus _playerStatus) {

        //引数の代入
        graphic = _graphic;
        battle_user_interface = _battle_user_interface;
        battle_activity = _battle_activity;
        battleUnitDataAdmin = _battleUnitDataAdmin;

        //BattleUnit配列のインスタンス化・初期化
        battle_units[0] = new BattlePlayer(graphic);
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i] = new BattleEnemy(graphic);
        }
        for (int i = 0; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].init();
        }
        for (int i = 0; i < 10000; i++) {
            touch_markers[i] = new TouchMarker(graphic);
        }

        //CalcUnitStatusのインスタンス化・初期化
        calc_unit_status = new CalcUnitStatus();
        calc_unit_status.init();


        /*
        //削除 仮に敵を適当な位置に配置する setBattleUnitDataに入れた
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].setPositionX(200 + 200 * (i - 1));
            battle_units[i].setPositionY(300);
            battle_units[i].setRadius(50);
        }

        //削除 仮に敵にUIのタッチIDを割り振る setBattleUnitDataに入れた
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            battle_units[i].setUIID(battle_user_interface.setCircleTouchUI(battle_units[i].getPositionX(), battle_units[i].getPositionY(), battle_units[i].getRadius()));
        }
        */

        //TODO 仮。プレイヤーデータのコンバート
        setPlayer(_playerStatus);

        //TODO 仮。敵の生成。本当はここじゃなくて戦闘画面に移動する画面エフェクト的な奴を処理した後とかに呼ぶとかする
        spawnEnemy();
    }

    //by kmhanko
    //BattleUnitDataAdminを使用し、敵情報をBattleUnit配列にセットする関数 enemy(i >=1 )にしか対応していない点に注意
    public int setBattleUnitData(String enemyName, int repeatCount) {
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (!battle_units[i].isExist()) {
                BattleBaseUnitData tempBattleBaseUnitData = battleUnitDataAdmin.getBattleUnitDataNum(enemyName);
                battle_units[i].setBattleUnitData(tempBattleBaseUnitData, repeatCount, i);//TODO: iはそのうち消す
                return i;
            }
        }
        return -1;
    }

    public void setPlayer(PlayerStatus playerStatus) {
        //TODO
        playerStatus.calcStatus();
        //battle_units[0].setBattleUnitData(playerStatus.makeBattleDungeonUnitData());
    }

    public void spawnEnemy() {
        //TODO 仮。本当はダンジョンのデータなどを引数にして出現する敵をランダムなどで決定する
        setBattleUnitData("m014", 1);
        setBattleUnitData("e54-3", 2);
        setBattleUnitData("m014", 3);
        setBattleUnitData("e54-3", 4);
    }


    public void update() {

        double touch_x = battle_user_interface.getTouchX();
        double touch_y = battle_user_interface.getTouchY();
        TouchState touch_state = battle_user_interface.getTouchState();

        for (int i = 0; i < 10000; i++) {
            if (touch_markers[i].isExist() == true) {
                touch_markers[i].update();
            }
        }

        System.out.println("touch_state:" + touch_state);

        //プレイヤーの攻撃によるマーカーの設置
        if ((touch_state == TouchState.DOWN) || (touch_state == TouchState.DOWN_MOVE) || (touch_state == TouchState.MOVE)) {
            count++;
            System.out.println("count:" + count);


            count = 0;

            for (int i = 0; i < 10000; i++) {
                if (touch_markers[i].isExist() == false) {
                    touch_markers[i].generate((int) touch_x, (int) touch_y, 100,10, 0.9);
                    break;
                }
            }
        }



        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isExist() == true) {
                int ex = battle_units[i].getPositionX();
                int ey = battle_units[i].getPositionY();
                int er = battle_units[i].getRadius();
                for(int j = 0; j< 10000; j++) {
                    if (touch_markers[j].isExist() == true) {
                        int cx = touch_markers[j].getPositionX();
                        int cy = touch_markers[j].getPositionY();
                        int cr = touch_markers[j].getRadius();

                        if((ex-cx)*(ex-cx)+(ey-cy)*(ey-cy)<(er-cr)*(er-cr)){
                            battle_units[i].setHitPoint(battle_units[i].getHitPoint() - touch_markers[j].getDamage());
                        }
                    }
                }
            }
        }



        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isExist() == true) {
                int id = battle_units[i].getUIID();

                if (battle_user_interface.checkUI(id, Constants.Touch.TouchWay.DOWN_MOMENT) == true) {
                    battle_units[0].setAttackUnitNum(i);
                }
            }
        }


        //敵の更新と攻撃処理
        for (int i = 0; i < BATTLE_UNIT_MAX; i++) {
            if (battle_units[i].isExist() == true) {
                //   battle_units[i].update();
            }
        }

        //HP更新
        for (int i = 0; i < BATTLE_UNIT_MAX; i++) {
            int attack_unit_num = battle_units[i].getAttackUnitNum();
            if (attack_unit_num != -1) {

                BattleUnit attack_unit = battle_units[i];
                BattleUnit attacked_unit = battle_units[attack_unit_num];

                int new_hit_point = attacked_unit.getHitPoint() - attack_unit.getAttack();

                if (new_hit_point > 0) {
                    attacked_unit.setHitPoint(new_hit_point);
                    //System.out.println(i + "→" + attack_unit_num + "      " + battle_units[attack_unit_num].getHitPoint());
                } else {
                    attacked_unit.setExist(false);
                    calc_unit_status.breakUnit(attack_unit, attacked_unit);
                }
                battle_units[i].setAttackUnitNum(-1);
            }
        }

        boolean result_flag = true;
        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {
            result_flag = result_flag & !battle_units[i].isExist();
        }

        /*
        //リザルト演出
        if (result_flag == true) {
            battle_activity.finish();
        }
        */

    }

    public void draw() {


        for(int i = 0; i < 10000; i++) {
            if(touch_markers[i].isExist() == true) {
                touch_markers[i].draw();
            }
        }

        for (int i = 1; i < BATTLE_UNIT_MAX; i++) {

            if (battle_units[i].isExist() == true) {

                battle_units[i].draw();

                //canvas.drawCircle(battle_units[i].getPositionX(), battle_units[i].getPositionY(), 50.0f, paint);

                //by kmhanko
                //graphic.bookingDrawBitmapName(battle_units[i].getName(), battle_units[i].getPositionX(), battle_units[i].getPositionY());

                /*
                if(i%2 == 0) {
                    graphic.bookingDrawBitmapName("ゴキ", battle_units[i].getPositionX(), battle_units[i].getPositionY());
                }

                if(i%2 == 1) {
                    graphic.bookingDrawBitmapName("apple", battle_units[i].getPositionX(), battle_units[i].getPositionY());
                }
                */
            }

        }

        //デバッグ用 プレイヤーのステータスを表示
        debugPlayerStatusDraw();

    }

    //by kmhanko
    Paint playerStatusPaint = new Paint();
    public void debugPlayerStatusDraw() {
        //playerStatusPaint.setTextSize(50);
        //graphic.bookingDrawText(battle_units[0].getName(), 0, 50, playerStatusPaint);
        //graphic.bookingDrawText(battle_units[0].getHitPoint() + " / " + battle_units[0].getMaxHitPoint(), 0, 100, playerStatusPaint);
        //graphic.bookingDrawText(""+ battle_units[0].getAttack(), 0, 150, playerStatusPaint);
        //graphic.bookingDrawText(""+battle_units[0].getDefence(), 0, 200, playerStatusPaint);
        //graphic.bookingDrawText(""+battle_units[0].getLuck(), 0, 250, playerStatusPaint);
    }


    public BattleUnitAdmin() {}

}
