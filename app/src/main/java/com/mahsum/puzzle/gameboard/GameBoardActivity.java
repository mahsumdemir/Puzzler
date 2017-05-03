package com.mahsum.puzzle.gameboard;

import static com.mahsum.puzzle.gameboard.GameBoardPresenter.GAME_TYPE;
import static com.mahsum.puzzle.gameboard.GameBoardPresenter.NEW_GAME;
import static com.mahsum.puzzle.gameboard.GameBoardPresenter.PREVIOUS_GAME;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mahsum.puzzle.LocalStorage;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.core.GameBoard;
import com.mahsum.puzzle.core.GameBoard.GameBoardTracer;
import com.mahsum.puzzle.core.Piece;
import com.mahsum.puzzle.core.Puzzle;
import com.mahsum.puzzle.gameboard.Contract.Presenter;
import database.DatabaseHelper;
import database.DatabaseInterface;
import java.util.Timer;
import java.util.TimerTask;


public class GameBoardActivity extends Activity implements Contract.View, GameBoardTracer{

  private static final String TAG = "GameBoardActivity";
  private Puzzle puzzle;
  private static PieceViewList pieceViewList;
  private ProgressBar progressBar;
  @BindView(R.id.original) ImageView original;
  @BindView(R.id.toggle) Button toggle;
  @BindView(R.id.zoomLayout)
  ZoomLayout zoomLayout;
  private boolean isFinished;
  private int gameType;
  private Presenter presenter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    Log.d(TAG, "GameBoardActivity.onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_board);
    ButterKnife.bind(this);

    presenter = new GameBoardPresenter(this);
  }

  @Override
  protected void onStart() {
    Log.d(TAG, "GameBoardActivity.onResume");
    super.onStart();
    presenter.createPuzzle(getIntent());
  }

  private void initBoard() {
    int piecesX = puzzle.getType().getXPieceNumber();
    int piecesY = puzzle.getType().getYPieceNumber();
    Bitmap image = puzzle.getImage();
    Piece[] pieces = puzzle.getPieces();

    progressBar = (ProgressBar) findViewById(R.id.progressBar);
    pieceViewList = new PieceViewList(piecesX * piecesY);

    original.setImageBitmap(image);

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
    progressBar.setProgress(pieceViewList.getProgress());

    /*//scale board
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
    });*/
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

  @OnClick(R.id.toggle)
  public void toggleClicked(View v){
    if (original.getVisibility() == View.VISIBLE){
      toggle.setText("Real Image");
      original.setVisibility(View.INVISIBLE);
      zoomLayout.setVisibility(View.VISIBLE);
    }
    else{
      toggle.setText("Puzzle");
      original.setVisibility(View.VISIBLE);
      zoomLayout.setVisibility(View.INVISIBLE);
    }
  }

  public int getImageViewIdByIndex(int index) {
    return pieceViewList.get(index).getId();
  }


  @Override
  public void onPuzzleCreated(final Puzzle puzzle, final int GAME_TYPE) {
    this.puzzle = puzzle;
    this.gameType = GAME_TYPE;

    //if it is a new game update GameBoard
    if (GAME_TYPE == GameBoardPresenter.NEW_GAME) {
      GameBoard.loadBoard(new GameBoard(puzzle));
    }

    GameBoard.current.subscribe(this);
  }

  @Override
  protected void onDestroy() {
    Log.d(TAG, "GameBoardActivity.onDestroy");
    super.onDestroy();
    if (gameType == NEW_GAME && !isFinished){
      DatabaseInterface.addGameBoard(GameBoard.current);
    }

    if (gameType == PREVIOUS_GAME ){
      if (isFinished){
        DatabaseInterface.deleteGameBoard(GameBoard.current);
      }
      else{
        DatabaseInterface.updataGameBoard(GameBoard.current);
      }
    }
  }

  @Override
  public void init(int[] pieceOrder) {
    GameBoard gameBoard = GameBoard.current;
    initBoard();
    for (int index = 0; index < pieceOrder.length; index++) {
      Piece currentPiece = puzzle.getPieceAt(pieceOrder[index]);
      PieceImageView view = pieceViewList.get(index);
      view.setPiece(currentPiece);
    }
    if (gameType == GameBoardPresenter.NEW_GAME) gameBoard.shuffle(100);

    //start Progress bar checking
    new Timer("Progress Bar Timer").schedule(new TimerTask() {
      @Override
      public void run() {
        progressBar.post(new Runnable() {
          @Override
          public void run() {
            progressBar.setProgress(pieceViewList.getProgress());
            if (progressBar.getProgress() == 100){
              Toast.makeText(GameBoardActivity.this.getApplicationContext(), "Game is finished", Toast.LENGTH_SHORT).show();
              isFinished = true;
            }
          }
        });
      }
    }, 0, 1000);
  }

  @Override
  public void swap(int pieceId1, int pieceId2) {
    PieceImageView view = PieceViewList.findViewById(pieceId1);
    PieceImageView view1 = PieceViewList.findViewById(pieceId2);
    Piece temp = view.getPiece();
    view.setPiece(view1.getPiece());
    view1.setPiece(temp);
  }
}
