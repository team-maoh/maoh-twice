package com.maohx2.kmhanko.dungeonselect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.GameSystem;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.UI.MapUserInterface;

import com.maohx2.ina.Constants.Touch.*;

/**
 * Created by user on 2017/10/08.
 */

/*
color関係とrはグラフィックの導入により消滅する予定なので
*/

public class DungeonSelectButton {
    String name;
    String image_name;
    int x;
    int y;
    int r;

    int touch_id;

    //グラフィック実装とともに消える予定
    int color_r;
    int color_g;
    int color_b;

    static UserInterface map_user_interface;
    static GameSystem game_system;

    Paint paint = new Paint();

    public DungeonSelectButton() {
    }

    public void loadTouchID() {
        if (map_user_interface == null) {
            throw new Error("DungeonSelectButton#constarcter : map_user_interface is null");
        }
        r = 100;
        touch_id = map_user_interface.setCircleTouchUI((double) x, (double) y, (double) r);
    }

    static public void setMapUserInterface(UserInterface _map_user_interface) {
        map_user_interface = _map_user_interface;
    }
    static public void setGameSystem(GameSystem _game_system) {
        game_system = _game_system;
    }

    public void draw(Canvas canvas) {
        //消える予定
        paint.setColor(Color.argb(128, color_r, color_g, color_b));
        canvas.drawCircle(x, y, r, paint);

        paint.setColor(Color.argb(128, 0, 0, 0));
        paint.setTextSize(60);
        canvas.drawText(name,(float)x, (float)y, paint);
    }

    public void update() {
        if (map_user_interface.checkUI(touch_id, TouchWay.DOWN_MOMENT)) {
            //押下瞬間
            r = 200;
        }

        if (map_user_interface.checkUI(touch_id, TouchWay.MOVE)) {
            //領域の中で、押している間
        } else {
            r = 100;
        }

        if (map_user_interface.checkUI(touch_id, TouchWay.UP_MOMENT)) {
            //領域の中で、押上瞬間
            r = 100;

            //選択時のイベント
            //game_system.setDungeonName(name);
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
