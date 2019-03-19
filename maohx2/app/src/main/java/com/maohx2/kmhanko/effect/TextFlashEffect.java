package com.maohx2.kmhanko.effect;

import android.graphics.Paint;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2019/03/19.
 */

public class TextFlashEffect {
    float speed = 0;
    float alpha = 255;
    String text = "";
    Paint paint;
    int x = 0;
    int y = 0;
    float textHeight;
    float textWidth;
    Graphic graphic;

    int mode = 1;
    boolean flag = false;

    public TextFlashEffect(Graphic _graphic, String _text, int _size, int _x, int _y, float _speed) {
        this(_graphic);
        this.setSize(_size);
        this.setTextAndReset(_text);
        this.setPosition(_x, _y);
        this.setSpeed(_speed);
    }
    public TextFlashEffect(Graphic _graphic) {
        paint = new Paint();
        paint.setARGB((int)alpha, 255, 255, 255);
        graphic = _graphic;
    }

    public void release() {
        paint = null;
    }

    public void update() {
        if (flag) {
            alpha += (float)mode * speed;
            if (alpha > 255) {
                alpha = 255;
                mode = -1;
            }
            if (alpha < 0) {
                alpha = 0;
                mode = 1;
            }
            paint.setAlpha((int)alpha);
        }
    }

    public void draw() {
        if (flag) {
            graphic.bookingDrawText(text, x - (int)textWidth/2, y + (int)textHeight/2, paint);
        }
    }

    public void setSize(int _size) {
        paint.setTextSize(_size);
    }

    public void setText(String _text) {
        text = _text;
        if (paint != null && text != null) {
            textHeight = paint.getTextSize();
            textWidth = paint.measureText(text);
        }
    }
    public void setTextAndReset(String _text) {
        this.setText(_text);
        alpha = 0;
        mode = 1;
    }
    public void setPosition(int _x, int _y) {
        x = _x;
        y = _y;
    }
    public void setSpeed(float _speed) {
        speed = _speed;
    }

    public void start() {
        flag = true;
    }
    public void stop() {
        flag = false;
    }
}
