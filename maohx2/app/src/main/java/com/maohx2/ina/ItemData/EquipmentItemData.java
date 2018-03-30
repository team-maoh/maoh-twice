package com.maohx2.ina.ItemData;

import com.maohx2.ina.Constants.Item.*;

/**
 * Created by ina on 2017/12/15.
 */

public class EquipmentItemData extends ItemData{

    EQUIPMENT_KIND equipment_kind;
    int useNum;
    int radius;
    float decayRate;
    int touchFrequency;
    int autoFrequencyRate;
    int attack;
    int defence;


    public EQUIPMENT_KIND getEquipmentKind() {
        return equipment_kind;
    }
    public void setEquipmentKind(EQUIPMENT_KIND _equipment_kind) {equipment_kind = _equipment_kind;}

    public int getUseNum() {
        return useNum;
    }
    public void setUseNum(int _useNum) {useNum = _useNum;}

    public int getRadius(){return radius;}
    public void setRadius(int radius) {this.radius = radius;}

    public float getDecayRate() {return decayRate;}
    public void setDecayRate(float decayRate) {this.decayRate = decayRate;}

    public int getTouchFrequency() {return touchFrequency;}
    public void setTouchFrequency(int touchFrequency) {this.touchFrequency = touchFrequency;}

    public int getAutoFrequencyRate() {return autoFrequencyRate;}
    public void setAutoFrequencyRate(int autoFrequencyRate) {this.autoFrequencyRate = autoFrequencyRate;}

    public int getAttack() {
        return attack;
    }
    public void setAttack(int _attack) {attack = _attack;}

    public int getDefence() {return defence;}
    public void setDefence(int defence) {this.defence = defence;}

}
