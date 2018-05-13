package com.maohx2.kmhanko.music;

import android.content.Context;

import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.session.MediaController;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/09/11.
 */

//音量調整を端末のボタンに任せる
//ActivityのonCreateで呼ぶ
//setVolumeControlStream(AudioManager.STREAM_MUSIC);


//TODO:ループがちゃんとできていない

public class MusicAdmin implements OnPreparedListener, Runnable {
    private final Context mContext;
    //private final int AUDIO_ACTIVE_NUM = 1;
    //private final int AUDIO_LOAD_NUM = 256;
    private final String FOLDER = "music";

    static final String DB_NAME = "musicDB";
    static final String DB_ASSET = "music.db";

    private String table_name;
    private boolean is_load_completed = false;

    private boolean isStart = true;
    private String loop_count_mode;
    private int option;
    private int loopstart;
    private int looplength;
    private long setupTime;

    Thread thread;

    MediaPlayer media_player;

    private MyDatabase database;
    private MyDatabaseAdmin databaseAdmin;

    public MusicAdmin(Context context) {
        mContext = context;
        //myDatabaseAdmin.addMyDatabase("");
    }

    public MusicAdmin(Context context, MyDatabaseAdmin _databaseAdmin) {
        mContext = context;
        databaseAdmin = _databaseAdmin;
        setDatabase(databaseAdmin);
    }

    public void setDatabase(MyDatabaseAdmin databaseAdmin) {
        databaseAdmin.addMyDatabase(DB_NAME, DB_ASSET, 1, "r");
        database = databaseAdmin.getMyDatabase(DB_NAME);
    }

    public void setDatabase(MyDatabase _database) {
        database = _database;
    }

    public void setTableName(String _table_name) {
        table_name = _table_name;
    }

    public boolean isLoadCompleted() {
        return is_load_completed;
    }

    private int sampleToMs(int _ms, int sample) {
        return (int)(((double)_ms/(double)sample)*1000.0);
    }

    @Override
    public void run() {

        while(thread != null) {
            //System.out.println("dg_mes:" + media_player.getCurrentPosition()+ " "+ sampleToMs(loopstart + looplength,44100));
            if (loop_count_mode.equals("sample")) {
                if (media_player.getCurrentPosition() >= sampleToMs(loopstart + looplength, option) - 100) {
                    media_player.seekTo(media_player.getCurrentPosition() - sampleToMs(looplength, option));
                    //TODO:結合部分に違和感
                }
            }

            if (loop_count_mode.equals("loop")) {
                long playTime = System.currentTimeMillis() - setupTime;
                if (playTime >= (long)(loopstart + looplength)) {
                    media_player.seekTo(loopstart);
                    setupTime = (long)loopstart + System.currentTimeMillis();
                }
            }
        }

    }

    public void threadStop() {
        thread = null;
    }

    public boolean play() {
        if (!is_load_completed) {
            return false;
        }
        if (media_player != null) {
            if (media_player.isPlaying()) {
                media_player.stop();
            }
        }
        media_player.start();
        setupTime = System.currentTimeMillis();

        thread = new Thread(this);
        thread.start();
        return true;
    }

    public void stop() {
        if (media_player != null) {
            if (media_player.isPlaying()) {
                media_player.stop();
            }
        }
        threadStop();
    }

    //getCurrentPosition：現在の再生位置をmsec単位で取得(不安定)
    //toSeek：再生位置をmsec単位で指定

    public void loadMusic(String name, boolean _isstart) {
        isStart =_isstart;
        is_load_completed = false;
        this.close();
        media_player = new MediaPlayer();

        AssetManager asm = mContext.getAssets();

        String w_script = "name = " + database.s_quo(name);

        List<String> filename = database.getString(table_name, "filename", w_script);
        List<Integer> _loopstart = database.getInt(table_name, "loopstart", w_script);
        List<Integer> _looplength = database.getInt(table_name, "looplength", w_script);
        List<String> _loop_count_mode = database.getString(table_name, "loop_count_mode", w_script);
        List<Integer> _option = database.getInt(table_name, "option", w_script);
        try {
            //音声ファイル読み込み
            AssetFileDescriptor afd = asm.openFd(FOLDER + "/" + filename.get(0));
            System.out.println("dg_mes:" + "MusicAdmin.loadAudioFile filename:" + filename.get(0));
            //メディアプレイヤーに音楽ファイルをセット
            media_player.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(),
                    afd.getLength());

            media_player.prepareAsync();
            media_player.setOnPreparedListener(this);

            //その他の数値をセット
            loopstart = _loopstart.get(0);
            looplength = _looplength.get(0);
            option = _option.get(0);
            loop_count_mode = _loop_count_mode.get(0);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        is_load_completed = true;
        if (isStart) {
            this.play();
        }
    }

    //ActivityのonPause()で呼ぶこと
    public void close() {
        if (media_player != null) {
            if (media_player.isPlaying()) {
                media_player.stop();
            }
            media_player.reset();
            media_player.release();
            media_player = null;
        }
    }
}
