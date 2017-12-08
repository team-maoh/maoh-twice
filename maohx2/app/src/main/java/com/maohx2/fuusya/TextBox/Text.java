package com.maohx2.fuusya.TextBox;

import android.graphics.Color;
import android.graphics.Paint;

import static android.graphics.Color.WHITE;

/**
 * Created by Fuusya on 2017/10/20.
 */

public class Text {

    int row_of_box;//箱の中の文章の最大行数(1から始まる)/箱の縦幅
    int column_of_box;//箱の中の文章の横幅/箱の横幅　(箱の実体を作るときにsetする)

    String sentence;//格納している文字列
    Paint paint;//文字色やフォント
    int begin_column;//文章の開始位置
    int num_of_lines;//このText()が持つ文は * 行目に含まれる

    boolean isMOP;//MOP(Medium of Paragraph 文章と文章の間に挟まる空のqueue)であるか否か

    public void init(int _row_of_box, int _column_of_box) {

        column_of_box = _column_of_box;
        row_of_box = _row_of_box;

        isMOP = true;

        begin_column = 0;
        num_of_lines = 0;
        sentence = "null";

    }

    public void setSentence(String _sentence) {
        sentence = _sentence.substring(0);
//        System.out.println(_sentence + "を格納した");
    }

    public void setPaint(Paint _paint) {
        paint = _paint;
    }

    public void setBeginColumn(int _begin_column) {

        if (0 <= _begin_column && _begin_column < column_of_box) {
            begin_column = _begin_column;
        } else {
            System.out.println("◆begin_columnが異常 from Text.java");
        }

    }

    public int getBeginColumn() {
        return begin_column;
    }

    public void setNumOfLines(int _num_of_lines) {

        if (0 <= _num_of_lines && _num_of_lines < row_of_box) {
            num_of_lines = _num_of_lines;
        } else {
            System.out.println("◆num_of_linesが異常 from Text.java");
        }
    }

    public int getNumOfLines() {
        return num_of_lines;
    }

    public String getSentence() {
        return sentence;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setMOP(boolean _isMOP) {
        isMOP = _isMOP;
    }

    public boolean isMOP() {
        return isMOP;
    }

}
