package com.maohx2.ina.Battle;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.effect.EffectAdmin;

import java.util.Random;

/**
 * Created by ina on 2017/09/21.
 */

public class BattlePlayer extends BattleUnit {

    boolean is_damaged = false;
    int shake_count = 0;

    BattlePlayer(Graphic _graphic, EffectAdmin _effectAdmin, EffectAdmin _backEnemyEffectAdmin){
        super(_graphic, null, null);
        paint.setARGB(255,0,255,0);
        max_hit_point = 10000;
        hit_point = max_hit_point;
        exist = true; //todo::消す
    }

    //@Override
    public void drawStatus(){
        //HPバー
        if(is_damaged || shake_count > 0) {
            Random rnd = new Random();
            if(is_damaged){
                shake_count = 5;
            }
            else{
                shake_count--;
            }
            int shake_x = shake_count;//rnd.nextInt(5)+1;
            int shake_y = shake_count;//rnd.nextInt(5)+1;
            int direction = rnd.nextInt(3);
            switch (direction){
                case 0:
                    graphic.bookingDrawRect(200+shake_x, 20+shake_y, (int) (200 + 1200 * ((double) hit_point / (double) max_hit_point))+shake_x, 40+shake_y, paint);
                    graphic.bookingDrawBitmapData(graphic.searchBitmap("player_icon"),140+shake_x,35+shake_y,2.5f,2.5f,0,255,false);
                    break;
                case 1:
                    graphic.bookingDrawRect(200+shake_x, 20-shake_y, (int) (200 + 1200 * ((double) hit_point / (double) max_hit_point))+shake_x, 40-shake_y, paint);
                    graphic.bookingDrawBitmapData(graphic.searchBitmap("player_icon"),140+shake_x,35-shake_y,2.5f,2.5f,0,255,false);
                    break;
                case 2:
                    graphic.bookingDrawRect(200-shake_x, 20+shake_y, (int) (200 + 1200 * ((double) hit_point / (double) max_hit_point))-shake_x, 40+shake_y, paint);
                    graphic.bookingDrawBitmapData(graphic.searchBitmap("player_icon"),140-shake_x,35+shake_y,2.5f,2.5f,0,255,false);
                    break;
                case 3:
                    graphic.bookingDrawRect(200-shake_x, 20-shake_y, (int) (200 + 1200 * ((double) hit_point / (double) max_hit_point))-shake_x, 40-shake_y, paint);
                    graphic.bookingDrawBitmapData(graphic.searchBitmap("player_icon"),140-shake_x,35-shake_y,2.5f,2.5f,0,255,false);
                    break;
            }
        }else{
            graphic.bookingDrawRect(200, 20, (int) (200 + 1200 * ((double) hit_point / (double) max_hit_point)), 40, paint);
            graphic.bookingDrawBitmapData(graphic.searchBitmap("player_icon"),140,35,2.5f,2.5f,0,255,false);
        }


        for(int i = 0; i < BattleBaseUnitData.ActionID.ACTION_ID_NUM.ordinal() -1; i++) {
            if (alimentCounts[i] > 0) {
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.POISON){graphic.bookingDrawBitmapData(graphic.searchBitmap("Z2"),1300,60,2.0f,2.0f,0,255,true);}
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.PARALYSIS){graphic.bookingDrawBitmapData(graphic.searchBitmap("A6"),1350,60,2.0f,2.0f,0,255,true);}
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.STOP){graphic.bookingDrawBitmapData(graphic.searchBitmap("Z6"),1400,60,2.0f,2.0f,0,255,true);}
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.BLINDNESS){graphic.bookingDrawBitmapData(graphic.searchBitmap("A1"),1450,60,2.0f,2.0f,0,255,true);}
                if(BattleBaseUnitData.ActionID.toEnum(i+1) == BattleBaseUnitData.ActionID.CURSE){graphic.bookingDrawBitmapData(graphic.searchBitmap("A9"),1500,60,2.0f,2.0f,0,255,true);}
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

    @Override
    public void setDamagedFlag(boolean _flag){
        is_damaged = _flag;
    }
}
