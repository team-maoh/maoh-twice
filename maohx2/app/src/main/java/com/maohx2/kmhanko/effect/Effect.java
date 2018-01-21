package com.maohx2.kmhanko.effect;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/01/19.
 */

public class Effect {
    EffectData effectData;

    static private Graphic graphic;
    static private SoundAdmin soundAdmin;

    List<String> soundName = new ArrayList<String>();
    List<BitmapData> bitmapData = new ArrayList<BitmapData>();

    int steps;

    int time;
    int step;

    int original_x;
    int original_y;

    int imageID;
    int soundID;
    int x;
    int y;
    float extend_x;
    float extend_y;
    float angle;
    int alpha;
    boolean isUpLeft;

    boolean is_start;
    boolean is_pause;

    boolean exist;


    public Effect() {
        exist = false;
    }

    public static void staticInit(Graphic _graphic, SoundAdmin _soundAdmin) {
        graphic = _graphic;
        soundAdmin = _soundAdmin;
    }

    public void create(EffectData _effectData) {
        exist = true;
        effectData = _effectData;
    }

    public void clear() {
        exist = false;
        EffectData effectData = null;
    }

    public void start() {
        time = 0;
        step = 0;
        is_start = true;
        steps = effectData.getSteps();
        toStep(0);
    }

    public void setPosition(int _original_x, int _original_y) {
        //基本座標の更新
        original_x = _original_x;
        original_y = _original_y;
    }

    /*
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
    */

    public void update() {
        if (!is_start) {
            return;
        }
        if (is_pause) {
            return;
        }
        if (step == steps - 1 && effectData.getNextID(step) == -1) {
            //ループせず、アニメーションの末端なら更新しない
            return;
        }

        if (effectData.isSwitchGr(step)) {
            //徐々に変化する場合

            int nextstep = step + 1;
            if (nextstep >= steps) {
                if ((effectData.getNextID(step) != -1)) {
                    //ループする場合
                    nextstep = effectData.getNextID(step);
                } else {
                  //ループしない場合。そもそもここには原理的に到達しない
                }
            }

            x = calcGraduallyInt(effectData.getX(step), effectData.getX(nextstep), time, step);
            y = calcGraduallyInt(effectData.getY(step), effectData.getY(nextstep), time, step);
            extend_x = calcGraduallyFloat(effectData.getExtendX(step), effectData.getExtendX(nextstep), time, step);
            extend_y = calcGraduallyFloat(effectData.getExtendY(step), effectData.getExtendY(nextstep), time, step);
            angle = calcGraduallyFloat(effectData.getAngle(step), effectData.getAngle(nextstep), time, step);
            alpha = calcGraduallyInt(effectData.getAlpha(step), effectData.getAlpha(nextstep), time, step);
        } else {
            //即時変化の場合
            //待機するだけなので何もない
        }

        if (effectData.getTime(step) < time) {
            //そのステップのラストに到達した
            if (effectData.getNextID(step, step) > steps) {
                //ループしない場合
                //特に何もしない。以降、updateを読んでも即座にreturnされる
            } else {
                //指定IDのステップへ遷移する。ループする場合もここで自動的に処理される。
                toStep(effectData.getNextID(step, step));
            }
        } else {
            //そのステップのラストにまだ達していない
            time++;
        }
    }

    public void draw() {
        //Graphicに描画を頼む
        //graphic.bookingDrawBitmapName(, new Point(original_x + x, original_y + y), extend_x, extend_y, angle, alpha, isUpLeft);
    }

    //ステップ遷移するメソッド
    private void toStep(int _step) {
        step = _step;
        startStep();
    }

    //新たなStepに移動した時に呼ぶメソッド
    private void startStep() {
        time = 0;
        setParam(step);
        sound();
    }

    //パラメータをセットするメソッド
    private void setParam(int _step) {
        x = effectData.getX(_step);
        y = effectData.getY(_step);
        extend_x = effectData.getExtendX(_step);
        extend_y = effectData.getExtendY(_step);
        angle = effectData.getAngle(_step);
        alpha = effectData.getAlpha(_step);

        isUpLeft = effectData.isUpLeft(_step);
        imageID = effectData.getImageID(_step);
        soundID = effectData.getSoundID(_step);
    }

    //効果音を鳴らすメソッド
    private void sound() {
        if (soundID != -1) {
            soundAdmin.play(soundName(soundID));
        }
    }

    private int calcGraduallyInt(int param, int pre_param, int time, int step) {
        int time_max = effectData.getTime(step);
        return (int)((double)(param - pre_param)*(double)time/(double)time_max) + pre_param;
    }

    private float calcGraduallyFloat(float param, float pre_param, int time, int step) {
        int time_max = effectData.getTime(step);
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
    public void pause() { is_pause = true; }
    public void restart() {
        is_pause = false;
    }

}
