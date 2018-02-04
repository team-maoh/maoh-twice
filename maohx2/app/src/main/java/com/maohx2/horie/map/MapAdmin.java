package com.maohx2.horie.map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;

import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;

import java.util.Random;

import static android.content.Context.SYSTEM_HEALTH_SERVICE;
import static android.content.Context.WINDOW_SERVICE;
import static java.lang.Math.abs;

/**
 * Created by horie on 2017/08/30.
 */

public class MapAdmin {
    int map_data_int[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };
    int[][] t_map_data_int = new int[map_data_int[0].length][map_data_int.length];

    Chip map_data[][];
    Graphic graphic;

    boolean is_debug_mode = false;

    final int MAX_MAP_SIZE_X = 1024;
    final int MAX_MAP_SIZE_Y = 1024;

    /*デバッグ用変数*/
    //Point map_size = new Point(map_data_int[0].length, map_data_int.length);
    //int magnification = 80;
    /*自動生成用変数*/
    //drawMap用
    //Point map_size = new Point(1080/30, 1700/30);//x : 左右幅, y : 上下幅
    //int magnification = 30;
    //drawMap2用
    Point map_size = new Point(80, 50);//x : 左右幅, y : 上下幅
    int magnification = 64;

    Point offset = new Point(0, 0);
    Point start_point = new Point(0, 0);

    Paint paint = new Paint();
    Point map_offset = new Point(0, 0);
    Camera camera = new Camera(map_size, magnification);
    SectionAdmin section_admin;
    Canvas canvas;
    SurfaceHolder holder;

    //auto_tile用
    AutoTile auto_tile_wall = new AutoTile();
    AutoTile auto_tile_side_wall = new AutoTile();
    AutoTileAdmin auto_tile_admin;
    boolean is_map_data_wall[][] = new boolean[map_size.x*2][map_size.y*2];//表示用に4分割されたmap_data
    boolean is_map_data_sidewall[][] = new boolean[map_size.x*2][map_size.y*2];
    BitmapData map_tile_set[][] = new BitmapData[map_size.x*2][map_size.y*2];//4分割されたmap画像
    BitmapData map_tile[][] = new BitmapData[map_size.x][map_size.y];//map_tile_set[][]を1つに纏めた画像
    BitmapData map_image;//mapを1つの画像にした物

    BitmapData floor_tile;
    BitmapData stair_tile;


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

    public Camera getCamera(){
        return camera;
    }

