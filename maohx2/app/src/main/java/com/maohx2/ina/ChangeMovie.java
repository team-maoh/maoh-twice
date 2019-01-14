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
    float RECT_HEIGHT = (900.0f/RECT_SET_NUM)/2.0f;

    public ChangeMovie(Graphic _graphic, SoundAdmin _soundAdmin){
        graphic = _graphic;
        soundAdmin = _soundAdmin;
        rectPaint = new Paint();
        rectPaint.setARGB(255,30,30,30);
    }

    public boolean update(boolean soundFlag){
        if(length == 0 && soundFlag){soundAdmin.play("encount00");}
        length += changeSpeed;

        if(length > 1600){
            length = 0;
            return true;
        }
        return false;
    }

    public void draw(){
        for(int i = 0; i < RECT_SET_NUM; i++) {
            graphic.bookingDrawRect(0, (int)(RECT_HEIGHT*(2*i+0)), length, (int)(RECT_HEIGHT*(2*i+1)), rectPaint);
            graphic.bookingDrawRect(1600 - length,  (int)(RECT_HEIGHT*(2*i+1)), 1600, (int)(RECT_HEIGHT*(2*i+2)), rectPaint);
        }
    }

}
