package com.maohx2.horie.map;

import android.graphics.Paint;
import android.graphics.Point;

import com.maohx2.ina.Draw.Graphic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by horie on 2017/10/06.
 */

public class SectionAdmin {
    int section_max_num;
    int now_leaves_number = 0;//使われているleaves[]の数
    int divide_times = 0;
    Point map_size = new Point(0, 0);
    Section root_section;
    Section leaves[];
    boolean is_leaves_connected[];//leavesが他のセクションと繋がっているかどうか
    boolean is_leaves_checked[];//leavesがcheckRoomConnectedを行ったかどうか

    public void setSection_max_num(int num) {
        section_max_num = num;
    }

    //マップ座標(x, y)がどのroomに属するか判定
    public Room getNowRoom(int x, int y){
        Section now_section = new Section();
        for(int i = 0;i <= now_leaves_number;i++){
            if(y >= leaves[i].getTop() && y <= leaves[i].getBottom() && x >= leaves[i].getLeft() && x <= leaves[i].getRight()){
                now_section = leaves[i];
                break;
            }
        }
        if(now_section == null){
            throw new Error("%☆セクションがない,x = "+x+", y = "+y);
        }
        if(now_section.getRoom() == null){
            throw new Error("%☆roomがない,x = "+x+", y = "+y);
        }
        return now_section.getRoom();
    }

    public SectionAdmin(int m_section_max_num, Point m_map_size) {
        setSection_max_num(m_section_max_num);
        is_leaves_connected = new boolean[m_section_max_num];
        is_leaves_checked = new boolean[m_section_max_num];
        leaves = new Section[m_section_max_num];
        for (int i = 0; i < m_section_max_num; i++) {
            leaves[i] = new Section();
            is_leaves_connected[i] = false;
            is_leaves_checked[i] = false;
        }
        map_size.set(m_map_size.x - 1, m_map_size.y - 1);
        root_section = new Section();
        root_section.setAll(0, 0, map_size.x, map_size.y);
    }

    public void updateMapData(Chip[][] m_map_data) {
        root_section.updateMapData(m_map_data);
    }

    public void startUpdateLeaves() {
        updateLeaves(root_section);
    }

    //leafの更新
    public void updateLeaves(Section m_section) {
        //ルートセクションだった場合はleaves[]は1つも使われていない
        if (m_section == root_section) {
            now_leaves_number = 0;
            for (int i = 0; i < section_max_num; i++) {
                leaves[i].setAll(0, 0, 0, 0);
            }
        }
        //引数のセクションが子を持っていた場合その子を探索
        if (m_section.hasChildren) {
            updateLeaves(m_section.getChildren1());
            updateLeaves(m_section.getChildren2());
        }
        //子を持っていない場合自身をleaves[]に追加
        else {
            for (int i = 0; i < section_max_num; i++) {
                if (leaves[i].area == 0) {
                    leaves[i] = m_section;
                    now_leaves_number = i;
                    leaves[i].setSectionNumber(i);
                    break;
                }
            }
        }
    }

