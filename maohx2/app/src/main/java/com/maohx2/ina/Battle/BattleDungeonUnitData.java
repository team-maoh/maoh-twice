package com.maohx2.ina.Battle;

import com.maohx2.ina.Draw.BitmapData;

import static com.maohx2.ina.Constants.UnitStatus.BonusStatus;
import static com.maohx2.ina.Constants.UnitStatus.Status;
import static com.maohx2.ina.Battle.BattleBaseUnitData.SpecialAction;
import static com.maohx2.ina.Battle.BattleBaseUnitData.ActionID;

/**
 * Created by ina on 2017/10/20.
 */

//そのダンジョン（周回回数やダンジョンレベルを考慮した）でのユニットの強さを保存したもので、これをユニットが持つ
//takano : 敵キャラクターの能力値の上昇を計算した結果のステータスを保存したもの。
//計算上、プレイヤーのデータもここに格納して使う
public class BattleDungeonUnitData {

    String name;
    BitmapData bitmap_data;
    int radius;

    int[] status = new int[Status.NUM_OF_STATUS.ordinal()];
    int[] bonus_status = new int[BonusStatus.NUM_OF_BONUS_STATUS.ordinal()];

    public void init(){}

    // *** setter & getter ***
    public String getName(){ return name; }
    public void setStatus(int[] _status){
        status = _status;
    }
    public void setBonusStatus(int[] _bonus_status){
        bonus_status = _bonus_status;
    }

    //by kmhanko
    public int getStatus(Status _status) { return status[_status.ordinal()]; }
    public int getBonusStatus(BonusStatus _bonusStatus) { return bonus_status[_bonusStatus.ordinal()]; }
    public void setName(String _name) { name = _name; }

    public BitmapData getBitmapDate(){return bitmap_data;}
    public void setBitmapData(BitmapData _bitmap_data){bitmap_data = _bitmap_data;}


    float[] actionRate = new float[BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal()];
    BattleBaseUnitData.SpecialAction specialAction;
    int specialActionPeriod;
    int specialActionWidth;
    int[] alimentTime = new int[BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal()];

    public void setActionRate(ActionID _actionRateID, float _actionRate) { actionRate[_actionRateID.ordinal()] = _actionRate; }
    public void setActionRate(float[] _actionRate) { actionRate = _actionRate; }
    public void setSpecialAction(SpecialAction _specialAction) { specialAction = _specialAction; }
    public void setSpecialActionWidth(int _specialActionWidth) { specialActionWidth = _specialActionWidth; }
    public void setSpecialActionPeriod(int _specialActionPeriod) { specialActionPeriod = _specialActionPeriod; }
    public void setAlimentTime(int[] _alimentTime) { alimentTime = _alimentTime; }

    public float[] getActionRate() { return actionRate; }
    public float getActionRate(ActionID _actionRateID) { return actionRate[_actionRateID.ordinal()]; }
    public SpecialAction getSpecialAction() { return specialAction; }
    public int getSpecialActionWidth() { return specialActionWidth; }
    public int getSpecialActionPeriod() { return specialActionPeriod; }

    public int[] getAlimentTime() { return alimentTime; }
    public int getAlimentTime(ActionID _actionRateID) { return alimentTime[_actionRateID.ordinal()]; }

    public void release() {
        bitmap_data = null;
        status = null;
        bonus_status = null;
        actionRate = null;
        specialAction = null;
        alimentTime = null;
    }

}
