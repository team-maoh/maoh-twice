package com.maohx2.ina.ItemData;
import com.maohx2.ina.Battle.BattleDungeonUnitData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.Item.*;

/**
 * Created by ina on 2018/04/15.
 */

public class EquipmentItemDataCreater {

    EquipmentItemBaseData[] equipmentItemBaseDatas;

    public EquipmentItemDataCreater(EquipmentItemBaseData[] _equipmentItemBaseDatas){

        equipmentItemBaseDatas = _equipmentItemBaseDatas;
    }

    public EquipmentItemData getEquipmentItemData(EQUIPMENT_KIND _equipmentKind, BattleDungeonUnitData _battleDungeonUnitData){

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


        newEquipmentItemData.setPrice(_battleDungeonUnitData.getStatus(Constants.UnitStatus.Status.LUCK) * equipmentItemBaseDatas[_equipmentKind.ordinal()].getPrice());
        newEquipmentItemData.setAttack(_battleDungeonUnitData.getStatus(Constants.UnitStatus.Status.ATTACK) * equipmentItemBaseDatas[_equipmentKind.ordinal()].getAttack());

        return newEquipmentItemData;
    }

}
