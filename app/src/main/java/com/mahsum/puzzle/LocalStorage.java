package com.mahsum.puzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.mahsum.puzzle.loadImage.ImageLoadCallBack;
import com.mahsum.puzzle.loadImage.ImageLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class LocalStorage {

    private static final String TAG = "LocalStorage";
    private static Context context;
    private static final ArrayList<Bitmap> bitmaps = new ArrayList<>();

    public static void addSavedPuzzles(Uri uri) {
        if (context == null) return;
        ImageLoader imageLoader = new ImageLoader(context.getContentResolver());
        imageLoader.loadImage(uri, new ImageLoadCallBack() {
            @Override
            public void onImageLoaded(Bitmap image) {
                Log.d(TAG, "Saved Image loaded");
                bitmaps.add(image);
            }
        });
    }

    public static ArrayList<Bitmap> getSavedPuzzles() {
        return bitmaps;
    }

    public static void init(Context context) {
        LocalStorage.context= context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("BITMAP_URIS", Context.MODE_PRIVATE);
        if (sharedPreferences == null) return; //we dont have one yet
        int count = sharedPreferences.getInt("COUNT", 0);

        for (int index = 0; index < count; index++) {
            try {
                String file = sharedPreferences.getString("BITMAP_KEY" + index, null);
                FileInputStream fileInputStream = context.openFileInput(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                bitmaps.add(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void save(){
        saveBitmaps();
        saveSharedPreferences();
        bitmaps.clear();
    }

    private static void saveSharedPreferences() {
        SharedPreferences.Editor editor = context.getSharedPreferences("BITMAP_URIS", Context.MODE_PRIVATE).edit();
        editor.putInt("COUNT", bitmaps.size());
        for (int index = 0; index < bitmaps.size(); index++) {
            editor.putString("BITMAP_KEY" + index, "IMAGE" + index);
        }
        editor.apply();
    }

    private static void saveBitmaps() {
        for(int index = 0; index < bitmaps.size(); index++) {
            try {
                FileOutputStream fileOutputStream = context.openFileOutput("IMAGE" + index, Context.MODE_PRIVATE);
                bitmaps.get(index).compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
