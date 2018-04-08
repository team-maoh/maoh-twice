package com.maohx2.fuusya;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.DungeonGameSystem;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.DungeonUserInterface;

/**
 * Created by Fuusya on 2018/03/25.
 */

public class MapPlateAdmin {

    Graphic graphic;
    DungeonUserInterface dungeon_user_interface;
    boolean is_displaying_menu, is_touching_outside_menu;

    int LEFT_COORD = 1300;
    int RIGHT_COORD = 1550;
    int UP_COORD = 50;
    int BUTTON_HEIGHT = 100;

    PlateGroup<BoxTextPlate> menuGroup;//ダンジョンリタイア、ステータス表示、所持アイテム表示等

    public MapPlateAdmin(Graphic _graphic, DungeonUserInterface _dungeon_user_interface) {
        graphic = _graphic;
        dungeon_user_interface = _dungeon_user_interface;

        Paint text_paint = new Paint();
        text_paint.setTextSize(30f);
        text_paint.setARGB(255,255,255,255);

        is_displaying_menu = false;
        is_touching_outside_menu = false;

        menuGroup = new PlateGroup<BoxTextPlate>(
                new BoxTextPlate[]{
                        new BoxTextPlate(
                                graphic, dungeon_user_interface,new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{LEFT_COORD, UP_COORD, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT},
                                "ステータス",
                                text_paint
                        ),
                        new BoxTextPlate(
                                graphic, dungeon_user_interface,new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{LEFT_COORD, UP_COORD + BUTTON_HEIGHT, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT * 2},
                                "アイテム",
                                text_paint
                        ),
                        new BoxTextPlate(
                                graphic, dungeon_user_interface,new Paint(),
                                Constants.Touch.TouchWay.UP_MOMENT,
                                Constants.Touch.TouchWay.MOVE,
                                new int[]{LEFT_COORD, UP_COORD + BUTTON_HEIGHT * 2, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT * 3},
                                "リタイア",
                                text_paint
                        )
                }
        );

    }

    public void update() {

        menuGroup.update();

        is_touching_outside_menu = false;

        Constants.Touch.TouchState touch_state = dungeon_user_interface.getTouchState();

        if (touch_state != Constants.Touch.TouchState.AWAY) {

            double touch_n_x = dungeon_user_interface.getTouchX();
            double touch_n_y = dungeon_user_interface.getTouchY();

            //Menuの領域内に指がある
            if(LEFT_COORD <= touch_n_x && touch_n_x <= RIGHT_COORD && UP_COORD <= touch_n_y && touch_n_y <= UP_COORD + BUTTON_HEIGHT * 3) {

                if(is_displaying_menu == true){
                    int content = menuGroup.getTouchContentNum();
                    switch (content) {
                        case 0://ステータス
                            System.out.println("tatami ステータス");
                            break;
                        case 1://アイテム
                            System.out.println("tatami アイテム");
                            break;
                        case 2://（ダンジョンを）リタイア
                            System.out.println("tatami リタイア");
                            break;
                        default:
//                System.out.println("デフォルト");
                            break;
                    }
                }

            } else {//Menuの範囲外に指がある
                is_touching_outside_menu = true;
            }

        }

    }


    public void draw() {

        if(is_displaying_menu == true) {
            menuGroup.draw();
        }

    }

    public void setIsDisplayingMenu(boolean _is_displaying_menu) {
        is_displaying_menu = _is_displaying_menu;
    }

    public void setIsTouchingOutsideMenu(boolean _is_touching_outside_menu) {
        is_touching_outside_menu = _is_touching_outside_menu;
    }

    public boolean getIsDisplayingMenu() {
        return is_displaying_menu;
    }

    public boolean getIsTouchingOutsideMenu() {
        return is_touching_outside_menu;
    }

}
