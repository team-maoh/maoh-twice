package com.maohx2.ina;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;

import android.view.SurfaceView;
import android.widget.RelativeLayout;
import com.maohx2.kmhanko.music.MusicAdmin;
import com.maohx2.ina.Draw.Graphic;
import android.media.AudioManager;
import static com.maohx2.ina.Constants.Bitmap.*;

/**
 * Created by ryomasenda on 2017/09/08.
 */

public abstract class BaseActivity extends Activity {

    RelativeLayout layout;
    BackSurfaceView backSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        layout = new RelativeLayout(this);
        setContentView(layout);

        //音量調整ボタンを使用できるようにする
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //MusicAdmin.setAudioService(this);

        /*
        backSurfaceView = new BackSurfaceView(this);
        layout.addView(backSurfaceView);
        */
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    void setImage(String name, double x, double y) {
        //graphic.setImage(name, x, y);
    }

    //レイヤー指定あり
    void setImage(String name, double x, double y, String layerName) {
        //graphic.setImage(name, x, y, layerName);
    }

    //void draw() {
      //  graphic.draw();
   // }

    /*
    以上グラフィック関数
     */
}
