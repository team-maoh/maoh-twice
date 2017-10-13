package com.maohx2.ina.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by ryomasenda on 2017/09/03.
 */


//セットされた描画データ


public class Graphic {

    //定数
    final int LAYER_MAX = Layer.FRONT.ordinal() + 1;
    final int BITMAP_MAX = 128;

    //ビットマップデータの配列(レイヤー番号*ビットマップ番号)
    BitmapData[][] bitmapDatas = new BitmapData[LAYER_MAX][BITMAP_MAX];
    int bitmapMax[] = new int[LAYER_MAX];

    Paint paint = new Paint();
    SurfaceHolder holder;

    public Graphic(Context context) {
        for(int i = 0; i<LAYER_MAX;i++) {
            for (int j = 0; j < BITMAP_MAX; j++) {
                bitmapDatas[i][j] = new BitmapData();
            }
        }
        paint.setColor(Color.BLUE);
    }

    /*
    ここから呼び出される関数
     */

    public void draw() {
        Canvas canvas = null;
        canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        for(int i = 0; i<LAYER_MAX; i++){
            for(int j = 0; j<bitmapMax[i]; j++) {
//                canvas.drawBitmap(bitmapDatas[i][j].bitmap, (float)bitmapDatas[i][j].x, (float)bitmapDatas[i][j].y, paint);
            }
        }
        holder.unlockCanvasAndPost(canvas);
    }





    public void drawBooking(String name, double x, double y){
        String[] splitName = name.split("/",0);
        String folderName = splitName[0];
        String fileName = splitName[1];

        int layerNum = (new LayerManager().getLayer(folderName)).ordinal();
//        bitmapDatas[layerNum][bitmapMax[layerNum]].setBitmapData(folderName,fileName,x,y);
        bitmapMax[layerNum] = bitmapMax[layerNum] + 1;
    }

    public void drawBooking(String name, double x, double y, String layerName){
        String[] splitName = name.split("/",0);
        String folderName = splitName[0];
        String fileName = splitName[1];

        int layerNum = (new LayerManager().getLayer(layerName)).ordinal();
//        bitmapDatas[layerNum][bitmapMax[layerNum]].setBitmapData(folderName,fileName,x,y);
        bitmapMax[layerNum] = bitmapMax[layerNum] + 1;
    }



    public void setHolder(SurfaceHolder _holder){
        holder = _holder;
    }

    public SurfaceHolder getHolder(){
        return holder;
    }
}
