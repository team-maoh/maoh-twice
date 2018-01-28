package com.maohx2.ina.Draw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.SurfaceHolder;

import com.maohx2.ina.Constants;
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

    final int DRAW_LEFT_END;
    final int DRAW_UP_END;
    final int DRAW_RIGHT_END;
    final int DRAW_DOWN_END;

    public final PointF NORMARIZED_DRAW_RATE;

    Activity activity;
    SurfaceHolder holder;

    BitmapDataAdmin global_bitmap_data_admin;
    BitmapDataAdmin local_bitmap_data_admin;

    int booking_circle_num;
    int booking_rect_num;
    int booking_text_num;
    int booking_bitmap_num;
    int booking_num;

    List<BookingTaskData> booking_circle_datas = new ArrayList<BookingTaskData>(BOOKING_DATA_INSTANCE);
    List<BookingTaskData> booking_rect_datas = new ArrayList<BookingTaskData>(BOOKING_DATA_INSTANCE);
    List<BookingTaskData> booking_text_datas = new ArrayList<BookingTaskData>(BOOKING_DATA_INSTANCE);
    List<BookingTaskData> booking_bitmap_datas = new ArrayList<BookingTaskData>(BOOKING_DATA_INSTANCE);

    List<BookingTaskData> booking_task_datas = new ArrayList<BookingTaskData>(4*BOOKING_DATA_INSTANCE);


    Paint draw_paint = new Paint();

    Point setting_point1;
    Point setting_point2;
    Matrix setting_matrix;

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

        DRAW_LEFT_END = global_constants.DRAW_LEFT_END;
        DRAW_UP_END = global_constants.DRAW_UP_END;
        DRAW_RIGHT_END = global_constants.DRAW_RIGHT_END;
        DRAW_DOWN_END = global_constants.DRAW_DOWN_END;

        NORMARIZED_DRAW_RATE = global_constants.NORMARIZED_DRAW_RATE;

        draw_paint.setColor(Color.BLUE);
        holder = _holder;
        local_bitmap_data_admin = new BitmapDataAdmin();
        local_bitmap_data_admin.init(activity);


        setting_point1 = new Point();
        setting_point2 = new Point();
        setting_matrix = new Matrix();

        booking_circle_num = 0;
        booking_rect_num = 0;
        booking_text_num = 0;
        booking_bitmap_num = 0;
        booking_num = 0;

        for(int i = 0; i < BOOKING_DATA_INSTANCE; i ++) {
            booking_circle_datas.add(new BookingCircleData());
            booking_rect_datas.add(new BookingRectData());
            booking_text_datas.add(new BookingTextData());
            booking_bitmap_datas.add(new BookingBitmapData());
        }

        for(int i = 0; i < 4*BOOKING_DATA_INSTANCE; i ++) {
            booking_task_datas.add(new BookingBitmapData());
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

            for (int i = 0; i < booking_num; i++) {
                booking_task_datas.get(i).draw(canvas);
            }

            draw_paint.setColor(Color.GREEN);
            canvas.drawRect(0,0,DRAW_RIGHT_END,DRAW_UP_END,draw_paint);
            canvas.drawRect(DISP_X,0,DRAW_RIGHT_END,DRAW_DOWN_END,draw_paint);
            canvas.drawRect(DISP_X,DISP_Y,DRAW_LEFT_END,DRAW_DOWN_END,draw_paint);
            canvas.drawRect(0,DISP_Y,DRAW_LEFT_END,DRAW_UP_END,draw_paint);

            holder.unlockCanvasAndPost(canvas);
        }

        booking_circle_num = 0;
        booking_rect_num = 0;
        booking_text_num = 0;
        booking_bitmap_num = 0;
        booking_num = 0;
    }

    public  void transrateNormalizedPositionToDispPosition(Point _normalize_position){
        _normalize_position.set((int)(_normalize_position.x*NORMARIZED_DRAW_RATE.x + DRAW_LEFT_END), (int)(_normalize_position.y*NORMARIZED_DRAW_RATE.y + DRAW_UP_END));
    }

    public double transrateNormalizedPositionToDispPositionX(int normalized_x){
        return normalized_x*NORMARIZED_DRAW_RATE.x + DRAW_LEFT_END;
    }

    public double transrateNormalizedPositionToDispPositionY(int normalized_y){
        return normalized_y*NORMARIZED_DRAW_RATE.y + DRAW_UP_END;
    }


    public Point transrateDispPositionToNormalizedPosition(Point _disp_position){
        return  new Point((int)((_disp_position.x + DRAW_LEFT_END)* DISP_NORMARIZED_RATE.x), (int)((_disp_position.y + DRAW_UP_END) * DISP_NORMARIZED_RATE.y));
    }

    public int transrateDispPositionToNormalizedPositionX(int disp_x){
        return (int)((disp_x - DRAW_LEFT_END)/NORMARIZED_DRAW_RATE.x);
        //return (int)((disp_x + DRAW_LEFT_END)* DISP_NORMARIZED_RATE.x);
    }

    public int transrateDispPositionToNormalizedPositionY(int disp_y){
        return (int)((disp_y - DRAW_UP_END)/NORMARIZED_DRAW_RATE.y);
        //return  (int)((disp_y + DRAW_UP_END) * DISP_NORMARIZED_RATE.y);
    }

    public void bookingDrawBitmapData(ImageContext _image_context){

        if(booking_bitmap_num >= booking_bitmap_datas.size()){
            booking_bitmap_datas.add(new BookingBitmapData());
        }

        ((BookingBitmapData)booking_bitmap_datas.get(booking_bitmap_num)).update(_image_context.getBitmapData(), _image_context.getMatrix(), _image_context.getPaint());

        if(booking_num >= booking_task_datas.size()) {
            booking_task_datas.add(new BookingBitmapData());
        }

        booking_task_datas.set(booking_num, booking_bitmap_datas.get(booking_bitmap_num));
        booking_num++;
        booking_bitmap_num++;

    }


    public void bookingDrawBitmapData(BitmapData draw_bitmap_data, Point position, float scale_x, float scale_y, float degree, int alpha, boolean is_upleft){

        setting_point1.set(position.x, position.y);
        transrateNormalizedPositionToDispPosition(setting_point1);

        setting_matrix.reset();

        if(is_upleft == false) {
            setting_matrix.postTranslate(-draw_bitmap_data.getBitmap().getWidth() / 2, -draw_bitmap_data.getBitmap().getHeight() / 2);
        }

        setting_matrix.postScale(DENSITY*scale_x, DENSITY*scale_y);
        setting_matrix.postRotate(degree);
        setting_matrix.postTranslate(setting_point1.x, setting_point1.y);

        //行列とビットマップデータの保存
        draw_paint.setAlpha(alpha);

        if(booking_bitmap_num >= booking_bitmap_datas.size()){
            booking_bitmap_datas.add(new BookingBitmapData());
        }

        ((BookingBitmapData)booking_bitmap_datas.get(booking_bitmap_num)).update(draw_bitmap_data, setting_matrix, draw_paint);

        if(booking_num >= booking_task_datas.size()) {
            booking_task_datas.add(new BookingBitmapData());
        }

        booking_task_datas.set(booking_num, booking_bitmap_datas.get(booking_bitmap_num));
        booking_num++;
        booking_bitmap_num++;

    }



    public void bookingDrawBitmapData(BitmapData bitmap_data, int position_x, int position_y, float scale_x, float scale_y, float degree, int alpha, boolean is_upleft){
        Point position = new Point(position_x, position_y);
        bookingDrawBitmapData(bitmap_data,position,scale_x,scale_y,degree,alpha,is_upleft);
    }

    public void bookingDrawBitmapData(BitmapData bitmap_data, Point position){
        bookingDrawBitmapData(bitmap_data,position,1,1,0,255,false);
    }

    public void bookingDrawBitmapData(BitmapData bitmap_data, int position_x, int position_y){
        bookingDrawBitmapData(bitmap_data, position_x, position_y,1,1,0,255,false);
    }

    public void bookingDrawBitmapName(String bitmap_name, int position_x, int position_y, float scale_x, float scale_y, float degree, int alpha, boolean is_upleft){
        Point position = new Point(position_x, position_y);
        bookingDrawBitmapData(searchBitmap(bitmap_name),position,scale_x,scale_y,degree,alpha,is_upleft);
    }

    public void bookingDrawBitmapName(String bitmap_name, Point position){
        bookingDrawBitmapData(searchBitmap(bitmap_name),position,1,1,0,255,false);
    }

    public void bookingDrawBitmapName(String bitmap_name, int position_x, int position_y){
        bookingDrawBitmapData(searchBitmap(bitmap_name), position_x, position_y,1,1,0,255,false);
    }

    public void bookingDrawCircle(int draw_x, int draw_y, int draw_radius){
        draw_paint.setARGB(100,255,0,0);
        bookingDrawCircle(draw_x, draw_y, draw_radius, draw_paint);
    }

    public void bookingDrawCircle(int draw_x, int draw_y, int draw_radius, Paint paint){

        setting_point1.set(draw_x, draw_y);
        transrateNormalizedPositionToDispPosition(setting_point1);

        int disp_radius = (int)(DENSITY * draw_radius);

        if(booking_circle_num >= booking_circle_datas.size()){
            booking_circle_datas.add(new BookingCircleData());
        }

        ((BookingCircleData)booking_circle_datas.get(booking_circle_num)).update(setting_point1.x, setting_point1.y, disp_radius, paint);

        if(booking_num >= booking_task_datas.size()) {
            booking_task_datas.add(new BookingBitmapData());
        }

        booking_task_datas.set(booking_num, booking_circle_datas.get(booking_circle_num));
        booking_num++;
        booking_circle_num++;
    }

    public void bookingDrawRect(int draw_left, int draw_up, int draw_right, int draw_down){
        draw_paint.setARGB(100,255,0,0);
        bookingDrawRect(draw_left, draw_up, draw_right, draw_down, draw_paint);
    }


    public void bookingDrawRect(int draw_left, int draw_up, int draw_right, int draw_down, Paint paint){

        setting_point1.set(draw_left, draw_up);
        transrateNormalizedPositionToDispPosition(setting_point1);

        setting_point2.set(draw_right, draw_down);
        transrateNormalizedPositionToDispPosition(setting_point2);

        if(booking_rect_num >= booking_rect_datas.size()){
            booking_rect_datas.add(new BookingRectData());
        }

        ((BookingRectData)booking_rect_datas.get(booking_rect_num)).update(setting_point1.x, setting_point1.y, setting_point2.x, setting_point2.y, paint);


        if(booking_num >= booking_task_datas.size()) {
            booking_task_datas.add(new BookingBitmapData());
        }

        booking_task_datas.set(booking_num, booking_rect_datas.get(booking_rect_num));
        booking_num++;
        booking_rect_num++;
    }

    public void bookingDrawText(String draw_string, int draw_left, int draw_down){

        draw_paint.setARGB(255,100,0,0);
        draw_paint.setTextSize(30);
        bookingDrawText(draw_string, draw_left, draw_down, draw_paint);
    }

    public void bookingDrawText(String draw_string, int draw_left, int draw_down, Paint paint){

        setting_point1.set(draw_left, draw_down);
        transrateNormalizedPositionToDispPosition(setting_point1);

        draw_paint.set(paint);
        draw_paint.setTextSize(DENSITY*paint.getTextSize());

        if(booking_text_num >= booking_text_datas.size()){
            booking_text_datas.add(new BookingTextData());
            //System.out.println("add" +booking_rect_num);
        }

        ((BookingTextData)booking_text_datas.get(booking_text_num)).update(draw_string, setting_point1.x, setting_point1.y, draw_paint);

        if(booking_num >= booking_task_datas.size()) {
            booking_task_datas.add(new BookingBitmapData());
        }

        booking_task_datas.set(booking_num, booking_text_datas.get(booking_text_num));
        booking_num++;
        booking_text_num++;
    }

    public BitmapData searchBitmap(String bitmap_name){

        //画像の検索
        int draw_bitmap_num = -1;
        BitmapData hit_bitmap_data = null;
        if(global_bitmap_data_admin != null){
            draw_bitmap_num = global_bitmap_data_admin.getBitmapDataNum(bitmap_name);

            if(draw_bitmap_num >= 0) {
                hit_bitmap_data = global_bitmap_data_admin.getBitmapData(draw_bitmap_num);
            }
        }


        if(draw_bitmap_num < 0 && local_bitmap_data_admin != null){
            draw_bitmap_num = local_bitmap_data_admin.getBitmapDataNum(bitmap_name);
            if(draw_bitmap_num >= 0) {
                hit_bitmap_data = local_bitmap_data_admin.getBitmapData(draw_bitmap_num);
            }
        }

        if(draw_bitmap_num < 0) {
            throw new Error("%☆イナガキ：drawBooking:画像名「" + bitmap_name + "」がありません     " + "global: " + global_bitmap_data_admin + "local:" + local_bitmap_data_admin);
        }

        //todo:やばい
        //setting_matrix.reset();
        //setting_matrix.postScale(DENSITY, DENSITY);

        return hit_bitmap_data;
    }


    public ImageContext makeImageContext(BitmapData draw_bitmap_data, Point position, float scale_x, float scale_y, float degree, int alpha, boolean is_upleft) {

        setting_point1.set(position.x, position.y);
        transrateNormalizedPositionToDispPosition(setting_point1);

        setting_matrix.reset();

        if (is_upleft == false) {
            setting_matrix.postTranslate(-draw_bitmap_data.getBitmap().getWidth() / 2, -draw_bitmap_data.getBitmap().getHeight() / 2);
        }

        setting_matrix.postScale(DENSITY * scale_x, DENSITY * scale_y);
        setting_matrix.postRotate(degree);
        setting_matrix.postTranslate(setting_point1.x, setting_point1.y);

        //行列とビットマップデータの保存
        draw_paint.setAlpha(alpha);

        ImageContext image_context = new ImageContext(draw_bitmap_data, setting_matrix, draw_paint);

        return image_context;
    }

    public ImageContext makeImageContext(BitmapData draw_bitmap_data, int position_x, int position_y , float scale_x, float scale_y, float degree, int alpha, boolean is_upleft){

        Point position = new Point(position_x, position_y);
        return makeImageContext(draw_bitmap_data, position, scale_x, scale_y, degree, alpha, is_upleft);
    }

    public ImageContext makeImageContext(BitmapData draw_bitmap_data, int position_x, int position_y){

        return makeImageContext(draw_bitmap_data, position_x, position_y, 1, 1, 0, 255, false);
    }

    public ImageContext makeImageContext(BitmapData draw_bitmap_data, int position_x, int position_y, boolean is_upleft){

        return makeImageContext(draw_bitmap_data, position_x, position_y, 1, 1, 0, 255, is_upleft);
    }

    /*
    public BitmapData processBitmapData(BitmapData src_bitmap_data, float scale_x, float scale_y, int alpha){

        setting_matrix.reset();
        //setting_matrix.postTranslate(-src_bitmap_data.getBitmap().getWidth() / 2, -src_bitmap_data.getBitmap().getHeight() / 2);
        setting_matrix.postScale(scale_x, scale_y);
        //setting_matrix.postTranslate((int)(src_bitmap_data.getBitmap().getWidth() * scale_x / 2), (int)(src_bitmap_data.getBitmap().getHeight() * scale_y / 2));

        //行列とビットマップデータの保存
        draw_paint.setAlpha(alpha);
        Bitmap dst_bitmap;

        //dst_bitmap = Bitmap.createBitmap((int) (src_bitmap_data.getWidth() * scale_x * 1.5), (int) (src_bitmap_data.getHeight() * scale_y * 1.5), Bitmap.Config.ARGB_8888);

        dst_bitmap = Bitmap.createBitmap((int) (src_bitmap_data.getWidth() * scale_x), (int) (src_bitmap_data.getHeight() * scale_y), Bitmap.Config.ARGB_8888);

        Canvas process_canvas = new Canvas(dst_bitmap);
        process_canvas.drawBitmap(src_bitmap_data.getBitmap(), setting_matrix, draw_paint);

        BitmapData dst_bitmap_data = new BitmapData();
        dst_bitmap_data.setBitmap(dst_bitmap);

        return dst_bitmap_data;

    }
*/
    public BitmapData processTrimmingBitmapData(BitmapData src_bitmap_data, int x, int y, int width, int height){

        BitmapData dst_bitmap_data;
        dst_bitmap_data = new BitmapData();

        dst_bitmap_data.setBitmap(Bitmap.createBitmap(src_bitmap_data.getBitmap(),x,y,width,height));

        return dst_bitmap_data;
    }

    public BitmapData processCombineBitmapData(BitmapData[] src_bitmap_datas, boolean yoko){

        BitmapData dst_bitmap_data;
        dst_bitmap_data = new BitmapData();
        int total_length = 0;
        int variable_length[] = new int[src_bitmap_datas.length];
        int constant_length = 0;
        int[][] source_pixels = new int[src_bitmap_datas.length][];

        for(int i = 0; i < src_bitmap_datas.length; i++){

            if(yoko == true) {
                variable_length[i] = src_bitmap_datas[i].getWidth();
                total_length += src_bitmap_datas[i].getWidth();
                constant_length = src_bitmap_datas[i].getHeight();
                source_pixels[i] = new int[variable_length[i]*constant_length];
                src_bitmap_datas[i].getBitmap().getPixels(source_pixels[i], 0, variable_length[i], 0, 0, variable_length[i], constant_length);
            } else {
                variable_length[i] = src_bitmap_datas[i].getHeight();
                total_length += src_bitmap_datas[i].getHeight();
                constant_length = src_bitmap_datas[i].getWidth();
                source_pixels[i] = new int[variable_length[i]*constant_length];
                src_bitmap_datas[i].getBitmap().getPixels(source_pixels[i], 0, constant_length, 0, 0, constant_length, variable_length[i]);
            }
        }

        int[] conbine_pixels;

        if(yoko == true) {
            conbine_pixels = new int[src_bitmap_datas[0].getHeight()*total_length];
        }else {
            conbine_pixels = new int[src_bitmap_datas[0].getWidth()*total_length];
        }

        int init_copy_index = 0;

        for(int i = 0; i < src_bitmap_datas.length; i++){
            if(yoko == true) {
                for(int y = 0; y < constant_length; y++) {
                    for(int x = 0; x < variable_length[i]; x++) {
                        int index = ((x + y * variable_length[i]) + (total_length*y));
                        //System.out.println("index = " + index);
                        conbine_pixels[x + (total_length*y + init_copy_index)] = source_pixels[i][x + y * variable_length[i]];
                    }
                }
            } else {
                for(int y = 0; y < variable_length[i]; y++) {
                    for(int x = 0; x < constant_length; x++) {
                        conbine_pixels[(x + y * constant_length) + (init_copy_index * constant_length)] = source_pixels[i][x + y * constant_length];
                    }
                }
            }
            init_copy_index += variable_length[i];
        }

        Bitmap conbine_bitmap;

        if(yoko == true) {
            conbine_bitmap = Bitmap.createBitmap(total_length, constant_length, Bitmap.Config.ARGB_8888);
            conbine_bitmap.setPixels(conbine_pixels, 0, total_length, 0, 0, total_length, constant_length);
        }else {
            conbine_bitmap = Bitmap.createBitmap(constant_length, total_length, Bitmap.Config.ARGB_8888);
            conbine_bitmap.setPixels(conbine_pixels, 0, constant_length, 0, 0, constant_length, total_length);
        }

        dst_bitmap_data.setBitmap(conbine_bitmap);

        return dst_bitmap_data;
    }

    public void setHolder(SurfaceHolder _holder){
        holder = _holder;
    }

    public SurfaceHolder getHolder(){return holder;}
}