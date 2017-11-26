package com.maohx2.horie.map;

import java.util.Random;

/**
 * Created by horie on 2017/09/10.
 */

public class Section {
    int top;
    int bottom;
    int left;
    int right;
    int height;
    int width;
    int area;
    int section_number = -1;//leaves[]における番号,-1ならleaves[]に含まれない
    //その方向に隣接するセクションの数
    int upper_neighbor_num = 0;
    int lower_neighbor_num = 0;
    int left_neighbor_num = 0;
    int right_neighbor_num = 0;
    //その方向にあるセクションで繋がれたもののセクション番号(無い場合は-1)
    int connected_upper_leaf_num[];
    int connected_lower_leaf_num[];
    int connected_left_leaf_num[];
    int connected_right_leaf_num[];
    Section children[];
    Section upper_neighbor[];
    Section lower_neighbor[];
    Section left_neighbor[];
    Section right_neighbor[];
    boolean hasChildren;
    Room room;

    Section() {
        hasChildren = false;
    }

    //マップのデータをChip map_dataに反映
    public void updateMapData(Chip[][] m_map_data) {
        if (hasChildren == false) {
            //room実装後のバージョン
            for (int i = top; i <= bottom; i++) {
                for (int j = left; j < room.left; j++) {
                    m_map_data[j][i].setWallFlag(true);
                    m_map_data[j][i].setRoomFlag(false);
                }
                for (int j = room.right + 1; j <= right; j++) {
                    m_map_data[j][i].setWallFlag(true);
                    m_map_data[j][i].setRoomFlag(false);
                }
            }
            for (int i = left; i <= right; i++) {
                for (int j = top; j < room.top; j++) {
                    m_map_data[i][j].setWallFlag(true);
                    m_map_data[i][j].setRoomFlag(false);
                }
                for (int j = room.bottom + 1; j <= bottom; j++) {
                    m_map_data[i][j].setWallFlag(true);
                    m_map_data[i][j].setRoomFlag(false);
                }
            }
            /*
            //room作成前のバージョン
            //System.out.println("top = " + top + ",bottom = " + bottom + ",left = " + left + ",right = " + right);
            for(int i = top;i <= bottom;i++){
                m_map_data[left][i].setWallFlag(true);
                m_map_data[right][i].setWallFlag(true);
            }
            for(int i = left;i <= right;i++){
                m_map_data[i][top].setWallFlag(true);
                m_map_data[i][bottom].setWallFlag(true);
            }
            for(int i = top + 1;i < bottom;i++){
                for(int j = left + 1;j < right;j++){
                    m_map_data[j][i].setWallFlag(false);
                }
            }*/

        }
        /*
            for(int i = top;i <= bottom;i++){
                m_map_data[i][left].setWallFlag(true);
                m_map_data[i][right].setWallFlag(true);
            }
            for(int i = left;i <= right;i++){
                m_map_data[top][i].setWallFlag(true);
                m_map_data[bottom][i].setWallFlag(true);
            }
            for(int i = top + 1;i < bottom;i++){
                for(int j = left + 1;j < right;j++){
                    m_map_data[i][j].setWallFlag(false);
                }
            }
        }*/
        if (hasChildren == true) {
            children[0].updateMapData(m_map_data);
            children[1].updateMapData(m_map_data);
        }
    }

    public void makeRoom() {
        Random rnd = new Random();
        int room_height, room_width, room_top, room_left;
        //roomの幅を決める(section幅の50%以上)
        for (; ; ) {
            room_width = rnd.nextInt(width - 4);
            if (room_width > width * 0.5) {
                break;
            }
        }
        //roomの高さを決める(section高さの50%以上)
        for (; ; ) {
            room_height = rnd.nextInt(height - 4);
            if (room_height > height * 0.5) {
                break;
            }
        }
        room_top = rnd.nextInt(height - room_height - 2);
        if (room_top <= 1) {
            room_top = 2;
        }
        room_left = rnd.nextInt(width - room_width - 2);
        if (room_left <= 1) {
            room_left = 2;
        }
        room.setAll(left + room_left, top + room_top, left + room_left + room_width, top + room_top + room_height);
    }

