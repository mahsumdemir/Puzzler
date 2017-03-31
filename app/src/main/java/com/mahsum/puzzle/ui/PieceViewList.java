package com.mahsum.puzzle.ui;


import android.view.View;
import com.mahsum.puzzle.Piece;

class PieceViewList {

  private static PieceImageView[] list;
  
  public PieceViewList(int size) {
    list = new PieceImageView[size];
  }

  public static void swapContents(int index1, int index2) {
    Piece piece1 = list[index1].getPiece();
    int pieceIndex1 = list[index1].getPieceArrayIndex();

    Piece piece2 = list[index2].getPiece();
    int pieceIndex2 = list[index2].getPieceArrayIndex();

    list[index1].setPiece(piece2);
    list[index1].setPieceArrayIndex(pieceIndex2);


    list[index2].setPiece(piece1);
    list[index2].setPieceArrayIndex(pieceIndex1);

  }

  public void add(PieceImageView imageView, int index) {
    list[index] = imageView;
  }

  public void scaleAll(double scaleFactor) {
    for (PieceImageView pieceImageView : list) {
      pieceImageView.scale(scaleFactor);
    }
  }

  public int size() {
    return list.length;
  }

  public PieceImageView get(int index) {
    return list[index];
  }
}
