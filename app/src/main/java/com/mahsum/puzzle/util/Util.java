package com.mahsum.puzzle.util;


import static com.mahsum.puzzle.Saving.saveBitmap;
import static junit.framework.Assert.fail;

import android.content.Context;
import android.graphics.Bitmap;
import com.mahsum.puzzle.PuzzleFactory;
import com.mahsum.puzzle.PuzzleFactory.TYPE;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import com.mahsum.puzzle.makepuzzle.BitmapMask;

public class Util {
  public static Bitmap[] imageToPuzzle(Context context, Bitmap image){
    image = Bitmap.createScaledBitmap(image, 1200, 1200, false);
    int maskHeight = 300, maskWeight = 300;
    PuzzleFactory.createPuzzle(image, BitmapMask.getAll(context, maskWeight, maskHeight), TYPE.FOUR_X_FOUR);
    return new Bitmap[5];
  }

  public static void assertBitmapsEquals(Bitmap bitmap, Bitmap bitmap2) {
    if (bitmap == bitmap2) return;
    if (bitmap.getHeight() != bitmap2.getHeight() || bitmap.getWidth() != bitmap2.getWidth()) {
      throw new RuntimeException("Heights of bitmaps are different");
    }
    for(int height = 0; height < bitmap.getHeight(); height++){
      for(int width = 0; width < bitmap.getWidth(); width++){
        if (bitmap.getPixel(width, height) != bitmap2.getPixel(width, height)){
          printErrorMessage(bitmap, bitmap2, height, width);
        }
      }
    }
  }

  private static void printErrorMessage(Bitmap bitmap, Bitmap bitmap2, int height, int width) {
    String path = "/storage/sdcard/puzzle/error/";
    String errorMessage = String.format("Pixels at (%d, %d) of bitmaps are different", width, height);
    try {
      saveBitmap(bitmap, path + "expected");
      saveBitmap( bitmap2, path + "found");
    }catch (FileCouldNotCreated | FileCouldNotSaved e){
      errorMessage += " Expected and found bitmaps could not saved on device.";
    }
    throw new RuntimeException(errorMessage);
  }
}
