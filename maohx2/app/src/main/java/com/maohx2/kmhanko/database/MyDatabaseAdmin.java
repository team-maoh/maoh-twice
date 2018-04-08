package com.maohx2.kmhanko.database;
/**
 * Created by user on 2017/09/17.
 * 12/17
 * エラーメッセージ改定
 * 同名DBが複数あった場合の処理を追加
 *
 */



//配列関係
import java.util.ArrayList;
import java.util.List;

//基本
import android.content.Context;
import java.io.IOException;
import java.sql.SQLException;

public class MyDatabaseAdmin {
    private List<MyDatabase> databases = new ArrayList<MyDatabase>();

    Context mContext;

    public MyDatabaseAdmin(Context context) {
        mContext = context;
    }

    public void close() {
        for (int i = 0; i < databases.size(); i++) {
            databases.get(i).close();
        }
    }

    public void addMyDatabase(String db_name, String db_asset, int db_version, String load_mode) {
        MyDatabase bufDatabase = null;
        if (countUpSameName(db_name) == 0) {
            databases.add(new MyDatabase(mContext));
            bufDatabase = databases.get(databases.size() - 1);
            bufDatabase.init(db_name, db_asset, db_version, load_mode);
        } else {
            System.out.println("☆タカノ:" + "MyDatabaseAdmin#addMyDatabase DbName = " + db_name + " は既に存在するので追加しません");
        }
    }

    public MyDatabase getMyDatabase(int i) {
        return databases.get(i);
    }

    public MyDatabase getMyDatabase(String name) {
        MyDatabase mydb = null;
        int count = 0;
        for (int i = 0; i < databases.size(); i++) {
            if (databases.get(i).getDbName() == name) {
                mydb = databases.get(i);
                count++;
            }
        }

        if (count == 1) {
            return mydb;
        } else {
            if (count == 0) {
                throw new Error("☆タカノ:" + "MyDatabaseAdmin#getMyDatabase : DBを見つけられませんでした : " + name);
            }
            if (count > 1) {
                throw new Error("☆タカノ:" + "MyDatabaseAdmin#getMyDatabase : 同じ名称のDBが複数個見つかりました : "+ count);
            }
            return null;
        }
    }

    public int countUpSameName(String name) {
        int count = 0;
        for (int i = 0; i < databases.size(); i++) {
            if (databases.get(i).getDbName() == name) {
                count++;
            }
        }
        return count;
    }

}
