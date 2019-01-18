package com.maohx2.ina.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.GlobalData;
import com.maohx2.ina.Constants;

/**
 * Created by ina on 2017/09/20.
 */

public class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    public Paint paint = new Paint();
    public SurfaceHolder holder;
    public Thread thread;
    //public ActivityChange activityChange;
    public BaseActivity currentActivity;

    public boolean openingFlag = false;

    public double touch_x = 0;
    public double touch_y = 0;

    public Constants.Touch.TouchState touch_state = Constants.Touch.TouchState.AWAY;
    public GlobalData global_data;

    public  long error = 0;
    public int fps = 30;
    public long idealSleep = (1000 << 16) / fps;
    public long oldTime;
    public long newTime = System.currentTimeMillis() << 16;

    public BackSurfaceView backSurfaceView;

    public Graphic graphic;
    public boolean back_ground_flag = false;

    public void release() {
        System.out.println("takanoRelease : BaseSurfaceView");
        paint = null;
        thread = null;
        //TODO ゲーム終了時に
        //global_data.release();
    }

    public BaseSurfaceView(Activity _currentActivity, BackSurfaceView _backSurfaceView) {
        super(_currentActivity);
        currentActivity = (BaseActivity)_currentActivity;
        backSurfaceView = _backSurfaceView;
        setZOrderOnTop(true);
        holder = getHolder();
        holder.addCallback(this);
        paint.setColor(Color.BLUE);
        global_data = (GlobalData) currentActivity.getApplication();
        //activityChange = new ActivityChange(this, currentActivity);

        //音量調整ボタンを使用できるようにする
        currentActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPurgeable = true; //再利用性のない画像を解放する
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //ImageContext backImageContext = graphic.makeImageContext(graphic.searchBitmap("e51-0"),0,0,true);
        //backSurfaceView.drawBackGround(backImageContext);

        //thread = new Thread(this);
        //thread.start();

        //activityChange = new ActivityChange(this, currentActivity);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}


    @Override
    public void run() {

        long old_frame = System.currentTimeMillis();

        while (thread != null) {
            oldTime = newTime;
            gameLoop();
            newTime = System.currentTimeMillis() << 16;
            long sleepTime = idealSleep - (newTime - oldTime) - error; // 休止できる時間
            if (sleepTime < 0x20000) sleepTime = 0x20000; // 最低でも2msは休止
            oldTime = newTime;
            try {
                thread.sleep(sleepTime >> 16); // 休止
            }catch (InterruptedException e){
                System.out.println("%☆イナガキ：fps固定処理で例外が渡されました(スリープが負の数など)");
            }

            newTime = System.currentTimeMillis() << 16;
            error = newTime - oldTime - sleepTime; // 休止時間の誤差

            final long now_frame = System.currentTimeMillis();
            final double fps = 1000.0 / (now_frame - old_frame);
            old_frame = now_frame;


            //System.out.println("fps:"+fps);

        }
    }

    public void gameLoop(){

    }

    public void stopThread(){
        thread = null;
    }


    //なぞられた点を記録するリスト
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_state = Constants.Touch.TouchState.DOWN;
                touch_x = event.getX();
                touch_y = event.getY();
                //System.out.println("***ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                touch_state = Constants.Touch.TouchState.MOVE;
                touch_x = event.getX();
                touch_y = event.getY();
                //System.out.println("***ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                touch_state = Constants.Touch.TouchState.UP;
                //System.out.println("***ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                //System.out.println("***ACTION_CANCEL");
                break;
        }
        return true;
    }


    public boolean getOpeningFlag() {
        return openingFlag;
    }

    public void setOpeningFlag(boolean _openingFlag) {
        openingFlag = _openingFlag;
    }

    public Constants.Touch.TouchState getTouchState() {
        return touch_state;
    }
    public void setTouchState(Constants.Touch.TouchState x) {
        touch_state = x;
    }
}