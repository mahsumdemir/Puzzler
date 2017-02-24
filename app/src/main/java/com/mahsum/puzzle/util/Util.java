package com.mahsum.puzzle.util;

import static com.mahsum.puzzle.Saving.saveBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
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
}
