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

import org.junit.Before;
import org.junit.Test;

import static com.mahsum.puzzle.util.Util.assertBitmapsEquals;
import static junit.framework.Assert.fail;


public class PuzzleTests {
    private Puzzle puzzle;
    private Bitmap image;

    @Before
    public void setUp() throws Exception {
        puzzle = new Puzzle(Type.Three_X_Three);
        image = BitmapFactory.decodeFile(BuildConfig.IMAGES_ROOT_DIR + "/harput.png");
        if (image == null) {
            fail("Error while decoding file: " + BuildConfig.IMAGES_ROOT_DIR + "/harput.png");
        }
    }

    @Test
    public void testCreatePuzzle_3X3_900x900_Image() throws Exception {
        //setup
        image = Bitmap.createScaledBitmap(image, 900, 900, false);
        puzzle.setImage(image);

        //exercise
        Piece[] pieces = puzzle.createPuzzle();

        //assert
        assertBitmapsEquals(image, joinPuzzlePieces(puzzle, pieces));
    }

    @Test
    public void testCreatePuzzle_3X3_1200x1200_Image() throws Exception {
        //setup
        image = Bitmap.createScaledBitmap(image, 1200, 1200, false);
        puzzle.setImage(image);

        //exercise
        Piece[] pieces = puzzle.createPuzzle();

        //assert
        assertBitmapsEquals(image, joinPuzzlePieces(puzzle, pieces));
    }

    @Test
    public void testCreatePuzzle_3X3_899x899_Image_ShouldFail() throws Exception {
        //setup
        image = Bitmap.createScaledBitmap(image, 899, 899, false);

        //exercise
        try {
            puzzle.setImage(image);
            fail("Creating of puzzle should failed cause of inappropriate size.");
        }catch (RuntimeException e){

        }

    }

    private static Bitmap joinPuzzlePieces(Puzzle puzzle, Piece[] pieces){
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

    private static void drawPiecesOn(Puzzle puzzle, Bitmap joinedBitmap, Piece[] pieces) {
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
