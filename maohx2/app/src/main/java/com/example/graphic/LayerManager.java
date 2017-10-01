package com.example.graphic;

/**
 * Created by ryomasenda on 2017/10/01.
 */

enum Layer{
    BACK,
    BACKGROUND,
    CHARACTER,
    FRONT
}

public class LayerManager {

    LayerManager(){

    }

    public Layer getLayer(String layerName){
        switch (layerName){
            case "back":
                return Layer.BACK;
            case "background":
                return Layer.BACKGROUND;
            case "character":
                return  Layer.CHARACTER;
            case "front":
                return  Layer.FRONT;
            default:
                return  Layer.BACK;
        }
    }

    public Layer getLayer(int layerNum){
        switch (layerNum){
            case 0:
                return Layer.BACK;
            case 1:
                return Layer.BACKGROUND;
            case 2:
                return  Layer.CHARACTER;
            case 3:
                return  Layer.FRONT;
            default:
                return  Layer.BACK;
        }
    }

}
