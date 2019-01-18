package com.maohx2.ina.Draw;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import com.maohx2.kmhanko.database.MyDatabase;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;

import static com.maohx2.ina.Constants.Bitmap.*;

/**
 * Created by ina on 2017/10/08.
 */
public class BitmapDataAdmin {

    //メンバ変数
    private Context context;
    private final String FOLDER = "image.item";
    BitmapData bitmap_data[];
    int next_load_point;


    //kmhanko
    //画像読み込み時に縮小して(容量節約して)読むかどうか。
    boolean optionFlag = false;

    //コンストラクタ
    public BitmapDataAdmin() {}

    public void init(Context _context) {
        next_load_point = 0;
        context = _context;
        bitmap_data = new BitmapData[BITMAP_DATA_INSTANCE];
        for (int i = 0; i < BITMAP_DATA_INSTANCE; i++) {
            bitmap_data[i] = new BitmapData();
        }

    }


    //データベースを読み込んで画像をbitmap_dataに保存する。
    //グローバルは、ゲーム起動時に一番初めだけ実行し、そこに書かれている画像ファイルを全部保存する。
    //image/global/"テーブル名のフォルダ"/の中にあり、そのファイルを読み込んでいる。
    public void loadGlobalImages(MyDatabase image_database) {

        if(image_database != null) {
            List<String> ltable_names = image_database.getTables();
            BufferedInputStream bis = null;
            final AssetManager assetManager = context.getAssets();

            for (int i = 0; i < ltable_names.size(); i++) {
                List<String> file_name = image_database.getString(ltable_names.get(i), "filename", null);
                List<String> image_name = image_database.getString(ltable_names.get(i), "imagename", null);
                System.out.println("takanoa : " + image_name);
                for (int j = 0; j < file_name.size(); j++) {
                    try {
                        bis = new BufferedInputStream(assetManager.open("image/global/" + ltable_names.get(i) + "/" + file_name.get(j)));
                        if (optionFlag) { //by kmhanko
                            BitmapFactory.Options options = new BitmapFactory.Options();

                            //optionsをいじって縮小処理を行う
                            options.inPreferredConfig = Bitmap.Config.ARGB_4444; //8888

                            options.inJustDecodeBounds = false;
                            for(int k = 0; k < BITMAP_DATA_INSTANCE; k++) {
                                if (bitmap_data[next_load_point].getBitmap() == null) {
                                    bitmap_data[next_load_point].setBitmap(BitmapFactory.decodeStream(bis, null, options));
                                    break;
                                } else {
                                    next_load_point = (next_load_point + 1)%BITMAP_DATA_INSTANCE;
                                }
                                if (k == BITMAP_DATA_INSTANCE - 1) {
                                    throw new Error("BitmapDataAdminがバンクしました");
                                }
                            }
                        } else {
                            for(int k = 0; k < BITMAP_DATA_INSTANCE; k++) {
                                if (bitmap_data[next_load_point].getBitmap() == null) {
                                    bitmap_data[next_load_point].setBitmap(BitmapFactory.decodeStream(bis));
                                    break;
                                } else {
                                    next_load_point = (next_load_point + 1)%BITMAP_DATA_INSTANCE;
                                }
                                if (k == BITMAP_DATA_INSTANCE - 1) {
                                    throw new Error("BitmapDataAdminがバンクしました");
                                }
                            }
                        }
                        bitmap_data[next_load_point].setImageName(image_name.get(j));
                        next_load_point = (next_load_point + 1)%BITMAP_DATA_INSTANCE;
                        bis.close();
                    } catch (IOException e) {
                        System.out.println("%☆イナガキ：画像の取り込みに失敗しました"+image_name.get(j));
                    }
                }
            }
        }
    }

    //データベースを読み込んで画像をbitmap_dataに保存する。
    //ローカルは、ダンジョン潜入時やワールド画面などの時に一番初めに使い、その場面でしか使わない、画像ファイルをメモリにあげておく。
    //読み込むデータベースはアクティビティの種類やその内容(ダンジョンの種類)ごとに分け、一つのデータベースファイルが一つのダンジョンなどを示す。
    //データベース内のファイルはすべて読み込む。
    //画像データは、image/local/"読み込むファイル名"/の中に保存する。
    public void loadLocalImages(MyDatabase image_database, String table_folder) {

        List<String> table_names = image_database.getTables();
        final AssetManager assetManager = context.getAssets();
        BufferedInputStream bis = null;


        for (int i = 0; i < table_names.size(); i++) {
            List<String> file_name = image_database.getString(table_names.get(i), "filename", null);
            List<String> image_name = image_database.getString(table_names.get(i), "imagename", null);
            System.out.println("takanob : " + image_name);

            for (int j = 0; j < file_name.size(); j++) {
                try {
                    bis = new BufferedInputStream(assetManager.open("image/local/" + table_folder + "/" + table_names.get(i) + "/" + file_name.get(j)));
                    if (optionFlag) { //by kmhanko
                        BitmapFactory.Options options = new BitmapFactory.Options();

                        //optionsをいじって縮小処理を行う
                        options.inPreferredConfig = Bitmap.Config.ARGB_4444;// 8888

                        options.inJustDecodeBounds = false;

                        for(int k = 0; k < BITMAP_DATA_INSTANCE; k++) {
                            if (bitmap_data[next_load_point].getBitmap() == null) {
                                bitmap_data[next_load_point].setBitmap(BitmapFactory.decodeStream(bis, null, options));
                                break;
                            } else {
                                next_load_point = (next_load_point + 1)%BITMAP_DATA_INSTANCE;
                            }
                            if (k == BITMAP_DATA_INSTANCE - 1) {
                                throw new Error("BitmapDataAdminがバンクしました");
                            }
                        }

                    } else {
                        for(int k = 0; k < BITMAP_DATA_INSTANCE; k++) {
                            if (bitmap_data[next_load_point].getBitmap() == null) {
                                bitmap_data[next_load_point].setBitmap(BitmapFactory.decodeStream(bis));
                                break;
                            } else {
                                next_load_point = (next_load_point + 1)%BITMAP_DATA_INSTANCE;
                            }
                            if (k == BITMAP_DATA_INSTANCE - 1) {
                                throw new Error("BitmapDataAdminがバンクしました");
                            }
                        }
                    }
                    bitmap_data[next_load_point].setImageName(image_name.get(j));
                    //System.out.println(next_load_point+","+j);
                    next_load_point = (next_load_point + 1)%BITMAP_DATA_INSTANCE;
                    bis.close();
                } catch (IOException e) {
                    System.out.println("%☆イナガキ：画像の取り込みに失敗しました"+image_name.get(j));
                }
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


    //bitmap番号をもらって返す
    public BitmapData getBitmapData(int bitmap_data_num) {
        return bitmap_data[bitmap_data_num];
    }

    //by kmhanko
    public void releaseBitmap() {
        System.out.println("takanoRelease : BitMapDataAdmin");
        if (bitmap_data != null) {
            for (int i = 0; i < bitmap_data.length; i++) {
                bitmap_data[i].releaseBitmap();
                bitmap_data[i] = null;
            }
        }
        bitmap_data = null;
    }

    public void clear() {
        System.out.println("takanoRelease : BitMapDataAdmin");
        if (bitmap_data != null) {
            for (int i = 0; i < bitmap_data.length; i++) {
                bitmap_data[i].clear();
            }
        }
    }


}

