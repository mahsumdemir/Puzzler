#!/bin/bash
if [ $1 == "update" ]
then
	adb shell rm -rf /storage/sdcard/puzzle
	adb shell mkdir -p /storage/sdcard/puzzle
	adb push harput.png /storage/sdcard/puzzle
elif [ $1 == "error" ]
then
	rm -rf error.old
	mv error error.old
	adb pull /storage/sdcard/puzzle/error
elif [ $1 == "masks" ]
then
	rm -rf masks.old
	mv masks/ masks.old
	adb pull /storage/sdcard/puzzle/masks
elif [ $1 == "clean" ]
then
	adb shell rm -rf /storage/sdcard/puzzle
elif [ $1 == "pull" ]
then
	rm -rf puzzle
	adb pull /storage/sdcard/puzzle
else
	echo "------------------USAGE-------------"
	echo "./helperScript update to update datas"
	echo "./helperScript error to pull error dir"
	echo "./helperScript masks to pull masks"
fi
