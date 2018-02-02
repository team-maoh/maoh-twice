package com.maohx2.fuusya;

import android.graphics.Bitmap;
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

    //    int ENCOUNT_STEPS_PERIOD = 500;//この歩数ごとに敵とエンカウントする
    int th_encount_steps;
    int mean_encount_steps, var_encount_steps;
    int SOUND_STEPS_PERIOD = 20;//この歩数ごとに足音SEが鳴る
    //
    int encount_steps;
    int sound_steps;

    SoundAdmin sound_admin;
    DungeonUserInterface dungeon_user_interface;

    int PLAYER_STEP = 40;//プレイヤーの歩幅
    double touch_w_x, touch_w_y, touch_n_x, touch_n_y;
    boolean is_moving;

    int touching_frame_count;

    TouchState touch_state;

    public MapPlayer(Graphic graphic, MapObjectAdmin _map_object_admin, MapAdmin _map_admin, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin, Camera _camera) {
        super(graphic, _map_object_admin, _map_admin, _camera);

        dungeon_user_interface = _dungeon_user_interface;

        touch_state = dungeon_user_interface.getTouchState();

        sound_admin = _sound_admin;

        mean_encount_steps = 50;
        var_encount_steps = 10;
        th_encount_steps = makeThresholdEncountSteps();

        encount_steps = 0;
        sound_steps = 0;

//        w_x = 550;
//        w_y = 550;
        w_x = map_admin.getRoomPoint().x * 64 + 32;
        w_y = map_admin.getRoomPoint().y * 64 + 32;

        touching_frame_count = 0;

        is_moving = false;

//        random = new Random();
    }

    public void init() {
        super.init();
    }

    @Override
    public void update() {

        touch_state = dungeon_user_interface.getTouchState();
        double dx, dy;

        if (touch_state == TouchState.DOWN || touch_state == TouchState.DOWN_MOVE || touch_state == TouchState.MOVE) {

            touch_n_x = dungeon_user_interface.getTouchX();
            touch_n_y = dungeon_user_interface.getTouchY();
            touch_w_x = camera.convertToWorldCoordinateX((int) touch_n_x);
            touch_w_y = camera.convertToWorldCoordinateY((int) touch_n_y);

            //デバッグ用
//            touch_x = touch_w_x;
//            touch_y = touch_w_y;

            touching_frame_count++;

            is_moving = true;
        } else if (touching_frame_count > 10) {//画面を10frames以上タッチした場合、

            touching_frame_count = 0;

            is_moving = false;//指を離した時点で歩行を中止する
        }

        if (is_moving == true) {

            dst_steps = (int) myDistance(touch_w_x, touch_w_y, w_x, w_y) / PLAYER_STEP;
            dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので
            //一歩あたりの距離
            dx = (touch_w_x - w_x) / dst_steps;
            dy = (touch_w_y - w_y) / dst_steps;

            //壁との衝突を考慮した上で、１歩進む
            walkOneStep(dx, dy, false);

            updateDirOnMap(touch_w_x, touch_w_y);

            encount_steps++;
            if (encount_steps >= th_encount_steps) {
                System.out.println("◆一定歩数 歩いたので敵と遭遇");
                encount_steps = 0;
//                System.out.println("nowawa" + th_encount_steps);
                th_encount_steps = makeThresholdEncountSteps();
            }

//            encount_steps = (encount_steps + 1) % (ENCOUNT_STEPS_PERIOD + 1);
//            if (encount_steps == ENCOUNT_STEPS_PERIOD) {
//                System.out.println("◆一定歩数 歩いたので敵と遭遇");
//            }
            sound_steps = (sound_steps + 1) % (SOUND_STEPS_PERIOD + 1);
            if (sound_steps % SOUND_STEPS_PERIOD == 0) {
                sound_admin.play("walk");//足音SE
            }

            //タッチ座標との距離が一定未満になる ときに歩み止まる
            if (3 > myDistance(touch_w_x, touch_w_y, w_x, w_y)) {
                is_moving = false;
                System.out.println("●タッチ座標との距離が一定未満 1");
            }
            //タッチ座標を通過してしまう ときに歩み止まる
            if (0 > (touch_w_x - (w_x + dx)) * dx || 0 > (touch_w_y - (w_y + dy)) * dy) {
                is_moving = false;
                System.out.println("●タッチ座標との距離が一定未満 2");
            }

        } else {//moving == false のとき
            touching_frame_count = 0;
        }

        //デバッグ用
//        touch_x = camera.convertToNormCoordinateX((int) touch_w_x);
//        touch_y = camera.convertToNormCoordinateY((int) touch_w_y);

        camera.setCameraOffset(w_x, w_y);
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

    //(x1, y1)と(x2, y2)の距離を返す
    private double myDistance(double x1, double y1, double x2, double y2) {
        return pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5);
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