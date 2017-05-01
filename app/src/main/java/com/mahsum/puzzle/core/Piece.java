package com.mahsum.puzzle.core;

import android.graphics.Bitmap;

public class Piece {

  public static final int TOP_LEFT = 0;
  public static final int TOP = 1;
  public static final int TOP_RIGHT = 2;
  public static final int LEFT = 3;
  public static final int CENTER = 4;
  public static final int RIGHT = 5;
  public static final int BOTTOM_LEFT = 6;
  public static final int BOTTOM = 7;
  public static final int BOTTOM_RIGHT = 8;

  private Bitmap bitmap;
  private BitmapMask mask;

  public int getMaskX() {
    return mask.getMaskX();
  }

  public int getAdditionSizeX() {
    return mask.getAdditionSizeX();
  }

  public int getMaskY() {
    return mask.getMaskY();
  }

  public int getAdditionSizeY() {
    return mask.getAdditionSizeY();
  }

  public Bitmap getBitmap() {
    return bitmap;
  }

  public void setBitmap(Bitmap bitmap) {
    this.bitmap = bitmap;
  }

  public void setMask(BitmapMask mask) {
    this.mask = mask;
  }
}
