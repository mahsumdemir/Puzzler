package com.mahsum.puzzle.loadImage;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.IOException;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class ImageLoader {
    private ContentResolver contentResolver;
    private ImageLoadCallBack callBack;

    public ImageLoader(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void loadImage(Uri imageUri, ImageLoadCallBack callBack){
        this.callBack = callBack;
        new CustomAsyncTask().execute(imageUri);
    }

    private class CustomAsyncTask extends AsyncTask<Uri, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Uri... params) {
            Uri contentUri = params[0];
            try {
                return getBitmap(contentResolver, contentUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            callBack.onImageLoaded(image);
        }
    }
}