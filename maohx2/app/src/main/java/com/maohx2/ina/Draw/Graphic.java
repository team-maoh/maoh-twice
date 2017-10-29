package com.maohx2.ina.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.SurfaceHolder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.AssetManager;

import com.maohx2.kmhanko.database.MyDatabase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Point;

import static com.maohx2.ina.Constants.Bitmap.*;


/**
 * Created by ryomasenda on 2017/09/03.
 */


//セットされた描画データ


public class Graphic {

    //定数
    final int LAYER_MAX = Layer.FRONT.ordinal() + 1;
    final int BITMAP_MAX = 128;
    int booking_num = 0;

    final int DISP_X;
    final int DISP_Y;
    final PointF NORMARIZED_DISP_RATE;

    final PointF DISP_NORMARIZED_RATE;

    Point disp_position;
    Point normarized_position;

    //ビットマップデータの配列(レイヤー番号*ビットマップ番号)
    BitmapData[][] bitmapDatas = new BitmapData[LAYER_MAX][BITMAP_MAX];
    int bitmapMax[] = new int[LAYER_MAX];

    Paint paint = new Paint();
    SurfaceHolder holder;

    BitmapDataAdmin global_bitmap_data_admin;
    BitmapDataAdmin local_bitmap_data_admin;
    List<BookingData> booking_datas = new ArrayList<BookingData>(BOOKING_DATA_INSTANCE);



    public Graphic(int disp_x, int disp_y, BitmapDataAdmin _global_bitmap_data_admin) {

        global_bitmap_data_admin = _global_bitmap_data_admin;

        DISP_X = disp_x;
        DISP_Y = disp_y;

        NORMARIZED_DISP_RATE = new PointF((float)DISP_X/1600, (float)DISP_Y/900);
        DISP_NORMARIZED_RATE = new PointF((float)1600/DISP_X, (float)900/DISP_Y);
    }


    public Graphic(int disp_x, int disp_y) {

        DISP_X = disp_x;
        DISP_Y = disp_y;

        NORMARIZED_DISP_RATE = new PointF((float)DISP_X/1600, (float)DISP_Y/900);
        DISP_NORMARIZED_RATE = new PointF((float)1600/DISP_X, (float)900/DISP_Y);
    }





    public void init(Context context, SurfaceHolder _holder, MyDatabase image_database){
        paint.setColor(Color.BLUE);
        holder = _holder;
        local_bitmap_data_admin = new BitmapDataAdmin();
        local_bitmap_data_admin.init(context, image_database);
        local_bitmap_data_admin.loadLocalImages();
        booking_num = 0;

        for(int i = 0; i < BOOKING_DATA_INSTANCE; i ++) {
            booking_datas.add(new BookingData());
        }

        disp_position = new Point(0,0);
        normarized_position = new Point(0,0);
    }


    public void draw() {
        Canvas canvas = null;
        canvas = holder.lockCanvas();

        if(canvas != null) {
            canvas.drawColor(Color.WHITE);

            for (int i = 0; i < booking_num; i++) {
                paint.setAlpha(booking_datas.get(i).getDrawAlpha());
                canvas.drawBitmap(booking_datas.get(i).getBitmapData().getBitmap(), booking_datas.get(i).getDrawMatrix(), paint);
            }

            holder.unlockCanvasAndPost(canvas);
        }

        booking_num = 0;
    }


    public  Point transrateNormalizedPositionToDispPosition(Point _normalize_position){

        disp_position.set((int)(_normalize_position.x * NORMARIZED_DISP_RATE.x), (int)(_normalize_position.y * NORMARIZED_DISP_RATE.y));

        return disp_position;

    }


    public  Point transrateDispPositionToNormalizedPosition(Point _disp_position){

        normarized_position.set((int)(_disp_position.x * NORMARIZED_DISP_RATE.x), (int)(_disp_position.y * NORMARIZED_DISP_RATE.y));

        return  normarized_position;
    }

/*
    public void drawBooking(String bitmap_name, Point draw_position){

        if(booking_num >= booking_datas.size()){
            booking_datas.add(new BookingData());
        }

        booking_datas.get(booking_num).setBitmap(bitmap_data_admin.getBitmapData(bitmap_name));
        booking_datas.get(booking_num).setDrawPosition(draw_position);
        booking_num++;
    }
*/

    public void drawBooking(String bitmap_name, Point position, int scale_x, int scale_y, int degree, int alpha, boolean is_upleft){

        //System.out.println(booking_num);

        if(booking_num >= booking_datas.size()){
            booking_datas.add(new BookingData());
        }

        int draw_bitmap_num = -1;
        BitmapData draw_bitmap_data = null;
        if(global_bitmap_data_admin != null){
            draw_bitmap_num = global_bitmap_data_admin.getBitmapDataNum(bitmap_name);

            if(draw_bitmap_num >= 0) {
                draw_bitmap_data = global_bitmap_data_admin.getBitmapData(draw_bitmap_num);
            }
        }


        if(draw_bitmap_num < 0 && local_bitmap_data_admin != null){
            draw_bitmap_num = local_bitmap_data_admin.getBitmapDataNum(bitmap_name);
            if(draw_bitmap_num >= 0) {
                draw_bitmap_data = local_bitmap_data_admin.getBitmapData(draw_bitmap_num);
            }
        }

        if(draw_bitmap_num < 0) {
            throw new Error("イナガキ：drawBooking:その名前の画像はありません     " + "global: " + global_bitmap_data_admin + "local:" + local_bitmap_data_admin);
        }

        Point draw_position = transrateNormalizedPositionToDispPosition(position);

        Matrix draw_matrix = new Matrix();
        if(is_upleft == true) {
            draw_matrix.postTranslate(-draw_bitmap_data.getBitmap().getWidth() / 2, -draw_bitmap_data.getBitmap().getHeight() / 2);
        }

        draw_matrix.postScale(scale_x, scale_y);
        draw_matrix.postRotate(degree);
        draw_matrix.postTranslate(draw_position.x, draw_position.y);

        booking_datas.get(booking_num).setBitmapData(draw_bitmap_data);
        booking_datas.get(booking_num).setDrawMatrix(draw_matrix);
        booking_datas.get(booking_num).setAlpha(alpha);
        booking_num++;

    }


    public void drawBooking(String bitmap_name, int position_x, int position_y, int scale_x, int scale_y, int degree, int alpha, boolean is_upleft){
        Point position = new Point(position_x, position_y);
        drawBooking(bitmap_name,position,scale_x,scale_y,degree,alpha,is_upleft);
    }


    public void drawBooking(String bitmap_name, Point position){
        drawBooking(bitmap_name,position,1,1,0,255,false);
    }

    public void drawBooking(String bitmap_name, int position_x, int position_y){
        drawBooking(bitmap_name, position_x, position_y,1,1,0,255,false);
    }

    public void setHolder(SurfaceHolder _holder){
        holder = _holder;
    }

    public SurfaceHolder getHolder(){
        return holder;
    }
}
