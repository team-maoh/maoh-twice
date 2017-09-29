package com.kmhanko.database;

/**
 * Created by user on 2017/09/17.
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

    public MyDatabase addMyDatabase(String db_name,String db_asset,int db_version,String load_mode) {
        databases.add(new MyDatabase(mContext));
        databases.get(databases.size() - 1).init(db_name,db_asset,db_version,load_mode);

        return databases.get(databases.size() - 1);
    }

    public MyDatabase getMyDatabase(int i) {
        return databases.get(i);
    }

    public MyDatabase getMyDatabase(String name) {
        //TODO:複数あった場合の処理
        for(int i=0;i<databases.size();i++) {
            if (databases.get(i).getDbName() == name) {
                return getMyDatabase(i);
            }
        }
        return null;
    }
}
