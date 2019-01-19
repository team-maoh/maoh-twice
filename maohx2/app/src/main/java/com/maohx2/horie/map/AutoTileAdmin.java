package com.maohx2.horie.map;

import android.graphics.Color;
import android.graphics.Paint;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by horie on 2017/12/15.
 */

public class AutoTileAdmin {
    BitmapData auto_tile_parts[][];
    Graphic graphic;
    BitmapData test, test2, test3;

    public AutoTileAdmin(Graphic m_graphic){
        graphic = m_graphic;
    }

    public BitmapData combineFourAutoTile(BitmapData left_up, BitmapData right_up, BitmapData left_down, BitmapData right_down){
        BitmapData up_data[], down_data[], up_down_data[];
        up_data = new BitmapData[2];
        down_data = new BitmapData[2];
        up_down_data = new BitmapData[2];
        up_data[0] = left_up;
        up_data[1] = right_up;
        down_data[0] = left_down;
        down_data[1] = right_down;
        BitmapData up, down, dst;
        up = graphic.processCombineBitmapData(up_data, true);
        //test2 = up;
        down = graphic.processCombineBitmapData(down_data, true);
        //test3 = down;
        up_down_data[0] = up;
        up_down_data[1] = down;
        dst = graphic.processCombineBitmapData(up_down_data, false);
        return dst;
    }

