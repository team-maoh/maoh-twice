package com.maohx2.ina.Draw;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.maohx2.ina.Constants;

/**
 * Created by ina on 2017/10/08.
 */

public class BitmapData{

    Bitmap bitmap;
    String image_name;



    BitmapData(){
        image_name = "";
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    void setBitmap(Bitmap _bitmap){
        bitmap = _bitmap;
    }

    String getImageName(){
        return image_name;
    }

    void setImageName(String _image_name){
        image_name = _image_name;
    }

    public int getWidth(){return bitmap.getWidth();}

    public int getHeight(){return bitmap.getHeight();}

    public void transBitmap(Matrix transMatrix){ bitmap = Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(), bitmap.getHeight(), transMatrix, true); }

    //by kmhanko
    public void releaseBitmap() {
        System.out.println("takanoRelease : BitmapData");
        if (bitmap != null) {
            bitmap.recycle();
            bitmap = null;
            image_name = null;
        }
    }

    public void release() {
        releaseBitmap();
    }

    public void clear() {
        bitmap = null;
        image_name = "";
    }

}