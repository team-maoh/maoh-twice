package com.maohx2.fuusya;

import android.graphics.Bitmap;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

import static java.lang.Math.PI;

/**
 * Created by Fuusya on 2017/12/10.
 */

public class MapObjectBitmap {

    int total_dirs;//画像が1方位なのか、4方位なのか、8方位なのか
    Graphic graphic;
    String object_name;

    int DRAW_SCALE = 4;

    int unit_width, unit_height;

    int MAX_BITMAP_DIR = 8;//最大で8方位
    int MAX_FRAME = 3;//3枚の画像をループ表示する
    BitmapData bitmap_data[][] = new BitmapData[MAX_BITMAP_DIR][MAX_FRAME];
    int FRAME_QUICK_PERIODS = 5;//画像の更新周期(値が小さいほどfpsが上がる)
    int FRAME_SLOW_PERIODS = 10;
    int frame_periods;

    int time_count;//画像の更新周期を数える
    boolean is_increasing_frame;//画像番号が増えている最中 or 減っている最中
    int frame;//いま表示しているフレームの番号（０～２）
    BitmapData raw_bitmap_data;
    double shift_double = 1.0;

    public MapObjectBitmap(int _total_dirs, Graphic _graphic, String _object_name) {

        if (!(_total_dirs == 1 || _total_dirs == 4 || _total_dirs == 8)) {
            throw new Error("◆%☆フジワラ：MapObjectBitmap()に渡す[画像方位]がおかしい");
        }
        total_dirs = _total_dirs;
        graphic = _graphic;
        object_name = _object_name;

        raw_bitmap_data = graphic.searchBitmap(object_name);
        switch (total_dirs) {
            case 1:
                unit_width = raw_bitmap_data.getWidth();
                unit_height = raw_bitmap_data.getHeight();
                break;
            case 8:
                unit_width = raw_bitmap_data.getWidth() / 6;
                unit_height = raw_bitmap_data.getHeight() / 4;
                break;
            default:
                break;
        }

//        if(total_dirs == 1){
//            unit_width = raw_bitmap_data.getWidth();
//            unit_height = raw_bitmap_data.getHeight();
//        }
//        unit_width = raw_bitmap_data.getWidth() / 6;
//        unit_height = raw_bitmap_data.getHeight() / 4;

        time_count = 0;
        is_increasing_frame = true;
        frame = 0;

        switch (total_dirs) {
            case 1:
                break;
            case 8:
                storeEightBD(raw_bitmap_data);
                break;
            default:
                System.out.println("Bitmapの方位数（total_dirs）がおかしい");
        }

        frame_periods = FRAME_SLOW_PERIODS;

//        if (total_dirs == 8) {
//            storeEightBD(raw_bitmap_data);
//        }
    }

    public void init() {

    }

    public void init(double _shift_double) {
        shift_double = _shift_double;
    }

    public void update() {

        switch (total_dirs) {
            case 1:
                break;

            case 8:
                //フレームの番号
                time_count = (time_count + 1) % frame_periods;
                if (time_count == 0) {

                    if (is_increasing_frame == true) {
                        frame++;
                    } else {
                        frame--;
                    }

                    switch (frame) {
                        case 0:
                            is_increasing_frame = true;
                            break;
                        case 2:
                            is_increasing_frame = false;
                            break;
                        default:
                    }
                }

                break;

            default:

        }
    }

    // 引数のx, yは画面内の座標
    public void draw(double _dir_on_map, double x, double y) {
        draw(_dir_on_map, x, y, 255);
    }
    public void draw(double _dir_on_map, double x, double y, int alpha) {

        int shift_from_width = -unit_width * DRAW_SCALE / 2;
        int shift_from_height = -unit_height * DRAW_SCALE / 2;

        switch (total_dirs) {
            case 1:
//                graphic.bookingDrawBitmapData(raw_bitmap_data, (int) x + shift_from_width, (int) y + shift_from_height, DRAW_SCALE, DRAW_SCALE, 0, 255, true);
                graphic.bookingDrawBitmapData(raw_bitmap_data, (int) x, (int) y, DRAW_SCALE, DRAW_SCALE, 0, alpha, true);
                break;

            case 8:

                //マップ上でのオブジェクトの向き
                //[0 ~ 2*PI]を[0 ~ 7]に変換する
                int int_dir_on_map = ((int) ((_dir_on_map + PI / total_dirs) / (2 * PI / total_dirs))) % total_dirs;

                graphic.bookingDrawBitmapData(bitmap_data[int_dir_on_map][frame], (int) x + shift_from_width, (int) y + shift_from_height * (int) shift_double, DRAW_SCALE, DRAW_SCALE, 0, alpha, true);
                break;

            default:
                break;
        }

    }

