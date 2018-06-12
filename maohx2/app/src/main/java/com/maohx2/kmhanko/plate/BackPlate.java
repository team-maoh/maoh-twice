package com.maohx2.kmhanko.plate;

import android.graphics.Paint;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.Text.BoxTextPlate;
import com.maohx2.ina.Text.BoxImagePlate;
import com.maohx2.ina.Text.CircleImagePlate;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.ina.WorldModeAdmin;
import com.maohx2.ina.Draw.Graphic;



/**
 * Created by user on 2018/02/04.
 */

/*
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
*/

public class BackPlate extends BoxImagePlate {
    public BackPlate(Graphic _graphic, UserInterface _userInterFace) {
        super(
                _graphic,
                _userInterFace, new Paint(),
                Constants.Touch.TouchWay.UP_MOMENT,
                Constants.Touch.TouchWay.MOVE,
                new int[]{1450, 50, 1550, 150},
                _graphic.makeImageContext(
                        _graphic.searchBitmap("backPlate01"),
                        1500, 100,
                        0.75f, 0.75f, 0,
                        255, false
                ),
                _graphic.makeImageContext(
                        _graphic.searchBitmap("backPlate01"),
                        1500, 100,
                        0.85f, 0.85f, 0,
                        255, false
                )
        );
    }

}