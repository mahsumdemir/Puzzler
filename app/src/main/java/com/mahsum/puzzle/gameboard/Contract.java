package com.mahsum.puzzle.gameboard;

import android.content.ContentResolver;
import android.content.Intent;
import com.mahsum.puzzle.core.Puzzle;

/**
 * Created by mahsum on 01.05.2017.
 */

public interface Contract {

  interface View{
    void onPuzzleCreated(Puzzle puzzle, final int GAME_TYPE);

    ContentResolver getContentResolver();
  }

  interface Presenter{
    void createPuzzle(Intent intent);
  }
}
