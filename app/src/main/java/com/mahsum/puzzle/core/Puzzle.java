package com.mahsum.puzzle.core;

import android.graphics.Bitmap;

public class Puzzle {

  private Bitmap image;
  public Type type;
  private Piece[] pieces;

  public void setType(Type type) {
    this.type = type;
  }

  public void setImage(Bitmap image) {
    this.image = resizeImage(image, type.getXPieceNumber(), type.getYPieceNumber());
    this.image = this.image.copy(Bitmap.Config.ARGB_8888, true);
  }

  private static Bitmap resizeImage(Bitmap image, int xPieceNumber, int yPieceNumber) {
    int newWidth, newHeight;
    int widthOutGrow = image.getWidth() % (xPieceNumber * 5);
    int heightOutGrow = image.getHeight() % (yPieceNumber * 5);

    if (widthOutGrow == 0 && heightOutGrow == 0) return image;
    else{
      newWidth = image.getWidth() - widthOutGrow;
      newHeight = image.getHeight() - heightOutGrow;
    }
    image = Bitmap.createScaledBitmap(image, newWidth, newHeight, false);
    return image;
  }

  /**
   * Setted image and getted image could have different size.
   * @return The Bitmap this puzzle is working on.
   */
  public Bitmap getImage() {
    return image;
  }

  public void setPieces(Piece[] pieces) {
    this.pieces = pieces;
  }

  public Type getType() {
    return type;
  }

  public Piece[] getPieces() {
    return pieces;
  }

  public Piece getPieceAt(int index) {
    return pieces[index];
  }
}
