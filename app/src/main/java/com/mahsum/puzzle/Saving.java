package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class Saving {
    private static final String TAG = "SAVING";

    public static void saveBitmap(@NonNull Bitmap bitmap, String fullPath) throws FileCouldNotCreated, FileCouldNotSaved {
        File file = createFile(fullPath);
        save(bitmap, file);
    }

    private static void save(Bitmap bitmap, File file) throws FileCouldNotSaved {
        try {
            FileOutputStream out = new FileOutputStream(file);
            compress(bitmap, out);
            out.flush();
            out.close();

        } catch (Exception ignored) {
            throw new FileCouldNotSaved(file);
        }
    }

    private static void compress(Bitmap bitmap, FileOutputStream out) {
        boolean compressResult = bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        if (!compressResult)
            Log.d(TAG, "Error at compressing bitmap");
    }

    private static File createFile(String fullPath) throws FileCouldNotCreated {
        File file = new File (fullPath);
        if (file.exists ()) {
            Log.d(TAG, "File: %s exist. Deleting it.");
            file.delete();
        }
        makeDirs(file);
        return file;
    }

    private static void makeDirs(File file) throws FileCouldNotCreated {
        try {
            if (!file.createNewFile())
                throw new FileCouldNotCreated(String.format("File: %s Could Not Created", file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
