package com.maohx2.ina.Arrange;

import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by ina on 2018/02/09.
 */

public class InventryData {

    ItemData item_data;
    int item_num;

    public InventryData(ItemData _item_data, int _item_num){
        item_data = _item_data;
        item_num = _item_num;
    }

    public int getItemNum(){return item_num;}
    public void setItemNum(int _item_num){item_num = _item_num;}

    public ItemData getItemData(){return item_data;}
    public void setItemData(ItemData _item_data){item_data = _item_data;}

}
