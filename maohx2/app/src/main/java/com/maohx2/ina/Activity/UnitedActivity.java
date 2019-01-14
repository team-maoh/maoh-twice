package com.maohx2.ina.Activity;

import android.os.Bundle;
import android.view.SurfaceView;

import com.maohx2.ina.Activity.BaseActivity;
import com.maohx2.ina.Constants;
import com.maohx2.ina.GlobalData;

/**
 * Created by user on 2019/01/14.
 */

public class UnitedActivity extends BaseActivity {
    boolean gameSystemStartFlag = false;
    boolean gameStartFlag = false;
    UnitedSurfaceView unitedSurfaceView;
    GlobalData global_data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unitedSurfaceView = new UnitedSurfaceView(this, backSurfaceView);
        layout.addView(unitedSurfaceView);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!gameStartFlag) {//アプリ開始時のみ実行する
            global_data = (GlobalData) this.getApplication();
            global_data.init(unitedSurfaceView.getWidth(), unitedSurfaceView.getHeight());
            unitedSurfaceView.startGame();
            gameStartFlag = true;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unitedSurfaceView = null;
    }

    @Override
    public String getActivityName() {
        return "UnitedActivity";
    }

    @Override
    public void finish() {
        super.finish();
        unitedSurfaceView.my_database_admin.close();
        unitedSurfaceView.release();
        unitedSurfaceView = null;
    }

    @Override
    public void stopSound() {
        unitedSurfaceView.getSoundAdmin().stopAll();
    };

    @Override
    public void touchReset() {
        unitedSurfaceView.setTouchState(Constants.Touch.TouchState.AWAY);
    }

    public UnitedSurfaceView getUnitedSurfaceView() {
        return unitedSurfaceView;
    }
}
