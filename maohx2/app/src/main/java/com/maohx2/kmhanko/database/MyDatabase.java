package com.maohx2.kmhanko.database;

import android.content.Context;
import java.io.IOException;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.database.Cursor;

//配列関係
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

/**
 * Created by user on 2017/09/03.
 * version : 1.02
 * s_quoをStaticに
 * getOne~~系列実装
 * getStringByRowID実装
 *
 * version : 1.01
 * getStringするとき、読み込み順序がおかしくなるバグを修正
 * データベースを読み込みするとき発生するエラーを修正
 * getSize()追加
 * getTables()追加
 * getBoolean()追加
 */

public class MyDatabase {
    Context mContext;

    private SQLiteDatabase db;
    private MySQLiteOpenHelper mDbHelper;

    private String db_name;
    private String db_asset;
    private int db_version;

    public MyDatabase(Context context) {
        //super();
        mContext = context;
    }

    public boolean init(String _db_name, String _db_asset, int _db_version, String loadMode) {
        if (loadMode != "r" && loadMode != "s" && loadMode != "ds") {
            throw new Error("☆タカノ : MyDatabase#init " + "please set r or s or ds");
        }
        db_name = _db_name;
        db_asset = _db_asset;
        db_version = _db_version;

        //データベース関係の処理

        //db_nameとして、/data/data/パッケージ名/database/ファイル名 が生成される。
        mDbHelper = new MySQLiteOpenHelper(mContext, db_name, db_asset, db_version);

        /*
        r = 読み込み専用。ゲーム開始時に毎回assetsからコピーする
        w = 書き込み専用。ゲーム開始時にassetsからコピーしない

         */

        try {
            if (loadMode == "r") {
                //内部DBファイルを生成する
                mDbHelper.getReadableDatabase();
                //assets内のDBファイルを、内部DBのファイルにコピーする
                mDbHelper.copyDataBaseFromAssets("r");
                mDbHelper.close();//TODO 初回起動時に落ちるバグの対症療法
                mDbHelper.getReadableDatabase();
                mDbHelper.copyDataBaseFromAssets("r");
                //assets内のDBファイルを、内部DBのファイルにコピーする
                //dbを取得する
                db = mDbHelper.getReadableDatabase();
            }
            if (loadMode == "s") {
                //内部DBに既にDBファイルが存在するかどうかを確認する。
                //存在するならばDBを獲得して終了
                //存在しないならば新規作成する
                mDbHelper.createEmptyDataBase(loadMode);
                db = mDbHelper.getWritableDatabase();
            }
            if (loadMode == "ds") {
                //内部DBに既にDBファイルが存在するかどうかを確認する。
                //存在するならばDBを獲得して終了
                //存在しないならば新規作成する
                mDbHelper.createEmptyDataBase(loadMode);
                db = mDbHelper.getWritableDatabase();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("☆タカノ : MyDatabase#init"+ e);
        }

/*
        close();

        try {
            if (load_mode == "r") {
                //内部DBファイルを生成する
                mDbHelper.getReadableDatabase();

                //assets内のDBファイルを、内部DBのファイルにコピーする
                mDbHelper.copyDataBaseFromAssets();
                mDbHelper.close();

                mDbHelper.getReadableDatabase();
                mDbHelper.copyDataBaseFromAssets();

                //mDbHelper.createEmptyDataBase(loadMode);
                //dbを取得する
                db = mDbHelper.getReadableDatabase();
            }
            if (loadMode == "w") {
                //内部DBに既にDBファイルが存在するかどうかを確認する。
                //存在するならばDBを獲得して終了
                //存在しないならば新規作成する
                mDbHelper.createEmptyDataBase(loadMode);
                db = mDbHelper.getWritableDatabase();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("☆タカノ : "+ e);
        }

        close();

        try {
            if (loadMode == "r") {
                //内部DBファイルを生成する
                mDbHelper.getReadableDatabase();

                //assets内のDBファイルを、内部DBのファイルにコピーする
                mDbHelper.copyDataBaseFromAssets();

                //mDbHelper.createEmptyDataBase(loadMode);
                //dbを取得する
                db = mDbHelper.getReadableDatabase();
            }
            if (loadMode == "w") {
                //内部DBに既にDBファイルが存在するかどうかを確認する。
                //存在するならばDBを獲得して終了
                //存在しないならば新規作成する
                mDbHelper.createEmptyDataBase(loadMode);
                db = mDbHelper.getWritableDatabase();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error("☆タカノ : "+ e);
        }
*/
        return true;
    }

    //'をつけた文字列を返す関数
    public static String s_quo (String str) {
        return "'"+str+"'";
    }

    //query
    public Cursor query (String table,
                         String[] columns,
                         String selection,
                         String[] selectionArgs,
                         String groupBy,
                         String having,
                         String orderBy,
                         String limit) {
        return db.query(table,columns,selection,selectionArgs,groupBy,having,orderBy,limit);
    }


    //カーソル関係
    public Cursor getCursor(String sql_script) {
        return db.rawQuery(sql_script , null);
    }
    private Cursor getCursor(String t_name, String c_name) {
        return db.rawQuery("SELECT "+c_name+" FROM "+t_name+" ORDER BY rowid ASC", null);
    }
    private Cursor getCursor(String t_name, String c_name, String w_script) {
        if (w_script == null) {
            return getCursor(t_name, c_name);
        }
        return db.rawQuery("SELECT "+c_name+" FROM "+t_name+" WHERE "+w_script+" ORDER BY rowid ASC",null);
    }


    //データベースからデータを取得する関数
    //TODO:LIKE,GLOBなどをオーバーライドして対応させる
    public List<Integer> getRowID(String t_name, String w_script) {
        return getInt(t_name, "rowid", w_script);
    }

    public int getOneRowID(String t_name, String w_script) {
        List<Integer> buf = getInt(t_name, "rowid", w_script);
        if (buf.size() == 1) {
            return buf.get(0);
        } else {
            System.out.println("☆タカノ: MyDatabase#getOneRowID 該当データがちょうど1つではない : 個数 " + buf.size() + " DB " + db_name + " table " + t_name + " w_script " + w_script);
            return -1;
        }
    }

    public int getOneRowIDForArray(String t_name, String w_script) {
        //System.out.println("dg_mes:" + getOneRowID(t_name, w_script));
        return getOneRowID(t_name, w_script) - 1;
    }

    public List<Integer> getInt(String t_name, String c_name) {
        return getInt(t_name, c_name, null);
    }
    public List<Integer> getInt(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        List<Integer> buf = new ArrayList<Integer>();
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return buf;
        }
        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getInt(0));
            isEof = c.moveToNext();
        }
        c.close();
        return buf;
    }
    public int getOneInt(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        //該当要素が存在しない場合
        if (c.getCount() <= 0) {
            throw new Error("☆タカノ: MyDatabase#getOneInt 該当データが存在しない or エラー" + c.getCount());
        }
        if (!c.moveToFirst()) {
            throw new Error("☆タカノ: MyDatabase#getOneInt moveToFirstエラー");
        }
        return c.getInt(0);
    }

    public List<String> getString(String t_name, String c_name) {
        return getString(t_name, c_name, null);
    }
    public List<String> getString(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        List<String> buf = new ArrayList<String>();
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return buf;
        }
        boolean isEof = c.moveToFirst();
        while (isEof) {
            String str = c.getString(0);
            buf.add(c.getString(0));
            isEof = c.moveToNext();
        }
        c.close();
        return buf;
    }
    public String getOneString(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        //該当要素が存在しない場合
        if (c.getCount() <= 0) {
            throw new Error("☆タカノ: MyDatabase#getOneString 該当データが存在しない or エラー" + c.getCount());
        }
        if (!c.moveToFirst()) {
            throw new Error("☆タカノ: MyDatabase#getOneString moveToFirstエラー");
        }
        return c.getString(0);
    }

    public String getStringByRowID(String t_name, String c_name, int rowid) {
        return getOneString(t_name, c_name, "rowid = " + rowid);
    }

    public List<Boolean> getBoolean(String t_name, String c_name) {
        return getBoolean(t_name, c_name, null);
    }
    public List<Boolean> getBoolean(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        List<Boolean> buf = new ArrayList<Boolean>();
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return buf;
        }
        boolean isEof = c.moveToFirst();
        while (isEof) {
            String str = c.getString(0);
            if (str.equals("true")) {
                buf.add(true);
            }
            if (str.equals("false")) {
                buf.add(false);
            }
            isEof = c.moveToNext();
        }
        c.close();
        return buf;
    }
    public List<Double> getDouble(String t_name, String c_name) {
        return getDouble(t_name, c_name, null);
    }
    public List<Double> getDouble(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        List<Double> buf = new ArrayList<Double>();
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return buf;
        }
        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getDouble(0));
            isEof = c.moveToNext();
        }
        c.close();
        return buf;
    }

    public double getOneDouble(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        //該当要素が存在しない場合
        if (c.getCount() <= 0) {
            throw new Error("☆タカノ: MyDatabase#getOneDouble 該当データが存在しない or エラー" + c.getCount());
        }
        if (!c.moveToFirst()) {
            throw new Error("☆タカノ: MyDatabase#getOneDouble moveToFirstエラー");
        }
        return c.getDouble(0);
    }

    public List<Float> getFloat(String t_name, String c_name) {
        return getFloat(t_name, c_name, null);
    }
    public List<Float> getFloat(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        List<Float> buf = new ArrayList<Float>();
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return buf;
        }
        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getFloat(0));
            isEof = c.moveToNext();
        }
        c.close();
        return buf;
    }
    public float getOneFloat(String t_name, String c_name, String w_script) {
        Cursor c = getCursor(t_name, c_name, w_script);
        //該当要素が存在しない場合
        if (c.getCount() <= 0) {
            throw new Error("☆タカノ: MyDatabase#getOneFloat 該当データが存在しない or エラー" + c.getCount());
        }
        if (!c.moveToFirst()) {
            throw new Error("☆タカノ: MyDatabase#getOneFloat moveToFirstエラー");
        }
        return c.getFloat(0);
    }

    public List<String> getOneLine(String t_name, int rowid) {
        return getOneLine(t_name,"rowid = "+rowid);
    }

    public List<String> getOneLine(String t_name, String w_script) {
        Cursor c = getCursor(t_name, "*", w_script);
        List<String> buf = new ArrayList<String>();
        if (c.getCount() == 0) {
            return buf;
        }
        c.moveToFirst();
        for(int i=0; i < c.getColumnCount(); i++) {
            buf.add(c.getString(i));
        }
        c.close();
        return buf;
    }

    // *** その他 ***
    public int getSize(String t_name) {
        Cursor c = getCursor(t_name, "rowid", null);
        return c.getCount();
    }

    public List<String> getTables() {
        //getCursor("select name from sqlite_master where type=’table’ order by name");
        //return getString("name","sqlite_master","type = 'table'");
        //return getString("name","sqlite_master","type='table'");

        Cursor c = getCursor("select name from sqlite_master where type='table'");//なんかここでこうやって書かないと読めない
        List<String> buf = new ArrayList<String>();
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return buf;
        }
        boolean isEof = c.moveToFirst();
        while (isEof) {
            String str = c.getString(0);
            if (!str.equals("android_metadata")) {
                buf.add(c.getString(0));
            }
            isEof = c.moveToNext();
        }
        c.close();
        return buf;
    }

    public void execSQL(String sql) {
        db.execSQL(sql);
    }

    // *** 書き込み関係 ***

    private ContentValues makeContentValues(String[] c_names, String[] values) {
        if (c_names.length != values.length) {
            throw new Error("タカノ : MyDatabase.insert : c_names.size != values.size");
        }
        int size = c_names.length;

        //ContentValuesに格納する
        ContentValues content_values = new ContentValues(size);
        for(int i = 0; i < size; i++) {
            content_values.put(c_names[i], values[i]);
        }
        return content_values;
    }

    // *** insert ***

    /*

    //1列を追加する。c_nameに列を示すカラムを、valiuesに対応するデータを格納して渡す。この列以外は全てNULLになる
    public boolean insertRawByArrayString(String t_name, String c_name, String[] values) {
        if (c_name == null || values == null) {
            return false;
        }
        if (c_name.equals("") || values.length == 0) {
            return false;
        }

        String[] bufColum = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            bufColum[i] = c_name;
        }

        //ContentValuesを作成する。
        ContentValues contentValues = makeContentValues(bufColum, values);

        //insertを行う
        //TODO:例外処理
        long id = db.insert(t_name, null, contentValues);
        if (id == -1) {
            return false;
        }

        return true;
    }

    public <T> boolean insertRawByList(String t_name, String c_name, List<T> values) {
        return insertRawByArrayString(t_name, c_name, list2ArrayString(values));
    }

    public <T> boolean insertRawByArray(String t_name, String c_name, T[] values) {
        return insertRawByArrayString(t_name, c_name, array2ArrayString(values));
    }

    */

    //1行を追加する。c_namesに被りなく各カラムを、valuesに対応するデータを格納して渡す。
    public boolean insertLineByArrayString(String t_name, String[] c_names, String[] values) {
        if (c_names == null || values == null) {
            return false;
        }
        if (c_names.length == 0 || values.length == 0) {
            return false;
        }

        //ContentValuesを作成する。
        ContentValues content_values = makeContentValues(c_names, values);

        //insertを行う
        //TODO:例外処理
        long id = db.insert(t_name, null, content_values);
        if (id == -1) {
            throw new Error("タカノ : MyDatabase#insertLineByArrayString : insert is Error");
        }
        return true;
    }

    public <T> boolean insertLineByList(String t_name, List<String> c_names, List<T> values) {
        return insertLineByArrayString(t_name, list2ArrayString(c_names), list2ArrayString(values));
    }

    public <T> boolean insertLineByList(String t_name, String[] c_names, List<T> values) {
        return insertLineByArrayString(t_name, c_names, list2ArrayString(values));
    }

    public <T> boolean insertLineByArray(String t_name, String[] c_names, T[] values) {
        return insertLineByArrayString(t_name, c_names, array2ArrayString(values));
    }

    public <T> boolean insertLineByArray(String t_name, List<String> c_names, T[] values) {
        return insertLineByArrayString(t_name, list2ArrayString(c_names), array2ArrayString(values));
    }

    // *** insertここまで ***

    // *** rewrite ***

    //返り値は変更された項目数

    // w_scriptに合致する全ての行について、指定カラムの書き換えを行う。
    public int rewrite(String t_name, String[] c_names, String[] values, String w_script) {
        if (c_names == null || values == null) {
            return 0;
        }
        if (c_names.length == 0 || values.length == 0) {
            return 0;
        }
        //ContentValuesを作成する。
        ContentValues content_values = makeContentValues(c_names, values);

        //書き換えを行う
        return db.update(t_name, content_values, w_script, null);
    }

    public int rewrite(String t_name, List<String> c_names, List<String> values, String w_script) {
        return rewrite(t_name, list2ArrayString(c_names), list2ArrayString(values), w_script);
    }

    /*

    //あるカラム列を全ての行について上書きしていく関数
    public <T> int rewriteRawByList(String tableName, String columName, List<T> values) {
        String[] bufCName = new String[values.size()];
        for (int i = 0; i < bufCName.length; i++) {
            bufCName[i] = columName;
        }
        return rewrite(tableName, bufCName, list2ArrayString(values), "null");
    }


    public <T> int rewriteRawByArray(String tableName, String columName, T[] values) {
        String[] bufColum = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            bufColum[i] = columName;
        }
        return rewrite(tableName, bufColum, array2ArrayString(values), "null");
    }

    */

    // *** rewriteここまで ***

    // *** delete ***

    //返り値は削除された項目数
    public int delete(String t_name, String w_script) {
        return db.delete(t_name, w_script, null);
    }

    public void deleteTableAll() {
        List<String> tables = getTables();
        for(int i = 0; i < tables.size(); i++) {
            db.execSQL("drop table " + tables.get(i));
            db.execSQL("vacuum");
        }
    }

    //ゲッター
    public String getDbName() {
        return db_name;
    }
    public String getDbAsset() { return db_asset; }
    public int getDbVersion() { return db_version; }
    public boolean isDatabaseNotNull() { return db != null; }


    public void close() {
        db.close();
        mDbHelper.close();
    }

    public <T> String[] list2ArrayString(List<T> list) {
        String[] buf = new String[list.size()];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = String.valueOf(list.get(i));
        }
        return buf;
    }

    public <T> String[] array2ArrayString(T[] array) {
        String[] buf = new String[array.length];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = String.valueOf(array[i]);
        }
        return buf;
    }

}

