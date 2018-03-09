package com.maohx2.ina.Arrange;

import android.graphics.Paint;
import android.provider.Settings;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.Text.BoxInventryPlate;
import com.maohx2.ina.Text.BoxItemPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import static com.maohx2.ina.Constants.Touch.TouchWay.*;
import static com.maohx2.ina.Constants.Inventry.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ina on 2017/12/15.
 */

public class Inventry {

    int inventry_item_num;
    PlateGroup<BoxInventryPlate> operate_inventry_list_box;
    BoxInventryPlate[] inventry_item_plates = new BoxInventryPlate[10];
    int[][] position = new int[10][4];
    InventryData[] inventry_datas = new InventryData[INVENTRY_DATA_MAX];
    UserInterface user_interface;


    public Inventry(UserInterface _user_interface, Graphic graphic){

        user_interface = _user_interface;

        Paint paint = new Paint();

        for(int i = 0; i< INVENTRY_DATA_MAX; i++){
                inventry_datas[i] = new InventryData(null, 0);
        }

        for (int i = 0; i < INVENTRY_CONTENT_MAX; i++) {
            position[i][0] = 1000;
            position[i][1] = 100 + (int)(i*40.8);
            position[i][2] = 1400;
            position[i][3] = 100 + (int)((i+1)*40.8);
            //inventry_item_plates[i] = new BoxItemPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], test_item);
            inventry_item_plates[i] = new BoxInventryPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], inventry_datas[i]);
        }


        paint.setARGB(255,0,0,0);
        paint.setTextSize(30);


        operate_inventry_list_box = new PlateGroup<BoxInventryPlate>(inventry_item_plates);
        inventry_item_num = 0;
    }


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
            if(inventry_datas[i].getItemData() != null) {
                if (inventry_datas[i].getItemData().getName() == _item_data.getName()) {
                    inventry_datas[i].setItemNum(inventry_datas[i].getItemNum() + 1);
                    break;
                }
            }
        }

        if(i==INVENTRY_DATA_MAX){
            i = 0;
            for(i = 0; i < INVENTRY_DATA_MAX; i++) {
                //todo:==を書き直す
                if(inventry_datas[i].getItemNum() == 0){
                    inventry_datas[i].setItemData(_item_data);
                    inventry_datas[i].setItemNum(1);
                    if(i < INVENTRY_CONTENT_MAX) {
                        operate_inventry_list_box.getPlate(i).changeInventryData();
                    }
                    break;
                }
            }
        }
    }

    public void subItemData(ItemData _item_data){
        int i = 0;
        for(i = 0; i < INVENTRY_DATA_MAX; i++) {
            //todo:==を書き直す
            if(inventry_datas[i].getItemData() != null) {
                if (inventry_datas[i].getItemData().getName() == _item_data.getName()) {
                    inventry_datas[i].setItemNum(inventry_datas[i].getItemNum() - 1);
                }
            }
        }
    }

    public PlateGroup<BoxInventryPlate> getOperateInventryListBox() {
        return operate_inventry_list_box;
    }
}
