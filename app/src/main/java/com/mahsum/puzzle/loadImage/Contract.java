package com.mahsum.puzzle.loadImage;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;

public interface Contract {
    interface View{
        void showImage(Bitmap image);
        void startActivityForResult(Intent chooser, int pick_photo);
        ContentResolver getContentResolver();
    }

    interface Presenter{
        void startImageChoosing();
        void readSelectedImage(int requestCode, int resultCode, Intent data);
    }
}
