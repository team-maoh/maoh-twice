package com.maohx2.ina;


import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;

import android.view.SurfaceView;

import com.maohx2.ina.Draw.BookingData;
import com.maohx2.ina.Draw.Graphic;

import static com.maohx2.ina.Constants.Bitmap.*;

/**
 * Created by ryomasenda on 2017/09/08.
 */

public abstract class BaseActivity extends Activity {
    SurfaceView surfaceView;
    SurfaceHolder holder;
    //Graphic graphic = new Graphic(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //graphic.setHolder(holder);
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
