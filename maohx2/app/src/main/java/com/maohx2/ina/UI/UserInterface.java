package com.maohx2.ina.UI;

import android.graphics.PointF;

import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Arrange.PaletteElement;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.GlobalConstants;

import static com.maohx2.ina.Constants.Touch.*;

/**
 * Created by ina on 2017/10/01.
 */

public class UserInterface {

    Graphic graphic;

    static final int LIST_MAX = 300;

    double touch_x;
    double touch_y;
    TouchState touch_state;
    int circle_touch_index_num;
    int box_touch_index_num;
    double circle_center_list_x[] = new double[LIST_MAX];
    double circle_center_list_y[] = new double[LIST_MAX];
    double circle_radius_list[] = new double[LIST_MAX];
    double box_left_list[] =  new double[LIST_MAX];
    double box_top_list[] =  new double[LIST_MAX];
    double box_right_list[] =  new double[LIST_MAX];
    double box_down_list[] =  new double[LIST_MAX];

    int item_id;

    final int DISP_X;
    final int DISP_Y;
    final PointF NORMARIZED_DISP_RATE;
    final PointF DISP_NORMARIZED_RATE;

    final float DENSITY;
    InventryData inventry_data;
    PaletteElement palette_element;
    boolean ui_palette_draw_falag = true;
    boolean up_check = true;
    boolean down_check = false;

    public UserInterface(GlobalConstants global_constants, Graphic _graphic){
        DISP_X = global_constants.DISP_X;
        DISP_Y = global_constants.DISP_Y;
        NORMARIZED_DISP_RATE = global_constants.NORMARIZED_DISP_RATE;
        DISP_NORMARIZED_RATE = global_constants.DISP_NORMARIZED_RATE;
        DENSITY = global_constants.DENSITY;
        ui_palette_draw_falag = false;

        graphic = _graphic;
    }


    public void init() {
        touch_x = 0;
        touch_y = 0;
        touch_state = TouchState.AWAY;
        up_check = true;
        down_check = false;
    }


    public void touchReset() {
        touch_state = TouchState.AWAY;
        up_check = true;
        down_check = false;
    }

    public void updateTouchState(double _touch_x, double _touch_y, TouchState _touch_state) {

        touch_x = _touch_x;
        touch_y = _touch_y;

        //Dowmが続く端末のために強制MOVEに変更
        if (touch_state == TouchState.DOWN) { touch_state = TouchState.MOVE; }

        //DOWN,UP判定を1フレームのみにするためのもの
        if (_touch_state == TouchState.DOWN) {
            down_check = true;
            if (touch_state == TouchState.AWAY) {
                touch_state = TouchState.DOWN;
                up_check = false;
            } else if (touch_state == TouchState.DOWN) {
                touch_state = TouchState.DOWN_MOVE;
                up_check = false;
            }


        } else if (_touch_state == TouchState.MOVE) {
            if(down_check == true) {
                if (touch_state == TouchState.DOWN_MOVE) {
                    touch_state = TouchState.MOVE;
                    up_check = false;
                } else if (touch_state == TouchState.UP) {
                    touch_state = TouchState.MOVE;
                } else if (touch_state == TouchState.AWAY) {
                    touch_state = TouchState.MOVE;
                    up_check = false;
                }
            }else{
                touch_state = TouchState.DOWN;
                down_check = true;
                up_check = false;
            }
        } else if (_touch_state == TouchState.UP) {
            if (((touch_state == TouchState.DOWN) || (touch_state == TouchState.DOWN_MOVE) || (touch_state == TouchState.MOVE)) && up_check == false) {
                touch_state = TouchState.UP;
                down_check = false;
                up_check = true;
            } else {
                touch_state = TouchState.AWAY;
                down_check = false;
                up_check = true;
            }
        }

        //   bSystem.out.println(touch_state);
    }

