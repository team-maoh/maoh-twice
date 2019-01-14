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
import com.maohx2.kmhanko.effect.EffectAdmin;

import java.io.PrintStream;
import java.util.Random;

import static com.maohx2.ina.Constants.BattleUnit.BATTLE_UNIT_MAX;
import static com.maohx2.ina.Constants.UnitStatus.BonusStatus.NUM_OF_BONUS_STATUS;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapItem extends MapInanimate {

    //Playerに対する当たり判定の半径
    double REACH_FOR_PLAYER = 150;

    int parameter = 0;
    float extend = 0.0f;
    static int repeatCount;

    int effectID;

    String name;

    PlayerStatus playerStatus;
    BattleUnitDataAdmin battleUnitDataAdmin;
    DungeonMonsterDataAdmin dungeonMonsterDataAdmin;

    static EffectAdmin backEffectAdmin;
    static EffectAdmin effectAdmin;


    static public void setBackEffectAdmin(EffectAdmin _backEffectAdmin) {
        backEffectAdmin = _backEffectAdmin;
    }
    static public void setEffectAdmin(EffectAdmin _effectAdmin) {
        effectAdmin = _effectAdmin;
    }

    public MapItem(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, PlayerStatus _playerStatus, BattleUnitDataAdmin _battleUnitDataAdmin, DungeonMonsterDataAdmin _dungeonMonsterDataAdmin) {
        super(graphic, _map_object_admin, _id, _camera);
        playerStatus = _playerStatus;
        battleUnitDataAdmin = _battleUnitDataAdmin;
        dungeonMonsterDataAdmin = _dungeonMonsterDataAdmin;

    }

    public void init() {
        super.init();

    }

    public void makeEffect() {//settingParaより後、nameより後、IDより後
        String effectImageName = "";
        switch (name) {
            case "HpUp":
                effectImageName = "hpItemEffect00";
                break;
            case "AtkUp":
                effectImageName = "attackItemEffect00";
                break;
            case "DefUp":
                effectImageName = "defenceItemEffect00";
                break;
            default:
                return;
        }
        effectID = effectAdmin.createEffect("itemEffect" ,effectImageName, 5, 3);
        effectAdmin.getEffect(effectID).setPosition(camera.convertToNormCoordinateX((int)w_x), camera.convertToNormCoordinateY((int)w_y));
        effectAdmin.getEffect(effectID).setExtends(extend, extend);
        effectAdmin.getEffect(effectID).start();
    }

    private void updateEffect() {
        effectAdmin.getEffect(effectID).setPosition(camera.convertToNormCoordinateX((int)w_x), camera.convertToNormCoordinateY((int)w_y));
    }

    static public void setRepeatCount(int _repeatCount) {
        repeatCount = _repeatCount;
    }

    public void update() {
        super.update();

        if (exists == true) {

            this.updateEffect();

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
                effectAdmin.getEffect(effectID).clear();

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
        int[][] statusMaxMin = battleUnitDataAdmin.getStatusMinMaxExceptBoss(dungeonMonsterDataAdmin, repeatCount, 5.024);
        int statusID = 0;

        switch (name) {
            case ("HpUp"):
                parameter = bonus_status[Constants.UnitStatus.BonusStatus.BONUS_HP.ordinal()];
                statusID = 0;
                break;
            case ("AtkUp"):
                parameter = bonus_status[Constants.UnitStatus.BonusStatus.BONUS_ATTACK.ordinal()];
                statusID = 1;
                break;
            case ("DefUp"):
                parameter = bonus_status[Constants.UnitStatus.BonusStatus.BONUS_DEFENSE.ordinal()];
                statusID = 2;
                break;
        }
        float tempRate =  (float)(parameter - statusMaxMin[statusID + 3][1])/(float)(statusMaxMin[statusID + 3][0] - statusMaxMin[statusID + 3][1]);
        extend = tempRate * 1.4f + 0.1f;
        parameter *= 1.5f;

    }


    public static class MapInventryAdmin {
    }
}
