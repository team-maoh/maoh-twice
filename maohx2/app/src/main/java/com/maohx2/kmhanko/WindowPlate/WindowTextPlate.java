package com.maohx2.kmhanko.WindowPlate;

import android.graphics.Paint;
import com.maohx2.ina.Draw.Graphic;

public class WindowTextPlate extends WindowPlate {

    static final int TEXT_MAX = 8;
    protected TextForPlate textForPlate[] = new TextForPlate[TEXT_MAX];


    public enum TextPosition {
        CENTER,
        RIGHT,
        LEFT,
        UP,
        DOWN,
        UPRIGHT,
        UPLEFT,
        DOWNLEFT,
        DOWNRIGHT,
    }


    public WindowTextPlate(Graphic _graphic, int[] _position) {
        super(_graphic, _position);
        initText();
    }

    public WindowTextPlate(Graphic _graphic, int[] _position, String _text, Paint _textPaint, TextPosition _textPosition) {
        super(_graphic, _position);
        initText();
        setText(_text, _textPaint, _textPosition);
    }

    public WindowTextPlate(Graphic _graphic, int[] _position, String _windowImageName) {
        super(_graphic, _position, _windowImageName);
        initText();
    }

    public WindowTextPlate(Graphic _graphic, int[] _position, String _text, Paint _textPaint, TextPosition _textPosition, String _windowImageName) {
        super(_graphic, _position, _windowImageName);
        initText();
        setText(_text, _textPaint, _textPosition);
    }

    private void initText() {
        for(int i = 0; i < textForPlate.length; i++) {
            textForPlate[i] = new TextForPlate(graphic);
        }
    }

    @Override
    public void draw() {
        if (!drawFlag) {
            return;
        }
        super.drawWindow();
        this.drawText();
    }

    public void setText(String _text, Paint _textPaint, TextPosition _textPosition) {
        setText(0, _text, _textPaint, _textPosition);
    }

    public void setText(int id, String _text, Paint _textPaint, TextPosition _textPosition) {
        textForPlate[id].existIs(true);
        textForPlate[id].setText(_text);
        textForPlate[id].setPaint(_textPaint);
        textForPlate[id].setTextPosition(_textPosition);
        textForPlate[id].setWidthAndHeight();
        textForPlate[id].updateTextPosition(position[0],position[1],width,height,new float[] { marginLeft, marginUp, marginRight, marginDown});
    }

    public void setTextOffset(int x, int y) {
        setTextOffset(0, x, y);
    }

    public void setTextOffset(int id, int x, int y) {
        textForPlate[id].setOffsetXY(x, y);
        textForPlate[id].updateTextPosition(position[0],position[1],width,height, new float[] { marginLeft, marginUp, marginRight, marginDown});
    }


    protected void drawText() {
        for(int i = 0; i < textForPlate.length; i++) {
            textForPlate[i].draw();
        }
    }

    @Override
    public void release() {
        super.release();
        for(int i = 0; i < textForPlate.length; i++) {
            textForPlate[i].release();
        }
        textForPlate = null;
    }

}
