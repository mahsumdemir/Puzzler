package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;

import com.mahsum.puzzle.BitmapMask;
import com.mahsum.puzzle.BuildConfig;
import com.mahsum.puzzle.Type;

public class Puzzle {
    private static final String TAG = "Puzzle";
    private final Type type;
    private Bitmap image;
    private Bitmap[] pieces;
    private static final Paint paint;

    static {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }


    public Puzzle(Type type) {
        this.type = type;
    }

    public void setImage(Bitmap image) {
        checkImageSize(image);
        this.image = image.copy(Bitmap.Config.ARGB_8888, true);
    }

    private void checkImageSize(Bitmap image) {
        if (image.getWidth() % (getXPieceNumber() * 5) != 0){
            throw new RuntimeException("Images width should be a multiple of " +
                    String.valueOf(getXPieceNumber() * 5));
        }
        else if(image.getHeight() % (getYPieceNumber() * 5) != 0){
            throw new RuntimeException("Images height should be a multiple of" +
                    String.valueOf(getYPieceNumber() * 5));
        }
    }

    public Piece[] createPuzzle() {
        int maskX = image.getWidth() / type.getXPieceNumber();
        int maskY = image.getHeight() / type.getYPieceNumber();
        BitmapMask[] masks = createMasks(maskX, maskY);
        Bitmap image = surroundImage(masks[0].getAdditionSizeX(), masks[0].getAdditionSizeY());
        Piece[] pieces = new Piece[type.getPieceNumber()];


        int currentX = 0;
        int currentY = 0;
        for (int index = 0; index < type.getPieceNumber(); index++) {
            Log.d(TAG, String.format("iterating for currentX: %d, currentY: %d", currentX, currentY));
            BitmapMask mask = type.decideMask(index, masks);
            pieces[index] = new Piece();
            pieces[index].setMask(mask);
            pieces[index].setBitmap(maskImage(image, mask, currentX, currentY));

            currentX += maskX;
            if (index % type.getXPieceNumber() == type.getXPieceNumber() - 1){
                currentX = 0;
                currentY += maskY;
            }
        }

        return pieces;
    }

    private Bitmap surroundImage(int x, int y) {
        Bitmap surroundedImage = Bitmap.createBitmap(image.getWidth() + 2 * x, image.getHeight() + 2 * y, Bitmap.Config.ARGB_8888);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(surroundedImage);
        canvas.drawBitmap(image, x, y, paint);
        return surroundedImage;
    }

    private Bitmap maskImage(Bitmap image, BitmapMask mask, int currentX, int currentY) {
        Canvas canvas = new Canvas(mask.getBitmap());
        Bitmap partOfSource = Bitmap.createBitmap(image, currentX, currentY, mask.getWidth(), mask.getHeight());
        canvas.drawBitmap(partOfSource, 0, 0, paint);
        return mask.getBitmap().copy(Bitmap.Config.ARGB_8888, false);
    }

    private void savePieces(BitmapMask[] masks) {
        pieces = new Bitmap[9];
        for (int index = 0; index < masks.length; index++) {
            pieces[index] = masks[index].getBitmap();
        }
    }

    private BitmapMask[] createMasks(int x, int y) {
        BitmapMask mask = new BitmapMask(x, y);
        int realX = mask.getWidth();
        int realY = mask.getHeight();

        BitmapMask[] masks = new BitmapMask[9];
        for (int index = 0; index < masks.length; index++) {
            Bitmap aMask = BitmapFactory.decodeFile(BuildConfig.IMAGES_ROOT_DIR + "/masks/"
                    + String.valueOf(index) + ".png");
            aMask = Bitmap.createScaledBitmap(aMask, realX, realY, false);
            masks[index] = new BitmapMask(aMask);
        }
        return masks;
    }

    public int getXPieceNumber() {
        return type.getXPieceNumber();
    }

    public int getYPieceNumber() {
        return type.getYPieceNumber();
    }
}
