package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;


import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Draw.Graphic;
//import com.maohx2.ina.MySprite;

import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

abstract public class MapObject {

    double x, y;
    //    SurfaceHolder holder;
    String draw_object;
    int icon_width, icon_height;
    //    Paint paint = new Paint();
    boolean exists;//自身がマップ上に存在しているかどうか
    int id;
    Graphic graphic;

//    MySprite sprite_object;
//    GL10 gl;

    public MapObject(Graphic _graphic, MapObjectAdmin _map_object_admin) {

        graphic = _graphic;

//        icon_width = draw_object.getWidth();
//        icon_height = draw_object.getHeight();
        exists = true;
        id = -1;

        x = 800;
        y = 450;
    }

    public void init() {

    }

    public void update() {
    }

    public void draw(MapAdmin _map_admin) {

        MapAdmin map_admin = _map_admin;

        if (exists == true) {
//            graphic.bookingDrawBitmap(draw_object, (int) x, (int) y);
            graphic.bookingDrawBitmap(draw_object, (int) x, (int) y);
//            graphic.bookingDrawBitmap("ゴキ魅",800,450);
        }

//        synchronized (holder) {
//            if (exists == true) {
//                canvas.drawBitmap(draw_object, (int) x - icon_width / 2 - map_admin.getMap_size_x() , (int) y - icon_height / 2 - map_admin.getMap_size_y(), paint);
////                canvas.drawBitmap(draw_object, (int) x - icon_width / 2 , (int) y - icon_height / 2, paint);
//            }
//        }
    }

//    public void draw(GL10 _gl) {
//
//            if (exists == true) {
//                sprite_object.draw(_gl,(float) x,(float) y);
//            }
//
//    }

    public double getMapX() {
        return x;
    }

    public double getMapY() {
        return y;
    }

    public boolean exists() {
        return exists;
    }

    public void setExists(boolean _exists) {
        exists = _exists;
    }

    public int getId() {
        return id;
    }

//    public void setId(int _id){
//        id = _id;
//    }

}


