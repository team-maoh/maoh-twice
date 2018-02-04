package com.maohx2.ina.Draw;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by ina on 2017/10/13.
 */

public class BookingBitmapData extends BookingTaskData {

    private BitmapData draw_bitmap_data;
    private Matrix draw_matrix;

    BookingBitmapData(){
        super();
        draw_matrix = new Matrix();
    }

    public Matrix getDrawMatrix() {
        return draw_matrix;
    }

    public void update(BitmapData _draw_bitmap_data, Matrix _draw_matrix, Paint _paint){
        draw_bitmap_data = _draw_bitmap_data;
        draw_matrix.set(_draw_matrix);
        paint.set(_paint);
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(draw_bitmap_data.getBitmap(), draw_matrix, paint);
        //canvas.drawBitmap(draw_bitmap_data.getBitmap(),0,0,paint);
    }

}
