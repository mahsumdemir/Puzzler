package com.mahsum.puzzle;

import static com.mahsum.puzzle.Saving.saveBitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import org.junit.Test;

public class MaskGenerator {
  private static final String MASK_DIR = BuildConfig.IMAGES_ROOT_DIR + "/masks/";
  private static final int MASK_COLOR = Color.GREEN;
  private static final Paint XOR_PORTER, SRC_PORTER;

  static {
    XOR_PORTER = new Paint(Paint.ANTI_ALIAS_FLAG);
    SRC_PORTER = new Paint(Paint.ANTI_ALIAS_FLAG);

    XOR_PORTER.setXfermode(new PorterDuffXfermode(Mode.XOR));
    SRC_PORTER.setXfermode(new PorterDuffXfermode(Mode.SRC));
  }

  private static final Rect BOTTOM_IN = new Rect(30, 50, 40, 60);
  private static final Rect LEFT_IN = new Rect(50, 30, 60, 40);
  private static final Rect LEFT_OUT = new Rect(0, 30, 10, 40);
  private static final Rect TOP_OUT = new Rect(30, 0, 40, 10);
  @Test
  public void testGenerateMasks() throws Exception {
    saveBitmap(createBaseMask(), MASK_DIR + "base.png");
  }

  private Bitmap createBaseMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap base = Bitmap.createBitmap(70, 70, Config.ARGB_8888);
    setColorToPixels(base, 0, 0, 70, 70, Color.TRANSPARENT);
    setColorToPixels(base, 10, 10, 60, 60, MASK_COLOR);
    return base;
  }

  @Test
  public void testGenerateAddition() throws Exception {
    saveBitmap(createAdditionPart(), MASK_DIR + "addition.png");
  }

  private Bitmap createAdditionPart() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap addition = Bitmap.createBitmap(10, 10, Config.ARGB_8888);
    setColorToPixels(addition, 0, 0, 10, 10, Color.TRANSPARENT);
    setColorToPixels(addition, 2, 0, 8, 4, MASK_COLOR);
    setColorToPixels(addition, 0, 4, 10, 8, MASK_COLOR);
    setColorToPixels(addition, 4, 8, 6, 10, MASK_COLOR);
    return addition;
  }

  @Test
  public void testCreateRotatedAddition() throws Exception {
    Bitmap original = createAdditionPart();
    saveBitmap(rotateBitmap(original, 270), MASK_DIR + "addition_270.png");
  }

  private Bitmap rotateBitmap(Bitmap original, int degree) throws FileCouldNotCreated, FileCouldNotSaved {
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    return Bitmap.createBitmap(original, 0, 0, original.getWidth(),
                               original.getHeight(), matrix, true);
  }

  @Test
  public void testGenerateTopLeftMask() throws Exception {
    saveBitmap(createTopLeftMask(), MASK_DIR + "0.png");
  }

  @NonNull
  private Bitmap createTopLeftMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap topLeft = createBaseMask();
    Bitmap bottomAddition = createAdditionPart();
    Canvas canvas = new Canvas(topLeft);
    canvas.drawBitmap(bottomAddition, null, BOTTOM_IN, XOR_PORTER);

    Bitmap rightAddition = rotateBitmap(createAdditionPart(), 270);
    canvas.drawBitmap(rightAddition, null, LEFT_IN, XOR_PORTER);
    return topLeft;
  }

  @Test
  public void testGenerateTopMask() throws Exception {
    Bitmap topMask = createTopMask();
    saveBitmap(topMask, MASK_DIR + "1.png");
  }

  @NonNull
  private Bitmap createTopMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap topMask = createTopLeftMask();
    Bitmap leftAddition = createAdditionPart();
    leftAddition = rotateBitmap(leftAddition, 270);
    Canvas canvas = new Canvas(topMask);
    canvas.drawBitmap(leftAddition, null, LEFT_OUT, SRC_PORTER);
    return topMask;
  }

  @Test
  public void testGenerateTopRightMask() throws Exception {
    Bitmap topRight = createTopRightMask();
    saveBitmap(topRight, MASK_DIR + "2.png");
  }

  @NonNull
  private Bitmap createTopRightMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap topRight = createBaseMask();
    Bitmap addition = createAdditionPart();
    Bitmap leftAddition = rotateBitmap(addition, 270);
    Canvas canvas = new Canvas(topRight);
    canvas.drawBitmap(addition, null, BOTTOM_IN, XOR_PORTER);
    canvas.drawBitmap(leftAddition, null, LEFT_OUT, SRC_PORTER);
    return topRight;
  }

  @Test
  public void testGenerateLeftMask() throws Exception {
    Bitmap left = createTopLeftMask();
    Bitmap addition = createAdditionPart();
    Canvas canvas = new Canvas(left);
    canvas.drawBitmap(addition, null, TOP_OUT, SRC_PORTER);
    saveBitmap(left, MASK_DIR + "3.png");
  }

  @Test
  public void testGenerateCenterMask() throws Exception {
    Bitmap center = createTopMask();
    Bitmap addition = createAdditionPart();
    Canvas canvas = new Canvas(center);
    canvas.drawBitmap(addition, null, TOP_OUT, SRC_PORTER);
    saveBitmap(center, MASK_DIR + "4.png");
  }

  @Test
  public void testGenerateRightMask() throws Exception {
    Bitmap right = createTopRightMask();
    Bitmap addition = createAdditionPart();
    Canvas canvas = new Canvas(right);
    canvas.drawBitmap(addition, null, TOP_OUT, SRC_PORTER);
    saveBitmap(right, MASK_DIR + "5.png");
  }

  @Test
  public void testGenerateBottomLeftMask() throws Exception {
    Bitmap bottomLeft = createBottomLeftMask();
    saveBitmap(bottomLeft, MASK_DIR + "6.png");
  }

  @NonNull
  private Bitmap createBottomLeftMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap bottomLeft = createBaseMask();
    Bitmap addition = createAdditionPart();
    Bitmap rightAddition = rotateBitmap(createAdditionPart(), 270);
    Canvas canvas = new Canvas(bottomLeft);
    canvas.drawBitmap(addition, null, TOP_OUT, SRC_PORTER);
    canvas.drawBitmap(rightAddition, null, LEFT_IN, XOR_PORTER);
    return bottomLeft;
  }

  @Test
  public void testGenerateBottomMask() throws Exception {
    Bitmap bottom = createBottomLeftMask();
    Bitmap leftAddition = rotateBitmap(createAdditionPart(), 270);
    Canvas canvas = new Canvas(bottom);
    canvas.drawBitmap(leftAddition, null, LEFT_OUT, SRC_PORTER);
    saveBitmap(bottom, MASK_DIR + "7.png");
  }

  @Test
  public void testGenerateBottomRightMask() throws Exception {
    Bitmap bottomRight = createBaseMask();
    Bitmap leftAddition = rotateBitmap(createAdditionPart(), 270);
    Bitmap topAddition = createAdditionPart();

    Canvas canvas = new Canvas(bottomRight);
    canvas.drawBitmap(leftAddition, null, LEFT_OUT, SRC_PORTER);
    canvas.drawBitmap(topAddition, null, TOP_OUT, SRC_PORTER);
    saveBitmap(bottomRight, MASK_DIR + "8.png");
  }

  private void setColorToPixels(Bitmap base, int x1, int y1, int x2, int y2, int color) {
    for (int x = x1; x < x2; x++) {
      for (int y = y1; y < y2; y++){
        base.setPixel(x, y, color);
      }
    }
  }
}
