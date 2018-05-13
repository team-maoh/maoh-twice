package com.maohx2.kmhanko.sound;

import android.content.Context;

//例外関係
import java.io.IOException;

//Assets関係
import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;

//オーディオ関係
import android.media.AudioManager;
import android.media.SoundPool;

//配列関係
import java.util.ArrayList;
import java.util.List;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;


/**
 * Created by user on 2017/09/10.
 * version : 1.01
 * 文字列がMonitorに
 */

//globalにおくと、全ての効果音を読み出すことになるからlocalにおく

//TODO:再生の度に、DBから読み出しをしているが、よくないかもしれないので、直した方がいいかもしれない。

public class SoundAdmin {
    //メンバ変数
    static final String DB_NAME = "soundDB";
    static final String DB_ASSET = "sound.db";
    private final Context mContext;
    private final int SOUND_ACTIVE_NUM = 10;
    private final int SOUND_LOAD_NUM = 256;
    private final String FOLDER = "sound";
    //private final String DB_NAME = "testdb";

    private String soundpack_name;

    private SoundPool sp = new SoundPool(SOUND_ACTIVE_NUM, AudioManager.STREAM_MUSIC, 0);
    //TODO:Builder

    private List<Integer> sound_ID = new ArrayList<Integer>(SOUND_LOAD_NUM);
    private List<Integer> stream_ID = new ArrayList<Integer>(SOUND_LOAD_NUM);

    private MyDatabase database;

    private boolean isLoaded;

    //TODO:ボリューム設定　activityと

    //コンストラクタ
    public SoundAdmin(Context _context) {
        mContext = _context;
        isLoaded = false;
    }
    public SoundAdmin(Context _context, MyDatabaseAdmin databaseAdmin) {
        //TODO:Builder
        /*
        audioAttributes = new AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        sp = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(2)
                .build();
        */
        mContext = _context;
        isLoaded = false;

        setDatabase(databaseAdmin);
    }

    private int getSoundID(String name) {
        int buf = database.getOneRowIDForArray(soundpack_name, "name=" + MyDatabase.s_quo(name));
        try {
            return sound_ID.get(buf);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new Error("SoundAdmin#getSoundID : Cannot get SoundID " + buf + " " + e);
        }
    }

    //****ユーザーが普段使用するメソッド****
    public void setDatabase(MyDatabase _database) {
        database = _database;
    }

    public void setDatabase(MyDatabaseAdmin databaseAdmin) {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }

    public boolean play(String name) {
        return this.play(name, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    public boolean play(String name, float leftVolume, float rightVolume, int priority, int loop, float rate) {
        //ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度
        int id = getSoundID(name);
        int stid = sp.play(id, leftVolume, rightVolume, priority, loop, rate);
        stream_ID.add(id - 1, stid);
        return true;
    }

    public void resume(String name) {
        int id = getSoundID(name) - 1;
        sp.resume(stream_ID.get(id));
    }

    public void pause(String name) {
        int id = getSoundID(name) - 1;
        sp.pause(stream_ID.get(id));
    }

    public void autoResume() {
        sp.autoPause();
    }

    public void autoPause() {
        sp.autoPause();
    }

    public void stop(String name) {
        int id = getSoundID(name) - 1;
        sp.stop(stream_ID.get(id));
    }

    public void release() {
        sp.release();
    }

    //****ここまで****

    public boolean loadSoundPack(String sound_pack_table_name) {
        if (isLoaded) {
            sp.release();
        }
        isLoaded = true;

        soundpack_name = sound_pack_table_name;
        AssetManager asm = mContext.getAssets();

        List<String> l_filename = new ArrayList<String>();

        l_filename = database.getString(sound_pack_table_name, "filename", null);


        for (int i = 0; i < l_filename.size(); i++) {
            try {
                //音声ファイル読み込み
                AssetFileDescriptor fd = asm.openFd(FOLDER + "/" + soundpack_name +  "/" + l_filename.get(i));
                sound_ID.add(i,sp.load(fd, 1));
                stream_ID.add(i, 0);//これがないと後でOutOfになる

                //System.out.println("dg_mes:" + " filename:" + l_filename.get(i) + " id:" + sound_ID.get(i));
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("SoundAdmin#loadSoundPack " + l_filename.get(i));
            }
        }

        //System.out.println("dg_mes:" + sound_ID.get(0) + " "+ sound_ID.get(1) + " " + sound_ID.get(2));

        if (SOUND_LOAD_NUM < l_filename.size()) {
            throw new Error("SoundAdmin#loadSoundPack : number of sound is over : " + l_filename.size());
        }
        return true;
    }

}


    /*
    旧ばーじょん
    public boolean play(String name) {
        return this.play(name,1.0f,1.0f,0,0,1.0f);
    }

    public boolean play(String name, float leftVolume, float rightVolume, int priority, int loop, float rate) {
        //ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度

        int id = sound_ID.get(sound_name.indexOf(name));
        int stid = sp.play(id, leftVolume, rightVolume, priority, loop, rate);
        stream_ID.add(id, stid);
        return true;
    }

    public boolean play(String table_name, String name, float leftVolume, float rightVolume, int priority, int loop, float rate) {
        //ロードしたID, 左音量, 右音量, 優先度, ループ,再生速度
        if (!isOnlyIndex(sound_name,name)) {
            return false;
        }
        int id = sound_ID.get(sound_name.indexOf(name));
        int stid = sp.play(id, leftVolume, rightVolume, priority, loop, rate);
        stream_ID.add(id, stid);
        return true;
    }

    public void resume(String name) {
        int id = sound_ID.get(sound_name.indexOf(name));
        sp.resume(stream_ID.get(id));
    }

    public void pause(String name) {
        int id = sound_ID.get(sound_name.indexOf(name));
        sp.pause(stream_ID.get(id));
    }

    public void autoResume() {
        sp.autoPause();
    }

    public void autoPause() {
        sp.autoPause();
    }

    public void stop(String name) {
        int id = sound_ID.get(sound_name.indexOf(name));
        sp.stop(stream_ID.get(id));
    }

    public void release() {
        sp.release();
    }

    //****ここまで****

    public void soundLoad() {
        AssetManager asm = mContext.getAssets();
        //mysqlite_admin = new MySQLiteOpenHelperAdmin(mContext);
        String[] files = new String[SOUND_FILE_NUM];

        try {
            files = asm.list(FOLDER);
        } catch(IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < files.length; i++) {
            InputStream is = null;
            try {
                //実際に読み込む
                AssetFileDescriptor fd = asm.openFd(FOLDER+"/"+files[i]);
                sound_ID.add(i,sp.load(fd, 1));
                sound_name.add(i,files[i]);
                stream_ID.add(i, 0);
                System.out.println("dg_mes:" + "soundload:"+files[i]+" ID="+sound_ID.get(i));

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("dg_mes:" + "soundload: error "+files[i]);
            }
        }
    }
    */
//}


/*
データベースを使用、サウンドパックなるものを用意し、
いくかの指定のサウンドパックをロードすることができるようにする。
これにより、ゲーム全体での効果音使用個数を256個以上にすることができる。

サウンドパックのロードは、場面場面によって行う。
例えば、戦闘用パックは、戦闘開始時に読み込むなど。
そしてパックはデータベースで管理する。
 */
