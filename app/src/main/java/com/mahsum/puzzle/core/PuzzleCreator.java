package com.mahsum.puzzle.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;
import com.mahsum.puzzle.Application;

public class PuzzleCreator {

  private static final String TAG = "PuzzleCreator";
  private static final Paint paint;

  static {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  private Puzzle puzzle = new Puzzle();
  private Bitmap[] pieces;


  public PuzzleCreator(Type type) {
    Puzzle.type = type;
  }

  public Piece[] createPuzzle() {
    int maskX = Puzzle.image.getWidth() / Puzzle.type.getXPieceNumber();
    int maskY = Puzzle.image.getHeight() / Puzzle.type.getYPieceNumber();
    BitmapMask[] masks = createMasks(maskX, maskY);
    Bitmap image = surroundImage(masks[0].getAdditionSizeX(), masks[0].getAdditionSizeY());
    Piece[] pieces = new Piece[Puzzle.type.getPieceNumber()];

    int currentX = 0;
    int currentY = 0;
    for (int index = 0; index < Puzzle.type.getPieceNumber(); index++) {
      Log.d(TAG, String.format("iterating for currentX: %d, currentY: %d", currentX, currentY));

      BitmapMask mask = masks[getPieceType(index)];
      pieces[index] = new Piece();
      pieces[index].setMask(mask);
      pieces[index].setBitmap(maskImage(image, mask, currentX, currentY));

      currentX += maskX;
      if (index % Puzzle.type.getXPieceNumber() == Puzzle.type.getXPieceNumber() - 1) {
        currentX = 0;
        currentY += maskY;
      }
    }

    return pieces;
  }

  private Bitmap surroundImage(int x, int y) {
    Bitmap surroundedImage = Bitmap
        .createBitmap(Puzzle.image.getWidth() + 2 * x, Puzzle.image.getHeight() + 2 * y, Bitmap.Config.ARGB_8888);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Canvas canvas = new Canvas(surroundedImage);
    canvas.drawBitmap(Puzzle.image, x, y, paint);
    return surroundedImage;
  }

  private Bitmap maskImage(Bitmap image, BitmapMask mask, int currentX, int currentY) {
    Canvas canvas = new Canvas(mask.getBitmap());
    Bitmap partOfSource = Bitmap
        .createBitmap(image, currentX, currentY, mask.getWidth(), mask.getHeight());
    canvas.drawBitmap(partOfSource, 0, 0, paint);
    return mask.getBitmap().copy(Bitmap.Config.ARGB_8888, false);
  }

  private void savePieces(BitmapMask[] masks) {
    pieces = new Bitmap[9];
    for (int index = 0; index < masks.length; index++) {
      pieces[index] = masks[index].getBitmap();
    }
  }

  private BitmapMask[] createMasks(int x, int y) {
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

  public int getXPieceNumber() {
    return Puzzle.type.getXPieceNumber();
  }

  public int getYPieceNumber() {
    return Puzzle.type.getYPieceNumber();
  }

  public int getPieceType(int index) {
    if (index < 0 || index > (Puzzle.type.getPieceNumber())) {
      return -1;
    }

    boolean hasLeft = (index % Puzzle.type.getXPieceNumber()) != 0;
    boolean hasRight = (index % Puzzle.type.getXPieceNumber()) != Puzzle.type.getXPieceNumber() - 1;
    boolean hasBottom = (index / Puzzle.type.getYPieceNumber()) != Puzzle.type.getYPieceNumber() - 1;
    boolean hasTop = (index / Puzzle.type.getYPieceNumber() != 0);

    if (!hasLeft && hasRight && hasBottom && !hasTop) {
      return Piece.TOP_LEFT;
    } else if (hasLeft && hasRight && hasBottom && !hasTop) {
      return Piece.TOP;
    } else if (hasLeft && !hasRight && hasBottom && !hasTop) {
      return Piece.TOP_RIGHT;
    } else if (!hasLeft && hasRight && hasBottom && hasTop) {
      return Piece.LEFT;
    } else if (hasLeft && hasRight && hasBottom && hasTop) {
      return Piece.CENTER;
    } else if (hasLeft && !hasRight && hasBottom && hasTop) {
      return Piece.RIGHT;
    } else if (!hasLeft && hasRight && !hasBottom && hasTop) {
      return Piece.BOTTOM_LEFT;
    } else if (hasLeft && hasRight && !hasBottom && hasTop) {
      return Piece.BOTTOM;
    } else if (hasLeft && !hasRight && !hasBottom && hasTop) {
      return Piece.BOTTOM_RIGHT;
    } else {
      return -1;
    }
  }

}
