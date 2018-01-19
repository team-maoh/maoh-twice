######使用方法######
#1.makeDB.shと、loadDirAndFile.shを、assetsディレクトリ内に入れる。
#2.ターミナルを起動し、cdコマンドでassetsディレクトリに移動する
# 例 : $cd Desktop/maohx2_local/app/src/main/assets
#3. $sh makeDB.sh　を実行する
#4.~~~ImageDB.dbというdbファイルが、assets内に生成されます。ファイル名は以下の法則に従います。
# globalの場合　→  globalAutoImageDB.db
# localの場合　→　local/(ディレクトリ名)　ならば → Local(ディレクトリ名)ImageDB.db
#5.生成されたdbファイルは、そのディレクトリ内に存在するディレクトリ名をテーブル名として持ち、
# imagenameカラムにそのディレクトリ内にあるファイルの拡張子を除いた部分、filenameカラムに拡張子を含んだファイル名を要素として持ちます。
#例 : Local/Dungeon/Monster/Goki.pngが存在する場合、
# LocalDungeonImageDB.dbファイルが生成され、
# このDBは、少なくともMonsterテーブルを持ち、
# Monsterテーブルには、imagenameカラムにGoki,filenameカラムにGoki.pngを要素としてもちます。
###################

#!/bin/bash
#一時ファイルの削除(~~Tempの削除)
for tempFile in `find . -maxdepth 1 -type f -name "*Temp"`; do
  rm $tempFile
done

#globalの場合
sh loadDirAndFile.sh ./image/global

#localの場合
for dirPath in `find ./image/local -mindepth 1 -maxdepth 1 -type d`; do
  dirName=`basename $dirPath`
  sh loadDirAndFile.sh ./image/local/${dirName} Local
done

#sqlite3に投げる
for DBCommandTemp in `find . -maxdepth 1 -type f -name "*DBCommandTemp"`; do
  DBCommandTempName=`basename $DBCommandTemp`
  #rm ${DBCommandTempName%DBCommandTemp}ImageMAC.db
  #rm ${DBCommandTempName%DBCommandTemp}ImageNEW.db
  rm ${DBCommandTempName%DBCommandTemp}Image.db
  sqlite3 ${DBCommandTempName%DBCommandTemp}Image.db < $DBCommandTemp
  echo "make ${DBCommandTempName%DBCommandTemp}Image.db"
  #iconv -f UTF-8-MAC -t UTF-8 < ${DBCommandTempName%DBCommandTemp}Image.db > ${DBCommandTempName%DBCommandTemp}ImageNEW.db
done

#一時ファイルの削除(~~Tempの削除)
for tempFile in `find . -maxdepth 4 -type f -name "*Temp"`; do
  rm $tempFile
done










######古いやつ。そのままでは動かないかも

#imageディレクトリ内のディレクトリ名一覧をdirNameTempに保存
#for dirPath in `find ./image -mindepth 1 -maxdepth 2 -type d`; do #imageフォルダ内のフォルダ列挙
#  dirName=`basename $dirPath`
#  echo $dirName >> dirNameTemp
#  for file in `find $dirPath -type f -name "?*.?*"`; do
#    filename=`basename $file`
#    name=${filename%.*}
#    echo "$name|$filename" >> ${dirName}FilenameTemp
#  done
#done


###ここからsqlite3のコマンドをcommandTempファイルに保存

#tableをディレクトリごとに作成・ファイル名をテーブルに格納
#for dirName in `cat dirNameTemp`; do
#  echo "create table $dirName(name unique, filename);" >> commandTemp
#  for filenameTemp in `find . -maxdepth 1 -type f -name "*FilenameTemp"`; do
#  echo ".import $filenameTemp $dirName" >> commandTemp
  #echo "insert into $dirName values($name,$filename);" >> commandTemp
#  done
#done

#sqlite3を終了
#echo ".quit" >> commandTemp

###コマンドの保存ここまで

#実際にsqlite3を実行する
#sqlite3 $1 < commandTemp