/*
データベースの内容は他のメンバーの編集する。
場合によっては、項目が増えることもあるし、テーブルが増えることもある。
テーブルには各列に名前付けられるから、その名前によって指定してもらってこられると良いかもしれん。
*/

/*
あるテーブルに対して

あるカラムにおいて、該当する値であるものの、ROWIDの配列・大きさを返す
あるROWIDについて、あるカラムにおける値を返す。返り値の型も、関数の名前という形で指定する






*/



    /*

    public boolean insertIntAA(String t_name, String[] c_names, int[] values) {
        String[] new_values = new String[values.length];
        for(int i = 0; i < new_values.length; i++){
            new_values[i] = String.valueOf(values[i]);
        }
        return insertStringAA(t_name, c_names, new_values);
    }

    public boolean insertFloatAA(String t_name, String[] c_names, float[] values) {
        String[] new_values = new String[values.length];
        for(int i = 0; i < new_values.length; i++){
            new_values[i] = String.valueOf(values[i]);
        }
        return insertStringAA(t_name, c_names, new_values);
    }

    public boolean insertDoubleAA(String t_name, String[] c_names, double[] values) {
        String[] new_values = new String[values.length];
        for(int i = 0; i < new_values.length; i++){
            new_values[i] = String.valueOf(values[i]);
        }
        return insertStringAA(t_name, c_names, new_values);
    }

    public boolean insertBooleanAA(String t_name, String[] c_names, boolean[] values) {
        String[] new_values = new String[values.length];
        for(int i = 0; i < new_values.length; i++){
            if (values[i]) {
                new_values[i] = "true";
            } else {
                new_values[i] = "false";
            }
        }
        return insertStringAA(t_name, c_names, new_values);
    }


    public boolean insertStringMA(String t_name, String c_name, String[] values) {
        String[] c_names = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            c_names[i] = c_name;
        }
        return insertStringAA(t_name, c_names, values);
    }
    //TODO これの配列とベクトルと型バージョン

    public boolean insertStringML(String t_name, String c_name, List<String> values) {
        return insertStringMA(t_name, c_name, (String[])values.toArray(new String[0]));
    }


    public boolean insertStringAA(String t_name, String[] c_names, String[] values) {
        //ContentValuesを作成する。
        ContentValues content_values = makeContentValues(c_names, values);

        //insertを行う
        //TODO:例外処理
        long id = db.insert(t_name, null, content_values);
        if (id == -1) {
            return false;
        }
        return true;
    }
    public boolean insertStringLL(String t_name, List<String> c_names, List<String> values) {
        return insertStringAA(t_name, (String[])c_names.toArray(new String[0]), (String[])values.toArray(new String[0]));
    }
    public boolean insertIntLL(String t_name, List<String> c_names, List<Integer> values) {
        int[] new_values = new int[values.size()];
        for (int i = 0; i < values.size(); i++) {
            new_values[i] = values.get(i);
        }
        return insertIntAA(t_name, (String[])c_names.toArray(new String[0]), new_values);
    }

    public boolean insertFloatLL(String t_name, List<String> c_names, List<Float> values) {
        float[] new_values = new float[values.size()];
        for (int i = 0; i < values.size(); i++) {
            new_values[i] = values.get(i);
        }
        return insertFloatAA(t_name, (String[])c_names.toArray(new String[0]), new_values);
    }
    public boolean insertDoubleLL(String t_name, List<String> c_names, List<Double> values) {
        double[] new_values = new double[values.size()];
        for (int i = 0; i < values.size(); i++) {
            new_values[i] = values.get(i);
        }
        return insertDoubleAA(t_name, (String[])c_names.toArray(new String[0]), new_values);
    }
    public boolean insertBooleanLL(String t_name, List<String> c_names, List<Boolean> values) {
        boolean[] new_values = new boolean[values.size()];
        for (int i = 0; i < values.size(); i++) {
            new_values[i] = values.get(i);
        }
        return insertBooleanAA(t_name, (String[])c_names.toArray(new String[0]), new_values);
    }
    */


