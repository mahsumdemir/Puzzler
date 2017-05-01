package com.mahsum.puzzle.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import com.mahsum.puzzle.Application;

public class PuzzleBuilder {

  private static final String TAG = "PuzzleBuilder";
  private static final Paint paint;

  static {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  public Puzzle puzzle;

  private PuzzleBuilder(){
    puzzle = new Puzzle();
  }

  public PuzzleBuilder setImage(Bitmap image){
    puzzle.setImage(image);
    return this;
  }

  public PuzzleBuilder setType(Type type){
    puzzle.setType(type);
    return this;
  }

  public Puzzle build() {
    Bitmap originalImage = puzzle.getImage();
    Type type = puzzle.getType();
    int maskX = originalImage.getWidth() / type.getXPieceNumber();
    int maskY = originalImage.getHeight() / type.getYPieceNumber();
    BitmapMask[] masks = createMasks(maskX, maskY);
    Bitmap surroundedImage = surroundImage(originalImage, masks[0].getAdditionSizeX(), masks[0].getAdditionSizeY());
    Piece[] pieces = new Piece[type.getPieceNumber()];

    int currentX = 0;
    int currentY = 0;
    for (int index = 0; index < type.getPieceNumber(); index++) {
      Log.d(TAG, String.format("iterating for currentX: %d, currentY: %d", currentX, currentY));

      BitmapMask mask = masks[puzzle.type.getPieceType(index)];
      pieces[index] = new Piece(index);
      pieces[index].setMask(mask);
      pieces[index].setBitmap(maskImage(surroundedImage, mask, currentX, currentY));

      currentX += maskX;
      if (index % type.getXPieceNumber() == type.getXPieceNumber() - 1) {
        currentX = 0;
        currentY += maskY;
      }
    }

    puzzle.setPieces(pieces);
    return puzzle;
  }

  public static PuzzleBuilder start(){ return new PuzzleBuilder();}


  private static Bitmap surroundImage(Bitmap image, int x, int y) {
    Bitmap surroundedImage = Bitmap
        .createBitmap(image.getWidth() + 2 * x, image.getHeight() + 2 * y, Bitmap.Config.ARGB_8888);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Canvas canvas = new Canvas(surroundedImage);
    canvas.drawBitmap(image, x, y, paint);
    return surroundedImage;
  }

  private static Bitmap maskImage(Bitmap image, BitmapMask mask, int currentX, int currentY) {
    Canvas canvas = new Canvas(mask.getBitmap());
    Bitmap partOfSource = Bitmap
        .createBitmap(image, currentX, currentY, mask.getWidth(), mask.getHeight());
    canvas.drawBitmap(partOfSource, 0, 0, paint);
    return mask.getBitmap().copy(Bitmap.Config.ARGB_8888, false);
  }

  private static BitmapMask[] createMasks(int x, int y) {
    BitmapMask mask = new BitmapMask(x, y);
    int realX = mask.getWidth();
    int realY = mask.getHeight();

    BitmapMask[] masks = new BitmapMask[9];
    for (int index = 0; index < masks.length; index++) {
      Bitmap aMask = Application.getMask(index);
      aMask = Bitmap.createScaledBitmap(aMask, realX, realY, false);
      masks[index] = new BitmapMask(aMask);
    }
    return masks;
  }

}
