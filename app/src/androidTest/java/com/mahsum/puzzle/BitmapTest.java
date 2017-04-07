package com.mahsum.puzzle;

import static junit.framework.Assert.fail;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import com.mahsum.puzzle.util.Util;
import org.junit.Before;
import org.junit.Test;

public class BitmapTest {

  private Context mTargetContext;

  @Before
  public void setUp() throws Exception {
    mTargetContext = InstrumentationRegistry.getTargetContext();
  }

  @Test
  public void testBitmapsEqual() throws Exception {
    Bitmap bitmap = BitmapFactoryWrapper.decodeResource(mTargetContext,
        R.drawable.harput);
    Bitmap bitmap2 = BitmapFactoryWrapper.decodeResource(mTargetContext,
        R.drawable.harput);
    Util.assertBitmapsEquals(bitmap, bitmap2);
  }

  @Test
  public void testBitmapsEqual_ShouldFail() throws Exception {
    Bitmap bitmap = BitmapFactoryWrapper.decodeResource(mTargetContext,
        R.drawable.harput);
    Bitmap bitmap2 = BitmapFactoryWrapper.decodeResource(mTargetContext,
        R.drawable.harput_modified);
    try {
      Util.assertBitmapsEquals(bitmap, bitmap2);
      fail("Images should be different.");
    } catch (RuntimeException e) {
      //Ignored.
    }
  }
}
