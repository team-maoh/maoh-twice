package com.maohx2.ina.Text;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.maohx2.ina.Arrange.InventryData;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.UI.UserInterface;

import com.maohx2.ina.Constants.Touch.TouchState;
import com.maohx2.ina.Constants.Touch.TouchWay;
/**
 * Created by ina on 2018/01/12.
 */

public abstract class Plate {

    protected Constants.Touch.TouchWay judge_way;
    protected Constants.Touch.TouchWay feedback_way;
    protected int touch_id;
    protected int alpha;
    protected UserInterface user_interface;
    protected Graphic graphic;
    protected boolean update_flag;
    protected boolean draw_flag;


    Plate(Graphic _graphic, UserInterface _user_interface, Constants.Touch.TouchWay _judge_way, Constants.Touch.TouchWay _feedback_way) {
        graphic = _graphic;
        user_interface = _user_interface;
        //button_paint = new Paint();
        //button_paint.set(_paint);
        judge_way = _judge_way;
        feedback_way = _feedback_way;

        alpha = 255;
        update_flag = true;
        draw_flag = true;
    }

    abstract public void draw();

    public void drawExceptEquip() {
        draw();
    };

    public boolean checkTouchContent() {

        if (user_interface.checkUI(touch_id, judge_way) == true) {
            return true;
        }
        return false;
    }

    public void update() {

        if (draw_flag == false){
            return;
        }
        if (user_interface.checkUI(touch_id, judge_way) == true) {
            alpha = 100;
            callBackEvent();
        } else if (user_interface.checkUI(touch_id, feedback_way) == true) {
            alpha = 255;
        } else {
            alpha = 100;
        }
    }

    public void setPaint(Paint _paint) {
        //button_paint.set(_paint);
    }

    public void callBackEvent() {}

    public ItemData getContentItem(){
        throw new Error("%☆イナガキ：getContentItem()はアイテム関係のボタンでしか使えません");
    }

    public void setContentItem(ItemData _content_item){
        throw new Error("%☆イナガキ：setContentItem()はアイテム関係のボタンでしか使えません");
    }

    public InventryData getInventryData(){
        throw new Error("%☆イナガキ：getContentItem()はアイテム関係のボタンでしか使えません");
    }

    public void setInventryData(InventryData _inventry_data){
        throw new Error("%☆イナガキ：setContentItem()はアイテム関係のボタンでしか使えません");
    }

    public void changeInventryData(){
        throw new Error("%☆イナガキ：setContentItem()はアイテム関係のボタンでしか使えません");
    }

    public boolean getDrawFlag(){
        return draw_flag;
    }

    public void setDrawFlag(boolean _draw_flag){
        draw_flag = _draw_flag;
    }

    public boolean getUpdateFlag(){
        return update_flag;
    }

    public void setUpdateFlag(boolean _update_flag){
        update_flag = _update_flag;
    }


    public void releaseTouchID() {};

    public void release() {
    }

}