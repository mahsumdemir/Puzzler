package com.mahsum.puzzle;

import static com.mahsum.puzzle.Saving.saveBitmap;

import android.graphics.Bitmap;
import org.junit.Test;


/**
 * This class is rather a helpful class than test.
 * Making it test able us to run it instantly.
 *
 * This class generates bitmap masks used to create puzzle pieces,
 * and saves them on the devices external storage.
 * Full saving path changes from device to device because
 * external storage location changes.
 * So relative path of masks is EXTERNAL_STORAGE + /puzzle/masks.
 */
public class MaskGeneratorTest {

  private static final MaskGenerator MASK_GENERATOR = new MaskGenerator();
  private static final String MASK_DIR = Application.getImagesRootDir() + "/masks/";

  //@Test
  public void testGenerateMasks() throws Exception {
    saveBitmap(MASK_GENERATOR.createBaseMask(), MASK_DIR + "base.png");
  }

  //@Test
  public void testGenerateAddition() throws Exception {
    saveBitmap(MASK_GENERATOR.createAdditionPart(), MASK_DIR + "addition.png");
  }

  //@Test
  public void testCreateRotatedAddition() throws Exception {
    Bitmap original = MASK_GENERATOR.createAdditionPart();
    Bitmap rotatedBitmap = MASK_GENERATOR.rotateBitmap(original, 270);
    saveBitmap(rotatedBitmap, MASK_DIR + "addition_270.png");
  }

  //@Test
  public void testGenerateTopLeftMask() throws Exception {
    saveBitmap(MASK_GENERATOR.createTopLeftMask(), MASK_DIR + "0.png");
  }

  //@Test
  public void testGenerateTopMask() throws Exception {
    Bitmap topMask = MASK_GENERATOR.createTopMask();
    saveBitmap(topMask, MASK_DIR + "1.png");
  }

  //@Test
  public void testGenerateTopRightMask() throws Exception {
    Bitmap topRight = MASK_GENERATOR.createTopRightMask();
    saveBitmap(topRight, MASK_DIR + "2.png");
  }

  //@Test
  public void testGenerateLeftMask() throws Exception {
    Bitmap left = MASK_GENERATOR.createLeftMask();
    saveBitmap(left, MASK_DIR + "3.png");
  }

  //@Test
  public void testGenerateCenterMask() throws Exception {
    Bitmap center = MASK_GENERATOR.createCenterMask();
    saveBitmap(center, MASK_DIR + "4.png");
  }

  //@Test
  public void testGenerateRightMask() throws Exception {
    Bitmap right = MASK_GENERATOR.createRightMask();
    saveBitmap(right, MASK_DIR + "5.png");
  }

  //@Test
  public void testGenerateBottomLeftMask() throws Exception {
    Bitmap bottomLeft = MASK_GENERATOR.createBottomLeftMask();
    saveBitmap(bottomLeft, MASK_DIR + "6.png");
  }

  //@Test
  public void testGenerateBottomMask() throws Exception {
    Bitmap bottom = MASK_GENERATOR.createBottomMask();
    saveBitmap(bottom, MASK_DIR + "7.png");
  }

  //@Test
  public void testGenerateBottomRightMask() throws Exception {
    Bitmap bottomRight = MASK_GENERATOR.createBottomRightMask();
    saveBitmap(bottomRight, MASK_DIR + "8.png");
  }

}
