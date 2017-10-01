package com.example.ina.maohx2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.content.Context;

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

    void setImage(String name, double x, double y){
        graphic.setImage(name,x,y);
    }

    void setImage(String name, double x, double y, String layerName){
        graphic.setImage(name,x,y,layerName);
    }

    void draw(){
        graphic.draw();
    }

}
