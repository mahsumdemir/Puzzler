package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

public class Puzzle {

  public static final int TOP_LEFT = 0;
  public static final int TOP = 1;
  public static final int TOP_RIGHT = 2;
  public static final int LEFT = 3;
  public static final int CENTER = 4;
  public static final int RIGHT = 5;
  public static final int BOTTOM_LEFT = 6;
  public static final int BOTTOM = 7;
  public static final int BOTTOM_RIGHT = 8;
  private static final String TAG = "Puzzle";
  private static final Paint paint;

  static {
    paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  private final Type type;
  private Bitmap image;
  private Bitmap[] pieces;


  public Puzzle(Type type) {
    this.type = type;
  }

  public void setImage(Bitmap image) {
    checkImageSize(image);
    this.image = image.copy(Bitmap.Config.ARGB_8888, true);
  }

  private void checkImageSize(Bitmap image) {
    if (image.getWidth() % (getXPieceNumber() * 5) != 0) {
      throw new RuntimeException("Images width should be a multiple of " +
          String.valueOf(getXPieceNumber() * 5));
    } else if (image.getHeight() % (getYPieceNumber() * 5) != 0) {
      throw new RuntimeException("Images height should be a multiple of" +
          String.valueOf(getYPieceNumber() * 5));
    }
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
}
