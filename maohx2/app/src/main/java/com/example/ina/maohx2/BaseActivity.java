package com.example.ina.maohx2;

import android.app.Activity;
import android.os.Bundle;

import com.example.graphic.Graphic;

/**
 * Created by ryomasenda on 2017/09/08.
 */

public abstract class BaseActivity extends Activity {
    Graphic graphic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graphic = new Graphic(this);
        setContentView(graphic);
    }



    /*
    以下グラフィック用関数
    setImage() :関数で描画する内容をセット
    draw() :セットされた内容を描画
     */

    void setImage(String name, double x, double y){
        graphic.setImage(name,x,y);
    }

    //レイヤー指定あり
    void setImage(String name, double x, double y, String layerName){
        graphic.setImage(name,x,y,layerName);
    }

    void draw(){
        graphic.draw();
    }

}
