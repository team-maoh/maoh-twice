package com.maohx2.kmhanko.database;

//sqlite関係
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import java.io.File;

//例外関係
import java.sql.SQLException;
import java.io.IOException;
import android.database.sqlite.SQLiteException;

//dbコピー関係
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by user on 2017/09/03.
 * version : 1.00
 */


//SQLiteOpenHelperは、Databaseのインスタンスを簡略化するためのサポートクラス。私の作ったこのクラスは、dbと一対一で存在する必要がある設計となっている。
//どうせ一対一なら、軽傷でRead用とWrite用の二種類のヘルパーに分けたほうがいいかもしれない
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String FOLDER_NAME = "database";

    private String db_name;
    private String db_asset;
    private int db_version;

    private SQLiteDatabase mDatabase;
    private final Context mContext;
    private final File mDatabasePath;

    //コンストラクタ
    public MySQLiteOpenHelper(Context context, String _db_name, String _db_asset, int _db_version) {
        // 任意のデータベースファイル名と、バージョンを指定する
        super(context, _db_name, null, _db_version);

        mContext = context;
        db_name = _db_name;
        db_asset = _db_asset;
        db_version = _db_version;

        //DBファイルを探し読み込む
        mDatabasePath = mContext.getDatabasePath(db_name);
        if (!mDatabasePath.exists()) {
            System.out.println("dg_mes:"+ db_name +" is not found.");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("dg_mes:"+db_name+" onCreate.");
    }

    //assetsフォルダ内のDBファイルをdatabasesフォルダ内のDBファイルにコピーするメソッド。同名のファイルでなければならない。
    public void copyDataBaseFromAssets() throws IOException {
        //database.close();

        //dbのコピーの下準備
        InputStream input = mContext.getAssets().open(db_asset); //assetフォルダ内のdbファイルを格納
        OutputStream  output = new FileOutputStream(mDatabasePath); //このクラスで扱うdbを指定
        copy(input, output);//このクラスで扱うdbに、assets内のdbをコピーする。

        //streamを閉じる
        output.flush();
        output.close();
        input.close();
    }

    //データベースが存在するかどうかを返すメソッド
    private boolean checkDataBaseExist(String mode) {
        String dbPath = mDatabasePath.getAbsolutePath();
        SQLiteDatabase checkDb = null;

        int open_mode;
        if (mode.equals("r")) {
            open_mode = SQLiteDatabase.OPEN_READONLY;
        } else if (mode.equals("w")) {
            open_mode = SQLiteDatabase.OPEN_READWRITE;
        } else {
            throw new Error("MySQLiteOpenHelper.checkDataBaseExist : please set r or w");
        }

        try {
            checkDb = SQLiteDatabase.openDatabase(dbPath, null, open_mode);
        } catch (SQLiteException e) {
            //DBはまだ存在していない
        }

        if (checkDb == null) {
            return false;
        }

        int oldVersion = checkDb.getVersion();
        int newVersion = db_version;

        if (oldVersion == newVersion) {
            checkDb.close();
            return true;
        }

        //最新でないので削除する
        /*
        File f = new File(dbPath);
        f.delete();
        return false;
        */

        return false;
    }

    //DBファイルが無い場合はAssetsからコピーし、DBファイルがある場合は特に何もしないメソッド
    public void createEmptyDataBase_w() throws IOException {
        boolean dbExist = checkDataBaseExist("w");
        if (dbExist) {
            //ファイルが存在したので、特に何もしない
        } else {
            //DBファイルが作成される。
            super.getWritableDatabase();
            try {
                //DBファイルをAssetsからコピーする
                copyDataBaseFromAssets();

                //コピーした結果のDBファイルがちゃんと生成されたかを確認する
                String dbPath = mDatabasePath.getAbsolutePath();
                SQLiteDatabase checkDb = null;
                try {
                    checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }

                //ちゃんと生成されていたなら、バージョンをセットして終了
                if (checkDb != null) {
                    checkDb.setVersion(db_version);
                    checkDb.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //CopyFromAssets用
    private int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        int count = 0,n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    //引数がwならgetWritable()を、rならgetReadable()を呼び出すだけのメソッド
    public SQLiteDatabase openDataBase(String mode) {
        if (mode.equals("r")) {
            return super.getReadableDatabase();
        } else if (mode.equals("w")) {
            return super.getWritableDatabase();
        } else {
            throw new Error("MySQLiteOpenHelper.copyDataBase : please set r or w");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("dg_mes:"+db_name+" onUpgrade.");
    }

    @Override
    public synchronized void close() {
        //データベースが読み込まれている場合にのみクローズする
        if (mDatabase != null) {
            super.close();
        }
    }

}

