#localフォルダからmonsterフォルダをサーチ
for dirPath in `find ../image/local -name monster -mindepth 2 -maxdepth 2 -type d`; do
  for fileName in `find $dirPath -type f -name "?*.?*"` ; do
    echo $fileName
    #トリミングする
    convert $fileName -trim $fileName
  done
done
#convert <返還前の画像> -trim <変換後の画像名>
