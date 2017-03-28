package com.mahsum.puzzle.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.mahsum.puzzle.Piece;

public class PieceImageView extends AppCompatImageView {
  private Piece piece;
  private int id;


  public PieceImageView(Context context) {
    super(context);
  }

  public void configure(Piece piece, int piecesX, int piecesY, int index){
    this.piece = piece;
    this.id = index;

    setId(ImageView.generateViewId());
    setX(piece, piecesX, index);
    setY(piece, piecesY, index);
    setImageBitmap(piece.getBitmap());
  }

  private void setY(Piece piece, int piecesY, int index){
    int row = index / piecesY;
    int viewY = row * piece.getBitmap().getHeight();
    int yPadding = 2;
    if (row != 0){
      viewY = viewY - (2 * row ) * piece.getAdditionSizeY() + yPadding + 2 * row * yPadding;
    }
    setY(viewY);
  }

  private void setX(Piece piece, int piecesX, int index) {
    int column = index % piecesX;
    int viewX = column * piece.getBitmap().getWidth();
    int xPadding = 2;
    if (column != 0){
      viewX = viewX - (2 * column) * piece.getAdditionSizeX() + 2 * column * xPadding;
    }
    setX(viewX);
  }

  public void scale(double scaleFactor) {
    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
    params.height = (int) (getHeight() * scaleFactor);
    params.width = (int) (getWidth() * scaleFactor);
    setX((float) (getX() * scaleFactor));
    setY((float) (getY() * scaleFactor));
  }
}
