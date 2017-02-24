package com.mahsum.puzzle;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;

import com.mahsum.puzzle.utility.Util;

import javax.annotation.Resource;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SavingTest {

  private Context mTargetContext;
  @Resource
  private int image = R.drawable.harput;

  @Before
  public void setUp() throws Exception {
    mTargetContext = InstrumentationRegistry.getTargetContext();
    final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    if (!Util.grantPermission(mTargetContext, PERMISSION)) {
      fail("Error. Could not grand permission: " + PERMISSION);
    }
  }

  @Test
  public void decodeBitmap() throws Exception {
    Bitmap bitmap = BitmapFactory.decodeResource(mTargetContext.getResources(),
        image);
    assertTrue(bitmap != null);
  }

  @Test
  public void saveBitmap() throws Exception {
    //setup
    Bitmap bitmap = BitmapFactory.decodeResource(mTargetContext.getResources(),
        image);
    String storage = Environment.getExternalStorageDirectory().toString();
    String fullFilePath = storage + "/test12/testBitmap3";
    //exercise
    Saving.saveBitmap(bitmap, fullFilePath);
    //assert
    assertTrue(String.format("Bitmap file could not found at location: %s", fullFilePath),
               new File(fullFilePath).exists());
  }

  @Test
  public void testSaveBitmap_saveTwice() throws Exception {
    //setup
    Bitmap bitmap = BitmapFactory.decodeResource(mTargetContext.getResources(),
                                                 image);
    String storage = Environment.getExternalStorageDirectory().toString();
    String fullFilePath = storage + "/test12/testBitmap3";
    Saving.saveBitmap(bitmap, fullFilePath);

    //Exercise
    Saving.saveBitmap(bitmap, fullFilePath);

    //assert
    assertTrue(String.format("Bitmap file could not found at location: %s", fullFilePath),
               new File(fullFilePath).exists());

  }
}