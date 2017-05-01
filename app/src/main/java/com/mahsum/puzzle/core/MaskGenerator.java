package com.mahsum.puzzle.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;

public class MaskGenerator {

  public static final int WIDTH = 70;
  public static final int HEIGHT = 70;
  public static final int BASE_START_X = 10;
  public static final int BASE_START_Y = 10;
  public static final int BASE_END_X = 60;
  public static final int BASE_END_Y = 60;
  public static final int ADDITION_X = 10;
  public static final int ADDITION_Y = 10;
  private static final int MASK_COLOR = Color.GREEN;
  private static final Paint XOR_PORTER, SRC_PORTER;
  private static final Rect BOTTOM_IN = new Rect(30, 50, 40, 60);
  private static final Rect LEFT_IN = new Rect(50, 30, 60, 40);
  private static final Rect LEFT_OUT = new Rect(0, 30, 10, 40);
  private static final Rect TOP_OUT = new Rect(30, 0, 40, 10);

  static {
    XOR_PORTER = new Paint(Paint.ANTI_ALIAS_FLAG);
    SRC_PORTER = new Paint(Paint.ANTI_ALIAS_FLAG);

    XOR_PORTER.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
    SRC_PORTER.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
  }

  private void setColorToPixels(Bitmap base, int x1, int y1, int x2, int y2, int color) {
    for (int x = x1; x < x2; x++) {
      for (int y = y1; y < y2; y++) {
        base.setPixel(x, y, color);
      }
    }
  }

  public Bitmap createBaseMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap base = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
    setColorToPixels(base, 0, 0, WIDTH, HEIGHT, Color.TRANSPARENT);
    setColorToPixels(base, BASE_START_X, BASE_START_Y, BASE_END_X, BASE_END_Y, MASK_COLOR);
    return base;
  }

  public Bitmap createAdditionPart() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap addition = Bitmap.createBitmap(ADDITION_X, ADDITION_Y, Bitmap.Config.ARGB_8888);
    setColorToPixels(addition, 0, 0, ADDITION_X, ADDITION_Y, Color.TRANSPARENT);
    setColorToPixels(addition, 2, 0, 8, 4, MASK_COLOR);
    setColorToPixels(addition, 0, 4, 10, 8, MASK_COLOR);
    setColorToPixels(addition, 4, 8, 6, 10, MASK_COLOR);
    return addition;
  }

  public Bitmap rotateBitmap(Bitmap original, int degree)
      throws FileCouldNotCreated, FileCouldNotSaved {
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    return Bitmap.createBitmap(original, 0, 0, original.getWidth(),
        original.getHeight(), matrix, true);
  }

  @NonNull
  public Bitmap createTopLeftMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap topLeft = createBaseMask();
    Bitmap bottomAddition = createAdditionPart();
    Canvas canvas = new Canvas(topLeft);
    canvas.drawBitmap(bottomAddition, null, BOTTOM_IN, XOR_PORTER);

    Bitmap rightAddition = rotateBitmap(createAdditionPart(), 270);
    canvas.drawBitmap(rightAddition, null, LEFT_IN, XOR_PORTER);
    return topLeft;
  }

  @NonNull
  public Bitmap createTopMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap topMask = createTopLeftMask();
    Bitmap leftAddition = createAdditionPart();
    leftAddition = rotateBitmap(leftAddition, 270);
    Canvas canvas = new Canvas(topMask);
    canvas.drawBitmap(leftAddition, null, LEFT_OUT, SRC_PORTER);
    return topMask;
  }

  @NonNull
  public Bitmap createTopRightMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap topRight = createBaseMask();
    Bitmap addition = createAdditionPart();
    Bitmap leftAddition = rotateBitmap(addition, 270);
    Canvas canvas = new Canvas(topRight);
    canvas.drawBitmap(addition, null, BOTTOM_IN, XOR_PORTER);
    canvas.drawBitmap(leftAddition, null, LEFT_OUT, SRC_PORTER);
    return topRight;
  }

  @NonNull
  public Bitmap createBottomLeftMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap bottomLeft = createBaseMask();
    Bitmap addition = createAdditionPart();
    Bitmap rightAddition = rotateBitmap(createAdditionPart(), 270);
    Canvas canvas = new Canvas(bottomLeft);
    canvas.drawBitmap(addition, null, TOP_OUT, SRC_PORTER);
    canvas.drawBitmap(rightAddition, null, LEFT_IN, XOR_PORTER);
    return bottomLeft;
  }

  @NonNull
  public Bitmap createBottomRightMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap bottomRight = createBaseMask();
    Bitmap leftAddition = rotateBitmap(createAdditionPart(), 270);
    Bitmap topAddition = createAdditionPart();

    Canvas canvas = new Canvas(bottomRight);
    canvas.drawBitmap(leftAddition, null, LEFT_OUT, SRC_PORTER);
    canvas.drawBitmap(topAddition, null, TOP_OUT, SRC_PORTER);
    return bottomRight;
  }

  @NonNull
  public Bitmap createBottomMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap bottom = createBottomLeftMask();
    Bitmap leftAddition = rotateBitmap(createAdditionPart(), 270);
    Canvas canvas = new Canvas(bottom);
    canvas.drawBitmap(leftAddition, null, LEFT_OUT, SRC_PORTER);
    return bottom;
  }

  @NonNull
  public Bitmap createRightMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap right = createTopRightMask();
    Bitmap addition = createAdditionPart();
    Canvas canvas = new Canvas(right);
    canvas.drawBitmap(addition, null, TOP_OUT, SRC_PORTER);
    return right;
  }

  @NonNull
  public Bitmap createCenterMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap center = createTopMask();
    Bitmap addition = createAdditionPart();
    Canvas canvas = new Canvas(center);
    canvas.drawBitmap(addition, null, TOP_OUT, SRC_PORTER);
    return center;
  }

  @NonNull
  public Bitmap createLeftMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap left = createTopLeftMask();
    Bitmap addition = createAdditionPart();
    Canvas canvas = new Canvas(left);
    canvas.drawBitmap(addition, null, TOP_OUT, SRC_PORTER);
    return left;
  }
}
