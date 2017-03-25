package com.mahsum.puzzle.loadImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;


public class MainActivityPresenter implements Contract.Presenter {

  private Contract.View mView;

  public MainActivityPresenter(Contract.View view) {
    mView = view;
  }

  @Override
  public void startImageChoosing() {
    final int PICK_PHOTO = 1;
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    mView.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PHOTO);
  }

  @Override
  public void readSelectedImage(int requestCode, int resultCode, Intent data) {
    ImageLoader imageLoader = new ImageLoader(mView.getContentResolver());
    imageLoader.loadImage(data.getData(), new ImageLoadCallBack() {
      @Override
      public void onImageLoaded(Bitmap image) {
        mView.showImage(image);
      }
    });
  }

  @Override
  public void startGameBoard(Uri bitmap) {

  }
}
