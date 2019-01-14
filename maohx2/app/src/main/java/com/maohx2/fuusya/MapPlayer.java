package com.maohx2.fuusya;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.icu.text.SymbolTable;
import android.view.SurfaceHolder;

import com.maohx2.horie.map.Camera;
import com.maohx2.horie.map.MapAdmin;
import com.maohx2.ina.Battle.BattleUnitAdmin;
import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.GameSystem.DungeonGameSystem;
import com.maohx2.ina.GameSystem.DungeonModeManage;
import com.maohx2.ina.UI.DungeonUserInterface;
import com.maohx2.kmhanko.sound.SoundAdmin;
//import com.maohx2.ina.MySprite;
import java.util.Random;

import static com.maohx2.ina.Constants.Touch.TouchState;

import javax.microedition.khronos.opengles.GL10;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.max;
import static java.lang.StrictMath.signum;

/**
 * Created by Fuusya on 2017/09/11.
 */

public class MapPlayer extends MapUnit {

    int th_encount_steps;
    int mean_encount_steps, var_encount_steps;
    int SOUND_STEPS_PERIOD = 10;//この歩数ごとに足音SEが鳴る
    int TOUCHING_FRAME_NUM = 10;
    //
    int encount_steps;
    int sound_steps;

    SoundAdmin sound_admin;
    DungeonUserInterface dungeon_user_interface;
    MapPlateAdmin map_plate_admin;
    BattleUnitAdmin battle_unit_admin;
    DungeonModeManage dungeon_mode_manage;
    int kind_of_enemy;

    //    int PLAYER_STEP = 26;//プレイヤーの歩幅
    int PLAYER_STEP = 50;//プレイヤーの歩幅 //デバッグ用

    double touch_w_x, touch_w_y, touch_n_x, touch_n_y, pre_w_x, pre_w_y;
    boolean is_moving;

    int touching_frame_count;

    TouchState touch_state;
    boolean avoid_battle_for_debug;

    boolean has_touched_within_player;

    boolean debug_first = false;
    boolean player_touch_refresh;//Playerを二度タッチしたら二度目でMenuが消える

    boolean gateSkipFlag = false; //一度ゲートを踏んだら、一度ゲートから出るかではゲートを起動しない、のためのフラグ

    int menu_frame;

    int dungeonEnterEncountWaitTime;//階層移動後するにはエンカウントしないための変数
    static final int ENCOUNT_START_TIME = 30 * 3;

    public MapPlayer(Graphic graphic, MapObjectAdmin _map_object_admin, DungeonUserInterface _dungeon_user_interface, SoundAdmin _sound_admin, Camera _camera, MapPlateAdmin _map_plate_admin, BattleUnitAdmin _battle_unit_admin, DungeonModeManage _dungeon_mode_manage, boolean _avoid_battle_for_debug) {
        super(graphic, _map_object_admin, _camera);


        dungeon_user_interface = _dungeon_user_interface;
        map_plate_admin = _map_plate_admin;
        //map_admin = _map_admin;
        battle_unit_admin = _battle_unit_admin;
        dungeon_mode_manage = _dungeon_mode_manage;


        touch_state = dungeon_user_interface.getTouchState();

        sound_admin = _sound_admin;

        mean_encount_steps = 120;//80
        var_encount_steps = 0;
        th_encount_steps = makeThresholdEncountSteps();

        encount_steps = 0;
        sound_steps = 0;

        kind_of_enemy = 1;

        touching_frame_count = 0;
        step = PLAYER_STEP;

        is_moving = false;

        can_exit_room = true;

        avoid_battle_for_debug = _avoid_battle_for_debug;

        has_touched_within_player = false;
        player_touch_refresh = true;

        menu_frame = 0;
    }

    public void init() {
        super.init();
    }


    //実行すると右向きにちょっと移動する
    public void openingUpdate() {

        w_x += 25;
        camera.setCameraOffset(w_x, w_y);

    }

