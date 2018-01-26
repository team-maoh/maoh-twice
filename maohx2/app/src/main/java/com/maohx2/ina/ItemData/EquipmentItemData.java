package com.maohx2.ina.ItemData;

/**
 * Created by ina on 2017/12/15.
 */

public class EquipmentItemData extends ItemData{

    int equipment_kind;
    int attack;

    public int getEquipmentKind() {
        return equipment_kind;
    }
    public void setEquipmentKind(int _equipment_kind) {equipment_kind = _equipment_kind;}

    public int getAttack() {
        return attack;
    }
    public void setAttack(int _attack) {attack = _attack;}

}
