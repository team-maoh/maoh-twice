package com.maohx2.ina.Arrange;

import com.maohx2.ina.ItemData.ItemData;

/**
 * Created by ina on 2017/12/15.
 */

public class InventryContent {

    InventryData inventry_data;
    ItemData inventry_item;

    InventryContent(InventryData _inventry_data){

        inventry_data = _inventry_data;
    }

    ItemData getInventyItem(){

        return inventry_item;
    }

    void setInventyItem(ItemData _inventry_item){

        inventry_item = _inventry_item;
    }


}
