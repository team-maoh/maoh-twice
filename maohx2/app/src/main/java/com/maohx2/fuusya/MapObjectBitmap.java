package com.maohx2.fuusya;

import android.graphics.Bitmap;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

import static java.lang.Math.PI;

/**
 * Created by Fuusya on 2017/12/10.
 */

public class MapObjectBitmap {

    double direction;//マップ上で自身がどちらを向いているか（0 ~ 2*PI）

    int bitmap_dir;//画像が1方位なのか、4方位なのか、8方位なのか
    Graphic graphic;
    String object_name;

    BitmapData bitmap_data;

    int MAX_NUM_OF_BITMAP = 8;//最大で8方位
    Bitmap[] object_bitmap = new Bitmap[MAX_NUM_OF_BITMAP];//各方位に対応する画像

    public MapObjectBitmap(int _bitmap_dir, Graphic _graphic, String _object_name) {
        graphic = _graphic;

        bitmap_data = graphic.searchBitmap(_object_name);

        if (_bitmap_dir == 1 || _bitmap_dir == 4 || _bitmap_dir == 8) {
            bitmap_dir = _bitmap_dir;
        } else {
            throw new Error("◆%☆フジワラ：MapObjectBitmap()に渡す[画像方位]がおかしい");
        }



    }

    public void init() {

    }

    public void draw() {

        if (bitmap_dir == 1) {

        }

//        object_bitmap[((int) ((direction + PI / bitmap_dir) / (2 * PI / bitmap_dir)))]


    }

    public void setDirection(double _direction) {

        if (0 <= _direction && _direction <= 2 * PI) {
            direction = _direction;
        }


    }

}