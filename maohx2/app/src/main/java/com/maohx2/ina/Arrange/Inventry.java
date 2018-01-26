package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import static com.maohx2.ina.Constants.Touch.TouchWay.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ina on 2017/12/15.
 */

public class Inventry {

    List<InventryItem> inventry_items = new ArrayList<InventryItem>();
    int inventry_item_num;
    PlateGroup<BoxTextPlate> operate_inventry_list_box;
    BoxTextPlate[] inventry_item_plates = new BoxTextPlate[3];
    int[][] position = new int[3][4];
    String[] text = new String[3];

    public Inventry(UserInterface user_interface, Graphic graphic){

        position[0][0] = 800;
        position[0][1] = 100;
        position[0][2] = 900;
        position[0][3] = 200;

        position[1][0] = 200;
        position[1][1] = 750;
        position[1][2] = 400;
        position[1][3] = 850;

        position[2][0] = 1000;
        position[2][1] = 750;
        position[2][2] = 1600;
        position[2][3] = 850;

        text[0] = "祝";
        text[1] = "ご";
        text[2] = "き";

        Paint paint = new Paint();
        paint.setARGB(255,0,0,0);
        paint.setTextSize(30);

        for (int i = 0; i < 3; i++) {
            inventry_item_plates[i] = new BoxTextPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], text[i], paint);
        }

        operate_inventry_list_box = new PlateGroup<BoxTextPlate>(inventry_item_plates);
        inventry_item_num = 0;
    }

    public void test_add_item(int i, ItemData add_item){
        //operate_inventry_list_box.setItemContent(0, add_item);
    }

    public void addInventryItem(ItemData add_item){

        InventryItem new_item = new InventryItem();
        new_item.setInventyItem(add_item);

        inventry_items.add(new_item);
        inventry_item_num++;
    }

    public ItemData removeInventryItem(int remove_inventry_item_index){

        ItemData return_item = inventry_items.get(inventry_item_num).getInventyItem();
        inventry_items.remove(remove_inventry_item_index);
        inventry_item_num--;

        return return_item;
    }

    public void updata(){

        operate_inventry_list_box.update();
    }

    public void draw(){

        operate_inventry_list_box.draw();
    }



    /*
    public Inventry(UserInterface user_interface, Graphic graphic){
        operate_inventry_list_box = new ListBox();
        operate_inventry_list_box.init(user_interface, graphic, Constants.Touch.TouchWay.UP_MOMENT ,7, 850, 100, 1100, 400);
        inventry_item_num = 0;
    }

    public void test_add_item(int i, ItemData add_item){
        operate_inventry_list_box.setItemContent(0, add_item);
    }

    public void addInventryItem(ItemData add_item){

        InventryItem new_item = new InventryItem();
        new_item.setInventyItem(add_item);

        inventry_items.add(new_item);
        inventry_item_num++;
    }

    public ItemData removeInventryItem(int remove_inventry_item_index){

        ItemData return_item = inventry_items.get(inventry_item_num).getInventyItem();
        inventry_items.remove(remove_inventry_item_index);
        inventry_item_num--;

        return return_item;
    }

    public void updata(){

        operate_inventry_list_box.update();
    }

    public void draw(){

        operate_inventry_list_box.draw();
    }

    */
}
