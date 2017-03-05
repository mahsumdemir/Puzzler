package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.mahsum.puzzle.utility.Util;

import org.junit.Before;
import org.junit.Test;

import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static com.mahsum.puzzle.utility.Util.joinPuzzlePieces;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

public class FourXFourPuzzleTest {
    private Puzzle puzzle;
    private Bitmap image;

    @Before
    public void setUp() throws Exception {
        puzzle = new Puzzle(Type.Four_X_Four);
        image = BitmapFactory.decodeFile(BuildConfig.IMAGES_ROOT_DIR + "/harput.png");
    }

    @Test
    public void testCreatePuzzle_4X4_1600x1600() throws Exception {
        //set up
        image = Bitmap.createScaledBitmap(image, 1600, 1600, false);
        puzzle.setImage(image);

        //exercise
        Piece[] pieces = puzzle.createPuzzle();

        //assert
        assertBitmapsEquals(image, joinPuzzlePieces(puzzle, pieces));
    }

    @Test
    public void testCreatePuzzle_4X4_899x899_SouldFail() throws Exception {
        image = Bitmap.createScaledBitmap(image, 899, 899, false);
        try {
            puzzle.setImage(image);
            fail("Puzzle with a inappropriate image should be throwed");
        }catch (RuntimeException e){}

    }

    @Test
    public void testPieceType() throws Exception {
        assertPiecesEquals(Puzzle.TOP_LEFT, 0);
        assertPiecesEquals(Puzzle.TOP, 1, 2);
        assertPiecesEquals(Puzzle.TOP_RIGHT, 3);
        assertPiecesEquals(Puzzle.LEFT, 4, 8);
        assertPiecesEquals(Puzzle.CENTER, 5, 6, 9, 10);
        assertPiecesEquals(Puzzle.RIGHT, 7, 11);
        assertPiecesEquals(Puzzle.BOTTOM_LEFT, 12);
        assertPiecesEquals(Puzzle.BOTTOM, 13, 14);
        assertPiecesEquals(Puzzle.BOTTOM_RIGHT, 15);
    }

    private void assertPiecesEquals(int type, int... indixes) {
        for (int i = 0; i < indixes.length; i++) {
            assertEquals(type, puzzle.getPieceType(indixes[i]));
        }
    }
}
