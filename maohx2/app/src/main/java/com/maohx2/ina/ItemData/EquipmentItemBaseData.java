package com.maohx2.ina.ItemData;

import com.maohx2.ina.Constants.Item.*;

/**
 * Created by ina on 2018/04/15.
 */

public class EquipmentItemBaseData extends ItemData{

    EQUIPMENT_KIND equipment_kind;
    int useNum;
    int radius;
    float decayRate;
    int touchFrequency;
    float autoFrequencyRate;
    int attack;
    int defence;


    public EQUIPMENT_KIND getEquipmentKind() {
        return equipment_kind;
    }
    public void setEquipmentKind(EQUIPMENT_KIND _equipment_kind) {equipment_kind = _equipment_kind;}

    public int getUseNum() {return useNum;}
    public void setUseNum(int _useNum) {useNum = _useNum;}

    public int getRadius(){return radius;}
    public void setRadius(int radius) {this.radius = radius;}

    public float getDecayRate() {return decayRate;}
    public void setDecayRate(float decayRate) {this.decayRate = decayRate;}

    public int getTouchFrequency() {return touchFrequency;}
    public void setTouchFrequency(int touchFrequency) {this.touchFrequency = touchFrequency;}

    public float getAutoFrequencyRate() {return autoFrequencyRate;}
    public void setAutoFrequencyRate(float autoFrequencyRate) {this.autoFrequencyRate = autoFrequencyRate;}

    public int getAttack() {return attack;}
    public void setAttack(int _attack) {attack = _attack;}

    public int getDefence() {return defence;}
    public void setDefence(int defence) {this.defence = defence;}

}
