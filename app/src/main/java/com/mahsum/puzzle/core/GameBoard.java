package com.mahsum.puzzle.core;

import android.util.Log;

public class GameBoard {

  private static final String TAG = "GameBoardActivity";
  private Puzzle puzzle;
  private int[] pieceOrder;

  public GameBoard(Puzzle puzzle) {
    this.puzzle = puzzle;
    initBoard();
  }

  private void initBoard() {
    Piece[] pieces = puzzle.getPieces();
    pieceOrder = new int[pieces.length];
    for (int index = 0; index < pieceOrder.length; index++) {
      pieceOrder[index] = index;
    }
  }

  public void swapPieces(int index1, int index2){
    if (index1 > pieceOrder.length ||
        index2 > pieceOrder.length){
      Log.d(TAG, "Invail piece index");
    }
    else {
      int temp = pieceOrder[index1];
      pieceOrder[index1] = pieceOrder[index2];
      pieceOrder[index2] = temp;
    }
  }
}
