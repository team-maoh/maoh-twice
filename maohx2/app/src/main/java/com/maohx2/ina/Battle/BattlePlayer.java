package com.maohx2.ina.Battle;

import com.maohx2.ina.Draw.Graphic;

/**
 * Created by ina on 2017/09/21.
 */

public class BattlePlayer extends BattleUnit {

    BattlePlayer(Graphic _graphic){
        super(_graphic);
        paint.setARGB(255,0,255,0);
        max_hit_point = 10000;
        hit_point = max_hit_point;
        exist = true; //todo::消す
    }

    //@Override
    public void drawStatus(){
        //HPバー
        graphic.bookingDrawRect(200,50, (int)(200+1200*((double)hit_point/(double)max_hit_point)), 80, paint);

        for(int i = 0; i < BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal() -1; i++) {
            if (alimentCounts[i] > 0) {
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.POISON){graphic.bookingDrawBitmapData(graphic.searchBitmap("Z2"),1400,50);}
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.PARALYSIS){graphic.bookingDrawBitmapData(graphic.searchBitmap("A6"),1430,50);}
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.BLINDNESS){graphic.bookingDrawBitmapData(graphic.searchBitmap("A1"),1460,50);}
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.CURSE){graphic.bookingDrawBitmapData(graphic.searchBitmap("A9"),1490,50);}
            }
        }


    }


    @Override
    public double getPositionX() {
        return -510;
    }

    @Override
    public void setPositionX(double _position_x) {}

    @Override
    public double getPositionY() {
        return -510;
    }

    @Override
    public void setPositionY(double _position_y) {}

    @Override
    public double getRadius() {
        return -510;
    }

    @Override
    public void setRadius(double _radius) {}

    @Override
    public int getUIID(){return  -510; }

    @Override
    public void setUIID(int _uiid){}

    @Override
    public int getWaitFrame() {
        return -1;
    }
    @Override
    public void setWaitFrame(int _wait_frame) {}
    @Override
    public double getAttackFrame() {
        return -1;
    }
    @Override
    public void setAttackFrame(int _attack_frame) {}
}
