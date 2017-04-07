package com.mahsum.puzzle.util;


import static com.mahsum.puzzle.Saving.saveBitmap;

import android.graphics.Bitmap;
import com.mahsum.puzzle.Application;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;

public class Util {

  public static void assertBitmapsEquals(Bitmap bitmap, Bitmap bitmap2) {
    if (bitmap == bitmap2) {
      return;
    }
    if (bitmap.getHeight() != bitmap2.getHeight() || bitmap.getWidth() != bitmap2.getWidth()) {
      throw new RuntimeException("Heights of bitmaps are different");
    }
    for (int height = 0; height < bitmap.getHeight(); height++) {
      for (int width = 0; width < bitmap.getWidth(); width++) {
        if (bitmap.getPixel(width, height) != bitmap2.getPixel(width, height)) {
          printErrorMessage(bitmap, bitmap2, height, width);
        }
      }
    }
  }

  private static void printErrorMessage(Bitmap bitmap, Bitmap bitmap2, int height, int width) {
    String path = Application.getImagesRootDir() + "/error";
    String errorMessage = String
        .format("Pixels at (%d, %d) of bitmaps are different", width, height);
    try {
      saveBitmap(bitmap, path + "expected");
      saveBitmap(bitmap2, path + "found");
    } catch (FileCouldNotCreated | FileCouldNotSaved e) {
      errorMessage += " Expected and found bitmaps could not saved on device.";
    }
    throw new RuntimeException(errorMessage);
  }
}
