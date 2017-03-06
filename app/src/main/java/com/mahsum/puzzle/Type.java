package com.mahsum.puzzle;

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

  public BitmapMask decideMask(int index, BitmapMask[] masks) {
    return masks[index];
  }
}
