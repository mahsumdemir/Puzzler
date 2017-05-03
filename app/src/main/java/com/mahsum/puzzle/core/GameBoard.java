package com.mahsum.puzzle.core;

import android.util.Log;
import java.util.Arrays;
import java.util.Random;

public class GameBoard {

  public static GameBoard current;
  public int id;
  public Puzzle getPuzzle() {
    return puzzle;
  }

  public int[] getPieceOrder() {
    int[] newArray = new int[pieceOrder.length];
    for (int index = 0; index < pieceOrder.length; index++) {
      newArray[index] = pieceOrder[index];
    }
    return newArray;
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

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static void loadBoard(GameBoard gameBoard) {
    Log.d(TAG, "loading game board" + gameBoard.toString());
    GameBoard.current = gameBoard.copy();
  }

  private GameBoard copy() {
    GameBoard gameBoard = new GameBoard(this.puzzle);
    gameBoard.pieceOrder = copyArray(pieceOrder);
    gameBoard.id = this.id;
    return gameBoard;
  }

  private int[] copyArray(int[] pieceOrder) {
    int[] newArray = new int[pieceOrder.length];
    for (int index = 0; index < pieceOrder.length; index++) {
      newArray[index] = pieceOrder[index];
    }
    return newArray;
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
      int index1 = findPieceIndex(pieceId1);
      int index2 = findPieceIndex(pieceId2);

      int temp = pieceOrder[index1];
      pieceOrder[index1] = pieceOrder[index2];
      pieceOrder[index2] = temp;
      tracer.swap(pieceId1, pieceId2);
    }
  }

  private int findPieceIndex(int pieceId1) {
    for (int index = 0; index < pieceOrder.length; index++) {
      if (pieceOrder[index] == pieceId1) return index;
    }
    return 0;
  }

  @Override
  public String toString() {
    return "GameBoard{ " + super.toString() + "id=" + id + ", puzzle=" + puzzle + ", pieceOrder=" + Arrays
        .toString(pieceOrder) + '}';
  }
}
