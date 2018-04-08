package com.maohx2.ina;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by ina on 2018/04/01.
 */

public class ActivityChange {

    Activity currentActivity;
    BaseSurfaceView currentSurfaceView;

    ActivityChange(BaseSurfaceView _currentSurfaceView, Activity _currentActivity){

        currentSurfaceView = _currentSurfaceView;
        currentActivity = _currentActivity;
    }


    public void toStartActivity() {
        currentSurfaceView.stopThread();
        Intent intent = new Intent(currentActivity, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        currentActivity.startActivity(intent);
    }

    public void toWorldActivity() {
        currentSurfaceView.stopThread();
        Intent intent = new Intent(currentActivity, WorldActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        currentActivity.startActivity(intent);
    }

    public void toDungeonActivity() {
        //thread.interrupt();
        currentSurfaceView.stopThread();
        Intent intent = new Intent(currentActivity, DungeonActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        currentActivity.startActivity(intent);
    }
}