    //隣のsectionを探す
    public void searchNeighbors() {
        //printLeaves();
        for (int i = 0; i < section_max_num; i++) {
            leaves[i].makeNeighbors(section_max_num - 1);
        }
        for (int i = 0; i < section_max_num; i++) {
            for (int j = 0; j < section_max_num; j++) {
                //System.out.println("leaves["+i+"].area = "+leaves[i].area+",leaves["+j+"]area = "+leaves[j].area);
                //上のsectionが接している
                if (leaves[i].top == leaves[j].bottom + 1 && i != j) {
                    int i_l = leaves[i].getLeft();//隣を探索するsectionのleft
                    int i_r = leaves[i].getRight();//隣を探索するsectionのright
                    int j_l = leaves[j].getLeft();//探索されるsectionのleft
                    int j_r = leaves[j].getRight();//探索されるsectionのright
                    //接している条件
                    if ((i_l <= j_l && j_l <= i_r) || (i_l <= j_r && j_r <= i_r) || (j_l <= i_l && i_r <= j_r)) {
                        leaves[i].setUpper_neighbor(leaves[j]);
                        leaves[i].addUpperNeighborNum();
                        //printNeighbors(i, j);
                        //System.out.println("this neighbor = "+leaves[i].area+",upper neighbor = "+leaves[j].area);
                    }
                }
                //下のsectionが接している
                if (leaves[i].bottom == leaves[j].top - 1 && i != j) {
                    int i_l = leaves[i].getLeft();//隣を探索するsectionのleft
                    int i_r = leaves[i].getRight();//隣を探索するsectionのright
                    int j_l = leaves[j].getLeft();//探索されるsectionのleft
                    int j_r = leaves[j].getRight();//探索されるsectionのright
                    //接している条件
                    if ((i_l <= j_l && j_l <= i_r) || (i_l <= j_r && j_r <= i_r) || (j_l <= i_l && i_r <= j_r)) {
                        leaves[i].setLower_neighbor(leaves[j]);
                        leaves[i].addLowerNeighborNum();
                        //printNeighbors(i, j);
                        //System.out.println("this neighbor = "+leaves[i].area+",lower neighbor = "+leaves[j].area);
                    }
                }
                //左のsectionが接している
                if (leaves[i].left == leaves[j].right + 1 && i != j) {
                    int i_t = leaves[i].getTop();//隣を探索するsectionのtop
                    int i_b = leaves[i].getBottom();//隣を探索するsectionのbottom
                    int j_t = leaves[j].getTop();//探索されるsectionのtop
                    int j_b = leaves[j].getBottom();//探索されるsectionのbottom
                    //接している条件
                    if ((i_t <= j_t && j_t <= i_b) || (i_t <= j_b && j_b <= i_b) || (j_t <= i_t && i_b <= j_b)) {
                        leaves[i].setLeft_neighbor(leaves[j]);
                        leaves[i].addLeftNeighborNum();
                        //printNeighbors(i, j);
                        //System.out.println("this neighbor = "+leaves[i].area+",left neighbor = "+leaves[j].area);
                    }
                }
                //右のsectionが接している
                if (leaves[i].right == leaves[j].left - 1 && i != j) {
                    int i_t = leaves[i].getTop();//隣を探索するsectionのtop
                    int i_b = leaves[i].getBottom();//隣を探索するsectionのbottom
                    int j_t = leaves[j].getTop();//探索されるsectionのtop
                    int j_b = leaves[j].getBottom();//探索されるsectionのbottom
                    //接している条件
                    if ((i_t <= j_t && j_t <= i_b) || (i_t <= j_b && j_b <= i_b) || (j_t <= i_t && i_b <= j_b)) {
                        leaves[i].setRight_neighbor(leaves[j]);
                        leaves[i].addRightNeighborNum();
                        //printNeighbors(i, j);
                        //System.out.println("this neighbor = "+leaves[i].area+",right neighbor = "+leaves[j].area);
                    }
                }
            }
        }
    }

    //セクション分割のスタート
    public void startDivideSection() {
        divideSection(root_section);
    }

