package com.maohx2.ina.Draw;

import android.graphics.Bitmap;

/**
 * Created by ina on 2017/10/08.
 */

public class BitmapData{

    Bitmap bitmap;
    String image_name;



    BitmapData(){
        image_name = "";
    }

    private Bitmap getBitmap(){
        return bitmap;
    }

    private void setBitmap(Bitmap _bitmap){
        bitmap = _bitmap;
    }

    private String getImageName(){
        return image_name;
    }

    private void setImageName(String _image_name){
        image_name = _image_name;
    }

    public int getWidth(){return bitmap.getWidth();}

    public int getHeight(){return bitmap.getHeight();}

}