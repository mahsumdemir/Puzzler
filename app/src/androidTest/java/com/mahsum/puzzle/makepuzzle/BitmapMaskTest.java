package com.mahsum.puzzle.makepuzzle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;

import com.mahsum.puzzle.R;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BitmapMaskTest {
    private Bitmap bitmap;
    @Before
    public void setUp() throws Exception {
        bitmap = BitmapFactory.decodeResource(InstrumentationRegistry.getContext().getResources(),
                R.drawable.harput);

    }

    @Test
    public void testMaskTopLeft() throws Exception {


    }
}