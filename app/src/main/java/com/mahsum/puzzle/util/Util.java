package com.mahsum.puzzle.util;


import static junit.framework.Assert.fail;

import android.content.Context;
import android.graphics.Bitmap;
import com.mahsum.puzzle.PuzzleFactory;
import com.mahsum.puzzle.PuzzleFactory.TYPE;
import com.mahsum.puzzle.makepuzzle.BitmapMask;

public class Util {
  public static Bitmap[] imageToPuzzle(Context context, Bitmap image){
    image = Bitmap.createScaledBitmap(image, 1200, 1200, false);
    int maskHeight = 300, maskWeight = 300;
    PuzzleFactory.createPuzzle(image, BitmapMask.getAll(context, maskWeight, maskHeight), TYPE.FOUR_X_FOUR);
    return new Bitmap[5];
  }

  public static void assertEquals(Bitmap bitmap, Bitmap bitmap2) {
    if (bitmap == bitmap2) return;
    if (bitmap.getHeight() != bitmap2.getHeight() || bitmap.getWidth() != bitmap2.getWidth()) {
      throw new RuntimeException("Heights of bitmaps are different");
    }
    for(int height = 0; height < bitmap.getHeight(); height++){
      for(int width = 0; width < bitmap.getWidth(); width++){
        if (bitmap.getPixel(width, height) != bitmap2.getPixel(width, height)){
          throw new RuntimeException(
              String.format("Pixels at (%d, %d) of bitmaps are different", width, height));
        }
      }
    }
  }
}
