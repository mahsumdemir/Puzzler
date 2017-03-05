package com.mahsum.puzzle;

public class Type {
    public static final Type Three_X_Three;
    static {
        Three_X_Three = new Type();
        Three_X_Three.setXPieceNumber(3);
        Three_X_Three.setYPieceNumber(3);
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
