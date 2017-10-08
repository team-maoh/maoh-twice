package com.maohx2.ina.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ina on 2017/10/08.
 */

public class GeoSlot {

    Paint paint;

    int position_x;
    int position_y;
    int radius;
    int touch_id;

    int item_id;


    public void init() {
        paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        item_id = -1;
    }


    public void draw(Canvas canvas) {

        if (item_id != -1) {
            paint.setColor(Color.argb(255, 100 * ((item_id + 0) % 3), 100 * ((item_id + 1) % 3), 100 * ((item_id + 2) % 3)));
        }else {
            paint.setColor(Color.argb(100, 0, 0, 0));
        }

        canvas.drawCircle(position_x, position_y, radius, paint);
    }

    public int getItemID() {
        return item_id;
    }

    public void setItemID(int _item_id) {
        item_id = _item_id;
    }

    public void setParam(int _position_x,int _position_y,int _radius){
        position_x = _position_x;
        position_y = _position_y;
        radius = _radius;
    }

    public int getTouchID(){

        return touch_id;
    }

    public void setTouchID(int _touch_id) {
        touch_id = _touch_id;
    }
}
