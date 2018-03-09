package com.maohx2.ina.Text;

import android.widget.Button;

import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by ina on 2018/01/19.
 */

public class PlateGroup<T extends Plate> {

    T[] plates;
    boolean update_flag = true;
    boolean draw_flag = true;

    public PlateGroup(T[] _plates) {

        plates = _plates;
    }

    public void draw() {

        if(draw_flag == true) {
            for (int i = 0; i < plates.length; i++) {
                plates[i].draw();
            }
        }
    }

    public void update() {

        if(update_flag == true) {
            for (int i = 0; i < plates.length; i++) {
                plates[i].update();
            }
        }
    }

    public int getTouchContentNum() {

        for (int i = 0; i < plates.length; i++) {
            if (plates[i].checkTouchContent() == true) {
                return i;
            }
        }

        return -1;
    }

    public Plate getPlate(int plate_num){

        if(plate_num < plates.length) {
            return plates[plate_num];
        }
        return null;
    }

    public boolean isTouchContentNum(int check_content_num) {

        if (plates[check_content_num].checkTouchContent() == true) {
            return true;
        }

        return false;
    }


    public ItemData getContentItem(int content_index) {
        return plates[content_index].getContentItem();
    }

    public void setContentItem(ItemData add_item, int content_index) {
        plates[content_index].setContentItem(add_item);
    }

    public InventryData getInventryData(int content_index) {

        if (content_index >= 0) {
            return plates[content_index].getInventryData();
        }

        return null;
    }

    public void setInventryData(InventryData _inventry_data, int content_index) {
        plates[content_index].setInventryData(_inventry_data);
    }

    public boolean getDrawFlag(){
        return draw_flag;
    }

    public void setDrawFlag(boolean _draw_flag){
        draw_flag = _draw_flag;
    }

    public boolean getUpdateFlag(){
        return update_flag;
    }

    public void setUpdateFlag(boolean _update_flag){
        update_flag = _update_flag;
    }

}