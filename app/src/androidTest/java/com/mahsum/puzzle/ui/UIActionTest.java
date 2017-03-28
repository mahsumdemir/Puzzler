package com.mahsum.puzzle.ui;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertTrue;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import com.mahsum.puzzle.Application;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.loadImage.PickImageActivity;
import com.mahsum.puzzle.utility.Util;
import org.junit.Rule;
import org.junit.Test;


public class UIActionTest {

  @Rule
  public ActivityTestRule<PickImageActivity> activityActivityTestRule =
      new ActivityTestRule<>(PickImageActivity.class);


  @Test
  public void testPickImage_EnterInput_ShowResult() throws Exception {
    //start image picking
    onView(withId(R.id.button)).perform(ViewActions.click());

    //chose Image at Location
    Thread.sleep(500); //wait to don't miss click event.
    final UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    uiDevice.click(50,300);

    //enter input
    onView(withId(R.id.piecesX))
        .perform(ViewActions.clearText())
        .perform(ViewActions.typeText("2"));
    onView(withId(R.id.piecesY))
        .perform(ViewActions.clearText())
        .perform(ViewActions.typeText("2"));

    //register activity monitor
    Instrumentation.ActivityMonitor activityMonitor = InstrumentationRegistry
        .getInstrumentation()
        .addMonitor(GameBoard.class.getName(), null, false);


    onView(withText("Ok")).perform(ViewActions.click());

    GameBoard activity = (GameBoard) activityMonitor.waitForActivityWithTimeout(2000);
    assertTrue(activity != null);

    uiDevice.findObject(By.res("com.mahsum.puzzle:id/pieceBoard")).pinchOpen(0.5f);

    //save activity result for further examination
    Util.takeScreenShot(activity, Application.getImagesRootDir() + "/ss/sample.png");

  }
}
