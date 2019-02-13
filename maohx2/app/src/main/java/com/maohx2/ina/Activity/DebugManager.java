package com.maohx2.ina.Activity;
import android.app.Activity;
import com.maohx2.ina.Draw.Graphic;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by user on 2019/02/13.
 */

public class DebugManager {
    //デバッグのために必要な表示などを行うクラス

    //デバッグ表示するかどうか
    static final boolean debugMode = true;

    private final static int DEBUG_TEXT_MAX = 40;
    private Activity activity;
    private Graphic graphic;
    private double fps;
    private Paint paint;
    private Paint paintEdge;
    private Runtime runtime;

    private String[] debugText = new String[DEBUG_TEXT_MAX];

    public DebugManager(Activity _activity) {
        activity = _activity;

        paint = new Paint();
        paint.setTextSize(40);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);

        runtime = Runtime.getRuntime();

    }

    public void setGraphic(Graphic _graphic) {
        graphic = _graphic;
    }

    public void draw() {
        if (graphic != null && debugMode) {
            graphic.bookingDrawText("FPS[f/s]: " + String.valueOf((int)fps), 0, (int)paint.getTextSize() * 1, paint);
            graphic.bookingDrawText("TotalMemory[KB]: " + String.valueOf((int)runtime.totalMemory()/1024.0), 0, (int)paint.getTextSize() * 2, paint);
            graphic.bookingDrawText("FreeMemory[KB]: " + String.valueOf((int)runtime.freeMemory()/1024.0), 0, (int)paint.getTextSize() * 3, paint);
            graphic.bookingDrawText("UsedMemory[KB]: " + String.valueOf((int)(runtime.totalMemory() - runtime.freeMemory())/1024.0), 0, (int)paint.getTextSize() * 4, paint);
            graphic.bookingDrawText("MaxMemory[KB]: " + String.valueOf((int)runtime.maxMemory()/1024.0), 0, (int)paint.getTextSize() * 5, paint);

            for (int i = 0; i < debugText.length; i++) {
                if (debugText[i] != null) {
                    if (!debugText[i].equals("")) {
                        graphic.bookingDrawText(debugText[i], 800, (int) paint.getTextSize() * (i + 1), paint);
                    }
                }
            }

        }
    }

    public void updateDebugText(int i, String text) {
        if ( i >= 0 && i < DEBUG_TEXT_MAX) {
            debugText[i] = text;
        }
    }

    public void setFPS(double _fps) {
        fps = _fps;
    }

    public void release() {
        paint = null;
        paintEdge = null;
        debugText = null;
    }


}
