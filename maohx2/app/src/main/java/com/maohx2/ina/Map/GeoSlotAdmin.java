package com.maohx2.ina.Map;

import android.graphics.Canvas;

import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2017/10/08.
 */

public class GeoSlotAdmin {


    GeoSlot geo_slots[] = new GeoSlot[10];
    UserInterface user_interface;

    public void init(UserInterface _user_interface) {
        user_interface = _user_interface;

        for(int i = 0; i < 10; i++) {
            geo_slots[i] = new GeoSlot();
            geo_slots[i].init();
        }

        for(int i = 0; i < 10; i++) {
            geo_slots[i].setParam(30+50*i, 30+50*i, 20);
            geo_slots[i].setTouchID(user_interface.setCircleTouchUI(30+50*i,30+50*i,30));
        }
    }

    public void updata(){

        for(int i = 0; i < 10; i++) {
            if(user_interface.checkUI(geo_slots[i].getTouchID(), Constants.Touch.TouchWay.UP_MOMENT) == true){
                System.out.println(user_interface.getItemID());
                geo_slots[i].setItemID(user_interface.getItemID());
            }
        }
    }


    public void draw(Canvas canvas) {

        for(int i = 0; i < 10; i++) {
            geo_slots[i].draw(canvas);
        }
    }
}
