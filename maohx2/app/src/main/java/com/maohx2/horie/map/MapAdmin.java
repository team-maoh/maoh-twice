package com.maohx2.horie.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.fuusya.MapObjectAdmin;
import com.maohx2.fuusya.MapPlayer;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.kmhanko.myavail.MyAvail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.SYSTEM_HEALTH_SERVICE;
import static android.content.Context.WINDOW_SERVICE;
import static java.lang.Math.abs;

/**
 * Created by horie on 2017/08/30.
 */
public class MapAdmin {
    int map_data_int[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };
    int[][] t_map_data_int = new int[map_data_int[0].length][map_data_int.length];

    Chip map_data[][];
    Chip opening_map_data[][];
    Graphic graphic;

    Point map_size = new Point(0, 0);//60, 40
    int magnification = 64 * 4;//倍率
    int time = 0;//アニメーションタイミング用
    int now_floor_num = 0;//現在のフロア階層
    int boss_floor_num;//ボスフロアの階層
    int mine_min_num;
    int mine_max_num;
    int animation_num;//アニメーション回数

    int accessoryNum;
    float accessoryRate;

    String dungeon_name;
    String floor_tile_name;
    String wall_tile_name;
    String sidewall_tile_name;

    Point offset = new Point(0, 0);
    Point start_point = new Point(0, 0);

    Paint paint = new Paint();
    Point room_point = new Point(0, 0);
    Point mine_point[];//採掘場所の座標
    Point opening_map_size = new Point (0, 0);

    Camera camera;// = new Camera(map_size, magnification);
    SectionAdmin section_admin;
    Canvas canvas;
    SurfaceHolder holder;
    MapPlayer map_player;
    MapObjectAdmin map_object_admin;
    DungeonData dungeon_data;
    List<DungeonMonsterData> dungeon_monster_data;

    //auto_tile用
    AutoTile auto_tile_wall = new AutoTile();
    AutoTile auto_tile_side_wall = new AutoTile();
    AutoTile at_wall[];
    AutoTile at_side_wall[];
    AutoTile at_floor = new AutoTile();
    AutoTile auto_tile_cave_hole[] = new AutoTile[3];
    AutoTileAdmin auto_tile_admin;
    boolean is_map_data_wall[][];// = new boolean[map_size.x*2][map_size.y*2];//表示用に4分割されたmap_data
    boolean is_map_data_sidewall[][];// = new boolean[map_size.x*2][map_size.y*2];

    BitmapData map_tile_set[][];// = new BitmapData[map_size.x*2][map_size.y*2];//4分割されたmap画像
    BitmapData map_tile_set_animation[][][];// = new BitmapData[3][map_size.x*2][map_size.y*2];
    BitmapData map_tile[][];// = new BitmapData[map_size.x][map_size.y];//map_tile_set[][]を1つに纏めた画像
    BitmapData map_tile_animation[][][];// = new BitmapData[3][map_size.x][map_size.y];//上のアニメション用
    BitmapData side_wall_4[][];
    BitmapData op_map_tile[][][];

    BitmapData floor_tile;
    BitmapData accseccaryFloorTileSet[];
    BitmapData stair_tile;
    BitmapData gate_tile;
    BitmapData stair_tile_div[] = new BitmapData[4];//階段の画像を4分割

    public int getMap_size_x() {
        return map_size.x;
    }

    public int getMap_size_y() {
        return map_size.y;
    }

    public int getOffset_x() {
        return camera.getCameraOffset().x;
    }

    public int getOffset_y() {
        return camera.getCameraOffset().y;
    }

    public Camera getCamera() {
        return camera;
    }

    public int getMagnification() {
        return magnification;
    }

    public int getNow_floor_num(){
        return now_floor_num;
    }

    public int getBoss_floor_num(){
        return boss_floor_num;
    }

    public MapAdmin(Graphic m_graphic, MapObjectAdmin m_map_object_admin, DungeonData m_dungeon_data, List<DungeonMonsterData> m_dungeon_monster_data, MapStatus _map_status, MapStatusSaver _map_status_saver) {
        graphic = m_graphic;
        map_object_admin = m_map_object_admin;
        map_player = map_object_admin.getPlayer();
        dungeon_data = m_dungeon_data;
        dungeon_monster_data = m_dungeon_monster_data;

        //データベースからマップ情報の読み込み
        dungeon_name = dungeon_data.getDungeon_name();
        map_size.set(dungeon_data.getMap_size_x(), dungeon_data.getMap_size_y());
        boss_floor_num = dungeon_data.getFloor_num();
        mine_min_num = dungeon_data.getMine_min_num();
        mine_max_num = dungeon_data.getMine_max_num();
        floor_tile_name = dungeon_data.getFloor_tile_name();
        wall_tile_name = dungeon_data.getWall_tile_name();
        sidewall_tile_name = dungeon_data.getSidewall_tile_name();

        accessoryNum = dungeon_data.getAccessoryNum();
        accessoryRate = dungeon_data.getAccessoryRate();

        //マップ生成用変数初期化
        camera = new Camera(map_size, magnification);
        is_map_data_wall = new boolean[map_size.x * 2][map_size.y * 2];//表示用に4分割されたmap_data
        is_map_data_sidewall = new boolean[map_size.x * 2][map_size.y * 2];
        map_tile_set = new BitmapData[map_size.x * 2][map_size.y * 2];//4分割されたmap画像
        map_tile_set_animation = new BitmapData[3][map_size.x * 2][map_size.y * 2];
        map_tile = new BitmapData[map_size.x][map_size.y];//map_tile_set[][]を1つに纏めた画像
        map_tile_animation = new BitmapData[3][map_size.x][map_size.y];//上のアニメション用

        //map_dataを初期化
        map_data = new Chip[map_size.x][map_size.y];
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                map_data[i][j] = new Chip();
            }
        }

        //採掘場の場所を格納する配列
        mine_point = new Point[5];
        for (int i = 0; i < 5; i++) {
            mine_point[i] = new Point(-1, -1);
        }

        //階段画像読み込み
        stair_tile = graphic.searchBitmap("step");

        //ゲート画像読み込み
        BitmapData gate_tile_tmp = graphic.searchBitmap("Gate03");
        gate_tile = graphic.processTrimmingBitmapData(gate_tile_tmp, 0, 64*2, 64, 64);

        //オートタイル生成
        auto_tile_admin = new AutoTileAdmin(graphic);
        //ドラゴンステージだけ場合分け
        if (dungeon_name.equals("Dragon")) {
            createFloorAutoTile();
        } else {
            createAutoTileImg();
        }
        getHolder();
    }

    //auto_tile生成
    private void createAutoTileImg() {
        //画像読込
        BitmapData auto_tile_block_side_wall = graphic.searchBitmap(sidewall_tile_name);//側壁のauto_tileの元データ
        BitmapData auto_tile_block_wall = graphic.searchBitmap(wall_tile_name);//壁のauto_tile元データ
        BitmapData raw_floor_tile = graphic.searchBitmap(floor_tile_name);
        floor_tile = auto_tile_admin.combineFourAutoTile(raw_floor_tile, raw_floor_tile, raw_floor_tile, raw_floor_tile);

        if (accessoryNum > 0) {
            accseccaryFloorTileSet = new BitmapData[accessoryNum];
            for (int i = 0; i < accseccaryFloorTileSet.length; i++) {
                accseccaryFloorTileSet[i] = graphic.searchBitmap("accessory0" + String.valueOf(i));
            }
        } else {
            accseccaryFloorTileSet = null;
        }


//        BitmapData cave_hole_raw = graphic.searchBitmap("cave_hole_01");//穴（アニメーション）
        //BitmapData dragon_hole_raw = graphic.searchBitmap("Dragon_hole_f_01");//穴（アニメーション）

        //階段画像4分割
//        stair_tile = graphic.searchBitmap("step");
//        stair_tile_div[0] = graphic.processTrimmingBitmapData(stair_tile, 0, 0, 32, 32);
//        stair_tile_div[1] = graphic.processTrimmingBitmapData(stair_tile, 32, 0, 32, 32);
//        stair_tile_div[2] = graphic.processTrimmingBitmapData(stair_tile, 0, 32, 32, 32);
//        stair_tile_div[3] = graphic.processTrimmingBitmapData(stair_tile, 32, 32, 32, 32);

        //縦分割、分割数可変
        //壁画像
        animation_num = auto_tile_block_wall.getWidth() / 32;
        BitmapData auto_tile_set_wall[] = new BitmapData[animation_num];
        for (int i = 0; i < animation_num; i++) {
            auto_tile_set_wall[i] = graphic.processTrimmingBitmapData(auto_tile_block_wall, i * 32, 0, 32, 32 * 5);
        }
        //横壁画像
//        int side_wall_animation_num = auto_tile_block_side_wall.getWidth()/32;
        BitmapData auto_tile_set_side_wall[] = new BitmapData[animation_num];
        for (int i = 0; i < animation_num; i++) {
            auto_tile_set_side_wall[i] = graphic.processTrimmingBitmapData(auto_tile_block_side_wall, i * 32, 0, 32, 32 * 5);
        }

        at_wall = new AutoTile[animation_num];
        at_side_wall = new AutoTile[animation_num];
        for (int i = 0; i < animation_num; i++) {
            at_wall[i] = new AutoTile();
            at_side_wall[i] = new AutoTile();
        }
        //auto_tileを元画像から5つに分割→auto_tileを作る
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < animation_num; j++) {
                at_wall[j].setAuto_tile(graphic.processTrimmingBitmapData(auto_tile_set_wall[j], 0, 32 * i, 32, 32), i);
                at_side_wall[j].setAuto_tile(graphic.processTrimmingBitmapData(auto_tile_set_side_wall[j], 0, 32 * i, 32, 32), i);
            }
        }
        for (int i = 0; i < animation_num; i++) {
            auto_tile_admin.createAutoTile(at_wall[i]);
            auto_tile_admin.createAutoTile(at_side_wall[i]);
        }

        side_wall_4 = new BitmapData[animation_num][4];
        //横壁4種類作成
        for (int i = 0; i < animation_num; i++) {
            auto_tile_admin.createAutoTileForSideWall(at_side_wall[i], side_wall_4[i]);
            auto_tile_admin.createBigAutoTile(at_wall[i], side_wall_4[i]);
        }
//        System.out.println("create map image finished!");
//        auto_tile_admin.createAutoTileForSideWall(auto_tile_side_wall, side_wall);
//        auto_tile_admin.createBigAutoTile(auto_tile_wall, side_wall);

//        for(int i = 0;i < 3;i++){
//            auto_tile_admin.createAutoTile(auto_tile_cave_hole[i]);
//        }
    }

    //フロアタイル用auto_tile生成
    private void createFloorAutoTile() {
        BitmapData auto_tile_set_floor = graphic.searchBitmap(floor_tile_name);
//        stair_tile = graphic.searchBitmap("step");
        //auto_tileを5枚に切る
        for(int i = 0;i < 5;i++) {
            at_floor.setAuto_tile(graphic.processTrimmingBitmapData(auto_tile_set_floor, 0, 32 * i, 32, 32), i);
        }
        auto_tile_admin.createAutoTile(at_floor);
        auto_tile_admin.createBigAutoTile_floor(at_floor);
    }

    //壁かどうかマップ座標で判定
    public boolean isWall(int x, int y) {
        try {
            return map_data[x][y].isWall();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("配列の要素数をこえています。");
            System.out.println(e + "クラスの例外が発生しました。");
            return false;
        }
    }

    //階段かどうかマップ座標で判定
    public boolean isStairs(int x, int y) {
        try {
            return map_data[x][y].isStairs();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("配列の要素数をこえています。");
            System.out.println(e + "クラスの例外が発生しました。");
            return false;
        }
    }

    //ゲートかどうか
    public boolean isGate(int x, int y){
        try {
            return map_data[x][y].isGate();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("配列の要素数をこえています。");
            System.out.println(e + "クラスの例外が発生しました。");
            return false;
        }
    }

    //玄関かどうかマップ座標で判定
    public boolean isEntrance(int x, int y) {
        try {
            return map_data[x][y].isEntrance();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("配列の要素数をこえています。");
            System.out.println(e + "クラスの例外が発生しました。");
            return false;
        }
    }

    public boolean isRoom(int i, int j) {
        return map_data[i][j].isRoom();
    }

    public boolean isDisp(int i, int j) {
        return map_data[i][j].isDisp();
    }

    /*public boolean isMine(int i, int j) {
        return map_data[i][j].isMine();
    }*/

    //壁かどうかワールドマップで判定
    public boolean isWallWorld(int world_x, int world_y) {
        return map_data[worldToMap(world_x)][worldToMap(world_y)].isWall();
    }

    //マップの自動生成
    public void createMap() {
        //map_dataの初期化
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                map_data[i][j].initializeChip();
            }
        }
        //マップ生成
        section_admin = new SectionAdmin(150, map_size);
        section_admin.startDivideSection();
        section_admin.startUpdateLeaves();
        section_admin.searchNeighbors();
        section_admin.updateMapData(map_data);
        section_admin.connectRooms(map_data);
        section_admin.makeStairs(map_data);
        section_admin.makeGate(map_data);
        if (accessoryNum > 0) {
            if (accseccaryFloorTileSet != null) {
                if (accseccaryFloorTileSet.length > 0) {
                    section_admin.makeAccessory(map_data, accessoryRate, accseccaryFloorTileSet.length);//Gate,Stairsより後に呼ぶ
                }
            }
        }

