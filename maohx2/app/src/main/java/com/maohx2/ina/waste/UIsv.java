package com.maohx2.ina.waste;

import android.content.Context;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ina on 2017/09/17.
 */

public class UIsv extends SurfaceView implements SurfaceHolder.Callback {

    public UIsv(Context context) {
        super(context);
        setZOrderOnTop(true);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
