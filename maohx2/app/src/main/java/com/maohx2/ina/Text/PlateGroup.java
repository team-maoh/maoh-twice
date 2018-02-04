package com.maohx2.ina.Text;

import android.widget.Button;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by ina on 2018/01/19.
 */

public class PlateGroup<T extends Plate> {

    T[] plates;

    public PlateGroup(T[] _plates) {

        plates = _plates;
    }

    public void draw() {

        for (int i = 0; i < plates.length; i++) {
            plates[i].draw();
        }
    }

    public void update() {
        for (int i = 0; i < plates.length; i++) {
            plates[i].update();
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


}



