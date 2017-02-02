package com.mahsum.puzzle;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;

import com.mahsum.puzzle.utility.Util;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import static org.junit.Assert.*;

public class SavingTest {
    private Context targetContext;
    private Bitmap bitmap;

    @Before
    public void setUp() throws Exception {
        targetContext = InstrumentationRegistry.getTargetContext();
        final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (!Util.grantPermission(targetContext, PERMISSION))
            fail("Error. Could not grand permission: " + PERMISSION);
    }

    @Test
    public void saveBitmap() throws Exception {
        bitmap = BitmapFactory.decodeResource(targetContext.getResources(), R.drawable.harput);
        String storage = Environment.getExternalStorageDirectory().toString();
        String fullFilePath = storage + "/test5/testBitmap3";
        //exercise
        Saving.saveBitmap(bitmap, fullFilePath);
        //assert
        assertTrue("Bitmap file could not found", new File(fullFilePath).exists());
    }
}