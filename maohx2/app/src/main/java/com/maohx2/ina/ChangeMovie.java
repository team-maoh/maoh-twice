package com.maohx2.ina;

import android.graphics.Paint;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;

public class ChangeMovie {

    Graphic graphic;
    SoundAdmin soundAdmin;
    Paint rectPaint;
    int length = 0;
    int changeSpeed = 100;
    int RECT_SET_NUM = 9;
    boolean startFlag = false;
    float RECT_HEIGHT = (900.0f/RECT_SET_NUM)/2.0f;

    public ChangeMovie(Graphic _graphic, SoundAdmin _soundAdmin){
        graphic = _graphic;
        soundAdmin = _soundAdmin;
        rectPaint = new Paint();
        rectPaint.setARGB(255,30,30,30);
    }

    public boolean update(boolean soundFlag){
        return update(soundFlag, true);
    }

    public boolean update(boolean soundFlag, boolean inOrOut){
        if (inOrOut == true) {
            if (!startFlag) {
                if (soundFlag) { soundAdmin.play("encount00"); };
                startFlag = true;
                length = 0;
            }
            length += changeSpeed;

            if (length > 1600) {
                length = 0;
                startFlag = false;
                return true;
            }
        } else {
            if (!startFlag) {
                if (soundFlag) { soundAdmin.play("encount00"); };
                startFlag = true;
                length = 1600;
            }
            length -= changeSpeed;

            if (length < 0) {
                length = 0;
                startFlag = false;
                return true;
            }
        }
        return false;
    }


    public void draw(){
        for(int i = 0; i < RECT_SET_NUM; i++) {
            graphic.bookingDrawRect(0, (int)(RECT_HEIGHT*(2*i+0)), length, (int)(RECT_HEIGHT*(2*i+1)), rectPaint);
            graphic.bookingDrawRect(1600 - length,  (int)(RECT_HEIGHT*(2*i+1)), 1600, (int)(RECT_HEIGHT*(2*i+2)), rectPaint);
        }
    }

    public void release() {
        rectPaint = null;
    }

}
