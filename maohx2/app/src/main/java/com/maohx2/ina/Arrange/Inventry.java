package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.Text.BoxItemPlate;
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
    PlateGroup<BoxItemPlate> operate_inventry_list_box;
    BoxItemPlate[] inventry_item_plates = new BoxItemPlate[10];
    int[][] position = new int[10][4];
    EquipmentItemDataAdmin equipment_item_data_admin;
    ItemData test_item;


    public Inventry(UserInterface user_interface, Graphic graphic, EquipmentItemDataAdmin _equipment_item_data_admin){
        equipment_item_data_admin = _equipment_item_data_admin;

        test_item = (ItemData) equipment_item_data_admin.getOneDataByName("破壊の剣");

        for(int i = 0; i < 10; i++) {
            position[i][0] = 1000;
            position[i][1] = 100 + (int)(i*test_item.getItemImage().getHeight() * 1.7);
            position[i][2] = 1400;
            position[i][3] = 100 + (int)((i+1)*test_item.getItemImage().getHeight() * 1.7);
        }

        Paint paint = new Paint();
        paint.setARGB(255,0,0,0);
        paint.setTextSize(30);

        for (int i = 0; i < 10; i++) {
            inventry_item_plates[i] = new BoxItemPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], test_item);
        }

        operate_inventry_list_box = new PlateGroup<BoxItemPlate>(inventry_item_plates);
        inventry_item_num = 0;
    }

    public void test_add_item(int i, ItemData add_item){
        operate_inventry_list_box.setContentItem(add_item, 0);
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
