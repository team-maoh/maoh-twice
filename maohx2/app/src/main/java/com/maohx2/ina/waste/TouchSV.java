package com.maohx2.ina.waste;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.maohx2.ina.BaseSurfaceView;

/**
 * Created by ina on 2017/09/20.
 */


public class TouchSV extends BaseSurfaceView implements SurfaceHolder.Callback {


    public TouchSV(Context context){
        super(context);

        created = false;
        System.out.println("BS Const");

        holder = this.getHolder();
        holder.addCallback(this);

    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        created = true;
    }

    public void startThread() {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {}

    public boolean onTouchEvent (MotionEvent event) {

        int actionID = event.getAction();

        switch (actionID) {
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default :
                break;
        }

        return true;
    }

}
