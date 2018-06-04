package com.maohx2.ina;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;

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

        CLASS_NAME = "BaseActivity";

        System.out.println("ActLife:" + CLASS_NAME + " onCreate");

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

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        System.out.println("ActLife:" + CLASS_NAME + " onPause");
        MusicAdmin.close();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);//遷移時のアニメを無効に
    }


    //ライフパス確認用
    String CLASS_NAME = "???";

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("ActLife:" + CLASS_NAME + " onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        System.out.println("ActLife:" + CLASS_NAME + " onChildTitleChanged");
        super.onChildTitleChanged(childActivity, title);
    }

    protected void onDestroy() {
        System.out.println("ActLife:" + CLASS_NAME + " onDestroy");
        super.onDestroy();
    }

    protected void onNewIntent(Intent intent) {
        System.out.println("ActLife:" + CLASS_NAME + " onNewIntent");
        super.onNewIntent(intent);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        System.out.println("ActLife:" + CLASS_NAME + " onPostCreate");
        super.onPostCreate(savedInstanceState);
    }

    protected void onPostResume() {
        System.out.println("ActLife:" + CLASS_NAME + " onPostResume");
        super.onPostResume();
    }

    protected void onRestart() {
        System.out.println("ActLife:" + CLASS_NAME + " onRestart");
        super.onRestart();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("ActLife:" + CLASS_NAME + " onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onResume() {
        System.out.println("ActLife:" + CLASS_NAME + " onResume");
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("ActLife:" + CLASS_NAME + " onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    protected void onStart() {
        System.out.println("ActLife:" + CLASS_NAME + " onStart");
        super.onStart();
    }

    protected void onStop() {
        System.out.println("ActLife:" + CLASS_NAME + " onStop");
        super.onStop();
    }

    protected void onTitleChanged(CharSequence title, int color) {
        System.out.println("ActLife:" + CLASS_NAME + " onTitleChanged");
        super.onTitleChanged(title, color);
    }

    protected void onUserLeaveHint() {
        System.out.println("ActLife:" + CLASS_NAME + " onUserLeaveHint");
        super.onUserLeaveHint();
    }

}