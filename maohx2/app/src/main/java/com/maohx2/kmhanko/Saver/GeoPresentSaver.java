package com.maohx2.kmhanko.Saver;

import com.maohx2.kmhanko.GeoPresent.GeoPresentManager;
import com.maohx2.kmhanko.database.MyDatabaseAdmin;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 2018/04/15.
 */

public class GeoPresentSaver extends SaveManager {
    static GeoPresentManager geoPresentManager;

    public GeoPresentSaver(MyDatabaseAdmin _databaseAdmin, String dbName, String dbAsset, int version, String _loadMode) {
        super(_databaseAdmin, dbName, dbAsset, version, _loadMode);
        //TODO geoPResent
    }



    static public void setGeoPresentManager(GeoPresentManager _geoPresentManager) {
        geoPresentManager = _geoPresentManager;
    }


    @Override
    //無限対応
    public void dbinit() {
        deleteAll();
        int size = geoPresentManager.getPresentListSize();
        for (int i = 1; i < size + 1; i++) {
            database.insertLineByArrayString(
                    "GeoPresentSave",
                    new String[] { "id", "count" },
                    new String[] { String.valueOf(i), String.valueOf(0) }
            );
        }


        database.insertLineByArray(
                "GeoPresentScore",
                new String[] { "hp", "attack", "defence", "luck", "special_score" },
                new Integer[] { 0,0,0,0,0 }
        );

    }

    @Override
    //無限対応
    public void save() {
        deleteAll();
        //List<Boolean> alreadyPresentGet = geoPresentManager.getAlreadyPresentGetFlags();
        List<Integer> presentGetCount = geoPresentManager.getPresentGetCounts();

        for(int i = 0; i < presentGetCount.size(); i++) {
            database.insertLineByArrayString(
                    "GeoPresentSave",
                    new String[] { "id", "count" },
                    new String[] { String.valueOf(i + 1), String.valueOf(presentGetCount.get(i)) }
            );
        }

        database.insertLineByArray(
                "GeoPresentScore",
                new String[] { "hp", "attack", "defence", "luck", "special_score" },
                geoPresentManager.getScores()
        );





    }

    @Override
    //無限対応
    public void load() {
        List<Integer> id = database.getInt("GeoPresentSave", "id");
        //List<Boolean> flag = database.getBoolean("GeoPresentSave", "flag");
        List<Integer> count = database.getInt("GeoPresentSave", "count");

        //List<Boolean> alreadyPresentGet = new ArrayList<>();
        /*
        for (int i = 0; i < id.size(); i++) {
            if (id.get(i) > alreadyPresentGet.size()) {
                int beforeSize = alreadyPresentGet.size();
                for (int j = 0; j < id.get(i) - beforeSize; j++) {
                    alreadyPresentGet.add(false);
                }
            }
            alreadyPresentGet.set(id.get(i) - 1, flag.get(i));
        }
        */

        geoPresentManager.setPresentGetCounts(count);

        geoPresentManager.setScores(
                new int[] {
                        database.getOneInt("GeoPresentScore", "hp", "rowid = 1"),
                        database.getOneInt("GeoPresentScore", "attack", "rowid = 1"),
                        database.getOneInt("GeoPresentScore", "defence", "rowid = 1"),
                        database.getOneInt("GeoPresentScore", "luck", "rowid = 1"),
                        database.getOneInt("GeoPresentScore", "special_score", "rowid = 1"),
                }
        );


    }


}