    public void makeNeighbors(int max) {
        connected_upper_leaf_num = new int[max];
        connected_lower_leaf_num = new int[max];
        connected_left_leaf_num = new int[max];
        connected_right_leaf_num = new int[max];
        for (int i = 0; i < max; i++) {
            connected_upper_leaf_num[i] = -1;
            connected_lower_leaf_num[i] = -1;
            connected_left_leaf_num[i] = -1;
            connected_right_leaf_num[i] = -1;
        }
        upper_neighbor = new Section[max];
        for (int i = 0; i < max; i++) {
            upper_neighbor[i] = new Section();
        }
        lower_neighbor = new Section[max];
        for (int i = 0; i < max; i++) {
            lower_neighbor[i] = new Section();
        }
        left_neighbor = new Section[max];
        for (int i = 0; i < max; i++) {
            left_neighbor[i] = new Section();
        }
        right_neighbor = new Section[max];
        for (int i = 0; i < max; i++) {
            right_neighbor[i] = new Section();
        }
    }

    //getter
    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getHeight() {
        return bottom - top + 1;
    }

    public int getWidth() {
        return right - left + 1;
    }

    public int getArea() {
        return area;
    }

    public Section getChildren1() {
        return children[0];
    }

    public Section getChildren2() {
        return children[1];
    }

    public int getSectionNumber() {
        return section_number;
    }

    //setter
    public void setAll(int m_left, int m_top, int m_right, int m_bottom) {
        top = m_top;
        bottom = m_bottom;
        left = m_left;
        right = m_right;
        height = m_bottom - m_top;
        width = m_right - m_left;
        area = height * width;
        room = new Room();
    }

    public void setHasChildren(boolean m_hasChildren) {
        hasChildren = m_hasChildren;
        if (hasChildren) {
            children = new Section[2];
            for (int i = 0; i < 2; i++) {
                children[i] = new Section();
            }
        }
    }

    public void setUpper_neighbor(Section m_section) {
        for (int i = 0; i < upper_neighbor.length; i++) {
            if (upper_neighbor[i].area == 0) {
                upper_neighbor[i] = m_section;
            }
        }
    }

    public void setLower_neighbor(Section m_section) {
        for (int i = 0; i < lower_neighbor.length; i++) {
            if (lower_neighbor[i].area == 0) {
                lower_neighbor[i] = m_section;
            }
        }
    }

    public void setLeft_neighbor(Section m_section) {
        for (int i = 0; i < left_neighbor.length; i++) {
            if (left_neighbor[i].area == 0) {
                left_neighbor[i] = m_section;
            }
        }
    }

    public void setRight_neighbor(Section m_section) {
        for (int i = 0; i < right_neighbor.length; i++) {
            if (right_neighbor[i].area == 0) {
                right_neighbor[i] = m_section;
            }
        }
    }

    public void addUpperNeighborNum() {
        upper_neighbor_num++;
    }

    public void addLowerNeighborNum() {
        lower_neighbor_num++;
    }

    public void addLeftNeighborNum() {
        left_neighbor_num++;
    }

    public void addRightNeighborNum() {
        right_neighbor_num++;
    }

    public void setArea(int m_area) {
        area = m_area;
    }

    public void setTop(int m_top) {
        top = m_top;
    }

    public void setBottom(int m_bottom) {
        bottom = m_bottom;
    }

    public void setLeft(int m_left) {
        left = m_left;
    }

    public void setRight(int m_right) {
        right = m_right;
    }

    public void setConnectedUpperLeafNum(int num) {
        for (int i = 0; i < connected_upper_leaf_num.length; i++) {
            if (connected_upper_leaf_num[i] == -1) {
                connected_upper_leaf_num[i] = num;
                break;
            }
        }
    }

    public void setConnectedLowerLeafNum(int num) {
        for (int i = 0; i < connected_lower_leaf_num.length; i++) {
            if (connected_lower_leaf_num[i] == -1) {
                connected_lower_leaf_num[i] = num;
                break;
            }
        }
    }

    public void setConnectedLeftLeafNum(int num) {
        for (int i = 0; i < connected_left_leaf_num.length; i++) {
            if (connected_left_leaf_num[i] == -1) {
                connected_left_leaf_num[i] = num;
                break;
            }
        }
    }

    public void setConnectedRightLeafNum(int num) {
        for (int i = 0; i < connected_right_leaf_num.length; i++) {
            if (connected_right_leaf_num[i] == -1) {
                connected_right_leaf_num[i] = num;
                break;
            }
        }
    }

    public void setSectionNumber(int num) {
        section_number = num;
    }

    //以下デバッグ用
    public void printArea() {
        if (hasChildren) {
            children[0].printArea();
            children[1].printArea();
        } else {
            System.out.println("area = " + area);
        }
    }
}