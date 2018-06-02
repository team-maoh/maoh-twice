package com.maohx2.kmhanko.PlayerStatus;


import com.maohx2.ina.Constants;
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

    int posX1;
    int posX2;
    int posY1;
    int posY2;

    Paint paint;

    PlayerStatus playerStatus;
    Graphic graphic;
    UserInterface userInterface;

    PlateGroup<BoxPlate> statusPlate;

    public PlayerStatusViewer(Graphic _graphic, UserInterface _userInterface, PlayerStatus _playerStatus) {
        playerStatus = _playerStatus;
        graphic = _graphic;
        userInterface = _userInterface;
        initPosition();
        initStatusPlate();
    }

    public void initPosition() {
        posX1 = 0;
        posX2 = 400;
        posY1 = 500;
        posY2 = 900;
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
                                for (int i = 0; i < 7 ; i++) {
                                    String statusName = "";
                                    String statusFigure = "";
                                    switch(i) {
                                        case 0:
                                            statusName = "HP";
                                            statusFigure = String.valueOf(playerStatus.getNowHP()) + " / " + String.valueOf(playerStatus.getHP());
                                            break;
                                        case 1:
                                            statusName = "Attack";
                                            statusFigure = String.valueOf(playerStatus.getAttack());
                                            break;
                                        case 2:
                                            statusName = "Defence";
                                            statusFigure = String.valueOf(playerStatus.getDefence());
                                            break;
                                        case 3:
                                            statusName = "Luck";
                                            statusFigure = String.valueOf(playerStatus.getLuck());
                                            break;
                                        case 4:
                                            statusName = "Money";
                                            statusFigure = String.valueOf(playerStatus.getMoney() + " Maon");
                                            break;
                                        case 5:
                                            statusName = "Clear";
                                            statusFigure = String.valueOf(playerStatus.getNowClearCount()) + " / " + String.valueOf(playerStatus.getClearCount());
                                            break;
                                        case 6:
                                            statusName = "MaohWin";
                                            statusFigure = String.valueOf(playerStatus.getMaohWinCount());
                                            break;
                                    }

                                    //graphic.bookingDrawText(posX1 + ,);
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
