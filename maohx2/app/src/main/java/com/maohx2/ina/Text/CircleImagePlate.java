package com.maohx2.ina.Text;

import android.graphics.Paint;

import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2018/01/14.
 */

public class CircleImagePlate extends CirclePlate {

    ImageContext draw_image_context;
    ImageContext default_image_context;
    ImageContext feedback_image_context;


    public CircleImagePlate(Graphic _graphic, UserInterface _user_interface, TouchWay _judge_way, TouchWay _feedback_way, int[] position, ImageContext _default_image_context, ImageContext _feedback_image_context) {
        super(_graphic, _user_interface, _judge_way, _feedback_way, position[0], position[1], position[2]);

        draw_image_context = _default_image_context;
        default_image_context = _default_image_context;
        feedback_image_context = _feedback_image_context;
    }

    @Override
    public void update() {

        if (user_interface.checkUI(touch_id, judge_way) == true) {
            draw_image_context = feedback_image_context;
            callBackEvent();
        } else if (user_interface.checkUI(touch_id, feedback_way) == true) {
            draw_image_context = feedback_image_context;
        } else {
            draw_image_context = default_image_context;
        }
    }

    public void drawCollisionRange(){
        System.out.println(default_image_context.getBitmapData().getHeight());
        System.out.println(feedback_image_context.getBitmapData().getWidth());
        //graphic.bookingDrawBitmapData(draw_image_context);
        //graphic.bookingDrawCircle(x,y,radius);
    }

    @Override
    public void draw() {
        drawCollisionRange();
        graphic.bookingDrawBitmapData(draw_image_context);
    }
}

