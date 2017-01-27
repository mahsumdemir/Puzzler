package com.mahsum.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;

import com.mahsum.puzzle.R.drawable;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import static org.junit.Assert.*;

public class SavingTest {
    private Context context;
    private Bitmap bitmap;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void saveBitmap() throws Exception {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.harput);

        String storage = Environment.getExternalStorageDirectory().toString();
        String fullFilePath = storage + "/test/testBitmap3";
        //exercise
        Saving.saveBitmap(bitmap, fullFilePath);

        //assert
        assertTrue("Bitmap file could not found", new File(fullFilePath).exists());
    }
}