package com.maohx2.horie.map;

import android.graphics.Point;

/**
 * Created by horie on 2018/05/01.
 */

public class DungeonData {
    String dungeon_name;
    String boss_name;
    String mid_boss_name;
    String monster_name;
    int mine_max_num;
    int mine_min_num;
    int floor_num;
    String floor_tile_name;
    String wall_tile_name;
    String sidewall_tile_name;
    Point map_size = new Point(0, 0);

    public DungeonData(){}

    public String getDungeon_name(){
        return dungeon_name;
    }
    public String getBoss_name(){
        return boss_name;
    }
    public String getMid_boss_name(){
        return mid_boss_name;
    }
    public String getMonster_name(){
        return monster_name;
    }
    public int getMine_max_num(){
        return mine_max_num;
    }
    public int getMine_min_num(){
        return mine_min_num;
    }
    public int getFloor_num(){
        return floor_num;
    }
    public String getFloor_tile_name(){
        return floor_tile_name;
    }
    public String getWall_tile_name(){
        return wall_tile_name;
    }
    public String getSidewall_tile_name(){
        return sidewall_tile_name;
    }
    public int getMap_size_x(){
        return map_size.x;
    }
    public int getMap_size_y(){
        return map_size.y;
    }

    public void setDungeon_name(String _dungeon_name){
        dungeon_name = _dungeon_name;
    }
    public void setBoss_name(String _boss_name){
        boss_name = _boss_name;
    }
    public void setMid_boss_name(String _mid_boss_name){
        mid_boss_name = _mid_boss_name;
    }
    public void setMonster_name(String _monster_name){
        monster_name = _monster_name;
    }
    public void setMine_max_num(int _mine_max_num){
        mine_max_num = _mine_max_num;
    }
    public void setMine_min_num(int _mine_min_num){
        mine_min_num = _mine_min_num;
    }
    public void setFloor_num(int _floor_num){
        floor_num = _floor_num;
    }
    public void setFloor_tile_name(String _floor_tile_name){
        floor_tile_name = _floor_tile_name;
    }
    public void setWall_tile_name(String _wall_tile_name){
        wall_tile_name = _wall_tile_name;
    }
    public void setSidewall_tile_name(String _sidewall_tile_name){
        sidewall_tile_name = _sidewall_tile_name;
    }
    public void setMap_size_x(int _map_size_x){
        map_size.x = _map_size_x;
    }
    public void setMap_size_y(int _map_size_y){
        map_size.y = _map_size_y;
    }
}
