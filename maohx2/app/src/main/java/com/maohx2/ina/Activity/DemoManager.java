package com.maohx2.ina.Activity;

/**
 * Created by user on 2019/01/24.
 */

public class DemoManager {

    boolean startGameDemoEndFlag = false;
    boolean worldGameDemoEndFlag = false;
    boolean dungeonGameDemoEndFlag = false;

    public DemoManager() {
    }

    public void startGameDemo() {
        if (startGameDemoEndFlag) {
            return;
        }
        startGameDemoEndFlag = true;
    }
    public void worldGameDemo() {
        if (worldGameDemoEndFlag) {
            return;
        }
        worldGameDemoEndFlag = true;
    }
    public void dungeonGameDemo() {
        if (dungeonGameDemoEndFlag) {
            return;
        }
        dungeonGameDemoEndFlag = true;
    }


    // プレイヤーステータス 書き換え
    public void playerStatusDemo() {
    }

    // 所持アイテム(消費)

    // アイテムスロットへのセット

    // 所持武器

    // 武器スロットへのセット

    // 所持ジオ

    // ジオスロットへのセット

    // マップ解放

    // ジオスロットの解放

    // 会話イベントのフラグ

    // 魔王討伐数

    // 献上ポイント

}
