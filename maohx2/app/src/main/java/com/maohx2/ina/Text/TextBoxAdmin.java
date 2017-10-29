package com.maohx2.ina.Text;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;

import static android.graphics.Color.WHITE;

/**
 * Created by ina on 2017/10/01.
 */

public class TextBoxAdmin {

    //デバッグ用の文章と速度
    String test_text[] = {"「え、死んでまんの。ほら、また。見たとこ、毛並みといい、体つきといい、えろうええ馬に見えますが。","「もし、もし」", "「へぇ」", "「この馬はどないしはったんですか。寝たはるんですか」", "「こんなとこで馬が寝ますかいな。死んでまんにゃがな」", "「え、死んでまんの。ほら、また。見たとこ、毛並みといい、体つきといい、えろうええ馬に見えますが。こらいったいどういう馬なんですか」"};
    double FADING_SPEED = 0;

    int frame_count;
//    String display_mode = "manual";//箱の中の文章の更新方法/manualだとタッチ送りで、autoだと自動送り

    TextBox text_box[] = new TextBox[100];
    UserInterface user_interface;

    public void init(UserInterface _user_interface) {
        user_interface = _user_interface;
        text_box[0] = new TextBox();

        text_box[0].init(user_interface.setBoxTouchUI(100.0, 550.0, 800.0, 750.0));

        /*
        //デバッグ用の文章を格納する
        for (int i = 0; i < 5; i++) {
            text_box[0].inputText(test_text[i], 0);
        }
        */

        frame_count = 0;

    }

    public void update() {

        //テスト用ここから
        Paint text_paint = new Paint();
        text_paint.setTextSize(40);
        text_paint.setColor(WHITE);

        frame_count = (frame_count + 1) % 300;
        if (frame_count % 50 == 0) {
//            text_box[0].inputText("地球をアイスピックでつついたとしたら、ちょうど良い感じにカチ割れるんじゃないかというくらいに冷え切った朝だった。", text_paint);
            for (int i = 0; i < 6; i++) {
                text_box[0].inputText(test_text[i], text_paint);
            }
            text_box[0].inputText(frame_count + "ダメージを受けた!!", text_paint);//manual,autoの別をここで設定する
        }
        //テスト用ここまで

        text_box[0].update(user_interface.checkUI(text_box[0].getTouchID()));

    }

    public void draw(Canvas canvas) {
        text_box[0].draw(canvas);
    }

}