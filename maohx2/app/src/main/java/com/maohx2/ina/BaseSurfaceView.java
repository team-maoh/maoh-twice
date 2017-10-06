package com.maohx2.ina;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ina on 2017/09/20.
 */

public class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    protected boolean created;
    protected SurfaceHolder holder;


    public BaseSurfaceView(Context context) {
        super(context);

        created = false;
        System.out.println("BS Const");

        holder = this.getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.TRANSLUCENT);

    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        created = true;
        System.out.println("BS Creat");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
    }

    public boolean isCreated(){

        return created;
    }


    public void run(){}

}
