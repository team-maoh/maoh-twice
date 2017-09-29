package com.kmhanko.database;

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
 */


//SQLiteOpenHelperは、Databaseのインスタンスを簡略化するためのサポートクラス。dbと一対一で存在する必要あり
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

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
            System.out.println("dg_mes:"+"db is not found.");
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //dbが存在しない状態でOpenすると、これが呼ばれ、dbに新規作成されたインスタンスがもらえる。
        System.out.println("dg_mes:"+db_name+" onCreate.");
    }

    //Assetsを利用しているため、読み込み専用
    public void copyDataBaseFromAsset(SQLiteDatabase database) throws IOException {
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

    private void copyDataBase_ww() throws IOException {
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
    private boolean checkDataBaseExist() {
        String dbPath = mDatabasePath.getAbsolutePath();
        SQLiteDatabase checkDb = null;

        //TODO:rとwでわける？
        try {
            checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
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

    //空のデータベースを作成するメソッド
    public boolean createEmptyDataBase_w() throws IOException {
        boolean dbExist = checkDataBaseExist();
        if (dbExist) {
            return false;
        } else {
            super.getWritableDatabase();
            return true;
        }
    }


    //TODO:ちゃんと継承で分けた方が良いかもしれない
    public SQLiteDatabase copyDataBase(SQLiteDatabase database) throws IOException {
        copyDataBaseFromAsset(database);
        return super.getReadableDatabase();
    }

    private int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        int count = 0,n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public SQLiteDatabase openDataBase_w() throws SQLException {
        return super.getWritableDatabase();
    }

    public SQLiteDatabase openDataBase_r() throws SQLException {
        return super.getReadableDatabase();
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

