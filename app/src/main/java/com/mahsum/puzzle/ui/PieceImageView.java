package com.mahsum.puzzle.ui;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.mahsum.puzzle.Piece;

public class PieceImageView extends AppCompatImageView implements OnLongClickListener{
  private Piece piece;
  private int pieceArrayIndex;
  private int viewArrayIndex;

  public PieceImageView(Context context) {
    super(context);
  }

  public void configure(Piece piece, int piecesX, int piecesY, int index){
    this.piece = piece;
    this.pieceArrayIndex = index;
    this.viewArrayIndex = index;

    setId(ImageView.generateViewId());
    setX(piece, piecesX, index);
    setY(piece, piecesY, index);
    setImageBitmap(piece.getBitmap());
    setOnLongClickListener(this);
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

  public void setPiece(Piece piece){
    this.piece = piece;
    setImageBitmap(piece.getBitmap());
  }


  @Override
  public boolean onLongClick(View v) {
    ClipData dragData = ClipData.newPlainText(null, String.valueOf(viewArrayIndex));
    startDrag(dragData, new ImageView.DragShadowBuilder(this), null, 0);
    return true;
  }

  @Override
  public boolean onDragEvent(DragEvent event) {
    // Defines a variable to store the action type for the incoming event
    final int action = event.getAction();

    // Handles each of the expected events
    switch(action) {

      case DragEvent.ACTION_DRAG_STARTED:

        // returns true to indicate that the View can accept the dragged data.
        return true;

      case DragEvent.ACTION_DRAG_ENTERED:

        return true;

      case DragEvent.ACTION_DRAG_LOCATION:

        // Ignore the event
        return true;

      case DragEvent.ACTION_DRAG_EXITED:

        return true;

      case DragEvent.ACTION_DROP:

        ClipData data = event.getClipData();
        String arrayIndex = (String) data.getItemAt(0).getText();
        GameBoard.swapContents(Integer.valueOf(arrayIndex), this.viewArrayIndex);
        // Returns true. DragEvent.getResult() will return true.
        return true;

      case DragEvent.ACTION_DRAG_ENDED:

        // returns true; the value is ignored.
        return true;

      // An unknown action type was received.
      default:
        Log.e("DragDrop Example", "Unknown action type received by OnDragListener.");
        break;
    }

    return false;

  }

  public void setPieceArrayIndex(int pieceArrayIndex) {
    this.pieceArrayIndex = pieceArrayIndex;
  }

  public Piece getPiece() {
    return piece;
  }

  public int getPieceArrayIndex() {
    return pieceArrayIndex;
  }
}
