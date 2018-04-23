package com.maohx2.ina.Battle;

//by kmhanko
import android.graphics.Paint;

import com.maohx2.ina.Draw.Graphic;

import java.util.Random;

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
    protected boolean dropFlag;
    Random rnd;
    Graphic graphic;
    Paint paint;
    double dx, dy, dl;
    int move_end ;
    int speed;
    int move_num;


    //by kmhanko
    protected String name;

    //by kmhanko
    protected BattleDungeonUnitData battleDungeonUnitData;


    //コンストラクタ
    public BattleUnit(Graphic _graphic){
        graphic = _graphic;
        paint = new Paint();
        speed = 10;
        rnd = new Random();
        exist = false;
        dropFlag = false;
    }

    /*
    //by kmhanko
    public void init() {
        rnd = new Random();
    }
*/

    /* by kmhanko
    public void initStatus(BattleDungeonUnitData _battleDungeonUnitData){
        max_hit_point = 10;
        hit_point = max_hit_point;
        attack = 1;
        attack_unit_num = -1;
    }*/

    //by kmhanko
    protected void setBattleDunogenUnitData(BattleDungeonUnitData _battleDungeonUnitData) {
        battleDungeonUnitData = _battleDungeonUnitData;
    }

    protected void statusInit() {
        name = battleDungeonUnitData.getName();
        max_hit_point = battleDungeonUnitData.getStatus(HP);
        hit_point = max_hit_point;
        attack = battleDungeonUnitData.getStatus(ATTACK);
        defence = battleDungeonUnitData.getStatus(DEFENSE);
        luck = battleDungeonUnitData.getStatus(LUCK);
        speed = battleDungeonUnitData.getStatus(SPEED);
        attack_unit_num = -1; //TODO 不明
    }

    //player用
    public void setBattleUnitData(BattleDungeonUnitData _battleDungeonUnitData) {
        //初期化処理 (データに寄らない)
        //init();
        exist = true;
        dropFlag = true;

        //格納
        battleDungeonUnitData = _battleDungeonUnitData;

        //BattleDungeonUnitDataをもとに初期化
        statusInit();
        return;
    }

    //enemy 用
    public void setBattleUnitData(BattleBaseUnitData _battleBaseUnitData, int repeatCount, int i) {
        //初期化処理 (データに寄らない)
        //init();

        exist = true;
        dropFlag = true;

        //TODO : 敵の出現位置決定
        setPositionX(rnd.nextInt(1200)+200);
        setPositionY(rnd.nextInt(500)+200);

        battleDungeonUnitData = new BattleDungeonUnitData();
        battleDungeonUnitData.setName(_battleBaseUnitData.getName());
        battleDungeonUnitData.setStatus(_battleBaseUnitData.getStatus(repeatCount));
        battleDungeonUnitData.setBonusStatus(_battleBaseUnitData.getBonusStatus(repeatCount));
        battleDungeonUnitData.setBitmapData(_battleBaseUnitData.getBitmapData());

        setRadius(_battleBaseUnitData.getRadius());

        dx = rnd.nextInt(1200)+200 - getPositionX();
        dy = rnd.nextInt(500)+200 - getPositionY();
        dl = Math.sqrt(dx*dx + dy*dy);

        dx = dx / dl;
        dy = dy / dl;
        move_end = (int) (dl / speed);

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
    public boolean isDropFlag() { return dropFlag; }
    public int getMaxHitPoint() { return max_hit_point; }
    public int getHitPoint() { return hit_point; }
    public int getAttack(){ return attack; }
    public int getDefence(){ return defence; }
    public int getLuck(){ return luck; }
    public String getName() { return name; }
    public BattleDungeonUnitData getBattleDungeonUnitData() { return battleDungeonUnitData; }

    // *** setter ***
    public void existIs(boolean _exist) { exist = _exist; }
    public void dropFlagIs(boolean _dropFlag) { dropFlag = _dropFlag; }
    public void setMaxHitPoint(int _max_hit_point){ max_hit_point = _max_hit_point; }
    public void setHitPoint(int _hit_point) { hit_point = _hit_point; }
    public void setAttack(int _attack) { attack = _attack; }
    public void setName(String _name) { name = _name; }

    // *** Override用 ***
    abstract public double getPositionX();
    abstract public double getPositionY();
    abstract public double getRadius();
    abstract public int getUIID();
    abstract public void setRadius(double _radius);
    abstract public void setPositionX(double _position_x);
    abstract public void setPositionY(double _position_y);
    abstract public void setUIID(int _uiid);
    abstract public int getWaitFrame();
    abstract public void setWaitFrame(int _wait_frame);
    abstract public double getAttackFrame();
    abstract public void setAttackFrame(int _attack_frame);
    public int update(){return 0;}
    public void draw(){}


}
