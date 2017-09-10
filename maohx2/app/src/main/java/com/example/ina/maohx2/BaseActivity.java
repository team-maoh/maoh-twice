package com.example.ina.maohx2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.content.Context;

/*
 * Created by ryomasenda on 2017/09/08.
 */

public abstract class BaseActivity extends Activity {
    LinearLayout layout;
    Graphic graphic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new LinearLayout(this);
        graphic = new Graphic();
        setContentView(layout);
    }

    void setImage(){
        layout.addView(graphic.setImage(this));
    }

    /*
    void setImageAt(String name, float x, float y){
        layout.addView();
    }
    */
}
