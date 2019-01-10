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

    static final int EFFECT_MAX = 128;
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


    private int searchTrimmedBitmapDataID(String _name) {
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

    // *** createEffect ***
    public int createEffect(String _dataName, String _imageName, int widthNum, int heightNum) {
        return createEffect(_dataName, _imageName, widthNum, heightNum, 0);
    }

    public int createEffect(String _dataName, String _imageName, int widthNum, int heightNum, int kind) {
        int number = searchTrimmedBitmapDataID(_imageName);
        if (number == -1) {
            BitmapData tempBitmapData[] = createEffectOnlyTrim(_imageName, widthNum, heightNum);
            return createEffect(_dataName, tempBitmapData, kind);
        } else {
            return createEffect(_dataName, trimmedBitmapData[number], kind);
        }
    }
    public BitmapData[] createEffectOnlyTrim(String _imageName, int widthNum, int heightNum) {
        int number = searchTrimmedBitmapDataID(_imageName);
        if (number == -1) {
            BitmapData tempBitmapData[] = new BitmapData[TRIMMED_BITMAP_MAX];
            BitmapData _bitmapData = graphic.searchBitmap(_imageName);
            //BitmapData _bitmapData = graphic.searchBitmapWithScale(_imageName,2,2);
            int x = _bitmapData.getWidth();
            int y = _bitmapData.getHeight();

            int count = 0;
            for (int i = 0; i < heightNum; i++) {
                for (int j = 0; j < widthNum; j++) {
                    tempBitmapData[count] = graphic.processTrimmingBitmapData(_bitmapData, x / widthNum * j, y / heightNum * i, x / widthNum, y / heightNum);
                    count++;
                }
            }
            trimmedBitmapName[dataIndex] = _imageName;
            trimmedBitmapData[dataIndex] = tempBitmapData;
            dataIndex++;
            dataIndex %= EFFECT_MAX;
            return tempBitmapData;
        }
        return null;
    }

    public int createEffect(String _dataName, BitmapData[] _bitmapData, int kind) {
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

        _effect.create(effectDataAdmin.getEffectData(_dataName), kind);
        _effect.setBitmapData(_bitmapData);

        return effectID;
    }

    // ** draw, update ***

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

    //全てのエフェクトを消す
    public void clearAllEffect() {
        for (int i = 0; i < effects.length; i++) {
            effects[i].clear();
        }
    }

    public void clearKindEffect(int kind) {
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].getKind() == kind) {
                effects[i].clear();
            }
        }
    }

    public void hideKindEffect(int kind) {
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].getKind() == kind) {
                effects[i].hide();
            }
        }
    }


    public void restartKindEffect(int kind) {
        for (int i = 0; i < effects.length; i++) {
            if (effects[i].getKind() == kind) {
                effects[i].restart();
            }
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
    public void setExtends(int i, float x, float y) {effects[i].setExtends(x, y);}
    public void clearEffect(int i) {
        effects[i].clear();
    }

    public EffectDataAdmin getEffectDataAdmin() {
        return effectDataAdmin;
    }
    public Effect getEffect(int i) {
        return effects[i];
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
