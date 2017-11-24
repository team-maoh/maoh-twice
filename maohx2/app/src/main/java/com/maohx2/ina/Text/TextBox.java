package com.maohx2.ina.Text;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import static java.security.AccessController.getContext;

/**
 * Created by ina on 2017/10/01.
 */

public class TextBox {

    int box_up_left_x;
    int box_up_left_y;
    int box_down_right_x;
    int box_down_right_y;
    int touch_id;
    Paint paint;

    public void init(int _touch_id) {
        paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        box_up_left_x = 100;
        box_up_left_y = 550;
        box_down_right_x = 800;
        box_down_right_y = 750;

        touch_id = _touch_id;
    }

    public void draw(Canvas canvas){

        Rect rect = new Rect(box_up_left_x, box_up_left_y ,box_down_right_x, box_down_right_y);
        canvas.drawRect(rect, paint);
    }

    public void update(boolean touch_state){

        if(touch_state == false) {
            paint.setColor(Color.argb(100, 0, 0, 0));
        } else {
            paint.setColor(Color.argb(100, 100, 0, 0));
        }

    }

    public int getTouchID(){

        return touch_id;
    }

    public void setTouch_id(int _touch_id) {

        touch_id = _touch_id;
    }

    public int getBoxUpLeftX(){
        return box_up_left_x;
    }

    public void setBoxUpLeftX(int _box_up_left_x) {
        box_up_left_x = _box_up_left_x;
    }

    public int getBoxUpLeftY() {
        return box_up_left_y;
    }

    public void setBoxUpLeftY(int _box_up_left_y) {
        box_up_left_y = _box_up_left_y;
    }

    public int getBoxDownRightX() {
        return box_down_right_x;
    }

    public void setBoxDownRightX(int _box_down_right_x) {
        box_down_right_x = _box_down_right_x;
    }

    public int getBoxDownRightY() {
        return box_down_right_y;
    }

    public void setBoxDownRightY(int _box_down_right_y) {
        box_down_right_y = _box_down_right_y;
    }

    public void resetCursor(){}
    public int getCursor(){return 0;}
    public void setText(){}
    public void resetContent(){}

    public TextBox(){}

}
