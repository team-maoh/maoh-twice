package com.maohx2.horie.EquipTutorial;

/**
 * Created by horie on 2018/06/12.
 */

public class EquipTutorialSaveData {
    int is_tutorial_finish;

    public EquipTutorialSaveData(){}

    //マップがクリアされたかどうか返す
    public int getTutorialFinishStatus(){
        return is_tutorial_finish;
    }

    public void setTutorialFinishStatus(int _is_tutorial_finish){
        is_tutorial_finish = _is_tutorial_finish;
    }

    public Integer[] getSaveStatus(){
        return new Integer[] {is_tutorial_finish};
    }
}