    @Override
    public void update() {
        super.update();

        has_touched_within_player = false;

        touch_state = dungeon_user_interface.getTouchState();

        if (dungeonEnterEncountWaitTime < ENCOUNT_START_TIME) {
            dungeonEnterEncountWaitTime++;
        }

        if (touch_state != Constants.Touch.TouchState.AWAY) {

            touch_n_x = dungeon_user_interface.getTouchX();
            touch_n_y = dungeon_user_interface.getTouchY();
            touch_w_x = camera.convertToWorldCoordinateX((int) touch_n_x);
            touch_w_y = camera.convertToWorldCoordinateY((int) touch_n_y);

            //Listではない領域をタッチしているとき
            if (map_plate_admin.isTouchingList(touch_n_x, touch_n_y) == false) {

//                int pre_content = map_plate_admin.getDisplayingContent();
//                map_plate_admin.setDisplayingContent(-1);

                if (map_plate_admin.getDisplayingContent() == 0 || map_plate_admin.getDisplayingContent() == 2) {
                    menu_frame++;
                    if (menu_frame > 12) {
                        menu_frame = 0;
                        map_plate_admin.setDisplayingContent(-1);
                    }
                }

                //Player(画面中央)をタッチしたらMENUを表示する
//                if (touch_state != TouchState.MOVE) {
                if (touch_state == TouchState.UP) {
                    if (map_plate_admin.getDisplayingContent() == -1) {
                        if (isWithinReach(touch_w_x, touch_w_y, 80) == true) {
                            map_plate_admin.setDisplayingContent(0);
                            sound_admin.play("enter00");
//                        player_touch_refresh = false;
                        }
                    } else if (map_plate_admin.getDisplayingContent() == 0 || map_plate_admin.getDisplayingContent() == 2 || map_plate_admin.getDisplayingContent() == 3) {
                        map_plate_admin.setDisplayingContent(-1);
                    }
                }


                //タッチ座標が現在位置からある程度離れていたら、その座標を目標座標とする
                //（近いと walkOneStep() の仕様上、足が早くなってしまう）
                //（[Playerの近くをタッチすれば高速移動できる]という裏技がまかり通ってしまう）
                if (isWithinReach(touch_w_x, touch_w_y, step * 2) == false) {
                    dst_w_x = touch_w_x;
                    dst_w_y = touch_w_y;
                } else {
                    has_touched_within_player = true;
                }

                touching_frame_count++;

                is_moving = true;

            }

        } else if (touching_frame_count > TOUCHING_FRAME_NUM) {//画面を10frames以上タッチした場合、

            touching_frame_count = 0;

            is_moving = false;//指を離した時点で歩行を中止する
        }

//        if(touch_state == TouchState.AWAY){
//            System.out.println("■AWAY_desuno");
//        }else if(touch_state == TouchState.MOVE){
//            System.out.println("■MOVE_desuno");
//        }else if(touch_state == TouchState.DOWN){
//            System.out.println("■DOWN_desuno");
//        }else if(touch_state == TouchState.DOWN_MOVE){
//            System.out.println("■DOWN_MOVE_desuno");
//        }else if(touch_state == TouchState.UP){
//            System.out.println("■UP_desuno");
//        }
//        System.out.println(touch_n_x + "    desuno    "+ touch_n_y);

        //BadStatus（状態異常）を表現するためにstep, dst_w_x, dst_w_y等を適宜書き換える
        step = checkBadStatus(PLAYER_STEP);

        if (has_bad_status == true) {
            updateDirOnMap(dst_w_x, dst_w_y);

            if (can_walk == true) {

                if (frames_before_blown_away == 0 && frames_being_blown_away > 0) {

                    //3 * step を引数にすると壁のだいぶ手前で立ち止まってしまう
                    collide_wall = walkOneStep(dst_w_x, dst_w_y, step);
                    collide_wall = walkOneStep(dst_w_x, dst_w_y, step);
                    collide_wall = walkOneStep(dst_w_x, dst_w_y, step);

                    if (collide_wall == true && frames_being_blown_away > 11) {
                        frames_being_blown_away = 10;
                    }
                } else {
                    //壁との衝突を考慮した上で、タッチ座標に向かって１歩進む
                    walkOneStep(dst_w_x, dst_w_y, step);
                }
            }

        } else if (is_moving == true) {

            //Playerアイコンの向きを更新する
            updateDirOnMap(dst_w_x, dst_w_y);

            //壁との衝突を考慮した上で、タッチ座標に向かって１歩進む
            walkOneStep(dst_w_x, dst_w_y, step);

            if (map_admin.getNow_floor_num() != map_admin.getBoss_floor_num()) {

                if (has_touched_within_player == false) {//直前にPlayerをタッチしていない場合に限り、エンカウント処理をする

                    encount_steps++;

                    if (encount_steps >= th_encount_steps && avoid_battle_for_debug == false && dungeonEnterEncountWaitTime >= ENCOUNT_START_TIME) {
                        System.out.println("◆一定歩数 歩いたので敵と遭遇");
                        encount_steps = 0;
                        //遭遇の瞬間に、次の遭遇までに要する歩数を乱数で決める
                        th_encount_steps = makeThresholdEncountSteps();

                        int now_floor_num = map_admin.getNow_floor_num();//nullが返ってくる
                        int boss_floor_num = map_admin.getBoss_floor_num();//nullが返ってくる

//                        int max_of_num_of_zako = 5;
//                        int num_of_zako = max_of_num_of_zako * now_floor_num / boss_floor_num + 2;
                        int num_of_zako = min(5, (int) (now_floor_num * 0.5) + 2);
                        //int num_of_zako = 20;

//                        num_of_zako = max_of_num_of_zako;//fuusya_debug

                        String[] tmp_zako = new String[num_of_zako];

                        int kind_of_zako = map_object_admin.getKindOfZako();

                        for (int i = 0; i < num_of_zako; i++) {
                            double ran1 = kind_of_zako * random.nextDouble();
                            tmp_zako[i] = map_admin.getMonsterName(0)[(int) ran1 % kind_of_zako];
//                            tmp_zako[i] = "dwarf";
                        }

                        //戦闘画面では「罠だ！」のTextBoxを出さない
                        map_object_admin.eraseEffectBox();

                        //デバッグ時にエンカウントすると鬱陶しいのでコメントアウト
                        if (dungeon_mode_manage.getMode() != Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING && dungeon_mode_manage.getMode() != Constants.GAMESYSTEN_MODE.DUNGEON_MODE.GEO_MINING_INIT) {
                            battle_unit_admin.reset(BattleUnitAdmin.MODE.BATTLE);
                            battle_unit_admin.spawnEnemy(tmp_zako);//
                            dungeon_mode_manage.setMode(Constants.GAMESYSTEN_MODE.DUNGEON_MODE.BUTTLE_INIT);
                        }
                    }
                }
            }

            if (has_touched_within_player == false) {
                sound_steps = (sound_steps + 1) % SOUND_STEPS_PERIOD;
                //壁にぶつかり続けてるときに音がなり続けるのはおかしいので、
                //if(has_touched_within_player == false) の中に入れる
                if (sound_steps == 0) {
                    sound_admin.play("step07");//足音SE
                }
            }

        } else {//moving == false のとき
            touching_frame_count = 0;
        }

        camera.setCameraOffset(w_x, w_y);

        //walkOneStep()でタッチ座標にたどり着くと自然に立ち止まる
        //立ち止まっている場合にはis_movingをfalseにする（さもなくば足音SEが出続ける）
        if (pre_w_x == w_x && pre_w_y == w_y) {
            is_moving = false;
        }
        pre_w_x = w_x;
        pre_w_y = w_y;

        //System.out.println("x_y = " + (int)(w_x/map_admin.getMagnification()) + " / " + (int)(w_y/map_admin.getMagnification()));
        //階段移動
        if (map_admin.isStairs((int)(w_x/map_admin.getMagnification()), (int)(w_y/map_admin.getMagnification()))) {
            sound_admin.play("step00");
            map_admin.goNextFloor();
        }

        //ゲート脱出
        if (map_admin.isGate((int)(w_x/map_admin.getMagnification()), (int)(w_y/map_admin.getMagnification()))) {
            if (gateSkipFlag == false) {
                map_plate_admin.escapeDungeonWindowActivate();
                gateSkipFlag = true;
            }
        } else {
            gateSkipFlag = false;
        }

    }
    public void setGateSkipFlag(boolean x) {
        gateSkipFlag = x;
    }