//        createMine(5, 5);
        createMine(mine_min_num, mine_max_num);
        //map_object_adminに採掘場所の座標を渡す

        //createMapTileData_old();//以前のバージョン

        if (dungeon_name.equals("Dragon")) {
            for (int i = 0; i < map_size.x; i++) {
                for (int j = 0; j < map_size.y; j++) {
                    map_tile_set[i][j] = null;
                    map_tile[i][j] = null;
                    for (int k = 0; k < animation_num; k++) {
                        map_tile_set_animation[k][i][j] = null;
                        map_tile_animation[k][i][j] = null;
                    }
                }
            }
            for (int i = 0; i < map_size.x; i++) {
                for (int j = 0; j < map_size.y; j++) {
                    if (!isWall(i, j) && !isStairs(i, j)) {
                        setAutoTile_light_floor(i, j, i, j);
                    } else if (isStairs(i, j) && !isGate(i, j)) {
                        map_tile[i][j] = stair_tile;
                    }
//                    if(isGate(i, j)){
//                        for (int k = 0; k < animation_num; k++) {
//                            map_tile_animation[k][i][j] = gate_tile;
//                        }
//                    }
                }
            }
        } else {
            for (int i = 0; i < map_size.x; i++) {
                for (int j = 0; j < map_size.y; j++) {
                    if (!isWall(i, j) && !isStairs(i, j)) {//床
//                    map_tile[i][j] = floor_tile;
                        //アニメーション用
                        for (int k = 0; k < animation_num; k++) {
                            map_tile_animation[k][i][j] = floor_tile;
                        }
                        //階段
                    } else if (isStairs(i, j) && !isGate(i, j)) {//階段
//                    map_tile[i][j] = stair_tile;
                        //アニメーション用
                        for (int k = 0; k < animation_num; k++) {
                            map_tile_animation[k][i][j] = stair_tile;
                        }
                    } else {//壁
                        setAutoTile_light_animation(i, j, i, j, map_tile_animation, false);
                    }
//                    if(isGate(i, j)) {
//                        for (int k = 0; k < animation_num; k++) {
//                            map_tile_animation[k][i][j] = gate_tile;
//                        }
//                    }
                }
            }
        }
    }
    public void resetMapObject() {
        //map_object_admin初期化
        map_object_admin.setMapAdmin(this);
        map_object_admin.spawnMapObject(mine_point);
    }

    //スタート地点を探す
    public void searchStartPoint() {
        Point point = getRoomNotEdgePoint();
        start_point.set(point.x, point.y);
    }

    //部屋のある一点を返す(magnificationをかけてある)
    public Point getRoomPoint() {
        Point point = new Point(0, 0);
        int raw[] = MyAvail.shuffle(map_size.x * map_size.y - 1);
        // by kmhanko
        for (int i = 0; i < raw.length ; i++) {
            //Random rnd = new Random();
            //int x = rnd.nextInt(map_size.x);
            //int y = rnd.nextInt(map_size.y);
            int x = raw[i]/map_size.y;
            int y = raw[i]%map_size.y;

            if (map_data[x][y].isRoom() && !map_data[x][y].isWall()) {
                point.set(x * magnification, y * magnification);
                break;
            }
        }
        return point;
    }

    //階段やゲートでない、部屋のある一点を返す(magnificationをかけてある)
    public Point getRoomPointNotStairsAndNotGate() {
        Point point = new Point(0, 0);
        int raw[] = MyAvail.shuffle(map_size.x * map_size.y - 1);
        // by kmhanko
        for (int i = 0; i < raw.length ; i++) {
            //Random rnd = new Random();
            //int x = rnd.nextInt(map_size.x);
            //int y = rnd.nextInt(map_size.y);
            int x = raw[i]/map_size.y;
            int y = raw[i]%map_size.y;

            if (map_data[x][y].isRoom() && !map_data[x][y].isWall() && !map_data[x][y].isStairs() && !map_data[x][y].isGate()) {
                point.set(x * magnification, y * magnification);
                break;
            }
        }
        return point;
    }

    //部屋のある一点の、スポーンに相応しい位置を返す(magnificationをかけてある)
    public Point getGoodSpawnRoomPoint() {
        Point point = new Point(0, 0);
        int raw[] = MyAvail.shuffle(map_size.x * map_size.y - 1);
        // by kmhanko
        for (int i = 0; i < raw.length ; i++) {
            //Random rnd = new Random();
            //int x = rnd.nextInt(map_size.x);
            //int y = rnd.nextInt(map_size.y);
            int x = raw[i]/map_size.y;
            int y = raw[i]%map_size.y;

            if (map_object_admin.isGoodSpawnPoint(x, y) && map_data[x][y].isRoom() && !map_data[x][y].isGate() && !map_data[x][y].isWall() && !map_data[x][y].isStairs() && !map_data[x][y].isMine()) {
                point.set(x * magnification, y * magnification);
                break;
            }
        }
        return point;
    }

    //プレイヤーとは別
    public Point getGoodSpawnRoomPointWithoutPlayer() {
        Point point = new Point(0, 0);
        int raw[] = MyAvail.shuffle(map_size.x * map_size.y - 1);
        // by kmhanko
        for (int i = 0; i < raw.length ; i++) {
            //Random rnd = new Random();
            //int x = rnd.nextInt(map_size.x);
            //int y = rnd.nextInt(map_size.y);
            int x = raw[i]/map_size.y;
            int y = raw[i]%map_size.y;



            if (map_object_admin.isGoodSpawnPoint(x, y) && map_data[x][y].isRoom() && !map_data[x][y].isGate() && !map_data[x][y].isWall() && !map_data[x][y].isStairs() && !map_data[x][y].isMine() & !map_player.isWithinReach(x * magnification, y * magnification, 10.0f * magnification)) {
                point.set(x * magnification, y * magnification);
                break;
            }
        }
        return point;
    }

    //部屋の壁際の座標を返す
    private Point getRoomEdgePoint() {
        Point point = new Point(0, 0);
        int raw[] = MyAvail.shuffle(map_size.x * map_size.y - 1);
        // by kmhanko
        for (int i = 0; i < raw.length ; i++) {
            //Random rnd = new Random();
            //int x = rnd.nextInt(map_size.x);
            //int y = rnd.nextInt(map_size.y);
            int x = raw[i]/map_size.y;
            int y = raw[i]%map_size.y;

            if (map_data[x][y].isRoom() && !map_data[x][y].isWall() && (map_data[x - 1][y].isWall() || map_data[x + 1][y].isWall() || map_data[x][y - 1].isWall() || map_data[x][y + 1].isWall())) {
                point.set(x * magnification, y * magnification);
                break;
            }
        }
        return point;
    }

    //部屋の壁際の座標かつ採掘ポイントでもなく、階段ポイントでもない地点を返す
    // by kmhanko
    private Point getMinePoint() {
        Point point = new Point(0, 0);
        int raw[] = MyAvail.shuffle(map_size.x * map_size.y - 1);
        // by kmhanko
        for (int i = 0; i < raw.length ; i++) {
            //Random rnd = new Random();
            //int x = rnd.nextInt(map_size.x);
            //int y = rnd.nextInt(map_size.y);
            int x = raw[i]/map_size.y;
            int y = raw[i]%map_size.y;

            if (!map_data[x][y].isStairs() && !map_data[x][y].isMine() && map_data[x][y].isRoom() && !map_data[x][y].isWall() && (map_data[x - 1][y].isWall() || map_data[x + 1][y].isWall() || map_data[x][y - 1].isWall() || map_data[x][y + 1].isWall())) {
                point.set(x * magnification, y * magnification);
                break;
            }
        }
        return point;
    }

    //部屋の壁際以外の座標を返す
    private Point getRoomNotEdgePoint() {
        Point point = new Point(0, 0);
        // by kmhanko
        int raw[] = MyAvail.shuffle(map_size.x * map_size.y - 1);
        for (int i = 0; i < raw.length ; i++) {
            //Random rnd = new Random();
            //int x = rnd.nextInt(map_size.x);
            //int y = rnd.nextInt(map_size.y);
            int x = raw[i]/map_size.y;
            int y = raw[i]%map_size.y;
            if (map_data[x][y].isRoom() && !map_data[x][y].isWall() && (!map_data[x - 1][y].isWall() && !map_data[x + 1][y].isWall() && !map_data[x][y - 1].isWall() && !map_data[x][y + 1].isWall())) {
                point.set(x * magnification, y * magnification);
                break;
            }
        }
        return point;
    }

    //中ボスを返す関数
    public String[] getMidMonster(int mid_boss_num) {
        int k = 0;
        if (mid_boss_num > 3) {
            throw new Error("%☆ホリエ:mid_boss_num > 3 (MapAdmin:getMidMonster)");
        }
        String mid_boss[] = new String[5];
        for (int i = 0; i < dungeon_monster_data.size(); i++) {
            if (dungeon_monster_data.get(i).getType() == 1 && k < mid_boss_num) {
                mid_boss[k] = dungeon_monster_data.get(i).getMonsterName();
                k++;
            }
        }
        return mid_boss;
    }

    //モンスターの名前を返す(最大50体, monster_kind 0:ザコ 1:中ボス 2:ボス)
    public String[] getMonsterName(int monster_kind) {
        int k = 0;
        String monster_name[] = new String[50];//TODO:とりあえず50にしている
        for (int i = 0; i < dungeon_monster_data.size(); i++) {
            if (dungeon_monster_data.get(i).getType() == monster_kind && k < 50) {
                monster_name[k] = dungeon_monster_data.get(i).getMonsterName();
                k++;
            }
        }
        return monster_name;
    }

    //現在の階層を返す
    public int getNowFloorNum(){
        return now_floor_num;
    }

    //ボスフロアかどうかを返す
    public boolean isBossFloor(){
        return now_floor_num == boss_floor_num;
    }

    //room_pointをset(magnificationをかけてない)
    public void setRoomPoint() {
        Point point = getRoomEdgePoint();
        room_point.set(point.x / magnification, point.y / magnification);
    }

    //playerの現在位置を返す
    public Point getNowPoint() {
        return camera.getNowPoint();
    }

    //階層移動
    public void goNextFloor() {
        now_floor_num++;
        if (now_floor_num < boss_floor_num) {
            createMap();

            resetMapObject();//中ボス以外
            //スタート地点を探す
            searchStartPoint();
            camera.setCameraOffset(start_point.x, start_point.y);
            map_player.putUnit(start_point.x + magnification / 2, start_point.y + magnification / 2);
            map_player.setDungeonEnterEncountWaitTime(0);

            //中ボスだけあとでやる
            map_object_admin.spawnEnemy();


        } else {
            goBossFloor();
        }
    }

    //ボスフロアに移動
    private void goBossFloor() {
        //map_dataの初期化
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                map_data[i][j].initializeChip();
                map_data[i][j].setWallFlag(true);
            }
        }
        //ボスマップの空間
        for (int i = 5; i < 10; i++) {
            for (int j = 5; j < 10; j++) {
                map_data[i][j].setWallFlag(false);
            }
        }
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {

                /* by kmhanko
                if (!isWall(i, j) && !isStairs(i, j)) {
                    map_tile[i][j] = floor_tile;
                    //アニメーション用
//                    for(int k = 0;k < 3;k++) {
//                        map_tile_set_animation[k][2*i][2*j] = floor_tile;
//                        map_tile_set_animation[k][2*i+1][2*j] = floor_tile;
//                        map_tile_set_animation[k][2*i][2*j+1] = floor_tile;
//                        map_tile_set_animation[k][2*i+1][2*j+1] = floor_tile;
//                    }
                    //階段
                } else if (isStairs(i, j)) {
                    map_tile[i][j] = stair_tile;
                    //アニメーション用
//                    for (int k = 0; k < 3; k++) {
//                        map_tile_set_animation[k][2 * i][2 * j] = stair_tile_div[0];
//                        map_tile_set_animation[k][2 * i + 1][2 * j] = stair_tile_div[1];
//                        map_tile_set_animation[k][2 * i][2 * j + 1] = stair_tile_div[2];
//                        map_tile_set_animation[k][2 * i + 1][2 * j + 1] = stair_tile_div[3];
//                    }
                } else {
                    setAutoTile_light(i, j, i, j);
                }
               */
                //by kmhanko
                if (!isWall(i, j) && !isStairs(i, j)) {
//                    map_tile[i][j] = floor_tile;
                    //アニメーション用
                    for (int k = 0; k < animation_num; k++) {
                        map_tile_animation[k][i][j] = floor_tile;
                    }
                    //階段
                } else if (isStairs(i, j)) {
//                    map_tile[i][j] = stair_tile;
                    //アニメーション用
                    for (int k = 0; k < animation_num; k++) {
                        map_tile_animation[k][i][j] = stair_tile;
                    }
                } else {
                    setAutoTile_light_animation(i, j, i, j, map_tile_animation, false);
                }
            }
        }

        //if(boss_floor_num == 1) {

        //}
        camera.setCameraOffset(7.5 * magnification, 9 * magnification);
        map_player.putUnit(7.5 * magnification, 9 * magnification);

        resetMapObject();
    }

    //採掘場所を作る(部屋の淵のみに生成)
    private void createMine(int min_num, int max_num) {
        Random rnd = new Random();
        int mine_num = rnd.nextInt(max_num - min_num + 1) + min_num;
        for (int i = 0; i < mine_num; i++) {
            /*
            while (true) {
                setRoomPoint();
                if (!map_data[room_point.x][room_point.y].isMine() && !map_data[room_point.x][room_point.y].isStairs()) {
                    map_data[room_point.x][room_point.y].setMineFlag(true);
                    mine_point[i].set(room_point.x, room_point.y);
                    break;
                }
            }*/
            Point point = getMinePoint();
            room_point.set(point.x / magnification, point.y / magnification);
            map_data[room_point.x][room_point.y].setMineFlag(true);
            mine_point[i].set(room_point.x, room_point.y);
        }
    }

    //一歩先に壁があるかどうかと壁の方向を判定
    public int detectWallDirection(double player_world_x_double, double player_world_y_double, double next_player_world_x_double, double next_player_world_y_double) {
        int player_world_x = (int) player_world_x_double;
        int player_world_y = (int) player_world_y_double;
        int next_player_world_x = (int) next_player_world_x_double;
        int next_player_world_y = (int) next_player_world_y_double;
        int direction = 0;//0 : 壁なし, 1 : 水平, 2 : 垂直
        int player_map_x = worldToMap(player_world_x);//移動前のマップ座標x
        int player_map_y = worldToMap(player_world_y);//移動前のマップ座標y
        int next_player_map_x = worldToMap(next_player_world_x);//移動後のマップ座標x
        int next_player_map_y = worldToMap(next_player_world_y);//移動後のマップ座標y

        //by kmhanko
        if (next_player_map_x >= map_size.x || next_player_map_x < 0) {
            return 1;
        }
        if (next_player_map_y >= map_size.y || next_player_map_y < 0) {
            return 2;
        }

        //プレイヤー座標と移動座標が同じマス
        if (player_map_x == next_player_map_x && player_map_y == next_player_map_y) {
            direction = 0;
        }

        if(next_player_map_x < 0){
            next_player_map_x = 0;
        }

        if(next_player_map_y < 0){
            next_player_map_y = 0;
        }

        if(player_map_x < -1){
            player_map_x = 0;
        }

        if(player_map_y < -1){
            player_map_y = 0;
        }

        //左右のマスに移動
        else if (player_map_y == next_player_map_y) {
            //次の移動先のマスが壁である

            if (map_data[next_player_map_x][next_player_map_y].isWall()) {
                direction = 2;
            }
            //移動先のマスが壁でない
            else {
                direction = 0;
            }

        }
        //上下のマスに移動
        else if (player_map_x == next_player_map_x) {
            //移動先のマスが壁である

            if (map_data[next_player_map_x][next_player_map_y].isWall()) {
                direction = 1;
            }
            //移動先のマスが壁でない
            else {
                direction = 0;
            }

        }
        //斜めのマスに移動
        else {
            double a, b, x_up, y_up, x_down, y_down, x_left, y_left, x_right, y_right, dst2_left, dst2_right, dst2_up, dst2_down;
            //1次関数y = ax + bを求める
            a = (next_player_world_y - player_world_y) / (next_player_world_x - player_world_x);
            b = player_world_y - a * player_world_x;
            x_left = magnification * (player_map_x - 1);
            y_left = a * x_left + b;  //x = 100 * (player_map_x - 1)の時のy座標(左の垂直壁のy座標)
            x_right = magnification * (player_map_x);
            y_right = a * x_right + b;     //x = 100 * (player_map_x)の時のy座標(右の垂直壁のy座標)
            y_up = magnification * (player_map_y - 1);
            x_up = (y_up - b) / a;  //y = 100 * (player_map_y - 1)の時のx座標(上の水平壁のx座標)
            y_down = magnification * (player_map_y);
            x_down = (y_down - b) / a;    //y = 100 * (player_map_y)の時のx座標(下の水平壁のx座標)
            //それぞれの壁の交点までの距離の2乗
            dst2_left = Math.pow(x_left - player_world_x, 2) + Math.pow(y_left - player_world_y, 2);
            dst2_right = Math.pow(x_right - player_world_x, 2) + Math.pow(y_right - player_world_y, 2);
            dst2_up = Math.pow(x_up - player_world_x, 2) + Math.pow(y_up - player_world_y, 2);
            dst2_down = Math.pow(x_down - player_world_x, 2) + Math.pow(y_down - player_world_y, 2);
            //移動先が左上
            if (next_player_map_x == player_map_x - 1 && next_player_map_y == player_map_y - 1) {
                if (dst2_left >= dst2_up) {
                    if (map_data[player_map_x][player_map_y - 1].isWall()) {
                        direction = 1;
                    } else if (map_data[player_map_x - 1][player_map_y - 1].isWall()) {
                        direction = 2;
                    }
                } else if (dst2_left < dst2_up) {
                    if (map_data[player_map_x - 1][player_map_y].isWall()) {
                        direction = 2;
                    } else if (map_data[player_map_x - 1][player_map_y - 1].isWall()) {
                        direction = 1;
                    }
                }
            }
            //移動先が右上
            if (next_player_map_x == player_map_x + 1 && next_player_map_y == player_map_y - 1) {
                if (dst2_right >= dst2_up) {
                    if (map_data[player_map_x][player_map_y - 1].isWall()) {
                        direction = 1;
                    } else if (map_data[player_map_x + 1][player_map_y - 1].isWall()) {
                        direction = 2;
                    }
                } else if (dst2_right < dst2_up) {
                    if (map_data[player_map_x + 1][player_map_y].isWall()) {
                        direction = 2;
                    } else if (map_data[player_map_x + 1][player_map_y - 1].isWall()) {
                        direction = 1;
                    }
                }
            }
            //移動先が左下
            if (next_player_map_x == player_map_x - 1 && next_player_map_y == player_map_y + 1) {
                if (dst2_left >= dst2_down) {
                    if (map_data[player_map_x][player_map_y + 1].isWall()) {
                        direction = 1;
                    } else if (map_data[player_map_x - 1][player_map_y + 1].isWall()) {
                        direction = 2;
                    }
                } else if (dst2_left < dst2_down) {
                    if (map_data[player_map_x - 1][player_map_y].isWall()) {
                        direction = 2;
                    } else if (map_data[player_map_x - 1][player_map_y + 1].isWall()) {
                        direction = 1;
                    }
                }
            }
            //移動先が右下
            if (next_player_map_x == player_map_x + 1 && next_player_map_y == player_map_y + 1) {
                if (dst2_right >= dst2_down) {
                    if (map_data[player_map_x][player_map_y + 1].isWall()) {
                        direction = 1;
                    } else if (map_data[player_map_x + 1][player_map_y + 1].isWall()) {
                        direction = 2;
                    }
                } else if (dst2_right < dst2_down) {
                    if (map_data[player_map_x + 1][player_map_y].isWall()) {
                        direction = 2;
                    } else if (map_data[player_map_x + 1][player_map_y + 1].isWall()) {
                        direction = 1;
                    }
                }
            }
        }
        return direction;
    }

    //マップ描画
    //4分割した物をくっつけて保存して表示(アニメーション)