    //セクションの分割
    public void divideSection(Section m_section) {
        divide_times++;
        int buf_space = 11;//分割するときに上下左右をどれだけ空けるか
        int top = m_section.top;
        int bottom = m_section.bottom;
        int right = m_section.right;
        int left = m_section.left;
        int height = m_section.height;
        int width = m_section.width;
        //Section children[] = new Section[2];
        /*for(int i = 0;i < 2;i++){
            m_section.children[i] = new Section();
        }*/
        Random rnd = new Random();
        int isver = rnd.nextInt(2);
        //System.out.println("isver = "+isver);
        //これ以上分割できない
        if ((height <= 2 * buf_space) && (width <= 2 * buf_space)) {
            //System.out.println("can't divide, height = "+height+",width = "+width);
        } else if (isver == 0) {
            if (height > 2 * buf_space) {
                m_section.setHasChildren(true);
                int divide_number = rnd.nextInt(height - 2 * buf_space) + buf_space + top;
                //System.out.println("1top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(bottom - top - (2 * buf_space - 1)));
                m_section.children[0].setAll(left, top, right, divide_number);
                m_section.children[1].setAll(left, divide_number + 1, right, bottom);
            } else if (width > 2 * buf_space) {
                m_section.setHasChildren(true);
                isver = 1;
                int divide_number = rnd.nextInt(width - 2 * buf_space) + buf_space + left;
                //System.out.println("2top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(right - left - (2 * buf_space - 1)));
                m_section.children[0].setAll(left, top, divide_number, bottom);
                m_section.children[1].setAll(divide_number + 1, top, right, bottom);
            }
        } else if (isver == 1) {
            if (width > 2 * buf_space) {
                m_section.setHasChildren(true);
                int divide_number = rnd.nextInt(width - 2 * buf_space) + buf_space + left;
                //System.out.println("3top = " + top + ",bottom = " + bottom + ",left = " + left + ",right = " + right + ",isver = " + isver + ",divide_number = " + divide_number + ",rnd = " + (right - left - (2 * buf_space - 1)));
                m_section.children[0].setAll(left, top, divide_number, bottom);
                m_section.children[1].setAll(divide_number + 1, top, right, bottom);
            } else if (height > 2 * buf_space) {
                m_section.setHasChildren(true);
                isver = 0;
                int divide_number = rnd.nextInt(height - 2 * buf_space) + buf_space + top;
                //System.out.println("4top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(bottom - top - (2 * buf_space - 1)));
                m_section.children[0].setAll(left, top, right, divide_number);
                m_section.children[1].setAll(left, divide_number + 1, right, bottom);
            }
        } else {
            System.out.println("exception occurred");
        }
        //生成されたセクションが上限以下だった場合セクションをさらに作る
        if (divide_times < section_max_num - 1) {
            /*面積最大のセクションを探す*/
            Section area_max_section;
            area_max_section = searchAreaMaxSection(root_section);
            divideSection(area_max_section);

            /*2択バージョン*/
            /*if(m_section.hasChildren) {
                if (m_section.getChildren1().area > m_section.getChildren2().area) {
                    divideSection(m_section.getChildren1());
                } else {
                    divideSection(m_section.getChildren2());
                }
            }*/
        }
        makeRoom(root_section);
    }

    //部屋を作る
    private void makeRoom(Section m_section) {
        if (m_section.hasChildren) {
            makeRoom(m_section.getChildren1());
            makeRoom(m_section.getChildren2());
        } else {
            m_section.makeRoom();
        }
    }

