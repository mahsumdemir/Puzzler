package com.mahsum.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

public class BitmapFactoryWrapper {

  public static Bitmap decodeFile(String path){
    return BitmapFactory.decodeFile(path);
  }

  public static Bitmap[] decodeMultipleResource(Context context, @DrawableRes int... ids){
    Bitmap[] bitmaps = new Bitmap[ids.length];
    for (int index = 0; index < ids.length; index++) {
      bitmaps[index] = BitmapFactoryWrapper.decodeResource(context, ids[index]);
    }

    return bitmaps;
  }
  public static Bitmap decodeResource(Context context, @DrawableRes int resource){
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;
    options.inMutable = true;
    options.inDensity = context.getResources().getDisplayMetrics().densityDpi;

    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource, options);
    int density = context.getResources().getDisplayMetrics().densityDpi;
    bitmap.setDensity(density);
    return bitmap;
  }

}
