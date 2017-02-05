package com.mahsum.puzzle.loadImage;

import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by mahsum on 22.10.2016.
 */

public class MainActivityPresenter implements Contract.Presenter {

  private Contract.View view;

  public MainActivityPresenter(Contract.View view) {
    this.view = view;
  }

  @Override
  public void startImageChoosing() {
    final int PICK_PHOTO = 1;
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    view.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PHOTO);
  }

  @Override
  public void readSelectedImage(int requestCode, int resultCode, Intent data) {
    ImageLoader imageLoader = new ImageLoader(view.getContentResolver());
    imageLoader.loadImage(data.getData(), new ImageLoadCallBack() {
      @Override
      public void onImageLoaded(Bitmap image) {
        view.showImage(image);
      }
    });
  }
}
