package com.mahsum.puzzle;

import static com.mahsum.puzzle.Saving.saveBitmap;
import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static com.mahsum.puzzle.utility.Util.joinPuzzlePieces;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import org.junit.Before;
import org.junit.Test;

public class AnySizePuzzleTest {

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

    Puzzle puzzle = new Puzzle(type);
    image = Bitmap.createScaledBitmap(image, width, height, false);
    puzzle.setImage(image);
    image = puzzle.getImage();

    //exercise
    Piece[] pieces = puzzle.createPuzzle();

    assertBitmapsEquals(image, joinPuzzlePieces(puzzle, pieces));
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


  @Test
  public void testPieceType_4X4Puzzle() throws Exception {
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
