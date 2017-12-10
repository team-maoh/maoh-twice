package com.maohx2.fuusya;

import static java.lang.Math.PI;

/**
 * Created by Fuusya on 2017/12/10.
 */

public class MapObjectBitmap {

    double direction;//マップ上で自身がどちらを向いているか（0 ~ 2*PI）

    int bitmap_direction;//画像が1方向なのか、4方向なのか、8方向なのか

//    public MapObjectBitmap(int _bitmap_direction) {
    public MapObjectBitmap() {

//        if (0 < _bitmap_direction) {
//            bitmap_direction = _bitmap_direction;
//        }


    }

    public void init() {

    }

    public void draw() {

        if (bitmap_direction == 4) {

//            switch ()


        }

    }

    public void setDirection(double _direction) {
        if (0 <= _direction && _direction <= 2 * PI) {
            direction = _direction;
        }


    }

}