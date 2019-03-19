package com.maohx2.kmhanko.effect;


import android.graphics.Paint;
import com.maohx2.ina.Draw.Graphic;


/**
 * Created by user on 2019/03/19.
 */

public class TextFlashAndArrow extends TextFlashEffect {

    int arrowX = 0;
    int arrowY = 0;
    int degree = 0;

    public TextFlashAndArrow(Graphic _graphic) {
        super(_graphic);
    }

    public TextFlashAndArrow(Graphic _graphic, int _x, int _y) {
        super(_graphic);
        this.setPositionArrow(_x, _y);
    }

    public TextFlashAndArrow(Graphic _graphic, String _text, int _size, int _x, int _y, float _speed, int _arrowX, int _arrowY, int _degree) {
        super(_graphic, _text, _size, _x, _y, _speed);
        this.setPositionArrow(_arrowX, _arrowY);
        this.setDegree(_degree);
    }

    public void setParameter(String _text, int _size, int _x, int _y, float _speed, int _arrowX, int _arrowY, int _degree) {
        this.setText(_text);
        this.setSize(_size);
        this.setPosition(_x, _y);
        this.setSpeed(_speed);
        this.setPositionArrow(_arrowX, _arrowY);
        this.setDegree(_degree);
    }

    public void setPositionArrow(int _x, int _y) {
        arrowX = _x;
        arrowY = _y;
    }
    public void setDegree(int _degree) {
        degree = _degree;
    }

    @Override
    public void update() {
        super.update();
        if (flag) {
            //やることなし
        }
    }

    @Override
    public void draw() {
        super.draw();
        if (flag) {
            graphic.bookingDrawBitmapData(graphic.searchBitmap("arrowMark00"),arrowX,arrowY,2.0f,2.0f, degree, (int)alpha,false);
        }
    }




}
