package com.maohx2.fuusya.TextBox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.BattleUserInterface;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.hardware.camera2.params.RggbChannelVector.BLUE;
/**
 * Created by ina on 2017/10/01.
 */

public class TextBoxAdmin {

    //テスト用
//    int COLUMN_OF_BOX = 700;//箱の横幅
    int ROW_OF_BOX = 4;//1度に表示するテキストの最大行数

    int frame_count;

    TextBox text_box[] = new TextBox[100];
    int box_count;
    UserInterface user_interface;
    Graphic graphic;
    SoundAdmin sound_admin;

    int debug_int;
    int debug_damage_int;
    int debug_i;

    public TextBoxAdmin(Graphic _graphic, SoundAdmin _sound_admin) {
        graphic = _graphic;
        box_count = 0;

        debug_i = 0;

        debug_int = 0;
        debug_damage_int = 0;

        sound_admin = _sound_admin;
    }

    public void init(UserInterface _user_interface) {
        user_interface = _user_interface;

//        createTextBox(100.0, 550.0, 800.0, 750.0, ROW_OF_BOX, true, true);
//        createTextBox(80.0, 150.0, 400.0, 350.0, 2, true, false);

//
//        frame_count = 0;
//
//        //テスト用ここから
//        Paint text_paint = new Paint();
//        text_paint.setTextSize(40);
//        text_paint.setColor(Color.GREEN);
//
//        Paint blue_paint = new Paint();
//        blue_paint.setTextSize(40);
//        blue_paint.setColor(Color.BLUE);
//
//        text_box[0].inputText("え、死んでまんの。ほら、また。", text_paint);
//        text_box[0].inputText("\n", text_paint);
//        text_box[0].inputText("見たとこ、", text_paint);
//        text_box[0].inputText("毛並みといい、", blue_paint);
//        text_box[0].inputText("\n", text_paint);
//        text_box[0].inputText("体つきといい、", text_paint);
//        text_box[0].inputText("\n", text_paint);
//        text_box[0].inputText("えろうええ馬に見えますが。", blue_paint);
//        text_box[0].inputText("MOP", blue_paint);
//
//        text_box[0].inputText(frame_count + "ダメージを受けた!!", text_paint);
//        text_box[0].inputText("MOP", text_paint);
//
//        text_box[0].inputText("地球", blue_paint);
//        text_box[0].inputText("をアイスピックでつついた", text_paint);
//        text_box[0].inputText("\n", text_paint);
//        text_box[0].inputText("としたら、ちょうど良い感じに", text_paint);
//        text_box[0].inputText("\n", text_paint);
//        text_box[0].inputText("カチ割れるんじゃないかという", text_paint);
//        text_box[0].inputText("\n", text_paint);
//        text_box[0].inputText("くらいに", text_paint);
//        text_box[0].inputText("冷", blue_paint);
//        text_box[0].inputText("えきった朝だった", text_paint);
//        text_box[0].inputText("MOP", blue_paint);
//        bookingDrawText(0, "え、死んでまんの。ほら、また。", text_paint);
//        bookingDrawText(0, "\n", text_paint);
//        bookingDrawText(0, "見たとこ、", text_paint);
//        bookingDrawText(0, "毛並みといい、", blue_paint);
//        bookingDrawText(0, "\n", text_paint);
//        bookingDrawText(0, "体つきといい、", text_paint);
//        bookingDrawText(0, "\n", text_paint);
//        bookingDrawText(0, "えろうええ馬に見えますが。", blue_paint);
//        bookingDrawText(0, "MOP", blue_paint);

    }

