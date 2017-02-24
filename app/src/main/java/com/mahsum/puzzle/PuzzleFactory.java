package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.util.SparseArray;
import com.mahsum.puzzle.makepuzzle.BitmapMask;
import com.mahsum.puzzle.makepuzzle.BitmapMask.KEY;

public class PuzzleFactory {
  public static Puzzle createPuzzle(Bitmap image, SparseArray<BitmapMask> allMasks, final TYPE PUZZLE_TYPE){
    int pieceCount = PUZZLE_TYPE.getTotalPieceNumber();
    int height = image.getHeight() / PUZZLE_TYPE.getY_PIECE_NUMBER();
    int width = image.getWidth() / PUZZLE_TYPE.getX_PIECE_NUMBER();
    Puzzle puzzle = new Puzzle(pieceCount);

    return puzzle;
  }


  public static final class TYPE {
    public static final TYPE FOUR_X_FOUR = new TYPE(4, 4);
    private int X_PIECE_NUMBER;
    private int Y_PIECE_NUMBER;

    private TYPE(){

    }

    private TYPE(final int X_PIECE_NUMBER, final int Y_PIECE_NUMBER){
      setX_PIECE_NUMBER(X_PIECE_NUMBER);
      setY_PIECE_NUMBER(Y_PIECE_NUMBER);
    }

    public int getX_PIECE_NUMBER() {
      return X_PIECE_NUMBER;
    }

    private void setX_PIECE_NUMBER(int X_PIECE_NUMBER) {
      this.X_PIECE_NUMBER = X_PIECE_NUMBER;
    }

    public int getY_PIECE_NUMBER() {
      return Y_PIECE_NUMBER;
    }

    private void setY_PIECE_NUMBER(int Y_PIECE_NUMBER) {
      this.Y_PIECE_NUMBER = Y_PIECE_NUMBER;
    }

    public int getTotalPieceNumber() {
      return this.X_PIECE_NUMBER * this.Y_PIECE_NUMBER;
    }
  }
}
