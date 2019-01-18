package com.maohx2.ina;

import android.graphics.Paint;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;

public class ChangeMovie {

    Graphic graphic;
    SoundAdmin soundAdmin;
    Paint paint;
    int length = 0;
    int changeSpeed = 100;
    int RECT_SET_NUM = 9;
    boolean startFlag = false;
    float RECT_HEIGHT = (900.0f/RECT_SET_NUM)/2.0f;
    boolean loadFlag = false;

    public ChangeMovie(Graphic _graphic, SoundAdmin _soundAdmin){
        graphic = _graphic;
        soundAdmin = _soundAdmin;
        paint = new Paint();
    }

    public boolean update(boolean soundFlag){
        return update(soundFlag, true);
    }

    public void init(){ loadFlag = false; }

        public boolean update(boolean soundFlag, boolean inOrOut){
            loadFlag = false;
            if (inOrOut == true) {
            if (!startFlag) {
                if (soundFlag) { soundAdmin.play("encount00"); };
                startFlag = true;
                length = 0;
            }

            if (length > 1600) {
                length = 0;
                startFlag = false;
                loadFlag = true;
                return true;
            }
            length += changeSpeed;
        } else {
            if (!startFlag) {
                if (soundFlag) { soundAdmin.play("encount00"); };
                startFlag = true;
                length = 1600 + changeSpeed;
            }
            if (length < 0) {
                length = 0;
                startFlag = false;
                return true;
            }
            length -= changeSpeed;
        }
        return false;
    }


    public void draw(){draw(false);}
    public void draw(boolean loadFlag){

        if(loadFlag == false) {
            paint.setARGB(255, 50, 50, 50);
            for (int i = 0; i < RECT_SET_NUM; i++) {
                graphic.bookingDrawRect(-changeSpeed, (int) (RECT_HEIGHT * (2 * i + 0)), length, (int) (RECT_HEIGHT * (2 * i + 1) + 2), paint);
                graphic.bookingDrawRect(1600 + changeSpeed - length, (int) (RECT_HEIGHT * (2 * i + 1)), 1600 + changeSpeed, (int) (RECT_HEIGHT * (2 * i + 2) + 2), paint);
            }
        }else{
            graphic.bookingDrawBitmapData(graphic.searchBitmap("grape"),0,0,true);
            /*
            paint.setARGB(255,50,50,50);
            graphic.bookingDrawRect(-2,  -2, 1602,902, paint);
            paint.setARGB(255,255,255,255);
            paint.setTextSize(130);
            graphic.bookingDrawText("Loading...",1000, 850, paint);
            paint.setARGB(255,163,73,164);
            paint.setTextSize(350);
            graphic.bookingDrawText("魔王×２",100, 600, paint);
            */
        }
    }

    public void release() {
        paint = null;
    }

}
