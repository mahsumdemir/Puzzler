package com.mahsum.puzzle.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.mahsum.puzzle.Piece;
import com.mahsum.puzzle.Puzzle;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.Type;
import com.mahsum.puzzle.loadImage.ImageLoadCallBack;
import com.mahsum.puzzle.loadImage.ImageLoader;
import java.util.Timer;
import java.util.TimerTask;


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
  private static PieceViewList pieceViewList;
  private static Piece[] pieces;
  private Uri imageFileURI;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_board);
    parseIntent(getIntent());
    readImage();
  }

  private void readImage() {
    if (imageFilePath != null){
      image = BitmapFactory.decodeFile(imageFilePath);
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
    progressBar = (ProgressBar) findViewById(R.id.progressBar);
    pieceViewList = new PieceViewList(piecesX * piecesY);

    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.pieceBoard);
    FrameLayout.LayoutParams layoutParams =
        new FrameLayout.LayoutParams(piecesX * pieces[0].getBitmap().getWidth(),
                                        piecesY * pieces[0].getBitmap().getHeight());
    relativeLayout.setLayoutParams(layoutParams);
    for (int index = 0; index < pieceViewList.size(); index++) {
      PieceImageView imageView = initImageView(pieces[index], piecesX, piecesY, index);
      pieceViewList.add(imageView, index);
      relativeLayout.addView(pieceViewList.get(index));
    }
    pieceViewList.shuffle(piecesX * piecesY);
    progressBar.setProgress(pieceViewList.getProgress());

    new Timer("Progress Bar Timer").schedule(new TimerTask() {
      @Override
      public void run() {
        progressBar.post(new Runnable() {
          @Override
          public void run() {
            progressBar.setProgress(pieceViewList.getProgress());
            if (progressBar.getProgress() == 100){
              Toast.makeText(GameBoard.this.getApplicationContext(), "Game is finished", Toast.LENGTH_LONG).show();
              GameBoard.this.finish();
            }
          }
        });
      }
    }, 0, 1000);

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
        pieceViewList.scaleAll(scaleFactor);
      }
    });
  }

  public static double findScaleFactor(int screenWidth, int screenHeight,
                                       int puzzleWidth, int puzzleHeight) {

    double scaleWidth = (double) screenWidth / puzzleWidth;
    double scaleHeight = (double) screenHeight / puzzleHeight;

    return Math.min(scaleWidth, scaleHeight);
  }
  private PieceImageView initImageView(Piece piece, int piecesX, int piecesY, int index) {
    PieceImageView pieceImageView = new PieceImageView(getApplicationContext());
    pieceImageView.configure(piece, piecesX, piecesY, index);
    return pieceImageView;
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
    return pieceViewList.get(index).getId();
  }

}
