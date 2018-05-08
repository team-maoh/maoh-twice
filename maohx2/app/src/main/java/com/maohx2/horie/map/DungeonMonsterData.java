package com.maohx2.horie.map;

/**
 * Created by horie on 2018/05/06.
 */

public class DungeonMonsterData {
    String monster_name;
    int type;

    public String getMonsterName(){
        return monster_name;
    }
    public int getType(){
        return type;
    }

    public void setMonsterName(String _monster_name){
        monster_name = _monster_name;
    }
    public void setType(int _type){
        type = _type;
    }
}
