package com.maohx2.ina.Arrange;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.Text.ListBox;
import com.maohx2.ina.UI.UserInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ina on 2017/12/15.
 */

public class Inventry {

    List<InventryItem> inventry_items = new ArrayList<InventryItem>();
    int inventry_item_num;
    ListBox operate_inventry_list_box;

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
}