    //部屋を繋げる通路を作る
    public void connectRooms(Chip[][] map_data) {
        int neighbor_direction;
        int neighbor_room_num;
        int now_room_point;
        int connected_room_point;
        Random rnd = new Random();
        main_loop:
        for (; ; ) {
            int chosen_section_num = rnd.nextInt(now_leaves_number);
            Section now_section = leaves[chosen_section_num];//接続するセクション
            //方向を決める
            for (; ; ) {
                neighbor_direction = rnd.nextInt(4);//0:上,1:下,2:左,3:右
                //System.out.println("d = "+neighbor_direction);
                if (neighbor_direction == 0 && now_section.upper_neighbor_num != 0) {
                    break;
                } else if (neighbor_direction == 1 && now_section.lower_neighbor_num != 0) {
                    break;
                } else if (neighbor_direction == 2 && now_section.left_neighbor_num != 0) {
                    break;
                } else if (neighbor_direction == 3 && now_section.right_neighbor_num != 0) {
                    break;
                }
            }
            //System.out.println("section = "+chosen_section_num+", direction = "+neighbor_direction);
            //接続する部屋を選ぶ
            outside:
            for (; ; ) {
                //上と繋ぐ
                if (neighbor_direction == 0) {
                    neighbor_room_num = rnd.nextInt(now_section.upper_neighbor_num + 1);
                    Section connected_section = now_section.upper_neighbor[neighbor_room_num];//接続されるセクション
                    int now_room_left = now_section.room.getLeft();
                    int now_room_right = now_section.room.getRight();
                    int connected_room_left = connected_section.room.getLeft();
                    int connected_room_right = connected_section.room.getRight();
                    for (int i = 0; i < section_max_num - 1; i++) {
                        if (now_section.connected_upper_leaf_num[i] == connected_section.section_number)
                            break outside;
                    }
                    if (now_room_left <= connected_room_left && now_room_right >= connected_room_right) {
                        now_room_point = rnd.nextInt(connected_room_right - connected_room_left + 1) + connected_room_left;//通路を作る位置
                        connected_room_point = rnd.nextInt(connected_room_right - connected_room_left + 1) + connected_room_left;
                    } else if (now_room_left <= connected_room_left) {
                        if ((now_room_right - connected_room_left) < 5) {
                            break;
                        }
                        now_room_point = rnd.nextInt(now_room_right - connected_room_left + 1) + connected_room_left;//通路を作る位置
                        connected_room_point = rnd.nextInt(now_room_right - connected_room_left + 1) + connected_room_left;
                    } else if (now_room_right >= connected_room_right) {
                        if ((connected_room_right - now_room_left) < 5) {
                            break;
                        }
                        now_room_point = rnd.nextInt(connected_room_right - now_room_left + 1) + now_room_left;//通路を作る位置
                        connected_room_point = rnd.nextInt(connected_room_right - now_room_left + 1) + now_room_left;
                    } else {
                        now_room_point = rnd.nextInt(now_room_right - now_room_left + 1) + now_room_left;//通路を作る位置
                        connected_room_point = rnd.nextInt(now_room_right - now_room_left + 1) + now_room_left;
                    }
                    makeVerticalPath(now_room_point, now_section.room.getTop(), connected_room_point, connected_section.room.getBottom(), now_section.getTop(), map_data);
                    now_section.setConnectedUpperLeafNum(connected_section.getSectionNumber());
                    connected_section.setConnectedLowerLeafNum(chosen_section_num);
                    break;
                }
                //下と繋ぐ
                else if (neighbor_direction == 1) {
                    neighbor_room_num = rnd.nextInt(now_section.lower_neighbor_num + 1);
                    Section connected_section = now_section.lower_neighbor[neighbor_room_num];//接続されるセクション
                    int now_room_left = now_section.room.getLeft();
                    int now_room_right = now_section.room.getRight();
                    int connected_room_left = connected_section.room.getLeft();
                    int connected_room_right = connected_section.room.getRight();
                    for (int i = 0; i < section_max_num - 1; i++) {
                        if (now_section.connected_lower_leaf_num[i] == connected_section.section_number)
                            break outside;
                    }
                    if (now_room_left <= connected_room_left && now_room_right >= connected_room_right) {
                        now_room_point = rnd.nextInt(connected_room_right - connected_room_left + 1) + connected_room_left;//通路を作る位置
                        connected_room_point = rnd.nextInt(connected_room_right - connected_room_left + 1) + connected_room_left;
                    } else if (now_room_left <= connected_room_left) {
                        if ((now_room_right - connected_room_left) < 5) {
                            break;
                        }
                        now_room_point = rnd.nextInt(now_room_right - connected_room_left + 1) + connected_room_left;//通路を作る位置
                        connected_room_point = rnd.nextInt(now_room_right - connected_room_left + 1) + connected_room_left;
                    } else if (now_room_right >= connected_room_right) {
                        if ((connected_room_right - now_room_left) < 5) {
                            break;
                        }
                        now_room_point = rnd.nextInt(connected_room_right - now_room_left + 1) + now_room_left;//通路を作る位置
                        connected_room_point = rnd.nextInt(connected_room_right - now_room_left + 1) + now_room_left;
                    } else {
                        now_room_point = rnd.nextInt(now_room_right - now_room_left + 1) + now_room_left;//通路を作る位置
                        connected_room_point = rnd.nextInt(now_room_right - now_room_left + 1) + now_room_left;
                    }
                    //now_room_point = rnd.nextInt(now_room_right - now_room_left + 1) + now_room_left;//通路を作る位置
                    //connected_room_point = rnd.nextInt(connected_room_right - connected_room_left + 1) + connected_room_left;
                    makeVerticalPath(connected_room_point, connected_section.room.getTop(), now_room_point, now_section.room.getBottom(), now_section.getBottom(), map_data);
                    now_section.setConnectedLowerLeafNum(connected_section.getSectionNumber());
                    connected_section.setConnectedUpperLeafNum(chosen_section_num);
                    break;
                } else if (neighbor_direction == 2) {//左と繋ぐ
                    neighbor_room_num = rnd.nextInt(now_section.left_neighbor_num + 1);
                    Section connected_section = now_section.left_neighbor[neighbor_room_num];//接続されるセクション
                    int now_room_top = now_section.room.getTop();
                    int now_room_bottom = now_section.room.getBottom();
                    int connected_room_top = connected_section.room.getTop();
                    int connected_room_bottom = connected_section.room.getBottom();
                    for (int i = 0; i < section_max_num - 1; i++) {
                        if (now_section.connected_left_leaf_num[i] == connected_section.section_number)
                            break outside;
                    }
                    //nowの方がでかい
                    if (now_room_bottom >= connected_room_bottom && now_room_top <= connected_room_top) {
                        now_room_point = rnd.nextInt(connected_room_bottom - connected_room_top + 1) + connected_room_top;//通路を作る位置
                        connected_room_point = rnd.nextInt(connected_room_bottom - connected_room_top + 1) + connected_room_top;
                    }
                    //nowが下
                    else if (now_room_bottom >= connected_room_bottom && now_room_top > connected_room_top) {
                        if ((connected_room_bottom - now_room_top) < 5) {
                            break;
                        }
                        now_room_point = rnd.nextInt(connected_room_bottom - now_room_top + 1) + now_room_top;//通路を作る位置
                        connected_room_point = rnd.nextInt(connected_room_bottom - now_room_top + 1) + now_room_top;
                    }
                    //nowが上
                    else if (now_room_top <= connected_room_top && now_room_bottom < connected_room_bottom) {
                        if ((now_room_bottom - connected_room_top) < 5) {
                            break;
                        }
                        now_room_point = rnd.nextInt(now_room_bottom - connected_room_top + 1) + connected_room_top;//通路を作る位置
                        connected_room_point = rnd.nextInt(now_room_bottom - connected_room_top + 1) + connected_room_top;
                    }
                    //nowの方が小さい
                    else if (now_room_top > connected_room_top && now_room_bottom < connected_room_bottom) {
                        now_room_point = rnd.nextInt(now_room_bottom - now_room_top + 1) + now_room_top;//通路を作る位置
                        connected_room_point = rnd.nextInt(now_room_bottom - now_room_top + 1) + now_room_top;
                    } else {
                        now_room_point = 0;
                        connected_room_point = 0;
                    }
                    makeHorizontalPath(now_room_point, now_section.room.getLeft(), connected_room_point, connected_section.room.getRight(), now_section.getLeft(), map_data);
                    now_section.setConnectedLeftLeafNum(connected_section.getSectionNumber());
                    connected_section.setConnectedRightLeafNum(chosen_section_num);
                    break;
                } else {//右と繋ぐ
                    neighbor_room_num = rnd.nextInt(now_section.right_neighbor_num + 1);
                    Section connected_section = now_section.right_neighbor[neighbor_room_num];//接続されるセクション
                    int now_room_top = now_section.room.getTop();
                    int now_room_bottom = now_section.room.getBottom();
                    int connected_room_top = connected_section.room.getTop();
                    int connected_room_bottom = connected_section.room.getBottom();
                    for (int i = 0; i < section_max_num - 1; i++) {
                        if (now_section.connected_right_leaf_num[i] == connected_section.section_number)
                            break outside;
                    }
                    //nowの方がでかい
                    if (now_room_bottom >= connected_room_bottom && now_room_top <= connected_room_top) {
                        now_room_point = rnd.nextInt(connected_room_bottom - connected_room_top + 1) + connected_room_top;//通路を作る位置
                        connected_room_point = rnd.nextInt(connected_room_bottom - connected_room_top + 1) + connected_room_top;
                    }
                    //nowが下
                    else if (now_room_bottom >= connected_room_bottom && now_room_top > connected_room_top) {
                        if ((connected_room_bottom - now_room_top) < 5) {
                            break;
                        }
                        now_room_point = rnd.nextInt(connected_room_bottom - now_room_top + 1) + now_room_top;//通路を作る位置
                        connected_room_point = rnd.nextInt(connected_room_bottom - now_room_top + 1) + now_room_top;
                    }
                    //nowが上
                    else if (now_room_top <= connected_room_top && now_room_bottom < connected_room_bottom) {
                        if ((now_room_bottom - connected_room_top) < 5) {
                            break;
                        }
                        now_room_point = rnd.nextInt(now_room_bottom - connected_room_top + 1) + connected_room_top;//通路を作る位置
                        connected_room_point = rnd.nextInt(now_room_bottom - connected_room_top + 1) + connected_room_top;
                    }
                    //nowの方が小さい
                    else if (now_room_top > connected_room_top && now_room_bottom < connected_room_bottom) {
                        now_room_point = rnd.nextInt(now_room_bottom - now_room_top + 1) + now_room_top;//通路を作る位置
                        connected_room_point = rnd.nextInt(now_room_bottom - now_room_top + 1) + now_room_top;
                    } else {
                        now_room_point = 0;
                        connected_room_point = 0;
                    }
                    makeHorizontalPath(connected_room_point, connected_section.room.getLeft(), now_room_point, now_section.room.getRight(), now_section.getRight(), map_data);
                    now_section.setConnectedRightLeafNum(connected_section.getSectionNumber());
                    connected_section.setConnectedLeftLeafNum(chosen_section_num);
                    break;
                }
            }
            if (isRoomConnected()) {
                break main_loop;
            }
        }
    }

