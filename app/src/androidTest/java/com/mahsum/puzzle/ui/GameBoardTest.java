package com.mahsum.puzzle.ui;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasImeAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.mahsum.puzzle.BuildConfig;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GameBoardTest {
  private Context targetApplicationContext;
  private Bitmap image;
  private final int RESOLUTION_X = 900, RESOLUTION_Y = 900;
  private final int PIECES_X = 3, PIECES_Y = 3;

  @Rule
  public ActivityTestRule<GameBoard> gameBoardActivityTestRule =
      new ActivityTestRule<>(GameBoard.class, true, false);

  @Before
  public void setUp() throws Exception {
    targetApplicationContext = InstrumentationRegistry.getContext();
    image = BitmapFactory.decodeFile(BuildConfig.IMAGES_ROOT_DIR + "/harput.png");

    Intent intent = new Intent(targetApplicationContext, GameBoard.class);
    intent.putExtra("ORIGINAL_IMAGE", image);
    intent.putExtra("RESOLUTION_X", RESOLUTION_X);
    intent.putExtra("RESOLUTION_Y", RESOLUTION_Y);
    intent.putExtra("PIECES_X", PIECES_X);
    intent.putExtra("PIECES_Y", PIECES_Y);
    gameBoardActivityTestRule.launchActivity(intent);
  }

  @Test
  public void testReceiveData() throws Exception {
    GameBoard gameBoard = gameBoardActivityTestRule.getActivity();
    assertEquals(gameBoard.getImage(), image);
  }

  @Test
  public void testUserSeesPuzzlePieces() throws Exception {
    //assert
    assertPiecesDisplayed(9);
  }

  private void assertPiecesDisplayed(int piecesNumber) {
    for (int index = 0; index < piecesNumber; index++) {
      onView(withId(1)).check((matches(isDisplayed())));
    }
  }
}
