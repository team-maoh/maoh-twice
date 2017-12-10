package com.maohx2.ina.ItemData;

import com.maohx2.kmhanko.database.NamedData;

/**
 * Created by user on 2017/11/05.
 */

public abstract class ItemData {
    String image_name;
    int price;
    String name;



    public ItemData() {}

    //Getter
    public String getName() { return name; }
    //Setter
    public void setName(String _name) {
        name = _name;
    }

    //Getter
    public String getImageName() {
        return image_name;
    }
    public int getPrice() {
        return price;
    }

    //Setter
    public void setImageName(String _image_name) {
        image_name = _image_name;
    }
    public void setPrice(int _price) {price = _price;}

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