    //部屋が繋がっているかどうか返す関数
    private boolean isRoomConnected() {
        boolean is_connected;
        for (int i = 0; i <= now_leaves_number; i++) {
            is_leaves_checked[i] = false;
        }
        is_connected = is_leaves_connected[0];
        checkRoomConnected(leaves[0]);
        for (int i = 0; i <= now_leaves_number; i++) {
            is_connected = is_connected & is_leaves_connected[i];
        }
        return is_connected;
    }

    //部屋が繋がっているかどうか判定する関数
    private void checkRoomConnected(Section m_section) {
        for (int i = 0; i < m_section.connected_upper_leaf_num.length; i++) {
            if (m_section.connected_upper_leaf_num[i] != -1) {
                if (!is_leaves_checked[m_section.connected_upper_leaf_num[i]]) {
                    is_leaves_connected[m_section.connected_upper_leaf_num[i]] = true;
                    is_leaves_checked[m_section.connected_upper_leaf_num[i]] = true;
                    checkRoomConnected(leaves[m_section.connected_upper_leaf_num[i]]);
                    is_leaves_connected[m_section.section_number] = true;
                }
            }
            if (m_section.connected_lower_leaf_num[i] != -1) {
                if (!is_leaves_checked[m_section.connected_lower_leaf_num[i]]) {
                    is_leaves_connected[m_section.connected_lower_leaf_num[i]] = true;
                    is_leaves_checked[m_section.connected_lower_leaf_num[i]] = true;
                    checkRoomConnected(leaves[m_section.connected_lower_leaf_num[i]]);
                    is_leaves_connected[m_section.section_number] = true;
                }
            }
            if (m_section.connected_left_leaf_num[i] != -1) {
                if (!is_leaves_checked[m_section.connected_left_leaf_num[i]]) {
                    is_leaves_connected[m_section.connected_left_leaf_num[i]] = true;
                    is_leaves_checked[m_section.connected_left_leaf_num[i]] = true;
                    checkRoomConnected(leaves[m_section.connected_left_leaf_num[i]]);
                    is_leaves_connected[m_section.section_number] = true;
                }
            }
            if (m_section.connected_right_leaf_num[i] != -1) {
                if (!is_leaves_checked[m_section.connected_right_leaf_num[i]]) {
                    is_leaves_connected[m_section.connected_right_leaf_num[i]] = true;
                    is_leaves_checked[m_section.connected_right_leaf_num[i]] = true;
                    checkRoomConnected(leaves[m_section.connected_right_leaf_num[i]]);
                    is_leaves_connected[m_section.section_number] = true;
                }
            }
        }
    }

