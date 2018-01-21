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

    public EffectAdmin(Graphic _graphic, MyDatabaseAdmin _databaseAdmin, SoundAdmin _soundAdmin) {
        Effect.staticInit(_graphic, _soundAdmin);
        effectDataAdmin = new EffectDataAdmin(_databaseAdmin);
    }

    public EffectAdmin(Graphic _graphic, EffectDataAdmin _effectDataAdmin, SoundAdmin _soundAdmin) {
        Effect.staticInit(_graphic, _soundAdmin);
        effectDataAdmin = _effectDataAdmin;
    }

    public int createEffect(String _name, List<BitmapData> _bitmapData, List<String> _soundName) {
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
        _effect.setSoundName(_soundName);

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

    public EffectDataAdmin getEffectDataAdmin() {
        return effectDataAdmin;
    }
}
