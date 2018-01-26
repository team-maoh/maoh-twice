package com.maohx2.kmhanko.geonode;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by user on 2017/12/17.
 */

//稲垣が作ったclass 名称未定を継承する予定。

public class GeoSlotMapButton {
    String name;
    String image_name;
    int x;
    int y;
    int r = 100;

    static Graphic graphic;

    int touch_id;

    static final int SCALE = 5;
    static final int SCALE_BIG = 10;
    boolean is_scale_big;

    static UserInterface userInterface;

    public GeoSlotMapButton() {
    }

    public static void staticInit(Graphic _graphic, UserInterface _userInterface) {
        graphic = _graphic;
        userInterface = _userInterface;
    }

    public void loadTouchID() {
        if (userInterface == null) {
            throw new Error("☆タカノ : DungeonSelectButton#constarcter : map_user_interface is null");
        }
        r = 100;
        touch_id = userInterface.setCircleTouchUI((double) x, (double) y, (double) r);
    }

    public void draw() {
        float scale = (float)SCALE;
        if (is_scale_big) {
            scale = SCALE_BIG;
        }
        graphic.bookingDrawBitmapName(image_name, x, y, scale, scale, 0, 255, false);
    }

    public void update() {
        if (userInterface.checkUI(touch_id, Constants.Touch.TouchWay.DOWN_MOMENT)) {
            //押下瞬間
            downMomentEvent();
        }

        if (userInterface.checkUI(touch_id, Constants.Touch.TouchWay.MOVE)) {
            //領域の中で、押している間
            moveEvent();
        } else {
            notMoveEvent();
        }

        if (userInterface.checkUI(touch_id, Constants.Touch.TouchWay.UP_MOMENT)) {
            //領域の中で、押上瞬間
            upMomentEvent();
        }
    }

    private void downMomentEvent() {
    }

    private void moveEvent() {
        is_scale_big = true;
    }

    private void notMoveEvent() {
        is_scale_big = false;
    }
    private void upMomentEvent() {
        //TODO:GeoSlotMapへ遷移する
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
