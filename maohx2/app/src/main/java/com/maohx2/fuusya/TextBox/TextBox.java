package com.maohx2.fuusya.TextBox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.maohx2.ina.Draw.Graphic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.WHITE;
import static java.security.AccessController.getContext;

/**
 * Created by ina on 2017/10/01.
 */

public class TextBox {

    int box_left;//箱の左上の頂点のx座標
    int box_top;//　　　　　　　　y座標
    int box_right;//箱の右下の頂点のx座標
    int box_down;//　　　　　　　　y座標
    int touch_id;
    int box_id;//箱の通し番号（外からテキストを投げ込むとき、この番号で箱を指定する）

    Paint box_paint;

    int column_of_box;//箱の横幅
    int row_of_box;//箱の縦幅

    int MAX_QUEUE_TEXT = 500;//下のキューの容量
    Text queue[] = new Text[MAX_QUEUE_TEXT];//もらった文章を格納するキュー
    int last, first;//text_queueの末尾と先頭（末尾にテキストを追加する/先頭のテキストを表示する）
    //0■
    //1■
    //2□　←last（次にinputしたtextを格納する位置）
    //3□
    //4□
    //5■　←first（次に表示するtextの位置）
    //6■
    //7■

    //firstの位置を記憶する
    //外の人がidを指定 → first = firsts[_sentence_id]
    //
    //first[sentence_count]という名前の配列にする？
    //firstの個数 = sentence_count = (MOPをsetするたびに１つ増える)
    int sentence_firsts[] = new int[MAX_QUEUE_TEXT];//first = sentence_firsts[sentence_id] : firstの位置を記憶する
    int sentence_count;//次に入ってきたsentenceに割り当てるid
//    int pre_first;//次にMOPを受け取ったときにsentence_firsts[]に格納するべきfirst
    //現在tmp_sentence[]に格納されつつあるsentenceの開始位置をこの変数で憶えておく

    //受け取った文はこの配列に一時的に格納する
    //MOPを受け取った時点で文queueの空きを確認して、十分な空きがあるなら格納する / ないなら破棄する
    //（受け取ったそばから文をqueueに格納して、十分な空きが無いことが途中で判明したら、
    //　それまでに格納したqueueを初期化してlastの位置をもとに戻して…という面倒な作業が必要になる）
    int MAX_TMP_TEXT = 20;
    String tmp_sentence[] = new String[MAX_TMP_TEXT];
    Paint tmp_paint[] = new Paint[MAX_TMP_TEXT];
    int tmp_num_of_lines[] = new int[MAX_TMP_TEXT];
    int tmp_begin_column[] = new int[MAX_TMP_TEXT];
    int tmp_text_count;//受け取った文の個数
    //
    int MAX_NUM_OF_LINES = 10;
    boolean is_too_long[] = new boolean[MAX_NUM_OF_LINES];// * 行目の文が、箱の横幅に対して長過ぎる（エラー文を出すためだけの配列）

    int num_of_lines;//格納している文の行数を保持
    int begin_column;//　　　 〃 　　　開始位置を保持

    boolean has_updated_text;//一度のタッチで文章が何度も切り替わらないようにするための変数
    boolean update_text_by_touching;

    Graphic graphic;

    boolean exists;//自分自身が画面に表示されているかどうか
    boolean sentence_exists;//textが画面に表示されているかどうか

    boolean assign_sentence_id;//sentenceをupdateした後で「今まで表示していたsentence」をqueueから抹消するかどうか
    //trueだとqueueが代謝するので次から次へと新しいsentenceを受け取れる（キャラの台詞などに向いている）
    //falseだと同じsentenceを二度も三度も表示することができる（アイテムの詳細表示などに向いている）

