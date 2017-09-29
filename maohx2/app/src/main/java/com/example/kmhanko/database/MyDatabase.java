package com.kmhanko.database;

import android.content.Context;
import java.io.IOException;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.database.Cursor;

//配列関係
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;

import com.kmhanko.sound.SoundAdmin;

/**
 * Created by user on 2017/09/03.
 */

public class MyDatabase {
    Context mContext;

    private SQLiteDatabase db;
    private MySQLiteOpenHelper mDbHelper;

    private String db_name;
    private String db_asset;
    private int db_version;

    public MyDatabase(Context context) {
        super();
        mContext = context;
    }

    public boolean init(String _db_name, String _db_asset, int _db_version, String load_mode) {
        if (load_mode != "r" && load_mode != "w") {
            System.out.println("dg_mes:" + "MyDatabase : please set r or w");
            return false;
        }

        db_name = _db_name;
        db_asset = _db_asset;
        db_version = _db_version;

        //データベース関係の処理
        mDbHelper = new MySQLiteOpenHelper(mContext, db_name, db_asset, db_version);//ヘルパーのインスタンス


        try {
            if (load_mode == "r") {
                db = mDbHelper.openDataBase_r();
                db = mDbHelper.copyDataBase(db);
            }
            if (load_mode == "w") {
                //存在しない場合生成する
                    //db = mDbHelper.openDataBase_w();//必ず最初にopenを呼ぶこと。これによりdbを獲得する
                db = mDbHelper.getWritableDatabase();

                //if (!mDbHelper.createEmptyDataBase_w()) {
                    try {
                        mDbHelper.copyDataBaseFromAsset(db);
                    } catch(IOException e) {
                    }
                //}
                //db = mDbHelper.copyDataBase(db);
            }
        } catch (IOException e) {
            //TODO : エラー処理
        } catch (SQLException sqle) {
            //TODO : エラー処理
        }

        return true;

        /*
        Cursor c = db.query("test_table", new String[] { "name", "gender", "score" }, null, null, null, null, null);

        String text = "";

        boolean isEof = c.moveToFirst();
        while (isEof) {
            text += String.format("%s(%s) : %d ,", c.getString(0), c.getString(1), c.getInt(2));
            isEof = c.moveToNext();
        }

        System.out.println("dg_mes:"+text);

        db.close();
        System.out.println("dg_mes:"+"db closed.");

        */
        //int test[] = getRowID("test_table","gender == man");

        /*
        for(int i=0;i < test.length; i++) {
            System.out.println("dg_mes:"+getStringFromRowID("test_table","name",test[i]));
        }
        */
    }


    /*
    private String[] makeRowIDAndNameColums(String c_name) {
        String cl[] = new String[2];
        cl[0] = "rowid";
        cl[1] = c_name;
        return cl;
    }
*/

    /*
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

    //'をつけた文字列を返す関数
    public String s_quo (String str) {
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
    private Cursor getCursor(String c_name, String t_name) {
        return db.rawQuery("SELECT "+c_name+" FROM "+t_name,null);
    }
    private Cursor getCursor(String c_name, String t_name, String w_script) {
        if (w_script == null) {
            return getCursor(c_name, t_name);
    }
       Cursor c = db.rawQuery("SELECT "+c_name+" FROM "+t_name+" WHERE "+w_script,null);
        return c;
        //return db.rawQuery("SELECT "+c_name+" FROM "+t_name+" WHERE "+"name == ?",new String[]{"chainsaw"});
    }


    //データベースからデータを取得する関数
    //TODO:LIKE,GLOBなどをオーバーライドして対応させる
    public List<Integer> getRowID(String t_name, String w_script) {
        return getInt("rowid", t_name, w_script);
    }

    public int getOneRowID(String t_name, String w_script) {
        List<Integer> buf = getInt("rowid", t_name, w_script);
        if (buf.size() == 1) {
            return buf.get(0);
        } else {
            System.out.println("dg_mes:" + "MyDatabase.getOneRowID : Found rowids are not only one.");
            return buf.get(0);
        }
    }

    public int getOneRowIDForArray(String t_name, String w_script) {
        System.out.println("dg_mes:" + getOneRowID(t_name, w_script));
        return getOneRowID(t_name, w_script) - 1;
    }

    public List<Integer> getInt(String c_name, String t_name) {
        return getInt(c_name, t_name, null);
    }
    public List<Integer> getInt(String c_name, String t_name, String w_script) {
        Cursor c = getCursor(c_name, t_name, w_script);
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return null;
        }
        List<Integer> buf = new ArrayList<Integer>();
        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getInt(0));
            isEof = c.moveToNext();
        }
        return buf;
    }

    public List<String> getString(String c_name, String t_name) {
        return getString(c_name, t_name, null);
    }
    public List<String> getString(String c_name, String t_name, String w_script) {
        Cursor c = getCursor(c_name, t_name, w_script);
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return null;
        }
        List<String> buf = new ArrayList<String>();
        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getString(0));
            isEof = c.moveToNext();
        }
        return buf;
    }

    public List<Double> getDouble(String c_name, String t_name) {
        return getDouble(c_name, t_name, null);
    }
    public List<Double> getDouble(String c_name, String t_name, String w_script) {
        Cursor c = getCursor(c_name, t_name, w_script);
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return null;
        }
        List<Double> buf = new ArrayList<Double>();
        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getDouble(0));
            isEof = c.moveToNext();
        }
        return buf;
    }

    public List<Float> getFloat(String c_name, String t_name) {
        return getFloat(c_name, t_name, null);
    }
    public List<Float> getFloat(String c_name, String t_name, String w_script) {
        Cursor c = getCursor(c_name, t_name, w_script);
        //該当要素が存在しない場合
        if (c.getCount() == 0) {
            return null;
        }
        List<Float> buf = new ArrayList<Float>();
        boolean isEof = c.moveToFirst();
        while (isEof) {
            buf.add(c.getFloat(0));
            isEof = c.moveToNext();
        }
        return buf;
    }

    //データベースにデータを書き出す関数
    public void execSQL(String sql) {
        db.execSQL(sql);
    }


    public boolean insert(String t_name, String[] c_names, String[] values) {

        //カラム群と値群の個数が異なる場合falseを返す
        if (c_names.length != values.length) {
            System.out.println("dg_mes:" + "MyDatabase.insert : c_names.size != values.size");
            return false;
        }
        int size = c_names.length;

        //ContentValuesに格納する
        ContentValues content_values = new ContentValues(size);
        for(int i = 0; i < size; i++) {
            content_values.put(c_names[i], values[i]);
        }

        //insertを行う
        //TODO:例外処理
        long id = db.insert(t_name, null, content_values);
        if (id == -1) {
            return false;
        }

        return true;
    }




    //ゲッター
    public String getDbName() {
        return db_name;
    }
    public String getDbAsset() { return db_asset; }
    public int getDbVersion() { return db_version; }


    public void close() {
        mDbHelper.close();
    }

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
            //TODO:わかりやすい値
            return -999999;
        }
    }

    public int getOneRowIDForArray(String t_name, String selection) {
        return getOneRowID(t_name, selection) - 1;
    }
*/


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







