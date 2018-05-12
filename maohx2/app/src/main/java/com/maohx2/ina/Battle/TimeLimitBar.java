package com.maohx2.ina.Battle;

import android.graphics.Paint;

import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2018/05/08.
 */

public class TimeLimitBar {
    int time;
    int timeMax;
    Graphic graphic;
    Paint paintBar;
    boolean exist;

    public TimeLimitBar(Graphic _graphic) {
        graphic = _graphic;
        paintBar = new Paint();
    }

    public void reset(int _timemax) {
        timeMax = _timemax;
        time = timeMax;
        exist = true;
    }

    public void delete() {
        timeMax = 0;
        time = 0;
        exist = false;
    }

    public void setTime(int _time) { time = _time; }
    public void setTimeMax(int _timeMax) { timeMax = _timeMax; }
    public void existIs(boolean _exist) { exist = _exist; }

    public int getTime() { return time; }
    public int getTimeMax() { return timeMax; }
    public boolean isExist() { return exist; }
    public boolean isTimeUp() { return time <= 0; }

    public void update() {
        if (!exist) {
            return;
        }
        if (time > 0) {
            time--;
        }
    }

    public void draw() {
        if (!exist) {
            return;
        }
        int left = 200;
        int up = 20;
        int length = 1200;
        int height = 40;
        float rate = (float)time/(float)timeMax;

        paintBar.setARGB(255,255 - (int)(rate * 255.0),(int)(rate * 255.0),0);

        graphic.bookingDrawRect(left,up, (int)(left+length*rate), height, paintBar);
    }



}
