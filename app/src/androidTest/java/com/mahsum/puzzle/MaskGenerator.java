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
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import org.junit.Test;

public class MaskGenerator {

  private static final int MASK_COLOR = Color.GREEN;

  @Test
  public void testGenerateMasks() throws Exception {
    saveBitmap(createBaseMask(), "/storage/sdcard/puzzle/masks/base.png");
  }

  private Bitmap createBaseMask() throws FileCouldNotCreated, FileCouldNotSaved {
    Bitmap base = Bitmap.createBitmap(70, 70, Config.ARGB_8888);
    setColorToPixels(base, 0, 0, 70, 70, Color.TRANSPARENT);
    setColorToPixels(base, 10, 10, 60, 60, MASK_COLOR);
    return base;
  }

  @Test
  public void testGenerateAddition() throws Exception {
    saveBitmap(createAdditionPart(), "/storage/sdcard/puzzle/masks/addition.png");
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
    saveBitmap(rotateBitmap(original, 270), "/storage/sdcard/puzzle/masks/addition_270.png");
  }

  private Bitmap rotateBitmap(Bitmap original, int degree) throws FileCouldNotCreated, FileCouldNotSaved {
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    return Bitmap.createBitmap(original, 0, 0, original.getWidth(),
                               original.getHeight(), matrix, true);
  }

  @Test
  public void testGenerateTopLeftMask() throws Exception {
    Bitmap topLeft = createBaseMask();
    Bitmap bottomAddition = createAdditionPart();
    Canvas canvas = new Canvas(topLeft);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setXfermode(new PorterDuffXfermode(Mode.XOR));
    canvas.drawBitmap(bottomAddition, 30, 50, paint);

    Bitmap rightAddition = rotateBitmap(createAdditionPart(), 270);
    canvas.drawBitmap(rightAddition, 50, 30, paint);
    saveBitmap(topLeft, "/storage/sdcard/puzzle/masks/0.png");
  }

  private void setColorToPixels(Bitmap base, int x1, int y1, int x2, int y2, int color) {
    for (int x = x1; x < x2; x++) {
      for (int y = y1; y < y2; y++){
        base.setPixel(x, y, color);
      }
    }
  }
}
