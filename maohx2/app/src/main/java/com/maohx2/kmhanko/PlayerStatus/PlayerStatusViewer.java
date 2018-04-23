/*
package com.maohx2.kmhanko.PlayerStatus;

import com.maohx2.fuusya.TextBox.TextBoxAdmin;
*/
/**
 * Created by user on 2018/04/23.
 */
/*
public class PlayerStatusViewer {

    int statusTextBoxID;

    PlayerStatus playerStatus;
    TextBoxAdmin textBoxAdmin;

    public PlayerStatusViewer(PlayerStatus _playerStatus, TextBoxAdmin _textBoxAdmin) {
        playerStatus = _playerStatus;
        textBoxAdmin = _textBoxAdmin;

        init();
    }

    public void init() {
        statusTextBoxID = textBoxAdmin.createTextBox(0,600,300,900,5);
        textBoxAdmin.setTextBoxUpdateTextByTouching(statusTextBoxID, false);
        textBoxAdmin.setTextBoxExists(statusTextBoxID, false);
        statusTextBoxUpdate();
    }

    public void statusTextBoxUpdate() {
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
    }

    public void setExist(boolean f) {
        textBoxAdmin.setTextBoxExists(statusTextBoxID, f);
    }
}
*/