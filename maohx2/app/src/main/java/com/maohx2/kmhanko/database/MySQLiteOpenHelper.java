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
    private static final String SAVE_FOLDER_NAME = "save";

    private String db_name;
    private String db_asset;
    private int db_version;

    private SQLiteDatabase mDatabase;
    private final Context mContext;
    private final File mDatabasePath;

    private int newVer;
    private int oldVer;

    //コンストラクタ
    public MySQLiteOpenHelper(Context context, String _db_name, String _db_asset, int _db_version) {
        // 任意のデータベースファイル名と、バージョンを指定する。第二引数ではファイル名を指定しており、
        // /data/data/パッケージ名/database/ファイル名 が生成される。

        // Create a helper object to create, open, and/or manage a database.
        // This method always returns very quickly.
        // The database is not actually created or opened until one of getWritableDatabase() or getReadableDatabase() is called.
        super(context, _db_name, null, _db_version);


        mContext = context;
        db_name = _db_name;
        db_version = _db_version;
        db_asset = _db_asset;

        //db_asset = FOLDER_NAME + "/" + _db_asset;

        // /data/data/パッケージ名/database/ファイル名のパスを取得する
        mDatabasePath = mContext.getDatabasePath(db_name);
        if (!mDatabasePath.exists()) {
            System.out.println("☆タカノ:"+ db_name +" is not found.");
        } else {
            System.out.println("☆タカノ:"+ db_name +" is in " + mDatabasePath);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //初めてファイルを作った時。
        System.out.println("☆タカノ:"+db_name+" onCreate.");
    }

    //assetsフォルダ内のDBファイルをdatabasesフォルダ内のDBファイルにコピーするメソッド。同名のファイルでなければならない。
    public void copyDataBaseFromAssets(String loadMode) throws IOException {
        //database.close();
        String folderName = getFolderName(loadMode);

        //dbのコピーの下準備
        InputStream input = mContext.getAssets().open(folderName + "/" + db_asset); //assetフォルダ内のdbファイルを格納
        OutputStream  output = new FileOutputStream(mDatabasePath); //内部DBのdbファイルを格納
        copy(input, output);//このクラスで扱うdbに、assets内のdbをコピーする。

        //streamを閉じる
        output.flush();
        output.close();
        input.close();
    }

    //CopyFromAssets用
    private int copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024 * 4];
        int count = 0,n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        buffer = null;
        return count;
    }

    //内部DBファイルが無い場合はAssetsからコピーし、内部DBファイルがある場合は特に何もしないメソッド
    public boolean createEmptyDataBase(String loadMode) throws IOException {
        File mDatabasePath = mContext.getDatabasePath(db_name);

        //内部DBが存在するか否かを確認
        if (!mDatabasePath.exists()) {
            //存在しない場合

            //内部DBを生成する
            getDatabase(loadMode);
            try {
                //内部DBにassets内のDBをコピーする
                copyDataBaseFromAssets(loadMode);

                //生成されたと思われる内部DBを取得してみる
                SQLiteDatabase checkDb = null;
                try {
                    checkDb = getDatabase(loadMode);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }

                //ちゃんと生成されていたならバージョンをセットして終了
                if (checkDb != null) {
                    checkDb.setVersion(db_version);
                    checkDb.close();
                }

                return true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //内部DBが既に存在している

            //"r"の場合はコピーしておく必要がある(厳密にはバージョンが違う場合)
            //"ds"の場合はデバッグによる書き換えを反映するためコピーしておく
            //"s"の場合はコピーしない

            if (loadMode.equals("r") || loadMode.equals("ds") || loadMode.equals("ns")) {
                try {
                    //内部DBにassets内のDBをコピーする
                    copyDataBaseFromAssets(loadMode);

                    //生成されたと思われる内部DBを取得してみる
                    SQLiteDatabase checkDb = null;
                    try {
                        checkDb = getDatabase(loadMode);
                    } catch (SQLiteException e) {
                        e.printStackTrace();
                    }

                    //ちゃんと生成されていたならバージョンをセットして終了
                    if (checkDb != null) {
                        checkDb.setVersion(db_version);
                        checkDb.close();
                    }

                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        throw new Error("☆タカノ : MySQLiteOpenHelper#createEmptyDataBase");
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return mDatabase = super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return mDatabase = super.getReadableDatabase();
    }

    public SQLiteDatabase getDatabase(String readMode) {
        if (readMode.equals("s") || readMode.equals("ds") || readMode.equals("ns")) {
            return getWritableDatabase();
        }
        if (readMode.equals("r")) {
            return getReadableDatabase();
        }
        throw new Error("☆タカノ : MySQLiteOpenHelper#getDatabase please set r or ds or s or ns");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("☆タカノ:"+db_name+" onUpgrade. " + oldVersion + " to " + newVersion);
        newVer = newVersion;
        oldVer = oldVersion;
    }

    @Override
    public void onDowngrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("☆タカノ:"+db_name+" onDowngrade. " + oldVersion + " to " + newVersion);
        newVer = newVersion;
        oldVer = oldVersion;
    }


    @Override
    public synchronized void close() {
        //データベースが読み込まれている場合にのみクローズする
        db_asset = null;
        db_name = null;
        if (mDatabase != null) {
            super.close();
        }
    }

    public synchronized void close2() {
        if (mDatabase != null) {
            super.close();
        }
    }

    private String getFolderName(String loadMode) {
        if (loadMode.equals("r")) {
            return FOLDER_NAME;
        }
        if (loadMode.equals("s") || loadMode.equals("ds") || loadMode.equals("ns")) {
            return SAVE_FOLDER_NAME;
        }
        throw new Error("☆タカノ : MySQLiteOpenHelper#getFolderName please set r or ds or s or ns: " + loadMode);
    }

    //データベースが存在するかどうかを返すメソッド
    /*
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

        //File f = new File(dbPath);
        //f.delete();
        //return false;


        return false;
    }
    */

    //assets/databaseディレクトリ下から、fileNameを検索し、assetsからの相対パスを返すメソッド
    //public String searchFileFromDatabase(String fileName) {
    //}

    public int getNewVer() { return newVer; }
    public int getOldVer() { return oldVer; }

}

