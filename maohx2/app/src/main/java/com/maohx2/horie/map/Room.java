package com.maohx2.horie.map;

import android.graphics.Paint;
import android.graphics.Point;

import com.maohx2.ina.Draw.Graphic;

/**
 * Created by horie on 2017/10/13.
 */

public class Room {
    int top;
    int bottom;
    int left;
    int right;
    int height;
    int width;
    int area;
    boolean isDisp;

    public Room(){}

    public int getTop(){
        return top;
    }

    public int getBottom(){
        return bottom;
    }

    public int getLeft(){
        return left;
    }

    public int getRight(){
        return right;
    }

    public boolean isDisp(){
        return isDisp;
    }

    public void setDispflag(boolean m_isDisp){
        isDisp = m_isDisp;
    }

    public void setAll(int m_left, int m_top, int m_right, int m_bottom){
        top = m_top;
        bottom = m_bottom;
        left = m_left;
        right = m_right;
        height = m_bottom - m_top;
        width = m_right - m_left;
        area = height * width;
    }

    public void drawRoom(Graphic graphic, Paint paint, int magnification, Point small_map_offset){
        if(isDisp) {
            graphic.bookingDrawRect(left*magnification+small_map_offset.x, top*magnification+small_map_offset.y, (right+1)*magnification+small_map_offset.x, (bottom+1)*magnification+small_map_offset.y, paint);
        }
    }
}
