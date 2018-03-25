package com.maohx2.ina.ItemData;

/**
 * Created by user on 2017/11/05.
 */

import java.util.List;
import java.util.ArrayList;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

public abstract class ItemDataAdmin<T extends ItemData>  {
    public String dbName;
    public String dbAsset;
    public String tableName;
    public List<T> datas = new ArrayList<>();
    public MyDatabase database;

    static public Graphic graphic;

    public ItemDataAdmin(Graphic _graphic, MyDatabaseAdmin database_admin){
        graphic = _graphic;
        database_admin.addMyDatabase("ItemData", "ItemData.db", 1, "r");
        database = database_admin.getMyDatabase("ItemData");
    }

    //TODO tableNameが定数なのをなんとかして、NamedDataAdminにまで落とし込む作業
    //public void init(MyDatabaseAdmin databaseAdmin) {}

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
/*
    public void loadDatabase(MyDatabaseAdmin database_admin) {
        database_admin.addMyDatabase(dbName, dbAsset, 1, "r");
        database = database_admin.getMyDatabase(dbName);
        loadItemData(tableName);
    }
*/

    abstract public void loadItemData(String tableName);
/*        int size = database.getSize(tableName);

        List<String> name = database.getString(tableName, "name");
        List<String> image_name = database.getString(tableName, "image_name");
        List<Integer> item_kind = database.getInt(tableName, "item_kind");
        List<Integer> price = database.getInt(tableName, "price");

        for (int i = 0; i < size; i++) {
            datas.get(i).setName(name.get(i));
            datas.get(i).setImageName(image_name.get(i));
            datas.get(i).setPrice(price.get(i));
            datas.get(i).setItemKind(item_kind.get(i));
            datas.get(i).setItemImage(graphic.searchBitmap(image_name.get(i)));
        }
    }
*/
    public List<T> getItemDatas() {return datas;}

    //public static ItemData getDebugItem(){return null;}

}
