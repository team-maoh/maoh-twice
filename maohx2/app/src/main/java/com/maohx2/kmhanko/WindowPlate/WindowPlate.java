package com.maohx2.kmhanko.WindowPlate;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

public class WindowPlate {

    Graphic graphic;

    static final String DEFAULT_WINDOW_NAME = "baseButton00";

    protected String windowImageName = null;

    protected float width;
    protected float height;
    protected int[] position;
    protected BitmapData[][] windowElement;
    protected int windowImageWidth;
    protected int windowImageHeight;

    protected int alpha = 192;

    protected boolean drawFlag = false;


    public WindowPlate(Graphic _graphic, int[] _position) {
        graphic = _graphic;
        this.setPosition(_position);
        windowImageName = DEFAULT_WINDOW_NAME;
        setWindowImage(windowImageName);
    }

    public WindowPlate(Graphic _graphic, int[] _position, String _windowImageName) {
        graphic = _graphic;
        this.setPosition(_position);
        windowImageName = _windowImageName;
        setWindowImage(windowImageName);
    }

    public void setWindowImage(String _windowImageName) {
        windowImageName = _windowImageName;

        windowElement = new BitmapData[3][3];
        windowImageWidth = graphic.searchBitmap(windowImageName).getWidth();
        windowImageHeight = graphic.searchBitmap(windowImageName).getHeight();

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                windowElement[i][j] = graphic.processTrimmingBitmapData(
                        graphic.searchBitmap(windowImageName),
                        (int)((double)j*(double)windowImageWidth/3.0),
                        (int)((double)i*(double)windowImageHeight/3.0),
                        (int)((double)windowImageWidth/3.0),
                        (int)((double)windowImageHeight/3.0));
            }
        }
    }

    public void draw() {
        drawWindow();
    }

    protected void drawWindow() {
        if (drawFlag == false) {
            return;
        }
        if (windowImageName == null) {
            return;
        }
        graphic.bookingDrawBitmapData(windowElement[0][0], position[0], position[1], 1.0f, 1.0f, 0, alpha, true);
        graphic.bookingDrawBitmapData(windowElement[1][0], position[0], (int)(position[1] + (float)windowImageHeight / 3.0f), 1.0f, (height - (windowImageHeight * 2.0f / 3.0f)) / (windowImageHeight / 3.0f), 0, alpha, true);
        graphic.bookingDrawBitmapData(windowElement[2][0], position[0], (int)(position[3] - (float)windowImageHeight / 3.0f), 1.0f, 1.0f, 0, alpha, true);

        graphic.bookingDrawBitmapData(windowElement[0][1], (int)(position[0] + (float)windowImageWidth / 3.0f), position[1], (width - windowImageWidth * 2.0f / 3.0f) / (windowImageWidth / 3.0f), 1.0f, 0, alpha, true);
        graphic.bookingDrawBitmapData(windowElement[1][1], (int)(position[0] + (float)windowImageWidth / 3.0f), (int)(position[1] + (float)windowImageHeight / 3.0f), (width - (windowImageWidth * 2.0f / 3.0f)) / (windowImageWidth / 3.0f), (height - (windowImageHeight * 2.0f / 3.0f)) / (windowImageHeight / 3.0f), 0, alpha, true);
        graphic.bookingDrawBitmapData(windowElement[2][1], (int)(position[0] + (float)windowImageWidth / 3.0f), (int)(position[3] - (float)windowImageHeight / 3.0f), (width - (windowImageWidth * 2.0f / 3.0f)) / (windowImageWidth / 3.0f), 1.0f, 0, alpha, true);

        graphic.bookingDrawBitmapData(windowElement[0][2], (int)(position[2] - (float)windowImageWidth / 3.0f), position[1], 1.0f, 1.0f, 0, alpha, true);
        graphic.bookingDrawBitmapData(windowElement[1][2], (int)(position[2] - (float)windowImageWidth / 3.0f), (int)(position[1] + (float)windowImageHeight / 3.0f), 1.0f, (height - (windowImageHeight * 2.0f / 3.0f)) / (windowImageHeight / 3.0f), 0, alpha, true);
        graphic.bookingDrawBitmapData(windowElement[2][2], (int)(position[2] - (float)windowImageWidth / 3.0f), (int)(position[3] - (float)windowImageHeight / 3.0f), 1.0f, 1.0f, 0, alpha, true);
    }


    public void setPosition(int[] _position) {
        position = _position;
        width = position[2] - position[0];
        height = position[3] - position[1];
    }

    public void setDrawFlag(boolean _drawFlag) {
        drawFlag = _drawFlag;
    }

    public void setAlpha(int _alpha) {
        if (_alpha >= 0 && _alpha <= 255) {
            alpha = _alpha;
        }
    }

    public void release() {
        for(int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                windowElement[i][j].release();
            }
        }
    }

}
