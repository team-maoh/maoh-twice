package com.maohx2.ina;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;

/**
 * Created by ina on 2017/09/21.
 */

public final class Constants {

    private Constants() {
    }

    public static class Math {
        public static final double SIN[] = {0, 0.7071, 1, 0.7071, 0, -0.7071, -1, -0.7071};
        public static final double COS[] = {1, 0.7071, 0, -0.7071, -1, -0.7071, 0, 0.7071};
    }

    public static class Palette {
        public static final int CIRCLE_COLOR[] = {Color.argb(100, 255, 0, 0), Color.argb(100, 255, 165, 0), Color.argb(100, 255, 255, 0), Color.argb(100, 0, 128, 0), Color.argb(100, 0, 255, 255), Color.argb(255, 0, 0, 255), Color.argb(255, 128, 0, 128), Color.argb(255, 255, 0, 128)};
        public static final int PALETTE_ELEMENT_RADIUS_BIG = 30;
        public static final int PALETTE_ELEMENT_RADIUS_SMALL = 10;
        public static final int PALETTE_CENTER_RADIUS_BIG = 70;
        public static final int PALETTE_CENTER_RADIUS_SMALL = 30;
        public static final int PALETTE_ARRANGE_RADIUS = 120;

    }

    public static class Inventry {
        public static final int INVENTRY_CONTENT_MAX = 10;
        public static final int INVENTRY_DATA_MAX = 1000;

    }

    public static class Touch {
        public enum TouchState {
            DOWN,
            DOWN_MOVE,
            MOVE,
            UP,
            AWAY;
        }

        public enum TouchWay {
            DOWN_MOMENT,
            MOVE,
            UP_MOMENT,
            NOTHING,
        }
    }

    public static class Bitmap {
        public static final int BITMAP_DATA_INSTANCE = 1000;
        public static final int BOOKING_DATA_INSTANCE = 1000;
    }

    public static class BattleUnit {
        public static final int BATTLE_UNIT_MAX = 6;
    }

    public static class UnitStatus {

        public enum Status {
            ATTACK_FRAME,
            HP,
            ATTACK,
            DEFENSE,
            LUCK,
            SPEED,
            NUM_OF_STATUS,
        }

        public enum BonusStatus {
            BONUS_HP,
            BONUS_ATTACK,
            BONUS_DEFENSE,
            BONUS_SPEED,
            NUM_OF_BONUS_STATUS,
        }
    }

    public static class Draw {

        public enum DRAW_TASK_KIND {
            CIRCLE,
            RECT,
            BITMAP,
            TEXT,
        }
    }

    public static class PlateGroup {

        public enum GROUP_KIND {
            BOX_
        }
    }


    // *** takano ***
    public static class WorldMap {
        public enum SELECT_MODE {
            DUNGEON_SELECT,
            GEOMAP_SELECT,
            SELECT_MODE_NUM
        }
    }

    public static class Mode {
        public enum ACTIVATE {
            STOP,
            DRAW_ONLY,
            ACTIVE,
        }
    }

    // *** takanoここまで ***

}