    //垂直な道を作る
    private void makeVerticalPath(int now_point, int now_top, int connected_point, int connected_bottom, int border, Chip[][] map_data) {
        map_data[now_point][now_top-1].setEntranceFlag(true);
        map_data[connected_point][connected_bottom+1].setEntranceFlag(true);
        for (int i = connected_bottom; i <= border; i++) {
            map_data[connected_point][i].setWallFlag(false);
        }
        for (int i = border; i <= now_top; i++) {
            map_data[now_point][i].setWallFlag(false);
        }
        if (now_point <= connected_point) {
            for (int i = now_point; i <= connected_point; i++) {
                map_data[i][border].setWallFlag(false);
            }
        } else {
            for (int i = connected_point; i <= now_point; i++) {
                map_data[i][border].setWallFlag(false);
            }
        }
    }

    //水平な道を作る
    private void makeHorizontalPath(int now_point, int now_left, int connected_point, int connected_right, int border, Chip[][] map_data) {
        map_data[now_left-1][now_point].setEntranceFlag(true);
        map_data[connected_right+1][connected_point].setEntranceFlag(true);
        for (int i = connected_right; i <= border; i++) {
            map_data[i][connected_point].setWallFlag(false);
        }
        for (int i = border; i <= now_left; i++) {
            map_data[i][now_point].setWallFlag(false);
        }
        if (now_point <= connected_point) {
            for (int i = now_point; i <= connected_point; i++) {
                map_data[border][i].setWallFlag(false);
            }
        } else {
            for (int i = connected_point; i <= now_point; i++) {
                map_data[border][i].setWallFlag(false);
            }
        }
    }