    public TextBox(Graphic _graphic, int _touch_id, int _box_id, boolean _update_text_by_touching, boolean _assign_sentence_id, double _box_left, double _box_top, double _box_right, double _box_down, int _row_of_box) {
        graphic = _graphic;
        box_id = _box_id;
        update_text_by_touching = _update_text_by_touching;
        assign_sentence_id = _assign_sentence_id;

        box_left = (int) _box_left;
        box_top = (int) _box_top;
        box_right = (int) _box_right;
        box_down = (int) _box_down;

        column_of_box = box_right - box_left;
        row_of_box = _row_of_box;

        box_paint = new Paint();
        box_paint.setColor(Color.argb(100, 0, 0, 0));


        touch_id = _touch_id;
        has_updated_text = false;

        first = 0;
        last = 0;

        for (int i = 0; i < MAX_QUEUE_TEXT; i++) {
            queue[i] = new Text();
            queue[i].init(row_of_box, column_of_box);
        }

        for (int i = 0; i < MAX_TMP_TEXT; i++) {
            tmp_num_of_lines[i] = 0;
            tmp_begin_column[i] = 0;
        }
        for (int i = 0; i < MAX_NUM_OF_LINES; i++) {
            is_too_long[i] = false;
        }

        tmp_text_count = 0;
        num_of_lines = 0;
        begin_column = 0;

        exists = true;
        sentence_exists = true;

        sentence_count = 0;
        for (int i = 0; i < MAX_QUEUE_TEXT; i++) {
            sentence_firsts[i] = 0;
        }

    }

    public void init() {
    }

    public void update(boolean touch_state) {

        if (touch_state == false) {//タッチしていない
            box_paint.setColor(Color.argb(100, 0, 0, 0));

            if (has_updated_text == false) {

                if (update_text_by_touching == true) {
                    updateText();
                }
//                if (first != last) {//文章キューが空でなかったら、
//
//                    int tmp_first = first;
//
//                    //次に表示する文章の冒頭まで first をずらす
//                    while (!(queue[first].isMOP() == true && queue[(first - 1 + MAX_QUEUE_TEXT) % MAX_QUEUE_TEXT].isMOP() == false)) {
//                        first = (first + 1) % MAX_QUEUE_TEXT;//firstを１個進める
//                    }
//                    while (!(queue[first].isMOP() == false && queue[(first - 1 + MAX_QUEUE_TEXT) % MAX_QUEUE_TEXT].isMOP() == true)) {
//                        first = (first + 1) % MAX_QUEUE_TEXT;//firstを１個進める
//                    }
//
//                    for (; tmp_first != first; tmp_first = (tmp_first + 1) % MAX_QUEUE_TEXT) {
//                        queue[tmp_first].initSentence();
//                    }
//
//                }
                has_updated_text = true;

            }
        } else {//タッチしている
            box_paint.setColor(Color.argb(100, 100, 0, 0));
            has_updated_text = false;

        }
    }

    public void draw() {

        //箱が存在する場合、
        if (exists == true) {

            //上のupdateで設定したpaintを使って箱を描画
//        Rect rect = new Rect(box_left, box_top, box_right, box_down);
            graphic.bookingDrawRect(box_left, box_top, box_right, box_down, box_paint);

            if (sentence_exists == true) {
                if (queue[first].getSentence().equals("null")) {
                    System.out.println("◆文queueが空です");

                } else {
                    displayText();//文章を描画する
                }
            }
        }
    }

