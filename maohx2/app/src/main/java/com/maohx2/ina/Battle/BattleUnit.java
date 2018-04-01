package com.maohx2.ina.Battle;

/**
 * Created by ina on 2017/09/21.
 */

abstract public class BattleUnit {

    int max_hit_point;
    int hit_point;
    int attack;
    int attack_unit_num;
    boolean exist;




    public void init(){

        max_hit_point = 10;
        hit_point = max_hit_point;
        attack = 1;
        attack_unit_num = -1;
        exist = true;
    }

    public void update(){}

    public  boolean isExist(){

        return exist;
    }

    public void setExist(boolean _exist){

        exist = _exist;
    }

    public int getHitPoint(){

        return hit_point;
    }

    public void  setHitPoint(int _hit_point){

        hit_point = _hit_point;
    }

    public  int getAttack(){

        return attack;
    }

    public void  setAttack(int _attack){

        attack = _attack;
    }

    public int getMaxHitPoint(){

        return max_hit_point;
    }

    public void setMaxHitPoint(int _max_hit_point){

        max_hit_point = _max_hit_point;
    }

    //のちに技について保存したクラスを返すことになるかも
    //技の対象、ダメージ、技の名前、などなどを保存している
    public int getAttackUnitNum() {
        return attack_unit_num;
    }

    public void setAttackUnitNum(int _attack_unit_num) {
        attack_unit_num = _attack_unit_num;
    }


    abstract public int getPositionX();
    abstract public void setPositionX(int _position_x);
    abstract public int getPositionY();
    abstract public void setPositionY(int _position_y);
    abstract public int getRadius();
    abstract public void setRadius(int _radius);
    abstract public int getUIID();
    abstract public void setUIID(int _uiid);



    public BattleUnit(){}


}