    //面積最大のsectionを返す関数
    private Section searchAreaMaxSection(Section m_section) {
        Section now_area_max_section;
        //引数のセクションが子を持っていた場合その子を探索
        if (m_section.hasChildren) {
            Section s1 = searchAreaMaxSection(m_section.getChildren1());
            Section s2 = searchAreaMaxSection(m_section.getChildren2());
            if (s1.getArea() > s2.getArea()) {
                now_area_max_section = s1;
            } else {
                now_area_max_section = s2;
            }
        }
        //子を持っていない場合自身が最大
        else {
            now_area_max_section = m_section;
        }
        return now_area_max_section;
    }

    //階段を作る
    public void makeStairs(Chip[][] map_data) {
        int ul, u, ur, l, r, dl, d, dr;
        Random rnd = new Random();
        for (; ; ) {
            int leaves_num = rnd.nextInt(now_leaves_number);
            int x = rnd.nextInt(leaves[leaves_num].room.getRight() - leaves[leaves_num].room.getLeft() + 1) + leaves[leaves_num].room.getLeft();
            int y = rnd.nextInt(leaves[leaves_num].room.getBottom() - leaves[leaves_num].room.getTop() + 1) + leaves[leaves_num].room.getTop();
            ul = boolToInt(map_data[x - 1][y - 1].isWall());
            u = boolToInt(map_data[x][y - 1].isWall());
            ur = boolToInt(map_data[x + 1][y - 1].isWall());
            l = boolToInt(map_data[x - 1][y].isWall());
            r = boolToInt(map_data[x + 1][y].isWall());
            dl = boolToInt(map_data[x - 1][y + 1].isWall());
            d = boolToInt(map_data[x][y + 1].isWall());
            dr = boolToInt(map_data[x + 1][y + 1].isWall());
            if (ul + u + ur != 2 && ul + l + dl != 2 && dl + d + dr != 2 && ur + r + dr != 2) {
                map_data[x][y].setStairsFlag(true);
                break;
            }
        }

    }

