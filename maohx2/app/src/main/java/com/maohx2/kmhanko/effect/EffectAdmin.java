package com.maohx2.kmhanko.effect;

/**
 * Created by user on 2018/01/19.
 */

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.sound.SoundAdmin;

import java.util.ArrayList;
import java.util.List;

//Effectの実体を持つ。外部からの依頼でEffectを生成したりする。Effectのupdate,drawを呼ぶ。
public class EffectAdmin {

    private List<Effect> effect = new ArrayList<Effect>();
    private EffectDataAdmin effectDataAdmin;
    private Graphic graphic;

    public EffectAdmin(Graphic _graphic) {
        graphic = _graphic;
    }

    public EffectAdmin(Graphic _graphic, MyDatabaseAdmin _databaseAdmin, SoundAdmin _soundAdmin) {
        this(_graphic);
        Effect.staticInit(_graphic, _soundAdmin);
        effectDataAdmin = new EffectDataAdmin(_databaseAdmin);
    }

    public EffectAdmin(Graphic _graphic, EffectDataAdmin _effectDataAdmin, SoundAdmin _soundAdmin) {
        this(_graphic);
        Effect.staticInit(_graphic, _soundAdmin);
        effectDataAdmin = _effectDataAdmin;
    }

    public int createEffect(String _name, String _imageName, int widthNum, int heightNum) {

        List<BitmapData> tempBitmapData = new ArrayList<BitmapData>();
        BitmapData _bitmapData = graphic.searchBitmap(_imageName);
        int x = _bitmapData.getWidth();
        int y = _bitmapData.getHeight();

        for (int i = 0; i < heightNum; i++ ) {
            for (int j = 0; j < widthNum; j++ ) {
                tempBitmapData.add(graphic.processTrimmingBitmapData(_bitmapData, x/widthNum * j, y/heightNum * i, x/widthNum, y/heightNum));
            }
        }

        return createEffect(_name, tempBitmapData);
    }

    public int createEffect(String _name, String _imageName, int widthNum) {
        return createEffect(_name, _imageName, widthNum, 1);
    }

/*
    public int createEffect(String _name, String _imageName, int widthNum) {

        List<BitmapData> tempBitmapData = new ArrayList<BitmapData>();
        BitmapData _bitmapData = graphic.searchBitmap(_imageName);
        int x = _bitmapData.getWidth();
        int y = _bitmapData.getHeight();

        for (int i = 0; i < widthNum; i ++ ) {
            tempBitmapData.add(graphic.processTrimmingBitmapData(_bitmapData, x/widthNum * i, 0, x/widthNum, y));
        }
        return createEffect(_name, tempBitmapData);
    }
    */

    public int createEffect(String _name, List<BitmapData> _bitmapData) {
        Effect _effect = null;
        int effectID = -1;

        for (int i = 0; i < effect.size(); i++) {
            if (!effect.get(i).isExist()) {
                _effect = effect.get(i);
                effectID = i;
                break;
            }
        }
        if (_effect == null) {
            effect.add(new Effect());
            effectID = effect.size() - 1;
            _effect = effect.get(effectID);
        }

        _effect.create(effectDataAdmin.getEffectData(_name));
        _effect.setBitmapData(_bitmapData);

        return effectID;
    }

    public void draw() {
        //existかどうかは内部処理している
        for (int i = 0; i < effect.size(); i++) {
            effect.get(i).draw();
        }
    }

    public void update() {
        //existかどうかは内部処理している
        for (int i = 0; i < effect.size(); i++) {
            effect.get(i).update();
        }
    }

    public Effect getEffect(int i) {
        return effect.get(i);
    }

    public List<Effect> getEffectList() {
        return effect;
    }

    //全てのエフェクトを消す
    public void clearAllEffect() {
        for (int i = 0; i < effect.size(); i++) {
            effect.get(i).clear();
        }
    }

    //全てのエフェクトを一時停止する
    //仕様として、一時停止しているエフェクトとそうでないエフェクトが一律で一時停止し、復活させると元から一時停止だったエフェクトも一時停止が解除される
    public void pauseAllEffect() {
        for (int i = 0; i < effect.size(); i++) {
            effect.get(i).pause();
        }
    }

    public void restartAllEffect() {
        for (int i = 0; i < effect.size(); i++) {
            effect.get(i).restart();
        }
    }

    public void startEffect(int i) {
        effect.get(i).start();
    }
    public void hideEffect(int i) {
        effect.get(i).hide();
    }
    public void pauseEffect(int i) {
        effect.get(i).setPause(true);
    }
    public void restartEffect(int i) {
        effect.get(i).setPause(false);
    }
    public void setPosition(int i, int x, int y) {
        effect.get(i).setPosition(x, y);
    }
    public void setPosition(int i, int x, int y, float rad) {
        effect.get(i).setPosition(x, y, rad);
    }
    public void clearEffect(int i) {
        effect.get(i).clear();
    }

    public EffectDataAdmin getEffectDataAdmin() {
        return effectDataAdmin;
    }
}
