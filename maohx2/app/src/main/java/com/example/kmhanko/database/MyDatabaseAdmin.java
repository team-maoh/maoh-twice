package com.example.kmhanko.database;
/**
 * Created by user on 2017/09/17.
 * version : 1.00
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

    public int addMyDatabase(String db_name,String db_asset,int db_version,String load_mode) {
        databases.add(new MyDatabase(mContext));
        databases.get(databases.size() - 1).init(db_name,db_asset,db_version,load_mode);

        return databases.size();
    }

    public MyDatabase getMyDatabase(int i) {
        return databases.get(i);
    }

    public MyDatabase getMyDatabase(String name) {
        int count = 0;
        MyDatabase mydb = null;
        for (int i = 0; i < databases.size(); i++) {
            if (databases.get(i).getDbName() == name) {
                mydb = getMyDatabase(i);
                count++;
            }
        }

        if (count == 1) {
            return mydb;
        } else {
            if (count == 0) {
                System.out.println("dg_mes:" + "MyDatabaseAdmin.getMyDatabase : db is not found");
            }
            if (count > 1) {
                System.out.println("dg_mes:" + "MyDatabaseAdmin.getMyDatabase : db is many : "+ count);
            }
            return null;
        }
    }

}
