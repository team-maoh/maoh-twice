package com.maohx2.ina.Battle;

/**
 * Created by ina on 2017/09/29.
 */

public class CalcUnitStatus {

    public void init() {}

    public void breakUnit(BattleUnit breaker, BattleUnit breaked_unit) {

        breaker.setMaxHitPoint(breaker.getMaxHitPoint() + breaked_unit.getMaxHitPoint());
        breaker.setHitPoint(breaker.getHitPoint() + breaked_unit.getMaxHitPoint());
        breaker.setAttack(breaker.getAttack() + breaked_unit.getAttack());
    }


}
