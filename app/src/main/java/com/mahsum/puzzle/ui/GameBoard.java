package com.mahsum.puzzle.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;
import android.widget.ImageView;
import com.mahsum.puzzle.Application;
import com.mahsum.puzzle.BuildConfig;
import com.mahsum.puzzle.Piece;
import com.mahsum.puzzle.Puzzle;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.Type;


public class GameBoard extends Activity {

  private static final String TAG = "GameBoard";
  private Bitmap image;
  private String imageFilePath;
  private int resolutionX;
  private int resolutionY;
  private int piecesX;
  private int piecesY;
  private ImageView[] views;
  private Piece[] pieces;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_board);
    parseIntent(getIntent());
    pieces = createPuzzle();
    initBoard(pieces);
  }

  private Piece[] createPuzzle() {
    Puzzle puzzle = new Puzzle(new Type(piecesX, piecesY));
    Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.harput);
    image = Bitmap.createScaledBitmap(image, resolutionX, resolutionY, false);
    puzzle.setImage(image);
    return puzzle.createPuzzle();
  }

  private void initBoard(final Piece[] pieces) {
    views = new ImageView[piecesX * piecesY];

    GridLayout gridLayout = (GridLayout) findViewById(R.id.pieceBoard);
    gridLayout.setRowCount(piecesY);
    gridLayout.setColumnCount(piecesX);
    for (int index = 0; index < views.length; index++){
      views[index] = initImageView(pieces[index].getBitmap());
      gridLayout.addView(views[index]);
    }

    //scale board
    final View rootView = findViewById(R.id.root);
    rootView.post(new Runnable() {
      @Override
      public void run() {
        int screenWidth = rootView.getWidth();
        int screenHeight = rootView.getHeight();
        double scaleFactor = findScaleFactor(screenWidth,
                                             screenHeight,
                                             pieces[0].getBitmap().getWidth() * piecesX,
                                             pieces[0].getBitmap().getHeight() * piecesY);
        scaleBoard(scaleFactor);
      }
    });

  }

  private void scaleBoard(double scaleFactor) {
    Log.d(TAG, "scaleBoard() called with: scaleFactor = [" + scaleFactor + "]");

    for (ImageView view : views) {
      LayoutParams params = (LayoutParams) view.getLayoutParams();
      params.height = (int) (view.getHeight() * scaleFactor);
      params.width = (int) (view.getWidth() * scaleFactor);
    }
  }

  public static double findScaleFactor(int screenWidth, int screenHeight,
                                       int puzzleWidth, int puzzleHeight) {

    double scaleWidth = (double) screenWidth / puzzleWidth;
    double scaleHeight = (double) screenHeight / puzzleHeight;

    return Math.min(scaleWidth, scaleHeight);
  }

  private ImageView initImageView(Bitmap image) {
    ImageView view = new ImageView(getApplicationContext());
    view.setId(ImageView.generateViewId());
    view.setImageBitmap(image);
    LayoutParams layoutParams = new LayoutParams();
    layoutParams.height = LayoutParams.WRAP_CONTENT;
    layoutParams.width = LayoutParams.WRAP_CONTENT;
    view.setLayoutParams(layoutParams);
    return view;
  }

  private void parseIntent(Intent intent) {
    imageFilePath = intent.getStringExtra("ORIGINAL_IMAGE_FILE_PATH");
    resolutionX = intent.getIntExtra("RESOLUTION_X", 1600);
    resolutionY = intent.getIntExtra("RESOLUTION_Y", 1600);
    piecesX = intent.getIntExtra("PIECES_X", 10);
    piecesY = intent.getIntExtra("PIECES_Y", 10);

    if (imageFilePath == null) imageFilePath = Application.getImagesRootDir() + "/harput.png";
  }

  public Bitmap getImage() {
    return image;
  }

  public String getImageFilePath() { return imageFilePath; }
  public int getResolutionX() { return resolutionX; }


  public int getResolutionY() {
    return resolutionY;
  }

  public int getPiecesX() {
    return piecesX;
  }

  public int getPiecesY() {
    return piecesY;
  }

  public int getImageViewIdByIndex(int index) {
    return views[index].getId();
  }

}
