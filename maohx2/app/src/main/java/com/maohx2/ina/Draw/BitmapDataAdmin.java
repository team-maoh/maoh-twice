package com.maohx2.ina.Draw;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Map.GeoSlot;
import com.maohx2.kmhanko.database.MyDatabase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ina on 2017/10/08.
 */
public class BitmapDataAdmin {

   //メンバ変数
    private final Context context;
    private final String FOLDER = "image.item";
    private MyDatabase database;
    BitmapData bitmap_data[] = new BitmapData[100];


    //コンストラクタ
    public BitmapDataAdmin(Context _context) {
        context = _context;
    }

    public void init() {

        for(int i = 0; i <100; i++) {
            bitmap_data[i] = new BitmapData();
        }
    }


    public void loadImages() {

        List<String> file_name = new ArrayList<String>();
        List<String> image_name = new ArrayList<String>();

        file_name = database.getString("item", "filename", null);
        image_name = database.getString("item", "imagename", null);
        final AssetManager assetManager = context.getAssets();


        BufferedInputStream bis = null;
        for (int i = 0; i < file_name.size(); i++) {
            try {
                bis = new BufferedInputStream(assetManager.open(file_name.get(i)));
                bitmap_data[i].setBitmap(BitmapFactory.decodeStream(bis));
                bitmap_data[i].setImageName(image_name.get(i));
            } catch (IOException e) {
                System.out.println("failed load images");
            }
        }
    }









    public void setDatabase(MyDatabase _database) {
        database = _database;
    }

}






























