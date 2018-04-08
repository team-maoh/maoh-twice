package com.maohx2.horie.map;

import android.graphics.Point;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by horie on 2017/12/15.
 */

public class AutoTile {
    public BitmapData raw_auto_tile[];//auto_tileを切っただけ
    public BitmapData auto_tile[];//使う可能性のあるauto_tile全パターン
    public BitmapData big_auto_tile[];//auto_tileの4つを一つにまとめたもの

    public AutoTile(){
        raw_auto_tile = new BitmapData[5];
        auto_tile = new BitmapData[47];
        big_auto_tile = new BitmapData[52];
    }

    public void setAuto_tile(BitmapData m_auto_tile, int num) {
        raw_auto_tile[num] = m_auto_tile;
    }

    public void drawAutoTile(Graphic graphic, int num, int x, int y, int magnification){
        graphic.bookingDrawBitmapData(raw_auto_tile[num], x, y, magnification, magnification, 0, 255, true);
    }
}
