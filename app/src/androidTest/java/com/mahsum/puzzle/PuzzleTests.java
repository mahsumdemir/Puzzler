package com.mahsum.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.mahsum.puzzle.BitmapMask;
import com.mahsum.puzzle.BuildConfig;
import com.mahsum.puzzle.Puzzle;
import com.mahsum.puzzle.Type;

import org.junit.Test;

import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static junit.framework.Assert.fail;


public class PuzzleTests {
    @Test
    public void testCreatePuzzle() throws Exception {
        //set up
        Puzzle puzzle = new Puzzle(Type.Three_X_Three);
        String file = BuildConfig.IMAGES_ROOT_DIR + "/harput_900x900/original.png";
        Bitmap image = BitmapFactory.decodeFile(file);
        if (image == null) fail("Error while decoding file: " + file);
        puzzle.setImage(image);
        BitmapMask[] pieces = puzzle.createPuzzle();

        Bitmap joinedBitmap = joinPuzzlePieces(puzzle, pieces);
        assertBitmapsEquals(image, joinedBitmap);
    }
    private static Bitmap joinPuzzlePieces(Puzzle puzzle, BitmapMask[] pieces){
        int width = pieces[0].getMaskX() * puzzle.getXPieceNumber() +
                2 * pieces[0].getAdditionSizeX();

        int height = pieces[0].getMaskY() * puzzle.getYPieceNumber() +
                2 * pieces[0].getAdditionSizeY();

        Bitmap joinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        drawPiecesOn(puzzle, joinedBitmap, pieces);
        Bitmap result = shelveJoinedBitmap(joinedBitmap, pieces[0].getAdditionSizeX(),
                pieces[0].getAdditionSizeY());
        return result;
    }

    private static Bitmap shelveJoinedBitmap(Bitmap joinedBitmap, int additionSizeX, int additionSizeY) {
        Bitmap shelvedBitmap = Bitmap.createBitmap(joinedBitmap, additionSizeX, additionSizeY,
                                                    joinedBitmap.getWidth() - 2 * additionSizeX,
                                                    joinedBitmap.getHeight() - 2 * additionSizeY);
        return shelvedBitmap;
    }

    private static void drawPiecesOn(Puzzle puzzle, Bitmap joinedBitmap, BitmapMask[] pieces) {
        Canvas canvas = new Canvas(joinedBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

        int currentX = 0, currentY = 0;
        int maskX = pieces[0].getMaskX(), maskY = pieces[0].getMaskY();
        for (int index = 0; index < pieces.length; index++) {
            canvas.drawBitmap(pieces[index].getBitmap(), currentX, currentY, paint);

            currentX += maskX;
            if(index % puzzle.getXPieceNumber() == 2){
                currentX = 0;
                currentY += maskY;
            }
        }
    }
}
