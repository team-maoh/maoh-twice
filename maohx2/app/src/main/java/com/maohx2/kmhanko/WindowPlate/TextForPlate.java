package com.maohx2.kmhanko.WindowPlate;

import android.graphics.Paint;

import com.maohx2.fuusya.TextBox.Text;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2019/01/18.
 */

public class TextForPlate {
    protected String text;
    protected Paint textPaint;
    protected float textWidth;
    protected float textHeight;
    protected int textX, textY, textXoffset, textYoffset;
    protected boolean exist;

    Graphic graphic;

    WindowTextPlate.TextPosition textPosition;

    public TextForPlate(Graphic _graphic) {
        graphic = _graphic;
        exist = false;
    }

    public void release() {
    }

    public void setText(String x) {
        text = x;
    }
    public void setPaint(Paint x) {
        textPaint = x;
    }
    public void setWidthAndHeight() {
        if (textPaint != null && text != null) {
            textHeight = textPaint.getTextSize();
            textWidth = textPaint.measureText(text);
        }
    }
    public void setTextPosition(WindowTextPlate.TextPosition x) {
        textPosition = x;
    }

    public void setTextX(int x) {
        textX = x;
    }
    public void setTextY(int y) {
        textY = y;
    }

    public void setTextXY(int x, int y) {
        textX = x;
        textY = y;
    }

    public void setOffsetX(int x) {
        textXoffset = x;
    }
    public void setOffsetY(int y) {
        textYoffset = y;
    }

    public void setOffsetXY(int x, int y) {
        textXoffset = x;
        textYoffset = y;
    }

    public int getTextX() {
        return textX;
    }
    public int getTextY() {
        return textY;
    }
    public int getOffsetX() {
        return textX;
    }
    public int getOffsetY() {
        return textX;
    }

    public void updateTextPosition(int x, int y, float width, float height, float[] margin) {
        if (textPosition == WindowTextPlate.TextPosition.UP || textPosition == WindowTextPlate.TextPosition.UPLEFT || textPosition == WindowTextPlate.TextPosition.UPRIGHT) {
            textY = y + (int)textHeight + (int)margin[1];
        }
        if (textPosition == WindowTextPlate.TextPosition.DOWN || textPosition == WindowTextPlate.TextPosition.DOWNLEFT || textPosition == WindowTextPlate.TextPosition.DOWNRIGHT) {
            textY = (int) (y + height - margin[3]);
        }
        if (textPosition == WindowTextPlate.TextPosition.RIGHT || textPosition == WindowTextPlate.TextPosition.UPRIGHT || textPosition == WindowTextPlate.TextPosition.DOWNRIGHT) {
            textX = x + (int)margin[0];
        }
        if (textPosition == WindowTextPlate.TextPosition.LEFT || textPosition == WindowTextPlate.TextPosition.DOWNLEFT || textPosition == WindowTextPlate.TextPosition.UPLEFT) {
            textX = (int) (x + width - textWidth - margin[4]);
        }
        if (textPosition == WindowTextPlate.TextPosition.UP || textPosition == WindowTextPlate.TextPosition.CENTER || textPosition == WindowTextPlate.TextPosition.DOWN) {
            textX = (int)(x + (width - textWidth)/2.0f - 4);
        }
        if (textPosition == WindowTextPlate.TextPosition.RIGHT || textPosition == WindowTextPlate.TextPosition.CENTER || textPosition == WindowTextPlate.TextPosition.LEFT) {
            textY = (int)(y + (height + textHeight)/2.0f - 4);
        }
        textX += textXoffset;
        textY += textYoffset;
    }

    public void draw() {
        if (exist && text != null && textPaint != null) {
            graphic.bookingDrawText(text, textX, textY, textPaint);
        }
    }

    public void existIs(boolean x) {
        exist = x;
    }

}
