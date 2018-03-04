package com.maohx2.ina.Battle;

//by kmhanko
import static com.maohx2.ina.Constants.UnitStatus.Status.*;

/**
 * Created by ina on 2017/09/21.
 */

abstract public class BattleUnit {

    protected int max_hit_point;
    protected int hit_point;
    protected int attack;
    protected int attack_unit_num;
    protected boolean exist;

    //by kmhanko
    protected String name;

    //by kmhanko
    private BattleDungeonUnitData battleDungeonUnitData;

    //by kmhanko
    public void init() {
        exist = false;
    }

    public void initStatus(BattleDungeonUnitData _battleDungeonUnitData){
        /* by kmhanko
        max_hit_point = 10;
        hit_point = max_hit_point;
        attack = 1;
        attack_unit_num = -1;
        */
        exist = true;
        setBattleDunogenUnitData(_battleDungeonUnitData);
        statusInit();
    }

    //by kmhanko
    private void setBattleDunogenUnitData(BattleDungeonUnitData _battleDungeonUnitData) {
        battleDungeonUnitData = _battleDungeonUnitData;
    }
    private void statusInit() {
        name = battleDungeonUnitData.getName();
        max_hit_point = battleDungeonUnitData.getStatus(HP);
        hit_point = max_hit_point;
        attack = battleDungeonUnitData.getStatus(ATTACK);
        attack_unit_num = -1; //TODO 不明
    }

    //のちに技について保存したクラスを返すことになるかも
    //技の対象、ダメージ、技の名前、などなどを保存している
    public int getAttackUnitNum() {
        return attack_unit_num;
    }
    public void setAttackUnitNum(int _attack_unit_num) {
        attack_unit_num = _attack_unit_num;
    }


    // *** getter ***
    public boolean isExist() { return exist; }
    public int getMaxHitPoint() { return max_hit_point; }
    public int getHitPoint() { return hit_point; }
    public int getAttack(){ return attack; }
    public String getName() { return name; }

    // *** setter ***
    public void setExist(boolean _exist) { exist = _exist; }
    public void setMaxHitPoint(int _max_hit_point){ max_hit_point = _max_hit_point; }
    public void setHitPoint(int _hit_point) { hit_point = _hit_point; }
    public void setAttack(int _attack) { attack = _attack; }
    public void setName(String _name) { name = _name; }

    // *** Override用 ***
    abstract public int getPositionX();
    abstract public int getPositionY();
    abstract public int getRadius();
    abstract public int getUIID();
    abstract public void setRadius(int _radius);
    abstract public void setPositionX(int _position_x);
    abstract public void setPositionY(int _position_y);
    abstract public void setUIID(int _uiid);
    public void update(){}

    //コンストラクタ
    public BattleUnit() {
    }
}
