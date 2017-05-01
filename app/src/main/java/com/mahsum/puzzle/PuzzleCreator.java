package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

public class PuzzleCreator {

  public static final int TOP_LEFT = 0;
  public static final int TOP = 1;
  public static final int TOP_RIGHT = 2;
  public static final int LEFT = 3;
  public static final int CENTER = 4;
  public static final int RIGHT = 5;
  public static final int BOTTOM_LEFT = 6;
  public static final int BOTTOM = 7;
  public static final int BOTTOM_RIGHT = 8;
  private static final String TAG = "PuzzleCreator";
  private static final Paint paint;

  static {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  private final Type type;
  private Bitmap image;
  private Bitmap[] pieces;


  public PuzzleCreator(Type type) {
    this.type = type;
  }

  public void setImage(Bitmap image) {
    this.image = resizeImage(image);
    this.image = this.image.copy(Bitmap.Config.ARGB_8888, true);
  }

  private Bitmap resizeImage(Bitmap image) {
    int newWidth, newHeight;
    int widthOutGrow = image.getWidth() % (getXPieceNumber() * 5);
    int heightOutGrow = image.getHeight() % (getYPieceNumber() * 5);

    if (widthOutGrow == 0 && heightOutGrow == 0) return image;
    else{
      newWidth = image.getWidth() - widthOutGrow;
      newHeight = image.getHeight() - heightOutGrow;
    }
    image = Bitmap.createScaledBitmap(image, newWidth, newHeight, false);
    return image;
  }

  public Piece[] createPuzzle() {
    int maskX = image.getWidth() / type.getXPieceNumber();
    int maskY = image.getHeight() / type.getYPieceNumber();
    BitmapMask[] masks = createMasks(maskX, maskY);
    Bitmap image = surroundImage(masks[0].getAdditionSizeX(), masks[0].getAdditionSizeY());
    Piece[] pieces = new Piece[type.getPieceNumber()];

    int currentX = 0;
    int currentY = 0;
    for (int index = 0; index < type.getPieceNumber(); index++) {
      Log.d(TAG, String.format("iterating for currentX: %d, currentY: %d", currentX, currentY));

      BitmapMask mask = masks[getPieceType(index)];
      pieces[index] = new Piece();
      pieces[index].setMask(mask);
      pieces[index].setBitmap(maskImage(image, mask, currentX, currentY));

      currentX += maskX;
      if (index % type.getXPieceNumber() == type.getXPieceNumber() - 1) {
        currentX = 0;
        currentY += maskY;
      }
    }

    return pieces;
  }

  private Bitmap surroundImage(int x, int y) {
    Bitmap surroundedImage = Bitmap
        .createBitmap(image.getWidth() + 2 * x, image.getHeight() + 2 * y, Bitmap.Config.ARGB_8888);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Canvas canvas = new Canvas(surroundedImage);
    canvas.drawBitmap(image, x, y, paint);
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
    return type.getXPieceNumber();
  }

  public int getYPieceNumber() {
    return type.getYPieceNumber();
  }

  public int getPieceType(int index) {
    if (index < 0 || index > (type.getPieceNumber())) {
      return -1;
    }

    boolean hasLeft = (index % type.getXPieceNumber()) != 0;
    boolean hasRight = (index % type.getXPieceNumber()) != type.getXPieceNumber() - 1;
    boolean hasBottom = (index / type.getYPieceNumber()) != type.getYPieceNumber() - 1;
    boolean hasTop = (index / type.getYPieceNumber() != 0);

    if (!hasLeft && hasRight && hasBottom && !hasTop) {
      return TOP_LEFT;
    } else if (hasLeft && hasRight && hasBottom && !hasTop) {
      return TOP;
    } else if (hasLeft && !hasRight && hasBottom && !hasTop) {
      return TOP_RIGHT;
    } else if (!hasLeft && hasRight && hasBottom && hasTop) {
      return LEFT;
    } else if (hasLeft && hasRight && hasBottom && hasTop) {
      return CENTER;
    } else if (hasLeft && !hasRight && hasBottom && hasTop) {
      return RIGHT;
    } else if (!hasLeft && hasRight && !hasBottom && hasTop) {
      return BOTTOM_LEFT;
    } else if (hasLeft && hasRight && !hasBottom && hasTop) {
      return BOTTOM;
    } else if (hasLeft && !hasRight && !hasBottom && hasTop) {
      return BOTTOM_RIGHT;
    } else {
      return -1;
    }
  }

  /**
   * Setted image and getted image could have different size.
   * @return The Bitmap this puzzle is working on.
   */
  public Bitmap getImage() {
    return image;
  }
}
