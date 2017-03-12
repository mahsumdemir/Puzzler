package com.mahsum.puzzle;

import static com.mahsum.puzzle.Saving.saveBitmap;
import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static com.mahsum.puzzle.utility.Util.joinPuzzlePieces;
import static junit.framework.Assert.fail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import org.junit.Before;
import org.junit.Test;

public class AnySizePuzzleTest {

  private Bitmap image;

  @Before
  public void setUp() throws Exception {
    image = BitmapFactory.decodeFile(Application.getImagesRootDir() + "/harput.png");
    if (image == null) {
      fail("Needed image file is null");
    }
  }

  @Test
  public void testCreatePuzzle_WithDifferentPieceNumbers_MultipleTests() throws Exception {
    testCreatePuzzle(new Type(10, 10), 1000, 1000);
    testCreatePuzzle(new Type(20, 20), 2000, 2000);
    testCreatePuzzle(new Type(2, 2), 2000, 2000);
  }

  private void testCreatePuzzle(Type type, int width, int height)
      throws FileCouldNotSaved, FileCouldNotCreated {
    //setup
    Puzzle puzzle = new Puzzle(type);
    image = Bitmap.createScaledBitmap(image, width, height, false);
    puzzle.setImage(image);

    //exercise
    Piece[] pieces = puzzle.createPuzzle();

    //assert
    String dir = "/harput_" + String.valueOf(puzzle.getXPieceNumber()) +
        "x" + String.valueOf(puzzle.getYPieceNumber()) + "_" +
        String.valueOf(width) + "x" + String.valueOf(height);

    savePieces(pieces, Application.getImagesRootDir() + dir);
    assertBitmapsEquals(image, joinPuzzlePieces(puzzle, pieces));
  }


  private void savePieces(Piece[] pieces, String dir)
      throws FileCouldNotSaved, FileCouldNotCreated {
    for (int index = 0; index < pieces.length; index++) {
      saveBitmap(pieces[index].getBitmap(), dir + "/" + String.valueOf(index) + ".png");
    }
  }
}
