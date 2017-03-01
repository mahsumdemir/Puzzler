package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

public class BitmapMask {

  private static final float MASK_CONSTANT = (float) 7 / 5;
  private final Bitmap bitmap;

  public BitmapMask(int x, int y) {
    bitmap = Bitmap.createBitmap(x, y, Config.ARGB_8888);
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

  public int getRealX() {
    return (int) (getWidth() * MASK_CONSTANT);
  }

  public int getRealY() {
    return (int) (getHeight() * MASK_CONSTANT);
  }

  public int getAdditionSizeX() {
    return getWidth() ;
  }
}
