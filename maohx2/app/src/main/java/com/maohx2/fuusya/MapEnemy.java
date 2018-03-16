package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.SurfaceHolder;

import static com.maohx2.ina.Constants.Touch.TouchState;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;


//import com.maohx2.ina.MySprite;
// import com.maohx2.ina.waste.MySprite;

import java.util.Random;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapEnemy extends MapUnit {

    double DEFAULT_STEP = 8;//プレイヤー未発見時の歩幅
    int chase_count;
    int REACH_FOR_PLAYER = 25;
    int total_dirs;//画像が1方位なのか、4方位なのか、8方位なのか(Playerの視界に入っているかどうかの判定に使う)

    int CHASE_STEPS = 6;//名前は仮 / EnemyはPlayerの{現在座標ではなく}CHASE_STEPS歩前の座標を追いかける
    double[] chase_w_x = new double[CHASE_STEPS];
    double[] chase_w_y = new double[CHASE_STEPS];

    double clock_rad;
    int FOUND_DWELL_FRAMES = 15;
    int found_dwell_count;

    boolean has_found_player;//プレイヤーを発見しているかどうか
    boolean detect_player, has_blind_spot;//Playerを発見するか否か, 全方位が見えているか否か
    double incremental_step;//Playerを発見して時間が経つとstepが徐々に増えていく（= 足が速くなっていく）

    public MapEnemy(Graphic graphic, MapObjectAdmin map_object_admin, Camera _camera, int _total_dirs, boolean _detect_player, boolean _has_blind_spot) {
        super(graphic, map_object_admin, _camera);

        total_dirs = _total_dirs;

        Point room_point = map_admin.getRoomPoint();
        w_x = room_point.x;
        w_y = room_point.y;

        dst_w_x = w_x;
        dst_w_y = w_y;

        chase_count = 0;
        for (int i = 0; i < CHASE_STEPS; i++) {
            chase_w_x[i] = 0.0;
            chase_w_y[i] = 0.0;
        }

        draw_object = "ゴキ太郎";


        clock_rad = 0.0;


        step = DEFAULT_STEP;

        found_dwell_count = FOUND_DWELL_FRAMES;

        has_found_player = false;

        detect_player = _detect_player;
        has_blind_spot = _has_blind_spot;
        incremental_step = 0;

        can_exit_room = false;
    }

    public void init() {
        super.init();
    }

    @Override
    public void update() {

        if (exists == true) {

            if (detect_player == true) {

                //[Playerを発見しているか否か]を更新
                if (has_found_player == false && exposedToPlayer() == true) {//未発見→発見（見つける）
                    if (playerWithinEyesight() == true || has_blind_spot == false) {
                        has_found_player = true;//Playerと自身の間に壁(or玄関マス)が無い && Playerが視野角内に居る
                    }

                    //found motion(「！」を出すとか)
                    found_dwell_count = FOUND_DWELL_FRAMES;

                }
                if (has_found_player == true && exposedToPlayer() == false) {//発見→未発見（見失う）
                    has_found_player = false;//Playerと自身の間に壁(or玄関マス)がある
                    incremental_step = 0;
                }
            }

            //Player座標をchase[]に格納
            chase_w_x[chase_count] = player.getWorldX();
            chase_w_y[chase_count] = player.getWorldY();
            chase_count = (chase_count + 1) % CHASE_STEPS;

            if (has_found_player == false) {//プレイヤーを発見していない
//                step = DEFAULT_STEP;

                double random_double = makeNormalDist() * 0.4;
                //念のため
                while (!(-0.6 <= random_double && random_double < 0.6)) {
                    random_double = makeNormalDist() * 0.4;
                }
                step = DEFAULT_STEP * (1 + random_double);

                //あてどもなく徘徊する(dst_x, dst_yを設定)
                wander_about();

            } else {//プレイヤーを発見している

                if (found_dwell_count > 0) {
                    found_dwell_count--;//FOUND_DWELL_FRAMESフレームだけ歩行を停止する

                } else {

                    step = 3 * DEFAULT_STEP + incremental_step;

                    incremental_step += DEFAULT_STEP / 100;

                    if (step >= player.getStep()) {
                        dst_w_x = player.getWorldX();
                        dst_w_y = player.getWorldY();
                    } else {
                        //●CHASE_STEPS歩前のプレイヤー座標を目標座標とする
                        dst_w_x = chase_w_x[chase_count];
                        dst_w_y = chase_w_y[chase_count];
                    }

                    //●プレイヤーがタッチしている間だけ移動する
                    //プレイヤーのタッチ座標をそのまま目標座標とする
//                if (player.getIsMoving() == true) {
//                    dst_x = player.getTouchWouldX();
//                    dst_y = player.getTouchWouldY();
//                } else {
//                    dst_x = w_x;
//                    dst_y = w_y;
//                }

                    //独楽のようにくるくる回る
//        clock_rad = (clock_rad + PI / 30.0) % (2 * PI);
//        System.out.println(clock_rad);
//        double clock_w_x = w_x + 10 * cos(clock_rad);
//        double clock_w_y = w_y + 10 * sin(clock_rad);
//        updateDirOnMap(clock_w_x, clock_w_y);
                    //自身の視界（角度cos(PI/6.0)の範囲）にプレイヤーが入るとprintする
//        double length = myDistance(player.getWorldX(), player.getWorldY(), w_x, w_y);
//        double inner_prod = cos(dir_on_map) * (player.getWorldX() - w_x) / length + sin(dir_on_map) * (player.getWorldY() - w_y) / length;
//        System.out.println("uncchi" + inner_prod);
//        if (inner_prod > cos(PI / 6.0)) {
//            System.out.println("●uncchi" + inner_prod);
//        }
                    //
                    //[0, 7]の8方位で[自分の向き]と[自分から見てプレイヤーの居る方角]が一致した場合、プレイヤーを追いかける
//        if (convertDirToIntDir(8, dir_on_map) == convertDirToIntDir(8, calcDirOnMap(player.getWorldX(), player.getWorldY()))) {
//            dst_x = player.getWorldX();
//            dst_y = player.getWorldY();
//        }


                }

            }

            step = checkBadStatus(step);
            walkOneStep(dst_w_x, dst_w_y, step);//一歩進む
            //
            updateDirOnMap(dst_w_x, dst_w_y);



        }

        double distance_for_player = myDistance(player.getWorldX(), player.getWorldY(), w_x, w_y);

        if (distance_for_player < REACH_FOR_PLAYER && exists == true) {
            System.out.println("敵と接触");
            //デバッグのためにコメントアウト
            exists = false;
//                map_enemy[i].setExists(false);//接触すると敵が消える(戦闘に突入する)
        }


    }

    //Playerから見える場所にいるorいない（Playerと自身の中間に壁(or玄関マス)が無いor有る）
    private boolean exposedToPlayer() {

        int num_of_units = (int) (myDistance(w_x, w_y, player.getWorldX(), player.getWorldY()) / step);
        num_of_units++;
        double unit_x = (player.getWorldX() - w_x) / num_of_units;
        double unit_y = (player.getWorldY() - w_y) / num_of_units;

        double x_0 = w_x;
        double y_0 = w_y;
        double x_1 = w_x + unit_x;
        double y_1 = w_y + unit_y;

        if (map_admin.isEntrance(map_admin.worldToMap((int) x_0), map_admin.worldToMap((int) y_0)) == true) {
            return false;
        }

        for (int i = 0; i < num_of_units; i++) {
            if (detectWall(x_0, y_0, x_1, y_1) != 0 || map_admin.isEntrance(map_admin.worldToMap((int) x_1), map_admin.worldToMap((int) y_1)) == true) {
                return false;
            }
            x_0 += unit_x;
            y_0 += unit_y;
            x_1 += unit_x;
            y_1 += unit_y;
        }

        return true;

    }

    private boolean playerWithinEyesight() {

        //Playerが自分の視界に入っているか否かを{total_dirs}方位で判定する
        if (convertDirToIntDir(total_dirs, calcDirOnMap(player.getWorldX(), player.getWorldY())) == convertDirToIntDir(total_dirs, dir_on_map)) {
            return true;
        } else {
            return false;
        }
    }

    private void wander_about() {

        double random_double = makeNormalDist() * 0.08;
        //念のため
        while (!(-1.0 <= random_double && random_double < 1.0)) {
            random_double = makeNormalDist() * 0.08;
        }

        //pre_random_radを平均とした正規分布
        double random_rad = random_double * PI + pre_random_rad;

        dst_w_x = w_x + 2 * step * cos(random_rad);
        dst_w_y = w_y + 2 * step * sin(random_rad);

        switch (detectWall(w_x, w_y, dst_w_x, dst_w_y)) {
            case 1://横向きの壁と衝突
                random_rad = 2 * PI - random_rad;
                dst_w_x = w_x + 2 * step * cos(random_rad);
                dst_w_y = w_y + 2 * step * sin(random_rad);
                break;
            case 2://縦向きの壁と衝突
                random_rad = (3 * PI - random_rad) % (2 * PI);
                dst_w_x = w_x + 2 * step * cos(random_rad);
                dst_w_y = w_y + 2 * step * sin(random_rad);
                break;
            default:
                break;
        }

        pre_random_rad = random_rad;
    }

    public void setHasFoundPlayer(boolean _has_found_player){
        has_found_player = _has_found_player;
    }

}


