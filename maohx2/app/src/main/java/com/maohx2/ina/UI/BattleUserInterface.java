package com.maohx2.ina.UI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;

import com.maohx2.ina.Constants.Touch.*;
import com.maohx2.ina.GlobalConstants;

import static com.maohx2.ina.Constants.Math.*;
import static com.maohx2.ina.Constants.Palette.*;

/**
 * Created by ina on 2017/09/21.
 */

public class BattleUserInterface extends UserInterface {

    int battle_palette_mode;
    double direction_section_check[] = new double[8];
    Paint paint;
    double direction_check;
    int select_circle_num;
    int selected_circle_num;

    public BattleUserInterface(GlobalConstants global_constants){
        super(global_constants);
    }

    @Override
    public void init() {
        super.init();
        select_circle_num = 0;
        selected_circle_num = 0;

        paint = new Paint();

        direction_check = 0;

        battle_palette_mode = 0;

        for (int i = -3; i <= 4; i++) {
            if (i < 0) {
                direction_section_check[8 + i] = Math.PI * (2 * i - 1) / 8;
            } else {
                direction_section_check[i] = Math.PI * (2 * i - 1) / 8;
            }
        }


        touch_x = 0;
        touch_y = 0;
        touch_state = TouchState.AWAY;

        for (int i = 0; i < 100; i++) {
            circle_center_list_x[i] = 0;
            circle_center_list_y[i] = 0;
            circle_radius_list[i] = 0;
            box_left_list[i] = 0;
            box_top_list[i] = 0;
            box_right_list[i] = 0;
            box_down_list[i] = 0;
        }

        circle_touch_index_num = 0;

    }


    public void update() {

        //小→展開、小さな円をタッチした
        if (touch_state != TouchState.AWAY && battle_palette_mode == 0 && (touch_x - 1000) * (touch_x - 1000) + (touch_y - 600) * (touch_y - 600) < 30 * 30) {
            battle_palette_mode = 1;
            //展開→方向的大、大きな円からはみ出た
        } else if (touch_state != TouchState.AWAY && battle_palette_mode == 1 && (touch_x - 1000) * (touch_x - 1000) + (touch_y - 600) * (touch_y - 600) >= 70 * 70) {
            battle_palette_mode = 2;
            //方向的大→展開、大きな円に戻った
        } else if (touch_state != TouchState.AWAY && battle_palette_mode == 2 && (touch_x - 1000) * (touch_x - 1000) + (touch_y - 600) * (touch_y - 600) < 70 * 70) {
            battle_palette_mode = 1;
        }

        if (touch_state == TouchState.UP) {

            if (battle_palette_mode == 2) {
                selected_circle_num = select_circle_num;
            }

            battle_palette_mode = 0;
        }


        direction_check = Math.atan2((600 - touch_y), (touch_x - 1000));

        for (int i = 0; i < 8; i++) {

            if (i != 4) {
                if (direction_check >= direction_section_check[i] && direction_check < direction_section_check[(i + 1) % 8]) {
                    select_circle_num = i;
                }
            } else {
                if ((direction_check >= direction_section_check[i] && direction_check <= Math.PI) || (direction_check < direction_section_check[(i + 1) % 8] && direction_check >= -Math.PI)) {
                    select_circle_num = i;
                }
            }
        }
    }


    public void drawBattlePalette(Canvas canvas) {

        if (battle_palette_mode == 0) {
            paint.setColor(CIRCLE_COLOR[selected_circle_num]);
            canvas.drawCircle(1000, 600, 30.0f, paint);
        } else if (battle_palette_mode == 1) {
            paint.setColor(CIRCLE_COLOR[selected_circle_num]);
            canvas.drawCircle(1000, 600, 70.0f, paint);
            for (int i = 0; i < 8; i++) {
                paint.setColor(CIRCLE_COLOR[i]);
                canvas.drawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 10.0f, paint);
            }
        } else if (battle_palette_mode == 2) {
            paint.setColor(CIRCLE_COLOR[selected_circle_num]);
            canvas.drawCircle(1000, 600, 70.0f, paint);

            for (int i = 0; i < 8; i++) {

                if (i == select_circle_num) {
                    paint.setColor(CIRCLE_COLOR[i]);
                    canvas.drawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 30.0f, paint);
                } else {
                    paint.setColor(CIRCLE_COLOR[i]);
                    canvas.drawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 10.0f, paint);
                }
            }
        }
    }


}

