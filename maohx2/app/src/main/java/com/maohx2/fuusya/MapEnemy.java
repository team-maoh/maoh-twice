package com.maohx2.fuusya;

import android.graphics.Bitmap;
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

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapEnemy extends MapUnit {

    MapPlayer player;
    int DEFAULT_STEP = 10;//プレイヤー未発見時の歩幅
    int step;
    int chase_count;
    double dst_x, dst_y;
    int total_dirs;//画像が1方位なのか、4方位なのか、8方位なのか(Playerの視界に入っているかどうかの判定に使う)

//    int DWELLING_STEPS = 30;
//    int dwell_count;
//    double[] pre_w_x = new double[DWELLING_STEPS];
//    double[] pre_w_y = new double[DWELLING_STEPS];
//    boolean is_dwelling;

    int CHASE_STEPS = 6;//名前は仮 / EnemyはPlayerの{現在座標ではなく}CHASE_STEPS歩前の座標を追いかける
    double[] chase_w_x = new double[CHASE_STEPS];
    double[] chase_w_y = new double[CHASE_STEPS];

    double clock_rad;
    double pre_random_rad;
    boolean has_found_player;//プレイヤーを発見しているかどうか

    public MapEnemy(Graphic graphic, MapObjectAdmin map_object_admin, MapAdmin _map_admin, Camera _camera, int _total_dirs) {
        super(graphic, map_object_admin, _map_admin, _camera);

        total_dirs = _total_dirs;

        player = map_object_admin.getPlayer();

        w_x = map_admin.getRoomPoint().x * 64 + 32;
        w_y = map_admin.getRoomPoint().y * 64 + 32;
//        よく考えたら意味ない
//        double tmp_x_1 = w_x + step;
//        double tmp_x_2 = w_x - step;
//        double tmp_y_1 = w_y + step;
//        double tmp_y_2 = w_y - step;
//        int cond = detectWall(w_x, w_y, tmp_x_1, tmp_y_1) * detectWall(w_x, w_y, tmp_x_2, tmp_y_1) * detectWall(w_x, w_y, tmp_x_1, tmp_y_2) * detectWall(w_x, w_y, tmp_x_2, tmp_y_2);
//        if (cond != 0) {
//            w_x = map_admin.getRoomPoint().x * 64 + 32;
//            w_y = map_admin.getRoomPoint().y * 64 + 32;
//        }

        dst_x = w_x;
        dst_y = w_y;

        chase_count = 0;
        for (int i = 0; i < CHASE_STEPS; i++) {
            chase_w_x[i] = 0.0;
            chase_w_y[i] = 0.0;
        }
//        for (int i = 0; i < DWELLING_STEPS; i++) {
//            pre_w_x[i] = w_x;
//            pre_w_y[i] = w_y;
//        }
//        dwell_count = 0;
//        is_dwelling = false;

        draw_object = "ゴキ太郎";

        has_found_player = false;

        clock_rad = 0.0;

        pre_random_rad = random.nextDouble() * 2 * PI;

        step = DEFAULT_STEP;
    }

    public void init() {
        super.init();
    }

    @Override
    public void update() {

        if (exists == true) {

            //[Playerを発見しているか否か]を更新
//            has_found_player = InViewOfThePlayer();
//            has_found_player = (inViewOfThePlayer() && playerWithinEyesight());

            if (has_found_player == false) {//未発見→発見（見つける）
                has_found_player = playerWithinEyesight() && hidFromPlayer();
            } else {//発見→未発見（見失う）
                has_found_player = hidFromPlayer();
            }


            double dx, dy;

            if (has_found_player == false) {//プレイヤーを発見していない
                step = DEFAULT_STEP;

                //あてどもなく徘徊する(dx, dyを設定)
                wander_about();

            } else {//プレイヤーを発見している
                step = 3 * DEFAULT_STEP;

                //●CHASE_STEPS歩前のプレイヤー座標を目標座標とする
                chase_w_x[chase_count] = player.getWorldX();
                chase_w_y[chase_count] = player.getWorldY();
                chase_count = (chase_count + 1) % CHASE_STEPS;
                dst_x = chase_w_x[chase_count];
                dst_y = chase_w_y[chase_count];

                //●プレイヤーがタッチしている間だけ移動する
                //プレイヤーのタッチ座標をそのまま目標座標とする
//                if (player.getIsMoving() == true) {
//                    dst_x = player.getTouchWouldX();
//                    dst_y = player.getTouchWouldY();
//                } else {
//                    dst_x = w_x;
//                    dst_y = w_y;
//                }

                //くるくる回る
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

            // dst_x, dst_yの方向に一歩進む
            dst_steps = (int) (myDistance(w_x, w_y, dst_x, dst_y) / (double) step);
            dst_steps++;//dst_steps = 0 のときゼロ除算が発生するので
            //
            dx = (dst_x - w_x) / dst_steps;
            dy = (dst_y - w_y) / dst_steps;
            walkOneStep(dx, dy, true);//一歩進む
            //
            updateDirOnMap(w_x + dx, w_y + dy);


//            boolean test = false;
//            dwell_count = (dwell_count + 1) % DWELLING_STEPS;
//            pre_w_x[dwell_count] = w_x;
//            pre_w_y[dwell_count] = w_y;
//            for (int i = 0; i < DWELLING_STEPS; i++) {
//                if (pre_w_x[i] == w_x && pre_w_y[i] == w_y) {
//                    test = true;
//                }
//            }
//            if (test == true) {
//                System.out.println("dwellしている");
//                is_dwelling = true;
//            } else {
//                is_dwelling = false;
//            }

        }

    }

    private double myDistance(double x1, double y1, double x2, double y2) {
        return (pow(pow(x1 - x2, 2.0) + pow(y1 - y2, 2.0), 0.5));
    }

    //Playerから見える場所にいるorいない（Playerと自身の間に壁が無いor有る）
    private boolean hidFromPlayer() {

        boolean tmp_has_found_player = true;

        int num_of_units = (int) (myDistance(w_x, w_y, player.getWorldX(), player.getWorldY()) / (double) step);
        num_of_units++;
        double unit_x = (player.getWorldX() - w_x) / num_of_units;
        double unit_y = (player.getWorldY() - w_y) / num_of_units;

        double x_0 = w_x;
        double y_0 = w_y;
        double x_1 = w_x + unit_x;
        double y_1 = w_y + unit_y;

//        has_found_player = false;//デバッグのため
        for (int i = 0; i < num_of_units; i++) {
            if (detectWall(x_0, y_0, x_1, y_1) != 0) {
                tmp_has_found_player = false;
                break;
            }
            x_0 += unit_x;
            y_0 += unit_y;
            x_1 += unit_x;
            y_1 += unit_y;
        }

        return tmp_has_found_player;

    }

    private boolean playerWithinEyesight() {

        //Playerが自分の視界に入っているか否かを{total_dirs}方位で判定する
        if (convertDirToIntDir(total_dirs, calcDirOnMap(player.getWorldX(), player.getWorldY())) == convertDirToIntDir(total_dirs, dir_on_map)) {
            return true;
        } else {
            return false;
        }

//        if(abs(calcDirOnMap(player.getWorldX(),player.getWorldY())-dir_on_map) < PI/4){
//            return true;
//        }else{
//            return false;
//        }

    }

    private void wander_about() {

        double random_double = makeNormalDist() * 0.08;
        //念のため
        while (!(-1.0 <= random_double && random_double < 1.0)) {
            random_double = makeNormalDist() * 0.08;
        }

        System.out.println("random_double=" + random_double);

        //pre_random_radを平均とした正規分布
        double random_rad = random_double * PI + pre_random_rad;

        dst_x = w_x + 2 * step * cos(random_rad);
        dst_y = w_y + 2 * step * sin(random_rad);

        switch (detectWall(w_x, w_y, dst_x, dst_y)) {
            case 1://横向きの壁と衝突
                random_rad = 2 * PI - random_rad;
                dst_x = w_x + 2 * step * cos(random_rad);
                dst_y = w_y + 2 * step * sin(random_rad);
                break;
            case 2://縦向きの壁と衝突
                random_rad = (3 * PI - random_rad) % (2 * PI);
                dst_x = w_x + 2 * step * cos(random_rad);
                dst_y = w_y + 2 * step * sin(random_rad);
                break;
            default:
                break;
        }

        pre_random_rad = random_rad;
    }

}


