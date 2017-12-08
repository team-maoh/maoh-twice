package com.maohx2.fuusya.TextBox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;

import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.hardware.camera2.params.RggbChannelVector.BLUE;

/**
 * Created by ina on 2017/10/01.
 */

public class TextBoxAdmin {

    //テスト用
    int COLUMN_OF_BOX = 680;//箱の横幅
    int ROW_OF_BOX = 4;//1度に表示するテキストの最大行数

    int frame_count;

    TextBox text_box[] = new TextBox[100];
    UserInterface user_interface;
    Graphic graphic;

    public TextBoxAdmin(Graphic _graphic){
        graphic = _graphic;
    }

    public void init(UserInterface _user_interface) {
        user_interface = _user_interface;
        text_box[0] = new TextBox(graphic);

        text_box[0].init(user_interface.setBoxTouchUI(100.0, 550.0, 800.0, 750.0), COLUMN_OF_BOX, ROW_OF_BOX);

        frame_count = 0;
    }

    public void update() {

        //テスト用ここから
        Paint text_paint = new Paint();
        text_paint.setTextSize(40);
        text_paint.setColor(Color.GREEN);

        Paint blue_paint = new Paint();
        blue_paint.setTextSize(40);
        blue_paint.setColor(Color.BLUE);

        frame_count = (frame_count + 1) % 300;

        if (frame_count % 50 == 0) {

            //テスト用の文章
            text_box[0].inputText("え、死んでまんの。ほら、また。", text_paint);
            text_box[0].inputText("\n", text_paint);
            text_box[0].inputText("見たとこ、", text_paint);
            text_box[0].inputText("毛並みといい、", blue_paint);
            text_box[0].inputText("\n", text_paint);
            text_box[0].inputText("体つきといい、", text_paint);
            text_box[0].inputText("\n", text_paint);
            text_box[0].inputText("えろうええ馬に見えますが。", blue_paint);
            text_box[0].inputText("MOP", blue_paint);

            text_box[0].inputText(frame_count + "ダメージを受けた!!", text_paint);
            text_box[0].inputText("MOP", text_paint);

            text_box[0].inputText("地球", blue_paint);
            text_box[0].inputText("をアイスピックでつついた", text_paint);
            text_box[0].inputText("\n", text_paint);
            text_box[0].inputText("としたら、ちょうど良い感じに", text_paint);
            text_box[0].inputText("\n", text_paint);
            text_box[0].inputText("カチ割れるんじゃないかという", text_paint);
            text_box[0].inputText("\n", text_paint);
            text_box[0].inputText("くらいに", text_paint);
            text_box[0].inputText("冷", blue_paint);
            text_box[0].inputText("えきった朝だった", text_paint);
            text_box[0].inputText("MOP", blue_paint);

//            text_box[0].inputText("行数が多すぎる文章の例1", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("行数が多すぎる文章の例2", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("行数が多すぎる文章の例3", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("行数が多すぎる文章の例4", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("行数が多すぎる文章の例5", text_paint);
//            text_box[0].inputText("MOP", text_paint);



        }
        //テスト用ここまで

        text_box[0].update(user_interface.checkUI(text_box[0].getTouchID(), Constants.Touch.TouchWay.MOVE));

    }

    public void draw() {
        text_box[0].draw();
    }

}