package com.maohx2.ina.Arrange;

import android.graphics.Paint;
import android.provider.Settings;

import com.maohx2.ina.Constants;
import com.maohx2.ina.Draw.BitmapData;
import com.maohx2.ina.Draw.Graphic;
import com.maohx2.ina.Draw.ImageContext;
import com.maohx2.ina.ItemData.EquipmentItemData;
import com.maohx2.ina.ItemData.EquipmentItemDataAdmin;
import com.maohx2.ina.ItemData.ItemData;
import com.maohx2.ina.Text.BoxImagePlate;
import com.maohx2.ina.Text.BoxInventryPlate;
import com.maohx2.ina.Text.BoxItemPlate;
import com.maohx2.ina.Text.PlateGroup;
import com.maohx2.ina.UI.UserInterface;
import com.maohx2.kmhanko.itemdata.ExpendItemData;
import com.maohx2.kmhanko.itemdata.GeoObjectData;

import static com.maohx2.ina.Constants.Touch.TouchWay.*;
import static com.maohx2.ina.Constants.Inventry.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ina on 2017/12/15.
 */

//TODO 頼む、位置を後からセッターで変更できるように。そうしないと売却画面でインベントリを並べられない(現状だと全ての画面共通で、最初に定めた位置となっている)

public class Inventry {

    PlateGroup<BoxInventryPlate> operate_inventry_list_box;
    InventryData[] inventry_datas = new InventryData[INVENTRY_DATA_MAX];
    UserInterface user_interface;
    int contentNum;
    Graphic graphic;
    Paint paint;

    BoxImagePlate leftPlate;
    BoxImagePlate rightPlate;

    ImageContext left_triangle;
    ImageContext right_triangle;

    int[] leftTrianglePosition = new int[4];
    int[] rightTrianglePosition = new int[4];

    int page;

    BoxInventryPlate[] inventry_item_plates;
    int[][] position;

    BitmapData[][] flameElement;
    int width;
    int height;


    public Inventry() {

        for(int i = 0; i< INVENTRY_DATA_MAX; i++) {
            //by kmhank　インスタンス引数追加
            inventry_datas[i] = new InventryData(null, 0, this);
        }
    }