    //1方位、時間的に変化しない
//    private void storeOneBD(BitmapData _raw_bitmap_data) {
//
//        bitmap_data[0][0] = graphic.processTrimmingBitmapData(_raw_bitmap_data, 0, 0, unit_width, unit_height);
//
//    }

    //8方位、時間的には3フレームの繰り返し
    private void storeEightBD(BitmapData _raw_bitmap_data) {

        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    store3Frames(2, 0, _raw_bitmap_data, i);//2行0列の画像をobject_bitmapに格納
                    break;
                case 1:
                    store3Frames(3, 1, _raw_bitmap_data, i);
                    break;
                case 2:
                    store3Frames(3, 0, _raw_bitmap_data, i);
                    break;
                case 3:
                    store3Frames(2, 1, _raw_bitmap_data, i);
                    break;
                case 4:
                    store3Frames(1, 0, _raw_bitmap_data, i);
                    break;
                case 5:
                    store3Frames(0, 1, _raw_bitmap_data, i);
                    break;
                case 6:
                    store3Frames(0, 0, _raw_bitmap_data, i);
                    break;
                case 7:
                    store3Frames(1, 1, _raw_bitmap_data, i);
                    break;
            }
        }
    }

    private void store3Frames(int row, int col, BitmapData _raw_bitmap_data, int _object_dir) {

        for (int i = 0; i < 3; i++) {
//            public BitmapData processTrimmingBitmapData(BitmapData src_bitmap_data, int x, int y, int width, int height){
            bitmap_data[_object_dir][i] = graphic.processTrimmingBitmapData(_raw_bitmap_data, col * (unit_width * 3) + i * unit_width, row * unit_height, unit_width, unit_height);
        }
    }

    public void makeFpsQuick() {
        frame_periods = FRAME_QUICK_PERIODS;
    }

    public void makeFpsSlow() {
        frame_periods = FRAME_SLOW_PERIODS;
    }

    public void setName(String _object_name) {
        object_name = _object_name;

        raw_bitmap_data = graphic.searchBitmap(object_name);
        switch (total_dirs) {
            case 1:
                unit_width = raw_bitmap_data.getWidth();
                unit_height = raw_bitmap_data.getHeight();
                break;
            case 8:
                unit_width = raw_bitmap_data.getWidth() / 6;
                unit_height = raw_bitmap_data.getHeight() / 4;
                break;
            default:
                break;
        }

    }

    public void release() {
        System.out.println("takanoRelease : MapObjectBitmap");
        /*
        for (int i = 0; i < bitmap_data.length; i++) {
            for (int j = 0; j < bitmap_data[i].length; i++) {
                if (bitmap_data[i][j] != null) {
                    bitmap_data[i][j].releaseBitmap();
                }
            }
        }
        bitmap_data = null;
        raw_bitmap_data = null;
        */
        object_name = null;
    }



//    private void storeEightBD(BitmapData _raw_bitmap_data) {
//
//        for (int i = 0; i < 8; i++) {
//            switch (i) {
//                case 0:
//                    store3Frames(2, 0, _raw_bitmap_data, i, 3);//2行0列の画像をobject_bitmapに格納
//                    break;
//                case 1:
//                    store3Frames(3, 1, _raw_bitmap_data, i, 3);
//                    break;
//                case 2:
//                    store3Frames(3, 0, _raw_bitmap_data, i, 3);
//                    break;
//                case 3:
//                    store3Frames(2, 1, _raw_bitmap_data, i, 3);
//                    break;
//                case 4:
//                    store3Frames(1, 0, _raw_bitmap_data, i, 3);
//                    break;
//                case 5:
//                    store3Frames(0, 1, _raw_bitmap_data, i, 3);
//                    break;
//                case 6:
//                    store3Frames(0, 0, _raw_bitmap_data, i, 3);
//                    break;
//                case 7:
//                    store3Frames(1, 1, _raw_bitmap_data, i, 3);
//                    break;
//            }
//        }
//
//    }
//
//    private void store3Frames(int row, int col, BitmapData _raw_bitmap_data, int _object_dir, int _frames) {
//        int frames = _frames / 3;
//
//        for (int i = 0; i < 3; i++) {
////        for (int i = 0; i < 1; i++) {
//            bitmap_data[_object_dir][i] = graphic.processTrimmingBitmapData(_raw_bitmap_data, col * (unit_width * frames) + i * unit_width, row * unit_height, unit_width, unit_height);
//        }
//    }
//
}
