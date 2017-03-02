package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.NonNull;

public class BitmapMask {

  private static final float REAL_BITMAP_SIZE_MULTIPLIER = (float) 7 / 5;
  private static final float ADDITION_SIZE_MULTIPLIER = (float) 1 / 7;
  private static final float MASK_SIZE_MULTIPLIER = (float) 5 / 7;
  private final Bitmap bitmap;

  /**
   * Creates a new bitmap. x % 5 and y % 5 should be zero, else they will be down graded to be 0.
   * @param x bitmap mask's x, used to calculate real bitmap size
   * @param y bitmap mask's y, used to calculate real bitmap size
     */
  public BitmapMask(int x, int y) {
    int surplusX = x % 5;
    int surplusY = y % 5;
    if (surplusX != 0) x = x - surplusX;
    if (surplusY != 0) y = y - surplusY;

    int realX = (int) (x * REAL_BITMAP_SIZE_MULTIPLIER);
    int realY = (int) (y * REAL_BITMAP_SIZE_MULTIPLIER);
    bitmap = Bitmap.createBitmap(realX, realY, Config.ARGB_8888);
  }

  public BitmapMask(@NonNull Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  public int getWidth() {
    return bitmap.getWidth();
  }

  public int getHeight() {
    return bitmap.getHeight();
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

  public int getMaskX() {
    return (int) (getWidth() * MASK_SIZE_MULTIPLIER);
  }

  public int getMaskY() {
    return (int) (getHeight() * MASK_SIZE_MULTIPLIER);
  }

  public int getAdditionSizeX() {
    return (int) (getWidth() * ADDITION_SIZE_MULTIPLIER) ;
  }

  public int getAdditionSizeY() {
    return (int) (getHeight() * ADDITION_SIZE_MULTIPLIER);
  }
}