    public void setMeanEncountSteps(int _mean) {
        mean_encount_steps = _mean;
    }

    public void setEncountSteps(int x) {
        encount_steps = x;
    }

    public void setVarEncountSteps(int _var) {
        var_encount_steps = _var;
    }

    public void setDungeonEnterEncountWaitTime(int x) {
        dungeonEnterEncountWaitTime = x;
    }

    private int makeThresholdEncountSteps() {

        double normal_dist = makeNormalDist();

        return (int) (var_encount_steps * normal_dist + mean_encount_steps);
    }

    public double getTouchWouldX() {
        return touch_w_x;
    }

    public double getTouchWouldY() {
        return touch_w_y;
    }

    public boolean getIsMoving() {
        return is_moving;
    }

    //自分の座標と (target_w_x, target_w_y) の距離が target_reach 以下であるか否か
    public boolean isWithinReach(double target_w_x, double target_w_y, double target_reach) {

        if (target_reach < myDistance(target_w_x, target_w_y, w_x, w_y)) {
            return false;
        } else {
            return true;
        }

    }

    public void checkIfTouchingGate() {

//        double gate_w_x = map_admin.getGate();
//        double gate_w_y = map_admin.getGate();

//        if (isWithinReach(gate_w_x, gate_w_y, 150) == true) {
//            map_object_admin.escapeDungeon();
//        }

    }


}