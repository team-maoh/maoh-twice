package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;


//import com.maohx2.ina.MySprite;

import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Draw.Graphic;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapTrap extends MapInanimate {

    double REACH_FOR_PLAYER = 25;
    int CONST_FRAMES_ACTIVATING = 50;
    int frames_activating;
    MapPlayer player;
    String status_name;
    boolean has_activated, is_visible;

    public MapTrap(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, boolean _is_visible, String _status_name) {
        super(graphic, _map_object_admin, _id, _camera);

        has_activated = false;
        frames_activating = CONST_FRAMES_ACTIVATING;
        player = map_object_admin.getPlayer();
        is_visible = _is_visible;

        status_name = _status_name;
    }

    public MapTrap(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, boolean _is_visible, String _status_name, int _frames_activating) {
        super(graphic, _map_object_admin, _id, _camera);

        has_activated = false;
        frames_activating = _frames_activating;
        player = map_object_admin.getPlayer();
        is_visible = _is_visible;

        status_name = _status_name;
    }

    public void init() {
        super.init();

    }

    public void update() {
        super.update();

        //マップに存在している
        if (exists == true) {

            //Trapを踏む && このTrapを初めて踏む
            if (player.isWithinReach(w_x, w_y, REACH_FOR_PLAYER) == true && has_activated == false) {
                has_activated = true;
                is_visible = true;

                player.setBadStatus(status_name, frames_activating);
            }
        }

        if (has_activated == true && exists == true) {
            frames_activating--;
        }
        if (frames_activating <= 0) {
            exists = false;
        }

    }

    public boolean isVisible() {
        return is_visible;
    }

    public void setId(int _id) {

        id = _id;
    }
}
