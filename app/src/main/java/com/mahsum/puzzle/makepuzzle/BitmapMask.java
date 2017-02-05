package com.mahsum.puzzle.makepuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.NonNull;

import com.mahsum.puzzle.R;

public class BitmapMask {

  private static final Paint PAINT = new Paint(Paint.ANTI_ALIAS_FLAG);

  static {
        /*
        According to android
        DST = background image.
        SRC = second image drawn by canvas.drawBitmap
         */
    PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
  }

  private final Bitmap MASK;

  private BitmapMask(Bitmap mask) {
    MASK = mask;
  }

  public static BitmapMask topLeftMask(Context context) {
    return aMask(context, R.raw.top_left);
  }

  public static BitmapMask topMask(Context context) {
    return aMask(context, R.raw.top);
  }

  public static BitmapMask topRightMask(Context context) {
    return aMask(context, R.raw.top_right);
  }

  public static BitmapMask centerMask(Context context) {
    return aMask(context, R.raw.center);
  }

  public static BitmapMask leftMask(Context context) {
    return aMask(context, R.raw.left);
  }

  public static BitmapMask rightMask(Context context) {
    return aMask(context, R.raw.right);
  }

  public static BitmapMask bottomLeftMask(Context context) {
    return aMask(context, R.raw.bottom_left);
  }

  public static BitmapMask bottomMask(Context context) {
    return aMask(context, R.raw.bottom);
  }

  public static BitmapMask bottomRightMask(Context context) {
    return aMask(context, R.raw.bottom_right);
  }

  private static BitmapMask aMask(Context context, int resource) {
    Bitmap mask = BitmapFactory.decodeResource(context.getResources(), resource);
    return new BitmapMask(mask);
  }


  public BitmapMask resizeMask(int x, int y) {
    Bitmap mask = Bitmap.createScaledBitmap(this.MASK, x, y, false);
    return new BitmapMask(mask);
  }


  /**
   * Put MASK (x, y) koordinate of source image.
   *
   * @param x x coordinate
   * @param y y coordinate
   * @return masked bitmap
   */
  public Bitmap maskBitmap(@NonNull Bitmap source, int x, int y) {
    Bitmap partOfSource = Bitmap.createBitmap(source, x, y, MASK.getHeight(), MASK.getWidth());
    new Canvas(partOfSource).drawBitmap(MASK, 0, 0, PAINT);
    return partOfSource;
  }
}
