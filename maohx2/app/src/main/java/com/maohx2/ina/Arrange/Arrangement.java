package com.maohx2.ina.Arrange;

import android.graphics.Canvas;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2017/11/10.
 */

public class Arrangement {

    ListBox bag_list;
    UserInterface user_interface;
    BagItemAdmin bag_item_admin;
    PaletteAdmin palette_admin;


    public Arrangement(Graphic graphic, UserInterface _user_interface){
        user_interface = _user_interface;
        bag_list = new ListBox();
        bag_list.init(_user_interface, graphic, Constants.Touch.TouchWay.UP_MOMENT ,10, 850, 100, 1100, 400);
        bag_list.setContent(0,"オラ");
        bag_list.setContent(1,"オラオラ");
        bag_list.setContent(2,"オラオラオラ");
        bag_list.setContent(3,"オラオラオラオラ");
        bag_list.setContent(4,"オラオラオラオラオラ");
        bag_list.setContent(5,"オラオラオラオラオラオラ");
        bag_list.setContent(6,"オラオラ");
        bag_list.setContent(7,"オラオラオラ");
        bag_list.setContent(8,"オラオラオラオラ");
        bag_list.setContent(9,"オラオラオラオラオラ");

        bag_item_admin = new BagItemAdmin();
        bag_item_admin.init();
        palette_admin = new PaletteAdmin();

    }


    public void update(){

        bag_list.update();


    }

    public void draw(Canvas canvas){

        bag_list.draw();

    }

}
