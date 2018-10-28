package com.maohx2.kmhanko.effect;

import android.util.Base64InputStream;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/01/19.
 */

public class Effect {
    private EffectData effectData;

    static private Graphic graphic;
    static private SoundAdmin soundAdmin;

    //private List<String> soundName = new ArrayList<String>();
    //private List<BitmapData> bitmapData = new ArrayList<BitmapData>();
    BitmapData[] bitmapData;

    private int steps;

    private int time;
    private int step;

    private int original_x;
    private int original_y;

    private int imageID;
    private String soundName;
    private int x;
    private int y;
    private float r;
    private float extend_x;
    private float extend_y;

    private float setted_extend_x;
    private float setted_extend_y;

    private float original_angle;
    private float original_angle_deg;

    private float anime_angle;
    private float angle;
    private int alpha;
    private boolean isUpLeft;

    private boolean is_start;
    private boolean is_pause;

    private boolean exist;
    private boolean isDraw;

    public void release() {
        //System.out.println("takanoRelease : Effect");
    }

    public Effect() {
        exist = false;
        isDraw = false;
        is_pause = false;
    }

    public static void staticInit(Graphic _graphic, SoundAdmin _soundAdmin) {
        graphic = _graphic;
        soundAdmin = _soundAdmin;
    }

    public void create(EffectData _effectData) {
        exist = true;
        isDraw = false;
        is_pause = false;
        effectData = _effectData;
    }

    public void clear() {
        exist = false;
        isDraw = false;
        is_pause = false;
        effectData = null;
        original_angle = 0.0f;
        original_angle_deg = 0.0f;
        setted_extend_x = 1.0f;
        setted_extend_y = 1.0f;
        release();
    }

    public void start() {
        time = 0;
        step = 0;
        is_start = true;
        isDraw = true;
        is_pause = false;
        steps = effectData.getSteps();
        toStep(0);
    }
    public void hide() {
        isDraw = false;
        is_pause = true;
    }

    public void setPosition(int _original_x, int _original_y) {
        //基本座標の更新
        original_x = _original_x;
        original_y = _original_y;
    }

    public void setExtends(float _setted_extend_x, float _setted_extend_y) {
        //倍率の変更
        setted_extend_x = _setted_extend_x;
        setted_extend_y = _setted_extend_y;
    }

    public void setPosition(int _original_x, int _original_y, float _original_angle) {
        //基本座標の更新
        original_x = _original_x;
        original_y = _original_y;
        original_angle = _original_angle;
        original_angle_deg = original_angle * 360.0f / (2.0f * (float)Math.PI);
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
        if (!exist) {
            return;
        }
        if (!is_start) {
            return;
        }
        if (is_pause) {
            return;
        }
        if (step == steps && effectData.getNextID(step - 1) == -1) {
            //ループせず、アニメーションの末端なら更新しない
            clear();
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
            do {
                if (effectData.getNextID(step, step) >= steps) {
                    //ループしない場合
                    //Effectを終了する
                    clear();
                    break;
                } else {
                    //指定IDのステップへ遷移する。ループする場合もここで自動的に処理される。
                    toStep(effectData.getNextID(step, step));
                }
            } while(effectData.getTime(step) <= 0);

        } else {
            //そのステップのラストにまだ達していない
            time++;
        }
    }

    public void draw() {
        if (!exist || !isDraw) {
            return;
        }
        //Graphicに描画を依頼
        //graphic.bookingDrawBitmapData(bitmapData.get(imageID), original_x + x, original_y + y, extend_x, extend_y, angle + original_angle_deg, alpha, isUpLeft);
        graphic.bookingDrawBitmapData(bitmapData[imageID], original_x + (int)(r * Math.cos(anime_angle + original_angle)), original_y  + (int)(r * Math.sin(anime_angle + original_angle)), extend_x, extend_y, angle + original_angle_deg, alpha, isUpLeft);
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
        r = (float)Math.sqrt(x*x+y*y);
        extend_x = effectData.getExtendX(_step) * setted_extend_x;
        extend_y = effectData.getExtendY(_step) * setted_extend_y;
        angle = effectData.getAngle(_step);
        alpha = effectData.getAlpha(_step);
        if (alpha == 255) {
            alpha = 254;
        }
        anime_angle = (float)Math.atan2((double)y,(double)x);
        isUpLeft = effectData.isUpLeft(_step);
        imageID = effectData.getImageID(_step);
        soundName = effectData.getSoundName(_step);
    }

    //効果音を鳴らすメソッド
    private void sound() {
        soundAdmin.play(soundName);
    }

    private int calcGraduallyInt(int pre_param, int param, int time, int step) {
        int time_max = effectData.getTime(step);
        return (int)((double)(param - pre_param)*(double)time/(double)time_max) + pre_param;
    }

    private float calcGraduallyFloat(float pre_param, float param, int time, int step) {
        int time_max = effectData.getTime(step);
        return (param - pre_param)*(float)time/(float)time_max + pre_param;
    }

    /*
    public boolean setSoundName(int i, String _soundName) {
        if (i > 0 && i < soundName.size()) {
            soundName.set(i, _soundName);
            return true;
        }
        return false;
    }
    */
    /*
    public boolean addSoundName(String _soundName) {
        return soundName.add(_soundName);
    }
    public boolean setSoundName(List<String> _soundName) {
        soundName = _soundName;
        return true;
    }
    */
    public boolean setBitmapData(int i, BitmapData _bitmapData) {
        if (i > 0 && i < bitmapData.length) {
            bitmapData[i] = _bitmapData;
            return true;
        }
        return false;
    }
    public boolean setBitmapData(BitmapData[] _bitmapData) {
        bitmapData = _bitmapData;
        return true;
    }

    /*
    public List<String> getSoundName() {
        return soundName;
    }
*/
    public String getSoundName() { return soundName; }

    /*public List<BitmapData> getBitmapData() {
        return bitmapData;
    }*/

    public BitmapData[] getBitmapData() {
        return bitmapData;
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
    public boolean isDraw() { return isDraw; }
    public void drawIs(boolean _isDraw) { isDraw = _isDraw; }

}
