package com.maohx2.ina.Battle;

import com.maohx2.ina.Draw.BitmapData;

import static com.maohx2.ina.Constants.UnitStatus.BonusStatus;
import static com.maohx2.ina.Constants.UnitStatus.Status;

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


}
