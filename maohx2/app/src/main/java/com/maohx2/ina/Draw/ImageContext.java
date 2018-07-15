package com.maohx2.ina.Draw;

import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by ina on 2018/01/21.
 */

public class ImageContext {

    private BitmapData bitmap_data;
    private Matrix matrix;
    private Paint paint;

    public ImageContext(BitmapData _bitmap_data, Matrix _matrix, Paint _paint){
        matrix = new Matrix();
        paint = new Paint();

        bitmap_data = _bitmap_data;
        matrix.set(_matrix);
        paint.set(_paint);
    }


    public BitmapData getBitmapData() {
        return bitmap_data;
    }

    public void setBitmapData(BitmapData _bitmap_data) {
        bitmap_data = _bitmap_data;
    }

    public Matrix getMatrix() {return matrix;}

    public void setMatrix(Matrix _matrix) {
        matrix = _matrix;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint _paint) {
        paint = _paint;
    }

    public void release() {
        System.out.println("takanoRelease : ImageContext");
    }

}
