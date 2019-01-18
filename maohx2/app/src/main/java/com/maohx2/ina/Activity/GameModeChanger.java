package com.maohx2.ina.Activity;

import com.maohx2.ina.Constants;
import com.maohx2.ina.ChangeMovie;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.sound.SoundAdmin;

/**
 * Created by user on 2019/01/18.
 */

public class GameModeChanger {
    UnitedSurfaceView unitedSurfaceView;
    ChangeMovie changeMovie;
    Graphic graphic;
    SoundAdmin soundAdmin;

    public GameModeChanger(UnitedSurfaceView _unitedSurfaceView, Graphic _graphic, SoundAdmin _soundAdmin) {
        unitedSurfaceView = _unitedSurfaceView;
        graphic = _graphic;
        soundAdmin = _soundAdmin;
        changeMovie = new ChangeMovie(graphic, soundAdmin);
    }

    UnitedSurfaceView.GAME_SYSTEM_MODE gameSystemModeReserve = UnitedSurfaceView.GAME_SYSTEM_MODE.NONE;

    public void toWorldGameMode() {
        gameSystemModeReserve = UnitedSurfaceView.GAME_SYSTEM_MODE.WORLD;
    }

    public void toDungeonGameMode(Constants.DungeonKind.DUNGEON_KIND _dungeon_kind) {
        unitedSurfaceView.setDungeonKind(_dungeon_kind);
        gameSystemModeReserve = UnitedSurfaceView.GAME_SYSTEM_MODE.DUNGEON;
    }

    public void toChangeGameMode() {//これ自体は毎ループ呼ばれている
        if (gameSystemModeReserve != UnitedSurfaceView.GAME_SYSTEM_MODE.NONE) {
            if (changeMovie.update(false, true)) {
                switch (gameSystemModeReserve) {
                    case START:
                        //ここには来ない(ゲーム画面からスタート画面に戻れるようにするなら必要)
                        gameSystemModeReserve = UnitedSurfaceView.GAME_SYSTEM_MODE.NONE;
                        unitedSurfaceView.getUserInterface().touchReset();
                        break;
                    case WORLD:
                        unitedSurfaceView.releaseNowGameSystem();
                        unitedSurfaceView.runWorldGameSystem();
                        gameSystemModeReserve = UnitedSurfaceView.GAME_SYSTEM_MODE.NONE;
                        unitedSurfaceView.getUserInterface().touchReset();
                        break;
                    case DUNGEON:
                        unitedSurfaceView.releaseNowGameSystem();
                        unitedSurfaceView.runDungeonGameSystem();
                        gameSystemModeReserve = UnitedSurfaceView.GAME_SYSTEM_MODE.NONE;
                        unitedSurfaceView.getDungeonUserInterface().touchReset();
                        unitedSurfaceView.getUserInterface().touchReset();
                        break;
                    default:
                        break;
                }
            }
        }
    }


    public void draw() {
        changeMovie.draw();
    }

    public void release() {
        changeMovie.release();
        changeMovie = null;
    }

}
