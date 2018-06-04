
####パラメータ###
circle1R="150"
circle1PosX="1400"
circle1PosY="750"
circle2R="150"
circle2PosX="200"
circle2PosY="750"

boxX1="180"
boxX2="1420"
boxY1="0"
boxY2="120"

parentDir="../image/local"
parentDirName=`basename $parentDir`

for backgroundFile in `find $parentDir -mindepth 3 -maxdepth 3 -type f -name "old_firstBackground.*"`; do

  #oldBackgroundFile=${backgroundFile%/*}/old_firstBackground.${backgroundFile##*.}
  newBackgroundFile=${backgroundFile%/*}/firstBackground.png
  #${backgroundFile##*.}

  #echo $oldBackgroundFile
  #echo $newBackgroundFile

  #mv $backgroundFile $oldBackgroundFile

  #なんか同時に丸を複数かけないぽい？
  #convert $backgroundFile -fill "#000000" -draw "circle $circle1PosX $circle1PosY $circle1PosX $((${circle1PosY} + ${circle1R}))" -fill "#000000" -draw "circle $circle2PosX $circle2PosY $circle2PosX $((${circle2PosY} + ${circle2R}))" $newBackgroundFile
  convert $backgroundFile -fill "#000000" -draw "rectangle $boxX1 $boxY1 $boxX2 $boxY2" -fill "#000000" -draw "circle $circle1PosX $circle1PosY $circle1PosX $((${circle1PosY} + ${circle1R}))" -fill "#000000" -draw "circle $circle2PosX $circle2PosY $circle2PosX $((${circle2PosY} + ${circle2R}))" $newBackgroundFile

done
