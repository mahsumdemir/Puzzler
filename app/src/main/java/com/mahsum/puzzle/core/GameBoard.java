package com.mahsum.puzzle.core;

import android.util.Log;
import java.util.Arrays;
import java.util.Random;

public class GameBoard {

  public static GameBoard current;

  public Puzzle getPuzzle() {
    return puzzle;
  }

  public int[] getPieceOrder() {
    return pieceOrder;
  }

  public void setPieceOrder(int[] pieceOrder) {
    this.pieceOrder = pieceOrder;
  }

  public void shuffle(int times) {
    Random random = new Random();
    for (int i = 0; i < times; i++) {
      int index1 = random.nextInt(pieceOrder.length);
      int index2 = random.nextInt(pieceOrder.length);
      swapPieces(index1, index2);
    }
  }

  public interface GameBoardTracer{
    void init(int[] pieceOrder);
    void swap(int pieceId1, int pieceId2);
  }

  private static final String TAG = "GameBoardActivity";
  private Puzzle puzzle;
  private int[] pieceOrder;
  private GameBoardTracer tracer;

  public GameBoard(Puzzle puzzle) {
    this.puzzle = puzzle;
    initBoard();
  }

  public void subscribe(GameBoardTracer tracer){
    this.tracer = tracer;
    tracer.init(pieceOrder);
  }

  private void initBoard() {
    Piece[] pieces = puzzle.getPieces();
    pieceOrder = new int[pieces.length];
    for (int index = 0; index < pieceOrder.length; index++) {
      pieceOrder[index] = index;
    }
  }

  public void swapPieces(int pieceId1, int pieceId2){
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

  @Override
  public String toString() {
    return "GameBoard{" + "puzzle=" + puzzle + ", pieceOrder=" + Arrays.toString(pieceOrder) + '}';
  }
}