    public void createAutoTile(AutoTile source_auto_tile){
        auto_tile_parts = new BitmapData[5][4];
        for(int i = 0;i < 5;i++){
            for(int j = 0;j < 4;j++){
                if(j <= 1) {
                    auto_tile_parts[i][j] = graphic.processTrimmingBitmapData(source_auto_tile.raw_auto_tile[i], 16 * (j % 2), 0, 16, 16);
                }
                else{
                    auto_tile_parts[i][j] = graphic.processTrimmingBitmapData(source_auto_tile.raw_auto_tile[i], 16 * (j % 2), 16, 16, 16);
                }
            }
        }
        source_auto_tile.auto_tile[0] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[4][1], auto_tile_parts[4][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[1] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[4][1], auto_tile_parts[4][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[2] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[3][1], auto_tile_parts[4][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[3] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[4][1], auto_tile_parts[4][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[4] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[4][1], auto_tile_parts[3][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[5] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[3][1], auto_tile_parts[4][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[6] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[3][1], auto_tile_parts[4][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[7] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[4][1], auto_tile_parts[3][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[8] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[4][1], auto_tile_parts[3][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[9] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[4][1], auto_tile_parts[4][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[10] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[3][1], auto_tile_parts[3][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[11] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[3][1], auto_tile_parts[3][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[12] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[4][1], auto_tile_parts[3][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[13] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[3][1], auto_tile_parts[3][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[14] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[3][1], auto_tile_parts[4][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[15] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[3][1], auto_tile_parts[3][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[16] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[4][1], auto_tile_parts[1][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[17] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[3][1], auto_tile_parts[1][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[18] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[4][1], auto_tile_parts[1][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[19] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[3][1], auto_tile_parts[1][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[20] = combineFourAutoTile(auto_tile_parts[2][0], auto_tile_parts[2][1], auto_tile_parts[4][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[21] = combineFourAutoTile(auto_tile_parts[2][0], auto_tile_parts[2][1], auto_tile_parts[4][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[22] = combineFourAutoTile(auto_tile_parts[2][0], auto_tile_parts[2][1], auto_tile_parts[3][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[23] = combineFourAutoTile(auto_tile_parts[2][0], auto_tile_parts[2][1], auto_tile_parts[3][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[24] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[1][1], auto_tile_parts[4][2], auto_tile_parts[1][3]);
        source_auto_tile.auto_tile[25] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[1][1], auto_tile_parts[3][2], auto_tile_parts[1][3]);
        source_auto_tile.auto_tile[26] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[1][1], auto_tile_parts[4][2], auto_tile_parts[1][3]);
        source_auto_tile.auto_tile[27] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[1][1], auto_tile_parts[3][2], auto_tile_parts[1][3]);
        source_auto_tile.auto_tile[28] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[4][1], auto_tile_parts[2][2], auto_tile_parts[2][3]);
        source_auto_tile.auto_tile[29] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[4][1], auto_tile_parts[2][2], auto_tile_parts[2][3]);
        source_auto_tile.auto_tile[30] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[3][1], auto_tile_parts[2][2], auto_tile_parts[2][3]);
        source_auto_tile.auto_tile[31] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[3][1], auto_tile_parts[2][2], auto_tile_parts[2][3]);
        source_auto_tile.auto_tile[32] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[1][1], auto_tile_parts[1][2], auto_tile_parts[1][3]);
        source_auto_tile.auto_tile[33] = combineFourAutoTile(auto_tile_parts[2][0], auto_tile_parts[2][1], auto_tile_parts[2][2], auto_tile_parts[2][3]);
        source_auto_tile.auto_tile[34] = combineFourAutoTile(auto_tile_parts[0][0], auto_tile_parts[2][1], auto_tile_parts[1][2], auto_tile_parts[4][3]);
        source_auto_tile.auto_tile[35] = combineFourAutoTile(auto_tile_parts[0][0], auto_tile_parts[2][1], auto_tile_parts[1][2], auto_tile_parts[3][3]);
        source_auto_tile.auto_tile[36] = combineFourAutoTile(auto_tile_parts[2][0], auto_tile_parts[0][1], auto_tile_parts[4][2], auto_tile_parts[1][3]);
        source_auto_tile.auto_tile[37] = combineFourAutoTile(auto_tile_parts[2][0], auto_tile_parts[0][1], auto_tile_parts[3][2], auto_tile_parts[1][3]);
        source_auto_tile.auto_tile[38] = combineFourAutoTile(auto_tile_parts[4][0], auto_tile_parts[1][1], auto_tile_parts[2][2], auto_tile_parts[0][3]);
        source_auto_tile.auto_tile[39] = combineFourAutoTile(auto_tile_parts[3][0], auto_tile_parts[1][1], auto_tile_parts[2][2], auto_tile_parts[0][3]);
        source_auto_tile.auto_tile[40] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[4][1], auto_tile_parts[0][2], auto_tile_parts[2][3]);
        source_auto_tile.auto_tile[41] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[3][1], auto_tile_parts[0][2], auto_tile_parts[2][3]);
        source_auto_tile.auto_tile[42] = combineFourAutoTile(auto_tile_parts[0][0], auto_tile_parts[0][1], auto_tile_parts[1][2], auto_tile_parts[1][3]);
        source_auto_tile.auto_tile[43] = combineFourAutoTile(auto_tile_parts[0][0], auto_tile_parts[2][1], auto_tile_parts[0][2], auto_tile_parts[2][3]);
        source_auto_tile.auto_tile[44] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[1][1], auto_tile_parts[0][2], auto_tile_parts[0][3]);
        source_auto_tile.auto_tile[45] = combineFourAutoTile(auto_tile_parts[2][0], auto_tile_parts[0][1], auto_tile_parts[2][2], auto_tile_parts[0][3]);
        source_auto_tile.auto_tile[46] = combineFourAutoTile(auto_tile_parts[0][0], auto_tile_parts[0][1], auto_tile_parts[0][2], auto_tile_parts[0][3]);
    }

    public void createAutoTileForSideWall(AutoTile source_auto_tile, BitmapData side_wall[]){
        auto_tile_parts = new BitmapData[2][4];
        for(int i = 0;i < 2;i++){
            for(int j = 0;j < 4;j++){
                if(j <= 1) {
                    auto_tile_parts[i][j] = graphic.processTrimmingBitmapData(source_auto_tile.raw_auto_tile[i*2], 16 * (j % 2), 0, 16, 16);
                }
                else{
                    auto_tile_parts[i][j] = graphic.processTrimmingBitmapData(source_auto_tile.raw_auto_tile[i*2], 16 * (j % 2), 16, 16, 16);
                }
            }
        }
        //r:部屋 w:壁
        side_wall[0] = combineFourAutoTile(auto_tile_parts[0][0], auto_tile_parts[0][1], auto_tile_parts[0][2], auto_tile_parts[0][3]);//rr 47
        side_wall[1] = combineFourAutoTile(auto_tile_parts[0][0], auto_tile_parts[1][1], auto_tile_parts[0][2], auto_tile_parts[1][3]);//rw 48
        side_wall[2] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[0][1], auto_tile_parts[1][2], auto_tile_parts[0][3]);//wr 49
        side_wall[3] = combineFourAutoTile(auto_tile_parts[1][0], auto_tile_parts[1][1], auto_tile_parts[1][2], auto_tile_parts[1][3]);//ww 50
    }

    private void createBigAutoTileParts(AutoTile source_auto_tile_wall, BitmapData source_side_wall[], int source_auto_tile_num, int first_parts_num, int second_parts_num, int third_parts_num, int fourth_parts_num) {
        //横壁なし
        if (third_parts_num < 47 || fourth_parts_num < 47) {
            source_auto_tile_wall.big_auto_tile[source_auto_tile_num] = combineFourAutoTile(source_auto_tile_wall.auto_tile[first_parts_num], source_auto_tile_wall.auto_tile[second_parts_num], source_auto_tile_wall.auto_tile[third_parts_num], source_auto_tile_wall.auto_tile[fourth_parts_num]);
        }
        //下二つ(3と4)が横壁
        else{
            source_auto_tile_wall.big_auto_tile[source_auto_tile_num] = combineFourAutoTile(source_auto_tile_wall.auto_tile[first_parts_num], source_auto_tile_wall.auto_tile[second_parts_num], source_side_wall[third_parts_num - 47], source_side_wall[fourth_parts_num - 47]);
        }
    }

    private void createBigAutoTileParts_floor(AutoTile source_auto_tile_wall, int source_auto_tile_num, int first_parts_num, int second_parts_num, int third_parts_num, int fourth_parts_num) {
        //横壁なし
        if (third_parts_num < 47 || fourth_parts_num < 47) {
            source_auto_tile_wall.big_auto_tile[source_auto_tile_num] = combineFourAutoTile(source_auto_tile_wall.auto_tile[first_parts_num], source_auto_tile_wall.auto_tile[second_parts_num], source_auto_tile_wall.auto_tile[third_parts_num], source_auto_tile_wall.auto_tile[fourth_parts_num]);
        }
    }

    public void createBigAutoTile(AutoTile m_source_auto_tile_wall, BitmapData m_source_side_wall[]) {
        //0〜48は壁,49〜52は横壁
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 0, 0, 0, 0, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 1, 1, 0, 0, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 2, 0, 2, 0, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 3, 0, 0, 0, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 4, 0, 0, 4, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 5, 1, 2, 0, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 6, 0, 6, 0, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 7, 4, 3, 16, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 8, 8, 0, 16, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 9, 1, 0, 0, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 10, 0, 2, 4, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 11, 0, 2, 4, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 12, 1, 0, 4, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 13, 1, 2, 4, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 14, 1, 2, 0, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 15, 1, 2, 4, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 16, 16, 0, 16, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 17, 16, 2, 16, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 18, 16, 0, 16, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 19, 16, 2, 16, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 20, 20, 20, 0, 0);
        //createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 21, 20, 20, 0, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 21, 20, 21, 0, 24);
        //createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 22, 20, 20, 4, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 22, 22, 20, 16, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 23, 20, 20, 4, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 24, 0, 24, 0, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 25, 0, 24, 4, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 26, 1, 24, 0, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 27, 1, 24, 4, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 28, 28, 28, 50, 50);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 29, 29, 28, 50, 50);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 30, 28, 30, 50, 50);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 31, 29, 30, 50, 50);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 32, 16, 24, 16, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 33, 33, 33, 50, 50);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 34, 34, 20, 16, 0);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 35, 34, 20, 16, 3);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 36, 20, 36, 0, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 37, 20, 36, 4, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 38, 28, 38, 50, 49);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 39, 29, 38, 50, 49);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 40, 40, 28, 48, 50);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 41, 40, 30, 48, 50);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 42, 34, 36, 16, 24);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 43, 43, 33, 48, 50);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 44, 40, 38, 48, 49);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 45, 33, 45, 50, 49);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 46, 43, 45, 50, 49);
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 47, 4, 0, 16, 0);//左下半分部屋
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 48, 0, 3, 0, 24);//右下半分部屋
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 49, 4, 24, 16, 24);//左下半分部屋右部屋
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 50, 16, 3, 16, 24);//右下半分部屋左部屋
        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 51, 4, 3, 16, 24);//右下半分と左下半分部屋


//        source_auto_tile.big_auto_tile[0] = combineFourAutoTile(source_auto_tile.auto_tile[0], source_auto_tile.auto_tile[0], source_auto_tile.auto_tile[0], source_auto_tile.auto_tile[0]);
    }

    public void createBigAutoTile_floor(AutoTile m_source_auto_tile_wall) {
        //0〜48は壁,49〜52は横壁
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 0, 0, 0, 0, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 1, 1, 0, 0, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 2, 0, 2, 0, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 3, 0, 0, 0, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 4, 0, 0, 4, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 5, 1, 2, 0, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 6, 0, 2, 0, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 7, 0, 0, 4, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 8, 1, 0, 4, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 9, 1, 0, 0, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 10, 0, 2, 4, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 11, 0, 2, 4, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 12, 1, 0, 4, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 13, 1, 2, 4, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 14, 1, 2, 0, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 15, 1, 2, 4, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 16, 16, 0, 16, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 17, 16, 2, 16, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 18, 16, 0, 16, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 19, 16, 2, 16, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 20, 20, 20, 0, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 21, 20, 20, 0, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 22, 20, 20, 4, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 23, 20, 20, 4, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 24, 0, 24, 0, 24);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 25, 0, 24, 4, 24);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 26, 1, 24, 0, 24);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 27, 1, 24, 4, 24);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 28, 0, 0, 28, 28);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 29, 1, 0, 28, 28);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 30, 0, 2, 28, 28);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 31, 1, 2, 28, 28);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 32, 16, 24, 16, 24);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 33, 20, 20, 28, 28);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 34, 34, 20, 16, 0);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 35, 34, 20, 16, 3);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 36, 20, 36, 0, 24);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 37, 20, 36, 4, 24);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 38, 0, 24, 28, 38);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 39, 1, 24, 28, 38);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 40, 16, 0, 40, 28);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 41, 16, 2, 40, 28);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 42, 34, 36, 16, 24);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 43, 34, 20, 40, 28);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 44, 16, 24, 40, 38);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 45, 20, 36, 28, 38);
        createBigAutoTileParts_floor(m_source_auto_tile_wall, 46, 34, 36, 40, 38);
