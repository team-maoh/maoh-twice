package com.maohx2.ina.Text;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Color.WHITE;
import static java.security.AccessController.getContext;

/**
 * Created by ina on 2017/10/01.
 */

public class TextBox {

    int box_up_left_x;//箱の左上の頂点のx座標
    int box_up_left_y;//　　　　　　　　y座標
    int box_down_right_x;//箱の右下の頂点のx座標
    int box_down_right_y;//　　　　　　　　y座標
    int touch_id;
    Paint box_paint;
    int text_count;

    //テスト用
//    int LENGTH_OF_LINE = 17;//1行あたりの表示文字数
    int COLUMN_OF_BOX = 680;//箱の横幅
    int ROW_OF_BOX = 4;//1度に表示するテキストの最大行数

    int MAX_QUEUE_TEXT = 300;//下のキューの容量
    //    String text_queue[] = new String[MAX_QUEUE_TEXT];//他所から貰ったテキストを格納するキュー
    int last, first;//text_queueの末尾と先頭（末尾にテキストを追加する/先頭のテキストを表示する）
    //0■
    //1■
    //2□　←last（次にinputしたtextを格納する位置）
    //3□
    //4□
    //5■　←first（次に表示するtextの位置）
    //6■
    //7■


    //    String display_line[] = new String[MAX_LINES];//表示される文章の1,2,3行目
    boolean hasUpdatedText;//一度のタッチでテキストが何度も切り替わらないようにするための変数

    Text queue[] = new Text[MAX_QUEUE_TEXT];

    //テスト用のテキスト
    String manual_text[] = {"地球をアイスピックでつついたとしたら、ちょうど良い感じにカチ割れるんじゃないかというくらいに冷え切った朝だった。", "いっそのこと、むしろ率先してカチ割りたいほどだ。", "とはいえ寒いのも当然で、それは今が冬だからだ。"};
    //    String manual_text[] = {"父が着地し全力疾走を続けて二秒も経たないうちに、熊の吠え声とオーストラリア人のわめき声が交叉した。", "バシッと何かを叩く音。オーストラリア人の悲鳴と罵り。何かが転がる音。", "石が転げて別の石とぶつかり合うガラガラカーンという音。"};
    int NUM_OF_SENTENCE = 3;//仮/manual_textの配列の個数 != MAX_LINES
    String auto_text[] = {"100ダメージを受けた!!"};

    public void init(int _touch_id) {
        box_paint = new Paint();
        box_paint.setColor(Color.argb(100, 0, 0, 0));

        box_up_left_x = 100;
        box_up_left_y = 550;
        box_down_right_x = 800;
        box_down_right_y = 750;

        touch_id = _touch_id;
        hasUpdatedText = false;

        text_count = 0;
        first = 0;
        last = 0;
//        queue[first] = "　";

        for (int i = 0; i < MAX_QUEUE_TEXT; i++) {
            queue[i] = new Text();
            queue[i].init(ROW_OF_BOX, COLUMN_OF_BOX);
        }

    }

    public void update(boolean touch_state) {

//        if(queue[first].getFadingSpeed() == 0){
        if (touch_state == false) {//タッチしていない
            box_paint.setColor(Color.argb(100, 0, 0, 0));
            if (hasUpdatedText == false) {
                if (first != last) {//文章キューが空でなかったら、
                    first = (first + 1) % MAX_QUEUE_TEXT;//firstを１個進める
                }

                hasUpdatedText = true;
            }
        } else {//タッチしている
            box_paint.setColor(Color.argb(100, 100, 0, 0));
            hasUpdatedText = false;
        }
//        }

        /*
        if (queue[].getFadingSpeed() == 0) {
            paint.setColor(Color.argb(100, 0, 0, 0));
            /*
            if (queue_first != queue_last) {
                queue_first = (queue_first + 1) % MAX_QUEUE_TEXT;
            }else{
                System.out.println("イコール");
            }

        } else if (display_mode.equals("manual")) {
            if (touch_state == false) {//タッチしていない
                paint.setColor(Color.argb(100, 0, 0, 0));
                if (hasUpdatedText == false) {
                    if (queue_first != queue_last) {//文章キューが空でなかったら、
                        queue_first = (queue_first + 1) % MAX_QUEUE_TEXT;//firstを１個進める
                    }

                    hasUpdatedText = true;
                }
            } else {//タッチしている
                paint.setColor(Color.argb(100, 100, 0, 0));
                hasUpdatedText = false;
            }
        }
        */
    }

