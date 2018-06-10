package com.maohx2.horie.map;

/**
 * Created by horie on 2018/05/12.
 */

public class MapStatus {
    int is_clear[];
    int is_tutorial_finish[];
    int stage_num;

    public MapStatus(int _stage_num){
        stage_num = _stage_num;
        is_clear = new int[stage_num];
        is_tutorial_finish = new int[stage_num];
    }

    //マップがクリアされたかどうか返す
    public int getMapClearStatus(int _map_num){
        return is_clear[_map_num];
    }

    public int getTutorialFinishStatus(int _map_num){
        return is_tutorial_finish[_map_num];
    }

    public void setMapClearStatus(int _is_clear, int map_num){
        is_clear[map_num] = _is_clear;
    }

    public void setTutorialFinishStatus(int _is_tutorial_finish, int map_num){
        is_tutorial_finish[map_num] = _is_tutorial_finish;
//        throw new Error("testError");
    }

    public Integer[] getSaveStatus(int _stage_num){
        return new Integer[] {
                is_clear[_stage_num],
                is_tutorial_finish[_stage_num]
        };
    }
}
