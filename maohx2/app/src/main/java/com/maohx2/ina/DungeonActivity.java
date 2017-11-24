package com.maohx2.ina;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;
import com.maohx2.kmhanko.database.MyDatabase;
import com.maohx2.kmhanko.sound.SoundAdmin;

import static com.maohx2.ina.Constants.Touch.TouchState;



public class DungeonActivity extends BaseActivity {

    DungeonSurfaceView dungeon_surface_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dungeon_surface_view = new DungeonSurfaceView(this);
        setContentView(dungeon_surface_view);
    }

    //ここでメインループを止める
    @Override
    protected void onStop(){
        super.onStop();
        System.out.println("onStop!!");
    }

    //ここでメインループを再開する
    @Override
    public void onResume(){
        super.onResume();
    }

}

class DungeonSurfaceView extends BaseSurfaceView{

    //画像読み込み
    Resources res = this.getContext().getResources();
    Bitmap neco = BitmapFactory.decodeResource(res, R.drawable.neco);
    Bitmap apple = BitmapFactory.decodeResource(res, R.drawable.apple);
    Bitmap banana = BitmapFactory.decodeResource(res, R.drawable.banana);
    //藤原追加
    Bitmap grape = BitmapFactory.decodeResource(res, R.drawable.grape);
    Bitmap watermelon = BitmapFactory.decodeResource(res, R.drawable.watermelon);
    Bitmap slime = BitmapFactory.decodeResource(res, R.drawable.slime);


    DungeonUserInterface dungeon_user_interface;
    SoundAdmin sound_admin;
    MyDatabaseAdmin my_database_admin;
    DungeonGameSystem game_system;
    Graphic graphic;
    Activity dungeon_activity;

    public DungeonSurfaceView(Activity _dungeon_activity) {
        super(_dungeon_activity);
        dungeon_activity = _dungeon_activity;


        dungeon_user_interface = new DungeonUserInterface(((GlobalData) dungeon_activity.getApplication()).getGlobalConstants());
        graphic = new Graphic(dungeon_activity, holder);
        my_database_admin = new MyDatabaseAdmin(dungeon_activity);
        game_system = new DungeonGameSystem();
        sound_admin = new SoundAdmin(dungeon_activity);


        dungeon_user_interface.init();


        //my_database_admin.addMyDatabase("DungeonDB", "Dungeon.db", 1, "r");
        //graphic.loadLocalImages(my_database_admin.getMyDatabase("DungeonDB"), "Dungeon");

        //todo:ここはダンジョンセレクト関係の人からもらってくる
        //my_database_admin.addMyDatabase("HauntedDB", "Haunted.db", 1, "r");
        //graphic.loadLocalImages(my_database_admin.getMyDatabase("HauntedDB"), "Haunted");

        my_database_admin.addMyDatabase("soundDB", "soundDB.db", 1, "r");//データベースのコピーしMySQLiteのdbを扱いやすいMyDataBase型にしている
        sound_admin.setDatabase(my_database_admin.getMyDatabase("soundDB"));//扱いやすいやつをセットしている
        sound_admin.loadSoundPack("sound_pack_map");

        game_system.init(dungeon_user_interface, graphic, sound_admin);//GameSystem()の初期化 (= GameSystem.javaのinit()を実行)
    }


    @Override
    public void gameLoop(){
        dungeon_user_interface.updateTouchState(touch_x, touch_y, touch_state);
        game_system.update();
        game_system.draw();
        if(dungeon_user_interface.getTouchState() == TouchState.DOWN){
            thread = null;
        }


        if(touch_state == TouchState.DOWN) {
            thread = null;
            Intent intent = new Intent(dungeon_activity, BattleActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            dungeon_activity.startActivity(intent);
        }
    }
}
