package com.maohx2.ina.Text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Constants.Touch.TouchState;
import com.maohx2.ina.Constants.Touch.TouchWay;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;

/**
 * Created by ina on 2017/10/01.
 */

public class ListBox {

    //todo:何番目が押されているか、〇番目は押されているか、押されているもの名前、押されているもののアイテム

    int box_up_left_x;
    int box_up_left_y;
    int box_down_right_x;
    int box_down_right_y;
    int content_num;
    TouchWay judge_way;
    int touch_id[];
    int alpha[];
    int content_height;
    int select_content_num;
    int selected_content_num;
    String content[];
    ItemData item_content[];//todo:itemdataクラスにする
    Paint paint;
    UserInterface user_interface;
    Graphic graphic;

    public void init(UserInterface _user_interface, Graphic _graphic, TouchWay _judge_way, int _content_num, int _box_up_left_x, int _box_up_left_y, int _box_down_right_x, int _box_down_right_y) {

        user_interface = _user_interface;
        graphic = _graphic;

        judge_way = _judge_way;
        content_num = _content_num;

        box_up_left_x = _box_up_left_x;
        box_up_left_y = _box_up_left_y;
        box_down_right_x = _box_down_right_x;
        box_down_right_y = _box_down_right_y;

        content_height = (box_down_right_y - box_up_left_y) / content_num;


        paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        paint.setTextSize(content_height/2);
        touch_id = new int[content_num];
        alpha = new int[content_num];
        for(int i = 0; i < content_num; i++) {
            touch_id[i] = user_interface.setBoxTouchUI(box_up_left_x, box_up_left_y + content_height * i, box_down_right_x, box_up_left_y + content_height * (i + 1));
            alpha[i] = 100;
        }


        select_content_num = -1;
        selected_content_num = -1;

        content = new String[content_num];
        for(int i = 0; i < content_num; i++){
            content[i] = "初期化してなくね？";
        }

        item_content = new ItemData[content_num];
    }

    public String getContent(int content_index){return content[content_index];}

    public void setContent(int content_index, String _content){
        content[content_index] = _content;
    }

    public ItemData getItemContent(int content_index){
        return item_content[content_index];
    }

    public void setItemContent(int content_index,ItemData add_item_data){

        item_content[content_index] = add_item_data;
        content[content_index] = add_item_data.getName();
    }



    public void draw(){

        for(int i = 0; i < content_num; i++) {
            paint.setColor(Color.argb(alpha[i], 100 * ((i + 0) % 3), 100 * ((i + 1) % 3), 100 * ((i + 2) % 3)));

            Point up_left = new Point(box_up_left_x, box_up_left_y);
            Point down_right = new Point(box_down_right_x, box_down_right_y);

            //System.out.println("(" + up_left.x +  "," + up_left.y + ")" + "(" + down_right.x  + "," + down_right.y + ")");

            graphic.bookingDrawRect(up_left.x, up_left.y + content_height * i, down_right.x, up_left.y + content_height * (i + 1), paint);
            paint.setColor(Color.argb(255, 0, 0, 0));
            graphic.bookingDrawText(content[i], up_left.x, up_left.y + content_height * (i + 1) - content_height/4, paint);
        }

        if(selected_content_num != -1) {
            paint.setColor(Color.argb(100, 100 * ((selected_content_num + 0) % 3), 100 * ((selected_content_num + 1) % 3), 100 * ((selected_content_num + 2) % 3)));
            graphic.bookingDrawCircle(1000, 700, 50, paint);
        }
    }

    public int checkTouchContent(){
        for(int i = 0; i < content_num; i++){
            if(user_interface.checkUI(touch_id[i], judge_way) == true){
                return i;
            }
        }
        return -1;
    }

    public void update(){
        for(int i = 0; i < content_num; i++) {

            if(user_interface.getTouchState() == TouchState.UP){
                user_interface.setItemID(-1);
            }

            //タッチ判定、falseがタッチされていない
            if (user_interface.checkUI(touch_id[i], TouchWay.MOVE) == true) {
                alpha[i] = 255;
            } else {
                alpha[i] = 100;
            }

            if(user_interface.checkUI(touch_id[i], judge_way) == true){
                selected_content_num = i;
                user_interface.setItemID(i);
            }
        }
    }










    public int getBoxUpLeftX(){
        return box_up_left_x;
    }

    public void setBoxUpLeftX(int _box_up_left_x) {
        box_up_left_x = _box_up_left_x;
    }

    public int getBoxUpLeftY() {
        return box_up_left_y;
    }

    public void setBoxUpLeftY(int _box_up_left_y) {
        box_up_left_y = _box_up_left_y;
    }

    public int getBoxDownRightX() {
        return box_down_right_x;
    }

    public void setBoxDownRightX(int _box_down_right_x) {
        box_down_right_x = _box_down_right_x;
    }

    public int getBoxDownRightY() {return box_down_right_y;}

    public void setBoxDownRightY(int _box_down_right_y) {
        box_down_right_y = _box_down_right_y;
    }

    public ListBox(){}


}
