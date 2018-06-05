package com.maohx2.kmhanko.plate;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.ina.Draw.Graphic;



/**
 * Created by user on 2018/02/04.
 */

public class BackPlate extends BoxTextPlate {
    public WorldModeAdmin worldModeAdmin;

    public BackPlate(Graphic _graphic, UserInterface _userInterFace, WorldModeAdmin _worldModeAdmin) {
        super(_graphic, _userInterFace,new Paint(),
                Constants.Touch.TouchWay.UP_MOMENT,
                Constants.Touch.TouchWay.MOVE,
                new int[] { 1200, 0, 1600, 100 },
                "戻る",
                new Paint()
                );
        worldModeAdmin = _worldModeAdmin;
        text_paint.setTextSize(70f);
        text_paint.setARGB(255,255,255,255);
    }

}