    public MapAdmin(Graphic m_graphic) {
        graphic = m_graphic;
        map_data = new Chip[map_size.x][map_size.y];
        for (int i = 0; i < map_size.x; i++) {
            for (int j = 0; j < map_size.y; j++) {
                map_data[i][j] = new Chip();
            }
        }
        //オートタイル生成
        BitmapData auto_tile_block_wall = graphic.searchBitmap("cave_wall_w_01");//auto_tile元データ
        BitmapData auto_tile_block_side_wall = graphic.searchBitmap("cave_wall_f_01");
        floor_tile = graphic.searchBitmap("cave_floor_01");
        stair_tile = graphic.searchBitmap("cave_step_01");
        auto_tile_admin = new AutoTileAdmin(graphic);
        for(int i = 0;i < 5;i++){
            auto_tile_wall.setAuto_tile(graphic.processTrimmingBitmapData(auto_tile_block_wall, 0, 32*i, 32, 32), i);
            auto_tile_side_wall.setAuto_tile(graphic.processTrimmingBitmapData(auto_tile_block_side_wall, 0, 32*i, 32, 32), i);
        }
        auto_tile_admin.createAutoTile(auto_tile_wall);
        auto_tile_admin.createAutoTile(auto_tile_side_wall);



        //マップ生成
        if (is_debug_mode) {
            transportMatrix();//デバッグ用
            intToChip(t_map_data_int);//デバッグ用
        } else {
            createMap();//自動生成
            createDispMapData();
        }
        //intToChip(map_data_int);//デバッグ用x, yが反転する

        //配列に画像を格納
        for(int i = 0;i < map_size.x;i++) {
            for (int j = 0; j < map_size.y; j++) {
//                map_tile_set[2*i][2*j] = auto_tile_wall.auto_tile[46];
//                map_tile_set[2*i+1][2*j] = auto_tile_wall.auto_tile[46];
//                map_tile_set[2*i][2*j+1] = auto_tile_wall.auto_tile[46];
//                map_tile_set[2*i+1][2*j+1] = auto_tile_wall.auto_tile[46];
                if (!isWall(i, j) && !isStairs(i, j)) {
                    map_tile_set[2*i][2*j] = floor_tile;
                    map_tile_set[2*i+1][2*j] = floor_tile;
                    map_tile_set[2*i][2*j+1] = floor_tile;
                    map_tile_set[2*i+1][2*j+1] = floor_tile;
                    //階段
                } else if (isStairs(i, j)) {
                    map_tile_set[2*i][2*j] = stair_tile;
                    map_tile_set[2*i+1][2*j] = stair_tile;
                    map_tile_set[2*i][2*j+1] = stair_tile;
                    map_tile_set[2*i+1][2*j+1] = stair_tile;
                } else {
                    setAutoTile(2 * i, 2 * j, 2*i, 2*j);
                    setAutoTile(2 * i + 1, 2 * j, 2*i+1, 2*j);
                    setAutoTile(2 * i, 2 * j + 1, 2*i, 2*j+1);
                    setAutoTile(2 * i + 1, 2 * j + 1, 2*i+1, 2*j+1);
                }
            }
        }
        //4つを1つに
        for(int i = 0;i < map_size.x;i++){
            for(int j = 0;j < map_size.y;j++){
                map_tile[i][j] = auto_tile_admin.combineFourAutoTile(map_tile_set[2*i][2*j], map_tile_set[2*i+1][2*j], map_tile_set[2*i][2*j+1], map_tile_set[2*i+1][2*j+1]);
            }
        }
        //1つの画像に
        BitmapData wide_map_image[] = new BitmapData[map_size.x];//横に繋げた画像
        for(int i = 0;i < map_size.x;i++){
            wide_map_image[i] = graphic.processCombineBitmapData(map_tile[i], false);
        }
        map_image = graphic.processCombineBitmapData(wide_map_image, true);
        getHolder();
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

    //玄関かどうかマップ座標で判定
    public boolean isEntrance(int x, int y){
        try {
            return map_data[x][y].isEntrance();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("配列の要素数をこえています。");
            System.out.println(e + "クラスの例外が発生しました。");
            return false;
        }
    }

    public boolean isRoom(int i, int j){
        return map_data[i][j].isRoom();
    }

    public boolean isDisp(int i, int j){
        return map_data[i][j].isDisp();
    }

    //壁かどうかワールドマップで判定
    public boolean isWallWorld(int world_x, int world_y, int magnification_x, int magnification_y) {
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
        section_admin = new SectionAdmin(150, map_size);
        section_admin.startDivideSection();
        section_admin.startUpdateLeaves();
        section_admin.searchNeighbors();
        section_admin.updateMapData(map_data);
        section_admin.connectRooms(map_data);
        section_admin.makeStairs(map_data);
        searchStartPoint();
        offset.set(start_point.x, start_point.y);
        //section_admin.printNeighborLeafNum();
    }

    //スタート地点を探す
    public void searchStartPoint(){
//        for(;;) {
//            Random rnd = new Random();
//            int x = rnd.nextInt(map_size.x);
//            int y = rnd.nextInt(map_size.y);
//            if(map_data[x][y].isRoom()){
//                start_point.set(x * magnification, y * magnification);
//                break;
//            }
//        }
        Point point = getRoomPoint();
        start_point.set(point.x, point.y);
    }

    //部屋のある一点を返す
    public Point getRoomPoint(){
        Point point = new Point(0, 0);
        for(;;) {
            Random rnd = new Random();
            int x = rnd.nextInt(map_size.x);
            int y = rnd.nextInt(map_size.y);
            if(map_data[x][y].isRoom()){
                point.set(x * magnification, y * magnification);
                break;
            }
        }
        return point;
    }

    //階層移動
    public void goNextFloor(){
        createMap();
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
        //プレイヤー座標と移動座標が同じマス
        if (player_map_x == next_player_map_x && player_map_y == next_player_map_y) {
            direction = 0;
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
    //Canvas使用バージョン
    public void drawMapForCanvas() {
        canvas = holder.lockCanvas();
        Paint l_gray_paint = new Paint();
        Paint d_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Paint paint = new Paint();
        //右手法で動く点の表示
        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()){
            offset.x = offset.x+10;
            offset.y = offset.y+10;
            //System.out.println("wall");
        }
        else{
            boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y-10)].isWall();
            boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y+10)].isWall();
            boolean left = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y)].isWall();
            boolean right = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y)].isWall();
            if(up&down&left&right){
                boolean ul = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y-10)].isWall();
                boolean ur = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y-10)].isWall();
                boolean dl = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y+10)].isWall();
                boolean dr = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y+10)].isWall();
                if(!ul&ur&dl&dr){
                    offset.x = offset.x-10;
                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                }
                else if(ul&!ur&dl&dr){
                    offset.y = offset.y-10;
                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                }
                else if(ul&ur&!dl&dr){
                    offset.y = offset.y+10;
                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                }
                else{
                    offset.x = offset.x+10;
                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                }
            }
            else if(!up&down&left&right|| !up&down&left&!right){
                offset.x = offset.x-10;
                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                //System.out.println("1");
            }
            else if(up&down&right || down&right){
                offset.y = offset.y+10;
                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                //System.out.println("2");
            }
            else if(up&left&right || up&right){
                offset.x = offset.x+10;
                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                //System.out.println("3");
            }
            else{
                offset.y = offset.y-10;
                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
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
                } else if(isEntrance(i, j)){
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
        /*
        canvas.drawLine(900, 0, 900, 1920, paint);
        canvas.drawLine(0, 1600, 1080, 1600, paint);
        canvas.drawLine(980, 0, 980, 1920, paint);
        canvas.drawLine(0, 1680, 1080, 1680, paint);
        */
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
        graphic.bookingDrawCircle(800,450,10,test_paint);
        drawSmallMap();
    }

    public void drawMap2() {
        //右手法で動く点の表示
        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()){
            offset.x = offset.x+10;
            //System.out.println("wall");
        }
        else{
            boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y-10)].isWall();
            boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y+10)].isWall();
            boolean left = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y)].isWall();
            boolean right = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y)].isWall();
            if(up&down&left&right){
                boolean ul = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y-10)].isWall();
                boolean ur = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y-10)].isWall();
                boolean dl = !map_data[worldToMap(offset.x-10)][worldToMap(offset.y+10)].isWall();
                boolean dr = !map_data[worldToMap(offset.x+10)][worldToMap(offset.y+10)].isWall();
                if(!ul&ur&dl&dr){
                    offset.x = offset.x-10;
                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                }
                else if(ul&!ur&dl&dr){
                    offset.y = offset.y-10;
                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                }
                else if(ul&ur&!dl&dr){
                    offset.y = offset.y+10;
                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                }
                else{
                    offset.x = offset.x+10;
                    updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                }
            }
            else if(!up&down&left&right|| !up&down&left&!right){
                offset.x = offset.x-10;
                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                //System.out.println("1");
            }
            else if(up&down&right || down&right){
                offset.y = offset.y+10;
                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                //System.out.println("2");
            }
            else if(up&left&right || up&right){
                offset.x = offset.x+10;
                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
                //System.out.println("3");
            }
            else{
                offset.y = offset.y-10;
                updateMiniMapDispState(offset.x/magnification, offset.y/magnification);
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
        /*
        canvas.drawLine(900, 0, 900, 1920, paint);
        canvas.drawLine(0, 1600, 1080, 1600, paint);
        canvas.drawLine(980, 0, 980, 1920, paint);
        canvas.drawLine(0, 1680, 1080, 1680, paint);
        */
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
        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()){
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
                } else if(isEntrance(i, j)){
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
        /*
        canvas.drawLine(900, 0, 900, 1920, paint);
        canvas.drawLine(0, 1600, 1080, 1600, paint);
        canvas.drawLine(980, 0, 980, 1920, paint);
        canvas.drawLine(0, 1680, 1080, 1680, paint);
        */
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
        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()){
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
                        drawWall(i, j, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), magnification/32);
                    }
                }
            }
        }
