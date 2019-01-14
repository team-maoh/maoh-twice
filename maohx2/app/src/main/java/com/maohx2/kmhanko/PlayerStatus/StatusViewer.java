package com.maohx2.kmhanko.PlayerStatus;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by user on 2019/01/14.
 */

abstract public class StatusViewer {
    boolean isExist;
    int textNum;
    static final int TEXT_X_OFFSET_LEFT1 = 65;
    static final float TEXT_SIZE_RATE = 0.6f;

    PlayerStatusEffect[] statusEffect = new PlayerStatusEffect[16];

    int posX1;
    int posX2;
    int posY1;
    int posY2;

    int sizeX;
    int sizeY;

    Paint paint;
    Paint boxPaint;

    Graphic graphic;
    UserInterface userInterface;

    PlateGroup<BoxPlate> statusPlate;

    BitmapData statusIcon[];

    public StatusViewer(Graphic _graphic, UserInterface _userInterface, int _textNum) {
        textNum = _textNum;
        graphic = _graphic;
        userInterface = _userInterface;
        for (int i = 0; i < statusEffect.length; i++) {
            statusEffect[i] = new PlayerStatusEffect(graphic);
        }
        statusIcon = new BitmapData[textNum];
        initPosition();
        initStatusPlate();
    }


    abstract public void initPosition();
    abstract public void initStatusPlate();

    public void setPosition(int x1, int y1, int x2, int y2) {
        posX1 = x1;
        posX2 = x2;
        posY1 = y1;
        posY2 = y2;

        sizeX = (posX2 - posX1)/textNum;
        sizeY = (posY2 - posY1);

        paint = new Paint();
        paint.setTextSize(sizeY * TEXT_SIZE_RATE);
    }

    abstract public void update();

    public void makeStatusEffect(int statusID, float parameter, boolean decimalFlag) {
        String text = "";
        Paint tempPaint = new Paint();
        if (decimalFlag) {
            if (parameter > 0) {
                text = "+" + String.format("%.2f", parameter);
                tempPaint.setColor(Color.rgb(255,0,0));
            }
            if (parameter <= 0) {
                text = String.format("%.2f", parameter);
                tempPaint.setColor(Color.rgb(0,0,255));
            }
        } else {
            if (parameter > 0) {
                text = "+" + String.valueOf((int)parameter);
                tempPaint.setColor(Color.rgb(255,128,128));

            }
            if (parameter <= 0) {
                text = String.valueOf((int)parameter);
                tempPaint.setColor(Color.rgb(0,0,255));
            }
        }

        for (int i = 0; i < statusEffect.length; i++) {
            if (!statusEffect[i].isExist()) {
                statusEffect[i].start(
                        text,
                        (int)(posX1 + sizeX * statusID + TEXT_X_OFFSET_LEFT1),
                        (int)(posY1),// + (int)((sizeY + sizeY * TEXT_SIZE_RATE)/2.0f)),
                        tempPaint
                );
                break;
            }
        }
    }

    public void draw() {
        if (!isExist) {
            return;
        }
        graphic.bookingDrawRect(posX1, posY1, posX2, posY2, boxPaint);
        statusPlate.draw();
        for (int i = 0; i < statusEffect.length; i++) {
            statusEffect[i].draw();
        }
    }

    public void Existis(boolean f) {
        isExist = f;
    }

    public boolean isExist() {
        return isExist;
    }

    public void release() {
        System.out.println("takanoRelease : PlayerStatusViewer");
        for (int i = 0; i < statusIcon.length; i++) {
            statusIcon[i] = null;
        }
        statusIcon = null;
        paint = null;
        boxPaint = null;
        statusPlate.release();
        statusPlate = null;
    }
}
