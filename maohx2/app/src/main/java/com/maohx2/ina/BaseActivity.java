package com.maohx2.ina;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.content.Context;

/**
 * Created by ryomasenda on 2017/09/08.
 */

enum Layer{
    BACKGROUND,
    CHARACTER,
}

public abstract class BaseActivity extends Activity {
    Graphic graphic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graphic = new Graphic(this);
        setContentView(graphic);
    }

    void setImage(String name, float x, float y){
        graphic.setImage(name,x,y);
    }

    void draw(){
        graphic.draw();
    }

}
