package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Before;
import org.junit.Test;

import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static com.mahsum.puzzle.utility.Util.joinPuzzlePieces;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;


public class ThreeXThreePuzzleTest {
    private Bitmap image;

    @Before
    public void setUp() throws Exception {
        image = BitmapFactory.decodeFile(BuildConfig.IMAGES_ROOT_DIR + "/harput.png");
        if (image == null) {
            fail("Error while decoding file: " + BuildConfig.IMAGES_ROOT_DIR + "/harput.png");
        }
    }

    @Test
    public void testCreatePuzzle_MultipleResolutions() throws Exception {
        testCreatePuzzle(900, 900);
        testCreatePuzzle(900, 600);
        testCreatePuzzle(600, 900);
        testCreatePuzzle(960, 840);
        testCreatePuzzle(1200, 1200);
    }

    private void testCreatePuzzle(int width, int height) {
        //set up
        Puzzle puzzle = new Puzzle(Type.Three_X_Three);
        image = Bitmap.createScaledBitmap(image, width, height, false);
        puzzle.setImage(image);

        //exercise
        Piece[] pieces = puzzle.createPuzzle();

        //assert
        assertBitmapsEquals(image, joinPuzzlePieces(puzzle, pieces));
    }

    @Test
    public void testCreatePuzzle_3X3_899x899_Image_ShouldFail() throws Exception {
        //setup
        Puzzle puzzle = new Puzzle(Type.Three_X_Three);
        image = Bitmap.createScaledBitmap(image, 899, 899, false);

        //exercise
        try {
            puzzle.setImage(image);
            fail("Creating of puzzle should failed cause of inappropriate size.");
        }catch (RuntimeException e){}
    }

    @Test
    public void testPieceType() throws Exception {
        //setup
        Puzzle puzzle = new Puzzle(Type.Three_X_Three);

        assertEquals(Puzzle.TOP_LEFT, puzzle.getPieceType(0));
        assertEquals(Puzzle.TOP, puzzle.getPieceType(1));
        assertEquals(Puzzle.TOP_RIGHT, puzzle.getPieceType(2));
        assertEquals(Puzzle.LEFT, puzzle.getPieceType(3));
        assertEquals(Puzzle.CENTER, puzzle.getPieceType(4));
        assertEquals(Puzzle.RIGHT, puzzle.getPieceType(5));
        assertEquals(Puzzle.BOTTOM_LEFT, puzzle.getPieceType(6));
        assertEquals(Puzzle.BOTTOM, puzzle.getPieceType(7));
        assertEquals(Puzzle.BOTTOM_RIGHT, puzzle.getPieceType(8));
        assertEquals(-1, puzzle.getPieceType(-1));
        assertEquals(-1, puzzle.getPieceType(10));
    }
}
