package com.mahsum.puzzle;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import org.junit.Before;
import org.junit.Test;

public class BitmapMaskTest {

  private Context targetContext;
  @Before
  public void setUp() throws Exception {
    targetContext = InstrumentationRegistry.getTargetContext();
  }

  @Test
  public void testCreateBitmapMask_MultipleTests() throws Exception {
    testCreateBitmapMask(500, 700);
    testCreateBitmapMask(300, 420);
    testCreateBitmapMask(301, 420);
    testCreateBitmapMask(299, 413);
  }

  @Test
  public void testCreateBitmapMask_FromBitmap() throws Exception {
    Bitmap bitmap = BitmapFactoryWrapper.decodeResource(targetContext, R.drawable.zero);
    if (bitmap == null) {
      fail(String.format("Error while reading bitmap from resources"));
    }
    BitmapMask mask = new BitmapMask(bitmap);
    assertEquals(bitmap.getHeight(), mask.getHeight());
    assertEquals(bitmap.getWidth(), mask.getWidth());
  }

  private void testCreateBitmapMask(int size, int realSize) {
    BitmapMask mask = new BitmapMask(size, size);

    assert mask.getBitmap() != null;
    assertEquals(realSize, mask.getWidth());
    assertEquals(realSize, mask.getHeight());
  }

  @Test
  public void testRealSize_MultipleTests() throws Exception {
    testRealSize(500, 500);
    testRealSize(301, 300);
    testRealSize(299, 295);
  }

  private void testRealSize(int size, int expected) {
    BitmapMask mask = new BitmapMask(size, size);

    assertEquals(expected, mask.getMaskX());
    assertEquals(expected, mask.getMaskY());
  }

  @Test
  public void testAdditionSize_MultipleTests() throws Exception {
    testAdditionSize(500, 100);
    testAdditionSize(299, 59);
    testAdditionSize(301, 60);
  }

  private void testAdditionSize(int size, int additionSize) {
    BitmapMask mask = new BitmapMask(size, size);

    assertEquals(additionSize, mask.getAdditionSizeX());
    assertEquals(additionSize, mask.getAdditionSizeY());
  }

  @Test
  public void testValuesMatches_MultipleTests() throws Exception {
    testValuesMatches(new BitmapMask(500, 500));
    testValuesMatches(new BitmapMask(299, 299));
    testValuesMatches(new BitmapMask(301, 301));

  }

  private void testValuesMatches(BitmapMask mask) {
    assertEquals(mask.getMaskX() + 2 * mask.getAdditionSizeX(), mask.getWidth());
    assertEquals(mask.getMaskY() + 2 * mask.getAdditionSizeY(), mask.getHeight());
  }
}
