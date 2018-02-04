package com.maohx2.ina.Arrange;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;

import static com.maohx2.ina.Constants.Math.COS;
import static com.maohx2.ina.Constants.Math.SIN;
import static com.maohx2.ina.Constants.Palette.CIRCLE_COLOR;

/**
 * Created by ina on 2017/11/10.
 */

public class Palette {

    private BattleUserInterface battle_user_interface;
    private PaletteElement[] palette_element = new PaletteElement[8];

    int battle_palette_mode;
    double direction_section_check[] = new double[8];
    Paint paint;
    double direction_check;
    int select_circle_num;
    int selected_circle_num;
    Graphic graphic;

    public Palette(Graphic _graphic, BattleUserInterface _battle_user_interface){
        graphic = _graphic;
        battle_user_interface = _battle_user_interface;
    }

    public void update(){

        //小→展開、小さな円をタッチした

        Constants.Touch.TouchState touch_state = battle_user_interface.getTouchState();
        double touch_x = battle_user_interface.getTouchX();
        double touch_y = battle_user_interface.getTouchY();

        int norm_x = graphic.transrateDispPositionToNormalizedPositionX((int)(touch_x));
        int norm_y = graphic.transrateDispPositionToNormalizedPositionY((int)(touch_y));
        double judge_x = norm_x-1000;
        double judge_y = norm_y-600;

        judge_x *= judge_x;
        judge_y *= judge_y;

        System.out.println(norm_x + "," + norm_y);

        if (touch_state != Constants.Touch.TouchState.AWAY && battle_palette_mode == 0 && judge_x + judge_y < 30 * 30) {
            battle_palette_mode = 1;
            //展開→方向的大、大きな円からはみ出た
        } else if (touch_state != Constants.Touch.TouchState.AWAY && battle_palette_mode == 1 && judge_x + judge_y >= 70 * 70) {
            battle_palette_mode = 2;
            //方向的大→展開、大きな円に戻った
        } else if (touch_state != Constants.Touch.TouchState.AWAY && battle_palette_mode == 2 && judge_x + judge_y < 70 * 70) {
            battle_palette_mode = 1;
        }

        if (touch_state == Constants.Touch.TouchState.UP) {

            if (battle_palette_mode == 2) {
                selected_circle_num = select_circle_num;
            }
            battle_palette_mode = 0;
        }


        direction_check = Math.atan2((600 - norm_y), (norm_x - 1000));

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

    public void draw(){

        if (battle_palette_mode == 0) {
            paint.setColor(CIRCLE_COLOR[selected_circle_num]);
            graphic.bookingDrawCircle(1000, 600, 30, paint);
            //canvas.drawCircle(1000, 600, 30.0f, paint);
        } else if (battle_palette_mode == 1) {
            paint.setColor(CIRCLE_COLOR[selected_circle_num]);
            graphic.bookingDrawCircle(1000, 600, 70, paint);
            //canvas.drawCircle(1000, 600, 70.0f, paint);
            for (int i = 0; i < 8; i++) {
                paint.setColor(CIRCLE_COLOR[i]);
                graphic.bookingDrawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 10, paint);
                //canvas.drawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 10.0f, paint);
            }
        } else if (battle_palette_mode == 2) {
            paint.setColor(CIRCLE_COLOR[selected_circle_num]);
            graphic.bookingDrawCircle(1000, 600, 30, paint);
            //canvas.drawCircle(1000, 600, 70.0f, paint);

            for (int i = 0; i < 8; i++) {
                if (i == select_circle_num) {
                    paint.setColor(CIRCLE_COLOR[i]);
                    graphic.bookingDrawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 30, paint);
                    //canvas.drawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 30.0f, paint);
                } else {
                    paint.setColor(CIRCLE_COLOR[i]);
                    graphic.bookingDrawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 10, paint);
                    //canvas.drawCircle(1000 + (int) (120 * COS[i]), 600 - (int) (120 * SIN[i]), 10.0f, paint);
                }
            }
        }
        //battle_user_interface.drawBattlePalette();
    }
}
