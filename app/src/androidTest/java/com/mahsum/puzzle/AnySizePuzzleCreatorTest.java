package com.mahsum.puzzle;

import static com.mahsum.puzzle.Saving.saveBitmap;
import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static com.mahsum.puzzle.utility.Util.joinPuzzlePieces;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import com.mahsum.puzzle.core.Piece;
import com.mahsum.puzzle.core.Puzzle;
import com.mahsum.puzzle.core.PuzzleBuilder;
import com.mahsum.puzzle.core.Type;
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
    Puzzle.setImage(image);
    image = Puzzle.getImage();

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
    PuzzleBuilder puzzleBuilder = new PuzzleBuilder(Type.Three_X_Three);

    assertEquals(Piece.TOP_LEFT, puzzleBuilder.puzzle.type.getPieceType(0));
    assertEquals(Piece.TOP, puzzleBuilder.puzzle.type.getPieceType(1));
    assertEquals(Piece.TOP_RIGHT, puzzleBuilder.puzzle.type.getPieceType(2));
    assertEquals(Piece.LEFT, puzzleBuilder.puzzle.type.getPieceType(3));
    assertEquals(Piece.CENTER, puzzleBuilder.puzzle.type.getPieceType(4));
    assertEquals(Piece.RIGHT, puzzleBuilder.puzzle.type.getPieceType(5));
    assertEquals(Piece.BOTTOM_LEFT, puzzleBuilder.puzzle.type.getPieceType(6));
    assertEquals(Piece.BOTTOM, puzzleBuilder.puzzle.type.getPieceType(7));
    assertEquals(Piece.BOTTOM_RIGHT, puzzleBuilder.puzzle.type.getPieceType(8));
    assertEquals(-1, puzzleBuilder.puzzle.type.getPieceType(-1));
    assertEquals(-1, puzzleBuilder.puzzle.type.getPieceType(10));
  }


  @Test
  public void testPieceType_4X4Puzzle() throws Exception {
    assertPiecesEquals(Piece.TOP_LEFT, 0);
    assertPiecesEquals(Piece.TOP, 1, 2);
    assertPiecesEquals(Piece.TOP_RIGHT, 3);
    assertPiecesEquals(Piece.LEFT, 4, 8);
    assertPiecesEquals(Piece.CENTER, 5, 6, 9, 10);
    assertPiecesEquals(Piece.RIGHT, 7, 11);
    assertPiecesEquals(Piece.BOTTOM_LEFT, 12);
    assertPiecesEquals(Piece.BOTTOM, 13, 14);
    assertPiecesEquals(Piece.BOTTOM_RIGHT, 15);
  }

  private void assertPiecesEquals(int type, int... indixes) {
    PuzzleBuilder puzzleBuilder = new PuzzleBuilder(Type.Four_X_Four);
    for (int i = 0; i < indixes.length; i++) {
      assertEquals(type, puzzleBuilder.puzzle.type.getPieceType(indixes[i]));
    }
  }
}
