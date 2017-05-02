package com.mahsum.puzzle.gameboard;


import com.mahsum.puzzle.core.GameBoard;
import com.mahsum.puzzle.core.Piece;
import com.mahsum.puzzle.gameboard.PieceImageView;
import java.util.Random;


class PieceViewList {

  private static PieceImageView[] list;


  public PieceViewList(int size) {
    list = new PieceImageView[size];
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
      Piece temp = list[index1].getPiece();
      list[index1].setPiece(list[index2].getPiece());
      list[index2].setPiece(temp);
    }
  }

  public int getProgress() {
    int progress = 0;
    for (int index = 0; index < list.length; index++) {
      PieceImageView pieceImageView = list[index];
      if (pieceImageView.getPiece().getId() == index){
        progress++;
      }
    }
    double percent = (double) progress / (double) size();
    return (int) (percent * 100);
  }

  public static PieceImageView findViewById(int pieceId) {
    for (PieceImageView view : list) {
      if (view.getPiece().getId() == pieceId) return view;
    }
    return null;
  }
}
