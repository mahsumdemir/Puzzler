package com.mahsum.puzzle.loadImage;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class ImageLoader {

  private ContentResolver mContentResolver;
  private ImageLoadCallBack mCallBack;

  public ImageLoader(ContentResolver contentResolver) {
    mContentResolver = contentResolver;
  }

  public void loadImage(Uri imageUri, ImageLoadCallBack callBack) {
    mCallBack = callBack;
    new CustomAsyncTask().execute(imageUri);
  }

  private class CustomAsyncTask extends AsyncTask<Uri, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(Uri... params) {
      Uri contentUri = params[0];
      try {
        return getBitmap(mContentResolver, contentUri);
      } catch (IOException e) {
        e.printStackTrace();
      }

      return null;
    }

    @Override
    protected void onPostExecute(Bitmap image) {
      mCallBack.onImageLoaded(image);
    }
  }
}