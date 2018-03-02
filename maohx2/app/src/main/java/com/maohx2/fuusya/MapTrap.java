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
    int FRAMES_ACTIVATING = 100;
    int frames_activating;
    boolean is_activating;
    MapPlayer player;

    public MapTrap(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera) {
        super(graphic, _map_object_admin, _id, _camera);

        frames_activating = FRAMES_ACTIVATING;
        is_activating = false;
        player = map_object_admin.getPlayer();

    }

    public void init() {
        super.init();

    }

    public void update() {
        super.update();

        if (exists == true) {
            if (collidePlayer(REACH_FOR_PLAYER) == true) {
                System.out.println("トラップを踏んだ");
                is_activating = true;

                player.setFramesBeingDrunk(FRAMES_ACTIVATING);
//                sound_admin.play("getItem");
//                exists = false;
            }
        }

        if (is_activating == true) {
            frames_activating--;
        }

        if(frames_activating <= 0){
            is_activating = false;
            exists = false;
        }

    }

    public void setId(int _id) {

        id = _id;
    }
}
