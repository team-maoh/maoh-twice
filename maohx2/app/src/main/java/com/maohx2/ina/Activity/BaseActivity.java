package com.maohx2.ina.Activity;


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
import com.maohx2.kmhanko.sound.SoundAdmin;

import android.media.AudioManager;
import static com.maohx2.ina.Constants.Bitmap.*;

/**
 * Created by ryomasenda on 2017/09/08.
 */

public abstract class BaseActivity extends Activity {

    public RelativeLayout layout;
    public BackSurfaceView backSurfaceView;
    public boolean isPaused;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isPaused = false;

        //System.out.println("ActLife:" + getActivityName() + " onCreate");

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        layout = new RelativeLayout(this);
        setContentView(layout);
        overridePendingTransition(0,0);

        //音量調整ボタンを使用できるようにする
        System.out.println("talano : setVolumeControlStream");
        super.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //MusicAdmin.setAudioService(this);

        /*
        backSurfaceView = new BackSurfaceView(this);
        layout.addView(backSurfaceView);
        */


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //return true; もともとこれしかなかった。これだと全ての端末のキー入力を無視することになる。
        if(keyCode != KeyEvent.KEYCODE_BACK){
            return super.onKeyDown(keyCode, event);//バックキーの場合のみ、無視する
        }else{
            return false;
        }
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
        System.out.println("ActLife:" + getActivityName() + " onPause");
        MusicAdmin.pauseAll();
        //stopSound();
        touchReset();
        isPaused = true;
    }

    public boolean isPaused() {
        return isPaused;
    }

    //ポーズ時用
    abstract public void stopSound();
    abstract public void touchReset();


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);//遷移時のアニメを無効に
    }

    public String getActivityName() { return "BaseActivity"; };

    //ライフパス確認用

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("ActLife:" + getActivityName() + " onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        System.out.println("ActLife:" + getActivityName() + " onChildTitleChanged");
        super.onChildTitleChanged(childActivity, title);
    }

    protected void onDestroy() {
        System.out.println("ActLife:" + getActivityName() + " onDestroy");
        super.onDestroy();
    }

    protected void onNewIntent(Intent intent) {
        System.out.println("ActLife:" + getActivityName() + " onNewIntent");
        super.onNewIntent(intent);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        System.out.println("ActLife:" + getActivityName() + " onPostCreate");
        super.onPostCreate(savedInstanceState);
    }

    protected void onPostResume() {
        System.out.println("ActLife:" + getActivityName() + " onPostResume");
        super.onPostResume();
    }

    protected void onRestart() {
        System.out.println("ActLife:" + getActivityName() + " onRestart");
        super.onRestart();
        MusicAdmin.restartAll();
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("ActLife:" + getActivityName() + " onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void onResume() {
        System.out.println("ActLife:" + getActivityName() + " onResume");
        isPaused = false;
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState) {
        System.out.println("ActLife:" + getActivityName() + " onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    protected void onStart() {
        System.out.println("ActLife:" + getActivityName() + " onStart");
        super.onStart();
    }

    protected void onStop() {
        System.out.println("ActLife:" + getActivityName() + " onStop");
        super.onStop();
    }

    protected void onTitleChanged(CharSequence title, int color) {
        System.out.println("ActLife:" + getActivityName() + " onTitleChanged");
        super.onTitleChanged(title, color);
    }

    protected void onUserLeaveHint() {
        System.out.println("ActLife:" + getActivityName() + " onUserLeaveHint");
        super.onUserLeaveHint();
    }

}