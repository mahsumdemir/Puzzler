package com.mahsum.puzzle.core;

import android.util.Log;

public class GameBoard {

  public interface GameBoardTracer{
    void init(int[] pieceOrder);
    void swap(int pieceId1, int pieceId2);
  }

  private static final String TAG = "GameBoardActivity";
  private static Puzzle puzzle;
  private static int[] pieceOrder;
  private static GameBoardTracer tracer;

  public GameBoard(Puzzle puzzle, GameBoardTracer tracer) {
    GameBoard.puzzle = puzzle;
    GameBoard.tracer = tracer;
    initBoard();
    tracer.init(pieceOrder);
  }

  private void initBoard() {
    Piece[] pieces = puzzle.getPieces();
    pieceOrder = new int[pieces.length];
    for (int index = 0; index < pieceOrder.length; index++) {
      pieceOrder[index] = index;
    }
  }

  public static void swapPieces(int pieceId1, int pieceId2){
    if (pieceId1 > pieceOrder.length ||
        pieceId2 > pieceOrder.length){
      Log.d(TAG, "Invalid piece id");
    }
    else {
      int temp = pieceOrder[pieceId1];
      pieceOrder[pieceId1] = pieceOrder[pieceId2];
      pieceOrder[pieceId2] = temp;
      tracer.swap(pieceId1, pieceId2);
    }
  }
}
