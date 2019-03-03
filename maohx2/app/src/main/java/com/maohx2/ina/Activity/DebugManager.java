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
    static final boolean debugMode = false;

    private final static int DEBUG_TEXT_MAX = 40;
    private Graphic graphic;
    private double fps;
    private Paint paint;
    private Runtime runtime;

    private String[] debugText = new String[DEBUG_TEXT_MAX];
    private int nowFpsID = 0;
    private float fpsLog[] = new float[100];

    public DebugManager(Activity _activity) {

        paint = new Paint();
        paint.setTextSize(40);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        runtime = Runtime.getRuntime();

    }

    public void setGraphic(Graphic _graphic) {
        graphic = _graphic;
    }

    public void draw() {
        if (graphic != null && debugMode) {
            graphic.bookingDrawText("FPS[f/s]: " + String.valueOf((int)fps), 0, (int)paint.getTextSize() * 1, paint);
            graphic.bookingDrawText("ThisAppMemory[KB]: " + String.valueOf((int)runtime.totalMemory()/1024.0), 0, (int)paint.getTextSize() * 2, paint);
            graphic.bookingDrawText("FreeMemory[KB]: " + String.valueOf((int)runtime.freeMemory()/1024.0), 0, (int)paint.getTextSize() * 3, paint);
            graphic.bookingDrawText("TotalMemory[KB]: " + String.valueOf((int)(runtime.totalMemory() - runtime.freeMemory())/1024.0), 0, (int)paint.getTextSize() * 4, paint);
            graphic.bookingDrawText("MaxMemory[KB]: " + String.valueOf((int)runtime.maxMemory()/1024.0), 0, (int)paint.getTextSize() * 5, paint);

            for (int i = 0; i < debugText.length; i++) {
                if (debugText[i] != null) {
                    if (!debugText[i].equals("")) {
                        graphic.bookingDrawText(debugText[i], 800, (int) paint.getTextSize() * (i + 1), paint);
                    }
                }
            }

            for (int i = 0; i < fpsLog.length; i++) {
                if (fpsLog[i] != 0) {
                    graphic.bookingDrawCircle(i * 1600/fpsLog.length, (int)(600.0f - fpsLog[i] * 10.0f), 5, paint);
                }
            }
            graphic.bookingDrawRect(0, 299,1600,300, paint);
            graphic.bookingDrawRect(0, 600,1600,601, paint);
        }
    }

    public void updateDebugText(int i, String text) {
        if ( i >= 0 && i < DEBUG_TEXT_MAX) {
            debugText[i] = text;
        }
    }

    public void setFPS(double _fps) {
        fps = _fps;
        fpsLog[nowFpsID] = (float)fps;
        nowFpsID = (nowFpsID+1)%fpsLog.length;
    }

    public void release() {
        paint = null;
        debugText = null;
        fpsLog = null;
    }


}
