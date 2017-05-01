#!/bin/bash
if [ $1 == "update" ]
then
	adb shell rm -rf /storage/sdcard/puzzleCreator
	adb shell mkdir -p /storage/sdcard/puzzleCreator
	adb push harput.png /storage/sdcard/puzzleCreator
elif [ $1 == "error" ]
then
	rm -rf error.old
	mv error error.old
	adb pull /storage/sdcard/puzzleCreator/error
elif [ $1 == "masks" ]
then
	rm -rf masks.old
	mv masks/ masks.old
	adb pull /storage/sdcard/puzzleCreator/masks
elif [ $1 == "clean" ]
then
	adb shell rm -rf /storage/sdcard/puzzleCreator
elif [ $1 == "pull" ]
then
	rm -rf puzzleCreator
	adb pull /storage/sdcard/puzzleCreator
else
	echo "------------------USAGE-------------"
	echo "./helperScript update to update datas"
	echo "./helperScript error to pull error dir"
	echo "./helperScript masks to pull masks"
fi
