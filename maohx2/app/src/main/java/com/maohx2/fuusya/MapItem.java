package com.maohx2.fuusya;


//import com.maohx2.ina.MySprite;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Battle.BattleBaseUnitData;
import com.maohx2.ina.Battle.BattleUnitDataAdmin;
import com.maohx2.horie.map.DungeonMonsterDataAdmin;
import com.maohx2.ina.Battle.BattleEnemy;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;

import java.util.Random;

import static com.maohx2.ina.Constants.BattleUnit.BATTLE_UNIT_MAX;
import static com.maohx2.ina.Constants.UnitStatus.BonusStatus.NUM_OF_BONUS_STATUS;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapItem extends MapInanimate {

    //Playerに対する当たり判定の半径
    double REACH_FOR_PLAYER = 150;

    int power = 0;
    int parameter = 0;
    float extend = 0;
    static int repeatCount;

    String name;

    PlayerStatus playerStatus;
    BattleUnitDataAdmin battleUnitDataAdmin;
    DungeonMonsterDataAdmin dungeonMonsterDataAdmin;

    public MapItem(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, PlayerStatus _playerStatus, BattleUnitDataAdmin _battleUnitDataAdmin, DungeonMonsterDataAdmin _dungeonMonsterDataAdmin) {
        super(graphic, _map_object_admin, _id, _camera);
        playerStatus = _playerStatus;
        battleUnitDataAdmin = _battleUnitDataAdmin;
        dungeonMonsterDataAdmin = _dungeonMonsterDataAdmin;

    }

    public void init() {
        super.init();

    }

    static public void setRepeatCount(int _repeatCount) {
        repeatCount = _repeatCount;
    }

    public void update() {
        super.update();

        if (exists == true) {

            if (player.isWithinReach(w_x, w_y, REACH_FOR_PLAYER) == true) {
                System.out.println("アイテム獲得");
                exists = false;
                sound_admin.play("cure00");

                switch (name) {
                    case ("HpUp"):
                        playerStatus.addBaseHP(parameter);
                        break;
                    case ("AtkUp"):
                        playerStatus.addBaseAttack(parameter);
                        break;
                    case ("DefUp"):
                        playerStatus.addBaseDefence(parameter);
                        break;
                    case ("LuckUp"):
                        playerStatus.addBaseLuck(parameter);
                        break;
                }

                playerStatus.calcStatus();
                playerStatus.save();

                //古い処理
//            bag_Item_admin.setItemIdToBagItem(map_Item[i].getId());//アイテムidを引き渡す
//            map_Item[i].setExists(false);
            }

        }

    }

    public void setId(int _id) {

        id = _id;
    }

    /**
     * Created by Fuusya on 2018/04/23.
     */


    public void setName(String _name) {
        if (_name.equals("HpUp") || _name.equals("AtkUp") || _name.equals("DefUp") || _name.equals("LuckUp")) {
            name = _name;
        } else {
            throw new Error("%☆◆フジワラ:spawnMapObject()で間違ったItem名を渡している");//アプリを落とす
        }
    }

    Random random = new Random();
    public void settingPower() {
        power = random.nextInt(3);
        switch (power) {
            case 0 :
                extend = 0.5f;
                break;
            case 1 :
                extend = 0.7f;
                break;
            case 2 :
                extend = 1.0f;
                break;
        }

        settingParameter();
    }

    public float getExtend() {
        return extend;
    }

    public void settingParameter() {
        int bonus_status[] = new int[NUM_OF_BONUS_STATUS.ordinal()];
        BattleBaseUnitData tempBattleBaseUnitData = battleUnitDataAdmin.getRandomBattleBaseUnitDataExceptBoss(dungeonMonsterDataAdmin);
        if ( tempBattleBaseUnitData == null) {
            parameter = 0;
            return;
        }
        bonus_status = tempBattleBaseUnitData.getBonusStatus(repeatCount, 5.024);

        switch (name) {
            case ("HpUp"):
                parameter = bonus_status[Constants.UnitStatus.BonusStatus.BONUS_HP.ordinal()];
                break;
            case ("AtkUp"):
                parameter = bonus_status[Constants.UnitStatus.BonusStatus.BONUS_ATTACK.ordinal()];
                break;
            case ("DefUp"):
                parameter = bonus_status[Constants.UnitStatus.BonusStatus.BONUS_DEFENSE.ordinal()];
                break;
            case ("LuckUp"):
                parameter = bonus_status[Constants.UnitStatus.BonusStatus.BONUS_SPEED.ordinal()];
                break;
        }

        switch (power) {
            case 1:
                parameter = (int)(parameter * 1.3);
            case 2:
                parameter = (int)(parameter * 1.5);
        }
    }


    public static class MapInventryAdmin {
    }
}