    //外から文章を受け取り、文章キューの末尾に追加する
    public int inputText(String _sentence, Paint _paint) {

        //この関数の返り値（貰ったsentenceのid）をここで定める
        int return_sentence_id;
        if (assign_sentence_id == true) {
            return_sentence_id = sentence_count;
        } else {
            return_sentence_id = -1;
        }

        if ((last + tmp_text_count + 1) % MAX_QUEUE_TEXT == first) {//文queueが満杯だったら、

            tmp_text_count = 0;
            num_of_lines = 0;
            begin_column = 0;
            System.out.println("◆文queueが満杯なので受け取ったtextを破棄した from TextBox.java◆");

        } else if ((tmp_text_count + 1) >= MAX_TMP_TEXT) {//tmp*[]が満杯だったら、

            tmp_text_count = 0;
            num_of_lines = 0;
            begin_column = 0;
            System.out.println("◆tmp_*[]が満杯なので受け取ったtextを破棄した from TextBox.java◆");

        } else {

            if (_sentence.equals("MOP")) {//文章と文章の間の記号だったら、

                //最終文が{箱の横幅に対して}長過ぎたら is_too_long[num_of_lines] = true とする（judgeSentence()のための準備）
                flagSentenceLength();

                num_of_lines++;

                //箱の横幅に対して長過ぎる文がある or 行数が多すぎる場合、エラーを吐いてアプリを落とす
                judgeSentence();

                if (assign_sentence_id == true) {
                    sentence_firsts[sentence_count] = last;
                }

                for (int i = 0; i < tmp_text_count; i++) {//tmp_*[]に保持しておいた文、paint、開始位置、行数をqueueに格納する
                    queue[last].setSentence(tmp_sentence[i]);
                    queue[last].setPaint(tmp_paint[i]);
                    queue[last].setNumOfLines(tmp_num_of_lines[i]);
                    queue[last].setBeginColumn(tmp_begin_column[i]);
                    queue[last].setMOP(false);

                    if (assign_sentence_id == true) {
                        queue[last].setSentenceId(sentence_count);
                    }

                    last = (last + 1) % MAX_QUEUE_TEXT;
                }

                queue[last].setMOP(true);
                last = (last + 1) % MAX_QUEUE_TEXT;

                tmp_text_count = 0;
                num_of_lines = 0;
                begin_column = 0;

                if (assign_sentence_id == true) {
                    sentence_count++;
//                    sentence_count = (sentence_count + 1) % MAX_QUEUE_TEXT;
                    if (sentence_count > MAX_QUEUE_TEXT) {
//                        throw new Error("%☆◆フジワラ:sentence_countがデカくなりすぎた");//アプリを落とす
                        System.out.println("%☆◆フジワラ:sentence_id指定モードのTextBoxが受け取れるsentenceの総数には限度がある");
                        throw new Error("%☆◆フジワラ:その限度を超える量のsentenceが入力されそうになった");//アプリを落とす
                    }
                }

            } else if (_sentence.equals("\n")) {//改行記号だったら、

                //num_of_lines行目の文章が{箱の横幅に対して}長過ぎたら is_too_long[num_of_lines]=true とする
                //（judgeSentence()のための準備）
                flagSentenceLength();

                num_of_lines++;
                begin_column = 0;

            } else {//文だったら、
                tmp_sentence[tmp_text_count] = _sentence.substring(0);
                tmp_paint[tmp_text_count] = _paint;
                tmp_num_of_lines[tmp_text_count] = num_of_lines;
                tmp_begin_column[tmp_text_count] = begin_column;

                begin_column += (int) _paint.measureText(_sentence);
                tmp_text_count++;

            }
        }

        return return_sentence_id;
    }

    private void flagSentenceLength() {
        if (begin_column >= column_of_box) {
            is_too_long[num_of_lines] = true;
        }
    }

    //箱の横幅に対して長過ぎる文がある or 行数が多すぎる場合、エラーを吐いてアプリを落とす
    private void judgeSentence() {

        boolean will_shutdown_app = false;//アプリを強制終了するか否か

        //行数が多すぎる
        if (num_of_lines > row_of_box) {
            System.out.println("%☆◆フジワラ:TextBoxの縦幅に対して文章の行数が多すぎる");
            for (int i = 0; i < tmp_text_count; i++) {
                System.out.println("%☆◆フジワラ>>" + tmp_sentence[i]);
            }

            will_shutdown_app = true;
        }

        //長すぎる文がある
        for (int i = 0; i < num_of_lines; i++) {
            if (is_too_long[i] == true) {
                System.out.println("%☆◆フジワラ:TextBoxの横幅に対して" + i + "行目の文が長すぎる");
                System.out.print("%☆◆フジワラ>>");
                for (int j = 0; j < tmp_text_count; j++) {
                    if (tmp_num_of_lines[j] == i) {
                        System.out.print(tmp_sentence[j]);
                    }
                }
                will_shutdown_app = true;
            }
        }

        if (will_shutdown_app == true) {
            System.out.println("");
            throw new Error(" ");//アプリを落とす
        }
    }

