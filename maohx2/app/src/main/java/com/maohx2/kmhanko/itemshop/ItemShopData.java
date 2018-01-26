package com.maohx2.kmhanko.itemshop;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.NamedDataAdmin;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.ItemData.ItemDataAdmin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/05.
 */

public abstract class ItemShopData<T extends ItemData> {
    String dbName;
    String dbAsset;
    public List<T> datas = new ArrayList<>();
    public MyDatabaseAdmin databaseAdmin;
    public MyDatabase database;
    public String tableName;
    public Graphic graphic;

    ItemShopData(Graphic _graphic, MyDatabaseAdmin _databaseAdmin){
        graphic = _graphic;
        databaseAdmin = _databaseAdmin;
    }

    //子クラスでdbNameなどに値を代入してから呼び出す
    public void setDatabase() {
        databaseAdmin.addMyDatabase(dbName, dbAsset, 1, "r");
        database = databaseAdmin.getMyDatabase(dbName);
    }

    public abstract void loadShopData(String _tableName);

    /** getter **/
    public int getItemDataSize() {
        return datas.size();
    }

    public List<T> getItemDatas() {
        return datas;
    }

    public T getItemData(int i) {
        try {
            return datas.get(i);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new Error("タカノ:ItemShopData#getItemData : Cannot get ItemData[" + i + "]" + e);
        }
    }

    public ItemData getOneDataByName(String _name) {
        for(int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getName().equals(_name)) {
                return datas.get(i);
            }
        }
        throw new Error(String.format("タカノ:NamedDataAdmin#getOneDataByName : There is no Data you request by name : " + _name));
    }

    public List<T> getDatasByName(String _name) {
        List<T> buf_data = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getName().equals(_name)) {
                buf_data.add((T)datas.get(i));
            }
        }
        return buf_data;
    }

    public List<T> getDatasByNames(List<String> names) {
        List<T> buf_data = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            for (int j = 0; j < names.size(); j++) {
                if (datas.get(i).getName().equals(names.get(j))) {
                    buf_data.add((T) datas.get(i));
                }
            }
        }
        return buf_data;
    }
}
