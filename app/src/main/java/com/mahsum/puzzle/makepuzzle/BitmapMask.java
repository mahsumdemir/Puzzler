package com.mahsum.puzzle.makepuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.NonNull;

import android.util.SparseArray;
import com.mahsum.puzzle.R;
import java.util.HashMap;

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

  /**
   * Create all masks with specified height and weight and put them all in a HasMap
   * HashMap can be queried with BitmapMask.KEY.* keys.
   * @param context Needed to get mash resources
   * @param maskHeight Height of a mask
   * @param maskWeight Weight of a mask
   * @return filled HashMap
   */
  public static SparseArray<BitmapMask> getAll(Context context, int maskHeight, int maskWeight) {
    SparseArray<BitmapMask> allMasks = new SparseArray<>();
    allMasks.put(KEY.TOP_LEFT, getMask(context, KEY.TOP_LEFT).resizeMask(maskWeight, maskHeight));
    allMasks.put(KEY.TOP, getMask(context, KEY.TOP).resizeMask(maskWeight, maskHeight));
    allMasks.put(KEY.TOP_RIGHT, getMask(context, KEY.TOP_RIGHT).resizeMask(maskWeight, maskHeight));
    allMasks.put(KEY.CENTER, getMask(context, KEY.CENTER).resizeMask(maskWeight, maskHeight));
    allMasks.put(KEY.LEFT, getMask(context, KEY.LEFT).resizeMask(maskWeight, maskHeight));
    allMasks.put(KEY.RIGHT, getMask(context, KEY.RIGHT).resizeMask(maskWeight, maskHeight));
    allMasks.put(KEY.BOTTOM_LEFT, getMask(context, KEY.BOTTOM_LEFT).resizeMask(maskWeight, maskHeight));
    allMasks.put(KEY.BOTTOM, getMask(context, KEY.BOTTOM).resizeMask(maskWeight, maskHeight));
    allMasks.put(KEY.BOTTOM_RIGHT, getMask(context, KEY.BOTTOM_RIGHT).resizeMask(maskWeight, maskHeight));
    return allMasks;
  }


  public static BitmapMask getMask(Context context, final int BITMAP_KEY) {
    switch (BITMAP_KEY){
      case KEY.BOTTOM: return aMask(context, R.raw.bottom);
      case KEY.BOTTOM_LEFT: return aMask(context, R.raw.bottom_left);
      case KEY.BOTTOM_RIGHT: return aMask(context, R.raw.bottom_right);
      case KEY.CENTER: return aMask(context, R.raw.center);
      case KEY.LEFT: return aMask(context, R.raw.left);
      case KEY.RIGHT: return aMask(context, R.raw.right);
      case KEY.TOP: return aMask(context, R.raw.top);
      case KEY.TOP_LEFT: return aMask(context, R.raw.top_left);
      case KEY.TOP_RIGHT: return aMask(context, R.raw.top_right);
      default: return aMask(context, KEY.CENTER);
    }
  }

  public static class KEY{
    public static final int TOP_LEFT = 0;
    public static final int TOP = 1;
    public static final int TOP_RIGHT = 2;
    public static final int CENTER = 3;
    public static final int LEFT = 4;
    public static final int RIGHT = 5;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM = 7;
    public static final int BOTTOM_RIGHT = 8;
  }
}
