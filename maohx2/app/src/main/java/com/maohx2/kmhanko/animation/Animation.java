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
    //AnimationBitmapData animation_bitmap_data;

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
    float extend_x;
    float extend_y;
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
        extend_x = 1.0f;
        extend_y = 1.0f;
        angle = 0;
        alpha = 0;
        is_start = false;
        is_pause = false;
        is_loop = false;
    }

    public void setAnimationBitmapDataName(String _animationBitmapDataName) {
        exist = true;
        name = _animationBitmapDataName;
        //name = animation_bitmap_data.getImageName();
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
        size = animation_data.getSteps();
    }

    public void setPosition(int _original_x, int _original_y) {
        //基本座標の更新
        original_x = _original_x;
        original_y = _original_y;
    }

    public void setTime(int animationTime) {
        for(int i = 0; i < animation_data.getSteps(); i++) {
            if (animationTime < animation_data.getTime(i)) {
                time = animationTime;
                step = i;
                break;
            }
            animationTime -= animation_data.getTime(i);
        }
        setStepAndTime();
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
            extend_x = calcGraduallyFloat(animation_data.getExtendX(step), animation_data.getExtendX(nextstep), time, step);
            extend_y = calcGraduallyFloat(animation_data.getExtendY(step), animation_data.getExtendY(nextstep), time, step);
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

    public void draw() {
        //Graphicに描画を頼む
        graphic.bookingDrawBitmap(name, new Point(original_x + x, original_y + y), extend_x, extend_y, angle, alpha, false);
    }

    private void toNextStep() {
        step++;
        time = 0;
        setStepAndTime();
    }

    private void setStepAndTime() {
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

    private float calcGraduallyFloat(float param, float pre_param, int time, int step) {
        int time_max = animation_data.getTime(step);
        return (param - pre_param)*(float)time/(float)time_max + pre_param;
    }

    public boolean isExist() {
        return exist;
    }

    public boolean isPause() {
        return is_pause;
    }
    public void setPause(boolean _is_pause) {
        is_pause = _is_pause;
    }

    public void pause() {
        is_pause = true;
    }

    public void restart() {
        is_pause = false;
    }

}




//Graphic#drawBooking(String image_name, Point point)