//    public void drawMap_for_autotile_4div_combine_animation() {
//        Paint l_gray_paint = new Paint();
//        Paint green_paint = new Paint();
//        Room now_point_room = new Room();
//        boolean is_debug_mode = false;
//        time++;
//        int mx = worldToMap(camera.getCameraOffset().x + 800);
//        int my = worldToMap(camera.getCameraOffset().y + 450);
//        if (map_data[mx][my].isStairs()) {
//            goNextFloor();
//        }
//
//        //周りを黒くする
//        graphic.bookingDrawBitmapData(auto_tile_wall.raw_auto_tile[4], 0, 0, 1600 / 32, 900 / 32, 0, 255, true);
//
//        int draw_mode = 2;
//        //4分割のままで表示
//        if (draw_mode == 1) {
//            for (int i = 0; i < map_size.x; i++) {
//                for (int j = 0; j < map_size.y; j++) {
//                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                        graphic.bookingDrawBitmapData(map_tile_set[2 * i][2 * j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                        graphic.bookingDrawBitmapData(map_tile_set[2 * i + 1][2 * j], camera.convertToNormCoordinateXForMap(i * magnification + magnification / 2), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                        graphic.bookingDrawBitmapData(map_tile_set[2 * i][2 * j + 1], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification + magnification / 2), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                        graphic.bookingDrawBitmapData(map_tile_set[2 * i + 1][2 * j + 1], camera.convertToNormCoordinateXForMap(i * magnification + magnification / 2), camera.convertToNormCoordinateYForMap(j * magnification + magnification / 2), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                    }
//                }
//            }
//        }
//
//        //4つを1つに纏めて表示
//        else if (draw_mode == 2) {
//            for (int i = 0; i < map_size.x; i++) {
//                for (int j = 0; j < map_size.y; j++) {
//                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                        graphic.bookingDrawBitmapData(map_tile_animation[(time / 3) % 3][i][j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                    }
//                }
//            }
//        }
//
//        //1つの画像で表示
//        else if (draw_mode == 3) {
//            graphic.bookingDrawBitmapData(map_image, -1 * camera.camera_offset.x - magnification, -1 * camera.camera_offset.y, 1, 1, 0, 255, true);
//        }
//
//        //1つの画像を切り取って表示
//        else if (draw_mode == 4) {
//            BitmapData trim_map_data;
//            int l_x = map_size.x;
//            int r_x = -1;
//            int u_y = map_size.y;
//            int d_y = -1;
//            for (int i = 0; i < map_size.x; i++) {
//                for (int j = 0; j < map_size.y; j++) {
//                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                        if (i < l_x) {
//                            l_x = i;
//                        }
//                        if (r_x < i) {
//                            r_x = i;
//                        }
//                        if (j < u_y) {
//                            u_y = j;
//                        }
//                        if (d_y < j) {
//                            d_y = j;
//                        }
//                    }
//                }
//            }
//            trim_map_data = graphic.processTrimmingBitmapData(map_image, l_x * 64, u_y * 64, r_x * 64 - l_x * 64, d_y * 64 - u_y * 64);
//            graphic.bookingDrawBitmapData(trim_map_data, -64, -64, 1, 1, 0, 255, true);
//        }
//
//        //中心点の表示
//        if (is_debug_mode) {
//            paint.setColor(Color.RED);
//            graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
//        }
//        updateMiniMapDispState(worldToMap(camera.getCameraOffset().x + 800), worldToMap(camera.getCameraOffset().y + 450));
//        drawSmallMap3(camera.getCameraOffset().x + 800, camera.getCameraOffset().y + 450);
//        //画像表示デバッグ用
//        //graphic.bookingDrawBitmapData(auto_tile_cave_hole[(time/3)%3].raw_auto_tile[1], 0, 0, 5, 5, 0, 255, true);
//    }

    //4分割した物を事前にくっつけて保存して表示
    public void drawMap_for_autotile_light() {
        boolean is_debug_mode = false;
        int mx = worldToMap(camera.getCameraOffset().x + 800);
        int my = worldToMap(camera.getCameraOffset().y + 450);
        int count = 0;
        if (map_data[mx][my].isStairs()) {
            goNextFloor();
        }

        //周りを黒くする
//        graphic.bookingDrawBitmapData(auto_tile_wall.raw_auto_tile[4], 0, 0, 1600/32, 900/32, 0, 255, true);

        //マップ表示
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification &&
                        camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification &&
                        camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification &&
                        camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification &&
                        map_tile[i][j] != null) {
                    graphic.bookingDrawBitmapData(map_tile[i][j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                    if(map_data[i][j].isGate()){
                        graphic.bookingDrawBitmapData(gate_tile, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                    }
                    count++;
                }
            }
        }
//        System.out.println("draw count = " + count);

        //中心点の表示
        if (is_debug_mode) {
            paint.setColor(Color.RED);
            graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        }
        //画像表示デバッグ用
        //graphic.bookingDrawBitmapData(auto_tile_wall.big_auto_tile[46], 0, 0, 1, 1, 0, 255, true);
    }

    //4分割した物を事前にくっつけて保存して表示,アニメーション(現行バージョン)
    public void drawMap_for_autotile_light_animation() {
        boolean is_debug_mode = false;
        int mx = worldToMap(camera.getCameraOffset().x + 800);
        int my = worldToMap(camera.getCameraOffset().y + 450);
//        int count = 0;

        /* MapPlayerクラスのupdate関数に移動
        if (map_data[mx][my].isStairs()) {
            goNextFloor();
        }

        //ゲート脱出
        if (map_data[mx][my].isGate()) {
            map_object_admin.escapeDungeon();
        }
        */

        //周りを黒くする
//        graphic.bookingDrawBitmapData(auto_tile_wall.raw_auto_tile[4], 0, 0, 1600/32, 900/32, 0, 255, true);

        //マップ表示
        if(dungeon_name.equals("Dragon")) {
            drawMap_for_autotile_light();
        }
        else {
            time++;

            int accessory[] = new int[4];
            for (int i = 0; i < map_size.x; i++) {
                for (int j = 0; j < map_size.y; j++) {
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawBitmapData(map_tile_animation[(time / 3) % animation_num][i][j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                        if(map_data[i][j].isGate()){
                            graphic.bookingDrawBitmapData(gate_tile, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                        }

                        //オブジェクトとして飾りを描画
                        if (map_data[i][j].isAccessory()) {
                            accessory = map_data[i][j].getAccessory();
                            for (int k = 0; k < 4; k++) {
                                if (accessory[k] >= 0 && accessory[k] < accseccaryFloorTileSet.length) {
                                    graphic.bookingDrawBitmapData(accseccaryFloorTileSet[accessory[k]], camera.convertToNormCoordinateXForMap(i * magnification + magnification/2*(k/2)), camera.convertToNormCoordinateYForMap(j * magnification + magnification/2*(k%2)), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                                }
                            }
                        }

                    }
                }
            }
//        System.out.println("draw count = "+count);
        }
        //画像表示デバッグ用
//        graphic.bookingDrawBitmapData(at_floor.big_auto_tile[34], 0, 0, 1, 1, 0, 255, true);
    }

    public void drawSmallMap() {
        if (now_floor_num < boss_floor_num) {
            //ミニマップの表示
            updateMiniMapDispState(worldToMap(camera.getNowPoint().x), worldToMap(camera.getNowPoint().y));
            drawSmallMap3(camera.getNowPoint().x, camera.getNowPoint().y);
        }
    }

//    private void createMapTileSet(boolean lu, boolean u, boolean ru, boolean l, boolean r, boolean ld, boolean d, boolean rd, AutoTile m_auto_tile, int i, int j, BitmapData map_tile_set[][]) {
//        if (lu && u && ru && l && r && ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[0];
//        } else if (!lu && u && ru && l && r && ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[1];
//        } else if (lu && u && !ru && l && r && ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[2];
//        } else if (lu && u && ru && l && r && ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[3];
//        } else if (lu && u && ru && l && r && !ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[4];
//        }
//        //5
//        else if (!lu && u && !ru && l && r && ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[5];
//        } else if (lu && u && !ru && l && r && ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[6];
//        } else if (lu && u && ru && l && r && !ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[7];
//        } else if (!lu && u && ru && l && r && !ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[8];
//        } else if (!lu && u && ru && l && r && ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[9];
//        }
//        //10
//        else if (lu && u && !ru && l && r && !ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[10];
//        } else if (lu && u && !ru && l && r && !ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[11];
//        } else if (!lu && u && ru && l && r && !ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[12];
//        } else if (!lu && u && !ru && l && r && !ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[13];
//        } else if (!lu && u && !ru && l && r && ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[14];
//        }
//        //15
//        else if (!lu && u && !ru && l && r && !ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[15];
//        } else if (u && ru && !l && r && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[16];
//        } else if (u && !ru && !l && r && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[17];
//        } else if (u && ru && !l && r && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[18];
//        } else if (u && !ru && !l && r && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[19];
//        }
//        //20
//        else if (!u && l && r && ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[20];
//        } else if (!u && l && r && ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[21];
//        } else if (!u && l && r && !ld && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[22];
//        } else if (!u && l && r && !ld && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[23];
//        } else if (lu && u && l && !r && ld && d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[24];
//        }
//        //25
//        else if (lu && u && l && !r && !ld && d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[25];
//        } else if (!lu && u && l && !r && ld && d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[26];
//        } else if (!lu && u && l && !r && !ld && d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[27];
//        } else if (lu && u && ru && l && r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[28];
//        } else if (!lu && u && ru && l && r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[29];
//        }
//        //30
//        else if (lu && u && !ru && l && r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[30];
//        } else if (!lu && u && !ru && l && r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[31];
//        } else if (u && !l && !r && d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[32];
//        } else if (!u && l && r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[33];
//        } else if (!u && !l && r && d && rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[34];
//        }
//        //35
//        else if (!u && !l && r && d && !rd) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[35];
//        } else if (!u && l && !r && ld && d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[36];
//        } else if (!u && l && !r && !ld && d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[37];
//        } else if (lu && u && l && !r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[38];
//        } else if (!lu && u && l && !r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[39];
//        }
//        //40
//        else if (u && ru && !l && r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[40];
//        } else if (u && !ru && !l && r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[41];
//        } else if (!u && !l && !r && d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[42];
//        } else if (!u && !l && r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[43];
//        } else if (u && !l && !r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[44];
//        }
//        //45
//        else if (!u && l && !r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[45];
//        } else if (!u && !l && !r && !d) {
//            map_tile_set[i][j] = m_auto_tile.auto_tile[46];
//        }
//    }

    private void createMapTileSetForBitmap(boolean lu, boolean u, boolean ru, boolean l, boolean r, boolean ld, boolean d, boolean rd, BitmapData m_auto_tile[], int i, int j, BitmapData map_tile_set[][]) {
        if (lu && u && ru && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[0];
        } else if (!lu && u && ru && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[1];
        } else if (lu && u && !ru && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[2];
        } else if (lu && u && ru && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[48];
        } else if (lu && u && ru && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[47];
        }
        //5
        else if (!lu && u && !ru && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[5];
        } else if (lu && u && !ru && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[6];
        } else if (lu && u && ru && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[51];
        } else if (!lu && u && ru && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[8];
        } else if (!lu && u && ru && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[9];
        }
        //10
        else if (lu && u && !ru && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[10];
        } else if (lu && u && !ru && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[11];
        } else if (!lu && u && ru && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[12];
        } else if (!lu && u && !ru && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[13];
        } else if (!lu && u && !ru && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[14];
        }
        //15
        else if (!lu && u && !ru && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[15];
        } else if ((u && ru && !l && r && d && rd)) {
            map_tile_set[i][j] = m_auto_tile[16];
        } else if (u && !ru && !l && r && d && rd) {
            map_tile_set[i][j] = m_auto_tile[17];
        } else if (u && ru && !l && r && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[50];
        } else if (u && !ru && !l && r && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[19];
        }
        //20
        else if (!u && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[20];
        } else if (!u && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[21];
        } else if (!u && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[22];
        } else if (!u && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[23];
        } else if ((lu && u && l && !r && ld && d)) {
            map_tile_set[i][j] = m_auto_tile[24];
        }
        //25
        else if (lu && u && l && !r && !ld && d) {
            map_tile_set[i][j] = m_auto_tile[49];
        } else if (!lu && u && l && !r && ld && d) {
            map_tile_set[i][j] = m_auto_tile[26];
        } else if (!lu && u && l && !r && !ld && d) {
            map_tile_set[i][j] = m_auto_tile[27];
        } else if (lu && u && ru && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[28];
        } else if (!lu && u && ru && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[29];
        }
        //30
        else if (lu && u && !ru && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[30];
        } else if (!lu && u && !ru && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[31];
        } else if (u && !l && !r && d) {
            map_tile_set[i][j] = m_auto_tile[32];
        } else if (!u && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[33];
        } else if (!u && !l && r && d && rd) {
            map_tile_set[i][j] = m_auto_tile[34];
        }
        //35
        else if (!u && !l && r && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[35];
        } else if (!u && l && !r && ld && d) {
            map_tile_set[i][j] = m_auto_tile[36];
        } else if (!u && l && !r && !ld && d) {
            map_tile_set[i][j] = m_auto_tile[37];
        } else if (lu && u && l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[38];
        } else if (!lu && u && l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[39];
        }
        //40
        else if (u && ru && !l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[40];
        } else if (u && !ru && !l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[41];
        } else if (!u && !l && !r && d) {
            map_tile_set[i][j] = m_auto_tile[42];
        } else if (!u && !l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[43];
        } else if (u && !l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[44];
        }
        //45
        else if (!u && l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[45];
        } else if (!u && !l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[46];
        }
    }

    private void createMapTileSetForBitmap_floor(boolean lu, boolean u, boolean ru, boolean l, boolean r, boolean ld, boolean d, boolean rd, BitmapData m_auto_tile[], int i, int j, BitmapData map_tile_set[][]) {
        if (lu && u && ru && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[0];
        } else if (!lu && u && ru && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[1];
        } else if (lu && u && !ru && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[2];
        } else if (lu && u && ru && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[3];
        } else if (lu && u && ru && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[4];
        }
        //5
        else if (!lu && u && !ru && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[5];
        } else if (lu && u && !ru && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[6];
        } else if (lu && u && ru && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[7];
        } else if (!lu && u && ru && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[8];
        } else if (!lu && u && ru && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[9];
        }
        //10
        else if (lu && u && !ru && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[10];
        } else if (lu && u && !ru && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[11];
        } else if (!lu && u && ru && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[12];
        } else if (!lu && u && !ru && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[13];
        } else if (!lu && u && !ru && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[14];
        }
        //15
        else if (!lu && u && !ru && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[15];
        } else if (u && ru && !l && r && d && rd) {
            map_tile_set[i][j] = m_auto_tile[16];
        } else if (u && !ru && !l && r && d && rd) {
            map_tile_set[i][j] = m_auto_tile[17];
        } else if (u && ru && !l && r && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[18];
        } else if (u && !ru && !l && r && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[19];
        }
        //20
        else if (!u && l && r && ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[20];
        } else if (!u && l && r && ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[21];
        } else if (!u && l && r && !ld && d && rd) {
            map_tile_set[i][j] = m_auto_tile[22];
        } else if (!u && l && r && !ld && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[23];
        } else if (lu && u && l && !r && ld && d) {
            map_tile_set[i][j] = m_auto_tile[24];
        }
        //25
        else if (lu && u && l && !r && !ld && d) {
            map_tile_set[i][j] = m_auto_tile[25];
        } else if (!lu && u && l && !r && ld && d) {
            map_tile_set[i][j] = m_auto_tile[26];
        } else if (!lu && u && l && !r && !ld && d) {
            map_tile_set[i][j] = m_auto_tile[27];
        } else if (lu && u && ru && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[28];
        } else if (!lu && u && ru && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[29];
        }
        //30
        else if (lu && u && !ru && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[30];
        } else if (!lu && u && !ru && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[31];
        } else if (u && !l && !r && d) {
            map_tile_set[i][j] = m_auto_tile[32];
        } else if (!u && l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[33];
        } else if (!u && !l && r && d && rd) {
            map_tile_set[i][j] = m_auto_tile[34];
        }
        //35
        else if (!u && !l && r && d && !rd) {
            map_tile_set[i][j] = m_auto_tile[35];
        } else if (!u && l && !r && ld && d) {
            map_tile_set[i][j] = m_auto_tile[36];
        } else if (!u && l && !r && !ld && d) {
            map_tile_set[i][j] = m_auto_tile[37];
        } else if (lu && u && l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[38];
        } else if (!lu && u && l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[39];
        }
        //40
        else if (u && ru && !l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[40];
        } else if (u && !ru && !l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[41];
        } else if (!u && !l && !r && d) {
            map_tile_set[i][j] = m_auto_tile[42];
        } else if (!u && !l && r && !d) {
            map_tile_set[i][j] = m_auto_tile[43];
        } else if (u && !l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[44];
        }
        //45
        else if (!u && l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[45];
        } else if (!u && !l && !r && !d) {
            map_tile_set[i][j] = m_auto_tile[46];
        }
    }

    //壁の表示
    //4分割しない
//    private void drawWall(int array_x, int array_y, int draw_point_x, int draw_point_y, float magnification) {
//        int before_array_x = array_x - 1;
//        int after_array_x = array_x + 1;
//        int before_array_y = array_y - 1;
//        int after_array_y = array_y + 1;
//        if (before_array_x < 0) {
//            before_array_x = 0;
//        }
//        if (after_array_x > map_size.x - 1) {
//            after_array_x = map_size.x - 1;
//        }
//        if (before_array_y < 0) {
//            before_array_y = 0;
//        }
//        if (after_array_y > map_size.y - 1) {
//            after_array_y = map_size.y - 1;
//        }
//        auto_tile_admin.drawAutoTile(map_data[before_array_x][before_array_y].isWall(), map_data[array_x][before_array_y].isWall(), map_data[after_array_x][before_array_y].isWall(), map_data[before_array_x][array_y].isWall(), map_data[after_array_x][array_y].isWall(), map_data[before_array_x][after_array_y].isWall(), map_data[array_x][after_array_y].isWall(), map_data[after_array_x][after_array_y].isWall(),
//                auto_tile_wall, draw_point_x, draw_point_y, magnification);
//    }

    //4分割
//    private void drawWall2(int array_x, int array_y, int draw_point_x, int draw_point_y, float magnification) {
//        int before_array_x = array_x - 1;
//        int after_array_x = array_x + 1;
//        int before_array_y = array_y - 1;
//        int after_array_y = array_y + 1;
//        if (before_array_x < 0) {
//            before_array_x = 0;
//        }
//        if (after_array_x > map_size.x * 2 - 1) {
//            after_array_x = map_size.x * 2 - 1;
//        }
//        if (before_array_y < 0) {
//            before_array_y = 0;
//        }
//        if (after_array_y > map_size.y * 2 - 1) {
//            after_array_y = map_size.y * 2 - 1;
//        }
//        if (!is_map_data_sidewall[array_x][array_y]) {
//            auto_tile_admin.drawAutoTile(
//                    is_map_data_wall[before_array_x][before_array_y] && !is_map_data_sidewall[before_array_x][before_array_y], is_map_data_wall[array_x][before_array_y] && !is_map_data_sidewall[array_x][before_array_y], is_map_data_wall[after_array_x][before_array_y] && !is_map_data_sidewall[after_array_x][before_array_y],
//                    is_map_data_wall[before_array_x][array_y] && !is_map_data_sidewall[before_array_x][array_y], is_map_data_wall[after_array_x][array_y] && !is_map_data_sidewall[after_array_x][array_y],
//                    is_map_data_wall[before_array_x][after_array_y] && !is_map_data_sidewall[before_array_x][after_array_y], is_map_data_wall[array_x][after_array_y] && !is_map_data_sidewall[array_x][after_array_y], is_map_data_wall[after_array_x][after_array_y] && !is_map_data_sidewall[after_array_x][after_array_y],
//                    auto_tile_wall, draw_point_x, draw_point_y, magnification);
//        } else {
//            auto_tile_admin.drawAutoTile(
//                    is_map_data_sidewall[before_array_x][before_array_y], is_map_data_sidewall[array_x][before_array_y], is_map_data_sidewall[after_array_x][before_array_y],
//                    is_map_data_sidewall[before_array_x][array_y], is_map_data_sidewall[after_array_x][array_y],
//                    is_map_data_sidewall[before_array_x][after_array_y], is_map_data_sidewall[array_x][after_array_y], is_map_data_sidewall[after_array_x][after_array_y],
//                    auto_tile_side_wall, draw_point_x, draw_point_y, magnification);
//        }
//    }

    /*private void setAutoTile(int array_x, int array_y, int i, int j) {
        int before_array_x = array_x - 1;
        int after_array_x = array_x + 1;
        int before_array_y = array_y - 1;
        int after_array_y = array_y + 1;
        if (before_array_x < 0) {
            before_array_x = 0;
        }
        if (after_array_x > map_size.x * 2 - 1) {
            after_array_x = map_size.x * 2 - 1;
        }
        if (before_array_y < 0) {
            before_array_y = 0;
        }
        if (after_array_y > map_size.y * 2 - 1) {
            after_array_y = map_size.y * 2 - 1;
        }
        if (!is_map_data_sidewall[array_x][array_y]) {
            createMapTileSet(
                    is_map_data_wall[before_array_x][before_array_y] && !is_map_data_sidewall[before_array_x][before_array_y], is_map_data_wall[array_x][before_array_y] && !is_map_data_sidewall[array_x][before_array_y], is_map_data_wall[after_array_x][before_array_y] && !is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_wall[before_array_x][array_y] && !is_map_data_sidewall[before_array_x][array_y], is_map_data_wall[after_array_x][array_y] && !is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_wall[before_array_x][after_array_y] && !is_map_data_sidewall[before_array_x][after_array_y], is_map_data_wall[array_x][after_array_y] && !is_map_data_sidewall[array_x][after_array_y], is_map_data_wall[after_array_x][after_array_y] && !is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_wall, i, j, map_tile_set);
            createMapTileSet(
                    is_map_data_wall[before_array_x][before_array_y] && !is_map_data_sidewall[before_array_x][before_array_y], is_map_data_wall[array_x][before_array_y] && !is_map_data_sidewall[array_x][before_array_y], is_map_data_wall[after_array_x][before_array_y] && !is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_wall[before_array_x][array_y] && !is_map_data_sidewall[before_array_x][array_y], is_map_data_wall[after_array_x][array_y] && !is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_wall[before_array_x][after_array_y] && !is_map_data_sidewall[before_array_x][after_array_y], is_map_data_wall[array_x][after_array_y] && !is_map_data_sidewall[array_x][after_array_y], is_map_data_wall[after_array_x][after_array_y] && !is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_cave_hole[0], i, j, map_tile_set_animation[0]);
            createMapTileSet(
                    is_map_data_wall[before_array_x][before_array_y] && !is_map_data_sidewall[before_array_x][before_array_y], is_map_data_wall[array_x][before_array_y] && !is_map_data_sidewall[array_x][before_array_y], is_map_data_wall[after_array_x][before_array_y] && !is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_wall[before_array_x][array_y] && !is_map_data_sidewall[before_array_x][array_y], is_map_data_wall[after_array_x][array_y] && !is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_wall[before_array_x][after_array_y] && !is_map_data_sidewall[before_array_x][after_array_y], is_map_data_wall[array_x][after_array_y] && !is_map_data_sidewall[array_x][after_array_y], is_map_data_wall[after_array_x][after_array_y] && !is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_cave_hole[1], i, j, map_tile_set_animation[1]);
            createMapTileSet(
                    is_map_data_wall[before_array_x][before_array_y] && !is_map_data_sidewall[before_array_x][before_array_y], is_map_data_wall[array_x][before_array_y] && !is_map_data_sidewall[array_x][before_array_y], is_map_data_wall[after_array_x][before_array_y] && !is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_wall[before_array_x][array_y] && !is_map_data_sidewall[before_array_x][array_y], is_map_data_wall[after_array_x][array_y] && !is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_wall[before_array_x][after_array_y] && !is_map_data_sidewall[before_array_x][after_array_y], is_map_data_wall[array_x][after_array_y] && !is_map_data_sidewall[array_x][after_array_y], is_map_data_wall[after_array_x][after_array_y] && !is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_cave_hole[2], i, j, map_tile_set_animation[2]);
        } else {
            createMapTileSet(
                    is_map_data_sidewall[before_array_x][before_array_y], is_map_data_sidewall[array_x][before_array_y], is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_sidewall[before_array_x][array_y], is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_sidewall[before_array_x][after_array_y], is_map_data_sidewall[array_x][after_array_y], is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_side_wall, i, j, map_tile_set);
            createMapTileSet(
                    is_map_data_sidewall[before_array_x][before_array_y], is_map_data_sidewall[array_x][before_array_y], is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_sidewall[before_array_x][array_y], is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_sidewall[before_array_x][after_array_y], is_map_data_sidewall[array_x][after_array_y], is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_side_wall, i, j, map_tile_set_animation[0]);
            createMapTileSet(
                    is_map_data_sidewall[before_array_x][before_array_y], is_map_data_sidewall[array_x][before_array_y], is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_sidewall[before_array_x][array_y], is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_sidewall[before_array_x][after_array_y], is_map_data_sidewall[array_x][after_array_y], is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_side_wall, i, j, map_tile_set_animation[1]);
            createMapTileSet(
                    is_map_data_sidewall[before_array_x][before_array_y], is_map_data_sidewall[array_x][before_array_y], is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_sidewall[before_array_x][array_y], is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_sidewall[before_array_x][after_array_y], is_map_data_sidewall[array_x][after_array_y], is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_side_wall, i, j, map_tile_set_animation[2]);
        }
    }*/

//    private void setAutoTile_light(int array_x, int array_y, int i, int j) {
//        int before_array_x = array_x - 1;
//        int after_array_x = array_x + 1;
//        int before_array_y = array_y - 1;
//        int after_array_y = array_y + 1;
//        if (before_array_x < 0) {
//            before_array_x = 0;
//        }
//        if (after_array_x > map_size.x - 1) {
//            after_array_x = map_size.x - 1;
//        }
//        if (before_array_y < 0) {
//            before_array_y = 0;
//        }
//        if (after_array_y > map_size.y - 1) {
//            after_array_y = map_size.y - 1;
//        }
//        createMapTileSetForBitmap(
//                isWall(before_array_x, before_array_y), isWall(array_x, before_array_y), isWall(after_array_x, before_array_y),
//                isWall(before_array_x, array_y), isWall(after_array_x, array_y),
//                isWall(before_array_x, after_array_y), isWall(array_x, after_array_y), isWall(after_array_x, after_array_y),
//                auto_tile_wall.big_auto_tile, i, j, map_tile);
//    }

    private void setAutoTile_light_animation(int array_x, int array_y, int i, int j, BitmapData _map_data[][][], boolean isOpening) {
        int before_array_x = array_x - 1;
        int after_array_x = array_x + 1;
        int before_array_y = array_y - 1;
        int after_array_y = array_y + 1;

        if(isOpening) {
            if (before_array_x < 0) {
                before_array_x = 0;
            }
            if (after_array_x > opening_map_size.x - 1) {
                after_array_x = opening_map_size.x - 1;
            }
            if (before_array_y < 0) {
                before_array_y = 0;
            }
            if (after_array_y > opening_map_size.y - 1) {
                after_array_y = opening_map_size.y - 1;
            }
            for (int k = 0; k < animation_num; k++) {
                createMapTileSetForBitmap(
                        isWall_op(before_array_x, before_array_y), isWall_op(array_x, before_array_y), isWall_op(after_array_x, before_array_y),
                        isWall_op(before_array_x, array_y), isWall_op(after_array_x, array_y),
                        isWall_op(before_array_x, after_array_y), isWall_op(array_x, after_array_y), isWall_op(after_array_x, after_array_y),
                        at_wall[k].big_auto_tile, i, j, _map_data[k]);
            }
        }
        else{
            if (before_array_x < 0) {
                before_array_x = 0;
            }
            if (after_array_x > map_size.x - 1) {
                after_array_x = map_size.x - 1;
            }
            if (before_array_y < 0) {
                before_array_y = 0;
            }
            if (after_array_y > map_size.y - 1) {
                after_array_y = map_size.y - 1;
            }
            for (int k = 0; k < animation_num; k++) {
                createMapTileSetForBitmap(
                        isWall(before_array_x, before_array_y), isWall(array_x, before_array_y), isWall(after_array_x, before_array_y),
                        isWall(before_array_x, array_y), isWall(after_array_x, array_y),
                        isWall(before_array_x, after_array_y), isWall(array_x, after_array_y), isWall(after_array_x, after_array_y),
                        at_wall[k].big_auto_tile, i, j, _map_data[k]);
            }
        }
    }

    private void setAutoTile_light_floor(int array_x, int array_y, int i, int j) {
        int before_array_x = array_x - 1;
        int after_array_x = array_x + 1;
        int before_array_y = array_y - 1;
        int after_array_y = array_y + 1;
        if (before_array_x < 0) {
            before_array_x = 0;
        }
        if (after_array_x > map_size.x - 1) {
            after_array_x = map_size.x - 1;
        }
        if (before_array_y < 0) {
            before_array_y = 0;
        }
        if (after_array_y > map_size.y - 1) {
            after_array_y = map_size.y - 1;
        }
        createMapTileSetForBitmap_floor(
                !isWall(before_array_x, before_array_y), !isWall(array_x, before_array_y), !isWall(after_array_x, before_array_y),
                !isWall(before_array_x, array_y), !isWall(after_array_x, array_y),
                !isWall(before_array_x, after_array_y), !isWall(array_x, after_array_y), !isWall(after_array_x, after_array_y),
                at_floor.big_auto_tile, i, j, map_tile);
    }

    //ミニマップ表示
    public void drawSmallMap3(int x, int y) {
        Paint blue_paint = new Paint();
        Paint red_paint = new Paint();
        Paint green_paint = new Paint();
        Paint l_glay_paint = new Paint();
        Paint yellow_paint = new Paint();
        int small_map_mag = 8;
        Point small_map_offset = new Point(0, 40);
//        int countMiniDrawRect = 0;
        if (map_data[worldToMap(x)][worldToMap(y)].isRoom()) {
            section_admin.getNowRoom(worldToMap(x), worldToMap(y)).setDispflag(true);
        }
        blue_paint.setARGB(100, 0, 0, 255);
        section_admin.drawAllRoom(graphic, blue_paint, small_map_mag, small_map_offset);
        for (int i = 0; i < this.getMap_size_x(); i++) {
            for (int j = 0; j < this.getMap_size_y(); j++) {
                //通路
                if (!isWall(i, j) && !isStairs(i, j) && !isRoom(i, j) && !isGate(i, j) && isDisp(i, j)) {
                    red_paint.setARGB(100, 255, 0, 0);
                    graphic.bookingDrawRect(small_map_mag * i + small_map_offset.x, small_map_mag * j + small_map_offset.y, small_map_mag * (i + 1) + small_map_offset.x, small_map_mag * (j + 1) + small_map_offset.y, red_paint);
//                    countMiniDrawRect++;
                }
                //階段
                else if (isStairs(i, j) && !isGate(i, j) && isDisp(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    graphic.bookingDrawRect(small_map_mag * i + small_map_offset.x, small_map_mag * j + small_map_offset.y, small_map_mag * (i + 1) + small_map_offset.x, small_map_mag * (j + 1) + small_map_offset.y, green_paint);
//                    countMiniDrawRect++;
                }
                //ゲート
                else if(isGate(i, j) && isDisp(i, j)){
                    l_glay_paint.setColor(Color.LTGRAY);
                    graphic.bookingDrawRect(small_map_mag * i + small_map_offset.x, small_map_mag * j + small_map_offset.y, small_map_mag * (i + 1) + small_map_offset.x, small_map_mag * (j + 1) + small_map_offset.y, l_glay_paint);
                }
            }
        }
//        System.out.println("countMiniDrawRect = "+countMiniDrawRect);
        yellow_paint.setColor(Color.YELLOW);
        graphic.bookingDrawCircle(small_map_offset.x + x * small_map_mag / magnification, small_map_offset.y + y * small_map_mag / magnification, 10, yellow_paint);
    }

    //ミニマップの表示を状態を更新
    public void updateMiniMapDispState(int x, int y) {
        if (!map_data[x][y].isWall()) {
            map_data[x][y].setDispFlag(true);
            if (map_data[x][y].isRoom()) {
                if (!map_data[x - 1][y].isDisp()) {
                    updateMiniMapDispState(x - 1, y);
                }
                if (!map_data[x + 1][y].isDisp()) {
                    updateMiniMapDispState(x + 1, y);
                }
                if (!map_data[x][y - 1].isDisp()) {
                    updateMiniMapDispState(x, y - 1);
                }
                if (!map_data[x][y + 1].isDisp()) {
                    updateMiniMapDispState(x, y + 1);
                }
            }
        }
        if (isStairs(x, y)) {
            map_data[x][y].setDispFlag(true);
        }
        if(isGate(x, y)){
            map_data[x][y].setDispFlag(true);
        }
    }

    //ワールドマップ座標からマップ座標に変更
    public int worldToMap(int world_coordinate) {
        return world_coordinate / magnification;
    }

    //holder取得
    public void getHolder() {
        holder = graphic.getHolder();
    }

    //map_player取得
    public void getMapPlayer(MapPlayer m_map_player) {
        map_player = m_map_player;
    }


    //以下チュートリアル用
    //int配列をChip配列に変換
    public void intToChip(int[][] m_map_data) {
        opening_map_data = new Chip[m_map_data.length][m_map_data[0].length];
        for (int i = 0; i < m_map_data.length; i++) {
            for (int j = 0; j < m_map_data[i].length; j++) {
                opening_map_data[i][j] = new Chip();
                if (m_map_data[i][j] == 0) {
                    opening_map_data[i][j].setWallFlag(false);
                } else {
                    opening_map_data[i][j].setWallFlag(true);
                }
            }
        }
    }

    //map_data_intを転置行列に変換
    public void transportMatrix() {
        for (int i = 0; i < map_data_int.length; i++) {
            for (int j = 0; j < map_data_int[0].length; j++) {
                t_map_data_int[j][i] = map_data_int[i][j];
            }
        }
    }

    //チュートリアル用isWallなど
    private boolean isWall_op(int i, int j){
        return opening_map_data[i][j].isWall();
    }

    private boolean isStairs_op(int i, int j){
        return opening_map_data[i][j].isStairs();
    }

    public void createOpeningMap(){
        transportMatrix();
        intToChip(t_map_data_int);
        opening_map_size.set(opening_map_data.length, opening_map_data[0].length);
        op_map_tile = new BitmapData[animation_num][opening_map_size.x][opening_map_size.y];
        for (int i = 0; i < opening_map_size.x; i++) {
            for (int j = 0; j < opening_map_size.y; j++) {
                if (!isWall_op(i, j) && !isStairs_op(i, j)) {
//                    map_tile[i][j] = floor_tile;
                    //アニメーション用
                    for (int k = 0; k < animation_num; k++) {
                        op_map_tile[k][i][j] = floor_tile;
                    }
                    //階段
                } else if (isStairs_op(i, j)) {
//                    map_tile[i][j] = stair_tile;
                    //アニメーション用
                    for (int k = 0; k < animation_num; k++) {
                        op_map_tile[k][i][j] = stair_tile;
                    }
                } else {
                    setAutoTile_light_animation(i, j, i, j, op_map_tile, true);
                }
            }
        }
        //camera.setCameraOffset(500, 500);
    }

    public void drawOpeningMap(){
        time++;
        for (int i = 0; i < opening_map_size.x; i++) {
            for (int j = 0; j < opening_map_size.y; j++) {
                if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification &&
                        camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification &&
                        camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification &&
                        camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification &&
                        op_map_tile[0][i][j] != null) {
                    graphic.bookingDrawBitmapData(op_map_tile[(time / 3) % animation_num][i][j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                }
            }
        }
    }

    public void release() {
        System.out.println("takanoRelease : MapAdmin");
        map_data_int = null;
        t_map_data_int = null;

        map_data = null;
        opening_map_data = null;

        map_size = null;

        offset  = null;
        start_point  = null;

        paint  = null;
        room_point  = null;
        mine_point = null;
        opening_map_size = null;

        if ( camera != null) {
            camera.release();
            camera = null;
        }
        if (section_admin != null) {
            section_admin.release();
            section_admin = null;
        }
        canvas = null;
        holder = null;
        map_player = null;
        map_object_admin = null;
        dungeon_data = null;
        dungeon_monster_data = null;

        //auto_tile用
        if (auto_tile_wall != null) {
            auto_tile_wall.release();
            auto_tile_wall = null;
        }
        if (auto_tile_side_wall != null) {
            auto_tile_side_wall.release();
            auto_tile_side_wall = null;
        }
        if (at_wall != null) {
            for (int i = 0; i < at_wall.length; i++) {
                if (at_wall[i] != null) {
                    at_wall[i].release();
                }
            }
            at_wall = null;
        }
        if (at_side_wall != null) {
            for (int i = 0; i < at_side_wall.length; i++) {
                if (at_side_wall[i] != null) {
                    at_side_wall[i].release();
                }
            }
            at_side_wall = null;
        }
        if (at_floor != null) {
            at_floor.release();
            at_floor = null;
        }
        if (auto_tile_cave_hole != null) {
            for (int i = 0; i < auto_tile_cave_hole.length; i++) {
                if (auto_tile_cave_hole[i] != null) {
                    auto_tile_cave_hole[i].release();
                }
            }
            auto_tile_cave_hole = null;
        }
        if (auto_tile_admin != null) {
            auto_tile_admin.release();
            auto_tile_admin = null;
        }

        is_map_data_wall = null;
        is_map_data_sidewall = null;

        map_tile_set = null;
        map_tile_set_animation = null;
        map_tile = null;
        map_tile_animation = null;
        side_wall_4 = null;
        op_map_tile = null;

        floor_tile = null;
        stair_tile = null;
        gate_tile = null;
        stair_tile_div = null;
    }

    //過去関数
/*
private void createDispMapData() {
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                if (map_data[i][j].isWall()) {
                    is_map_data_wall[2 * i][2 * j] = true;
                    is_map_data_wall[2 * i + 1][2 * j] = true;
                    is_map_data_wall[2 * i][2 * j + 1] = true;
                    is_map_data_wall[2 * i + 1][2 * j + 1] = true;
                } else {
                    is_map_data_wall[2 * i][2 * j] = false;
                    is_map_data_wall[2 * i + 1][2 * j] = false;
                    is_map_data_wall[2 * i][2 * j + 1] = false;
                    is_map_data_wall[2 * i + 1][2 * j + 1] = false;
                }
            }
        }
        for (int i = 0; i < map_size.x * 2; i++) {
            for (int j = 0; j < map_size.y * 2 - 1; j++) {
                is_map_data_sidewall[i][j] = is_map_data_wall[i][j] && !is_map_data_wall[i][j + 1];
            }
        }
    }
    private void createAutoTileImg_old() {
        //画像読込
        BitmapData auto_tile_block_wall = graphic.searchBitmap("cave_wall_w_01");//壁のauto_tile元データ
        BitmapData auto_tile_block_side_wall = graphic.searchBitmap("cave_wall_f_01");//側壁のauto_tileの元データ
        BitmapData raw_floor_tile = graphic.searchBitmap("cave_floor_01");
        floor_tile = auto_tile_admin.combineFourAutoTile(raw_floor_tile, raw_floor_tile, raw_floor_tile, raw_floor_tile);

        //BitmapData cave_hole_raw = graphic.searchBitmap("cave_hole_01");//穴（アニメーション）
        //BitmapData dragon_hole_raw = graphic.searchBitmap("Dragon_hole_f_01");//穴（アニメーション）

        //階段画像4分割
        stair_tile = graphic.searchBitmap("step");
        stair_tile_div[0] = graphic.processTrimmingBitmapData(stair_tile, 0, 0, 32, 32);
        stair_tile_div[1] = graphic.processTrimmingBitmapData(stair_tile, 32, 0, 32, 32);
        stair_tile_div[2] = graphic.processTrimmingBitmapData(stair_tile, 0, 32, 32, 32);
        stair_tile_div[3] = graphic.processTrimmingBitmapData(stair_tile, 32, 32, 32, 32);

        //採掘場画像4分割
//        mine_tile = graphic.searchBitmap("cave_thing_01");
//        mine_tile4 = auto_tile_admin.combineFourAutoTile(mine_tile, mine_tile, mine_tile, mine_tile);
//        mine_tile_div[0] = graphic.processTrimmingBitmapData(mine_tile, 0, 0, 32, 32);
//        mine_tile_div[1] = graphic.processTrimmingBitmapData(mine_tile, 32, 0, 32, 32);
//        mine_tile_div[2] = graphic.processTrimmingBitmapData(mine_tile, 0, 32, 32, 32);
//        mine_tile_div[3] = graphic.processTrimmingBitmapData(mine_tile, 32, 32, 32, 32);

        //アニメーション用、横3分割
//        BitmapData cave_hole_div[] = new BitmapData[3];
//        cave_hole_div[0] = graphic.processTrimmingBitmapData(cave_hole_raw, 0, 0, 32, 32*5);
//        cave_hole_div[1] = graphic.processTrimmingBitmapData(cave_hole_raw, 32, 0, 32, 32*5);
//        cave_hole_div[2] = graphic.processTrimmingBitmapData(cave_hole_raw, 64, 0, 32, 32*5);

        for (int j = 0; j < 3; j++) {
            auto_tile_cave_hole[j] = new AutoTile();
        }

        //autotileを元画像から5つに分割
        for (int i = 0; i < 5; i++) {
            auto_tile_wall.setAuto_tile(graphic.processTrimmingBitmapData(auto_tile_block_wall, 0, 32 * i, 32, 32), i);
            auto_tile_side_wall.setAuto_tile(graphic.processTrimmingBitmapData(auto_tile_block_side_wall, 0, 32 * i, 32, 32), i);
            for (int j = 0; j < 3; j++) {
                auto_tile_cave_hole[j].setAuto_tile(graphic.processTrimmingBitmapData(cave_hole_div[j], 0, 32 * i, 32, 32), i);
            }
        }
        auto_tile_admin.createAutoTile(auto_tile_wall);
        auto_tile_admin.createAutoTile(auto_tile_side_wall);
        //横壁4種類作成
        auto_tile_admin.createAutoTileForSideWall(auto_tile_side_wall, side_wall);
        auto_tile_admin.createBigAutoTile(auto_tile_wall, side_wall);

        for (int i = 0; i < 3; i++) {
            auto_tile_admin.createAutoTile(auto_tile_cave_hole[i]);
        }

        //マップ生成
        if (is_debug_mode) {
            transportMatrix();//デバッグ用
            intToChip(t_map_data_int);//デバッグ用
        } else {
            goNextFloor();
            //createMap();//自動生成
            //createDispMapData();
        }
        //intToChip(map_data_int);//デバッグ用x, yが反転する

        //配列に画像を格納
//        for(int i = 0;i < map_size.x;i++) {
//            for (int j = 0; j < map_size.y; j++) {
//                //map_tile_set[2*i][2*j] = auto_tile_wall.auto_tile[46];
//                //map_tile_set[2*i+1][2*j] = auto_tile_wall.auto_tile[46];
//                //map_tile_set[2*i][2*j+1] = auto_tile_wall.auto_tile[46];
//                //map_tile_set[2*i+1][2*j+1] = auto_tile_wall.auto_tile[46];
//                if (!isWall(i, j) && !isStairs(i, j)) {
//                    map_tile_set[2*i][2*j] = floor_tile;
//                    map_tile_set[2*i+1][2*j] = floor_tile;
//                    map_tile_set[2*i][2*j+1] = floor_tile;
//                    map_tile_set[2*i+1][2*j+1] = floor_tile;
//                    //階段
//                } else if (isStairs(i, j)) {
//                    map_tile_set[2*i][2*j] = stair_tile_div[0];
//                    map_tile_set[2*i+1][2*j] = stair_tile_div[1];
//                    map_tile_set[2*i][2*j+1] = stair_tile_div[2];
//                    map_tile_set[2*i+1][2*j+1] = stair_tile_div[3];
//                } else {
//                    setAutoTile(2 * i, 2 * j, 2*i, 2*j);
//                    setAutoTile(2 * i + 1, 2 * j, 2*i+1, 2*j);
//                    setAutoTile(2 * i, 2 * j + 1, 2*i, 2*j+1);
//                    setAutoTile(2 * i + 1, 2 * j + 1, 2*i+1, 2*j+1);
//                }
//            }
//        }
//        //4つを1つに
//        for(int i = 0;i < map_size.x;i++){
//            for(int j = 0;j < map_size.y;j++){
//                map_tile[i][j] = auto_tile_admin.combineFourAutoTile(map_tile_set[2*i][2*j], map_tile_set[2*i+1][2*j], map_tile_set[2*i][2*j+1], map_tile_set[2*i+1][2*j+1]);
//            }
//        }
//        //1つの画像に
//        BitmapData wide_map_image[] = new BitmapData[map_size.x];//横に繋げた画像
//        for(int i = 0;i < map_size.x;i++){
//            wide_map_image[i] = graphic.processCombineBitmapData(map_tile[i], false);
//        }
//        map_image = graphic.processCombineBitmapData(wide_map_image, true);
    }

    //Canvas使用バージョン
    public void drawMapForCanvas() {
        canvas = holder.lockCanvas();
        Paint l_gray_paint = new Paint();
        Paint d_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Paint paint = new Paint();
        //右手法で動く点の表示
        if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()) {
            offset.x = offset.x + 10;
            offset.y = offset.y + 10;
            //System.out.println("wall");
        } else {
            boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y - 10)].isWall();
            boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y + 10)].isWall();
            boolean left = !map_data[worldToMap(offset.x - 10)][worldToMap(offset.y)].isWall();
            boolean right = !map_data[worldToMap(offset.x + 10)][worldToMap(offset.y)].isWall();
            if (up & down & left & right) {
                boolean ul = !map_data[worldToMap(offset.x - 10)][worldToMap(offset.y - 10)].isWall();
                boolean ur = !map_data[worldToMap(offset.x + 10)][worldToMap(offset.y - 10)].isWall();
                boolean dl = !map_data[worldToMap(offset.x - 10)][worldToMap(offset.y + 10)].isWall();
                boolean dr = !map_data[worldToMap(offset.x + 10)][worldToMap(offset.y + 10)].isWall();
                if (!ul & ur & dl & dr) {
                    offset.x = offset.x - 10;
                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                } else if (ul & !ur & dl & dr) {
                    offset.y = offset.y - 10;
                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                } else if (ul & ur & !dl & dr) {
                    offset.y = offset.y + 10;
                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                } else {
                    offset.x = offset.x + 10;
                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                }
            } else if (!up & down & left & right || !up & down & left & !right) {
                offset.x = offset.x - 10;
                updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                //System.out.println("1");
            } else if (up & down & right || down & right) {
                offset.y = offset.y + 10;
                updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                //System.out.println("2");
            } else if (up & left & right || up & right) {
                offset.x = offset.x + 10;
                updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                //System.out.println("3");
            } else {
                offset.y = offset.y - 10;
                updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                //System.out.println("4");
            }
        }
        camera.setCameraOffset(offset.x, offset.y);
        //map_offset.set(camera.getCameraOffset().x, camera.getCameraOffset().y);
        for (int i = 0; i < getMap_size_x(); i++) {
            for (int j = 0; j < this.getMap_size_y(); j++) {
                if (!isWall(i, j) && !isStairs(i, j) && !isEntrance(i, j)) {
                    l_gray_paint.setColor(Color.LTGRAY);//部屋は青
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        canvas.drawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), l_gray_paint);
                        //System.out.println(camera.convertToNormCoordinateXForMap(i * magnification) + "," + camera.convertToNormCoordinateYForMap(j * magnification) + "," + camera.convertToNormCoordinateXForMap((i + 1) * magnification) + "," + camera.convertToNormCoordinateYForMap((j + 1) * magnification));
                    }
                } else if (isStairs(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        canvas.drawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), green_paint);
                        //System.out.println(camera.convertToNormCoordinateXForMap(i * magnification) + "," + camera.convertToNormCoordinateYForMap(j * magnification) + "," + camera.convertToNormCoordinateXForMap((i + 1) * magnification) + "," + camera.convertToNormCoordinateYForMap((j + 1) * magnification));
                    }
                } else if (isEntrance(i, j)) {
                    paint.setColor(Color.YELLOW);
                    canvas.drawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), paint);
                } else {
                    d_gray_paint.setColor(Color.BLACK);//壁は
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        canvas.drawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), d_gray_paint);
                        //System.out.println(camera.convertToNormCoordinateXForMap(i * magnification) + "," + camera.convertToNormCoordinateYForMap(j * magnification) + "," + camera.convertToNormCoordinateXForMap((i + 1) * magnification) + "," + camera.convertToNormCoordinateYForMap((j + 1) * magnification));
                    }
                }
            }
        }
        paint.setColor(Color.RED);
        //canvas.drawLine(80, 0, 80, 1920, paint);
        //canvas.drawLine(0, 80, 1080, 80, paint);

//        canvas.drawLine(900, 0, 900, 1920, paint);
//        canvas.drawLine(0, 1600, 1080, 1600, paint);
//        canvas.drawLine(980, 0, 980, 1920, paint);
//        canvas.drawLine(0, 1680, 1080, 1680, paint);

        canvas.drawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMapForCanvas();
        holder.unlockCanvasAndPost(canvas);
    }

    public void drawMap() {
        //magnification = 5;
        Paint blue_paint = new Paint();
        Paint red_paint = new Paint();
        Paint green_paint = new Paint();
        //camera.setCameraOffset(400, 600);
        //map_offset.set(camera.getCameraOffset().x, camera.getCameraOffset().y);
        for (int i = 0; i < this.getMap_size_x(); i++) {
            for (int j = 0; j < this.getMap_size_y(); j++) {
                if (!this.isWall(i, j) && !this.isStairs(i, j)) {
                    blue_paint.setColor(Color.BLUE);
                    graphic.bookingDrawRect(magnification * i - map_offset.x, magnification * j - map_offset.y, magnification * (i + 1) - map_offset.x, magnification * (j + 1) - map_offset.y, blue_paint);
                } else if (this.isStairs(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    graphic.bookingDrawRect(magnification * i - map_offset.x, magnification * j - map_offset.y, magnification * (i + 1) - map_offset.x, magnification * (j + 1) - map_offset.y, green_paint);
                } else {
                    red_paint.setColor(Color.RED);
                    graphic.bookingDrawRect(magnification * i - map_offset.x, magnification * j - map_offset.y, magnification * (i + 1) - map_offset.x, magnification * (j + 1) - map_offset.y, red_paint);
                }
            }
        }
        //paint.setColor(Color.YELLOW);
        Paint test_paint = new Paint();
        test_paint.setColor(Color.YELLOW);
        graphic.bookingDrawCircle(800, 450, 10, test_paint);
        drawSmallMap();
    }

    public void drawMap2() {
        //右手法で動く点の表示
        if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()) {
            offset.x = offset.x + 10;
            //System.out.println("wall");
        } else {
            boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y - 10)].isWall();
            boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y + 10)].isWall();
            boolean left = !map_data[worldToMap(offset.x - 10)][worldToMap(offset.y)].isWall();
            boolean right = !map_data[worldToMap(offset.x + 10)][worldToMap(offset.y)].isWall();
            if (up & down & left & right) {
                boolean ul = !map_data[worldToMap(offset.x - 10)][worldToMap(offset.y - 10)].isWall();
                boolean ur = !map_data[worldToMap(offset.x + 10)][worldToMap(offset.y - 10)].isWall();
                boolean dl = !map_data[worldToMap(offset.x - 10)][worldToMap(offset.y + 10)].isWall();
                boolean dr = !map_data[worldToMap(offset.x + 10)][worldToMap(offset.y + 10)].isWall();
                if (!ul & ur & dl & dr) {
                    offset.x = offset.x - 10;
                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                } else if (ul & !ur & dl & dr) {
                    offset.y = offset.y - 10;
                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                } else if (ul & ur & !dl & dr) {
                    offset.y = offset.y + 10;
                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                } else {
                    offset.x = offset.x + 10;
                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                }
            } else if (!up & down & left & right || !up & down & left & !right) {
                offset.x = offset.x - 10;
                updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                //System.out.println("1");
            } else if (up & down & right || down & right) {
                offset.y = offset.y + 10;
                updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                //System.out.println("2");
            } else if (up & left & right || up & right) {
                offset.x = offset.x + 10;
                updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                //System.out.println("3");
            } else {
                offset.y = offset.y - 10;
                updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                //System.out.println("4");
            }
        }
        camera.setCameraOffset(offset.x, offset.y);
        //map_offset.set(camera.getCameraOffset().x, camera.getCameraOffset().y);
        for (int i = 0; i < this.getMap_size_x(); i++) {
            for (int j = 0; j < this.getMap_size_y(); j++) {
                if (!this.isWall(i, j) && !this.isStairs(i, j)) {
                    paint.setColor(Color.LTGRAY);//部屋は青
                } else if (this.isStairs(i, j) == true) {
                    paint.setColor(Color.GREEN);
                } else {
                    paint.setColor(Color.BLACK);//壁は
                }
                if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                    graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), paint);
                    //System.out.println(camera.convertToNormCoordinateXForMap(i * magnification) + "," + camera.convertToNormCoordinateYForMap(j * magnification) + "," + camera.convertToNormCoordinateXForMap((i + 1) * magnification) + "," + camera.convertToNormCoordinateYForMap((j + 1) * magnification));
                }
            }
        }
        paint.setColor(Color.RED);
        //canvas.drawLine(80, 0, 80, 1920, paint);
        //canvas.drawLine(0, 80, 1080, 80, paint);

//        canvas.drawLine(900, 0, 900, 1920, paint);
//        canvas.drawLine(0, 1600, 1080, 1600, paint);
//        canvas.drawLine(980, 0, 980, 1920, paint);
//        canvas.drawLine(0, 1680, 1080, 1680, paint);

        graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMap();
    }

    public void drawMap3() {
        Paint l_gray_paint = new Paint();
        Paint d_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Paint yellow_paint = new Paint();
        Room now_point_room = new Room();
        int step = 30;
        boolean go_stair_flag = false;
        if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()) {
            goNextFloor();
        }
        offset.set(500, 300);
        //右手法で動く点の表示
//        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()){
//            offset.x = offset.x+step;
//            offset.y = offset.y+step;
//            //System.out.println("wall");
//        }
//        //階段へ向かう
//        else {
//            if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isRoom()) {
//                now_point_room = section_admin.getNowRoom(worldToMap(offset.x), worldToMap(offset.y));
//                if(now_point_room == null){
//                    System.out.println("%☆roomがない");
//                }
//                if(now_point_room != null) {
//                    for (int i = now_point_room.getLeft(); i <= now_point_room.getRight(); i++) {
//                        for (int j = now_point_room.getTop(); j <= now_point_room.getBottom(); j++) {
//                            if (map_data[i][j].isStairs()) {
//                                go_stair_flag = true;
//                                int dst_x = i * magnification + magnification / 2;
//                                int dst_y = j * magnification + magnification / 2;
//                                if (abs(dst_x - offset.x) <= step) {
//                                    offset.x = dst_x;
//                                } else if (abs(dst_y - offset.y) <= step) {
//                                    offset.y = dst_y;
//                                }
//                                if (dst_x != offset.x && dst_x > offset.x) {
//                                    offset.x = offset.x + step;
//                                    break;
//                                } else if (dst_x != offset.x && dst_x < offset.x) {
//                                    offset.x = offset.x - step;
//                                    break;
//                                } else if (dst_y != offset.y && dst_y > offset.y) {
//                                    offset.y = offset.y + step;
//                                    break;
//                                } else {
//                                    offset.y = offset.y - step;
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (!go_stair_flag) {
//                boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y - step)].isWall();
//                boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y + step)].isWall();
//                boolean left = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y)].isWall();
//                boolean right = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y)].isWall();
//                if (up & down & left & right) {
//                    boolean ul = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y - step)].isWall();
//                    boolean ur = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y - step)].isWall();
//                    boolean dl = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y + step)].isWall();
//                    boolean dr = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y + step)].isWall();
//                    if (!ul & ur & dl & dr) {
//                        offset.x = offset.x - step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    } else if (ul & !ur & dl & dr) {
//                        offset.y = offset.y - step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    } else if (ul & ur & !dl & dr) {
//                        offset.y = offset.y + step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    } else {
//                        offset.x = offset.x + step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    }
//                } else if (!up & down & left & right || !up & down & left & !right) {
//                    offset.x = offset.x - step;
//                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    //System.out.println("1");
//                } else if (up & down & right || down & right) {
//                    offset.y = offset.y + step;
//                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    //System.out.println("2");
//                } else if (up & left & right || up & right) {
//                    offset.x = offset.x + step;
//                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    //System.out.println("3");
//                } else {
//                    offset.y = offset.y - step;
//                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    //System.out.println("4");
//                }
//            }
//        }
        camera.setCameraOffset(offset.x, offset.y);
//        int countDrawRect = 0;
//        map_offset.set(camera.getCameraOffset().x, camera.getCameraOffset().y);
        for (int i = 0; i < this.getMap_size_x(); i++) {
            for (int j = 0; j < this.getMap_size_y(); j++) {
                if (!isWall(i, j) && !isStairs(i, j) && !isEntrance(i, j)) {
                    l_gray_paint.setColor(Color.LTGRAY);//部屋
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), l_gray_paint);
                        //System.out.println(camera.convertToNormCoordinateXForMap(i * magnification) + "," + camera.convertToNormCoordinateYForMap(j * magnification) + "," + camera.convertToNormCoordinateXForMap((i + 1) * magnification) + "," + camera.convertToNormCoordinateYForMap((j + 1) * magnification));
//                        countDrawRect++;
                    }
                } else if (isStairs(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), green_paint);
                        //System.out.println(camera.convertToNormCoordinateXForMap(i * magnification) + "," + camera.convertToNormCoordinateYForMap(j * magnification) + "," + camera.convertToNormCoordinateXForMap((i + 1) * magnification) + "," + camera.convertToNormCoordinateYForMap((j + 1) * magnification));
                        //countDrawRect++;
                    }
                } else if (isEntrance(i, j)) {
                    yellow_paint.setColor(Color.YELLOW);
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), yellow_paint);
                        //countDrawRect++;
                    }
                } else {
                    d_gray_paint.setColor(Color.BLACK);//壁は
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), d_gray_paint);
                        //System.out.println(camera.convertToNormCoordinateXForMap(i * magnification) + "," + camera.convertToNormCoordinateYForMap(j * magnification) + "," + camera.convertToNormCoordinateXForMap((i + 1) * magnification) + "," + camera.convertToNormCoordinateYForMap((j + 1) * magnification));
                        //countDrawRect++;
                    }
                }
            }
        }
//        System.out.println("countDrawRect = "+countDrawRect);
        paint.setColor(Color.RED);
        //canvas.drawLine(80, 0, 80, 1920, paint);
        //canvas.drawLine(0, 80, 1080, 80, paint);

        canvas.drawLine(900, 0, 900, 1920, paint);
        canvas.drawLine(0, 1600, 1080, 1600, paint);
        canvas.drawLine(980, 0, 980, 1920, paint);
        canvas.drawLine(0, 1680, 1080, 1680, paint);

        graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMap2(offset.x, offset.y);

        //bitmapdataテスト
//        Point position = new Point(0,0);
//        BitmapData test_bitmap = graphic.searchBitmap("cave_wall_w_01");
//        graphic.bookingDrawBitmapData(test_bitmap, position.x, position.y, 10, 10, 0, 255, true);
//        auto_tile_admin.printAutoTileTest(auto_tile);
    }

    public void drawMap_for_autotile() {
        Paint l_gray_paint = new Paint();
        Paint d_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Paint yellow_paint = new Paint();
        Room now_point_room = new Room();
        int step = 64;
        boolean go_stair_flag = false;
        if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()) {
            goNextFloor();
        }
        offset.set(1500, 1000);

        camera.setCameraOffset(offset.x, offset.y);
        //map_offset.set(camera.getCameraOffset().x, camera.getCameraOffset().y);
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                //床
                if (!isWall(i, j) && !isStairs(i, j)) {
                    l_gray_paint.setColor(Color.LTGRAY);
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), l_gray_paint);
                        //graphic.bookingDrawBitmapData(floor_tile,camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), 1, 1, 0, 255, true);
                    }
                    //階段
                } else if (isStairs(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), green_paint);
                    }
                } else {
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        drawWall(i, j, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), magnification / 32);
                    }
                }
            }
        }
//        System.out.println("countDrawRect = "+countDrawRect);
        paint.setColor(Color.RED);
        //canvas.drawLine(80, 0, 80, 1920, paint);
        //canvas.drawLine(0, 80, 1080, 80, paint);

        canvas.drawLine(900, 0, 900, 1920, paint);
        canvas.drawLine(0, 1600, 1080, 1600, paint);
        canvas.drawLine(980, 0, 980, 1920, paint);
        canvas.drawLine(0, 1680, 1080, 1680, paint);

        graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMap2(offset.x, offset.y);

    }

    public void drawMap_for_autotile_4div_combine_canvas() {
        //canvas = holder.lockCanvas();
        Paint l_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Room now_point_room = new Room();
        int step = 10;
        boolean go_stair_flag = false;
        if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()) {
            goNextFloor();
        }
        offset.set(800, 300);

        //右手法で動く点の表示
//        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()){
//            offset.x = offset.x+step;
//            offset.y = offset.y+step;
//            //System.out.println("wall");
//        }
//        //階段へ向かう
//        else {
//            if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isRoom()) {
//                now_point_room = section_admin.getNowRoom(worldToMap(offset.x), worldToMap(offset.y));
//                if(now_point_room == null){
//                    System.out.println("%☆roomがない");
//                }
//                if(now_point_room != null) {
//                    for (int i = now_point_room.getLeft(); i <= now_point_room.getRight(); i++) {
//                        for (int j = now_point_room.getTop(); j <= now_point_room.getBottom(); j++) {
//                            if (map_data[i][j].isStairs()) {
//                                go_stair_flag = true;
//                                int dst_x = i * magnification + magnification / 2;
//                                int dst_y = j * magnification + magnification / 2;
//                                if (abs(dst_x - offset.x) <= step) {
//                                    offset.x = dst_x;
//                                } else if (abs(dst_y - offset.y) <= step) {
//                                    offset.y = dst_y;
//                                }
//                                if (dst_x != offset.x && dst_x > offset.x) {
//                                    offset.x = offset.x + step;
//                                    break;
//                                } else if (dst_x != offset.x && dst_x < offset.x) {
//                                    offset.x = offset.x - step;
//                                    break;
//                                } else if (dst_y != offset.y && dst_y > offset.y) {
//                                    offset.y = offset.y + step;
//                                    break;
//                                } else {
//                                    offset.y = offset.y - step;
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            if (!go_stair_flag) {
//                boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y - step)].isWall();
//                boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y + step)].isWall();
//                boolean left = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y)].isWall();
//                boolean right = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y)].isWall();
//                if (up & down & left & right) {
//                    boolean ul = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y - step)].isWall();
//                    boolean ur = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y - step)].isWall();
//                    boolean dl = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y + step)].isWall();
//                    boolean dr = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y + step)].isWall();
//                    if (!ul & ur & dl & dr) {
//                        offset.x = offset.x - step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    } else if (ul & !ur & dl & dr) {
//                        offset.y = offset.y - step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    } else if (ul & ur & !dl & dr) {
//                        offset.y = offset.y + step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    } else {
//                        offset.x = offset.x + step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    }
//                } else if (!up & down & left & right || !up & down & left & !right) {
//                    offset.x = offset.x - step;
//                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    //System.out.println("1");
//                } else if (up & down & right || down & right) {
//                    offset.y = offset.y + step;
//                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    //System.out.println("2");
//                } else if (up & left & right || up & right) {
//                    offset.x = offset.x + step;
//                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    //System.out.println("3");
//                } else {
//                    offset.y = offset.y - step;
//                    updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                    //System.out.println("4");
//                }
//            }
//        }
        //camera.setCameraOffset(offset.x, offset.y);
//        map_offset.set(camera.getCameraOffset().x, camera.getCameraOffset().y);

        //4分割のままで表示
//        for (int i = 0; i < map_size.x; i++) {
//            for (int j = 0; j < map_size.y; j++) {
//                if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                    graphic.bookingDrawBitmapData(map_tile_set[2*i][2*j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float)magnification / 64, (float)magnification / 64, 0, 255, true);
//                    graphic.bookingDrawBitmapData(map_tile_set[2*i+1][2*j], camera.convertToNormCoordinateXForMap(i * magnification+magnification/2), camera.convertToNormCoordinateYForMap(j * magnification), (float)magnification / 64, (float)magnification / 64, 0, 255, true);
//                    graphic.bookingDrawBitmapData(map_tile_set[2*i][2*j+1], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification+magnification/2), (float)magnification / 64, (float)magnification / 64, 0, 255, true);
//                    graphic.bookingDrawBitmapData(map_tile_set[2*i+1][2*j+1], camera.convertToNormCoordinateXForMap(i * magnification+magnification/2), camera.convertToNormCoordinateYForMap(j * magnification+magnification/2), (float)magnification / 64, (float)magnification / 64, 0, 255, true);
//                }
//            }
//        }

        //4つを1つに纏めて表示
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                    graphic.bookingDrawBitmapData(map_tile[i][j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                    //canvas.drawBitmap(map_tile[i][j].getBitmap(), camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), paint);
                    //posRect.set(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification),(int)(camera.convertToNormCoordinateXForMap(i * magnification) + map_tile[i][j].getBitmap().getWidth()*1.2),(int)(camera.convertToNormCoordinateYForMap(j * magnification) + map_tile[i][j].getBitmap().getHeight()*1.2));
                    //canvas.drawBitmap(map_tile[i][j].getBitmap(),sizeRect, posRect,null);
                }
            }
        }

        //1つの画像で表示
//        graphic.bookingDrawBitmapData(map_image, -1*camera.camera_offset.x, -1*camera.camera_offset.y, 1, 1, 0, 255, true);

//        canvas.drawBitmap(map_image.getBitmap(), -1*camera.camera_offset.x, -1*camera.camera_offset.y, paint);


        paint.setColor(Color.RED);
        graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMap3(offset.x, offset.y);
        //holder.unlockCanvasAndPost(canvas);
    }

    //4分割して表示
    public void drawMap_for_autotile_4div() {
        Paint l_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Room now_point_room = new Room();
        int step = 30;
        boolean go_stair_flag = false;
        if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()) {
            goNextFloor();
        }
        //offset.set(300, 500);

//        //右手法で動く点の表示
//        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()){
//            offset.x = offset.x+10;
//            //System.out.println("wall");
//        }
//        else{
//            boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y-10)].isWall();
//            boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y+10)].isWall();
//            boolean left = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y)].isWall();
//            boolean right = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y)].isWall();
//            if(up&down&left&right){
//                boolean ul = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y-10)].isWall();
//                boolean ur = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y-10)].isWall();
//                boolean dl = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y+10)].isWall();
//                boolean dr = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y+10)].isWall();
//                if(!ul&ur&dl&dr){
//                    offset.x = offset.x-10;
//                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
//                }
//                else if(ul&!ur&dl&dr){
//                    offset.y = offset.y-10;
//                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
//                }
//                else if(ul&ur&!dl&dr){
//                    offset.y = offset.y+10;
//                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
//                }
//                else{
//                    offset.x = offset.x+10;
//                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
//                }
//            }
//            else if(!up&down&left&right|| !up&down&left&!right){
//                offset.x = offset.x-10;
//                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
//                //System.out.println("1");
//            }
//            else if(up&down&right || down&right){
//                offset.y = offset.y+10;
//                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
//                //System.out.println("2");
//            }
//            else if(up&left&right || up&right){
//                offset.x = offset.x+10;
//                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
//                //System.out.println("3");
//            }
//            else{
//                offset.y = offset.y-10;
//                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
//                //System.out.println("4");
//            }
//        }

        camera.setCameraOffset(offset.x, offset.y);
        //map_offset.set(camera.getCameraOffset().x, camera.getCameraOffset().y);
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                //床
                if (!isWall(i, j) && !isStairs(i, j)) {
                    l_gray_paint.setColor(Color.LTGRAY);
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        //graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), l_gray_paint);
                        //graphic.bookingDrawBitmapData(floor_tile,camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), 1, 1, 0, 255, true);
                        graphic.bookingDrawBitmapData(floor_tile, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                        graphic.bookingDrawBitmapData(floor_tile, camera.convertToNormCoordinateXForMap(i * magnification) + magnification / 2, camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                        graphic.bookingDrawBitmapData(floor_tile, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification) + magnification / 2, (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                        graphic.bookingDrawBitmapData(floor_tile, camera.convertToNormCoordinateXForMap(i * magnification) + magnification / 2, camera.convertToNormCoordinateYForMap(j * magnification) + magnification / 2, (float) magnification / 64, (float) magnification / 64, 0, 255, true);
                    }
                    //階段
                } else if (isStairs(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), green_paint);
                    }
                } else {
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        drawWall2(i * 2, j * 2, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64);
                        drawWall2(i * 2 + 1, j * 2, camera.convertToNormCoordinateXForMap(i * magnification) + magnification / 2, camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64);
                        drawWall2(i * 2, j * 2 + 1, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification) + magnification / 2, (float) magnification / 64);
                        drawWall2(i * 2 + 1, j * 2 + 1, camera.convertToNormCoordinateXForMap(i * magnification) + magnification / 2, camera.convertToNormCoordinateYForMap(j * magnification) + magnification / 2, (float) magnification / 64);
                    }
                }
            }
        }
        paint.setColor(Color.RED);
        graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMap2(offset.x, offset.y);
    }

    //4分割した物をくっつけて保存して表示
    public void drawMap_for_autotile_4div_combine() {
        Paint l_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Room now_point_room = new Room();
        boolean is_debug_mode = false;
        int step = 10;
        boolean go_stair_flag = false;
        int mx = worldToMap(camera.getCameraOffset().x + 800);
        int my = worldToMap(camera.getCameraOffset().y + 450);
        if (map_data[mx][my].isStairs()) {
            goNextFloor();
        }

//        //右手法で動く点の表示
//        if(is_debug_mode) {
//            if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()) {
//                offset.x = offset.x + step;
//                offset.y = offset.y + step;
//                //System.out.println("wall");
//            }
//            //階段へ向かう
//            else {
//                if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isRoom()) {
//                    now_point_room = section_admin.getNowRoom(worldToMap(offset.x), worldToMap(offset.y));
//                    if (now_point_room == null) {
//                        System.out.println("%☆roomがない");
//                    }
//                    if (now_point_room != null) {
//                        for (int i = now_point_room.getLeft(); i <= now_point_room.getRight(); i++) {
//                            for (int j = now_point_room.getTop(); j <= now_point_room.getBottom(); j++) {
//                                if (map_data[i][j].isStairs()) {
//                                    go_stair_flag = true;
//                                    int dst_x = i * magnification + magnification / 2;
//                                    int dst_y = j * magnification + magnification / 2;
//                                    if (abs(dst_x - offset.x) <= step) {
//                                        offset.x = dst_x;
//                                    } else if (abs(dst_y - offset.y) <= step) {
//                                        offset.y = dst_y;
//                                    }
//                                    if (dst_x != offset.x && dst_x > offset.x) {
//                                        offset.x = offset.x + step;
//                                        break;
//                                    } else if (dst_x != offset.x && dst_x < offset.x) {
//                                        offset.x = offset.x - step;
//                                        break;
//                                    } else if (dst_y != offset.y && dst_y > offset.y) {
//                                        offset.y = offset.y + step;
//                                        break;
//                                    } else {
//                                        offset.y = offset.y - step;
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                if (!go_stair_flag) {
//                    boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y - step)].isWall();
//                    boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y + step)].isWall();
//                    boolean left = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y)].isWall();
//                    boolean right = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y)].isWall();
//                    if (up & down & left & right) {
//                        boolean ul = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y - step)].isWall();
//                        boolean ur = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y - step)].isWall();
//                        boolean dl = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y + step)].isWall();
//                        boolean dr = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y + step)].isWall();
//                        if (!ul & ur & dl & dr) {
//                            offset.x = offset.x - step;
//                            updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                        } else if (ul & !ur & dl & dr) {
//                            offset.y = offset.y - step;
//                            updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                        } else if (ul & ur & !dl & dr) {
//                            offset.y = offset.y + step;
//                            updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                        } else {
//                            offset.x = offset.x + step;
//                            updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                        }
//                    } else if (!up & down & left & right || !up & down & left & !right) {
//                        offset.x = offset.x - step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                        //System.out.println("1");
//                    } else if (up & down & right || down & right) {
//                        offset.y = offset.y + step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                        //System.out.println("2");
//                    } else if (up & left & right || up & right) {
//                        offset.x = offset.x + step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                        //System.out.println("3");
//                    } else {
//                        offset.y = offset.y - step;
//                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
//                        //System.out.println("4");
//                    }
//                }
//            }
//            camera.setCameraOffset(offset.x, offset.y);
//            map_offset.set(getOffset_x()+800, getOffset_y()+450);
//        }
//        else {
////            offset.set(800, 1300);
////            camera.setCameraOffset(offset.x, offset.y);
//        }
//
//        //周りを黒くする
////        graphic.bookingDrawBitmapData(auto_tile_wall.raw_auto_tile[4], 0, 0, 1600/32, 900/32, 0, 255, true);
//
//        int draw_mode = 2;
//        //4分割のままで表示
//        if(draw_mode == 1) {
//            for (int i = 0; i < map_size.x; i++) {
//                for (int j = 0; j < map_size.y; j++) {
//                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                        graphic.bookingDrawBitmapData(map_tile_set[2 * i][2 * j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                        graphic.bookingDrawBitmapData(map_tile_set[2 * i + 1][2 * j], camera.convertToNormCoordinateXForMap(i * magnification + magnification / 2), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                        graphic.bookingDrawBitmapData(map_tile_set[2 * i][2 * j + 1], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification + magnification / 2), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                        graphic.bookingDrawBitmapData(map_tile_set[2 * i + 1][2 * j + 1], camera.convertToNormCoordinateXForMap(i * magnification + magnification / 2), camera.convertToNormCoordinateYForMap(j * magnification + magnification / 2), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                    }
//                }
//            }
//        }
//
//        //4つを1つに纏めて表示
//        else if(draw_mode == 2) {
//            for (int i = 0; i < map_size.x; i++) {
//                for (int j = 0; j < map_size.y; j++) {
//                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                        graphic.bookingDrawBitmapData(map_tile[i][j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float) magnification / 64, (float) magnification / 64, 0, 255, true);
//                    }
//                }
//            }
//        }
//
//        //1つの画像で表示
//        else if(draw_mode == 3) {
//            graphic.bookingDrawBitmapData(map_image, -1 * camera.camera_offset.x - magnification, -1 * camera.camera_offset.y, 1, 1, 0, 255, true);
//        }
//
//        //1つの画像を切り取って表示
//        else if(draw_mode == 4) {
//            BitmapData trim_map_data;
//            int l_x = map_size.x;
//            int r_x = -1;
//            int u_y = map_size.y;
//            int d_y = -1;
//            for (int i = 0; i < map_size.x; i++) {
//                for (int j = 0; j < map_size.y; j++) {
//                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                        if (i < l_x) {
//                            l_x = i;
//                        }
//                        if (r_x < i) {
//                            r_x = i;
//                        }
//                        if (j < u_y) {
//                            u_y = j;
//                        }
//                        if (d_y < j) {
//                            d_y = j;
//                        }
//                    }
//                }
//            }
//            trim_map_data = graphic.processTrimmingBitmapData(map_image, l_x * 64, u_y * 64, r_x * 64 - l_x * 64, d_y * 64 - u_y * 64);
//            graphic.bookingDrawBitmapData(trim_map_data, -64, -64, 1, 1, 0, 255, true);
//        }
//
//        //中心点の表示
//        if(is_debug_mode) {
//            paint.setColor(Color.RED);
//            graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
//        }
//        updateMiniMapDispState(worldToMap(camera.getCameraOffset().x+800), worldToMap(camera.getCameraOffset().y+450));
//        drawSmallMap3(camera.getCameraOffset().x+800, camera.getCameraOffset().y+450);
//        //画像表示デバッグ用
//        graphic.bookingDrawBitmapData(auto_tile_wall.big_auto_tile[46], 0, 0, 1, 1, 0, 255, true);
//    }
//
//    //四角たくさん
//    public void drawSmallMap(){
//        Paint blue_paint = new Paint();
//        Paint red_paint = new Paint();
//        Paint green_paint = new Paint();
//        Paint yellow_paint = new Paint();
//        int small_map_mag = 8;
//        int small_map_offset_x = 0;
//        int small_map_offset_y = 0;
////        int countMiniDrawRect = 0;
//        for (int i = 0; i < this.getMap_size_x(); i++) {
//            for (int j = 0; j < this.getMap_size_y(); j++) {
//                //部屋
//                if (!isWall(i, j) && !isStairs(i, j) && isRoom(i, j) && isDisp(i, j)) {
//                    blue_paint.setARGB(200,0,0,255);
//                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, blue_paint);
////                    countMiniDrawRect++;
//                }
//                //通路
//                else if(!isWall(i, j) && !isStairs(i, j) && !isRoom(i, j) && isDisp(i, j)){
//                    red_paint.setARGB(100,255,0,0);
//                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, red_paint);
////                    countMiniDrawRect++;
//                }
//                //階段
//                else if (isStairs(i, j) && isDisp(i, j)) {
//                    green_paint.setColor(Color.GREEN);
//                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, green_paint);
////                    countMiniDrawRect++;
//                }
//            }
//        }
////        System.out.println("countMiniDrawRect = "+countMiniDrawRect);
//        yellow_paint.setColor(Color.YELLOW);
//        graphic.bookingDrawCircle(small_map_offset_x + offset.x*small_map_mag/magnification, small_map_offset_y + offset.y*small_map_mag/magnification, 10, yellow_paint);
//    }
//
//    //canvas使用
//    public void drawSmallMapForCanvas(){
//        Paint blue_paint = new Paint();
//        Paint red_paint = new Paint();
//        Paint green_paint = new Paint();
//        Paint yellow_paint = new Paint();
//        int small_map_mag = 8;
//        int small_map_offset_x = 0;
//        int small_map_offset_y = 0;
//        for (int i = 0; i < this.getMap_size_x(); i++) {
//            for (int j = 0; j < this.getMap_size_y(); j++) {
//                //部屋
//                if (!isWall(i, j) && !isStairs(i, j) && isRoom(i, j) && isDisp(i, j)) {
//                    blue_paint.setARGB(200,0,0,255);
//                    canvas.drawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, blue_paint);
//                }
//                //通路
//                else if(!isWall(i, j) && !isStairs(i, j) && !isRoom(i, j) && isDisp(i, j)){
//                    red_paint.setARGB(100,255,0,0);
//                    canvas.drawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, red_paint);
//                }
//                //階段
//                else if (isStairs(i, j) && isDisp(i, j)) {
//                    green_paint.setColor(Color.GREEN);
//                    canvas.drawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, green_paint);
//                }
//            }
//        }
//        yellow_paint.setColor(Color.YELLOW);
//        canvas.drawCircle(small_map_offset_x + offset.x*small_map_mag/magnification, small_map_offset_y + offset.y*small_map_mag/magnification, 10, yellow_paint);
//    }
//
//    //部屋を1つの四角で表示
//    public void drawSmallMap2(int x, int y){
//        Paint blue_paint = new Paint();
//        Paint red_paint = new Paint();
//        Paint green_paint = new Paint();
//        Paint yellow_paint = new Paint();
//        int small_map_mag = 8;
//        int small_map_offset_x = 0;
//        int small_map_offset_y = 0;
////        int countMiniDrawRect = 0;
//        if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isRoom()) {
//            section_admin.getNowRoom(worldToMap(offset.x), worldToMap(offset.y)).setDispflag(true);
//        }
//        blue_paint.setARGB(200, 0, 0, 255);
//        section_admin.drawAllRoom(graphic, blue_paint, small_map_mag);
//        for (int i = 0; i < this.getMap_size_x(); i++) {
//            for (int j = 0; j < this.getMap_size_y(); j++) {
//                //通路
//                if(!isWall(i, j) && !isStairs(i, j) && !isRoom(i, j) && isDisp(i, j)){
//                    red_paint.setARGB(100,255,0,0);
//                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, red_paint);
////                    countMiniDrawRect++;
//                }
//                //階段
//                else if (isStairs(i, j) && isDisp(i, j)) {
//                    green_paint.setColor(Color.GREEN);
//                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, green_paint);
////                    countMiniDrawRect++;
//                }
//            }
//        }
////        System.out.println("countMiniDrawRect = "+countMiniDrawRect);
//        yellow_paint.setColor(Color.YELLOW);
//        graphic.bookingDrawCircle(small_map_offset_x + offset.x*small_map_mag/magnification, small_map_offset_y + offset.y*small_map_mag/magnification, 10, yellow_paint);
//    }
//
//    }
    }
    */
}
