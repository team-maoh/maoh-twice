package com.maohx2.kmhanko.effect;

/**
 * Created by user on 2018/01/19.
 */

import com.maohx2.ina.Activity.UnitedActivity;
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
    static final int TRIMMED_BITMAP_MAX= 128;

    //private List<Effect> effect = new ArrayList<Effect>();
    private EffectDataAdmin effectDataAdmin;
    private Graphic graphic;
    private Effect[] effects = new Effect[EFFECT_MAX];

    //private String[] trimmedBitmapName = new String[EFFECT_MAX];
    //private BitmapData[][] trimmedBitmapData = new BitmapData[EFFECT_MAX][TRIMMED_BITMAP_MAX];
    private EffectBitmapData effectBitmapDatas[] = new EffectBitmapData[TRIMMED_BITMAP_MAX];
    int dataIndex = 0;
    int effectIndex = 0;

    UnitedActivity unitedActivity;

    public enum EXTEND_MODE {
        BEFORE,
        AFTER
    }

    /*
    public EffectAdmin(Graphic _graphic) {
        graphic = _graphic;
    }
    */

    public EffectAdmin(Graphic _graphic, MyDatabaseAdmin _databaseAdmin, SoundAdmin _soundAdmin, UnitedActivity _unitedActivity) {
        //this(_graphic);
        graphic = _graphic;
        Effect.staticInit(_graphic, _soundAdmin);
        effectDataAdmin = new EffectDataAdmin(_databaseAdmin);
        for (int i = 0; i < effects.length; i++) {
            effects[i] = new Effect();
        }
        for (int i = 0; i < effectBitmapDatas.length; i++) {
            effectBitmapDatas[i] = new EffectBitmapData();
        }
        unitedActivity = _unitedActivity;

        EffectBitmapData.setStatics(graphic, effectDataAdmin);
    }

    public EffectAdmin(Graphic _graphic, EffectDataAdmin _effectDataAdmin, SoundAdmin _soundAdmin, UnitedActivity _unitedActivity) {
        //this(_graphic);
        graphic = _graphic;
        Effect.staticInit(_graphic, _soundAdmin);
        for (int i = 0; i < effects.length; i++) {
            effects[i] = new Effect();
        }
        for (int i = 0; i < effectBitmapDatas.length; i++) {
            effectBitmapDatas[i] = new EffectBitmapData();
        }
        unitedActivity = _unitedActivity;

        effectDataAdmin = _effectDataAdmin;
        EffectBitmapData.setStatics(graphic, effectDataAdmin);
    }

