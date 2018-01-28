#!/bin/bash

#ItemData.db EquipmentItemDataテーブルについてランダム生成する

tableName="EquipmentItemData"

#テーブルの全ての行を削除
echo "delete from $tableName;" >> DBCommandTemp

#image/global/item内のファイルから、拡張子を省いた文字列を取得
dirPath=../image/global/item
cnt=0
materialArray=(鉄 金 木 火 水 太陽 月 破壊 土 氷 雷 妖精 魔法 風 悪魔 天使 神 巨人)
for file in `find $dirPath -type f -name "?*.?*"`; do
  filename=`basename $file`
  imagename=${filename%.*}

#1画像につき２種類の名前の武器を用意。武器の名前は画像名 + 数字
for var in 0 1; do
  name="${materialArray[$cnt]}の${imagename::1}"
  cnt=`expr $cnt + 1`
  imageName=$imagename
  attack=$RANDOM
  price=$RANDOM

  case "${imagename::1}" in
    "剣") equipmentKind=0;;
    "杖") equipmentKind=1;;
    "槌") equipmentKind=2;;
    "斧") equipmentKind=3;;
    "弓") equipmentKind=4;;
  esac

  echo "insert into $tableName values('$name', '$imageName', '$equipmentKind', '$attack', '$price');" >> DBCommandTemp

done
done

#sqlite3に投げる
sqlite3 ItemData.db < DBCommandTemp
echo "ItemData.db#$tableName is created."

#一時ファイルの削除(~~Tempの削除)
for tempFile in `find . -maxdepth 4 -type f -name "*Temp"`; do
  rm $tempFile
done
