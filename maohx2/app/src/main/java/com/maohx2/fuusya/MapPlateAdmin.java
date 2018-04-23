package com.maohx2.fuusya;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Arrange.Inventry;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.DungeonGameSystem;
import com.maohx2.ina.Text.BoxPlate;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.DungeonUserInterface;

/**
 * Created by Fuusya on 2018/03/25.
 */

public class MapPlateAdmin {

    Graphic graphic;
    DungeonUserInterface dungeon_user_interface;
    Inventry inventry;
    boolean is_displaying_list;

    int displaying_content;
    // -1 : 何も表示していない
    // 0  : ステータス、アイテム、リタイア、というmenu
    // 1  : ステータス表示
    // 2  : アイテム（inventory）
    // 3  : 本当にリタイアしますか？[はい][いいえ]

    int LEFT_COORD = 1300;
    int RIGHT_COORD = 1550;
    int UP_COORD = 50;
    int BUTTON_HEIGHT = 100;
    //
    int HP_BG_TOP = 5;
    int HP_BG_RIGHT = 150;
    int HP_BG_LEFT = 1570;
    int HP_BG_BOTTOM = 35;
    //
    int HP_RIGHT = 160;
    int HP_LEFT = 1560;
    int HP_HEIGHT = 15;
    //
    double hp_ratio;//0.00 ~ 1.00

    PlateGroup<BoxTextPlate> menuGroup;//ダンジョンリタイア、ステータス表示、所持アイテム表示等
    PlateGroup<BoxPlate> hitpoint;

    public MapPlateAdmin(Graphic _graphic, DungeonUserInterface _dungeon_user_interface) {
        graphic = _graphic;
        dungeon_user_interface = _dungeon_user_interface;
        inventry = new Inventry();
        inventry.init(dungeon_user_interface, graphic, 1000, 100, 1400, 800, 10);

        Paint text_paint = new Paint();
        text_paint.setTextSize(30f);
        text_paint.setARGB(255, 255, 255, 255);

        is_displaying_list = false;

        displaying_content = -1;

        hp_ratio = 1;

        menuGroup = new PlateGroup<BoxTextPlate>(new BoxTextPlate[]{new BoxTextPlate(graphic, dungeon_user_interface, new Paint(), Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{LEFT_COORD, UP_COORD, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT}, "ステータス", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, new Paint(), Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{LEFT_COORD, UP_COORD + BUTTON_HEIGHT, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT * 2}, "アイテム", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, new Paint(), Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{LEFT_COORD, UP_COORD + BUTTON_HEIGHT * 2, RIGHT_COORD, UP_COORD + BUTTON_HEIGHT * 3}, "リタイア", text_paint)});

        Paint hp_bg_paint = new Paint();
//        hp_bg_paint.setARGB(255,0,0,255);
        hp_bg_paint.setColor(Color.BLUE);
        Paint hp_paint = new Paint();
//        hp_paint.setARGB(255,0,255,0);
        hp_paint.setColor(Color.GREEN);
        Paint hp_hole_paint = new Paint();
//        hp_hole_paint.setARGB(255,255,0,0);
        hp_hole_paint.setColor(Color.RED);
        int hp_top = HP_BG_TOP + ((HP_BG_BOTTOM - HP_BG_TOP) - HP_HEIGHT) / 2;
        int hp_cutting_x = (int) ((HP_RIGHT - HP_LEFT) * hp_ratio) + HP_LEFT;

        hitpoint = new PlateGroup<BoxPlate>(new BoxTextPlate[]{new BoxTextPlate(graphic, dungeon_user_interface, hp_bg_paint, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{HP_BG_LEFT, HP_BG_TOP, HP_BG_RIGHT, hp_top}, " ", text_paint),
                //↓　HPバー
                new BoxTextPlate(graphic, dungeon_user_interface, hp_paint, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{HP_LEFT, hp_top, hp_cutting_x, hp_top + HP_HEIGHT}, " ", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, hp_hole_paint, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{hp_cutting_x, hp_top, HP_RIGHT, hp_top + HP_HEIGHT}, " ", text_paint),
                //↑　HPバー
                new BoxTextPlate(graphic, dungeon_user_interface, hp_bg_paint, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{HP_BG_LEFT, hp_top + HP_HEIGHT, HP_BG_RIGHT, HP_BG_BOTTOM}, " ", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, hp_bg_paint, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{HP_BG_LEFT, hp_top, HP_LEFT, hp_top + HP_HEIGHT}, " ", text_paint), new BoxTextPlate(graphic, dungeon_user_interface, hp_bg_paint, Constants.Touch.TouchWay.UP_MOMENT, Constants.Touch.TouchWay.MOVE, new int[]{HP_BG_RIGHT, hp_top, HP_RIGHT, hp_top + HP_HEIGHT}, " ", text_paint)});
    }

    public void update() {

        menuGroup.update();
        hitpoint.update();
        inventry.updata();

        System.out.println("displaying_content_desu =   " + displaying_content);

        Constants.Touch.TouchState touch_state = dungeon_user_interface.getTouchState();

        if (touch_state != Constants.Touch.TouchState.AWAY) {

            double touch_n_x = dungeon_user_interface.getTouchX();
            double touch_n_y = dungeon_user_interface.getTouchY();

            //Menuが表示されている && Menuの領域内に指がある
            if (isTouchingList(touch_n_x, touch_n_y) == true) {

                switch (displaying_content) {
                    case 0:

                        int content = menuGroup.getTouchContentNum();

                        switch (content) {
                            case 0://ステータス
                                System.out.println("tatami ステータス");
                                displaying_content = 1;
                                is_displaying_list = false;
                                break;
                            case 1://アイテム
                                System.out.println("tatami アイテム");
                                displaying_content = 2;
                                is_displaying_list = false;
                                break;
                            case 2://（ダンジョンを）リタイア
                                System.out.println("tatami リタイア");
                                displaying_content = 3;
                                is_displaying_list = false;
                                break;
                            default:
//                            System.out.println("tatami content = "+content);
                                break;

                        }

                    default:
                        break;
                }
            }

        }

    }

    public void draw() {

        switch (displaying_content) {
            case 0:
                menuGroup.draw();
                break;
            case 1:
                break;
            case 2:
                inventry.draw();
                break;
            case 3:
                break;
            default:
                break;
        }

//        if(displaying_content == 0) {
//            menuGroup.draw();
//        }
        hitpoint.draw();
        hitpoint.draw();
        hitpoint.draw();

    }

    public void setIsDisplayingList(boolean _is_displaying_list) {
        is_displaying_list = _is_displaying_list;
    }

    public void setDisplayingContent(int _displaying_content) {
        displaying_content = _displaying_content;
    }

    public int getDisplayingContent() {
        return displaying_content;
    }

    public boolean getIsDisplayingList() {
        return is_displaying_list;
    }

    public boolean isTouchingList(double touch_n_x, double touch_n_y) {

        switch (displaying_content) {
            case -1://Listが画面に出ていない
                return false;
            case 0:
                return LEFT_COORD <= touch_n_x && touch_n_x <= RIGHT_COORD && UP_COORD <= touch_n_y && touch_n_y <= UP_COORD + BUTTON_HEIGHT * 3;
            case 2:
                return 1000 <= touch_n_x && touch_n_x <= 1400 && 100 <= touch_n_y && touch_n_y <= 508;
            default:
                return false;
        }

    }

    //by kmhanko
    public Inventry getInventry() {
        return inventry;
    }

}
