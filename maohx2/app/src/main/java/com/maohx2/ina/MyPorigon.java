package com.maohx2.ina;


/**
 * Created by ina on 2017/09/22.
 */

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * (矩形)板ポリゴン
 *
 * @author オレがガンダムだ
 *
 */
public class MyPorigon {

    /**
     * マトリックスのクラス
     * マトリックスと名付けてますが、単に、座標とか諸々をまとめた構造体みたいなものです
     */
    private class Graph2DMatrix {
        /**
         * X座標
         */
        public float posX = 0.0f;
        /**
         * Y座標
         */
        public float posY = 0.0f;
        /**
         * 拡大・縮小率X(1.0で等倍)
         */
        public float exRateX = 1.0f;
        /**
         * 拡大・縮小率Y(1.0で等倍)
         */
        public float exRateY = 1.0f;
        /**
         * 角度
         */
        public float degree = 0.0f;

        /**
         * 設定します
         * @param sizeW:画像１コマ分のサイズ(幅)px
         * @param sizeH:画像１コマ分のサイズ(高)px
         * @param posX:X座標(左)
         * @param posY:Y座標(上)
         * @param exRateX:拡大・縮小率X
         * @param exRateY:拡大・縮小率Y
         * @param degree:角度
         * @param isLRReverse:trueで左右反転
         * @param isTBReverse:trueで上下反転
         */
        public void set(int sizeW, int sizeH, float posX, float posY, float exRateX, float exRateY, float degree, boolean isLRReverse, boolean isTBReverse) {
            this.posX = posX;
            this.posY = posY;
            this.exRateX = exRateX;
            this.exRateY = exRateY;
            this.degree = degree;
// 左右反転するときのみ
            if(isLRReverse) {
// 拡大・縮小率を負にすると反転する
                this.exRateX *= -1.0f;
// 左右反転に伴ってサイズ(幅)の分だけ移動させる(そうしないと左に寄ってしまう)
                this.posX += sizeW;
            }
// 上下反転するときのみ
            if(isTBReverse) {
// 拡大・縮小率を負にすると反転する
                this.exRateY *= -1.0f;
// 左右反転に伴ってサイズ(高)の分だけ移動させる(そうしないと上に寄ってしまう)
                this.posY += sizeH;
            }
        }
    }

    /**
     * マトリックスのインスタンス
     */
    private Graph2DMatrix matrix = new Graph2DMatrix();
    /**
     * UV配列オブジェクト(U:ヨコ、V:タテとする左上原点の画像切り出し範囲。画像全体をサイズW＝1.0f、サイズH=1.0fとして表現される)
     */
    private FloatBuffer uvArray = null;
    /**
     * 頂点配列オブジェクト
     */
    private FloatBuffer vertexArray = null;

    /**
     * サイズ(幅)
     */
    private final int sizeW;
    /**
     * サイズ(高)
     */
    private final int sizeH;
    /**
     * 半分のサイズ(幅)
     */
    private final int halfSizeW;
    /**
     * 半分のサイズ(高)
     */
    private final int halfSizeH;

    /**
     * コンストラクタ
     * @param sizeW:テクスチャの幅(pixel)
     * @param sizeH:テクスチャの高さ(pixel)
     */
    public MyPorigon(int sizeW, int sizeH) {
        this.sizeW = sizeW;
        this.sizeH = sizeH;
// 半分のサイズを設定
        this.halfSizeW = this.sizeW / 2;
        this.halfSizeH = this.sizeH / 2;

// UV配列オブジェクトを生成する
        this.uvArray = this.createUVArray();
// 頂点配列オブジェクトを生成
        this.vertexArray = this.createVertexArray();
    }

    /**
     * マトリックスを設定します
     * @param gl:GL10
     * @param posX:X座標
     * @param posY:Y座標
     * @param exRateX:X方向拡大縮小率(1.0で等倍)
     * @param exRateY:Y方向拡大縮小率(1.0で等倍)
     * @param degree:角度(0～360)
     * @param isLRReverse:左右反転するかどうか
     * @param isTBReverse:上下反転するかどうか
     */
    public void setMatrix(GL10 gl, float posX, float posY, float exRateX, float exRateY, float degree, boolean isLRReverse, boolean isTBReverse) {
        this.matrix.set(this.sizeW, this.sizeH, posX, posY, exRateX, exRateY, degree, isLRReverse, isTBReverse);
    }

