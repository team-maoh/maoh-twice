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

    public static class DungeonKind {
        public enum DUNGEON_KIND {
            CHESS,
            DRAGON,
            FOREST,
            GOKI,
            HAUNTED,
            DNGEON_KIND_NUM
        }
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
        public static final int BATTLE_UNIT_MAX = 15;
    }

    public static class UnitStatus {

        public enum Status {
            ATTACK_FRAME,
            HP,
            ATTACK,
            DEFENSE,
            LUCK,
            SPEED,
            NUM_OF_STATUS;


            //enumへのキャスト用
            public static Status toEnum(int x) {
                for(Status type : Status.values()) {
                    if (type.ordinal() == x) {
                        return type;
                    }
                }
                return null;
            }

        }

        public enum BonusStatus {
            BONUS_HP,
            BONUS_ATTACK,
            BONUS_DEFENSE,
            BONUS_SPEED,
            NUM_OF_BONUS_STATUS,
        }
    }

    public enum UnitKind {
        PLAYER,
        ENEMY,
        ROCK,
        UNIT_KIND_NUM,
        NONE,
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


    public static class GAMESYSTEN_MODE {

        public enum DUNGEON_MODE {
            MAP,
            BUTTLE_INIT,
            BUTTLE,
            GEO_MINING_INIT,
            GEO_MINING,
        }

        public enum WORLD_MODE {
            DUNGEON_SELECT,
            GEO_MAP_SELECT,
            SHOP,
            GEO_MAP,
            EQUIP,
        }
    }

    public static class POPUP_WINDOW {
        public static final int MESS_LEFT = 400;
        public static final int MESS_RIGHT = 1200;
        public static final int MESS_UP = 300;
        public static final int MESS_BOTTOM = 500;

        public static final int OK_LEFT = 400;
        public static final int OK_RIGHT = 1200;
        public static final int OK_UP = 500;
        public static final int OK_BOTTOM = 600;

        public static final float TEXT_SIZE = 40f;
        public static final int MESS_ROW = 5;
    }

    public static class SELECT_WINDOW {
        public static final int MESS_LEFT = 200;
        public static final int MESS_RIGHT = 1400;
        public static final int MESS_UP = 300;
        public static final int MESS_BOTTOM = 500;

        public static final int YES_LEFT = 200;
        public static final int YES_RIGHT = 800;
        public static final int YES_UP = 500;
        public static final int YES_BOTTOM = 600;

        public static final int NO_LEFT = 800;
        public static final int NO_RIGHT = 1400;
        public static final int NO_UP = 500;
        public static final int NO_BOTTOM = 600;

        public static final float TEXT_SIZE = 40f;
        public static final int MESS_ROW = 5;
    }

    public static class Item {
        public enum ITEM_KIND {
            EXPEND,
            EQUIPMENT,
            GEO,
        }

        public enum EQUIPMENT_KIND {
            SWORD,
            WAND,
            AX,
            SPEAR,
            BOW,
            GUN,
            FIST,
            CLUB,
            WHIP,
            MUSIC,
            MONSTER,
            SHIELD,
            BARE,
            NUM,
        }
        public enum GEO_PARAM_KIND_NORMAL {
            HP,
            ATTACK,
            DEFENCE,
            LUCK,
            NUM;

            //enumへのキャスト用
            public static GEO_PARAM_KIND_NORMAL toEnum(int x) {
                for(GEO_PARAM_KIND_NORMAL type : GEO_PARAM_KIND_NORMAL.values()) {
                    if (type.ordinal() == x) {
                        return type;
                    }
                }
                return null;
            }
        }



        public enum GEO_PARAM_KIND_RATE {
            HP_RATE,
            ATTACK_RATE,
            DEFENCE_RATE,
            LUCK_RATE,
            NUM;

            //enumへのキャスト用
            public static GEO_PARAM_KIND_RATE toEnum(int x) {
                for(GEO_PARAM_KIND_RATE type : GEO_PARAM_KIND_RATE.values()) {
                    if (type.ordinal() == x) {
                        return type;
                    }
                }
                return null;
            }
        }

        public static final int DROP_NUM = 3;

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

    public static class PresentFlag {
        public enum FLAG {
            NO_DATA,
            YET,
            GOT,
        }
    }

    public static class GeoSlotParam {
        public static final float GEO_SLOT_SCALE = 4.0f;
    }

    // *** takanoここまで ***

}
