package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mahsum.puzzle.PuzzleFactory.TYPE;

public class Puzzle {
  private Bitmap originalBitmap;
  private TYPE type;
  private Bitmap[] pieces;

  public Puzzle() {
  }

  public void addPiece(Bitmap bitmap) {

  }

  public void setOriginalBitmap(Bitmap originalBitmap) {
    this.originalBitmap = originalBitmap;
  }

  public void setType(TYPE type) {
    this.type = type;
  }

  public void createPuzzle() {
    if (type == TYPE.FOUR_X_FOUR) createFour_x_FourPuzzle();
  }

  private void createFour_x_FourPuzzle() {
    final String MASK_DIR = BuildConfig.IMAGES_ROOT_DIR + "/masks";
    Bitmap[] masks = readPieces(MASK_DIR);
    Bitmap[] pieces =  maskOriginalImage(masks);
  }

  private Bitmap[] maskOriginalImage(Bitmap[] masks) {
    return new Bitmap[1];
  }

  private Bitmap[] readPieces(String dir) {
    Bitmap[] pieces = new Bitmap[9];
    for (int i = 0; i < 9 ; i++){
      pieces[i] = BitmapFactory.decodeFile(dir + "/" + String.valueOf(i) + ".png");
    }
    return pieces;
  }

  public Bitmap[] getPieces() {
    return pieces;
  }
}
