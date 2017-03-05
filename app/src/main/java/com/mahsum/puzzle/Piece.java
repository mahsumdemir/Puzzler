package com.mahsum.puzzle;

import android.graphics.Bitmap;

class Piece {
    private Bitmap bitmap;
    private BitmapMask mask;

    public int getMaskX() {
        return mask.getMaskX();
    }

    public int getAdditionSizeX() {
        return mask.getAdditionSizeX();
    }

    public int getMaskY() {
        return mask.getMaskY();
    }

    public int getAdditionSizeY() {
        return mask.getAdditionSizeY();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setMask(BitmapMask mask) {
        this.mask = mask;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
