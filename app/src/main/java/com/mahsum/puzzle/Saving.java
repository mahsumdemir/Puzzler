package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Saving {

  private static final String TAG = "SAVING";

  public static void saveBitmap(@NonNull Bitmap bitmap, String fullPath)
      throws FileCouldNotSaved, FileCouldNotCreated {
    saveBitmap(bitmap, fullPath, true);
  }

  public static void saveBitmap(@NonNull Bitmap bitmap, String fullPath, boolean deleteIfExist)
      throws FileCouldNotCreated, FileCouldNotSaved {
    File file = createFile(fullPath, deleteIfExist);
    if (file == null) {
      throw new RuntimeException("File could not created. Hence saving bitmap is failed.");
    } else {
      if (deleteIfExist) save(bitmap, file);
    }
  }

  private static void save(@NonNull Bitmap bitmap, @NonNull File file) throws FileCouldNotSaved {
    try {
      FileOutputStream out = new FileOutputStream(file);
      compress(bitmap, out);
      out.flush();
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void compress(Bitmap bitmap, FileOutputStream out) {
    boolean compressResult = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
    if (!compressResult) {
      Log.d(TAG, "Error at compressing bitmap");
    }
  }

  /**
   * Creates a file at given path. If a file already exist at this path. Replaces it with a new
   * empty file.
   *
   * @param fullPath full path for file
   * @return null if an error occurred while creation file, created file if everything goes well.
   */
  @Nullable
  private static File createFile(String fullPath, boolean deleteIfExist) {
    try {
      createParent(fullPath);
      File file = new File(fullPath);
      if (file.exists() && deleteIfExist) {
        file.delete();
        file.createNewFile();
      }
      return file;
    } catch (FileCouldNotCreated fileCouldNotCreated) {
      fileCouldNotCreated.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static void createParent(String fullPath) throws FileCouldNotCreated {
    File parent = new File(fullPath).getParentFile();
    if (parent.exists()) {
      return;
    }
    if (!parent.mkdirs()) {
      throw new FileCouldNotCreated(parent);
    }
  }

  public static void saveBitmapArray(Bitmap[] array, String dir, boolean deleteIfExist) {
    for (int index = 0; index < array.length; index++) {
      try {
        saveBitmap(array[index], dir + "/" + String.valueOf(index) + ".png", deleteIfExist);
      } catch (FileCouldNotCreated | FileCouldNotSaved error) {
        error.printStackTrace();
      }
    }
  }
}
