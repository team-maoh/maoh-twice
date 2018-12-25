package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;


//import com.maohx2.ina.MySprite;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.horie.map.Camera;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapTrap extends MapInanimate {

    int EFFECT_TEXT_FRAMES_MAX = 100;
    int effect_text_frames;
    double REACH_FOR_PLAYER = 25;
    int CONST_FRAMES_ACTIVATING = 30 * 4;//トラップ継続時間
    int frames_activating;
    MapPlayer player;
    String name;
    boolean has_activated, is_visible;
    SoundAdmin sound_admin;


    public MapTrap(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, boolean _is_visible, String _name, SoundAdmin _sound_admin) {
        super(graphic, _map_object_admin, _id, _camera);

        has_activated = false;
        frames_activating = CONST_FRAMES_ACTIVATING;
        player = map_object_admin.getPlayer();
        is_visible = _is_visible;
        sound_admin = _sound_admin;

        name = _name;
        effect_text_frames = 0;

    }
//
//    public MapTrap(Graphic graphic, MapObjectAdmin _map_object_admin, int _id, Camera _camera, boolean _is_visible, String _name, int _frames_activating) {
//        super(graphic, _map_object_admin, _id, _camera);
//
//        has_activated = false;
//        frames_activating = _frames_activating;
//        player = map_object_admin.getPlayer();
//        is_visible = _is_visible;
//
//        name = _name;
//    }

    public void init() {
        super.init();

    }

    public void update() {
        super.update();

        //マップに存在している
        if (exists == true) {

            //Trapを踏む && このTrapを初めて踏む && ここはボスフロアではない
            boolean cond1 = player.isWithinReach(w_x + map_object_admin.getMagnification() / 4, w_y, REACH_FOR_PLAYER);
            boolean cond2 = map_admin.getNow_floor_num() != map_admin.getBoss_floor_num();
            if (cond1 && cond2 && has_activated == false) {
                // + map_object_admin.getMagnification() / 4
                // はTrapの画像と当たり判定の帳尻を合わすための姑息な項
                has_activated = true;
                is_visible = true;

                player.setBadStatus(name, frames_activating);
                if (name.equals("being_teleported")) {
                    sound_admin.play("warp01");//SE
                } else {
                    sound_admin.play(name);//SE
                }

                map_object_admin.displayEffectText(name);
                effect_text_frames = EFFECT_TEXT_FRAMES_MAX;
            }

        }

        if (has_activated == true && exists == true) {
            frames_activating--;
        }
        if (frames_activating <= 0) {
            exists = false;
        }

        if (effect_text_frames > 1) {
            effect_text_frames--;
        } else if (effect_text_frames == 1) {
            map_object_admin.eraseEffectBox();
            effect_text_frames--;
        }

    }

    public boolean isVisible() {
        return is_visible;
    }

    public void setId(int _id) {

        id = _id;
    }

    public void setName(String _name) {

        if (_name.equals("walking_slowly") || _name.equals("walking_inversely") || _name.equals("cannot_walk") || _name.equals("being_drunk") || _name.equals("being_teleported") || _name.equals("cannot_exit_room") || _name.equals("being_blown_away") || _name.equals("found_by_enemy")) {

            name = _name;

        } else {

            throw new Error("%☆◆フジワラ:spawnMapObject()で間違ったTrap名を渡している");//アプリを落とす

        }

    }


}