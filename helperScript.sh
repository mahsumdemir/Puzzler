#!/bin/bash
if [ $1 == "update" ]
then
	adb shell rm -rf /storage/sdcard/puzzleBuilder
	adb shell mkdir -p /storage/sdcard/puzzleBuilder
	adb push harput.png /storage/sdcard/puzzleBuilder
elif [ $1 == "error" ]
then
	rm -rf error.old
	mv error error.old
	adb pull /storage/sdcard/puzzleBuilder/error
elif [ $1 == "masks" ]
then
	rm -rf masks.old
	mv masks/ masks.old
	adb pull /storage/sdcard/puzzleBuilder/masks
elif [ $1 == "clean" ]
then
	adb shell rm -rf /storage/sdcard/puzzleBuilder
elif [ $1 == "pull" ]
then
	rm -rf puzzleBuilder
	adb pull /storage/sdcard/puzzleBuilder
else
	echo "------------------USAGE-------------"
	echo "./helperScript update to update datas"
	echo "./helperScript error to pull error dir"
	echo "./helperScript masks to pull masks"
fi
