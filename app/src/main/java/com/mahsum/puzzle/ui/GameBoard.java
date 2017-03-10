package com.mahsum.puzzle.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.mahsum.puzzle.R;


public class GameBoard extends Activity {

  private Bitmap image;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_board);
  }

  public Bitmap getImage() {
    return image;
  }
}
