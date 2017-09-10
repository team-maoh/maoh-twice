package com.example.ina.maohx2;

import android.graphics.Bitmap;
import android.view.SurfaceHolder;

/**
 * Created by ina on 2017/09/05.
 */

public class GameSystem {

    UnitAdmin unit_admin;

    public void Init(SurfaceHolder m_holder, Bitmap m_neco) {

        unit_admin = new UnitAdmin();
        unit_admin.Init(m_holder, m_neco);


    }

    public void Update(double touch_x, double touch_y, int touch_state) {

        unit_admin.Update(touch_x, touch_y, touch_state);

    }

    public void Draw(double touch_x, double touch_y, int touch_state) {

        unit_admin.Draw(touch_x, touch_y, touch_state);

    }

    public GameSystem(){}
}
