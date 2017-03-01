package com.mahsum.puzzle;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class BitmapMaskTest {

  @Test
  public void testCreateBitmap() throws Exception {
    int size = 500;
    BitmapMask mask = new BitmapMask(size, size);

    assert mask.getBitmap() != null;
    assertEquals(size, mask.getWidth());
    assertEquals(size, mask.getHeight());
  }

  @Test
  public void testRealSize() throws Exception {
    int size = 500;
    int realSize = 700;
    BitmapMask mask = new BitmapMask(size, size);

    assertEquals(realSize, mask.getRealX());
    assertEquals(realSize, mask.getRealY());
  }

  @Test
  public void testAdditionSize() throws Exception {
    int size = 500;
    int additionSize = 100;
    BitmapMask mask = new BitmapMask(size, size);

    assertEquals(additionSize, mask.getAdditionSizeX());
    assertEquals(additionSize, mask.getAdditionSizeY());

  }
}