    /**
     * 描画します
     * @param gl:GL10
     * @param textureID:テクスチャID(nullでテクスチャ無し)
     */
    public void draw(GL10 gl, Integer textureID) {

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 頂点配列を有効にする
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ブレンドを有効にする
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 色と透明度の指定
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// まっ白
        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // 最後の引数を0.0f～1.0fの範囲で透明度を表せます

        if(textureID != null) {
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// テクスチャ、テクスチャ座標配列を有効にする
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// テクスチャ画像の指定
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// テクスチャの指定
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
// 0番テクスチャを有効化(この板ポリゴンにテクスチャを重ねて貼る場合に0～Nで指定するが今回は0しかない)
            gl.glActiveTexture(GL10.GL_TEXTURE0); // 0はデフォルトで有効のためこの行はコメントアウトしてもかまわない

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// UV配列オブジェクトの指定(テクスチャのどの部分を切出して描画するか)
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.uvArray);
        }

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 頂点配列オブジェクトの指定
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.vertexArray);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// マトリックスの有効化
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// マトリックス有効化
        gl.glPushMatrix();
// まず移動(実際は最後に移動が適用される)
        gl.glTranslatef(this.matrix.posX, this.matrix.posY, 0.0f);
// 次に回転
        if(this.matrix.degree > 0 || this.matrix.degree < 0) {
// 画像中心点で回転させるために移動が必要
            gl.glTranslatef(this.halfSizeW * this.matrix.exRateX, this.halfSizeH * this.matrix.exRateY, 0.0f);
// 回転させる
            gl.glRotatef(this.matrix.degree, 0.0f, 0.0f, 1.0f);
// 移動を元に戻す
            gl.glTranslatef(-this.halfSizeW * this.matrix.exRateX, -this.halfSizeH * this.matrix.exRateY, 0.0f);
        }
// 最後に拡大・縮小
        gl.glScalef(this.matrix.exRateX, this.matrix.exRateY, 1.0f);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 描画
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// マトリックスの無効化
// これをしないと全ての描画オブジェクトに影響してしまいます
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// マトリックス無効化
        gl.glPopMatrix();

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 頂点配列、テクスチャ座標配列を無効にする
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// テクスチャ、テクスチャ座標配列を無効にする
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glDisable(GL10.GL_TEXTURE_2D);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// ブレンドを無効にする
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        gl.glDisable(GL10.GL_BLEND);
    }

    /**
     * UV配列オブジェクトを生成します
     * @return UV配列オブジェクト
     */
    private FloatBuffer createUVArray() {
// float配列で定義
        float[] uv = {
                0.0f, 0.0f, // 左上
                0.0f, 1.0f, // 左下
                1.0f, 0.0f, // 右上
                1.0f, 1.0f, // 右下
        };

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
// 画像全体を幅1.0、高さ1.0として扱われる
// 切出し範囲のWidth/Heightを0.0～1.0の間で指定することができる
// 今回は画像切り出しなしなので、1.0までを（全体を）指定をする
//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

// FloatBufferに変換して返す
        return this.getFloatBufferFromFloatArray(uv);
    }
    /**
     * 頂点配列オブジェクトを生成します
     */
    private FloatBuffer createVertexArray() {
        float left = 0.0f;
        float top = 0.0f;
        float right = left + this.sizeW;
        float bottom = top + this.sizeH;

//頂点バッファの生成
        float[] vertexs = {
// x, y, z
                left, top, 0.0f, // 頂点0
                left, bottom, 0.0f, // 頂点1
                right, top, 0.0f, // 頂点2
                right, bottom, 0.0f, // 頂点3
        };

// FloatBufferに変換して返す
        return this.getFloatBufferFromFloatArray(vertexs);
    }
    /**
     * float配列をFloatBufferに変換します
     * @param fa:float配列
     * @return 変換後のFloatBuffer
     */
    public FloatBuffer getFloatBufferFromFloatArray(float[] fa) {

// このやり方はAndroidDocで紹介されているため通例(と思われる)
        FloatBuffer fb = ByteBuffer.allocateDirect(fa.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        fb.put(fa).position(0);

        return fb;
    }
}