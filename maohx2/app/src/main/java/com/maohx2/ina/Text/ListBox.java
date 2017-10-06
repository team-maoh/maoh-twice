package com.maohx2.ina.Text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import com.maohx2.ina.Constants.Touch.TouchState;
import com.maohx2.ina.UI.BattleUserInterface;

/**
 * Created by ina on 2017/10/01.
 */

public class ListBox {


    int box_up_left_x;
    int box_up_left_y;
    int box_down_right_x;
    int box_down_right_y;
    int content_num;
    int touch_id[];
    int alpha[];
    int content_height;
    int select_content_num;
    int selected_content_num;
    Paint paint;
    BattleUserInterface battle_user_interface;

    public void init(BattleUserInterface _battle_user_interface) {
        box_up_left_x = 850;
        box_up_left_y = 100;
        box_down_right_x = 1000;
        box_down_right_y = 400;
        content_num = 6;

        paint = new Paint();
        paint.setColor(Color.argb(100, 0, 0, 0));
        content_height = (box_down_right_y - box_up_left_y) / content_num;
        touch_id = new int[content_num];
        alpha = new int[content_num];
        select_content_num = -1;
        selected_content_num = -1;

        battle_user_interface = _battle_user_interface;

        for(int i = 0; i < content_num; i++) {
            touch_id[i] = battle_user_interface.setBoxTouchUI(box_up_left_x, box_up_left_y + content_height * i, box_down_right_x, box_up_left_y + content_height * (i + 1));
            alpha[i] = 100;
        }
    }








    public void draw(Canvas canvas){

        for(int i = 0; i < content_num; i++) {
            paint.setColor(Color.argb(alpha[i], 100 * ((i + 0) % 3), 100 * ((i + 1) % 3), 100 * ((i + 2) % 3)));
            Rect rect = new Rect(box_up_left_x, box_up_left_y + content_height * i, box_down_right_x, box_up_left_y + content_height * (i + 1));
            canvas.drawRect(rect, paint);
        }

        if(selected_content_num != -1) {
            paint.setColor(Color.argb(100, 100 * ((selected_content_num + 0) % 3), 100 * ((selected_content_num + 1) % 3), 100 * ((selected_content_num + 2) % 3)));
            canvas.drawCircle(200, 200, 50.0f, paint);
        }

/*
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "NotoSansJP-Thin.otf");
        mPaint.setTypeface(typeface);
        canvas.drawText("Hello Canvas!!", 0, 0, mPaint);
*/



    }

    public void update(){
        for(int i = 0; i < content_num; i++) {

            if (battle_user_interface.checkUI(touch_id[i]) == false) {
                alpha[i] = 100;
            } else {
                alpha[i] = 255;
                select_content_num = i;
            }

            if(battle_user_interface.getTouchState() == TouchState.UP){
                selected_content_num = select_content_num;
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

    public int getBoxDownRightY() {
        return box_down_right_y;
    }

    public void setBoxDownRightY(int _box_down_right_y) {
        box_down_right_y = _box_down_right_y;
    }

    public ListBox(){}


}
