package com.mahsum.puzzle.ui;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.mahsum.puzzle.Saving.saveBitmap;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.fail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import com.mahsum.puzzle.Application;
import com.mahsum.puzzle.BuildConfig;
import com.mahsum.puzzle.exceptions.FileCouldNotCreated;
import com.mahsum.puzzle.exceptions.FileCouldNotSaved;
import com.mahsum.puzzle.utility.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GameBoardTest {

  @Rule
  public ActivityTestRule<GameBoard> gameBoardActivityTestRule = new ActivityTestRule<>(
      GameBoard.class, true, false);

  @Before
  public void setUp() throws Exception {
    final String PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    if (!Util.grantPermission(InstrumentationRegistry.getTargetContext(), PERMISSION)) {
      fail("Error. Could not grand permission: " + PERMISSION);
    }
  }

  @Test
  public void testActivityReceivedData() throws Exception {
    //set up
    String imageFilePath = Application.getImagesRootDir() + "/harput.png";
    int resolutionX = 900, resolutionY = 900;
    int piecesX = 3, piecesY = 3;
    Intent intent = createIntentWithExtras(imageFilePath, resolutionX, resolutionY, piecesX,
                                           piecesY);
    gameBoardActivityTestRule.launchActivity(intent);

    //assert
    GameBoard gameBoard = gameBoardActivityTestRule.getActivity();
    assertEquals(imageFilePath, gameBoard.getImageFilePath());
    assertEquals(resolutionX, gameBoard.getResolutionX());
    assertEquals(resolutionY, gameBoard.getResolutionY());
    assertEquals(piecesX, gameBoard.getPiecesX());
    assertEquals(piecesY, gameBoard.getPiecesY());
  }

  private Intent createIntentWithExtras(String imageFilePath, int resolutionX, int resolutionY,
                                        int piecesX, int piecesY) {

    Context targetApplicationContext = InstrumentationRegistry.getContext();

    Intent intent = new Intent(targetApplicationContext, GameBoard.class);
    intent.putExtra("ORIGINAL_IMAGE_FILE_PATH", imageFilePath);
    intent.putExtra("RESOLUTION_X", resolutionX);
    intent.putExtra("RESOLUTION_Y", resolutionY);
    intent.putExtra("PIECES_X", piecesX);
    intent.putExtra("PIECES_Y", piecesY);
    return intent;
  }

  @Test
  public void testUserSeesPuzzlePieces_MultiplePuzzleType() throws Exception {
    testUserSeesPuzzlePieces(900, 900, 3, 3);
    testUserSeesPuzzlePieces(600, 600, 2, 2);
    testUserSeesPuzzlePieces(600, 600, 3, 3);
    testUserSeesPuzzlePieces(1600, 1600, 5, 5);
  }

  private void testUserSeesPuzzlePieces(int resolutionX, int resolutionY, int piecesX, int piecesY)
      throws FileCouldNotSaved, FileCouldNotCreated {
    //set up
    String imageFilePath = Application.getImagesRootDir() + "/harput.png";
    Intent intent = createIntentWithExtras(imageFilePath, resolutionX, resolutionY, piecesX,
                                           piecesY);
    //exercise
    gameBoardActivityTestRule.launchActivity(intent);

    //take Screen shot for examination
    String ssName =
        Application.getImagesRootDir() + "/ss" + "/harput" + String.valueOf(resolutionX) + "x" + String
            .valueOf(resolutionY) + "_" + String.valueOf(piecesX) + "x" + String.valueOf(piecesY) + ".png";
    takeScreenShot(gameBoardActivityTestRule.getActivity(), ssName);

    //assert
    assertPiecesDisplayed(gameBoardActivityTestRule.getActivity(), piecesX * piecesY);

    gameBoardActivityTestRule.getActivity().finish();
  }

  private void assertPiecesDisplayed(GameBoard gameBoard, int piecesNumber)
      throws FileCouldNotSaved, FileCouldNotCreated {
    //assert
    for (int index = 0; index < piecesNumber; index++) {
      int id = gameBoard.getImageViewIdByIndex(index);
      onView(withId(id)).check((matches(isDisplayed())));
    }
  }

  private void takeScreenShot(Activity activity, String ssName)
      throws FileCouldNotSaved, FileCouldNotCreated {
    View rootView = activity.getWindow().getDecorView().getRootView();
    rootView.setDrawingCacheEnabled(true);
    Bitmap bitmap = rootView.getDrawingCache();
    saveBitmap(bitmap, ssName);
  }
}
