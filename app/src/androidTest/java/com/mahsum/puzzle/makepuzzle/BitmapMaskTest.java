package com.mahsum.puzzle.makepuzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;

import com.mahsum.puzzle.R;

import org.junit.Before;
import org.junit.Test;


public class BitmapMaskTest {

  private Bitmap mBitmap;

  @Before
  public void setUp() throws Exception {
    mBitmap = BitmapFactory.decodeResource(InstrumentationRegistry.getContext().getResources(),
        R.drawable.harput);

  }

  @Test
  public void testMaskTopLeft() throws Exception {

  }
}