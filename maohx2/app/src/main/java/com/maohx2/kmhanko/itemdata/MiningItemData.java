package com.maohx2.kmhanko.itemdata;

import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by user on 2018/05/08.
 */

public class MiningItemData extends ItemData {

    int radius;
    float decayRate;
    int touchFrequency;
    float autoFrequencyRate;
    int attack;

    int palettePosition;

    public MiningItemData(){
        radius = 1;
        decayRate = 1.5f;
        touchFrequency = 1;
        autoFrequencyRate = 0.5f;
        attack = 100;
        palettePosition = 0;
    }

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

    public int getPalettePosition() {return palettePosition;}
    public void setPalettePosition(int _palettePosition) {palettePosition = _palettePosition;}


}
