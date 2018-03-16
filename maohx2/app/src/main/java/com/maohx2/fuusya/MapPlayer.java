package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.icu.text.SymbolTable;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.MySprite;
import java.util.Random;

import static com.maohx2.ina.Constants.Touch.TouchState;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.signum;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapPlayer extends MapUnit {

    int th_encount_steps;
    int mean_encount_steps, var_encount_steps;
    int SOUND_STEPS_PERIOD = 20;//この歩数ごとに足音SEが鳴る
    int TOUCHING_FRAME_NUM = 10;
    //
    int encount_steps;
    int sound_steps;

    SoundAdmin sound_admin;
    DungeonUserInterface dungeon_user_interface;

    int PLAYER_STEP = 26;//プレイヤーの歩幅
    double touch_w_x, touch_w_y, touch_n_x, touch_n_y, pre_w_x, pre_w_y;
    boolean is_moving;

    int touching_frame_count;

    TouchState touch_state;

    public MapPlayer(Graphic graphic, MapObjectAdmin _map_object_admin, MapAdmin _map_admin, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin, Camera _camera) {
        super(graphic, _map_object_admin, _camera);

        dungeon_user_interface = _dungeon_user_interface;

        touch_state = dungeon_user_interface.getTouchState();

        sound_admin = _sound_admin;

        mean_encount_steps = 50;
        var_encount_steps = 10;
        th_encount_steps = makeThresholdEncountSteps();

        encount_steps = 0;
        sound_steps = 0;


        touching_frame_count = 0;
        step = PLAYER_STEP;

        is_moving = false;

        can_exit_room = true;
    }

    public void init() {
        super.init();
    }

    @Override
    public void update() {

        touch_state = dungeon_user_interface.getTouchState();

        if (touch_state == TouchState.DOWN || touch_state == TouchState.DOWN_MOVE || touch_state == TouchState.MOVE) {

            touch_n_x = dungeon_user_interface.getTouchX();
            touch_n_y = dungeon_user_interface.getTouchY();
            touch_w_x = camera.convertToWorldCoordinateX((int) touch_n_x);
            touch_w_y = camera.convertToWorldCoordinateY((int) touch_n_y);

            dst_w_x = touch_w_x;
            dst_w_y = touch_w_y;

            touching_frame_count++;

            is_moving = true;
        } else if (touching_frame_count > TOUCHING_FRAME_NUM) {//画面を10frames以上タッチした場合、

            touching_frame_count = 0;

            is_moving = false;//指を離した時点で歩行を中止する
        }

        //BadStatus（状態異常）を表現するためにstepとdst_w_x, dst_w_yを適宜書き換える
        step = checkBadStatus(PLAYER_STEP);

        if (has_bad_status == true) {
            updateDirOnMap(dst_w_x, dst_w_y);

            if (can_walk == true) {

                if (being_blown_away == true) {
                    collide_wall = walkOneStep(dst_w_x, dst_w_y, step);
                    collide_wall = walkOneStep(dst_w_x, dst_w_y, step);
                    collide_wall = walkOneStep(dst_w_x, dst_w_y, step);
                    System.out.println("paopao1");
                    if (collide_wall == true) {
                        being_blown_away = false;
                    }
                } else {
                    System.out.println("paopao2");
                    walkOneStep(dst_w_x, dst_w_y, step);
                }
            }

        } else if (is_moving == true) {
//        if ((is_moving == true) || (has_bad_status = true)) {

//            //BadStatus（状態異常）を表現するためにstepとdst_w_x, dst_w_yを適宜書き換える
//            //歩行不能時は step = 0 と書かれる
//            step = checkBadStatus(PLAYER_STEP);
//            //Playerアイコンの向きを更新する
            updateDirOnMap(dst_w_x, dst_w_y);

            System.out.println("paopao4");
            walkOneStep(dst_w_x, dst_w_y, step);

            //壁との衝突を考慮した上で、タッチ座標に向かって１歩進む
//            walkOneStep(dst_w_x, dst_w_y, step);
////            //Playerアイコンの向きを更新する
//            updateDirOnMap(dst_w_x, dst_w_y);

            encount_steps++;
            if (encount_steps >= th_encount_steps) {
                System.out.println("◆一定歩数 歩いたので敵と遭遇");
                encount_steps = 0;
                th_encount_steps = makeThresholdEncountSteps();
            }

            sound_steps = (sound_steps + 1) % SOUND_STEPS_PERIOD;
            if (sound_steps == 0) {
                sound_admin.play("walk");//足音SE
            }

        } else {//moving == false のとき
            touching_frame_count = 0;
        }

        camera.setCameraOffset(w_x, w_y);

        //walkOneStep()でタッチ座標にたどり着くと自然に立ち止まる
        //立ち止まっている場合にはis_movingをfalseにする（さもなくば足音SEが出続ける）
        if (pre_w_x == w_x && pre_w_y == w_y) {
            is_moving = false;
        }
        pre_w_x = w_x;
        pre_w_y = w_y;
    }

    public void setMeanEncountSteps(int _mean) {
        mean_encount_steps = _mean;
    }

    public void setVarEncountSteps(int _var) {
        var_encount_steps = _var;
    }

    private int makeThresholdEncountSteps() {

        double normal_dist = makeNormalDist();

        return (int) (var_encount_steps * normal_dist + mean_encount_steps);
    }

    public double getTouchWouldX() {
        return touch_w_x;
    }

    public double getTouchWouldY() {
        return touch_w_y;
    }

    public boolean getIsMoving() {
        return is_moving;
    }

}