package com.mahsum.puzzle.ui;


import com.mahsum.puzzle.core.Piece;
import java.util.Random;


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

  public void shuffle(int times) {
    Random random = new Random();
    for (int i = 0; i < times; i++) {
      int index1 = random.nextInt(size());
      int index2 = random.nextInt(size());
      swapContents(index1, index2);
    }
  }

  public int getProgress() {
    int progress = 0;
    for (PieceImageView pieceImageView : list) {
      if (pieceImageView.getPieceArrayIndex() == pieceImageView.getViewArrayIndex()){
        progress++;
      }
    }
    double percent = (double) progress / (double) size();
    return (int) (percent * 100);
  }
}
