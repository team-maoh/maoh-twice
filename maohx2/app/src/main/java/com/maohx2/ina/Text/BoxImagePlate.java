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

public class BoxImagePlate extends BoxPlate {

    protected ImageContext draw_image_context;
    protected ImageContext default_image_context;
    protected ImageContext feedback_image_context;

    public BoxImagePlate(Graphic _graphic, UserInterface _user_interface, Paint _paint, TouchWay _judge_way, TouchWay _feedback_way, int[] position, ImageContext _default_image_context, ImageContext _feedback_image_context){
        super(_graphic, _user_interface, _judge_way, _feedback_way, position[0], position[1], position[2], position[3]);

        default_image_context = _default_image_context;
        feedback_image_context = _feedback_image_context;

    }


    @Override
    public void update() {
        if (update_flag == false){
            return;
        }
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
        if (draw_flag == false){
            return;
        }
        graphic.bookingDrawRect(left, up, right, down);
    }

    @Override
    public void draw() {
        if (draw_flag == false){
            return;
        }
        graphic.bookingDrawBitmapData(draw_image_context);
    }
}
