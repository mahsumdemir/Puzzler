package com.mahsum.puzzle.makepuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;

import com.mahsum.puzzle.R;

import com.mahsum.puzzle.makepuzzle.BitmapMask.KEY;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;


public class BitmapMaskTest {

  private Context targetContext;
  private Bitmap mBitmap;
  @Resource private static final int IMAGE = R.drawable.harput;

  @Before
  public void setUp() throws Exception {
    targetContext = InstrumentationRegistry.getTargetContext();
    mBitmap = BitmapFactory.decodeResource(targetContext.getResources(), IMAGE);
  }

  @Test
  public void testMaskTopLeft() throws Exception {
    BitmapMask topLeftMask = BitmapMask.getMask(targetContext, KEY.TOP_LEFT);
    Bitmap maskedBitmap = topLeftMask.maskBitmap(mBitmap, 0, 0);
  }
}