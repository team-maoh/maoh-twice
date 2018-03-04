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
    protected int defence;
    protected int luck;
    protected boolean exist;

    //by kmhanko
    protected String name;

    //by kmhanko
    protected BattleDungeonUnitData battleDungeonUnitData;

    //by kmhanko
    public void init() {
        exist = false;
    }

    /* by kmhanko
    public void initStatus(BattleDungeonUnitData _battleDungeonUnitData){
        max_hit_point = 10;
        hit_point = max_hit_point;
        attack = 1;
        attack_unit_num = -1;
    }
    */

    //by kmhanko
    protected void setBattleDunogenUnitData(BattleDungeonUnitData _battleDungeonUnitData) {
        battleDungeonUnitData = _battleDungeonUnitData;
    }

    protected void statusInit() {
        exist = true;
        name = battleDungeonUnitData.getName();
        max_hit_point = battleDungeonUnitData.getStatus(HP);
        hit_point = max_hit_point;
        attack = battleDungeonUnitData.getStatus(ATTACK);
        defence = battleDungeonUnitData.getStatus(DEFENSE);
        luck = battleDungeonUnitData.getStatus(LUCK);
        attack_unit_num = -1; //TODO 不明
    }

    //player用
    public void setBattleUnitData(BattleDungeonUnitData _battleDungeonUnitData) {
        //初期化処理 (データに寄らない)
        init();

        //格納
        battleDungeonUnitData = _battleDungeonUnitData;

        //BattleDungeonUnitDataをもとに初期化
        statusInit();
        return;
    }

    //enemy 用
    public void setBattleUnitData(BattleBaseUnitData _battleBaseUnitData, int repeatCount, int i) {
        //初期化処理 (データに寄らない)
        init();

        //TODO : 敵の出現位置決定
        setPositionX(350 + 400 * (i - 1));
        setPositionY(300);

        //TODO : 敵のタッチ半径 これは敵のデータベースか画像サイズ依存にするべきかも
        setRadius(50);

        battleDungeonUnitData = new BattleDungeonUnitData();
        battleDungeonUnitData.setName(_battleBaseUnitData.getName());
        battleDungeonUnitData.setStatus(_battleBaseUnitData.getStatus(repeatCount));
        battleDungeonUnitData.setBonusStatus(_battleBaseUnitData.getBonusStatus(repeatCount));

        //BattleDungeonUnitDataをもとに初期化
        statusInit();
        return;
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
    public int getDefence(){ return defence; }
    public int getLuck(){ return luck; }
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
