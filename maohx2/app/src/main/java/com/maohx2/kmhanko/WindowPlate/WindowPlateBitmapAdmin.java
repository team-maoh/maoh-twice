package com.maohx2.kmhanko.WindowPlate;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2019/03/08.
 */

public class WindowPlateBitmapAdmin {
    static final int ELEMENT_NUM = 32;

    BitmapData[][][] windowElement;
    String[] windowElementName;
    int index = 0;

    public WindowPlateBitmapAdmin() {
        windowElement = new BitmapData[ELEMENT_NUM][3][3];
        windowElementName = new String[ELEMENT_NUM];
    }


    public BitmapData[][] addWindowElement(Graphic graphic, String windowImageName) {
        BitmapData[][] tempWindowElement;
        if ((tempWindowElement = searchWindowElement(windowImageName)) != null) {
            return tempWindowElement;
        }

        float windowImageWidth = (float)graphic.searchBitmap(windowImageName).getWidth();
        float windowImageHeight = (float)graphic.searchBitmap(windowImageName).getHeight();

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                windowElement[index][i][j] = graphic.processTrimmingBitmapData(
                        graphic.searchBitmap(windowImageName),
                        (int)((float)j*(float)windowImageWidth/3.0f),
                        (int)((float)i*(float)windowImageHeight/3.0f),
                        (int)((float)windowImageWidth/3.0f),
                        (int)((float)windowImageHeight/3.0f)
                );
            }
        }
        windowElementName[index] = windowImageName;

        tempWindowElement = windowElement[index];
        index++;

        return tempWindowElement;

    }

    public BitmapData[][] searchWindowElement(String windowImageName) {
        for(int i = 0; i < windowElementName.length; i++) {
            if (windowImageName.equals(windowElementName[i])) {
                return windowElement[i];
            }
        }
        return null;
    }

    public void release() {
        if (windowElementName != null) {
            for (int i = 0; i < windowElementName.length; i++) {
                if (windowElementName[i] != null) {
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 3; k++) {
                            windowElement[i][j][k] = null;
                        }
                    }
                }
            }
        }
        windowElement = null;
        windowElementName = null;
    }


}


