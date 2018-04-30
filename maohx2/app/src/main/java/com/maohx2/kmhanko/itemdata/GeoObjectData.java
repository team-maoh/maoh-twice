package com.maohx2.kmhanko.itemdata;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.myavail.*;

/**
 * Created by user on 2017/11/12.
 */

public class GeoObjectData extends ItemData {
    int hp;
    int attack;
    int defence;
    int luck;

    double hp_rate;
    double attack_rate;
    double defence_rate;
    double luck_rate;

    String slotSetName = "noSet";
    int slotSetID;

    //Graphic graphic;


    public GeoObjectData() {
    }


    public GeoObjectData(String _name, BitmapData _bitmapData, int[] status1, double[] status2) {
        this(_name,_bitmapData, status1[0],status1[1],status1[2],status1[3],status2[0],status2[1],status2[2],status2[3]);
    }

    //DBからの生成用
    public GeoObjectData(String _name, String _imageName, BitmapData _bitmapData, int _hp, int _attack, int _defence, int _luck, double _hp_rate, double _attack_rate, double _defence_rate, double _luck_rate, String _slotSetName, int _slotSetID) {
        this(_name,_bitmapData, _hp,_attack,_defence,_luck,_hp_rate,_attack_rate,_defence_rate,_luck_rate);
        this.setImageName(_imageName);
    }


    /*
    public GeoObjectData(String _name, String _imageName, int _hp, int _attack, int _defence, int _luck, double _hp_rate, double _attack_rate, double _defence_rate, double _luck_rate, String _slotSetName, int _slotSetID) {
        this(_name,null,_hp,_attack,_defence,_luck,_hp_rate,_attack_rate,_defence_rate,_luck_rate);
    }
    */

    public GeoObjectData(String _name, BitmapData _bitmapData, int _hp, int _attack, int _defence, int _luck, double _hp_rate, double _attack_rate, double _defence_rate, double _luck_rate, String _slotSetName, int _slotSetID) {
        this(_name,_bitmapData,_hp,_attack,_defence,_luck,_hp_rate,_attack_rate,_defence_rate,_luck_rate);
        slotSetName = _slotSetName;
        slotSetID = _slotSetID;
    }

    public GeoObjectData(String _name, BitmapData _bitmapData, int _hp, int _attack, int _defence, int _luck, double _hp_rate, double _attack_rate, double _defence_rate, double _luck_rate) {
        //graphic = _graphic;
        setName(_name);
        setItemImage(_bitmapData);

        setStatus(_hp, _attack, _defence, _luck, _hp_rate, _attack_rate, _defence_rate, _luck_rate);
        //setGeoImage();
        setItemKind(Constants.Item.ITEM_KIND.GEO);
    }

    public void setSlotSetName(String _slotSetName) {
        slotSetName = _slotSetName;
    }
    public void setSlotSetID(int _slotSetID) {
        slotSetID = _slotSetID;
    }
    public String getSlotSetName() {
        return slotSetName;
    }
    public int getSlotSetID() {
        return slotSetID;
    }

    public void setStatus(int _hp, int _attack, int _defence, int _luck, double _hp_rate, double _attack_rate, double _defence_rate, double _luck_rate) {
        hp = _hp;
        attack = _attack;
        defence = _defence;
        luck = _luck;
        hp_rate = _hp_rate;
        attack_rate = _attack_rate;
        defence_rate = _defence_rate;
        luck_rate = _luck_rate;
    }

    /*
    public void setGeoImage() {
        //適当に計算してジオ画像を決める

        String tempBitName = "apple";
        if (hp > 0) { setName("体力ジオ" + hp); tempBitName = "HpGeo05"; }
        if (attack > 0) { setName("攻撃ジオ" + attack); tempBitName = "AttackGeo05"; }
        if (defence > 0) { setName("防御ジオ" + defence); tempBitName = "DeffenceGeo05"; }
        if (luck > 0) { setName("運命ジオ" + luck); tempBitName = "LuckGeo05"; }
        if (hp_rate > 1.0) { setName("体力倍ジオ" + hp_rate); tempBitName = "HpGeo06"; }
        if (attack_rate > 1.0) { setName("攻撃倍ジオ" + attack_rate); tempBitName = "AttackGeo06"; }
        if (defence_rate > 1.0) { setName("防御倍ジオ" + defence_rate); tempBitName = "DeffenceGeo06"; }
        if (luck_rate > 1.0) { setName("運命倍ジオ" + luck_rate); tempBitName = "LuckGeo06"; }


        switch (MyAvail.maxFromIntArrays(new int[] { hp, attack, defence, luck })) {
            case(0):
                setName("体力ジオ" + hp);
                tempBitName = "icon_gem_0";
                break;
            case(1):
                setName("攻撃ジオ" + attack);
                tempBitName = "icon_gem_1";
                break;
            case(2):
                setName("防御ジオ" + defence);
                tempBitName = "icon_gem_2";
                break;
            case(3):
                setName("運命ジオ" + luck);
                tempBitName = "icon_gem_4";
                break;
            default:
                setName("エラージオ" + luck);
                tempBitName = "icon_gem_9";
                break;
        }

        setItemImage(graphic.searchBitmap(tempBitName));
    }
    */

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getLuck() {
        return luck;
    }

    public double getHpRate() {
        return hp_rate;
    }

    public double getAttackRate() {
        return attack_rate;
    }

    public double getDefenceRate() {
        return defence_rate;
    }

    public double getLuckRate() {
        return luck_rate;
    }

    public void setHp(int _hp) {
        hp = _hp;
    }

    public void setAttack(int _attack) {
        attack = _attack;
    }

    public void setDefence(int _defence) {
        defence = _defence;
    }

    public void setLuck(int _luck) {
        luck = _luck;
    }

    public void setHpRate(double _hp_rate) {
        hp_rate = _hp_rate;
    }

    public void setAttackRate(double _attack_rate) {
        attack_rate = _attack_rate;
    }

    public void setDefenceRate(double _defence_rate) {
        defence_rate = _defence_rate;
    }

    public void setLuckRate(double _luck_rate) {
        luck_rate = _luck_rate;
    }
}