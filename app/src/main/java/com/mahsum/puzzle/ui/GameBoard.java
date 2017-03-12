package com.mahsum.puzzle.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

  private Bitmap image;
  private String imageFilePath;
  private int resolutionX;
  private int resolutionY;
  private int piecesX;
  private int piecesY;
  private ImageView[] views;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_board);
    parseIntent(getIntent());
    Piece[] pieces = createPuzzle();
    initBoard(pieces);
  }

  private Piece[] createPuzzle() {
    Puzzle puzzle = new Puzzle(new Type(piecesX, piecesY));
    Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.harput);
    image = Bitmap.createScaledBitmap(image, resolutionX, resolutionY, false);
    puzzle.setImage(image);
    return puzzle.createPuzzle();
  }

  private void initBoard(Piece[] pieces) {
    views = new ImageView[piecesX * piecesY];

    GridLayout gridLayout = (GridLayout) findViewById(R.id.pieceBoard);
    gridLayout.setRowCount(piecesY);
    gridLayout.setColumnCount(piecesX);
    for (int index = 0; index < views.length; index++){
      views[index] = initImageView(pieces[index].getBitmap());
      gridLayout.addView(views[index]);
    }
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
    resolutionX = intent.getIntExtra("RESOLUTION_X", 900);
    resolutionY = intent.getIntExtra("RESOLUTION_Y", 900);
    piecesX = intent.getIntExtra("PIECES_X", 2);
    piecesY = intent.getIntExtra("PIECES_Y", 2);

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
