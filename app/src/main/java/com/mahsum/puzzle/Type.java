package com.mahsum.puzzle;

public class Type {
    public static final Type Three_X_Three;
    public static final Type Four_X_Four;

    private Type(){

    }
    static {
        Three_X_Three = new Type();
        Three_X_Three.setXPieceNumber(3);
        Three_X_Three.setYPieceNumber(3);

        Four_X_Four = new Type();
        Four_X_Four.setXPieceNumber(4);
        Four_X_Four.setYPieceNumber(4);
    }

    private int XPieceNumber;
    private int YPieceNumber;

    public int getXPieceNumber() {
        return XPieceNumber;
    }

    public void setXPieceNumber(int XPieceNumber) {
        this.XPieceNumber = XPieceNumber;
    }

    public void setYPieceNumber(int YPieceNumber) {
        this.YPieceNumber = YPieceNumber;
    }

    public int getYPieceNumber() {
        return YPieceNumber;
    }

    public int getPieceNumber() {
        return getXPieceNumber() * getYPieceNumber();
    }

    public BitmapMask decideMask(int index, BitmapMask[] masks) {
        return masks[index];
    }
}
