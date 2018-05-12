package com.maohx2.horie.map;

/**
 * Created by horie on 2018/05/12.
 */

public class MapStatus {
    int is_clear[];
    int stage_num;

    public MapStatus(int _stage_num){
        stage_num = _stage_num;
        is_clear = new int[stage_num];
    }

    public int getMapStatus(int map_num){
        return is_clear[map_num];
    }

    public void setMapStatus(int _is_clear, int map_num){
        is_clear[map_num] = _is_clear;
    }

    public Integer[] getSaveStatus(int _stage_num){
        return new Integer[] {
                is_clear[_stage_num]
        };
    }
}
