package com.maohx2.ina.ItemData;

import com.maohx2.ina.Constants.Item.*;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.kmhanko.PlayerStatus.PlayerStatus;
import com.maohx2.kmhanko.database.NamedData;

/**
 * Created by user on 2017/11/05.
 */

//test

public abstract class ItemData {
    protected String image_name;
    protected int price;
    protected String name;
    protected ITEM_KIND item_kind;
    protected BitmapData item_image;



    public ItemData() {}

    public String getName() { return name; }
    public void setName(String _name) {
        name = _name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int _price) {price = _price;}

    public String getImageName() {return image_name;}
    public void setImageName(String _image_name) {
        image_name = _image_name;
    }

    public ITEM_KIND getItemKind() {
        return item_kind;
    }
    public void setItemKind(ITEM_KIND _item_kind) {item_kind = _item_kind;}

    public BitmapData getItemImage() {
        return item_image;
    }
    public void setItemImage(BitmapData _item_image) {item_image = _item_image;}

    public int getPriceByPlayerStatus(PlayerStatus playerStatus) {
        return 0;
    }

}

/*
やたらごちゃごちゃしているので継承関係をまとめておく
NamedData //名前をデータとして含むデータベースの読み込みをサポートする
└ItemData //アイテム一般のデータ
　└*ExpendItemData //消耗品アイテムのデータ
　└*WeaponData
　└*GeoObjectData
NamedDataAdmin //NamedDataのまとまりの管理をサポートする
└ItemDataAdmin //ItemDataを管理する
　└*ExpendItemDataAdmin //ExpendItemDataを管理する
 */