package com.mahsum.puzzle.core;

public class Type {

  public static final Type Three_X_Three;
  public static final Type Four_X_Four;

  static {
    Three_X_Three = new Type();
    Three_X_Three.setXPieceNumber(3);
    Three_X_Three.setYPieceNumber(3);

    Four_X_Four = new Type();
    Four_X_Four.setXPieceNumber(4);
    Four_X_Four.setYPieceNumber(4);
  }

  private int xPieceNumber;
  private int yPieceNumber;

  private Type() {

  }

  public Type(int xPieceNumber, int yPieceNumber) {
    this.xPieceNumber = xPieceNumber;
    this.yPieceNumber = yPieceNumber;
  }

  public int getXPieceNumber() {
    return xPieceNumber;
  }

  public void setXPieceNumber(int XPieceNumber) {
    this.xPieceNumber = XPieceNumber;
  }

  public int getYPieceNumber() {
    return yPieceNumber;
  }

  public void setYPieceNumber(int YPieceNumber) {
    this.yPieceNumber = YPieceNumber;
  }

  public int getPieceNumber() {
    return getXPieceNumber() * getYPieceNumber();
  }

  public int getPieceType(int index) {
    if (index < 0 || index > (getPieceNumber())) {
      return -1;
    }

    boolean hasLeft = (index % getXPieceNumber()) != 0;
    boolean hasRight = (index % getXPieceNumber()) != getXPieceNumber() - 1;
    boolean hasBottom = (index / getYPieceNumber()) != getYPieceNumber() - 1;
    boolean hasTop = (index / getYPieceNumber() != 0);

    if (!hasLeft && hasRight && hasBottom && !hasTop) {
      return Piece.TOP_LEFT;
    } else if (hasLeft && hasRight && hasBottom && !hasTop) {
      return Piece.TOP;
    } else if (hasLeft && !hasRight && hasBottom && !hasTop) {
      return Piece.TOP_RIGHT;
    } else if (!hasLeft && hasRight && hasBottom && hasTop) {
      return Piece.LEFT;
    } else if (hasLeft && hasRight && hasBottom && hasTop) {
      return Piece.CENTER;
    } else if (hasLeft && !hasRight && hasBottom && hasTop) {
      return Piece.RIGHT;
    } else if (!hasLeft && hasRight && !hasBottom && hasTop) {
      return Piece.BOTTOM_LEFT;
    } else if (hasLeft && hasRight && !hasBottom && hasTop) {
      return Piece.BOTTOM;
    } else if (hasLeft && !hasRight && !hasBottom && hasTop) {
      return Piece.BOTTOM_RIGHT;
    } else {
      return -1;
    }
  }
}
