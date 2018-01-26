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
}
