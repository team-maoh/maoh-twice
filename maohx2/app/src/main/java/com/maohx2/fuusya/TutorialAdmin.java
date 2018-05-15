package com.maohx2.fuusya;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.ItemData.ItemDataAdmin.graphic;

/**
 * Created by Fuusya on 2018/05/15.
 */

public class TutorialAdmin {

    String slide_name;
    boolean has_displayed_tutorial;

    public TutorialAdmin(Graphic _graphic, String _slide_name) {

        slide_name = _slide_name;

    }

    public void draw() {

//        if (playerStatus.getTutorialInDungeon() == 0) {
//
//            String bitmap_name = tutorial_name + String.valueOf(i_of_tutorial_bitmap);
//            BitmapData tutorial_bitmap = graphic.searchBitmap(bitmap_name);
//
//            if (tutorial_bitmap != null) {
//                graphic.bookingDrawBitmapData(tutorial_bitmap, 0, 0, 1, 1, 0, 255, true);
//            }
//
//            Constants.Touch.TouchState touch_state = dungeon_user_interface.getTouchState();
//
//            if (touch_state == Constants.Touch.TouchState.UP) {
//                i_of_tutorial_bitmap++;
//
//                if (i_of_tutorial_bitmap > NUM_OF_TUTORIAL_BITMAP) {
//                    playerStatus.setTutorialInDungeon(1);
//                    playerStatusSaver.save();
//
//                }
//            }
//        }

    }

    public boolean getHasDisplayedTutorial(){
        return has_displayed_tutorial;
    }

}
