package com.maohx2.ina.Draw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.SurfaceHolder;

import com.maohx2.ina.GlobalConstants;
import com.maohx2.ina.GlobalData;
import com.maohx2.kmhanko.database.MyDatabase;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Point;

import static com.maohx2.ina.Constants.Bitmap.*;

//定数
//final int LAYER_MAX = Layer.FRONT.ordinal() + 1;
//final int BITMAP_MAX = 128;

//ビットマップデータの配列(レイヤー番号*ビットマップ番号)
//BitmapData[][] bitmapDatas = new BitmapData[LAYER_MAX][BITMAP_MAX];
//int bitmapMax[] = new int[LAYER_MAX];





//描画の管理方法、自身がなんの予約なのかを示したものだけを共通部分として持つプリモフィズム←これで行く
//booking_num→booking_kind[booking_num] = rect などとして、それぞれのクラスを描画

public class Graphic {

    final int DISP_X;
    final int DISP_Y;
    final PointF NORMARIZED_DISP_RATE;
    final PointF DISP_NORMARIZED_RATE;
    final float DENSITY;

    Activity activity;
    SurfaceHolder holder;

    BitmapDataAdmin global_bitmap_data_admin;
    BitmapDataAdmin local_bitmap_data_admin;

    int booking_circle_num;
    int booking_rect_num;
    int booking_text_num;
    int booking_bitmap_num;

    List<BookingTaskData> booking_circle_datas = new ArrayList<BookingTaskData>(BOOKING_DATA_INSTANCE);
    List<BookingTaskData> booking_rect_datas = new ArrayList<BookingTaskData>(BOOKING_DATA_INSTANCE);
    List<BookingTaskData> booking_text_datas = new ArrayList<BookingTaskData>(BOOKING_DATA_INSTANCE);
    List<BookingTaskData> booking_bitmap_datas = new ArrayList<BookingTaskData>(BOOKING_DATA_INSTANCE);

    List<BookingTaskData> booking_task_datas = new ArrayList<BookingTaskData>(4*BOOKING_DATA_INSTANCE);


    Paint draw_paint = new Paint();

    public Graphic(Activity _activity, SurfaceHolder _holder) {

        activity = _activity;

        GlobalData global_data = (GlobalData)activity.getApplication();
        global_bitmap_data_admin = global_data.getGlobalBitmapDataAdmin();


        GlobalConstants global_constants = global_data.getGlobalConstants();

        DISP_X = global_constants.DISP_X;
        DISP_Y = global_constants.DISP_Y;

        NORMARIZED_DISP_RATE = global_constants.NORMARIZED_DISP_RATE;
        DISP_NORMARIZED_RATE = global_constants.DISP_NORMARIZED_RATE;

        DENSITY = global_constants.DENSITY;

        draw_paint.setColor(Color.BLUE);
        holder = _holder;
        local_bitmap_data_admin = new BitmapDataAdmin();
        local_bitmap_data_admin.init(activity);

        booking_circle_num = 0;
        booking_rect_num = 0;
        booking_text_num = 0;
        booking_bitmap_num = 0;

        for(int i = 0; i < BOOKING_DATA_INSTANCE; i ++) {
            booking_circle_datas.add(new BookingCircleData());
            booking_rect_datas.add(new BookingRectData());
            booking_text_datas.add(new BookingTextData());
            booking_bitmap_datas.add(new BookingBitmapData());
        }
    }


    public void init(){}



    public void loadLocalImages(MyDatabase image_database, String table_folder){
        local_bitmap_data_admin.loadLocalImages(image_database, table_folder);
    }


    public void draw() {
        Canvas canvas = null;
        canvas = holder.lockCanvas();
        if(canvas != null) {
            canvas.drawColor(Color.WHITE);

            int booking_num = booking_circle_num + booking_rect_num + booking_text_num + booking_bitmap_num;

            for (int i = 0; i < booking_num; i++) {
                booking_task_datas.get(i).draw(canvas);
                //paint.setAlpha(booking_datas.get(i).getDrawAlpha());
                //canvas.drawBitmap(booking_datas.get(i).getBitmapData().getBitmap(), booking_datas.get(i).getDrawMatrix(), paint);
            }

            holder.unlockCanvasAndPost(canvas);
        }
        booking_circle_num = 0;
        booking_rect_num = 0;
        booking_text_num = 0;
        booking_bitmap_num = 0;
        booking_task_datas.clear();
    }


    public  Point transrateNormalizedPositionToDispPosition(Point _normalize_position){
        return new Point((int)(_normalize_position.x * NORMARIZED_DISP_RATE.x), (int)(_normalize_position.y * NORMARIZED_DISP_RATE.y));
    }