    public void draw(Canvas canvas) {

        //上のupdateで設定したpaintを使って箱を描画
        Rect rect = new Rect(box_up_left_x, box_up_left_y, box_down_right_x, box_down_right_y);
        canvas.drawRect(rect, box_paint);

        //文字描画のためにpaintの設定を変更

        if (queue[first].line(0).equals("null")) {
            System.out.println("◆nullです");
        } else {

            displayText(canvas);//first番目の文章を描画する

            /*
            if (display_mode.equals("auto")) {
                displayText(canvas, breakTouchText(queue[first].inputSentence()));//箱の中に文章を表示
                if (first == last) {
//                    first = (first + 1) % MAX_QUEUE_TEXT;
                }
                System.out.println("nullでない");

            } else if (display_mode.equals("manual")) {
                displayText(canvas, breakTouchText(queue[first].inputSentence()));//箱の中に文章を表示
                System.out.println("nullでない");
            }
            */
        }

/*
        if (num_of_lines < 0 || num_of_lines == MAX_NUM_OF_LINES + 1) {
            System.out.println("エラー");
        } else {
            displayText(canvas, num_of_lines);//箱の中に文章を表示
        }
        */
    }

    //外から文章を受け取り、文章キューの末尾に追加する
    public void inputText(String _sentence, Paint _paint) {

        int begin_row, begin_column;

//        if ((queue_last + 1) % MAX_QUEUE_TEXT == queue_first) {
        if (_sentence.equals("\n")) {
            queue[(last - 1 + MAX_QUEUE_TEXT) % MAX_QUEUE_TEXT].setEOP(true);//直前に文を格納したqueueにEOP = true(text is EndOfParagraph)をset
            queue[(last - 1 + MAX_QUEUE_TEXT) % MAX_QUEUE_TEXT].setEndPosition(0, 0);//便宜的に、文が(0,0)で終わったことにする
        } else {

            if ((last + 1 - first) % MAX_QUEUE_TEXT == 0) {
                System.out.println("◆文queueが満杯です◆");
            } else {
                //直前に格納した文の末尾の座標をget
                begin_row = queue[(last - 1 + MAX_QUEUE_TEXT) % MAX_QUEUE_TEXT].getEndRow();//queue[(last-1)%MAX_QUEUE_TEXT]とは書かない(負数の剰余を求めるとアプリが止まるので)
                begin_column = queue[(last - 1 + MAX_QUEUE_TEXT) % MAX_QUEUE_TEXT].getEndColumn();
                //文を箱に流し込む
                queue[last].makeParagraph(_sentence, _paint, begin_row, begin_column);

//            queue[last].setColor(_color);
//            queue[last].setFadingSpeed(_fading_speed);
                last = (last + 1) % MAX_QUEUE_TEXT;

                text_count++;
            }
        }
    }
/*
    public void terminateText(){
        queue[last].terminateSentence(text_count);
        text_count = 0;
    }
    */

    private void displayText(Canvas canvas) {

        for (int i = 0; i < queue[first].getRowOfText(); i++) {
            canvas.drawText(queue[first].line(i), 105, 595 + 40 * i, queue[first].getPaint());
        }
    }

    public int getTouchID() {

        return touch_id;
    }

    public void setTouch_id(int _touch_id) {

        touch_id = _touch_id;
    }

    public int getBoxUpLeftX() {
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

    public void resetCursor() {
    }

    public int getCursor() {
        return 0;
    }

    public void setText() {
    }

    public void resetContent() {
    }

    public TextBox() {
    }

}
