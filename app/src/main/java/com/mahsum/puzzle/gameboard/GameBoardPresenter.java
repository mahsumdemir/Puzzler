package com.mahsum.puzzle.gameboard;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import com.mahsum.puzzle.core.GameBoard;
import com.mahsum.puzzle.core.Puzzle;
import com.mahsum.puzzle.core.PuzzleBuilder;
import com.mahsum.puzzle.core.Type;
import com.mahsum.puzzle.loadImage.ImageLoadCallBack;
import com.mahsum.puzzle.loadImage.ImageLoader;

public class GameBoardPresenter implements Contract.Presenter{
  public static final String ORIGINAL_IMAGE_FILE_PATH = "ORIGINAL_IMAGE_FILE_PATH";
  public static final String RESOLUTION_X = "RESOLUTION_X";
  public static final String RESOLUTION_Y = "RESOLUTION_Y";
  public static final String PIECES_X = "PIECES_X";
  public static final String PIECES_Y = "PIECES_Y";
  public static int PREVIOUS_GAME = 1;
  public static int NEW_GAME = 0;
  public static final String GAME_TYPE = "GAME_TYPE";


  private Contract.View view;
  public GameBoardPresenter(Contract.View view) {
    this.view = view;
  }

  @Override
  public void createPuzzle(final Intent intent) {
    int gameType = intent.getIntExtra("GAME_TYPE", 0);
    if (gameType == PREVIOUS_GAME){
      loadPreviousGame();
      return;
    }

    ImageLoadCallBack imageLoadCallBack = new ImageLoadCallBack() {
      @Override
      public void onImageLoaded(Bitmap image) {
        int resolutionX = intent.getIntExtra(RESOLUTION_X, 1600);
        int resolutionY = intent.getIntExtra(RESOLUTION_Y, 1600);
        int piecesX = intent.getIntExtra(PIECES_X, 10);
        int piecesY = intent.getIntExtra(PIECES_Y, 10);

        image = Bitmap.createScaledBitmap(image, resolutionX, resolutionY, false);
        Type type = new Type(piecesX, piecesY);
        Puzzle puzzle = PuzzleBuilder.start()
            .setType(type)
            .setImage(image)
            .build();

        view.onPuzzleCreated(puzzle, NEW_GAME);
      }
    };

    ImageLoader imageLoader = new ImageLoader(view.getContentResolver());


    String imageFilePath = intent.getStringExtra(ORIGINAL_IMAGE_FILE_PATH);
    if (imageFilePath != null) {
      imageLoader.loadImage(imageFilePath, imageLoadCallBack);
    }
    else {
      Uri imageUri = intent.getParcelableExtra(ORIGINAL_IMAGE_FILE_PATH);
      imageLoader.loadImage(imageUri, imageLoadCallBack);
    }

  }

  private void loadPreviousGame() {
    view.onPuzzleCreated(GameBoard.current.getPuzzle(), PREVIOUS_GAME);
  }
}
