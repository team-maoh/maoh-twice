package com.maohx2.kmhanko.itemdata;

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