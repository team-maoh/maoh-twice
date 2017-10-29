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

import static com.maohx2.ina.Constants.Bitmap.*;

/**
 * Created by ina on 2017/10/08.
 */
public class BitmapDataAdmin {

    //メンバ変数
    private Context context;
    private final String FOLDER = "image.item";
    private MyDatabase image_database;
    BitmapData bitmap_data[] = new BitmapData[BITMAP_DATA_INSTANCE];


    //コンストラクタ
    public BitmapDataAdmin() {}

    public void init(Context _context, MyDatabase _image_database) {
        context = _context;
        image_database = _image_database;
        for (int i = 0; i < BITMAP_DATA_INSTANCE; i++) {
            bitmap_data[i] = new BitmapData();
        }
    }


    //データベースを読み込んで画像をbitmap_dataに保存する
    public void loadGlobalImages() {

        List<String> file_name = new ArrayList<String>();
        List<String> image_name = new ArrayList<String>();

        file_name = image_database.getString("item", "filename", null);
        image_name = image_database.getString("item", "imagename", null);
        final AssetManager assetManager = context.getAssets();


        BufferedInputStream bis = null;
        for (int i = 0; i < file_name.size(); i++) {
            try {
                bis = new BufferedInputStream(assetManager.open("image/item/" + file_name.get(i)));
                bitmap_data[i].setBitmap(BitmapFactory.decodeStream(bis));
                bitmap_data[i].setImageName(image_name.get(i));
            } catch (IOException e) {
                System.out.println("failed load images");
            }
        }
    }

    //データベースを読み込んで画像をbitmap_dataに保存する
    public void loadLocalImages() {

        List<String> file_name = new ArrayList<String>();
        List<String> image_name = new ArrayList<String>();

        file_name = image_database.getString("unit", "filename", null);
        image_name = image_database.getString("unit", "imagename", null);
        final AssetManager assetManager = context.getAssets();


        BufferedInputStream bis = null;
        for (int i = 0; i < file_name.size(); i++) {
            try {
                bis = new BufferedInputStream(assetManager.open("image/unit/" + file_name.get(i)));
                bitmap_data[i].setBitmap(BitmapFactory.decodeStream(bis));
                bitmap_data[i].setImageName(image_name.get(i));
            } catch (IOException e) {
                System.out.println("failed load images");
            }
        }
    }


    //bitmapを名前で検索して、番号を返す
    public int getBitmapDataNum(String bitmap_name) {

        for (int i = 0; i < BITMAP_DATA_INSTANCE; i++) {
            if (bitmap_data[i].getImageName().equals(bitmap_name) == true) {
                return i;
            }
        }
        return -1;
    }


    //bitmapを名前で検索して、返す
    public BitmapData getBitmapData(int bitmap_data_num) {
        return bitmap_data[bitmap_data_num];
    }


    public void setDatabase(MyDatabase _image_database) {image_database = _image_database;}

}