    public void update() {

//        if(isLastSentence(0)){
//            System.out.println("LastSentenceDesu");
//        }else{
//            System.out.println("LastSentenceDenai");
//        }

//        int debug_first = text_box[0].first;
//        int debug_last = text_box[0].last;
//        System.out.println("LastSentence TextFirst  " + debug_first);
//        System.out.println("LastSentence TextLast  " + debug_last);
//        System.out.println("isLastSentence  " + text_box[0].isLastSentence());


        //テスト用ここから
        Paint text_paint = new Paint();
        text_paint.setTextSize(40);
        text_paint.setColor(Color.GREEN);

        Paint blue_paint = new Paint();
        blue_paint.setTextSize(40);
        blue_paint.setColor(Color.BLUE);

        frame_count = (frame_count + 1) % 300;

        if (frame_count % 5 == 0) {

            //テスト用の文章
//            text_box[0].inputText("え、死んでまんの。ほら、また。", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("見たとこ、", text_paint);
//            text_box[0].inputText("毛並みといい、", blue_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("体つきといい、", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("えろうええ馬に見えますが。", blue_paint);
//            text_box[0].inputText("MOP", blue_paint);
//
//            text_box[0].inputText(frame_count + "ダメージを受けた!!", text_paint);
//            text_box[0].inputText("MOP", text_paint);
//
//            text_box[0].inputText("地球", blue_paint);
//            text_box[0].inputText("をアイスピックでつついた", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("としたら、ちょうど良い感じに", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("カチ割れるんじゃないかという", text_paint);
//            text_box[0].inputText("\n", text_paint);
//            text_box[0].inputText("くらいに", text_paint);
//            text_box[0].inputText("冷", blue_paint);
//            text_box[0].inputText("えきった朝だった", text_paint);
//            text_box[0].inputText("MOP", blue_paint);
//            bookingDrawText(0, "え、死んでまんの。ほら、また。", text_paint);
//            bookingDrawText(0, "\n", text_paint);
//            bookingDrawText(0, "見たとこ、", text_paint);
//            bookingDrawText(0, "毛並みといい、", blue_paint);
//            bookingDrawText(0, "\n", text_paint);
//            bookingDrawText(0, "体つきといい、", text_paint);
//            bookingDrawText(0, "\n", text_paint);
//            bookingDrawText(0, "えろうええ馬に見えますが。", blue_paint);
//            bookingDrawText(0, "MOP", blue_paint);
//            for (int i = 0; i < 51; i++) {
//                bookingDrawText(0, debug_damage_int + "ダメージを受けた!!", text_paint);
//                bookingDrawText(0, "MOP", text_paint);
//                debug_damage_int++;
//            }
//            bookingDrawText(1, "test", text_paint);
//            bookingDrawText(1, "\n", text_paint);
//            bookingDrawText(1, "desu", text_paint);
//            bookingDrawText(1, "MOP", text_paint);
//
//            bookingDrawText(1, "テスト", text_paint);
//            bookingDrawText(1, "\n", text_paint);
//            bookingDrawText(1, "です", text_paint);
//            bookingDrawText(1, "MOP", text_paint);


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

            //20180429
//            debug_i = text_box[0].inputText("テスト" + debug_i, text_paint);
//            text_box[0].inputText("MOP", text_paint);
//            System.out.println("vaovao" + debug_i);
//            debug_i = text_box[0].inputText("テスト" + debug_i, text_paint);
//            text_box[0].inputText("MOP", text_paint);
//            System.out.println("vaovao" + debug_i);
//            debug_i = text_box[0].inputText("テスト" + debug_i, text_paint);
//            text_box[0].inputText("MOP", text_paint);
//            System.out.println("vaovao" + debug_i);
//            debug_i = text_box[0].inputText("テスト" + debug_i, text_paint);
//            text_box[0].inputText("MOP", text_paint);
//            System.out.println("vaovao" + debug_i);

        }

//        if (frame_count == 100) {
//            text_box[0].updateText();
//        }

//        if (frame_count % 20 == 0) {
//            updateText(0, 3 * debug_int);
//            debug_int = (debug_int + 1) % 50;
//
//            System.out.println(debug_int + "unchi");
//        }

        //テスト用ここまで

        for (int i = 0; i < box_count; i++) {
            text_box[i].update(user_interface.checkUI(text_box[i].getTouchID(), Constants.Touch.TouchWay.MOVE));
        }
    }

    public void draw() {
        for (int i = 0; i < box_count; i++) {
            text_box[i].draw();
        }
    }