    public void draw() {

        ui_palette_draw_falag = false;

        if (palette_element != null && touch_state != TouchState.DOWN) {
            if (palette_element.getItemData() != null) {
                ui_palette_draw_falag = true;
                graphic.bookingDrawBitmapData(palette_element.getItemData().getItemImage(), (int) getTouchX(), (int) getTouchY(), 4.0f, 4.0f, 0, 128, false);
            }
        }
    }


    public double getTouchX() {return graphic.transrateDispPositionToNormalizedPositionX((int)touch_x);}

    public double getTouchY() { return graphic.transrateDispPositionToNormalizedPositionY((int)touch_y); }

    public TouchState getTouchState() {
        return touch_state;
    }

    public int setCircleTouchUI(double center_x, double center_y, double ciclre_radius) {
        int index_num = 0;
        for(int i = 0 ; i < LIST_MAX ; i++) {
            if (circle_radius_list[i] == 0) {
                index_num = i;
                break;
            }
            if (i == LIST_MAX - 1) {
                System.out.println("UserInterface: circleCenterListの上限オーバー");
            }
        }
        circle_center_list_x[index_num] = graphic.transrateNormalizedPositionToDispPositionX((int)center_x);
        circle_center_list_y[index_num] = graphic.transrateNormalizedPositionToDispPositionY((int)center_y);
        circle_radius_list[index_num] = DENSITY*ciclre_radius;
        int id = 314000 + index_num;
        //circle_touch_index_num++;
        return id;
    }

    public int setBoxTouchUI(double left, double top, double right, double down) {
        int index_num = 0;
        for(int i = 0 ; i < LIST_MAX ; i++) {
            if (box_left_list[i] * box_top_list[i] * box_right_list[i] * box_down_list[i]  == 0) {
                index_num = i;
                break;
            }
            if (i == LIST_MAX - 1) {
                System.out.println("UserInterface: circleCenterListの上限オーバー");
            }
        }
        box_left_list[index_num] = graphic.transrateNormalizedPositionToDispPositionX((int)left);
        box_top_list[index_num] = graphic.transrateNormalizedPositionToDispPositionY((int)top);
        box_right_list[index_num] = graphic.transrateNormalizedPositionToDispPositionX((int)right);
        box_down_list[index_num] = graphic.transrateNormalizedPositionToDispPositionY((int)down);

        int id = 8010000 + index_num;
        //box_touch_index_num++;
        return id;
    }

    public void setCircleTouchUI(int i, double center_x, double center_y, double ciclre_radius) {
        int index_num = i % 1000;
        circle_center_list_x[index_num] = graphic.transrateNormalizedPositionToDispPositionX((int)center_x);
        circle_center_list_y[index_num] = graphic.transrateNormalizedPositionToDispPositionY((int)center_y);
        circle_radius_list[index_num] = DENSITY*ciclre_radius;
        return;
    }

    public void setBoxTouchUI(int i, double left, double top, double right, double down) {
        int index_num = i % 1000;
        box_left_list[index_num] = graphic.transrateNormalizedPositionToDispPositionX((int)left);
        box_top_list[index_num] = graphic.transrateNormalizedPositionToDispPositionY((int)top);
        box_right_list[index_num] = graphic.transrateNormalizedPositionToDispPositionX((int)right);
        box_down_list[index_num] = graphic.transrateNormalizedPositionToDispPositionY((int)down);
        return;
    }

