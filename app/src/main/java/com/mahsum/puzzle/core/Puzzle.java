package com.mahsum.puzzle.core;

import android.graphics.Bitmap;

public class Puzzle {

  static Bitmap image;
  static Type type;

  public static void setImage(Bitmap image) {
    Puzzle.image = resizeImage(image);
    Puzzle.image = Puzzle.image.copy(Bitmap.Config.ARGB_8888, true);
  }

  private static Bitmap resizeImage(Bitmap image) {
    int newWidth, newHeight;
    int widthOutGrow = image.getWidth() % (type.getXPieceNumber() * 5);
    int heightOutGrow = image.getHeight() % (type.getYPieceNumber() * 5);

    if (widthOutGrow == 0 && heightOutGrow == 0) return image;
    else{
      newWidth = image.getWidth() - widthOutGrow;
      newHeight = image.getHeight() - heightOutGrow;
    }
    image = Bitmap.createScaledBitmap(image, newWidth, newHeight, false);
    return image;
  }

  /**
   * Setted image and getted image could have different size.
   * @return The Bitmap this puzzle is working on.
   */
  public static Bitmap getImage() {
    return image;
  }
}
