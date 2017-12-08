package com.maohx2.kmhanko.dungeonselect;

import com.maohx2.ina.Constants;
import com.maohx2.ina.UI.UserInterface;

import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2017/10/13.
 */

//将来的には大幅に書き換えるか消えるかする

public class LoopSelectButton {
    String name;
    String image_name;
    int x;
    int y;
    int size_x;
    int size_y;

    int touch_id;

    Graphic graphic;

    //グラフィック実装とともに消える予定
    int color_r;
    int color_g;
    int color_b;

    static UserInterface map_user_interface;
    //static GameSystem game_system;

    public LoopSelectButton() {
    }

    public void init(Graphic _graphic) {
        graphic = _graphic;
    }

    public void loadTouchID() {
        if (map_user_interface == null) {
            throw new Error("LoopSelectButton#constarcter : map_user_interface is null");
        }

        touch_id = map_user_interface.setBoxTouchUI((double) x, (double) y, (double)(x + size_x), (double)(y + size_y));
    }

    static public void setMapUserInterface(UserInterface _map_user_interface) {
        map_user_interface = _map_user_interface;
    }
    //static public void setGameSystem(GameSystem _game_system) { game_system = _game_system; }

    public void draw() {
        //消える予定
        /*
        paint.setColor(Color.argb(128, color_r, color_g, color_b));
        canvas.drawRect((float) x, (float) y, (float)(x + size_x), (float)(y + size_y), paint);

        paint.setColor(Color.argb(128, 0, 0, 0));
        paint.setTextSize(60);
        canvas.drawText(name,(float)x, (float)y + 60, paint);
        */
    }

    public void update() {
        if (map_user_interface.checkUI(touch_id, Constants.Touch.TouchWay.DOWN_MOMENT)) {
            //押下瞬間
        }

        if (map_user_interface.checkUI(touch_id, Constants.Touch.TouchWay.MOVE)) {
            //領域の中で、押している間
        } else {
        }

        if (map_user_interface.checkUI(touch_id, Constants.Touch.TouchWay.UP_MOMENT)) {
            //領域の中で、押上瞬間

            //選択時のイベント
            //game_system.setLoopID(name);
        }
    }

    public void setName(String _name) {
        name = _name;
    }
    public void setX(int _x) {
        x = _x;
    }
    public void setY(int _y) {
        y = _y;
    }

    public void setSizeX(int _size_x) {
        size_x = _size_x;
    }
    public void setSizeY(int _size_y) {
        size_y = _size_y;
    }

    //消える予定
    public void setColorR(int _color_r) {
        color_r = _color_r;
    }
    public void setColorG(int _color_g) {
        color_g = _color_g;
    }
    public void setColorB(int _color_b) {
        color_b = _color_b;
    }

}