    //boolean->int
    private int boolToInt(boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    //部屋の描画(ミニマップ用)
    public void drawAllRoom(Graphic graphic, Paint paint, int small_map_mag){
        for(int i = 0;i <= now_leaves_number;i++){
            leaves[i].getRoom().drawRoom(graphic, paint, small_map_mag);
        }
    }

    //採掘場の生成
    public void createMine(int min_num, int max_num, Chip[][] map_data){
        ArrayList<Integer> section_num_list = new ArrayList<Integer>();
        for(int i = 0;i <= section_max_num;i++){
            section_num_list.add(i);
        }
        Collections.shuffle(section_num_list);
        Random rnd = new Random();
        int mine_num = rnd.nextInt(max_num - min_num + 1) + min_num - 1;
        for(int i = 0;i == mine_num;i++){
            leaves[section_num_list.get(i)].makeMine(map_data);
        }
    }

    /*以下デバッグ用*/
    public void printSectionsArea() {
        root_section.printArea();
    }

    public void printLeavesArea() {
        for (int i = 0; i < section_max_num; i++) {
            System.out.println("leaves[" + i + "].area = " + leaves[i].area);
        }
    }

    public void printLeaves() {
        for (int i = 0; i < section_max_num; i++) {
            printLeaf(i);
        }
    }

    public void printLeaf(int i) {
        System.out.println("leaves[" + i + "] left = " + leaves[i].left + ", top = " + leaves[i].top + ", right = " + leaves[i].right + ", bottom = " + leaves[i].bottom + ", area = " + leaves[i].area);
    }

    public void printNeighbors(int i, int j) {
        System.out.print("i = ");
        printLeaf(i);
        System.out.print("j = ");
        printLeaf(j);
    }

    public void printLeavesNeighbors() {
        for (int i = 0; i < section_max_num; i++) {
            for (int j = 0; j < section_max_num - 1; j++) {
                System.out.println("leaves[" + i + "].neighbor.upper_neighbor[" + j + "].area = " + leaves[i].upper_neighbor[j].area);
                System.out.println("leaves[" + i + "].neighbor.lower_neighbor[" + j + "].area = " + leaves[i].lower_neighbor[j].area);
                System.out.println("leaves[" + i + "].neighbor.left_neighbor[" + j + "].area = " + leaves[i].left_neighbor[j].area);
                System.out.println("leaves[" + i + "].neighbor.right_neighbor[" + j + "].area = " + leaves[i].right_neighbor[j].area);
            }
        }
    }

    public void printNeighborLeafNum() {
        for (int i = 0; i < section_max_num; i++) {
            System.out.println("leaves[" + leaves[i].section_number + "]");
            for (int j = 0; j < section_max_num - 1; j++) {
                if (leaves[i].connected_upper_leaf_num[j] != -1)
                    System.out.println("upper_neighbor_num[" + j + "] = " + leaves[i].connected_upper_leaf_num[j]);
            }
            for (int j = 0; j < section_max_num - 1; j++) {
                if (leaves[i].connected_lower_leaf_num[j] != -1)
                    System.out.println("lower_neighbor_num[" + j + "] = " + leaves[i].connected_lower_leaf_num[j]);
            }
            for (int j = 0; j < section_max_num - 1; j++) {
                if (leaves[i].connected_lower_leaf_num[j] != -1)
                    System.out.println("left_neighbor_num[" + j + "] = " + leaves[i].connected_left_leaf_num[j]);
            }
            for (int j = 0; j < section_max_num - 1; j++) {
                if (leaves[i].connected_right_leaf_num[j] != -1)
                    System.out.println("right_neighbor_num[" + j + "] = " + leaves[i].connected_right_leaf_num[j]);
            }
        }
    }
}