    //TextBoxを生成する関数
    //　引数：Boxの上下左右の座標, Boxに表示する文章の行数, 文章をタッチで切り替えるか否か, sentenceにidを割り当てるか否か
    //　戻り値：Boxの通し番号（= box_id）
    public int createTextBox(double _box_left, double _box_top, double _box_right, double _box_down, int _row_of_box, boolean _update_text_by_touching, boolean _assign_sentence_id) {

        int touch_id = user_interface.setBoxTouchUI(_box_left, _box_top, _box_right, _box_down);

        text_box[box_count] = new TextBox(graphic, touch_id, box_count, _update_text_by_touching, _assign_sentence_id, _box_left, _box_top, _box_right, _box_down, _row_of_box, sound_admin);
//        text_box[box_count] = new TextBox(graphic, touch_id, (int) (_box_right - _box_left), _row_of_box, box_count);
        text_box[box_count].init();

        box_count++;

        return (box_count - 1);
    }

    public int createTextBox(double _box_left, double _box_top, double _box_right, double _box_down, int _row_of_box) {
        return createTextBox(_box_left, _box_top, _box_right, _box_down, _row_of_box, true, true);
    }

    //TextBoxの表示・非表示を切り替える
    public void setTextBoxExists(int _box_id, boolean _exists) {
        if (text_box[_box_id] != null) {
            text_box[_box_id].setExists(_exists);
        }
    }

    //Textをすべて初期化して、first = last = 0 とする
    public void resetTextBox(int _box_id) {
        if (text_box[_box_id] != null) {
            text_box[_box_id].reset();
        }
    }

    //Textをタッチで切り替えるかどうか
    public void setTextBoxUpdateTextByTouching(int _box_id, boolean _update_text_by_touching) {
        if (text_box[_box_id] != null) {
            text_box[_box_id].setUpdateTextByTouching(_update_text_by_touching);
        }
    }

    //TextBoxのTextを更新する関数
    public void updateText(int _box_id) {
        text_box[_box_id].updateText();
    }

    //TextBoxのTextを変更する関数(idを指定する)
    public void updateText(int _box_id, int _sentence_id) {
        if (_sentence_id < 0) {
            updateText(_box_id);
        } else {
            text_box[_box_id].changeText(_sentence_id);
        }
    }

    //TextBoxを削除する関数
//    public void deleteTextBox(int _box_id) {
//
//    }

    //TextBoxに文章を入力する関数
    //　引数：Boxの通し番号, 文章, 文章のPaint
    //　　"\n"　……　改行記号
    //　　"MOP" ……　Boxに一度に表示する文章の末尾に付ける記号 (Medium of Paragraphs)
    //
    //    例
    //    bookingDrawText(1,"テスト",text_paint);
    //    bookingDrawText(1,"\n",text_paint);
    //    bookingDrawText(1,"です",text_paint);
    //    bookingDrawText(1,"MOP",text_paint);
    public void bookingDrawText(int _box_id, String _text) {

        Paint default_paint = new Paint();
        default_paint.setTextSize(40);
        default_paint.setColor(Color.GREEN);

        bookingDrawText(_box_id, _text, default_paint);
//        text_box[_box_id].inputText(_text, default_paint);
    }

    //
    public void bookingDrawText(int _box_id, String _text, Paint _paint) {

        text_box[_box_id].inputText(_text, _paint);
    }

    //改行する
    public void bookingBreakingLine(int _box_id) {
        Paint default_paint = new Paint();
        text_box[_box_id].inputText("\n", default_paint);
    }

    //いま表示されている文章が最終文か否か
    //（id指定しないモードで使用可能）
    public boolean isLastSentence(int _box_id) {
        return text_box[_box_id].isLastSentence();
//        return true;
    }


    public void release() {
        System.out.println("takanoRelease : TextBoxAdmin");
        for (int i = 0; i < text_box.length; i++) {
            if (text_box[i] != null) {
                text_box[i].release();
            }
        }
        text_box = null;
    }

}