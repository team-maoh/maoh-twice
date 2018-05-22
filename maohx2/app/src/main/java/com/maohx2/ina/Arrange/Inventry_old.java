package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.Text.BoxInventryPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;

import java.util.ArrayList;
import java.util.List;

import static com.maohx2.ina.Constants.Inventry.INVENTRY_CONTENT_MAX;
import static com.maohx2.ina.Constants.Inventry.INVENTRY_DATA_MAX;
import static com.maohx2.ina.Constants.Touch.TouchWay.MOVE;
import static com.maohx2.ina.Constants.Touch.TouchWay.UP_MOMENT;


/**
 * Created by ina on 2017/12/15.
 */

public class Inventry_old {

    List<InventryContent> inventry_items = new ArrayList<InventryContent>();
    int inventry_item_num;
    PlateGroup<BoxInventryPlate> operate_inventry_list_box;
    BoxInventryPlate[] inventry_item_plates = new BoxInventryPlate[10];
    int[][] position = new int[10][4];
    EquipmentItemDataAdmin equipment_item_data_admin;
    ItemData test_item;
    InventryData[] inventry_datas = new InventryData[INVENTRY_DATA_MAX];
    InventryContent[] inventry_contnts = new InventryContent[INVENTRY_CONTENT_MAX];
    UserInterface user_interface;


    public Inventry_old(UserInterface _user_interface, Graphic graphic, EquipmentItemDataAdmin _equipment_item_data_admin){
        equipment_item_data_admin = _equipment_item_data_admin;

        test_item = (ItemData) equipment_item_data_admin.getOneDataByName("破壊の剣");
        user_interface = _user_interface;

        Paint paint = new Paint();

        for(int i = 0; i< INVENTRY_DATA_MAX; i++){
            if(i%2 == 0) {
                inventry_datas[i] = new InventryData((ItemData) equipment_item_data_admin.getOneDataByName("破壊の剣"), 90, null);
            }else{
                inventry_datas[i] = new InventryData((ItemData) equipment_item_data_admin.getOneDataByName("水の弓"), 25, null);
            }
        }

        for (int i = 0; i < INVENTRY_CONTENT_MAX; i++) {
            position[i][0] = 1000;
            position[i][1] = 100 + (int)(i*test_item.getItemImage().getHeight() * 1.7);
            position[i][2] = 1400;
            position[i][3] = 100 + (int)((i+1)*test_item.getItemImage().getHeight() * 1.7);
            //inventry_item_plates[i] = new BoxItemPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], test_item);
            inventry_item_plates[i] = new BoxInventryPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], inventry_datas[i]);
        }


        paint.setARGB(255,0,0,0);
        paint.setTextSize(30);


        operate_inventry_list_box = new PlateGroup<BoxInventryPlate>(inventry_item_plates);
        inventry_item_num = 0;

    }
/*
    public void test_add_item(int i, ItemData add_item){
        operate_inventry_list_box.setContentItem(add_item, 0);
    }


    public void addInventryItem(ItemData add_item){

        InventryContent new_item = new InventryContent();
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
*/
    public void updata(){

        System.out.println(inventry_datas[0].getItemNum());

        operate_inventry_list_box.update();

        InventryData touch_inventry_data = operate_inventry_list_box.getInventryData(operate_inventry_list_box.getTouchContentNum());

        if(touch_inventry_data != null){
            user_interface.setInventryData(touch_inventry_data);
        }

    }

    public void draw(){

        operate_inventry_list_box.draw();
    }

    public void addItemData(ItemData _item_data){
        int i = 0;

        for(i = 0; i < INVENTRY_DATA_MAX; i++) {
            //todo:==を書き直す
            if(inventry_datas[i].getItemData().getName() == _item_data.getName()){
                inventry_datas[i].setItemNum(inventry_datas[i].getItemNum()+1);
            }
        }

        if(i==INVENTRY_DATA_MAX-1){
            for(i = 0; i < INVENTRY_DATA_MAX; i++) {
                //todo:==を書き直す
                if(inventry_datas[i].getItemNum() == 0){
                    inventry_datas[i].setItemData(_item_data);
                    inventry_datas[i].setItemNum(1);
                }
            }
        }
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

        InventryContent new_item = new InventryContent();
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