//        System.out.println("countDrawRect = "+countDrawRect);
        paint.setColor(Color.RED);
        //canvas.drawLine(80, 0, 80, 1920, paint);
        //canvas.drawLine(0, 80, 1080, 80, paint);
        /*
        canvas.drawLine(900, 0, 900, 1920, paint);
        canvas.drawLine(0, 1600, 1080, 1600, paint);
        canvas.drawLine(980, 0, 980, 1920, paint);
        canvas.drawLine(0, 1680, 1080, 1680, paint);
        */
        graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMap2(offset.x, offset.y);

    }

    //4分割して表示
    public void drawMap_for_autotile_4div() {
        Paint l_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Room now_point_room = new Room();
        int step = 30;
        boolean go_stair_flag = false;
        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()){
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
                        graphic.bookingDrawBitmapData(floor_tile, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float)magnification/64, (float)magnification/64, 0, 255, true);
                        graphic.bookingDrawBitmapData(floor_tile, camera.convertToNormCoordinateXForMap(i * magnification)+magnification/2, camera.convertToNormCoordinateYForMap(j * magnification), (float)magnification/64, (float)magnification/64, 0, 255, true);
                        graphic.bookingDrawBitmapData(floor_tile, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification)+magnification/2, (float)magnification/64, (float)magnification/64, 0, 255, true);
                        graphic.bookingDrawBitmapData(floor_tile, camera.convertToNormCoordinateXForMap(i * magnification)+magnification/2, camera.convertToNormCoordinateYForMap(j * magnification)+magnification/2, (float)magnification/64, (float)magnification/64, 0, 255, true);
                    }
                    //階段
                } else if (isStairs(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        graphic.bookingDrawRect(camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), camera.convertToNormCoordinateXForMap((i + 1) * magnification), camera.convertToNormCoordinateYForMap((j + 1) * magnification), green_paint);
                    }
                } else {
                    if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
                        drawWall2(i*2, j*2, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float)magnification/64);
                        drawWall2(i*2+1, j*2, camera.convertToNormCoordinateXForMap(i * magnification)+magnification/2, camera.convertToNormCoordinateYForMap(j * magnification), (float)magnification/64);
                        drawWall2(i*2, j*2+1, camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification)+magnification/2, (float)magnification/64);
                        drawWall2(i*2+1, j*2+1, camera.convertToNormCoordinateXForMap(i * magnification)+magnification/2, camera.convertToNormCoordinateYForMap(j * magnification)+magnification/2, (float)magnification/64);
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
        boolean is_debug_mode = true;
        int step = 10;
        boolean go_stair_flag = false;
        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()){
            goNextFloor();
        }

        //右手法で動く点の表示
        if(is_debug_mode) {
            if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isWall()) {
                offset.x = offset.x + step;
                offset.y = offset.y + step;
                //System.out.println("wall");
            }
            //階段へ向かう
            else {
                if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isRoom()) {
                    now_point_room = section_admin.getNowRoom(worldToMap(offset.x), worldToMap(offset.y));
                    if (now_point_room == null) {
                        System.out.println("%☆roomがない");
                    }
                    if (now_point_room != null) {
                        for (int i = now_point_room.getLeft(); i <= now_point_room.getRight(); i++) {
                            for (int j = now_point_room.getTop(); j <= now_point_room.getBottom(); j++) {
                                if (map_data[i][j].isStairs()) {
                                    go_stair_flag = true;
                                    int dst_x = i * magnification + magnification / 2;
                                    int dst_y = j * magnification + magnification / 2;
                                    if (abs(dst_x - offset.x) <= step) {
                                        offset.x = dst_x;
                                    } else if (abs(dst_y - offset.y) <= step) {
                                        offset.y = dst_y;
                                    }
                                    if (dst_x != offset.x && dst_x > offset.x) {
                                        offset.x = offset.x + step;
                                        break;
                                    } else if (dst_x != offset.x && dst_x < offset.x) {
                                        offset.x = offset.x - step;
                                        break;
                                    } else if (dst_y != offset.y && dst_y > offset.y) {
                                        offset.y = offset.y + step;
                                        break;
                                    } else {
                                        offset.y = offset.y - step;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if (!go_stair_flag) {
                    boolean up = !map_data[worldToMap(offset.x)][worldToMap(offset.y - step)].isWall();
                    boolean down = !map_data[worldToMap(offset.x)][worldToMap(offset.y + step)].isWall();
                    boolean left = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y)].isWall();
                    boolean right = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y)].isWall();
                    if (up & down & left & right) {
                        boolean ul = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y - step)].isWall();
                        boolean ur = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y - step)].isWall();
                        boolean dl = !map_data[worldToMap(offset.x - step)][worldToMap(offset.y + step)].isWall();
                        boolean dr = !map_data[worldToMap(offset.x + step)][worldToMap(offset.y + step)].isWall();
                        if (!ul & ur & dl & dr) {
                            offset.x = offset.x - step;
                            updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                        } else if (ul & !ur & dl & dr) {
                            offset.y = offset.y - step;
                            updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                        } else if (ul & ur & !dl & dr) {
                            offset.y = offset.y + step;
                            updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                        } else {
                            offset.x = offset.x + step;
                            updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                        }
                    } else if (!up & down & left & right || !up & down & left & !right) {
                        offset.x = offset.x - step;
                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                        //System.out.println("1");
                    } else if (up & down & right || down & right) {
                        offset.y = offset.y + step;
                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                        //System.out.println("2");
                    } else if (up & left & right || up & right) {
                        offset.x = offset.x + step;
                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                        //System.out.println("3");
                    } else {
                        offset.y = offset.y - step;
                        updateMiniMapDispState(offset.x / magnification, offset.y / magnification);
                        //System.out.println("4");
                    }
                }
            }
            camera.setCameraOffset(offset.x, offset.y);
            map_offset.set(camera.getCameraOffset().x, camera.getCameraOffset().y);
        }
        else {
            offset.set(800, 1300);
            camera.setCameraOffset(offset.x, offset.y);
        }

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
//        for (int i = 0; i < map_size.x; i++) {
//            for (int j = 0; j < map_size.y; j++) {
//                if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                    graphic.bookingDrawBitmapData(map_tile[i][j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float)magnification / 64, (float)magnification / 64, 0, 255, true);
//                }
//            }
//        }

        //1つの画像で表示
        graphic.bookingDrawBitmapData(map_image, -1*camera.camera_offset.x-64, -1*camera.camera_offset.y, 1, 1, 0, 255, true);


        paint.setColor(Color.RED);
        graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMap2(offset.x, offset.y);
    }

    public void drawMap_for_autotile_4div_combine_canvas() {
        canvas = holder.lockCanvas();
        Paint l_gray_paint = new Paint();
        Paint green_paint = new Paint();
        Room now_point_room = new Room();
        int step = 10;
        boolean go_stair_flag = false;
        if(map_data[worldToMap(offset.x)][worldToMap(offset.y)].isStairs()){
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
        camera.setCameraOffset(offset.x, offset.y);
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
//        for (int i = 0; i < map_size.x; i++) {
//            for (int j = 0; j < map_size.y; j++) {
//                if (camera.convertToNormCoordinateXForMap(i * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap(j * magnification) > -1 * magnification && camera.convertToNormCoordinateXForMap((i + 1) * magnification) > -1 * magnification && camera.convertToNormCoordinateYForMap((j + 1) * magnification) > -1 * magnification) {
//                    graphic.bookingDrawBitmapData(map_tile[i][j], camera.convertToNormCoordinateXForMap(i * magnification), camera.convertToNormCoordinateYForMap(j * magnification), (float)magnification / 64, (float)magnification / 64, 0, 255, true);
//                }
//            }
//        }

        //1つの画像で表示
//        graphic.bookingDrawBitmapData(map_image, -1*camera.camera_offset.x, -1*camera.camera_offset.y, 1, 1, 0, 255, true);

        canvas.drawBitmap(map_image.getBitmap(), -1*camera.camera_offset.x, -1*camera.camera_offset.y, paint);


        paint.setColor(Color.RED);
        graphic.bookingDrawCircle(camera.convertToNormCoordinateX(offset.x), camera.convertToNormCoordinateY(offset.y), 20, paint);
        drawSmallMap2(offset.x, offset.y);
        holder.unlockCanvasAndPost(canvas);
    }

    private void createMapTileSet(boolean lu, boolean u, boolean ru, boolean l, boolean r, boolean ld, boolean d, boolean rd, AutoTile m_auto_tile, int i, int j){
        if(lu && u && ru && l && r && ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[0];
        }
        else if(!lu && u && ru && l && r && ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[1];
        }
        else if(lu && u && !ru && l && r && ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[2];
        }
        else if(lu && u && ru && l && r && ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[3];
        }
        else if(lu && u && ru && l && r && !ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[4];
        }
        //5
        else if(!lu && u && !ru && l && r && ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[5];
        }
        else if(lu && u && !ru && l && r && ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[6];
        }
        else if(lu && u && ru && l && r && !ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[7];
        }
        else if(!lu && u && ru && l && r && !ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[8];
        }
        else if(!lu && u && ru && l && r && ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[9];
        }
        //10
        else if(lu && u && !ru && l && r && !ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[10];
        }
        else if(lu && u && !ru && l && r && !ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[11];
        }
        else if(!lu && u && ru && l && r && !ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[12];
        }
        else if(!lu && u && !ru && l && r && !ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[13];
        }
        else if(!lu && u && !ru && l && r && ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[14];
        }
        //15
        else if(!lu && u && !ru && l && r && !ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[15];
        }
        else if(u && ru && !l && r && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[16];
        }
        else if(u && !ru && !l && r && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[17];
        }
        else if(u && ru && !l && r && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[18];
        }
        else if(u && !ru && !l && r && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[19];
        }
        //20
        else if(!u && l && r && ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[20];
        }
        else if(!u && l && r && ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[21];
        }
        else if(!u && l && r && !ld && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[22];
        }
        else if(!u && l && r && !ld && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[23];
        }
        else if(lu && u && l && !r && ld && d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[24];
        }
        //25
        else if(lu && u && l && !r && !ld && d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[25];
        }
        else if(!lu && u && l && !r && ld && d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[26];
        }
        else if(!lu && u && l && !r && !ld && d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[27];
        }
        else if(lu && u && ru && l && r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[28];
        }
        else if(!lu && u && ru && l && r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[29];
        }
        //30
        else if(lu && u && !ru && l && r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[30];
        }
        else if(!lu && u && !ru && l && r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[31];
        }
        else if(u && !l && !r && d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[32];
        }
        else if(!u && l && r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[33];
        }
        else if(!u && !l && r && d && rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[34];
        }
        //35
        else if(!u && !l && r && d && !rd){
            map_tile_set[i][j] = m_auto_tile.auto_tile[35];
        }
        else if(!u && l && !r && ld && d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[36];
        }
        else if(!u && l && !r && !ld && d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[37];
        }
        else if(lu && u && l && !r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[38];
        }
        else if(!lu && u && l && !r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[39];
        }
        //40
        else if(u && ru && !l && r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[40];
        }
        else if(u && !ru && !l && r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[41];
        }
        else if(!u && !l && !r && d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[42];
        }
        else if(!u && !l && r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[43];
        }
        else if(u && !l && !r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[44];
        }
        //45
        else if(!u && l && !r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[45];
        }
        else if(!u && !l && !r && !d){
            map_tile_set[i][j] = m_auto_tile.auto_tile[46];
        }
    }

    //壁の表示
    //4分割しない
    private void drawWall(int array_x, int array_y, int draw_point_x, int draw_point_y, float magnification){
        int before_array_x = array_x - 1;
        int after_array_x = array_x + 1;
        int before_array_y = array_y - 1;
        int after_array_y = array_y + 1;
        if (before_array_x < 0){
            before_array_x = 0;
        }
        if (after_array_x > map_size.x-1){
            after_array_x = map_size.x-1;
        }
        if (before_array_y < 0){
            before_array_y = 0;
        }
        if (after_array_y > map_size.y-1){
            after_array_y = map_size.y-1;
        }
        auto_tile_admin.drawAutoTile(map_data[before_array_x][before_array_y].isWall(), map_data[array_x][before_array_y].isWall(), map_data[after_array_x][before_array_y].isWall(), map_data[before_array_x][array_y].isWall(), map_data[after_array_x][array_y].isWall(), map_data[before_array_x][after_array_y].isWall(), map_data[array_x][after_array_y].isWall(), map_data[after_array_x][after_array_y].isWall(),
                auto_tile_wall, draw_point_x, draw_point_y, magnification);
    }

    //4分割
    private void drawWall2(int array_x, int array_y, int draw_point_x, int draw_point_y, float magnification){
        int before_array_x = array_x - 1;
        int after_array_x = array_x + 1;
        int before_array_y = array_y - 1;
        int after_array_y = array_y + 1;
        if (before_array_x < 0){
            before_array_x = 0;
        }
        if (after_array_x > map_size.x*2-1){
            after_array_x = map_size.x*2-1;
        }
        if (before_array_y < 0){
            before_array_y = 0;
        }
        if (after_array_y > map_size.y*2-1){
            after_array_y = map_size.y*2-1;
        }
        if(!is_map_data_sidewall[array_x][array_y]) {
            auto_tile_admin.drawAutoTile(
                    is_map_data_wall[before_array_x][before_array_y] && !is_map_data_sidewall[before_array_x][before_array_y], is_map_data_wall[array_x][before_array_y] && !is_map_data_sidewall[array_x][before_array_y], is_map_data_wall[after_array_x][before_array_y] && !is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_wall[before_array_x][array_y] && !is_map_data_sidewall[before_array_x][array_y], is_map_data_wall[after_array_x][array_y] && !is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_wall[before_array_x][after_array_y] && !is_map_data_sidewall[before_array_x][after_array_y], is_map_data_wall[array_x][after_array_y] && !is_map_data_sidewall[array_x][after_array_y], is_map_data_wall[after_array_x][after_array_y] && !is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_wall, draw_point_x, draw_point_y, magnification);
        }
        else{
            auto_tile_admin.drawAutoTile(
                    is_map_data_sidewall[before_array_x][before_array_y], is_map_data_sidewall[array_x][before_array_y], is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_sidewall[before_array_x][array_y], is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_sidewall[before_array_x][after_array_y], is_map_data_sidewall[array_x][after_array_y], is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_side_wall, draw_point_x, draw_point_y, magnification);
        }
    }

    private void setAutoTile(int array_x, int array_y, int i, int j){
        int before_array_x = array_x - 1;
        int after_array_x = array_x + 1;
        int before_array_y = array_y - 1;
        int after_array_y = array_y + 1;
        if (before_array_x < 0){
            before_array_x = 0;
        }
        if (after_array_x > map_size.x*2-1){
            after_array_x = map_size.x*2-1;
        }
        if (before_array_y < 0){
            before_array_y = 0;
        }
        if (after_array_y > map_size.y*2-1){
            after_array_y = map_size.y*2-1;
        }
        if(!is_map_data_sidewall[array_x][array_y]) {
            createMapTileSet(
                    is_map_data_wall[before_array_x][before_array_y] && !is_map_data_sidewall[before_array_x][before_array_y], is_map_data_wall[array_x][before_array_y] && !is_map_data_sidewall[array_x][before_array_y], is_map_data_wall[after_array_x][before_array_y] && !is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_wall[before_array_x][array_y] && !is_map_data_sidewall[before_array_x][array_y], is_map_data_wall[after_array_x][array_y] && !is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_wall[before_array_x][after_array_y] && !is_map_data_sidewall[before_array_x][after_array_y], is_map_data_wall[array_x][after_array_y] && !is_map_data_sidewall[array_x][after_array_y], is_map_data_wall[after_array_x][after_array_y] && !is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_wall, i, j);
        }
        else{
            createMapTileSet(
                    is_map_data_sidewall[before_array_x][before_array_y], is_map_data_sidewall[array_x][before_array_y], is_map_data_sidewall[after_array_x][before_array_y],
                    is_map_data_sidewall[before_array_x][array_y], is_map_data_sidewall[after_array_x][array_y],
                    is_map_data_sidewall[before_array_x][after_array_y], is_map_data_sidewall[array_x][after_array_y], is_map_data_sidewall[after_array_x][after_array_y],
                    auto_tile_side_wall, i, j);
        }
    }

    private void createDispMapData(){
        for(int i = 0;i < map_size.x;i++){
            for(int j = 0;j < map_size.y;j++){
                if(map_data[i][j].isWall()){
                    is_map_data_wall[2*i][2*j] = true;
                    is_map_data_wall[2*i+1][2*j] = true;
                    is_map_data_wall[2*i][2*j+1] = true;
                    is_map_data_wall[2*i+1][2*j+1] = true;
                }
                else{
                    is_map_data_wall[2*i][2*j] = false;
                    is_map_data_wall[2*i+1][2*j] = false;
                    is_map_data_wall[2*i][2*j+1] = false;
                    is_map_data_wall[2*i+1][2*j+1] = false;
                }
            }
        }
        for(int i = 0;i < map_size.x*2;i++) {
            for (int j = 0; j < map_size.y*2-1; j++) {
                is_map_data_sidewall[i][j] = false;
                if(is_map_data_wall[i][j] && !is_map_data_wall[i][j+1]){
                    is_map_data_sidewall[i][j] = true;
                }
            }
        }
    }

    //ミニマップ表示
    //四角たくさん
    public void drawSmallMap(){
        Paint blue_paint = new Paint();
        Paint red_paint = new Paint();
        Paint green_paint = new Paint();
        Paint yellow_paint = new Paint();
        int small_map_mag = 8;
        int small_map_offset_x = 0;
        int small_map_offset_y = 0;
//        int countMiniDrawRect = 0;
        for (int i = 0; i < this.getMap_size_x(); i++) {
            for (int j = 0; j < this.getMap_size_y(); j++) {
                //部屋
                if (!isWall(i, j) && !isStairs(i, j) && isRoom(i, j) && isDisp(i, j)) {
                    blue_paint.setARGB(200,0,0,255);
                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, blue_paint);
//                    countMiniDrawRect++;
                }
                //通路
                else if(!isWall(i, j) && !isStairs(i, j) && !isRoom(i, j) && isDisp(i, j)){
                    red_paint.setARGB(100,255,0,0);
                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, red_paint);
//                    countMiniDrawRect++;
                }
                //階段
                else if (isStairs(i, j) && isDisp(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, green_paint);
//                    countMiniDrawRect++;
                }
            }
        }
