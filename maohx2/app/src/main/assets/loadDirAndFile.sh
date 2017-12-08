#!/bin/bash

parentDir=$1;
parentDirName=`basename $parentDir`

grandparentDirName=$2;

for dirPath in `find ${parentDir} -mindepth 1 -maxdepth 1 -type d`; do #parentDir内のフォルダ列挙
  dirName=`basename $dirPath`

  #dirNameの名でテーブル作成
  echo "create table $dirName(imagename, filename);" >> ${grandparentDirName}${parentDirName}DBCommandTemp

  for file in `find $dirPath -type f -name "?*.?*"`; do
    filename=`basename $file`
    imagename=${filename%.*}

    echo "$imagename|$filename" >> ${grandparentDirName}${parentDirName}${dirName}FilenameTemp

    #ファイル名を挿入する
    #echo "insert into ${dirName} values('${imagename}','${filename}');" >> ${grandparentDirName}${parentDirName}DBCommandTemp
  done

  iconv -f UTF-8-MAC -t UTF-8 ${grandparentDirName}${parentDirName}${dirName}FilenameTemp  > UTF8${grandparentDirName}${parentDirName}${dirName}FilenameTemp
  echo ".import UTF8${grandparentDirName}${parentDirName}${dirName}FilenameTemp $dirName" >> ${grandparentDirName}${parentDirName}DBCommandTemp
done

echo ".quit" >> ${grandparentDirName}${parentDirName}DBCommandTemp
