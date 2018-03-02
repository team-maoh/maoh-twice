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

    int unit_width, unit_height;

    int MAX_BITMAP_DIR = 8;//最大で8方位
    int MAX_FRAME = 3;//3枚の画像をループ表示する
    BitmapData bitmap_data[][] = new BitmapData[MAX_BITMAP_DIR][MAX_FRAME];
    int FRAME_PERIODS = 5;//画像の更新周期(値が小さいほどfpsが上がる)

    int time_count;//画像の更新周期を数える
    boolean is_increasing_frame;
    int frame;//いま表示しているフレームの番号（０～２）

    public MapObjectBitmap(int _total_dirs, Graphic _graphic, String _object_name) {

        if (!(_total_dirs == 1 || _total_dirs == 4 || _total_dirs == 8)) {
            throw new Error("◆%☆フジワラ：MapObjectBitmap()に渡す[画像方位]がおかしい");
        }
        total_dirs = _total_dirs;
        graphic = _graphic;
        object_name = _object_name;

        BitmapData raw_bitmap_data = graphic.searchBitmap(object_name);
        unit_width = raw_bitmap_data.getWidth() / 6;
        unit_height = raw_bitmap_data.getHeight() / 4;

        time_count = 0;
        is_increasing_frame = true;
        frame = 0;

        if (total_dirs == 8) {
            storeEightBD(raw_bitmap_data);
        }
    }

    public void init() {

    }

    public void update() {

        if (total_dirs == 1) {


        } else {
            //フレームの番号
            time_count = (time_count + 1) % FRAME_PERIODS;
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
                        break;
                }
            }
        }
    }

    //_dir_on_map : 0 ~ 2*PI

    public void draw(double _dir_on_map, double x, double y) {

        //マップ上でのオブジェクトの向き
        //[0 ~ 2*PI]を[0 ~ 7]に変換する
        int int_dir_on_map = ((int) ((_dir_on_map + PI / total_dirs) / (2 * PI / total_dirs))) % total_dirs;

        graphic.bookingDrawBitmapData(bitmap_data[int_dir_on_map][frame], (int) x, (int) y);

    }

    private void storeEightBD(BitmapData _raw_bitmap_data) {

        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:
                    storeBD(2, 0, _raw_bitmap_data, i);//2行0列の画像をobject_bitmapに格納
                    break;
                case 1:
                    storeBD(3, 1, _raw_bitmap_data, i);
                    break;
                case 2:
                    storeBD(3, 0, _raw_bitmap_data, i);
                    break;
                case 3:
                    storeBD(2, 1, _raw_bitmap_data, i);
                    break;
                case 4:
                    storeBD(1, 0, _raw_bitmap_data, i);
                    break;
                case 5:
                    storeBD(0, 1, _raw_bitmap_data, i);
                    break;
                case 6:
                    storeBD(0, 0, _raw_bitmap_data, i);
                    break;
                case 7:
                    storeBD(1, 1, _raw_bitmap_data, i);
                    break;
            }
        }

    }

    private void storeBD(int row, int col, BitmapData _raw_bitmap_data, int _object_dir) {

        for (int i = 0; i < 3; i++) {
//            public BitmapData processTrimmingBitmapData(BitmapData src_bitmap_data, int x, int y, int width, int height){
            bitmap_data[_object_dir][i] = graphic.processTrimmingBitmapData(_raw_bitmap_data, col * (unit_width * 3) + i * unit_width, row * unit_height, unit_width, unit_height);

        }
    }
}
