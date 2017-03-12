package com.mahsum.puzzle;

import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static com.mahsum.puzzle.utility.Util.joinPuzzlePieces;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.junit.Before;
import org.junit.Test;

public class FourXFourPuzzleTest {

  private Bitmap image;

  @Before
  public void setUp() throws Exception {
    image = BitmapFactory.decodeFile(Application.getImagesRootDir() + "/harput.png");
  }

  @Test
  public void testCreatePuzzle_MultipleDimensions() throws Exception {
    testCreatePuzzle(1600, 1600);
    testCreatePuzzle(900, 800);
    testCreatePuzzle(1240, 1880);
  }

  @Test
  public void testCreatePuzzle_SouldFail() throws Exception {
    try {
      testCreatePuzzle(899, 899);
      fail("Puzzle with a inappropriate image should be throwed");
    } catch (RuntimeException e) {
    }
  }

  private void testCreatePuzzle(int width, int height) {
    //setup
    Puzzle puzzle = new Puzzle(Type.Four_X_Four);
    image = Bitmap.createScaledBitmap(image, width, height, false);
    puzzle.setImage(image);

    //exercise
    Piece[] pieces = puzzle.createPuzzle();

    //assert
    assertBitmapsEquals(image, joinPuzzlePieces(puzzle, pieces));
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
    Puzzle puzzle = new Puzzle(Type.Four_X_Four);
    for (int i = 0; i < indixes.length; i++) {
      assertEquals(type, puzzle.getPieceType(indixes[i]));
    }
  }
}