//        System.out.println("countMiniDrawRect = "+countMiniDrawRect);
        yellow_paint.setColor(Color.YELLOW);
        graphic.bookingDrawCircle(small_map_offset_x + offset.x*small_map_mag/magnification, small_map_offset_y + offset.y*small_map_mag/magnification, 10, yellow_paint);
    }

    //canvas使用
    public void drawSmallMapForCanvas(){
        Paint blue_paint = new Paint();
        Paint red_paint = new Paint();
        Paint green_paint = new Paint();
        Paint yellow_paint = new Paint();
        int small_map_mag = 8;
        int small_map_offset_x = 0;
        int small_map_offset_y = 0;
        for (int i = 0; i < this.getMap_size_x(); i++) {
            for (int j = 0; j < this.getMap_size_y(); j++) {
                //部屋
                if (!isWall(i, j) && !isStairs(i, j) && isRoom(i, j) && isDisp(i, j)) {
                    blue_paint.setARGB(200,0,0,255);
                    canvas.drawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, blue_paint);
                }
                //通路
                else if(!isWall(i, j) && !isStairs(i, j) && !isRoom(i, j) && isDisp(i, j)){
                    red_paint.setARGB(100,255,0,0);
                    canvas.drawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, red_paint);
                }
                //階段
                else if (isStairs(i, j) && isDisp(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    canvas.drawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, green_paint);
                }
            }
        }
        yellow_paint.setColor(Color.YELLOW);
        canvas.drawCircle(small_map_offset_x + offset.x*small_map_mag/magnification, small_map_offset_y + offset.y*small_map_mag/magnification, 10, yellow_paint);
    }

    //部屋を1つの四角で表示
    public void drawSmallMap2(int x, int y){
        Paint blue_paint = new Paint();
        Paint red_paint = new Paint();
        Paint green_paint = new Paint();
        Paint yellow_paint = new Paint();
        int small_map_mag = 8;
        int small_map_offset_x = 0;
        int small_map_offset_y = 0;
//        int countMiniDrawRect = 0;
        if (map_data[worldToMap(offset.x)][worldToMap(offset.y)].isRoom()) {
            section_admin.getNowRoom(worldToMap(offset.x), worldToMap(offset.y)).setDispflag(true);
        }
        blue_paint.setARGB(200, 0, 0, 255);
        section_admin.drawAllRoom(graphic, blue_paint, small_map_mag);
        for (int i = 0; i < this.getMap_size_x(); i++) {
            for (int j = 0; j < this.getMap_size_y(); j++) {
                //通路
                if(!isWall(i, j) && !isStairs(i, j) && !isRoom(i, j) && isDisp(i, j)){
                    red_paint.setARGB(100,255,0,0);
                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, red_paint);
//                    countMiniDrawRect++;
                }
                //階段
                else if (isStairs(i, j) && isDisp(i, j)) {
                    green_paint.setColor(Color.GREEN);
                    graphic.bookingDrawRect(small_map_mag*i + small_map_offset_x, small_map_mag*j + small_map_offset_y, small_map_mag*(i + 1) + small_map_offset_x, small_map_mag*(j + 1) + small_map_offset_y, green_paint);
//                    countMiniDrawRect++;
                }
            }
        }
