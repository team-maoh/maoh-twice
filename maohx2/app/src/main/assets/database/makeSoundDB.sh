#!/bin/bash

parentDir="../sound"
parentDirName=`basename $parentDir`

for dirPath in `find ${parentDir} -mindepth 1 -maxdepth 1 -type d`; do #soundDir内のフォルダ列挙
  dirName=`basename $dirPath`
  echo $dirName
  #dirNameの名でテーブル作成
  echo "create table $dirName(name, filename);" >> ${parentDirName}DBCommandTemp

  for file in `find $dirPath -type f -name "?*.?*"`; do #soundDir
    echo $file
    filename=`basename $file`
    name=${filename%.*}
    echo "$name|$filename" >> ${parentDirName}${dirName}FilenameTemp
    done
  iconv -f UTF-8-MAC -t UTF-8 ${parentDirName}${dirName}FilenameTemp  > UTF8${parentDirName}${dirName}FilenameTemp
  echo ".import UTF8${parentDirName}${dirName}FilenameTemp $dirName" >> ${parentDirName}DBCommandTemp
done

echo ".quit" >> ${parentDirName}DBCommandTemp

#sqlite3に投げる
for DBCommandTemp in `find . -maxdepth 1 -type f -name "*DBCommandTemp"`; do
  DBCommandTempName=`basename $DBCommandTemp`
  rm ${DBCommandTempName%DBCommandTemp}.db
  sqlite3 ${DBCommandTempName%DBCommandTemp}.db < $DBCommandTemp
  echo "make ${DBCommandTempName%DBCommandTemp}.db"
done

#一時ファイルの削除(~~Tempの削除)
for tempFile in `find . -maxdepth 4 -type f -name "*Temp"`; do
  rm $tempFile
done