//        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 47, 4, 0, 16, 0);//左下半分部屋
//        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 48, 0, 3, 0, 24);//右下半分部屋
//        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 49, 4, 24, 16, 24);//左下半分部屋右部屋
//        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 50, 16, 3, 16, 24);//右下半分部屋左部屋
//        createBigAutoTileParts(m_source_auto_tile_wall, m_source_side_wall, 51, 4, 3, 16, 24);//右下半分と左下半分部屋


//        source_auto_tile.big_auto_tile[0] = combineFourAutoTile(source_auto_tile.auto_tile[0], source_auto_tile.auto_tile[0], source_auto_tile.auto_tile[0], source_auto_tile.auto_tile[0]);
    }

    public void drawAutoTile(boolean lu,boolean u, boolean ru, boolean l, boolean r, boolean ld, boolean d, boolean rd, AutoTile m_auto_tile, int x, int y, float magnification){
        //luからrdは自分と同じ属性のときtrue
        //0
        if(lu && u && ru && l && r && ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[0], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && ru && l && r && ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[1], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && !ru && l && r && ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[2], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && ru && l && r && ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[3], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && ru && l && r && !ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[4], x, y, magnification, magnification, 0, 255, true);
        }
        //5
        else if(!lu && u && !ru && l && r && ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[5], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && !ru && l && r && ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[6], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && ru && l && r && !ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[7], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && ru && l && r && !ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[8], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && ru && l && r && ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[9], x, y, magnification, magnification, 0, 255, true);
        }
        //10
        else if(lu && u && !ru && l && r && !ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[10], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && !ru && l && r && !ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[11], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && ru && l && r && !ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[12], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && !ru && l && r && !ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[13], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && !ru && l && r && ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[14], x, y, magnification, magnification, 0, 255, true);
        }
        //15
        else if(!lu && u && !ru && l && r && !ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[15], x, y, magnification, magnification, 0, 255, true);
        }
        else if(u && ru && !l && r && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[16], x, y, magnification, magnification, 0, 255, true);
        }
        else if(u && !ru && !l && r && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[17], x, y, magnification, magnification, 0, 255, true);
        }
        else if(u && ru && !l && r && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[18], x, y, magnification, magnification, 0, 255, true);
        }
        else if(u && !ru && !l && r && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[19], x, y, magnification, magnification, 0, 255, true);
        }
        //20
        else if(!u && l && r && ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[20], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && l && r && ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[21], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && l && r && !ld && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[22], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && l && r && !ld && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[23], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && l && !r && ld && d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[24], x, y, magnification, magnification, 0, 255, true);
        }
        //25
        else if(lu && u && l && !r && !ld && d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[25], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && l && !r && ld && d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[26], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && l && !r && !ld && d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[27], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && ru && l && r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[28], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && ru && l && r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[29], x, y, magnification, magnification, 0, 255, true);
        }
        //30
        else if(lu && u && !ru && l && r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[30], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && !ru && l && r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[31], x, y, magnification, magnification, 0, 255, true);
        }
        else if(u && !l && !r && d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[32], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && l && r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[33], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && !l && r && d && rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[34], x, y, magnification, magnification, 0, 255, true);
        }
        //35
        else if(!u && !l && r && d && !rd){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[35], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && l && !r && ld && d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[36], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && l && !r && !ld && d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[37], x, y, magnification, magnification, 0, 255, true);
        }
        else if(lu && u && l && !r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[38], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!lu && u && l && !r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[39], x, y, magnification, magnification, 0, 255, true);
        }
        //40
        else if(u && ru && !l && r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[40], x, y, magnification, magnification, 0, 255, true);
        }
        else if(u && !ru && !l && r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[41], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && !l && !r && d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[42], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && !l && r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[43], x, y, magnification, magnification, 0, 255, true);
        }
        else if(u && !l && !r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[44], x, y, magnification, magnification, 0, 255, true);
        }
        //45
        else if(!u && l && !r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[45], x, y, magnification, magnification, 0, 255, true);
        }
        else if(!u && !l && !r && !d){
            graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[46], x, y, magnification, magnification, 0, 255, true);
        }

    }

    //以下デバッグ用
    public void printAutoTileParts(){
        for(int i = 0;i < 5;i++) {
            for (int j = 0; j < 4; j++) {
                if(j <= 1) {
                    graphic.bookingDrawBitmapData(auto_tile_parts[i][j], 100 + 16 * (j % 2), 100+32*i);
                }
                else {
                    graphic.bookingDrawBitmapData(auto_tile_parts[i][j], 100 + 16 * (j % 2), 116+32*i);
                }
            }
        }
    }

    public void combineTest(AutoTile auto_tile){

        //System.out.println("ホリエ"+auto_tile.raw_auto_tile.length);
        test = graphic.processCombineBitmapData(auto_tile.raw_auto_tile, false);
        //test = auto_tile.raw_auto_tile[0];
    }

    public void combine4Test(AutoTile auto_tile){
        //test = combineFourAutoTile(auto_tile.raw_auto_tile[0], auto_tile.raw_auto_tile[1], auto_tile.raw_auto_tile[2], auto_tile.raw_auto_tile[3]);
    }

    public void printAutoTileTest(AutoTile m_auto_tile){
        //graphic.bookingDrawBitmapData(test, 100, 100, 3, 3 , 0, 255, true);
        for(int i  = 0;i < 8;i++){
            for(int j = 0;j < 6;j++){
                if(i + j * 8 <= 46) {
                    graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[i + j * 8], 100 + 32*2*i, 100 + 32*2*j, (float) 2, (float) 2, 0, 255, true);
                }
            }
        }
        //graphic.bookingDrawBitmapData(m_auto_tile.auto_tile[3], 100, 100, 3, 3 , 0, 255, true);
    }

    public void release() {
        System.out.println("takanoRelease : AutoTileAdmin");
        auto_tile_parts = null;
        test = null;
        test2 = null;
        test3 = null;
    }
}
