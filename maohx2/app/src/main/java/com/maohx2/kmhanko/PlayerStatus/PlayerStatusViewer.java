package com.maohx2.kmhanko.PlayerStatus;


import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2018/04/23.
 */

public class PlayerStatusViewer {

    int statusTextBoxID;

    boolean isExist;

    int posX1;
    int posX2;
    int posY1;
    int posY2;

    PlayerStatus playerStatus;
    Graphic graphic;

    public PlayerStatusViewer(Graphic _graphic, PlayerStatus _playerStatus) {
        playerStatus = _playerStatus;
        graphic = _graphic;
        init();
    }

    public void init() {
        posX1 = 0;
        posX2 = 400;
        posY1 = 500;
        posY2 = 900;
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
    }

    public void draw() {
        if (!isExist) {
            return;
        }
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
