//書きかけ
package com.maohx2.fuusya;

/**
 * Created by Fuusya on 2017/09/21.
 */

public class BagItem {

    int unique_id;//アイテム固有の番号
    //    int display_id;//バッグ内での並び順
//    int pocket_kind;//この番号のポケットに格納する
    int num;//所持数

    public void init() {

        num = 0;
    }

    public int getUniqueId() {
        return unique_id;
    }

    public void setUniqueId(int _unique_id) {
        unique_id = _unique_id;
    }

    /*
        public int getDisplayId(){
            return display_id;
        }
        public void setDisplayId(int input){
            display_id = input;
        }
    */
    public int getItemNum() {
        return num;
    }

    public void addItemNum(int add_num) {
        num += add_num;
    }

    public void release() {

    }

}