    //箱１個分の文章を描画する関数
    private void displayText() {

        for (int i = first; queue[i].isMOP() == false; i = (i + 1) % MAX_QUEUE_TEXT) {
            graphic.bookingDrawText(queue[i].getSentence(), box_left + 5 + queue[i].getBeginColumn(), box_top + 45 + 40 * queue[i].getNumOfLines(), queue[i].getPaint());
//            graphic.bookingDrawText(queue[i].getSentence(), box_left + 5 + queue[i].getBeginColumn(), box_top + 45 + (int)(box_paint.getTextSize()) * queue[i].getNumOfLines(), queue[i].getPaint());
            System.out.println("first");
        }
        //デバッグ用
//        if (tmp_first != first) {
//            for (int i = 0; i < MAX_QUEUE_TEXT; i++) {
//                System.out.println(queue[i].getSentence());
//            }
//            System.out.println("■");
//            tmp_first = first;
//        }
    }

    public int getTouchID() {

        return touch_id;
    }

    public void setTouch_id(int _touch_id) {

        touch_id = _touch_id;
    }

    public void updateText() {
        if (first != last) {//文章キューが空でなかったら、

            int pre_first = first;

            //次に表示する文章の冒頭まで first をずらす
            while (!(queue[first].isMOP() == true && queue[(first - 1 + MAX_QUEUE_TEXT) % MAX_QUEUE_TEXT].isMOP() == false)) {
                first = (first + 1) % MAX_QUEUE_TEXT;//firstを１個進める
            }
            while (!(queue[first].isMOP() == false && queue[(first - 1 + MAX_QUEUE_TEXT) % MAX_QUEUE_TEXT].isMOP() == true)) {
                first = (first + 1) % MAX_QUEUE_TEXT;//firstを１個進める
            }

            if (assign_sentence_id == true) {
                erasePreQueue(pre_first);
            }
        }
    }

    //さっきまで表示されていたsentenceをqueueから抹消する
    private void erasePreQueue(int pre_first) {
        for (; pre_first != first; pre_first = (pre_first + 1) % MAX_QUEUE_TEXT) {
            queue[pre_first].initSentence();
        }
    }

    public void changeText(int _sentence_id) {

        if (assign_sentence_id == false) {
            System.out.println("%☆◆フジワラ:sentence_idを指定しない設定で作ったTextBoxに対して、[sentence_idを指定してsentenceを表示する関数]を使った from TextBox.java");
            updateText();

        } else {
            sentence_exists = true;

            System.out.println("uunchi changeText()");

            if (_sentence_id < sentence_count) {
                System.out.println("uunchi");

                if (sentence_firsts[_sentence_id] > 0) {
                    first = sentence_firsts[_sentence_id];
                } else {
                    System.out.println("◆負数のsentence_firsts[]をfirstに代入しようとした from TextBox.java");
                }
            }
        }
    }

    public void setExists(boolean _exists) {
        exists = _exists;
    }

    public void setSentenceExists(boolean _sentence_exists) {
        sentence_exists = _sentence_exists;
    }

    public void setUpdateTextByTouching(boolean _update_text_by_touching) {
        update_text_by_touching = _update_text_by_touching;
    }

    public void setAssignSentenceId(boolean _assign_sentence_id) {
        assign_sentence_id = _assign_sentence_id;
    }

    public int getBoxId() {
        return box_id;
    }

    public int getBoxUpLeftX() {
        return box_left;
    }

    public void setBoxUpLeftX(int _box_left) {
        box_left = _box_left;
    }

    public int getBoxUpLeftY() {
        return box_top;
    }

    public void setBoxUpLeftY(int _box_top) {
        box_top = _box_top;
    }

    public int getBoxDownRightX() {
        return box_right;
    }

    public void setBoxDownRightX(int _box_right) {
        box_right = _box_right;
    }

    public int getBoxDownRightY() {
        return box_down;
    }

    public void setBoxDownRightY(int _box_down) {
        box_down = _box_down;
    }

    public void resetCursor() {
    }

    public int getCursor() {
        return 0;
    }

    public void setText() {
    }

    public void resetContent() {
    }

}
