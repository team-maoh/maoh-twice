package com.maohx2.kmhanko.animation;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.myavail.MyAvail;

import java.util.ArrayList;
import java.util.List;

import com.maohx2.kmhanko.myavail.MyAvail;
import com.maohx2.ina.Draw.Graphic;

import android.graphics.Point;

/**
 * Created by user on 2017/10/15.
 */

public class Animation {
    AnimationBitmapData animation_bitmap_data;

    AnimationData animation_data;

    static private Graphic graphic;

    String name;
    int size;

    int time;
    int step;

    int original_x;
    int original_y;

    int id;
    int x;
    int y;
    double extend_x;
    double extend_y;
    int angle;
    int alpha;

    boolean is_start;
    boolean is_pause;

    boolean is_loop;

    boolean exist;


    public Animation() {
        exist = false;
    }

    public void create() {
        exist = true;
    }

    public void clear() {
        exist = false;
        AnimationData animation_data = null;
        String name = "";
        size = 0;
        time = 0;
        step = 0;
        original_x = 0;
        original_y = 0;
        id = 0;
        x = 0;
        y = 0;
        extend_x = 1.0;
        extend_y = 1.0;
        angle = 0;
        alpha = 0;
        is_start = false;
        is_pause = false;
        is_loop = false;
    }

    public void setAnimationBitmapData(AnimationBitmapData _animation_bitmap_data) {
        exist = true;
        animation_bitmap_data = _animation_bitmap_data;
        name = animation_bitmap_data.getImageName();
        size = animation_bitmap_data.getBitmapDatas().size();
    }

    static public void setGraphic(Graphic _graphic) {
        graphic = _graphic;
    }

    public void setAnimationData(AnimationData _animation_data) {
        animation_data = _animation_data;
    }

    public void start() {
        time = 0;
        step = 0;
        is_start = true;
        setParam();
        is_loop = (animation_data.getTime(size - 1) == 0);
    }

    public void setPosition(int _original_x, int _original_y) {
        //基本座標の更新
        original_x = _original_x;
        original_y = _original_y;
    }

    public void update() {
        if (!is_start) {
            return;
        }
        if (is_pause) {
            return;
        }
        if (step == size - 1 && is_loop == false) {
            //ループせず、アニメーションの末端なら更新しない
            return;
        }

        if (animation_data.isSwitchGr(step)) {

            int nextstep = step + 1;
            if (nextstep >= size) {
                if (is_loop) {
                    nextstep = 0;
                }
            }

            x = calcGraduallyInt(animation_data.getX(step), animation_data.getX(nextstep), time, step);
            y = calcGraduallyInt(animation_data.getY(step), animation_data.getY(nextstep), time, step);
            extend_x = calcGraduallyDouble(animation_data.getExtendX(step), animation_data.getExtendX(nextstep), time, step);
            extend_y = calcGraduallyDouble(animation_data.getExtendY(step), animation_data.getExtendY(nextstep), time, step);
            angle = calcGraduallyInt(animation_data.getAngle(step), animation_data.getAngle(nextstep), time, step);
            alpha= calcGraduallyInt(animation_data.getAlpha(step), animation_data.getAlpha(nextstep), time, step);
        }

        if (animation_data.getTime(step) > time) {
            if (is_loop) {
                toNextStep();
            }
        } else {
            time++;
        }
    }

    public void drawBooking() {
        //Graphicに描画を頼む
        //graphic.drawBooking(name, new Point(original_x + x, original_y + y), id, extend_x, extend_y, angle, alpha);
    }

    private void toNextStep() {
        step++;
        time = 0;

        if (step >= size) {
            step = 0;
        }

        if (!animation_data.isSwitchGr(step)) {
            //Immediately
            setParam();
        }

        //Immediately,Graduallyに関わらず実行する。
        id = animation_data.getID(step);
    }

    private void setParam() {
        x = animation_data.getX(step);
        y = animation_data.getY(step);
        extend_x = animation_data.getExtendX(step);
        extend_y = animation_data.getExtendY(step);
        angle = animation_data.getAngle(step);
        alpha = animation_data.getAlpha(step);
    }

    private int calcGraduallyInt(int param, int pre_param, int time, int step) {
        int time_max = animation_data.getTime(step);
        return (int)((double)(param - pre_param)*(double)time/(double)time_max) + pre_param;
    }

    private double calcGraduallyDouble(double param, double pre_param, int time, int step) {
        int time_max = animation_data.getTime(step);
        return (param - pre_param)*(double)time/(double)time_max + pre_param;
    }

    public boolean isExist() {
        return exist;
    }

    public boolean isPause() {
        return is_pause;
    }
    public void setIsPause(boolean _is_pause) {
        is_pause = _is_pause;
    }

}




//Graphic#drawBooking(String image_name, Point point)