    public void init(UserInterface _user_interface, Graphic _graphic, int left, int top, int right, int bottom, int _contentNum) {
        user_interface = _user_interface;
        contentNum = _contentNum;
        graphic = _graphic;

        paint = new Paint();

        inventry_item_plates = new BoxInventryPlate[contentNum];
        position = new int[contentNum][4];


        paint.setARGB(255, 0, 0, 0);
        paint.setTextSize(30);


        for (int i = 0; i < contentNum; i++) {
            position[i][0] = left;
            position[i][1] = top + (i * (bottom - top) / contentNum);
            position[i][2] = right;
            position[i][3] = top + ((i + 1) * (bottom - top) / contentNum);
            //inventry_item_plates[i] = new BoxItemPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], test_item);
            inventry_item_plates[i] = new BoxInventryPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], inventry_datas[i]);
        }

        operate_inventry_list_box = new PlateGroup<BoxInventryPlate>(inventry_item_plates);


        paint.setARGB(255, 0, 255, 0);

        left_triangle = graphic.makeImageContext(graphic.searchBitmap("矢印左"), left+20, bottom,((float)(bottom-top)/(float)contentNum)/(float)graphic.searchBitmap("矢印左").getHeight(),((float)(bottom-top)/(float)contentNum)/(float)graphic.searchBitmap("矢印左").getHeight(),0,255,true);

        leftTrianglePosition[0] = left;
        leftTrianglePosition[1] = bottom;
        leftTrianglePosition[2] = left + (right - left) / 2;
        leftTrianglePosition[3] = leftTrianglePosition[1] + 100;

        right_triangle = graphic.makeImageContext(graphic.searchBitmap("矢印右"), right-graphic.searchBitmap("矢印右").getWidth()-20, bottom,((float)(bottom-top)/(float)contentNum)/(float)graphic.searchBitmap("矢印左").getHeight(),((float)(bottom-top)/(float)contentNum)/(float)graphic.searchBitmap("矢印右").getHeight(),0,255,true);

        rightTrianglePosition[0] = left + (right - left) / 2;
        rightTrianglePosition[1] = bottom;
        rightTrianglePosition[2] = right;
        rightTrianglePosition[3] = rightTrianglePosition[1] + 100;

        leftPlate = new BoxImagePlate(graphic, user_interface, paint, UP_MOMENT, MOVE, leftTrianglePosition, left_triangle, left_triangle);
        rightPlate = new BoxImagePlate(graphic, user_interface, paint, UP_MOMENT, MOVE, rightTrianglePosition, right_triangle, right_triangle);

        page = 0;

        flameElement = new BitmapData[3][3];

        width = graphic.searchBitmap("ウィンドウ5_枠のみ").getWidth();
        height = graphic.searchBitmap("ウィンドウ5_枠のみ").getHeight();

        for(int i = 0; i <3; i++) {
            for(int j = 0; j < 3; j++) {
                flameElement[i][j] = graphic.processTrimmingBitmapData(graphic.searchBitmap("ウィンドウ5_枠のみ"), (int)((double)j*(double)width/3.0), (int)((double)i*(double)height/3.0), (int)((double)width/3.0), (int)((double)height/3.0));
            }
        }
    }


    public void updata() {

        //System.out.println(inventry_datas[0].getItemNum());

        operate_inventry_list_box.update();

        leftPlate.update();
        rightPlate.update();

        InventryData touch_inventry_data = operate_inventry_list_box.getInventryData(operate_inventry_list_box.getTouchContentNum());

        if (touch_inventry_data != null) {
            user_interface.setInventryData(touch_inventry_data);
        }


        if (leftPlate.checkTouchContent() == true && page > 0) {
            page--;
            boolean pageCheck = false;
            for (int i = 0; i < contentNum; i++) {
                operate_inventry_list_box.setInventryData(inventry_datas[page * contentNum + i], i);
                if (inventry_datas[page * contentNum + i].getItemData() != null) {
                    pageCheck = true;
                }
            }

            if (pageCheck == false) {
                page++;
                for (int i = 0; i < contentNum; i++) {
                    operate_inventry_list_box.setInventryData(inventry_datas[page * contentNum + i], i);
                    if (inventry_datas[page * contentNum + i].getItemData() != null) {
                        pageCheck = true;
                    }
                }
            }
        }

        if (rightPlate.checkTouchContent() == true && page < 100) {
            page++;
            boolean pageCheck = false;
            for (int i = 0; i < contentNum; i++) {
                operate_inventry_list_box.setInventryData(inventry_datas[page * contentNum + i], i);
                if (inventry_datas[page * contentNum + i].getItemData() != null) {
                    pageCheck = true;
                }
            }

            if (pageCheck == false) {
                page--;
                for (int i = 0; i < contentNum; i++) {
                    operate_inventry_list_box.setInventryData(inventry_datas[page * contentNum + i], i);
                    if (inventry_datas[page * contentNum + i].getItemData() != null) {
                        pageCheck = true;
                    }
                }
            }
        }
    }

    public void draw() {
        operate_inventry_list_box.draw();

        for (int i = 0; i < contentNum; i++) {
            graphic.bookingDrawBitmapData(flameElement[0][0], position[i][0], position[i][1], 1.0f, 1.0f, 0, 254, true);
            graphic.bookingDrawBitmapData(flameElement[1][0], position[i][0], position[i][1] + height / 3, 1.0f, 1.0f, 0, 254, true);
            graphic.bookingDrawBitmapData(flameElement[2][0], position[i][0], position[i][3] - height / 3, 1.0f, 1.0f, 0, 254, true);

            graphic.bookingDrawBitmapData(flameElement[0][1], position[i][0] + width / 3, position[i][1], ((float)(position[i][2]-position[i][0]-(int)((double)width*2.0/3.0)))/((float)width/3.0f), 1.0f, 0, 254, true);
            graphic.bookingDrawBitmapData(flameElement[2][1], position[i][0] + width / 3, position[i][3] - height / 3, ((float)(position[i][2]-position[i][0]-(int)((double)width*2.0/3.0)))/((float)width/3.0f), 1.0f, 0, 254, true);

            graphic.bookingDrawBitmapData(flameElement[0][2], position[i][2] - width / 3, position[i][1], 1.0f, 1.0f, 0, 254, true);
            graphic.bookingDrawBitmapData(flameElement[1][2], position[i][2] - width / 3, position[i][1] + height / 3, 1.0f, 1.0f, 0, 254, true);
            graphic.bookingDrawBitmapData(flameElement[2][2], position[i][2] - width / 3, position[i][3] - height / 3, 1.0f, 1.0f, 0, 254, true);
        }

        rightPlate.draw();
        leftPlate.draw();
    }

    public void drawOnly() {
        operate_inventry_list_box.draw();
    }

    public void addItemData(ItemData _item_data) {
        int i = 0;

        if (_item_data.getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT || _item_data.getItemKind() == Constants.Item.ITEM_KIND.GEO) {
            i = INVENTRY_DATA_MAX;
        } else {
            for (i = 0; i < INVENTRY_DATA_MAX; i++) {
                if (inventry_datas[i].getItemData() != null) {
                    if (inventry_datas[i].getItemData().getName().equals(_item_data.getName())) {
                        inventry_datas[i].setItemNum(inventry_datas[i].getItemNum() + 1);
                        break;
                    }
                }
            }
        }

        if (i == INVENTRY_DATA_MAX) {
            i = 0;
            for (i = 0; i < INVENTRY_DATA_MAX; i++) {
                if (inventry_datas[i].getItemNum() == 0) {
                    inventry_datas[i].setItemData(_item_data);
                    inventry_datas[i].setItemNum(1);
                    if (i < contentNum) {
                        operate_inventry_list_box.getPlate(i).changeInventryData();
                    }
                    break;
                }
            }
        }
    }

    public void subItemData(ItemData _item_data) {
        int i = 0;
        for (i = 0; i < INVENTRY_DATA_MAX; i++) {
            if (inventry_datas[i].getItemData() != null) {
                if (inventry_datas[i].getItemData().getName().equals(_item_data.getName())) {
                    inventry_datas[i].setItemNum(inventry_datas[i].getItemNum() - 1);
                    break;
                }
            }
        }

        if(inventry_datas[i].getItemNum() <= 0){
            for(int j = i; j < INVENTRY_DATA_MAX-1; j++){
                //todo:直す
                inventry_datas[j] = inventry_datas[j+1];
            }
        }

    }

    //by kmhanko
    public ItemData getItemData(int i) {
        return inventry_datas[i].getItemData();
    }

    public int getItemNum(int i) {
        return inventry_datas[i].getItemNum();
    }

    /*
    public void deleteItemData(int i) {
        inventry_datas[i].delete();
    }
    */
    public void deleteItemData(String name) {
        for (int i = 0; i < INVENTRY_DATA_MAX; i++) {
            if (inventry_datas[i].getItemData() != null) {
                if (inventry_datas[i].getItemData().getName().equals(name)) {
                    inventry_datas[i].delete();
                }
            }
        }
    }
    public InventryData searchInventryData(ItemData _itemData) {
        for(int i = 0; i < INVENTRY_DATA_MAX; i++) {
            if(inventry_datas[i].getItemData().equals(_itemData)) {
                return inventry_datas[i];
            }
        }
        return null;
    }

    public InventryData getInventryData(int i) {
        return inventry_datas[i];
    }

    public void setPosition(int left, int top, int right, int bottom) {

        paint.setARGB(255, 0, 0, 0);
        paint.setTextSize(30);


        for (int i = 0; i < contentNum; i++) {
            position[i][0] = left;
            position[i][1] = top + (i * (bottom - top) / contentNum);
            position[i][2] = right;
            position[i][3] = top + ((i + 1) * (bottom - top) / contentNum);
            inventry_item_plates[i] = new BoxInventryPlate(graphic, user_interface, paint, UP_MOMENT, MOVE, position[i], inventry_datas[i]);
        }

        operate_inventry_list_box = new PlateGroup<BoxInventryPlate>(inventry_item_plates);
    }

    public int getInventryNum(){
        int num = 0;
        for(int i = 0; i < INVENTRY_DATA_MAX; i++) {
            if (inventry_datas[i].getItemData() != null) {
                num++;
            }
        }
        return num;
    }

    public void sortItemData() {

        page = 0;

        for (int i = 0; i < INVENTRY_DATA_MAX - 1; i++) {
            for (int j = 1; j < INVENTRY_DATA_MAX - i; j++) {
                if (inventry_datas[j].getItemData() != null && inventry_datas[j-1].getItemData() != null) {
                    if (inventry_datas[j].getItemData().getPrice() > inventry_datas[j - 1].getItemData().getPrice()) {
                        InventryData tmpInventryData = inventry_datas[j];
                        inventry_datas[j] = inventry_datas[j-1];
                        inventry_datas[j-1] = tmpInventryData;
                    }
                }
            }
        }

        for (int i = 0; i < contentNum; i++) {
            operate_inventry_list_box.setInventryData(inventry_datas[page * contentNum + i], i);
        }
    }

    public void sortItemDatabyKind() {
        page = 0;
        for (int i = 0; i < INVENTRY_DATA_MAX - 1; i++) {
            for (int j = 1; j < INVENTRY_DATA_MAX - i; j++) {
                if (inventry_datas[j].getItemData() != null && inventry_datas[j-1].getItemData() != null) {
                    if(inventry_datas[j].getItemData().getItemKind() == Constants.Item.ITEM_KIND.EQUIPMENT) {
                        if (((EquipmentItemData)(inventry_datas[j].getItemData())).getEquipmentKind().ordinal() > ((EquipmentItemData)(inventry_datas[j - 1].getItemData())).getEquipmentKind().ordinal()) {
                            InventryData tmpInventryData = inventry_datas[j];
                            inventry_datas[j] = inventry_datas[j-1];
                            inventry_datas[j-1] = tmpInventryData;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < contentNum; i++) {
            operate_inventry_list_box.setInventryData(inventry_datas[page * contentNum + i], i);
        }
    }
}