/***ここから旧版***/

    /*
    private String[] makeRowIDAndNameColums(String c_name) {
        String cl[] = new String[2];
        cl[0] = "rowid";
        cl[1] = c_name;
        return cl;
    }

    //あるカラムにおける全ての値を返す。
    public List<Integer> getIntAll(String t_name, String c_name) {
        String cl[] = new String[1];
        cl[0] = c_name;

        Cursor c = db.query(t_name, cl, null, null, null, null, null);
        List<Integer> buf = new ArrayList<Integer>();

        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getInt(0));
            isEof = c.moveToNext();
        }
        return buf;
    }

    public List<String> getStringAll(String t_name, String c_name) {
        String cl[] = new String[1];
        cl[0] = c_name;

        Cursor c = db.query(t_name, cl, null, null, null, null, null);
        List<String> buf = new ArrayList<String>();

        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getString(0));
            isEof = c.moveToNext();
        }
        return buf;
    }

    public List<Double> getDoubleAll(String t_name, String c_name) {
        String cl[] = new String[1];
        cl[0] = c_name;

        Cursor c = db.query(t_name, cl, null, null, null, null, null);
        List<Double> buf = new ArrayList<Double>();

        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getDouble(0));
            isEof = c.moveToNext();
        }
        return buf;
    }

    //あるROWIDについて、あるカラムにおける値を返す。
    public int getIntFromRowID(String t_name, String c_name, int r_id) {
        String cl[] = makeRowIDAndNameColums(c_name);
        Cursor c = db.query(t_name, cl, "rowid == " + r_id, null, null, null, null);
        if (c.getCount() == 0) {
            return -1;
        }
        c.moveToFirst();
        return c.getInt(1);
    }

    public String getStringFromRowID(String t_name, String c_name, int r_id) {
        String cl[] = makeRowIDAndNameColums(c_name);
        Cursor c = db.query(t_name, cl, "rowid == " + r_id, null, null, null, null);
        if (c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        return c.getString(1);
    }

    public double getDoubleFromRowID(String t_name, String c_name, int r_id) {
        String cl[] = makeRowIDAndNameColums(c_name);
        Cursor c = db.query(t_name, cl, "rowid == " + r_id, null, null, null, null);
        if (c.getCount() == 0) {
            return -1.0;
        }
        c.moveToFirst();
        return c.getDouble(1);
    }
    */

    /*
    //あるカラムにおいて、該当する値であるものの、ROWIDの配列を返す
    public List<Integer> getRowID(String t_name, String selection) {
        String[] buf = selection.split(" ", 0);
        String cl[] = makeRowIDAndNameColums(buf[0]);

        String buf2[] = new String[1];
        buf2[0] = buf[2];

        Cursor c = db.query(t_name, cl, buf[0] + " " + buf[1] + " ?", buf2, null, null, null);

        //Cursor c = db.query(t_name, cl , buf[0] +" == "+ buf[2], null, null, null, null);
        //Cursor c = db.query(t_name, cl , selection, null, null, null, null);
        //Cursor c = db.query(t_name, new String[] { "rowid", "score" } , "score == 50" , null, null, null, null);

        //System.out.println("dg_mes:"+c.getCount());

        if (c.getCount() == 0) {
            return null;
        }

        List<Integer> r_id = new ArrayList<Integer>();

        boolean isEof = c.moveToFirst();
        while (isEof) {
            r_id.add(c.getInt(0));
            isEof = c.moveToNext();
        }

        return r_id;
    }

    public int getOneRowID(String t_name, String selection) {
        List<Integer> buf = getRowID(t_name, selection);
        if (buf.size() == 1) {
            return buf.get(0);
        } else {
            return -999999;
        }
    }

    public int getOneRowIDForArray(String t_name, String selection) {
        return getOneRowID(t_name, selection) - 1;
    }
*/

