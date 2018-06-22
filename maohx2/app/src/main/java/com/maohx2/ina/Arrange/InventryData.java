package com.maohx2.ina.Arrange;

import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by ina on 2018/02/09.
 */

public class InventryData {

    ItemData item_data;
    int item_num;

    //by kmhanko 売却画面で使用。このアイテムをいくつ売るかを格納する
    int sold_num;
    //by kmhanko UI取得したinventryDataが、どのインベントリに属するかを判定するために使用
    //(1画面に同時に、同じアイテム種を取り扱う二つのインベントリを設置するため)
    Inventry parentInventry;

    public InventryData(ItemData _item_data, int _item_num, Inventry _parentInventry){
        item_data = _item_data;
        item_num = _item_num;
        parentInventry = _parentInventry;
    }

    public int getItemNum(){return item_num;}
    public void setItemNum(int _item_num){item_num = _item_num;}

    public ItemData getItemData(){return item_data;}
    public void setItemData(ItemData _item_data){item_data = _item_data;}

    //by kmhanko
    public int getSoldNum(){ return sold_num;}
    public void setSoldNum(int _sold_num) {
        if (_sold_num <= item_num && _sold_num >= 0) {
            sold_num = _sold_num;
        }
    }
    public void addSoldNum(){ if (sold_num < item_num) { sold_num++; };}
    public void subSoldNum(){ if (sold_num >= 1) { sold_num--; }}

    public Inventry getParentInventry() {return parentInventry;}

    //by kmhanko
    public void delete() {
        item_data = null;
        item_num = 0;
        sold_num = 0;
    }
    public void release() {
        this.delete();
    }

}
