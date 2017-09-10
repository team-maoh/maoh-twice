package com.example.ina.maohx2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.content.Context;

/**
 * Created by ryomasenda on 2017/09/08.
 */
enum LAYER {
    NOTHING, //LAYERが設定していなかった場合の例外
    BACKGROUND,
    CHARACTER,
}

public abstract class BaseActivity extends Activity {

    LinearLayout layout;
    Graphic graphic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new LinearLayout(this);
        graphic = new Graphic(this);
        setContentView(layout);
    }

    void setMoveImage(String name){
        layout.addView(graphic.setMoveImage(name));
    }

    void setImageAt(String name, float x, float y){
        layout.addView(graphic.getImageAt(name,x,y));
    }

    void setImageAt(String name, float x, float y, LAYER layer){
        layout.addView(graphic.getImageAt(name,x,y,layer));
    }

}
