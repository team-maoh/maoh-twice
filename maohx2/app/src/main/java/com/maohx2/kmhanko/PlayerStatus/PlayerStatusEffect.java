package com.maohx2.kmhanko.PlayerStatus;

import com.maohx2.ina.Battle.BattleBaseUnitData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by user on 2018/12/25.
 */

public class PlayerStatusEffect {
    Graphic graphic;
    private int x,y;
    private int time;
    private String text;
    static final private int DRAW_TIME = 30 * 2;

    private boolean exist;
    private Paint paint;

    public PlayerStatusEffect(Graphic _graphic) {
        graphic = _graphic;
        exist = false;
        paint = new Paint();
    }
    public void start(String _text, int _x, int _y) {
        time = 0;
        exist = true;
        x = _x;
        y = _y;
        paint.setColor(Color.WHITE);
        paint.setTextSize(35);
        text = _text;
    }

    public void update() {
        if (exist) {
            time++;
            y = y - 2;

            if (time >= DRAW_TIME) {
                exist = false;
                return;
            }
        }
    }

    public void draw() {
        if (exist) {
            paint.setAlpha((int)(255.0f * ((float)(DRAW_TIME - time)) / (float)DRAW_TIME));
            graphic.bookingDrawText(text, x, y, paint);
        }
    }

    public boolean isExist() {
        return exist;
    }
}
