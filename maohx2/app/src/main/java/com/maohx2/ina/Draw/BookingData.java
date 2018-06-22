package com.maohx2.ina.Draw;

import android.graphics.Matrix;
import android.graphics.Point;

/**
 * Created by ina on 2017/10/13.
 */

public class BookingData {

    BitmapData bitmap_data;
    Matrix draw_matrix;
    int alpha;

    BookingData(){

        draw_matrix = new Matrix();

        draw_matrix.postScale(1, 1);
        draw_matrix.postRotate(0);
        draw_matrix.postTranslate(0, 0);

        alpha = 255;

    }

    BitmapData getBitmapData(){
        return bitmap_data;
    }

    void setBitmapData(BitmapData _bitmap_data){
        bitmap_data = _bitmap_data;
    }

    Matrix getDrawMatrix(){

        return draw_matrix;
    }

    void setDrawMatrix(Matrix _draw_matrix){

        draw_matrix.set(_draw_matrix);
    }

    int getDrawAlpha(){

        return alpha;
    }

    void setAlpha(int _alpha){

        alpha = _alpha;
    }


}
