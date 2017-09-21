//書きかけ
package com.example.ina.maohx2;

/**
 * Created by Fuusya on 2017/09/21.
 */

public class BagItem {

    int NUM_OF_POCKETS = 5;
    int SIZE_OF_POCKET = 100;
    int type_of_sorting;
    int[][] id = new int[NUM_OF_POCKETS][SIZE_OF_POCKET];
    int[] num_of_items = new int[NUM_OF_POCKETS];

    public void init() {
        type_of_sorting = -1;
        for (int i = 0; i < NUM_OF_POCKETS; i++) {
            num_of_items[i] = 0;
            for (int k = 0; k < SIZE_OF_POCKET; k++) {
                id[i][k] = -1;
            }
        }
    }

    public void setItemIdToBagItem(int item_id) {



switch ((int)(item_id / 1000.0)){
    case 0:


}


        type_of_sorting = 1;//1だと入手順、2だとid順

        switch (type_of_sorting) {
            case 1:


        }
    }
}
