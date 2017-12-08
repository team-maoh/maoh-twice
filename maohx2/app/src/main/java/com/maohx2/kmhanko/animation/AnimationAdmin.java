package com.maohx2.kmhanko.animation;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.myavail.MyAvail;

import java.util.ArrayList;
import java.util.List;

import com.maohx2.kmhanko.myavail.MyAvail;
import com.maohx2.ina.Draw.Graphic;

/**
 * Created by user on 2017/10/15.
 */

public class AnimationAdmin {
    private static final int ANIMATION_DEFAULT_MAX = 256;
    private static final String DB_NAME = "AnimationDB";

    private List<Animation> animation = new ArrayList<Animation>(ANIMATION_DEFAULT_MAX);
    private List<AnimationData> animation_data;

    private Graphic graphic;
    private List<String> table_names;

    private boolean isLoadDatabase = false;

    /** 初期処理 **/

    public void init(Graphic _graphic) {
        graphic = _graphic;
        Animation.setGraphic(graphic);
    }

    public void loadDatabase(MyDatabaseAdmin database_admin) {
        if (isLoadDatabase) { //呼ぶのは一度だけ
            return;
        }
        isLoadDatabase = true;

        MyDatabase database = database_admin.getMyDatabase(DB_NAME);
        table_names = database.getTables();
        int t_size = table_names.size();

        animation_data = new ArrayList<AnimationData>(t_size);

        for (int i = 0; i < t_size; i++) {
            animation_data.add(i, new AnimationData(database, table_names.get(i)));
        }
    }

    /** 随時呼び出しが必要なメソッド **/

    public void update() {
        for(int i = 0; i < animation.size(); i++) {
            if (animation.get(i).isExist()) {
                animation.get(i).update();
            }
        }
    }

    public void draw() {
        for(int i = 0; i < animation.size(); i++) {
            if (animation.get(i).isExist()) {
                animation.get(i).draw();
            }
        }
    }

    /** Animation関係 **/

    //Animationを番号で取得する
    private Animation getAnimation(int i) {
        try {
            return animation.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return null;
        }
    }

    //List<Animation>に対してAnimationで上書き処理
    private void setAnimation(int i, Animation _animation) {
        try {
            animation.set(i, _animation);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
        }
    }

    //List<Animation>に、使われていないインスタンス化済みのAnimationが存在する場合は、そのexistをtrueにして、その番号を返す。
    private int addAnimation() {
        for(int i = 0; i < animation.size(); i++) {
            if (!animation.get(i).isExist()) {
                animation.get(i).create();
                return i;
            }
        }
        animation.add(new Animation());
        return animation.size() - 1;
    }


    /** AnimationData関係 **/

    //AnimationDataの名前を返す。
    public String getTableName(int i) {
        try {
            return table_names.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return null;
        }
    }

    //AnimationDataの名前をまとめて返す
    public List<String> getTableNames() {
        return table_names;
    }

    //AnimationDataを番号で取得する
    public AnimationData getAnimationData(int i) {
        try {
            return animation_data.get(i);
        } catch(IndexOutOfBoundsException e) {
            MyAvail.errorMes(e);
            return null;
        }
    }

    //AnimationDataを名前で取得する
    public AnimationData getAnimationData(String name) {
        for(int i = 0; i < animation_data.size(); i++) {
            if (animation_data.get(i).getName().equals(name)) {
                return this.getAnimationData(i);
            }
        }
        return null;
    }

    /** ユーザーが使用するメソッド **/

    //Animationを実行する前準備。
    /*
    分割されたBitmapを含むAnimationBitmapData
    AnimationDataを指定する文字列
    を引数とする。帰り値として、Animationを指定するためのIDが帰ってくる
     */

    public Animation readyAnimation(String animationBitmapDataName, String animation_name) {
        int index = addAnimation();//この時点で、AnimationはようやくExistする。
        animation.get(index).setAnimationBitmapDataName(animationBitmapDataName);
        animation.get(index).setAnimationData(this.getAnimationData(animation_name));
        return animation.get(index);
    }

    /*
    //Animationをstartする。現在の仕様では、すでにアニメーションを開始している状態で呼ぶと最初から開始される(はず)。
    public boolean startAnimation(int index) {
        Animation buf_animation = animation.get(index);
        if (!buf_animation.isExist()) {
            return false;
        }
        buf_animation.start();
        return true;
    }

    //Animationの位置を変更する。
    public boolean setAnimationPosition(int index, int _original_x, int _original_y) {
        Animation buf_animation = animation.get(index);
        if (!buf_animation.isExist()) {
            return false;
        }
        buf_animation.setPosition(_original_x, _original_y);
        return true;
    }

    //Animationの一時停止
    public boolean pauseAnimation(int index) {
        Animation buf_animation = animation.get(index);
        if (!buf_animation.isExist()) {
            return false;
        }
        if (buf_animation.isPause()) {
            return false;
        }
        buf_animation.setIsPause(true);
        return true;
    }

    //Animationの再開
    public boolean restartAnimation(int index) {
        Animation buf_animation = animation.get(index);
        if (!buf_animation.isExist()) {
            return false;
        }
        if (!buf_animation.isPause()) {
            return false;
        }
        buf_animation.setIsPause(false);
        return true;
    }
    */
    //Animationの消滅
    public boolean removeAnimation(int index) {
        Animation buf_animation = animation.get(index);
        if (!buf_animation.isExist()) {
            return false;
        }
        buf_animation.clear();
        return true;
    }

}

//AnimationBitMapDataを引数にとる。
