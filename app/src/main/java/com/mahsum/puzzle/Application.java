package com.mahsum.puzzle;

import android.os.Environment;

/**
 * Created by mahsum on 12.03.2017.
 */

public class Application extends android.app.Application {

  private static String imagesRootDir;
  @Override
  public void onCreate() {
    super.onCreate();
    imagesRootDir = Environment.getExternalStorageDirectory().toString() + "/puzzle";
  }

  public static String getImagesRootDir(){ return imagesRootDir; }

}