    public boolean checkUI(int id, TouchWay touch_way) {
        int index_num = id % 1000;

        if ((id - index_num) / 1000 == 314) {
            switch (touch_way) {
                case DOWN_MOMENT:
                    if ((touch_state == TouchState.DOWN) && (Math.pow(circle_center_list_x[index_num] - touch_x, 2) + Math.pow(circle_center_list_y[index_num] - touch_y, 2) <= Math.pow(circle_radius_list[index_num], 2))) {
                        return true;
                    }
                    break;
                case MOVE:
                    if((touch_state == TouchState.DOWN || touch_state == TouchState.DOWN_MOVE || touch_state == TouchState.MOVE) && (Math.pow(circle_center_list_x[index_num] - touch_x, 2) + Math.pow(circle_center_list_y[index_num] - touch_y, 2) <= Math.pow(circle_radius_list[index_num], 2))){
                        return true;
                    }
                    break;
                case UP_MOMENT:
                    if ((touch_state == TouchState.UP) && (Math.pow(circle_center_list_x[index_num] - touch_x, 2) + Math.pow(circle_center_list_y[index_num] - touch_y, 2) <= Math.pow(circle_radius_list[index_num], 2))) {
                        touch_state = TouchState.MOVE;
                        up_check = true;//TODO 要チェック kmhanko
                        return true;
                    }
                    break;
            }
        }

        else if ((id - index_num) / 1000 == 8010) {
            switch (touch_way) {
                case DOWN_MOMENT:
                    if((touch_state == TouchState.DOWN) && (touch_x >= box_left_list[index_num] && touch_x <= box_right_list[index_num]) && (touch_y >= box_top_list[index_num] && touch_y < box_down_list[index_num])) {
                        return true;
                    }
                    break;
                case MOVE:
                    if((touch_state == TouchState.DOWN || touch_state == TouchState.DOWN_MOVE || touch_state == TouchState.MOVE) && (touch_x >= box_left_list[index_num] && touch_x <= box_right_list[index_num]) && (touch_y >= box_top_list[index_num] && touch_y < box_down_list[index_num])) {
                    return true;
                }
                    break;
                case UP_MOMENT:
                    if((touch_state == TouchState.UP) && (touch_x >= box_left_list[index_num] && touch_x <= box_right_list[index_num]) && (touch_y >= box_top_list[index_num] && touch_y < box_down_list[index_num])) {
                    return true;
                }
                    break;
            }
        }
        return false;
    }


    public boolean checkCircleTouchUI(double center_x, double center_y, double circle_radius) {

        if (Math.pow(center_x - touch_x, 2) + Math.pow(center_y - touch_y, 2) <= Math.pow(circle_radius, 2)) {
            return true;
        }

        return false;
    }

    public void resetCircleUI() {

        for (int i = 0; i < LIST_MAX; i++) {
            circle_center_list_x[i] = 0;
            circle_center_list_y[i] = 0;
            circle_radius_list[i] = 0;
        }

        circle_touch_index_num = 0;
    }

    public void resetBoxUI() {

        for (int i = 0; i < LIST_MAX; i++) {
            box_left_list[i] = 0;
            box_top_list[i] =  0;
            box_right_list[i] = 0;
            box_down_list[i] = 0;
        }

        box_touch_index_num = 0;
    }

    public void resetCircleUI(int i) {
        int index_num = i % 1000;
        circle_center_list_x[index_num] = 0;
        circle_center_list_y[index_num] = 0;
        circle_radius_list[index_num] = 0;
    }

    public void resetBoxUI(int i) {
        int index_num = i % 1000;
        box_left_list[index_num] = 0;
        box_top_list[index_num] =  0;
        box_right_list[index_num] = 0;
        box_down_list[index_num] = 0;
    }

    public void resetUI() {

        resetCircleUI();
        resetBoxUI();
    }


    public int getItemID(){

        return item_id;
    }

    public void setItemID(int _item_id) {
        item_id = _item_id;
    }


    public PaletteElement getPaletteElement(){return palette_element;}
    public void setPaletteElement(PaletteElement _palette_element){
        palette_element = _palette_element;
        if(palette_element == null) {
            System.out.println("set null");
        }else{
            System.out.println("set item");
        }
    }

    public InventryData getInventryData(){return inventry_data;}
    public void setInventryData(InventryData _inventry_data){inventry_data = _inventry_data;}

    public boolean isUIPaletteDraw(){
        return ui_palette_draw_falag;
    }

    public void release() {
        System.out.println("takanoRelease : UserInterface");
        circle_center_list_x = null;
        circle_center_list_y = null;
        circle_radius_list = null;
        box_left_list = null;
        box_top_list = null;
        box_right_list = null;
        box_down_list = null;
    }

}
