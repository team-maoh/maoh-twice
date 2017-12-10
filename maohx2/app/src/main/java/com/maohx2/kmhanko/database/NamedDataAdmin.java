package com.maohx2.kmhanko.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/11/05.
 */

//nameカラムを持つデータベースについてサポートする
public abstract class NamedDataAdmin<T extends NamedData> {

    public List<T> datas = new ArrayList<>();
    public MyDatabase database;


    public void loadData(String table_name) {
        int size = database.getSize(table_name);
        List<String> name = database.getString(table_name, "name");
        for (int i = 0; i < size; i++) {
            datas.get(i).setName(name.get(i));
        }
    }


    public T getOneDataByName(String _name) {
        for(int i = 0; i < datas.size(); i++) {
            if (datas.get(i).getName().equals(_name)) {
                return (T)datas.get(i);
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


    public List<T> getDatasBySQL(String table_name, String w_script) {
        List<Integer> rowid
                = database.getRowID(table_name, w_script);
        List<T> buf_itemdata = new ArrayList<>(rowid.size());
        for(int i = 0; i < rowid.size(); i++) {
            buf_itemdata.add((T)datas.get(rowid.get(i)));
        }
        return buf_itemdata;
    }

    public List<T> getDatas() {
        return datas;
    }

}
