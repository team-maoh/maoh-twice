package com.maohx2.fuusya;

/**
 * Created by Fuusya on 2017/09/21.
 */

public class BagItemAdmin {

    //    int NUM_OF_POCKETS = 5;
    int SIZE_OF_POCKET = 100;
    int display_type;//1…入手順 / 2…id順
    BagItem[] bag_item = new BagItem[SIZE_OF_POCKET];
    int num_of_kinds;//所持アイテムの種類の数

    public void init() {

        num_of_kinds = 0;

        for (int i = 0; i < SIZE_OF_POCKET; i++) {
            bag_item[i] = new BagItem();
            bag_item[i].init();
        }
    }

    public void setItemIdToBagItem(int item_id) {

        int kari = 0;//あとで消す
        for (int i = 0; i < num_of_kinds; i++) {
            if (bag_item[i].getUniqueId() == item_id) {
                bag_item[i].addItemNum(1);
                kari = 1;
//                return;
            }
        }
        if (kari == 0) {
            bag_item[num_of_kinds].setUniqueId(item_id);
            bag_item[num_of_kinds].addItemNum(1);
            num_of_kinds++;
        }


        quick_sort(bag_item, 0, num_of_kinds-1);
        //コメントアウトするとアイテムが入手順で表示される

        for (int i = 0; i < num_of_kinds; i++) {
            System.out.println("unique_id = " + bag_item[i].getUniqueId());
            System.out.println("個数 = " + bag_item[i].getItemNum());
        }

    }

    static void quick_sort(BagItem[] d, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = d[(left + right) / 2].getUniqueId();
        int l = left, r = right, tmp;
        while (l <= r) {
            while (d[l].getUniqueId() < p) {
                l++;
            }
            while (d[r].getUniqueId() > p) {
                r--;
            }
            if (l <= r) {
                tmp = d[l].getUniqueId();
                d[l].setUniqueId(d[r].getUniqueId());
                d[r].setUniqueId(tmp);
                l++;
                r--;
            }
        }
        quick_sort(d, left, r);  // ピボットより左側をクイックソート
        quick_sort(d, l, right); // ピボットより右側をクイックソート
    }

    public void idSort(int num_of_kinds, BagItem bag_item[]) {


    /*
    for(int i = 0; i < num_of_kinds ; i++){
        bag_item[i].addItemNum(100);
    }
    */

    }



}
