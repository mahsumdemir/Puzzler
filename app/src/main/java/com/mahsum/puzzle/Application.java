package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.DrawableRes;
import com.mahsum.puzzle.R;
import database.DatabaseInterface;

public class Application extends android.app.Application {

  
  public static Application application;
  private static String imagesRootDir;
  private static Bitmap[] masks;

  @Override
  public void onCreate() {
    super.onCreate();
    application = this;
    imagesRootDir = Environment.getExternalStorageDirectory().toString() + "/puzzle";
    loadMasks();
  }

  private static void loadMasks() {
    @DrawableRes int[] drawables = {R.drawable.zero, R.drawable.one, R.drawable.two,
        R.drawable.three, R.drawable.four, R.drawable.five,
        R.drawable.six, R.drawable.seven, R.drawable.eight};

    masks = BitmapFactoryWrapper.decodeMultipleResource(Application.application.getApplicationContext(),
                                                drawables);

  }

  public static Bitmap getMask(int index){ return masks[index]; }
  
  public static String getImagesRootDir(){ return imagesRootDir; }

}
