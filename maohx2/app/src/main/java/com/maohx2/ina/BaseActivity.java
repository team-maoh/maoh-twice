package com.maohx2.ina;


import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceHolder;

import android.view.SurfaceView;

/**
 * Created by ryomasenda on 2017/09/08.
 */

public abstract class BaseActivity extends Activity {
    SurfaceView surfaceView;
    SurfaceHolder holder;
    Graphic graphic = new Graphic(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graphic.setHolder(holder);
    }

    /*
    以下グラフィック用関数
    setImage() :関数で描画する内容をセット
    draw() :セットされた内容を描画
     */

    void setImage(String name, double x, double y) {
        graphic.setImage(name, x, y);
    }

    //レイヤー指定あり
    void setImage(String name, double x, double y, String layerName) {
        graphic.setImage(name, x, y, layerName);
    }

    void draw() {
        graphic.draw();
    }

    /*
    以上グラフィック関数
     */
}
