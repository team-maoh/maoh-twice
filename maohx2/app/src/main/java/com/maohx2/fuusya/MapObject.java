package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.MySprite;

import java.util.Map;
import java.util.Random;

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
    Random random;
    MapObjectAdmin map_object_admin;
    SoundAdmin sound_admin;
    MapPlayer player;
    MapAdmin map_admin;

    public MapObject(Graphic _graphic, MapObjectAdmin _map_object_admin, Camera _camera) {

        graphic = _graphic;
        map_object_admin = _map_object_admin;
        camera = _camera;
        sound_admin = map_object_admin.getSoundAdmin();
        player = map_object_admin.getPlayer();
        map_admin = map_object_admin.getMapAdmin();

        exists = true;
        id = -1;

        dir_on_map = 0.0;

        random = new Random();
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

    //平均0, 分散1の標準正規分布 N(0, 1)
    public double makeNormalDist() {
        double seed1 = random.nextDouble();
        double seed2 = random.nextDouble();

        return sqrt(-2 * log(seed1)) * cos(2 * PI * seed2);
    }

    protected double myDistance(double x1, double y1, double x2, double y2) {
        return (pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5));
    }

    public void initPosition(MapAdmin _map_admin){
        map_admin = _map_admin;

        camera = map_admin.getCamera();

        Point room_point = map_admin.getRoomPoint();
        w_x = room_point.x;
        w_y = room_point.y;

    }


}


