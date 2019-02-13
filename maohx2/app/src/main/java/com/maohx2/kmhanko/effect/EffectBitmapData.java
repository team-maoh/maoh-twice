package com.maohx2.kmhanko.effect;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2019/01/10.
 */

public class EffectBitmapData  {
    private static final int TRIMMED_BITMAP_MAX = 64;
    private static Graphic graphic;
    private static EffectDataAdmin effectDataAdmin;

    //このクラスのインスタンスはEffectDataとimageNameが決まれば一意に決まる
    private String imageName;//トリムする前の画像名
    private BitmapData[] trimmedBitmapData;
    private EffectData effectData;

    private boolean exist;

    public EffectBitmapData() {
        this.clear();
    }

    static public void setStatics(Graphic _graphic, EffectDataAdmin _effectDataAdmin) {
        graphic = _graphic;
        effectDataAdmin = _effectDataAdmin;
    }

    public void make(String _effectDataName, String _imageName, int widthNum, int heightNum, float modiExtendX, float modiExtendY) {
        float extendX = 1.0f;
        float extendY = 1.0f;


        //作成失敗チェック
        if ((effectData = effectDataAdmin.getEffectData(_effectDataName)) == null) {
            System.out.println("Takano: EffectBitmapData: EffectData名が不適切: " + _effectDataName);
        }
        if (graphic.searchBitmap(_imageName) == null) {
            System.out.println("Takano: EffectBitmapData: 画像名が不適切: " + _imageName);
        }
        imageName = _imageName;

        if (exist) {
            this.clear();
        }

        //EffectDataを見て、新規のextendX,Yの組があれば新たにwithScaleで読み出す。
        boolean loadFlag[] = new boolean[effectData.getSteps()];
        trimmedBitmapData = new BitmapData[effectData.getSteps()];
        for (int i = 0; i < loadFlag.length; i++) {
            loadFlag[i] = false;
        }
        int count = 0;

        while(count < effectData.getSteps()) {
            if (!loadFlag[count]) {
                extendX = effectData.getExtendX(count) * modiExtendX;
                extendY = effectData.getExtendY(count) * modiExtendY;
                BitmapData tempBitmapData = graphic.searchBitmapWithScale(imageName, extendX, extendY);
                int width = tempBitmapData.getWidth();
                int height = tempBitmapData.getHeight();

                //該当する倍率のステップの画像を全て埋め込む
                for (int i = count; i < effectData.getSteps(); i++) {
                    if (extendX == effectData.getExtendX(i) && extendY == effectData.getExtendY(i)) {
                        int imageID = effectData.getImageID(i);
                        trimmedBitmapData[i] = graphic.processTrimmingBitmapData(
                                tempBitmapData,
                                width / widthNum * (imageID % widthNum),
                                height / heightNum * (imageID / widthNum),
                                width / widthNum,
                                height / heightNum
                        );
                        loadFlag[i] = true;
                    }
                }
            }
            count++;

            for (int i = 0; i < loadFlag.length; i++) {
                if (!loadFlag[i]) {
                    continue;
                }
            }
            break;
        }

        exist = true;
        return;
    }

    public void clear() {
        imageName = null;
        effectData = null;
        trimmedBitmapData = null;
        exist = false;
    }

    public boolean equals(String _effectDataName, String _imageName) {
        if (!exist) { return false; }
        return _imageName.equals(imageName) && _effectDataName.equals(effectData.getName());
    }

    public BitmapData[] getTrimmedBitmapData() {
        return trimmedBitmapData;
    }

    public BitmapData getTrimmedBitmapData(int i) {
        return trimmedBitmapData[i];
    }

    public EffectData getEffectData() {
        return effectData;
    }

    public boolean isExist() {
        return exist;
    }

    public void release() {
        trimmedBitmapData = null;
    }

}
