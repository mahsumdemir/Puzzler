package com.mahsum.puzzle.utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.StringBuilderPrinter;

import com.mahsum.puzzle.BuildConfig;
import com.mahsum.puzzle.Saving;
import com.mahsum.puzzle.Type;

import org.junit.Test;

import static junit.framework.Assert.fail;
import static junit.framework.Assert.failNotEquals;


public class PuzzleTests {
    @Test
    public void testCreatePuzzle() throws Exception {
        Puzzle puzzle = new Puzzle(Type.Three_X_Three);
        String file = BuildConfig.IMAGES_ROOT_DIR + "/harput_900x900/original.png";
        Bitmap image = BitmapFactory.decodeFile(file);
        if (image == null) fail("Error while decoding file: " + file);
        puzzle.setImage(image);
        Bitmap[] pieces = puzzle.createPuzzle();
        Saving.saveBitmapArray(pieces, BuildConfig.IMAGES_ROOT_DIR + "/harput_900x900");
    }
}
