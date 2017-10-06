package com.example.horie.map;

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
    int number_of_leaves;
    Section children[];
    Section leaves[];
    boolean hasChildren;

    Section(){
        hasChildren = false;
    }

    //マップのデータをChip map_dataに反映
    public void updateMapData(Chip m_map_data[][]){
        if(hasChildren == false) {
            System.out.println("top = " + top + ",bottom = " + bottom + ",left = " + left + "right = " + right);
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
        }
        if(hasChildren == true){
            children[0].updateMapData(m_map_data);
            children[1].updateMapData(m_map_data);
        }
    }

    //分割アルゴリズム1(完全にランダムに2分割を繰り返す)
    public void divideSection(int divide_times){
        int buf_space = 20;//分割するときに上下左右をどれだけ空けるか
        children = new Section[2];
        for(int i = 0;i < 2;i++){
            children[i] = new Section();
        }
        Random rnd = new Random();
        int isver = rnd.nextInt(2);
        if((height <= 2 * buf_space) && (width <= 2 * buf_space)) {//これ以上分割できない
            System.out.println("can't divide, height = "+height+",width = "+width);
        }
        else if (isver == 0) {
            if (height > 2 * buf_space) {
                hasChildren = true;
                int divide_number = rnd.nextInt(height - 2 * buf_space) + buf_space + top;
                System.out.println("1top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(bottom - top - (2 * buf_space - 1)));
                children[0].setAll(top, divide_number, left, right);
                children[1].setAll(divide_number + 1, bottom, left, right);
            } else if (width > 2 * buf_space) {
                hasChildren = true;
                isver = 1;
                int divide_number = rnd.nextInt(width - 2 * buf_space) + buf_space + left;
                System.out.println("2top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(right - left - (2 * buf_space - 1)));
                children[0].setAll(top, bottom, left, divide_number);
                children[1].setAll(top, bottom, divide_number + 1, right);
            }
        }
        else if(isver == 1){
            if(width > 2 * buf_space) {
                hasChildren = true;
                int divide_number = rnd.nextInt(width- 2 * buf_space) + buf_space + left;
                System.out.println("3top = " + top + ",bottom = " + bottom + ",left = " + left + ",right = " + right + ",isver = " + isver + ",divide_number = " + divide_number + ",rnd = " + (right - left - (2 * buf_space - 1)));
                children[0].setAll(top, bottom, left, divide_number);
                children[1].setAll(top, bottom, divide_number + 1, right);
            }
            else if(height > 2 * buf_space){
                hasChildren = true;
                isver = 0;
                int divide_number = rnd.nextInt(height - 2 * buf_space) + buf_space + top;
                System.out.println("4top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(bottom - top - (2 * buf_space - 1)));
                children[0].setAll(top, divide_number, left, right);
                children[1].setAll(divide_number + 1, bottom, left, right);
            }
        }
        else{
            System.out.println("exception occurred");
        }
        if (divide_times - 1 > 0) {
            int which_children = rnd.nextInt(2);//分割する子を適当に選ぶ
            if (children[which_children].getHeight() < 10 && children[which_children].getWidth() < 10) {//これ以上分割できない
                which_children = 1 - which_children;
            }
            children[which_children].divideSection(divide_times - 1);
        }
        System.out.println("divide_times"+divide_times);
    }

    //分割アルゴリズム2(子を選択する際面積の広い方を選ぶ)
    public void divideSection2(int divide_times){
        int buf_space = 20;//分割するときに上下左右をどれだけ空けるか
        children = new Section[2];
        for(int i = 0;i < 2;i++){
            children[i] = new Section();
        }
        Random rnd = new Random();
        int isver = rnd.nextInt(2);
        if((bottom - top + 1 <= 2 * buf_space) && (right - left + 1 <= 2 * buf_space)) {//これ以上分割できない
            System.out.println("can't divide");
            divide_times = 0;
        }
        else if (isver == 0) {
            if (bottom - top + 1 > 2 * buf_space) {
                hasChildren = true;
                int divide_number = rnd.nextInt(bottom - top - (2 * buf_space - 1)) + buf_space + top;
                System.out.println("1top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(bottom - top - (2 * buf_space - 1)));
                children[0].setAll(top, divide_number, left, right);
                children[1].setAll(divide_number + 1, bottom, left, right);
            } else if (right - left + 1 > 2 * buf_space) {
                hasChildren = true;
                isver = 1;
                int divide_number = rnd.nextInt(right - left - (2 * buf_space - 1)) + buf_space + left;
                System.out.println("2top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(right - left - (2 * buf_space - 1)));
                children[0].setAll(top, bottom, left, divide_number);
                children[1].setAll(top, bottom, divide_number + 1, right);
            }
        }
        else if(isver == 1){
            if(right - left + 1 > 2 * buf_space) {
                hasChildren = true;
                int divide_number = rnd.nextInt(right - left - (2 * buf_space - 1)) + buf_space + left;
                System.out.println("3top = " + top + ",bottom = " + bottom + ",left = " + left + ",right = " + right + ",isver = " + isver + ",divide_number = " + divide_number + ",rnd = " + (right - left - (2 * buf_space - 1)));
                children[0].setAll(top, bottom, left, divide_number);
                children[1].setAll(top, bottom, divide_number + 1, right);
            }
            else if(bottom - top + 1 > 2 * buf_space){
                hasChildren = true;
                isver = 0;
                int divide_number = rnd.nextInt(bottom - top - (2 * buf_space - 1)) + buf_space + top;
                System.out.println("4top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(bottom - top - (2 * buf_space - 1)));
                children[0].setAll(top, divide_number, left, right);
                children[1].setAll(divide_number + 1, bottom, left, right);
            }
        }
        else{
            System.out.println("exception occurred");
        }
        if (divide_times - 1 > 0) {
            if(children[0].area >= children[1].area){
                children[0].divideSection2(divide_times - 1);
            }
            else{
                children[1].divideSection2(divide_times - 1);
            }
        }
        System.out.println("divide_times"+divide_times);
    }

    //分割アルゴリズム3(分割する部屋を全ての部屋の中からランダムに選ぶ)
    public void divideSection3(int divide_times){
        int buf_space = 20;//分割するときに上下左右をどれだけ空けるか
        children = new Section[2];
        for(int i = 0;i < 2;i++){
            children[i] = new Section();
        }
        number_of_leaves = divide_times + 1;
        leaves = new Section[number_of_leaves];
        for(int i = 0;i < divide_times + 1;i++){
            leaves[i] = new Section();
        }
        Random rnd = new Random();
        int isver = rnd.nextInt(2);
        if((bottom - top + 1 <= 2 * buf_space) && (right - left + 1 <= 2 * buf_space)) {//これ以上分割できない
            System.out.println("can't divide");
            divide_times = 0;
        }
        else if (isver == 0) {
            if (bottom - top + 1 > 2 * buf_space) {
                hasChildren = true;
                int divide_number = rnd.nextInt(bottom - top - (2 * buf_space - 1)) + buf_space + top;
                System.out.println("1top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(bottom - top - (2 * buf_space - 1)));
                children[0].setAll(top, divide_number, left, right);
                children[1].setAll(divide_number + 1, bottom, left, right);
                updateLeaves(leaves, children[0], children[1]);
            } else if (right - left + 1 > 2 * buf_space) {
                hasChildren = true;
                isver = 1;
                int divide_number = rnd.nextInt(right - left - (2 * buf_space - 1)) + buf_space + left;
                System.out.println("2top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(right - left - (2 * buf_space - 1)));
                children[0].setAll(top, bottom, left, divide_number);
                children[1].setAll(top, bottom, divide_number + 1, right);
                updateLeaves(leaves, children[0], children[1]);
            }
        }
        else if(isver == 1){
            if(right - left + 1 > 2 * buf_space) {
                hasChildren = true;
                int divide_number = rnd.nextInt(right - left - (2 * buf_space - 1)) + buf_space + left;
                System.out.println("3top = " + top + ",bottom = " + bottom + ",left = " + left + ",right = " + right + ",isver = " + isver + ",divide_number = " + divide_number + ",rnd = " + (right - left - (2 * buf_space - 1)));
                children[0].setAll(top, bottom, left, divide_number);
                children[1].setAll(top, bottom, divide_number + 1, right);
                updateLeaves(leaves, children[0], children[1]);
            }
            else if(bottom - top + 1 > 2 * buf_space){
                hasChildren = true;
                isver = 0;
                int divide_number = rnd.nextInt(bottom - top - (2 * buf_space - 1)) + buf_space + top;
                System.out.println("4top = "+top+",bottom = "+bottom+",left = "+left+",right = "+right+",isver = "+isver+",divide_number = "+divide_number+",rnd = "+(bottom - top - (2 * buf_space - 1)));
                children[0].setAll(top, divide_number, left, right);
                children[1].setAll(divide_number + 1, bottom, left, right);
                updateLeaves(leaves, children[0], children[1]);
            }
        }
        else{
            System.out.println("exception occurred");
        }
        if (divide_times - 1 > 0) {
            int max_area_leaf_num = 0;//面積最大のleafの番号
            for(int i = 1;i < number_of_leaves;i++){
                if(leaves[i].area > leaves[max_area_leaf_num].area){
                    max_area_leaf_num = i;
                }
            }
            leaves[max_area_leaf_num].divideSection3(divide_times - 1);
        }
        System.out.println("divide_times"+divide_times);
    }

    //leafの更新
    public void updateLeaves(Section m_leaves[], Section m_children0, Section m_children1){
        int now_number_of_leaves = 0;
        if(m_children0.hasChildren == false){
            m_leaves[now_number_of_leaves] = m_children0;
            now_number_of_leaves++;
        }
        else{
            for(int i = 0;i < m_children0.number_of_leaves;i++){
                m_leaves[now_number_of_leaves] = m_children0.leaves[i];
                now_number_of_leaves++;
            }
        }
        if(m_children1.hasChildren == false){
            m_leaves[now_number_of_leaves] = m_children1;
            now_number_of_leaves++;
        }
        else{
            for(int i = 0;i < m_children0.number_of_leaves;i++){
                m_leaves[now_number_of_leaves] = m_children1.leaves[i];
                now_number_of_leaves++;
            }
        }
    }


    //getter
    public int getTop(){
        return top;
    }

    public int getBottom(){
        return bottom;
    }

    public int getLeft(){
        return left;
    }

    public int getRight(){
        return right;
    }

    public int getHeight(){
        return bottom - top + 1;
    }

    public int getWidth(){
        return right - left + 1;
    }

    public int getNumber_of_leaves(){
        return number_of_leaves;
    }

    //setter
    public void setAll(int m_top, int m_bottom, int m_left, int m_right){
        top = m_top;
        bottom = m_bottom;
        left = m_left;
        right = m_right;
        height = m_bottom - m_top + 1;
        width = m_right - m_left + 1;
        area = height * width;
    }
    public void setTop(int m_top){
        top = m_top;
    }

    public void setBottom(int m_bottom){
        bottom = m_bottom;
    }

    public void setLeft(int m_left){
        left = m_left;
    }

    public void setRight(int m_right){
        right = m_right;
    }

}
