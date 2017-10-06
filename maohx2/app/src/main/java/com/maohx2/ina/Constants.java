package com.maohx2.ina;

import android.graphics.Color;

/**
 * Created by ina on 2017/09/21.
 */

public final class Constants {

    private Constants() {
    }

    public static class Math{
        public static final double SIN[] = {0, 0.7071, 1, 0.7071, 0, -0.7071, -1, -0.7071};
        public static final double COS[] = {1, 0.7071, 0, -0.7071, -1, -0.7071, 0, 0.7071};
    }

    public static class Palette{
        public static final int CIRCLE_COLOR[] = {Color.argb(100, 255, 0, 0), Color.argb(100, 255, 165, 0), Color.argb(100, 255, 255, 0), Color.argb(100, 0, 128, 0), Color.argb(100, 0, 255, 255), Color.argb(255, 0, 0, 255), Color.argb(255, 128, 0, 128), Color.argb(255, 255, 0, 128)};

    }

    public static class Touch {
        public enum TouchState {
            DOWN,
            DOWN_MOVE,
            MOVE,
            UP,
            AWAY;
        }
    }

    public static class BattleUnit {
        public static final int BATTLE_UNIT_MAX = 6;
    }
}