//        System.out.println("countMiniDrawRect = "+countMiniDrawRect);
        yellow_paint.setColor(Color.YELLOW);
        graphic.bookingDrawCircle(small_map_offset_x + offset.x*small_map_mag/magnification, small_map_offset_y + offset.y*small_map_mag/magnification, 10, yellow_paint);
    }


    //ミニマップの表示を状態を更新
    public void updateMiniMapDispState(int x, int y){
        if(!map_data[x][y].isWall()){
            map_data[x][y].setDispFlag(true);
            if(map_data[x][y].isRoom()){
                if(!map_data[x-1][y].isDisp()){
                    updateMiniMapDispState(x-1, y);
                }
                if(!map_data[x+1][y].isDisp()){
                    updateMiniMapDispState(x+1, y);
                }
                if(!map_data[x][y-1].isDisp()){
                    updateMiniMapDispState(x, y-1);
                }
                if(!map_data[x][y+1].isDisp()){
                    updateMiniMapDispState(x, y+1);
                }
            }
        }
        if(isStairs(x, y)){
            map_data[x][y].setDispFlag(true);
        }
    }

    //ワールドマップ座標からマップ座標に変更
    public int worldToMap(int world_coordinate) {
        return world_coordinate / magnification;
    }

    //以下デバッグ用
    //int配列をChip配列に変換
    public void intToChip(int[][] m_map_data) {
        for (int i = 0; i < m_map_data.length; i++) {
            for (int j = 0; j < m_map_data[i].length; j++) {
                if (m_map_data[i][j] == 0) {
                    map_data[i][j].setWallFlag(false);
                } else {
                    map_data[i][j].setWallFlag(true);
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

    //holder取得
    public void getHolder(){
        holder = graphic.getHolder();
    }
}
