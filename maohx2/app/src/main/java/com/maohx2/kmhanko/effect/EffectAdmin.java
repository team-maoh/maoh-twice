package com.maohx2.kmhanko.effect;

/**
 * Created by user on 2018/01/19.
 */

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.Constants.EffectConstant;

import java.util.ArrayList;
import java.util.List;

//Effectの実体を持つ。外部からの依頼でEffectを生成したりする。Effectのupdate,drawを呼ぶ。
public class EffectAdmin {

    static final int EFFECT_MAX = 256;
    static final int TRIMMED_BITMAP_MAX= 64;

    //private List<Effect> effect = new ArrayList<Effect>();
    private EffectDataAdmin effectDataAdmin;
    private Graphic graphic;
    private Effect[] effects = new Effect[EFFECT_MAX];

    private String[] trimmedBitmapName = new String[EFFECT_MAX];
    private BitmapData[][] trimmedBitmapData = new BitmapData[EFFECT_MAX][TRIMMED_BITMAP_MAX];
    int dataIndex = 0;


    public EffectAdmin(Graphic _graphic) {
        graphic = _graphic;
    }

    public EffectAdmin(Graphic _graphic, MyDatabaseAdmin _databaseAdmin, SoundAdmin _soundAdmin) {
        this(_graphic);
        Effect.staticInit(_graphic, _soundAdmin);
        effectDataAdmin = new EffectDataAdmin(_databaseAdmin);
        for (int i = 0; i < effects.length; i++) {
            effects[i] = new Effect();
        }
    }

    public EffectAdmin(Graphic _graphic, EffectDataAdmin _effectDataAdmin, SoundAdmin _soundAdmin) {
        this(_graphic);
        Effect.staticInit(_graphic, _soundAdmin);
        effectDataAdmin = _effectDataAdmin;
    }

    public int createEffect(String _name, String _imageName, int widthNum, int heightNum) {
        int number = searchEffect(_name);
        if (number == -1) {
            BitmapData tempBitmapData[] = createEffectOnlyTrim(_name, _imageName, widthNum, heightNum);
            return createEffect(_name, tempBitmapData);
        } else {
            return createEffect(_name, trimmedBitmapData[number]);
        }
    }

    public int searchEffect(String _name) {
        int number = -1;
        for (int i = 0 ; i < trimmedBitmapName.length; i++) {
            if ( trimmedBitmapName[i] != null) {
                if (_name.equals(trimmedBitmapName[i])) {
                    number = i;
                }
            }
        }
        return number;
    }

    public BitmapData[] createEffectOnlyTrim(String _name, String _imageName, int widthNum, int heightNum) {
        int number = searchEffect(_name);
        if (number == -1) {
            BitmapData tempBitmapData[] = new BitmapData[TRIMMED_BITMAP_MAX];
            BitmapData _bitmapData = graphic.searchBitmap(_imageName);
            int x = _bitmapData.getWidth();
            int y = _bitmapData.getHeight();

            int count = 0;
            for (int i = 0; i < heightNum; i++) {
                for (int j = 0; j < widthNum; j++) {
                    tempBitmapData[count] = graphic.processTrimmingBitmapData(_bitmapData, x / widthNum * j, y / heightNum * i, x / widthNum, y / heightNum);
                    count++;
                }
            }
            trimmedBitmapName[dataIndex] = _name;
            trimmedBitmapData[dataIndex] = tempBitmapData;
            dataIndex++;
            dataIndex %= EFFECT_MAX;
            return tempBitmapData;
        }
        return null;
    }

    public int createEffect(String _name, String _imageName, int widthNum) {
        return createEffect(_name, _imageName, widthNum, 1);
    }

    public int createEffect(String _name, BitmapData[] _bitmapData) {
        Effect _effect = null;
        int effectID = -1;

        for (int i = 0; i < effects.length; i++) {
            if (!effects[i].isExist()) {
                _effect = effects[i];
                effectID = i;
                break;
            }
        }
        if (_effect == null) {
            //エフェクト数の上限オーバー
            return effectID;
        }

        _effect.create(effectDataAdmin.getEffectData(_name));
        _effect.setBitmapData(_bitmapData);

        return effectID;
    }

    public void draw() {
        //existかどうかは内部処理している
        for (int i = 0; i < effects.length; i++) {
            effects[i].draw();
        }
    }

    public void update() {
        //existかどうかは内部処理している
        for (int i = 0; i < effects.length; i++) {
            effects[i].update();
        }
    }

    public Effect getEffect(int i) {
        return effects[i];
    }

    //全てのエフェクトを消す
    public void clearAllEffect() {
        for (int i = 0; i < effects.length; i++) {
            effects[i].clear();
        }
    }

    //全てのエフェクトを一時停止する
    //仕様として、一時停止しているエフェクトとそうでないエフェクトが一律で一時停止し、復活させると元から一時停止だったエフェクトも一時停止が解除される
    public void pauseAllEffect() {
        for (int i = 0; i < effects.length; i++) {
            effects[i].pause();
        }
    }

    public void restartAllEffect() {
        for (int i = 0; i < effects.length; i++) {
            effects[i].restart();
        }
    }

    public void startEffect(int i) {
        effects[i].start();
    }
    public void hideEffect(int i) {
        effects[i].hide();
    }
    public void pauseEffect(int i) {
        effects[i].setPause(true);
    }
    public void restartEffect(int i) {
        effects[i].setPause(false);
    }
    public void setPosition(int i, int x, int y) {
        effects[i].setPosition(x, y);
    }
    public void setPosition(int i, int x, int y, float rad) {
        effects[i].setPosition(x, y, rad);
    }
    public void clearEffect(int i) {
        effects[i].clear();
    }

    public EffectDataAdmin getEffectDataAdmin() {
        return effectDataAdmin;
    }

    public void release() {
        System.out.println("takanoRelease : EffectAdmin");
        if (effectDataAdmin != null) {
            effectDataAdmin.release();
        }
        if (effects != null) {
            for (int i = 0; i < effects.length; i++) {
                if (effects[i] != null) {
                    effects[i].release();
                }
            }
            effects = null;
        }

        if (trimmedBitmapData != null) {
            for (int i = 0; i < trimmedBitmapData.length; i++) {
                for (int j = 0; j < trimmedBitmapData[i].length; j++) {
                    if (trimmedBitmapData[i][j] != null) {
                        trimmedBitmapData[i][j].release();
                    }
                }
            }
            trimmedBitmapData = null;
        }

        if (trimmedBitmapName != null) {
            for (int i = 0; i < trimmedBitmapName.length; i++) {
                if (trimmedBitmapName[i] != null) {
                    trimmedBitmapName[i] = null;
                }
            }
            trimmedBitmapName = null;
        }
    }

}
