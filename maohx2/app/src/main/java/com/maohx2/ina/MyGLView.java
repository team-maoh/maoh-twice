package com.maohx2.ina;


/**
 * Created by ina on 2017/09/22.
 */

import android.app.Activity;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.view.Display;
import android.view.MotionEvent;

public class MyGLView extends GLSurfaceView {

    MyRenderer my_renderer;
    double DISP_MAX_X;
    double DISP_MAX_Y;

    public MyGLView(Activity activity) {
        super(activity);
        this.getHolder().addCallback(this);

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        DISP_MAX_X = point.x;
        DISP_MAX_Y = point.y;

        my_renderer = new MyRenderer(activity.getApplicationContext(), (int)DISP_MAX_X, (int)DISP_MAX_Y);

        // レンダラ―をセット
        this.setRenderer(my_renderer);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touch_state;
        double touch_x = 0;
        double touch_y = 0;


//        final float width = this.getWidth();
//        final float height = this.getHeight();
//        final float widthRatio = this.my_render.getWidthRatio();
//        final float heightRatio = this.my_render.getHeightRatio();

//        final float x = event.getX() / width * widthRatio - widthRatio / 2.0f;
//        final float y = event.getY() / height * -heightRatio + heightRatio / 2.0f;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch_state = 0;
            touch_x = event.getX();
            touch_y = event.getY();
            my_renderer.setTouchParam(touch_x,touch_y,touch_state);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            touch_state = 1;
            touch_x = event.getX();
            touch_y = event.getY();
            my_renderer.setTouchParam(touch_x,touch_y,touch_state);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            touch_state = 2;
            touch_x = event.getX();
            touch_y = event.getY();
            my_renderer.setTouchParam(touch_x,touch_y,touch_state);
            return true;
        }
        return false;
    }


}