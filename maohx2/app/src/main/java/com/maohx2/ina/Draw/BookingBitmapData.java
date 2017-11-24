package com.maohx2.ina.Draw;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by ina on 2017/10/13.
 */

public class BookingBitmapData extends BookingTaskData {

    BitmapData draw_bitmap_data;
    Matrix draw_matrix;

    BookingBitmapData(){
        super();
        draw_matrix = new Matrix();
    }

    public void update(BitmapData _draw_bitmap_data, Matrix _draw_matrix, Paint _paint){
        draw_bitmap_data = _draw_bitmap_data;
        draw_matrix = _draw_matrix;
        paint = _paint;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(draw_bitmap_data.getBitmap(), draw_matrix, paint);
    }

}



/*


    BitmapData bitmap_data;
    Matrix draw_matrix;
    int alpha;

    BookingBitmapData(){

        draw_matrix = new Matrix();

        draw_matrix.postScale(1, 1);
        draw_matrix.postRotate(0);
        draw_matrix.postTranslate(0, 0);

        alpha = 255;

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
*/