    public  Point transrateDispPositionToNormalizedPosition(Point _disp_position){
        return  new Point((int)(_disp_position.x * DISP_NORMARIZED_RATE.x), (int)(_disp_position.y * DISP_NORMARIZED_RATE.y));
    }


    public void bookingDrawBitmap(String bitmap_name, Point position, float scale_x, float scale_y, float degree, int alpha, boolean is_upleft){

        //System.out.println(booking_num);


        if(booking_bitmap_num >= booking_bitmap_datas.size()){
            booking_bitmap_datas.add(new BookingBitmapData());
        }

        //画像の検索
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
            throw new Error("%☆イナガキ：drawBooking:その名前の画像はありません     " + "global: " + global_bitmap_data_admin + "local:" + local_bitmap_data_admin);
        }


        //描画行列の定義
        Point draw_position = transrateNormalizedPositionToDispPosition(position);
        Matrix draw_matrix = new Matrix();
        if(is_upleft == false) {
            draw_matrix.postTranslate(-draw_bitmap_data.getBitmap().getWidth() / 2, -draw_bitmap_data.getBitmap().getHeight() / 2);
        }
        draw_matrix.postScale(DENSITY*scale_x, DENSITY*scale_y);
        draw_matrix.postRotate(degree);
        draw_matrix.postTranslate(draw_position.x, draw_position.y);

        /*
        booking_datas.get(booking_num).setBitmapData(draw_bitmap_data);
        booking_datas.get(booking_num).setDrawMatrix(draw_matrix);
        booking_datas.get(booking_num).setAlpha(alpha);
        */

        //行列とビットマップデータの保存
        draw_paint.setAlpha(alpha);
        ((BookingBitmapData)booking_bitmap_datas.get(booking_bitmap_num)).update(draw_bitmap_data, draw_matrix, draw_paint);
        booking_task_datas.add(booking_bitmap_datas.get(booking_bitmap_num));
        booking_bitmap_num++;
    }


    public void bookingDrawBitmap(String bitmap_name, int position_x, int position_y, float scale_x, float scale_y, float degree, int alpha, boolean is_upleft){
        Point position = new Point(position_x, position_y);
        bookingDrawBitmap(bitmap_name,position,scale_x,scale_y,degree,alpha,is_upleft);
    }

    public void bookingDrawBitmap(String bitmap_name, Point position){
        bookingDrawBitmap(bitmap_name,position,1,1,0,255,false);
    }

    public void bookingDrawBitmap(String bitmap_name, int position_x, int position_y){
        bookingDrawBitmap(bitmap_name, position_x, position_y,1,1,0,255,false);
    }

    public void bookingDrawCircle(int draw_x, int draw_y, int draw_radius, Paint paint){

        Point position = new Point(draw_x, draw_y);
        Point draw_position = transrateNormalizedPositionToDispPosition(position);
        int disp_radius = (int)(DENSITY * draw_radius);

        if(booking_circle_num >= booking_circle_datas.size()){
            booking_circle_datas.add(new BookingCircleData());
        }

        ((BookingCircleData)booking_circle_datas.get(booking_circle_num)).update(draw_position.x, draw_position.y, disp_radius, paint);
        booking_task_datas.add(booking_circle_datas.get(booking_circle_num));
        booking_circle_num++;
    }

    public void bookingDrawRect(int draw_left, int draw_up, int draw_right, int draw_down, Paint paint){

        Point position_up_left = new Point(draw_left, draw_up);
        Point position_down_right = new Point(draw_right, draw_down);

        Point draw_position_up_left = transrateNormalizedPositionToDispPosition(position_up_left);
        Point draw_position_down_right = transrateNormalizedPositionToDispPosition(position_down_right);

        if(booking_rect_num >= booking_rect_datas.size()){
            booking_rect_datas.add(new BookingRectData());
        }

        ((BookingRectData)booking_rect_datas.get(booking_rect_num)).update(draw_position_up_left.x, draw_position_up_left.y, draw_position_down_right.x, draw_position_down_right.y, paint);
        booking_task_datas.add(booking_rect_datas.get(booking_rect_num));
        booking_rect_num++;
    }

    public void bookingDrawText(String draw_string, int draw_left, int draw_down, Paint paint){

        Point position = new Point(draw_left, draw_down);
        Point draw_position = transrateNormalizedPositionToDispPosition(position);

        draw_paint.setTextSize(DENSITY*paint.getTextSize());

        if(booking_rect_num >= booking_rect_datas.size()){
            booking_rect_datas.add(new BookingRectData());
        }

        ((BookingTextData)booking_text_datas.get(booking_text_num)).update(draw_string, draw_position.x, draw_position.y, paint);
        booking_task_datas.add(booking_text_datas.get(booking_text_num));
        booking_text_num++;
    }

    public void setHolder(SurfaceHolder _holder){
        holder = _holder;
    }

    public SurfaceHolder getHolder(){return holder;}

}
