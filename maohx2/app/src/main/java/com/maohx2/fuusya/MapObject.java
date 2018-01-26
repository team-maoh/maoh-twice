package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;
import static java.lang.Math.PI;


import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Draw.Graphic;
//import com.maohx2.ina.MySprite;

import java.util.Map;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

abstract public class MapObject {

    double w_x, w_y;
    String draw_object;
    boolean exists;//自身がマップ上に存在しているかどうか
    int id;
    Graphic graphic;
    Camera camera;
    double dir_on_map;//マップ上での自分自身の向き( 0 ~ 2*PI )

    public MapObject(Graphic _graphic, MapObjectAdmin _map_object_admin, Camera _camera) {

        graphic = _graphic;
        camera = _camera;

        exists = true;
        id = -1;

        w_x = 800;
        w_y = 450;
        dir_on_map = 0.0;

    }

    public void init() {

    }

    public void update() {
    }

    public double getWorldX() {
        return w_x;
    }

    public double getWorldY() {
        return w_y;
    }

    public double getNormX() {
        return camera.convertToNormCoordinateX((int) w_x);
    }

    public double getNormY() {
        return camera.convertToNormCoordinateY((int) w_y);
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

    public double getDirOnMap() {
        return dir_on_map;
    }

    public int convertDirToIntDir(int total_dirs, double _dir_on_map) {
        return ((int) ((_dir_on_map + PI / total_dirs) / (2 * PI / total_dirs))) % total_dirs;
    }

//    public void setId(int _id){
//        id = _id;
//    }

}


