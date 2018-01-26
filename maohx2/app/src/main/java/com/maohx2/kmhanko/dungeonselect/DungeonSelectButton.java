/*
package com.maohx2.kmhanko.dungeonselect;


import com.maohx2.ina.UI.UserInterface;

import com.maohx2.ina.Constants.Touch.*;
import com.maohx2.ina.Draw.Graphic;

*/

/**
 * Created by user on 2017/10/08.
 */

/*

//稲垣が作ったclass 名称未定を継承する予定。

public class DungeonSelectButton {
    String name;
    String image_name;
    int x;
    int y;
    int r = 100;

    Graphic graphic;

    int touch_id;

    static final int SCALE = 5;
    static final int SCALE_BIG = 10;
    boolean is_scale_big;

    static UserInterface userInterface;
    static DungeonSelectButtonAdmin dungeonSelectButtonAdmin;
    //static MapGameSystem game_system;

    public DungeonSelectButton() {
    }

    public void init(Graphic _graphic) {
        graphic = _graphic;
    }

    public static void staticInit(DungeonSelectButtonAdmin _dungeonSelectButtonAdmin, UserInterface _userInterface) {
        dungeonSelectButtonAdmin = _dungeonSelectButtonAdmin;
        userInterface = _userInterface;
    }

    public void loadTouchID() {
        if (userInterface == null) {
            throw new Error("タカノ : DungeonSelectButton#constarcter : map_user_interface is null¥n");
        }
        r = 100;
        touch_id = userInterface.setCircleTouchUI((double) x, (double) y, (double) r);
    }

    //static public void setGameSystem(GameSystem _game_system) {game_system = _game_system;}

    public void draw() {
        float scale = (float)SCALE;
        if (is_scale_big) {
            scale = SCALE_BIG;
        }
        graphic.bookingDrawBitmapName(image_name, x, y, scale, scale, 0, 255, false);
    }

    public void update() {
        if (userInterface.checkUI(touch_id, TouchWay.DOWN_MOMENT)) {
            //押下瞬間

            //ListBoxを出現させる
            dungeonSelectButtonAdmin.getDungeonSelectManager().makeEnterListBox(this);
        }

        if (userInterface.checkUI(touch_id, TouchWay.MOVE)) {
            //領域の中で、押している間
            is_scale_big = true;
        } else {
            is_scale_big = false;
        }

        if (userInterface.checkUI(touch_id, TouchWay.UP_MOMENT)) {
            //領域の中で、押上瞬間

            //選択時のイベント
            //game_system.setDungeonName(name);
        }
    }

    public void setName(String _name) {
        name = _name;
    }
    public void setImageName(String _image_name) {
        image_name = _image_name;
    }
    public void setX(int _x) {
        x = _x;
    }
    public void setY(int _y) {
        y = _y;
    }

}

*/