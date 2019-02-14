package com.maohx2.ina.Draw;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.maohx2.ina.Constants;

/**
 * Created by ina on 2017/10/08.
 */

public class BitmapData {

    Bitmap bitmap;
    String image_name;

    float scaleX = 1.0f;
    float scaleY = 1.0f;



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

    public float getScaleX(){return scaleX;}

    public float getScaleY(){return scaleY;}

    public void setScales(float _scaleX, float _scaleY) {
        scaleX = _scaleX;
        scaleY = _scaleY;
    }

    public void transBitmap(Matrix transMatrix, float _scaleX, float _scaleY){
        bitmap = Bitmap.createBitmap(bitmap, 0,0, bitmap.getWidth(), bitmap.getHeight(), transMatrix, true);
        setScales(_scaleX, _scaleY);
    }

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
        scaleX = 1.0f;
        scaleY = 1.0f;
    }

}