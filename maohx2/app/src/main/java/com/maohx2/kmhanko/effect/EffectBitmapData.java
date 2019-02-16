package com.maohx2.kmhanko.effect;

import android.media.effect.*;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.effect.EffectAdmin.EXTEND_MODE;

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

    private float modifiX;
    private float modifiY;

    private EffectAdmin.EXTEND_MODE extendMode;

    public EffectBitmapData() {
        this.clear();
    }

    static public void setStatics(Graphic _graphic, EffectDataAdmin _effectDataAdmin) {
        graphic = _graphic;
        effectDataAdmin = _effectDataAdmin;
    }

    public void make(String _effectDataName, String _imageName, final int widthNum, final int heightNum, float _modiExtendX, float _modiExtendY, EXTEND_MODE _extendMode) {
        extendMode = _extendMode;

        float extendX = 1.0f;
        float extendY = 1.0f;

        if (extendMode == EXTEND_MODE.BEFORE) {
            modifiX = _modiExtendX;
            modifiY = _modiExtendY;
        } else {
            modifiX = 1.0f;
            modifiY = 1.0f;
        }

        int sizeCount = 0;


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
            if (effectData.getTime(i) <= 0) {
                loadFlag[i] = true;
            } else {
                loadFlag[i] = false;
            }
        }
        int count = 0;

        Runtime runtime = Runtime.getRuntime();

        System.out.println("Takano: EffectBitmapData: Make:"
                + " dataName = " + _effectDataName + " , imageName = " + _imageName);

        if (extendMode == EXTEND_MODE.BEFORE) {
            boolean continueFlag = true;

            while (count < effectData.getSteps() || continueFlag) {
                if (!loadFlag[count]) {
                    System.out.println("Takano: EffectBitmapData: Make: count = " + String.valueOf(count));

                    extendX = effectData.getExtendX(count) * modifiX;
                    extendY = effectData.getExtendY(count) * modifiY;
                    BitmapData tempBitmapData = graphic.searchBitmapWithScale(imageName, extendX, extendY);
                    int width = tempBitmapData.getWidth();
                    int height = tempBitmapData.getHeight();

                    //該当する倍率のステップの画像を全て埋め込む
                    for (int i = count; i < effectData.getSteps(); i++) {
                        if (effectData.getExtendX(count) == effectData.getExtendX(i) && effectData.getExtendY(count) == effectData.getExtendY(i) && !loadFlag[i]) {
                            int imageID = effectData.getImageID(i);
                            System.out.println("Takano: EffectBitmapData: Make: i, imageID = " + String.valueOf(i) +","+String.valueOf(imageID));
                            System.out.println("Takano: EffectBitmapData: Make: width, height = " + String.valueOf(width / widthNum) +","+String.valueOf(height / heightNum));
                            System.out.println("Takano: EffectBitmapData: Make: ExX, ExY = " + String.valueOf(extendX) +","+String.valueOf(extendY));
                            System.out.println("Takano: EffectBitmapData: Make: ModX, ModY = " + String.valueOf(modifiX) +","+String.valueOf(modifiY));

                            trimmedBitmapData[i] = graphic.processTrimmingBitmapData(
                                    tempBitmapData,
                                    width / widthNum * (imageID % widthNum),
                                    height / heightNum * (imageID / widthNum),
                                    width / widthNum,
                                    height / heightNum
                            );
                            loadFlag[i] = true;
                            sizeCount = trimmedBitmapData[i].getBitmap().getByteCount();
                            System.out.println("Takano: EffectBitmapData: Make: SizeOfBitmap[KB] = " + String.valueOf((float) sizeCount / 1024.0f));
                            System.out.println(" Takano: EffectBitmapData: Make: TotalMemory[KB]: " + String.valueOf((int) runtime.totalMemory() / 1024.0));
                            System.out.println(" Takano: EffectBitmapData: Make: FreeMemory[KB]: " + String.valueOf((int) runtime.freeMemory() / 1024.0));
                            System.out.println(" Takano: EffectBitmapData: Make: UsedMemory[KB]: " + String.valueOf((int) (runtime.totalMemory() - runtime.freeMemory()) / 1024.0));
                            System.out.println(" Takano: EffectBitmapData: Make: MaxMemory[KB]: " + String.valueOf((int) runtime.maxMemory() / 1024.0));

                        }
                    }
                }
                count++;

                continueFlag = false;
                for (int i = 0; i < loadFlag.length; i++) {
                    if (!loadFlag[i]) {
                        continueFlag = true;
                        break;
                    }
                }
            }
        } else if (extendMode == EXTEND_MODE.AFTER) {

            BitmapData tempBitmapData = graphic.searchBitmap(imageName);
            int width = tempBitmapData.getWidth();
            int height = tempBitmapData.getHeight();

            for (int i = 0; i < effectData.getSteps(); i++) {
                if (!loadFlag[i]) {
                    int imageID = effectData.getImageID(i);
                    trimmedBitmapData[i] = graphic.processTrimmingBitmapData(
                            tempBitmapData,
                            width / widthNum * (imageID % widthNum),
                            height / heightNum * (imageID / widthNum),
                            width / widthNum,
                            height / heightNum
                    );
                    sizeCount = trimmedBitmapData[i].getBitmap().getByteCount();
                    System.out.println("Takano: EffectBitmapData: Make: SizeOfBitmap[KB] = " + String.valueOf((float) sizeCount / 1024.0f));
                    System.out.println(" Takano: EffectBitmapData: Make: TotalMemory[KB]: " + String.valueOf((int) runtime.totalMemory() / 1024.0));
                    System.out.println(" Takano: EffectBitmapData: Make: FreeMemory[KB]: " + String.valueOf((int) runtime.freeMemory() / 1024.0));
                    System.out.println(" Takano: EffectBitmapData: Make: UsedMemory[KB]: " + String.valueOf((int) (runtime.totalMemory() - runtime.freeMemory()) / 1024.0));
                    System.out.println(" Takano: EffectBitmapData: Make: MaxMemory[KB]: " + String.valueOf((int) runtime.maxMemory() / 1024.0));
                    loadFlag[i] = true;
                }
            }
        }

        for (int i = 0; i < loadFlag.length; i++) {
            if (!loadFlag[i]) {
                System.out.println("Takano: EffectBitmapData: Make: 適切にエフェクト画像が読み込まれなかった");
            }
        }

        exist = true;
        return;
    }

    public void clear() {
        imageName = null;
        effectData = null;
        if (trimmedBitmapData != null) {
            for (int i = 0; i < trimmedBitmapData.length; i++) {
                if (trimmedBitmapData[i] != null) {
                    trimmedBitmapData[i].releaseBitmap();
                    trimmedBitmapData[i] = null;
                }
            }
            trimmedBitmapData = null;
        }
        exist = false;
    }

    public boolean equals(String _effectDataName, String _imageName) {
        if (!exist) { return false; }
        return _imageName.equals(imageName) && _effectDataName.equals(effectData.getName());
    }

    public String getEffectDataName() {
        return effectData.getName();
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

    public EXTEND_MODE getExtendMode() {
        return extendMode;
    }

    public float getModifiX() {
        return modifiX;
    }
    public float getModifiY() {
        return modifiY;
    }

    public void release() {
        clear(); trimmedBitmapData = null;
    }

}
