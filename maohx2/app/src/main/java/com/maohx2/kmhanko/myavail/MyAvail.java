package com.maohx2.kmhanko.myavail;


import com.maohx2.fuusya.TextBox.TextBoxAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.PlateGroup;

import android.graphics.Color;
import android.graphics.Paint;

import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 2017/10/15.
 */

public class MyAvail {
    static public void errorMes(Exception e) {
        StackTraceElement[] ste = e.getStackTrace();
        throw new Error(e.getClass().getName() + ": "+ e.getMessage() + "\tat ");
    }

    static public int[] shuffle(int num) {
        int raw[] = new int[num];
        Random rnd = new Random();

        for (int i = 0; i < num ; i++) {
            raw[i] = i;
        }

        int temp = 0, t = 0;
        for (int i = num - 1; i > 0 ; i--) {
            temp = rnd.nextInt(i);
            t = raw[i];
            raw[i] = raw[temp];
            raw[temp] = t;
        }

        return raw;

    }

    static public double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }

    static public int maxFromIntArrays(int x[]) {
        int max = x[0];
        int maxID = 0;
        for(int i = 1; i < x.length; i++) {
            if (x[i] > max) {
                max = x[i];
                maxID = i;
            }
        }
        return maxID;
    }

    static public boolean matchStringList(String str, List<String> buf) {
        for (int i = 0; i < buf.size(); i++) {
            if (str.equals(buf.get(i))) {
                return true;
            }
        }
        return false;
    }

    /*
    static public void makeSelection(
            Graphic graphic,
            UserInterface userInterface,
            PlateGroup<BoxTextPlate> plateGroup,
            TextBoxAdmin textBoxAdmin,
            Paint textPaint,
            String leftText,
            String rightText,
            Integer textBoxID
    ) {

        Paint selectPaint = new Paint();
        selectPaint.setTextSize(Constants.SELECT_WINDOW.TEXT_SIZE);
        selectPaint.setColor(Color.WHITE);

        //「解放する」「解放しない」ボタン表示　→　ListBox<Button>の完成待ち
        plateGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{Constants.SELECT_WINDOW.YES_LEFT, Constants.SELECT_WINDOW.YES_UP, Constants.SELECT_WINDOW.YES_RIGHT, Constants.SELECT_WINDOW.YES_BOTTOM},
                                leftText,
                                selectPaint
                        ),
                        new BoxTextPlate(
                                graphic, userInterface, new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{Constants.SELECT_WINDOW.NO_LEFT, Constants.SELECT_WINDOW.NO_UP, Constants.SELECT_WINDOW.NO_RIGHT, Constants.SELECT_WINDOW.NO_BOTTOM},
                                rightText,
                                selectPaint
                        )
                }
        );
        plateGroup.setDrawFlag(false);
        plateGroup.setUpdateFlag(false);

        textBoxID = textBoxAdmin.createTextBox(Constants.SELECT_WINDOW.BACK_LEFT, Constants.SELECT_WINDOW.BACK_UP, Constants.SELECT_WINDOW.BACK_RIGHT, Constants.SELECT_WINDOW.BACK_BOTTOM, Constants.SELECT_WINDOW.BACK_ROW);
        textBoxAdmin.setTextBoxUpdateTextByTouching(textBoxID, false);
        textBoxAdmin.setTextBoxExists(textBoxID, false);
        textPaint = new Paint();
        textPaint.setTextSize(Constants.SELECT_WINDOW.TEXT_SIZE);
        textPaint.setColor(Color.WHITE);

    }
    */
}
