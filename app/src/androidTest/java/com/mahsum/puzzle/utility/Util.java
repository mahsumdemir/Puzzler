package com.mahsum.puzzle.utility;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static com.mahsum.puzzle.Saving.saveBitmap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.view.View;
import com.mahsum.puzzle.DummyPermissionActivity;
import com.mahsum.puzzle.Piece;
import com.mahsum.puzzle.PuzzleCreator;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;

public class Util {

  private static final int GRANT_BUTTON_INDEX = 1;

  public static boolean grantPermission(Context appContext, String permission) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return checkPermission(appContext, permission);
    } else {
      if (checkPermission(appContext, permission)) return true;
      startActivityToHostPermissionDialog(appContext, permission);
      return checkPermission(appContext, permission);
    }
  }

  private static void startActivityToHostPermissionDialog(Context appContext, String permission) {
    Intent intent = new Intent(appContext, DummyPermissionActivity.class);
    intent.putExtra("PERMISSION", permission);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    appContext.startActivity(intent);
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    clickAllowPermission();
  }

  private static void clickAllowPermission() {
    try {
      UiDevice device = UiDevice.getInstance(getInstrumentation());
      UiObject allowPermissions = device.findObject(new UiSelector()
          .clickable(true)
          .checkable(false)
          .index(GRANT_BUTTON_INDEX));
      if (allowPermissions.exists()) {
        allowPermissions.click();
      }
    } catch (UiObjectNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static boolean checkPermission(Context appContext, String permission) {
    return PackageManager.PERMISSION_GRANTED == appContext.checkCallingOrSelfPermission(permission);
  }


  public static Bitmap joinPuzzlePieces(PuzzleCreator puzzleCreator, Piece[] pieces) {
    int width = pieces[0].getMaskX() * puzzleCreator.getXPieceNumber() +
        2 * pieces[0].getAdditionSizeX();

    int height = pieces[0].getMaskY() * puzzleCreator.getYPieceNumber() +
        2 * pieces[0].getAdditionSizeY();

    Bitmap joinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    drawPiecesOn(puzzleCreator, joinedBitmap, pieces);
    Bitmap result = shelveJoinedBitmap(joinedBitmap, pieces[0].getAdditionSizeX(),
        pieces[0].getAdditionSizeY());
    return result;
  }

  private static Bitmap shelveJoinedBitmap(Bitmap joinedBitmap, int additionSizeX,
      int additionSizeY) {
    Bitmap shelvedBitmap = Bitmap.createBitmap(joinedBitmap, additionSizeX, additionSizeY,
        joinedBitmap.getWidth() - 2 * additionSizeX,
        joinedBitmap.getHeight() - 2 * additionSizeY);
    return shelvedBitmap;
  }

  private static void drawPiecesOn(PuzzleCreator puzzleCreator, Bitmap joinedBitmap, Piece[] pieces) {
    Canvas canvas = new Canvas(joinedBitmap);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

    int currentX = 0, currentY = 0;
    int xIncrement = pieces[0].getMaskX(), yIncrement = pieces[0].getMaskY();
    for (int index = 0; index < pieces.length; index++) {
      canvas.drawBitmap(pieces[index].getBitmap(), currentX, currentY, paint);

      currentX += xIncrement;
      if (index % puzzleCreator.getXPieceNumber() == puzzleCreator.getXPieceNumber() - 1) {
        currentX = 0;
        currentY += yIncrement;
      }
    }
  }

  public static void takeScreenShot(final Activity activity, final String ssName)
      throws FileCouldNotSaved, FileCouldNotCreated {
    activity.runOnUiThread(new Runnable() {
      @Override
      public void run() {
        View rootView = activity.getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = rootView.getDrawingCache();
        try {
          saveBitmap(bitmap, ssName);
        } catch (FileCouldNotSaved | FileCouldNotCreated error) {
          error.printStackTrace();
        }
      }
    });
  }
}
