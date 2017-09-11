package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapUnit extends MapObject{


    int STEP;
    int dx, dy, dst_steps, now_steps;
    boolean moving;


    @Override
    public void init(SurfaceHolder _holder, Bitmap _draw_object) {
        super.init(_holder, _draw_object);

        dx = 0;//移動距離(differential x)
        dy = 0;
        STEP = 20;//歩幅( (dx)^2 + (dy)^2 = (STEP)^2 )
        dst_steps = 1;//今の目標地点までの歩数
        now_steps = 0;//前の目標地点にたどり着いてから今までに歩いた歩数
        moving = false;

    }


}
