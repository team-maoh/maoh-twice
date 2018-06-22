package com.maohx2.ina.ItemData;

import com.maohx2.ina.Battle.BattleDungeonUnitData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.Item.*;

import java.util.Random;

/**
 * Created by ina on 2018/04/15.
 */

public class EquipmentItemDataCreater {

    EquipmentItemBaseData[] equipmentItemBaseDatas;

    public EquipmentItemDataCreater(EquipmentItemBaseData[] _equipmentItemBaseDatas) {

        equipmentItemBaseDatas = _equipmentItemBaseDatas;
    }

    public EquipmentItemData getEquipmentItemData(EQUIPMENT_KIND _equipmentKind, BattleDungeonUnitData _battleDungeonUnitData, int player_luck) {

        EquipmentItemData newEquipmentItemData = new EquipmentItemData();

        newEquipmentItemData.setName(equipmentItemBaseDatas[_equipmentKind.ordinal()].getName());
        newEquipmentItemData.setItemImage(equipmentItemBaseDatas[_equipmentKind.ordinal()].getItemImage());
        newEquipmentItemData.setImageName(equipmentItemBaseDatas[_equipmentKind.ordinal()].getImageName());
        newEquipmentItemData.setItemKind(equipmentItemBaseDatas[_equipmentKind.ordinal()].getItemKind());

        newEquipmentItemData.setEquipmentKind(_equipmentKind);
        newEquipmentItemData.setUseNum(equipmentItemBaseDatas[_equipmentKind.ordinal()].getUseNum());
        newEquipmentItemData.setRadius(equipmentItemBaseDatas[_equipmentKind.ordinal()].getRadius());
        newEquipmentItemData.setDecayRate(equipmentItemBaseDatas[_equipmentKind.ordinal()].decayRate);
        newEquipmentItemData.setTouchFrequency(equipmentItemBaseDatas[_equipmentKind.ordinal()].touchFrequency);
        newEquipmentItemData.setAutoFrequencyRate(equipmentItemBaseDatas[_equipmentKind.ordinal()].autoFrequencyRate);
        newEquipmentItemData.setDefence(equipmentItemBaseDatas[_equipmentKind.ordinal()].getDefence());

//        double weapon_rate;
//        switch (_equipmentKind) {
//            case SWORD:
//                weapon_rate = 0.5;
//                break;
//            case WAND:
//                weapon_rate = 0.8;
//                break;
//            case AX:
//                weapon_rate = 1.0;
//                break;
//            case SPEAR:
//                weapon_rate = 0.7;
//                break;
//            case BOW:
//                weapon_rate = 0.6;
//                break;
//            case GUN:
//                weapon_rate = 0.6;
//                break;
//            case FIST:
//                weapon_rate = 0.4;
//                break;
//            case CLUB:
//                weapon_rate = 0.7;
//                break;
//            case WHIP:
//                weapon_rate = 0.3;
//                break;
//            case MUSIC:
//                weapon_rate = 0.2;
//                break;
//            case SHIELD:
//                weapon_rate = 0.0;
//                break;
//            default:
//                weapon_rate = 0.0;
//                break;
//        }
//
        double set_double = _battleDungeonUnitData.getStatus(Constants.UnitStatus.Status.ATTACK) * equipmentItemBaseDatas[_equipmentKind.ordinal()].getAttack();
//        Random random = new Random();
//        double random_num = (random.nextDouble() - 0.5) / 0.5;
        System.out.println("weapon_desuno_set_" + set_double);
        System.out.println("weapon_desuno_stt_" + _battleDungeonUnitData.getStatus(Constants.UnitStatus.Status.ATTACK));
        System.out.println("weapon_desuno_eq_" + equipmentItemBaseDatas[_equipmentKind.ordinal()].getAttack());

        //Luck依存で武器威力を０～１割増加
        double enemy_luck = _battleDungeonUnitData.getStatus(Constants.UnitStatus.Status.LUCK);
        double luck_rate = player_luck * 1000.0 / (enemy_luck * 22222.0) - 1.00;
        if (luck_rate < 0.0) {
            luck_rate = 0.0;
        } else if (0.13 < luck_rate) {
            luck_rate = 0.13;
        }
        luck_rate = (luck_rate / 0.13) / 10.0;

        newEquipmentItemData.setAttack((int) (set_double * (1.0 + luck_rate) * 0.3));

        newEquipmentItemData.setPrice(newEquipmentItemData.getAttack() * 7);

        return newEquipmentItemData;
    }

}