/*
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
*/

    private int searchEffectBitmapDataID(String _dataName, String _imageName, float _extendX, float _extendY, EXTEND_MODE extendMode) {
        //extendMode == AFTERの場合は、_extendX, _extendYは無視し、素の大きさから探すことになる

        int number = -1;
        for (int i = 0 ; i < effectBitmapDatas.length; i++) {
            if (effectBitmapDatas[i] != null) {
                if (effectBitmapDatas[i].equals(_dataName, _imageName)) {
                    if (effectBitmapDatas[i].getModifiX() == _extendX && effectBitmapDatas[i].getModifiY() == _extendY && extendMode == EXTEND_MODE.BEFORE && effectBitmapDatas[i].getExtendMode() == EXTEND_MODE.BEFORE) {
                        number = i;
                        break;
                    }
                    if (extendMode == EXTEND_MODE.AFTER && effectBitmapDatas[i].getExtendMode() == EXTEND_MODE.AFTER) {
                        number = i;
                        break;
                    }
                }
            }
        }
        return number;
    }

    // *** createEffect ***
    public int createEffect(String _dataName, String _imageName, int widthNum, int heightNum, EXTEND_MODE extendMode) {
        return createEffect(_dataName, _imageName, widthNum, heightNum, 1.0f, 1.0f, 0, extendMode);
    }

    public int createEffect(String _dataName, String _imageName, int widthNum, int heightNum, float extendX, float extendY, EXTEND_MODE extendMode) {
        return createEffect(_dataName, _imageName, widthNum, heightNum, extendX, extendY, 0, extendMode);
    }


    public int createEffect(String _dataName, String _imageName, int widthNum, int heightNum, int _kind, EXTEND_MODE extendMode) {
        return createEffect(_dataName, _imageName, widthNum, heightNum, 1.0f, 1.0f, _kind, extendMode);
    }

    public int createEffect(String _dataName, String _imageName, int widthNum, int heightNum, float extendX, float extendY, int kind, EXTEND_MODE extendMode) {
        int number = searchEffectBitmapDataID(_dataName, _imageName, extendX, extendY, extendMode);
        EffectBitmapData tempEffectBitmapData;
        if (number == -1) {
            tempEffectBitmapData = createEffectBitmapData(_dataName, _imageName, widthNum, heightNum, extendX, extendY, extendMode);
        } else {
            tempEffectBitmapData = effectBitmapDatas[number];
        }

        int makeEffectID = effectIndex;
        for(int i = 0; i < effects.length; i++) {
            if (!effects[(makeEffectID + i) % effects.length].isExist()) {
                makeEffectID = (makeEffectID + i) % effects.length;
                break;
            }
            if (i == effects.length - 1) {
                System.out.println("Takano: EffectAdmin: 表示Effect数の上限オーバーです.");
                makeEffectID = effectIndex;
                effects[makeEffectID].clear();
            }
        }

        effects[makeEffectID].create(tempEffectBitmapData, kind);
        effectIndex = (effectIndex+1)%EFFECT_MAX;

        return makeEffectID;
    }
    public EffectBitmapData createEffectBitmapData(String _dataName, String _imageName, int widthNum, int heightNum, EXTEND_MODE extendMode) {
        return createEffectBitmapData(_dataName, _imageName, widthNum, heightNum,1.0f, 1.0f, extendMode);
    }

    public EffectBitmapData createEffectBitmapData(String _dataName, String _imageName, int widthNum, int heightNum, float extendX, float extendY, EXTEND_MODE extendMode) {
        int number = searchEffectBitmapDataID(_dataName, _imageName, extendX, extendY, extendMode);
        if (number == -1) {
            int makeID = dataIndex;
            if (effectBitmapDatas[makeID].isExist()) {//エフェクトが埋まっている
                System.out.println("Takano: EffectAdmin: Effect用のBitmap格納個数の上限オーバーにより負荷が掛かっています.");
            }
            effectBitmapDatas[makeID].make(_dataName, _imageName, widthNum, heightNum, extendX, extendY, extendMode);

            System.out.println("Takano: EffectAdmin: NumOfActiveEffectBitmapData = " + String.valueOf(getNumOfActiveEffectBitmapData()));

            dataIndex = (dataIndex+1)%TRIMMED_BITMAP_MAX;
            return effectBitmapDatas[makeID];
        } else {
            return effectBitmapDatas[number];
        }
    }

    /*
    public BitmapData[] createEffectOnlyTrim(String _dataName, String _imageName, int widthNum, int heightNum) {
        int number = searchTrimmedBitmapDataID(_imageName);
        if (number == -1) {
            //

            BitmapData tempBitmapData[] = new BitmapData[TRIMMED_BITMAP_MAX];
            BitmapData _bitmapData = graphic.searchBitmapWithScale(_imageName,);
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
    */

    /*
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
    */

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

        int count = getNumOfActiveEffectBitmapData();
        unitedActivity.getUnitedSurfaceView().getDebugManager().updateDebugText(0,
                "Num of EffectBitmapData: " + count + "/" + effectBitmapDatas.length);
    }

    public int getNumOfActiveEffectBitmapData() {
        int count = 0;
        for (int i = 0 ; i < effectBitmapDatas.length; i++) {
            if (effectBitmapDatas[i] != null) {
                if (effectBitmapDatas[i].isExist()) {
                    count++;
                }
            }
        }
        return count;
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

    //effectBitmapDataのリリース関係

    //dataNameを指定して手動リリース
    public void clearEffectBitmapDataByDataName(String _dataName) {
        for (int i = 0; i < effectBitmapDatas.length; i++) {
            if (effectBitmapDatas[i] != null) {
                if (effectBitmapDatas[i].getEffectData() != null) {
                    if (effectBitmapDatas[i].getEffectDataName().equals(_dataName)) {
                        effectBitmapDatas[i].clear();
                    }
                }
            }
        }
    }
    public void clearEffectBitmapDataAll() {
        for (int i = 0; i < effectBitmapDatas.length; i++) {
            if (effectBitmapDatas[i] != null) {
                effectBitmapDatas[i].clear();
            }
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
    //public void setExtends(int i, float x, float y) {effects[i].setExtends(x, y);}
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

        effects = null;
        if (effectBitmapDatas != null) {
            for (int i = 0; i < effectBitmapDatas.length; i++) {
                if (effectBitmapDatas[i] != null) {
                    effectBitmapDatas[i].release();
                }
            }
            effectBitmapDatas = null;
        }

        /*
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
        */
    }

}
