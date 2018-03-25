package com.maohx2.ina.ItemData;

import com.maohx2.ina.Constants.Item.*;

/**
 * Created by ina on 2017/12/15.
 */

public class EquipmentItemData extends ItemData{

    EQUIPMENT_KIND equipment_kind;
    int attack;

    public EQUIPMENT_KIND getEquipmentKind() {
        return equipment_kind;
    }
    public void setEquipmentKind(EQUIPMENT_KIND _equipment_kind) {equipment_kind = _equipment_kind;}

    public int getAttack() {
        return attack;
    }
    public void setAttack(int _attack) {attack = _attack;}

}
