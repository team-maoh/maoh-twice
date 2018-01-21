package com.maohx2.ina.Text;

import android.widget.Button;

import com.maohx2.ina.Draw.Graphic;

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

    public void updaate() {
        for (int i = 0; i < plates.length; i++) {
            plates[i].update();
        }
    }

    public int getTouchContenttNum() {

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
}



