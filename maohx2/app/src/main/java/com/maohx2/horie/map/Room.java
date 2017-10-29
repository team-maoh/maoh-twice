package com.maohx2.horie.map;

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

    public void Room(){}

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

    public void setAll(int m_left, int m_top, int m_right, int m_bottom){
        top = m_top;
        bottom = m_bottom;
        left = m_left;
        right = m_right;
        height = m_bottom - m_top;
        width = m_right - m_left;
        area = height * width;
    }
}
