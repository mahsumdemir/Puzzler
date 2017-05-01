package com.mahsum.puzzle;

import static com.mahsum.puzzle.Saving.saveBitmap;
import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static com.mahsum.puzzle.utility.Util.joinPuzzlePieces;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import org.junit.Before;
import org.junit.Test;

public class AnySizePuzzleCreatorTest {

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void testCreatePuzzle_WithDifferentPieceNumbers_MultipleTests() throws Exception {
    testCreatePuzzle(new Type(10, 10), 1000, 1000);
    testCreatePuzzle(new Type(7, 7), 2000, 2000);
    testCreatePuzzle(new Type(2, 2), 2000, 2000);
  }

  private void testCreatePuzzle(Type type, int width, int height)
      throws FileCouldNotSaved, FileCouldNotCreated {
    //setup
    Context mTargetContext = InstrumentationRegistry.getTargetContext();
    Bitmap image = BitmapFactoryWrapper.decodeResource(mTargetContext, R.drawable.harput);

    PuzzleCreator puzzleCreator = new PuzzleCreator(type);
    image = Bitmap.createScaledBitmap(image, width, height, false);
    puzzleCreator.setImage(image);
    image = puzzleCreator.getImage();

    //exercise
    Piece[] pieces = puzzleCreator.createPuzzle();

    assertBitmapsEquals(image, joinPuzzlePieces(puzzleCreator, pieces));
  }


  private void savePieces(Piece[] pieces, String dir)
      throws FileCouldNotSaved, FileCouldNotCreated {
    for (int index = 0; index < pieces.length; index++) {
      saveBitmap(pieces[index].getBitmap(), dir + "/" + String.valueOf(index) + ".png");
    }
  }

  @Test
  public void testPieceType_3X3Puzzle() throws Exception {
    //setup
    PuzzleCreator puzzleCreator = new PuzzleCreator(Type.Three_X_Three);

    assertEquals(PuzzleCreator.TOP_LEFT, puzzleCreator.getPieceType(0));
    assertEquals(PuzzleCreator.TOP, puzzleCreator.getPieceType(1));
    assertEquals(PuzzleCreator.TOP_RIGHT, puzzleCreator.getPieceType(2));
    assertEquals(PuzzleCreator.LEFT, puzzleCreator.getPieceType(3));
    assertEquals(PuzzleCreator.CENTER, puzzleCreator.getPieceType(4));
    assertEquals(PuzzleCreator.RIGHT, puzzleCreator.getPieceType(5));
    assertEquals(PuzzleCreator.BOTTOM_LEFT, puzzleCreator.getPieceType(6));
    assertEquals(PuzzleCreator.BOTTOM, puzzleCreator.getPieceType(7));
    assertEquals(PuzzleCreator.BOTTOM_RIGHT, puzzleCreator.getPieceType(8));
    assertEquals(-1, puzzleCreator.getPieceType(-1));
    assertEquals(-1, puzzleCreator.getPieceType(10));
  }


  @Test
  public void testPieceType_4X4Puzzle() throws Exception {
    assertPiecesEquals(PuzzleCreator.TOP_LEFT, 0);
    assertPiecesEquals(PuzzleCreator.TOP, 1, 2);
    assertPiecesEquals(PuzzleCreator.TOP_RIGHT, 3);
    assertPiecesEquals(PuzzleCreator.LEFT, 4, 8);
    assertPiecesEquals(PuzzleCreator.CENTER, 5, 6, 9, 10);
    assertPiecesEquals(PuzzleCreator.RIGHT, 7, 11);
    assertPiecesEquals(PuzzleCreator.BOTTOM_LEFT, 12);
    assertPiecesEquals(PuzzleCreator.BOTTOM, 13, 14);
    assertPiecesEquals(PuzzleCreator.BOTTOM_RIGHT, 15);
  }

  private void assertPiecesEquals(int type, int... indixes) {
    PuzzleCreator puzzleCreator = new PuzzleCreator(Type.Four_X_Four);
    for (int i = 0; i < indixes.length; i++) {
      assertEquals(type, puzzleCreator.getPieceType(indixes[i]));
    }
  }
}
