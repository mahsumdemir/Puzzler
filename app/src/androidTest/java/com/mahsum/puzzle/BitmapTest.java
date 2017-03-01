package com.mahsum.puzzle;

import static android.graphics.BitmapFactory.decodeByteArray;
import static android.graphics.BitmapFactory.decodeResource;
import static com.mahsum.puzzle.Saving.saveBitmap;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.support.test.InstrumentationRegistry;
import com.mahsum.puzzle.PuzzleFactory.TYPE;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import com.mahsum.puzzle.util.Util;
import org.junit.Before;
import org.junit.Test;

public class BitmapTest {

  private Context mTargetContext;

  @Before
  public void setUp() throws Exception {
    mTargetContext = InstrumentationRegistry.getTargetContext();
  }

  @Test
  public void testBitmapsEqual() throws Exception {
    Bitmap bitmap = decodeResource(mTargetContext.getResources(),
                                                 R.drawable.harput);
    Bitmap bitmap2 = decodeResource(mTargetContext.getResources(),
                                                  R.drawable.harput);
    Util.assertBitmapsEquals(bitmap, bitmap2);
  }

  @Test
  public void testBitmapsEqual_ShouldFail() throws Exception {
    Bitmap bitmap = decodeResource(mTargetContext.getResources(),
                                                 R.drawable.harput);
    Bitmap bitmap2 = decodeResource(mTargetContext.getResources(),
                                                  R.drawable.harput_modified);
    try {
      Util.assertBitmapsEquals(bitmap, bitmap2);
      fail("Images should be different.");
    }catch (RuntimeException e){
      //Ignored.
    }
  }

  @Test
  public void testJoinBitmaps_ShouldFail() throws Exception {
    Puzzle puzzle = new Puzzle();
    Bitmap original = BitmapFactory.decodeFile(BuildConfig.IMAGES_ROOT_DIR +
                                                   "/harput_900x900/original.png");
    puzzle.setOriginalBitmap(original);
    puzzle.setType(TYPE.FOUR_X_FOUR);
    puzzle.createPuzzle();

    Bitmap joinedBitmap = Bitmap.createBitmap(original.getWidth(), original.getHeight(), Config.ARGB_8888);
    joinAll(puzzle.getPieces(), new Canvas(joinedBitmap));

    Util.assertBitmapsEquals(original, joinedBitmap);
  }

  private void joinAll(Bitmap[] pieces, Canvas canvas) {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    int index = 0;
    for (int y = 0, height = 0; y < 3; y++, height += 300 ){
      for (int x = 0, width = 0; x < 3; x++, width += 300){
        canvas.drawBitmap(pieces[index], width, height, paint);
        index++;
      }

    }
  }


}
