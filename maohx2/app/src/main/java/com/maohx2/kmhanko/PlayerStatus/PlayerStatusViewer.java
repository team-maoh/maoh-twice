package com.maohx2.kmhanko.PlayerStatus;


import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Text.BoxPlate;
import com.maohx2.ina.Text.Plate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by user on 2018/04/23.
 */

public class PlayerStatusViewer {
    boolean isExist;

    static final int TEXT_NUM = 5;
    static final int TEXT_X_OFFSET_LEFT1 = 65;
    static final int TEXT_X_OFFSET_LEFT2 = 125;

    static final float TEXT_SIZE_RATE= 0.6f;


    int posX1;
    int posX2;
    int posY1;
    int posY2;

    int sizeX;
    int sizeY;

    Paint paint;
    Paint boxPaint;

    PlayerStatus playerStatus;
    Graphic graphic;
    UserInterface userInterface;

    PlateGroup<BoxPlate> statusPlate;
    BitmapData statusIcon[] = new BitmapData[TEXT_NUM];

    public PlayerStatusViewer(Graphic _graphic, UserInterface _userInterface, PlayerStatus _playerStatus) {
        playerStatus = _playerStatus;
        graphic = _graphic;
        userInterface = _userInterface;
        initPosition();
        initStatusPlate();
    }

    public void initPosition() {
        /*
        posX1 = 0;
        posX2 = 400;
        posY1 = 550;
        posY2 = 900;
        */

        posX1 = 0;
        posX2 = 1600;
        posY1 = 850;
        posY2 = 900;

        sizeX = (posX2 - posX1)/TEXT_NUM;
        sizeY = (posY2 - posY1);

        isExist = true;
        paint = new Paint();
        paint.setTextSize(sizeY * TEXT_SIZE_RATE);
        boxPaint = new Paint();
        boxPaint.setARGB(128,0,0,0);

        statusIcon[0] = graphic.searchBitmap("status_hp");
        statusIcon[1] = graphic.searchBitmap("剣");
        statusIcon[2] = graphic.searchBitmap("盾");
        statusIcon[3] = graphic.searchBitmap("status_luck");
        statusIcon[4] = graphic.searchBitmap("status_money");

    }

    public void initStatusPlate() {
        statusPlate = new PlateGroup<BoxPlate>(
                new BoxPlate[] {
                        new BoxPlate(
                                graphic,
                                userInterface,
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                posX1, posY1, posX2, posY2
                                ) {
                            @Override
                            public void draw() {
                                for (int i = 0; i < TEXT_NUM ; i++) {
                                    String statusName = "";
                                    String statusFigure = "";

                                    switch(i) {
                                        case 0:
                                            statusName = "HP";
                                            statusFigure = String.valueOf(playerStatus.getHP());
                                            paint.setARGB(255,128,255,255);
                                            break;
                                        case 1:
                                            statusName = "Atk";
                                            statusFigure = String.valueOf(playerStatus.getAttack());
                                            paint.setARGB(255,255,128,128);
                                            break;
                                        case 2:
                                            statusName = "Def";
                                            statusFigure = String.valueOf(playerStatus.getDefence());
                                            paint.setARGB(255,128,128,255);
                                            break;
                                        case 3:
                                            statusName = "Luc";
                                            statusFigure = String.valueOf(playerStatus.getLuck());
                                            paint.setARGB(255,255,128,255);
                                            break;
                                        case 4:
                                            statusName = String.valueOf(playerStatus.getMoney() + " Maon");
                                            statusFigure = "";
                                            paint.setARGB(255,255,255,255);
                                            break;
                                    }

                                    //graphic.bookingDrawText(statusName,posX1 + TEXT_X_OFFSET_LEFT1, posY1 + sizeY * i + sizeY, paint);
                                    //graphic.bookingDrawText(statusFigure,posX1 + TEXT_X_OFFSET_LEFT1 + TEXT_X_OFFSET_LEFT2, posY1 + sizeY * i + sizeY, paint);

                                    graphic.bookingDrawBitmapData(statusIcon[i],posX1 + sizeX * i , posY1, 1.5f, 1.5f,0,255, true);
                                    graphic.bookingDrawText(statusName,posX1 + sizeX * i + TEXT_X_OFFSET_LEFT1, posY1 + (int)((sizeY + sizeY * TEXT_SIZE_RATE)/2.0f), paint);
                                    graphic.bookingDrawText(statusFigure,posX1 + sizeX * i + TEXT_X_OFFSET_LEFT2, posY1 + (int)((sizeY + sizeY * TEXT_SIZE_RATE)/2.0f), paint);

                                }
                            }
                        }
                }
        );
    }

    public void setPosition(int x1, int y1, int x2, int y2) {
        posX1 = x1;
        posX2 = x2;
        posY1 = y1;
        posY2 = y2;

        sizeX = (posX2 - posX1)/TEXT_NUM;
        sizeY = (posY2 - posY1);

        paint = new Paint();
        paint.setTextSize(sizeY * TEXT_SIZE_RATE);
    }

    public void update() {
        if (!isExist) {
            return;
        }
        //statusPlate.update();あえて呼ばない
    }

    public void draw() {
        if (!isExist) {
            return;
        }
        graphic.bookingDrawRect(posX1, posY1, posX2, posY2, boxPaint);
        statusPlate.draw();
    }

    public void statusTextBoxUpdate() {
        /*
        textBoxAdmin.bookingDrawText(statusTextBoxID, "Status");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "HP " + playerStatus.getHP());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "Attack " + playerStatus.getAttack());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "Deffence " + playerStatus.getDefence());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "\n");
        textBoxAdmin.bookingDrawText(statusTextBoxID, "Luck " + playerStatus.getLuck());
        textBoxAdmin.bookingDrawText(statusTextBoxID, "MOP");
        textBoxAdmin.updateText(statusTextBoxID);
        */
    }

    public void Existis(boolean f) {
        isExist = f;
    }

    public boolean isExist() {
        return isExist;
    }
}
