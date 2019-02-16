package com.maohx2.horie.map;

import android.graphics.Point;

/**
 * Created by horie on 2018/05/01.
 */

public class DungeonData {
    private String dungeon_name;
    private int mine_max_num;
    private int mine_min_num;
    private int floor_num;
    private String floor_tile_name;
    private String wall_tile_name;
    private String sidewall_tile_name;
    private Point map_size = new Point(0, 0);

    private int accessoryNum;
    private float accessoryRate;
    private int itemNum;
    private int trapNum;
    private int enemyNum;

    public DungeonData(){}

    public String getDungeon_name(){
        return dungeon_name;
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
    public int getAccessoryNum() {return accessoryNum;}
    public float getAccessoryRate() {return accessoryRate;}
    public int getItemNum() {return itemNum;}
    public int getTrapNum() {return trapNum;}
    public int getEnemyNum() {return enemyNum;}

    public void setDungeon_name(String _dungeon_name){
        dungeon_name = _dungeon_name;
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

    public void setAccessoryNum(int x) { accessoryNum = x;}
    public void setAccessoryRate(float x) { accessoryRate = x;}
    public void setItemNum(int x) { itemNum = x;}
    public void setTrapNum(int x) { trapNum = x;}
    public void setEnemyNum(int x) { enemyNum = x;}

    public void release() {
        System.out.println("takanoRelease : DungeonData");
        map_size = null;
        dungeon_name = null;
        floor_tile_name = null;
        wall_tile_name = null;
        sidewall_tile_name = null;
    }
}
