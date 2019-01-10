package com.maohx2.ina.Draw;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by ina on 2017/10/13.
 */

public class BookingBitmapData extends BookingTaskData {

    private BitmapData draw_bitmap_data;
    private Matrix draw_matrix;
    private Rect draw_rect;
    private boolean isRect;
    private boolean isSimple;
    private int draw_x;
    private int draw_y;

    BookingBitmapData(){
        super();
        draw_matrix = new Matrix();
        draw_rect = new Rect();
        isRect = true;
    }

    public Matrix getDrawMatrix() {
        return draw_matrix;
    }

    public void update(BitmapData _draw_bitmap_data, Rect _draw_rect, Paint _paint){
        draw_bitmap_data = _draw_bitmap_data;
        draw_rect.set(_draw_rect);
        paint.set(_paint);
        isRect = true;
        isSimple = false;
    }

    public void update(BitmapData _draw_bitmap_data, Matrix _draw_matrix, Paint _paint){
        draw_bitmap_data = _draw_bitmap_data;
        draw_matrix.set(_draw_matrix);
        paint.set(_paint);
        isRect = false;
        isSimple = false;
    }

    public void update(BitmapData _draw_bitmap_data, int _draw_x, int _draw_y, Paint _paint){
        draw_bitmap_data = _draw_bitmap_data;
        draw_x = _draw_x;
        draw_y = _draw_y;
        paint.set(_paint);
        isRect = false;
        isSimple = true;
    }

    @Override
    public void draw(Canvas canvas){

        if(isSimple == true){
            canvas.drawBitmap(draw_bitmap_data.getBitmap(), draw_x, draw_y,null);
        }else{
        if(isRect == true) {
            //TODO by kmhanko　なんかエラー吐くのでチェック
            if (canvas == null) {
                System.out.println("BookingBitMapData#draw MyERROR!!! ☆タカノ: canvas == null");
                return;
            }
            if (draw_bitmap_data == null) {
                System.out.println("BookingBitMapData#draw MyERROR!!! ☆タカノ: draw_bitmap_data == null");
                return;
            }
            canvas.drawBitmap(draw_bitmap_data.getBitmap(), new Rect(0,0, draw_bitmap_data.getWidth(),draw_bitmap_data.getHeight()), draw_rect,null);
            //canvas.drawBitmap(draw_bitmap_data.getBitmap(),draw_rect.left,draw_rect.top,paint);
            //canvas.drawBitmap(draw_bitmap_data.getBitmap(),0,0,paint);
        }else{
            canvas.drawBitmap(draw_bitmap_data.getBitmap(), draw_matrix, paint);
        }
        }
    }

    @Override
    public void release() {
        System.out.println("takanoRelease : BookingBitmapData");
        super.release();
        draw_bitmap_data = null;
        draw_matrix = null;
        draw_rect = null;
        isRect = false;
    }
}
