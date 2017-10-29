package com.maohx2.ina.Text;

import android.graphics.Paint;

import static android.graphics.Color.WHITE;

/**
 * Created by Fuusya on 2017/10/20.
 */

public class Text {

    int MAX_ROW_OF_BOX = 30;//箱の中の文章が30行を超えるとバグる
    int row_of_box;//箱の中の文章の最大行数(1から始まる)/箱の縦幅
    int row_of_text;//箱の中の文章の行数(1から始まる)/貰った文章を改行したら全部で何行になったのか

    int column_of_box;//箱の中の文章の横幅/箱の横幅　(箱の実体を作るときにsetする)
    Paint text_paint;//文字色やフォントを保持
    boolean isEOP;//EOP(End of Paragraph)/この文が段落の最後の文だったらtrue,それ以外ならfalse

    int begin_row, begin_column;//文の開始位置
    int end_row, end_column;//文の終了位置

    String line[] = new String[MAX_ROW_OF_BOX];//格納している文章
    double fading_speed;//文章がフェードアウトする速さ（0の場合はタッチで切り替わる）
//    String color;//文字色

    int width_of_surplus;//最終行の文字列の横幅

    public void init(int _row_of_box, int _column_of_box) {

        if (_row_of_box > MAX_ROW_OF_BOX) {
            System.out.println("箱の中の文章の行数が多すぎる/Text().javaのMAX_ROW_OF_BOXを書き換えよ");
        } else {
            row_of_box = _row_of_box;
        }

        column_of_box = _column_of_box;
        //length_of_line = _length_of_line;

        text_paint = new Paint();


//        inputSentence("null", text_paint);
//        inputSentence("地球をアイスピックでつついたとしたら、ちょうど良い感じにカチ割れるんじゃないかというくらいに冷え切った朝だった。", text_paint);

        fading_speed = 0;
        isEOP = false;

        begin_row = 0;
        begin_column = 0;
        end_row = 0;
        end_column = 0;

    }

    //外から貰った文章を改行してline[]に格納する
    public void makeParagraph(String _sentence, Paint _text_paint, int _begin_row, int _begin_column) {
//    public void inputSentence(String _sentence, Paint _text_paint) {

//        int line_count = _begin_row;

        int line_count = 0;

        row_of_text = 1 + (int) _text_paint.measureText(_sentence) / column_of_box;

        //まず文章を改行記号ごとに分割する
//        String[] paragraph = text.split("\n", 0);

        if (row_of_text > row_of_box) {
            _sentence = "◆警告！　箱に一度に表示できる文字数をオーバーしています◆";
            row_of_text = 1 + (int) _text_paint.measureText(_sentence) / column_of_box;
        }

        String testSentence;
        int cp_head, cp_tail, cp_length;
        for (cp_head = 0; line_count < row_of_text - 1; ) {

            for (cp_tail = 0; ; cp_tail++) {
                testSentence = _sentence.substring(cp_head, cp_head + (cp_tail + 1));
                if (column_of_box < (int) _text_paint.measureText(testSentence)) {
                    line[line_count] = _sentence.substring(cp_head, cp_head + cp_tail);

                    cp_head += cp_tail;
                    line_count++;

                    break;
                }
            }

        }
        line[line_count] = _sentence.substring(cp_head, _sentence.length());
        line_count++;

        text_paint = _text_paint;

        end_row = line_count;
        //row_of_text = line_count;

        /*
        //姑息な帳尻合わせ
        int length_of_lines = 15;

        int surplus = _sentence.length() % length_of_lines;//最終行の文字数
        for (line_count = 0; line_count < _sentence.length() / length_of_lines + 1; line_count++) {

            if (line_count < _sentence.length() / length_of_lines) {//1行ずつ文章を表示する
                line[line_count] = _sentence.substring(length_of_lines * line_count, length_of_lines * (line_count + 1));//最終行以外
            } else {
                line[line_count] = _sentence.substring(length_of_lines * line_count, length_of_lines * line_count + surplus);//最終行

            }
//            line_count++;//表示した文章の総行数を数えて、returnする
        }
        */

        /*
        int para = paragraph.length;
        for (int i = 0; i < para; i++) {
            int turn = paragraph[i].length() / LENGTH_OF_LINE + 1;
            for (int j = 0; j <= turn; j++) {
                display_line[line_count] = paragraph[i].substring(j * (LENGTH_OF_LINE + 1), LENGTH_OF_LINE + j * (LENGTH_OF_LINE + 1));
                line_count++;
                System.out.println(line_count);
                if (line_count >= MAX_NUM_OF_LINES) {
                    return MAX_NUM_OF_LINES + 1;
                }
            }
        }
        */

    }

    //格納した文章のnum_of_lines行目を返す/引数を取るのでgetLine()という名前にはしなかった
    public String line(int num_of_lines) {
        if (num_of_lines >= row_of_text) {
            System.out.println("存在しない行数にアクセスしようとした from Text().java");
            return "null";
        } else {
            return line[num_of_lines];
        }

    }

    public Paint getPaint() {
        return text_paint;
    }

    public int getRowOfText() {
        return row_of_text;
    }

    public void setEOP(boolean _isEOP) {
        isEOP = _isEOP;
    }

    public boolean isEOP() {
        return isEOP;
    }

    public void setBeginPosition(int _begin_row, int _begin_column) {

        begin_row = _begin_row;
        begin_column = _begin_column;
    }

    public int getBeginRow() {
        return begin_row;
    }

    public int getBeginColumn() {
        return begin_column;
    }

    public void setEndPosition(int _end_row, int _end_column) {

        end_row = _end_row;
        end_column = _end_column;
    }

    public int getEndRow() {
        return end_row;
    }

    public int getEndColumn() {
        return end_column;
    }


    /*
    //文字色のセット/ifの中身（色の種類）は必要に応じて付け足していく
    public void setColor(String _color) {
        if (_color.equals("WHITE") || _color.equals("RED") || _color.equals("BLUE") || _color.equals("BLACK")) {
            color = _color.substring(0);
        }
    }

    public String getColor(){
        return color;
    }
    */



    /*
    public String getSentence() {
        return sentence;
    }

    public void setSentence(String _sentence){
//        sentence = _sentence;
        sentence = _sentence.substring(0);
    }*/

/*
    public double getFadingSpeed(){
        return fading_speed;
    }

    public void setFadingSpeed(double _fading_speed){
        if(_fading_speed >= 0){
            fading_speed = _fading_speed;
        }
    }
    */


}
