package com.mahsum.puzzle.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.mahsum.puzzle.Piece;
import com.mahsum.puzzle.Puzzle;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.Type;
import com.mahsum.puzzle.loadImage.ImageLoadCallBack;
import com.mahsum.puzzle.loadImage.ImageLoader;


public class GameBoard extends Activity {

  public static final String ORIGINAL_IMAGE_FILE_PATH = "ORIGINAL_IMAGE_FILE_PATH";
  public static final String RESOLUTION_X = "RESOLUTION_X";
  public static final String RESOLUTION_Y = "RESOLUTION_Y";
  public static final String PIECES_X = "PIECES_X";
  public static final String PIECES_Y = "PIECES_Y";
  private static final String TAG = "GameBoard";
  private Bitmap image;
  private Puzzle puzzle;
  private String imageFilePath;
  private int resolutionX;
  private int resolutionY;
  private int piecesX;
  private int piecesY;
  private ImageView[] views;
  private Piece[] pieces;
  private Uri imageFileURI;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_board);
    parseIntent(getIntent());
    readImage();
  }

  private void readImage() {
    if (imageFilePath != null){
      image = Bitmap.createScaledBitmap(image, resolutionX, resolutionY, false);
      createPuzzle();
      initBoard();
    }
    if (imageFileURI != null){
      ImageLoader imageLoader = new ImageLoader(getContentResolver());
      imageLoader.loadImage(imageFileURI, new ImageLoadCallBack() {
        @Override
        public void onImageLoaded(Bitmap image) {
          GameBoard.this.image = Bitmap.createScaledBitmap(image, resolutionX, resolutionY, false);
          createPuzzle();
          initBoard();
        }
      });
    }
  }

  private void createPuzzle() {
    puzzle = new Puzzle(new Type(piecesX, piecesY));
    puzzle.setImage(image);
    pieces = puzzle.createPuzzle();
  }

  private void initBoard() {
    views = new ImageView[piecesX * piecesY];

    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pieceBoard);
    FrameLayout.LayoutParams layoutParams =
        new FrameLayout.LayoutParams(piecesX * pieces[0].getBitmap().getWidth(),
                                        piecesY * pieces[0].getBitmap().getHeight());
    relativeLayout.setLayoutParams(layoutParams);
    for (int index = 0; index < views.length; index++) {
      views[index] = initImageView(pieces[index], piecesX, piecesY, index);
      relativeLayout.addView(views[index]);
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
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
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
  private ImageView initImageView(Piece piece, int piecesX, int piecesY, int index) {
    int row = index / piecesY;
    int column = index % piecesX;
    int viewX = column * piece.getBitmap().getWidth();
    int viewY = row * piece.getBitmap().getHeight();
    ImageView view = new ImageView(getApplicationContext());
    view.setId(ImageView.generateViewId());
    view.setImageBitmap(piece.getBitmap());
    view.setX(viewX);
    view.setY(viewY);
    return view;
  }

  private void parseIntent(Intent intent) {
    imageFilePath = intent.getStringExtra(ORIGINAL_IMAGE_FILE_PATH);
    if (imageFilePath == null) {
      imageFileURI = intent.getParcelableExtra(ORIGINAL_IMAGE_FILE_PATH);
    }

    resolutionX = intent.getIntExtra(RESOLUTION_X, 1600);
    resolutionY = intent.getIntExtra(RESOLUTION_Y, 1600);
    piecesX = intent.getIntExtra(PIECES_X, 10);
    piecesY = intent.getIntExtra(PIECES_Y, 10);
  }

  public Bitmap getImage() {
    return image;
  }

  public String getImageFilePath() {
    return imageFilePath;
  }

  public int getResolutionX() {
    return resolutionX;
  }


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
