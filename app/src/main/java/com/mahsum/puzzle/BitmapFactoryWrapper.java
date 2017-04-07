package com.mahsum.puzzle;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

public class BitmapFactoryWrapper {

  public static Bitmap decodeFile(String path){
    return BitmapFactory.decodeFile(path);
  }

  public static Bitmap[] decodeMultipleResource(Context context, @DrawableRes int... ids){
    Resources resources = context.getResources();
    Bitmap[] bitmaps = new Bitmap[ids.length];
    for (int index = 0; index < ids.length; index++) {
      bitmaps[index] = BitmapFactory.decodeResource(resources, ids[index]);
    }

    return bitmaps;
  }
  public static Bitmap decodeResource(Context context, @DrawableRes int resource){
    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
    int density = context.getResources().getDisplayMetrics().densityDpi;
    bitmap.setDensity(density);
    return bitmap;
  }

}
