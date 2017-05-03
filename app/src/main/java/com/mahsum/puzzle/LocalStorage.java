package com.mahsum.puzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import android.util.SparseIntArray;
import com.google.gson.Gson;
import com.mahsum.puzzle.core.GameBoard;
import com.mahsum.puzzle.core.Puzzle;
import com.mahsum.puzzle.core.PuzzleBuilder;
import com.mahsum.puzzle.core.Type;
import com.mahsum.puzzle.loadImage.ImageLoadCallBack;
import com.mahsum.puzzle.loadImage.ImageLoader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class LocalStorage {

    private static final String TAG = "LocalStorage";
    private static Context context;
    private static final ArrayList<GameBoard> gameBoards = new ArrayList<>();

    public static void saveNewGameBoard(){
      SharedPreferences sharedPreferences = context.getSharedPreferences("GAME_BOARDS", Context.MODE_PRIVATE);
      int count = sharedPreferences.getInt("COUNT", 0);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt("COUNT", count + 1);
      editor.apply();
      saveGameBoard(GameBoard.current, count + 1);
    }

    private static void saveGameBoard(GameBoard gameBoard, int id){
      gameBoards.add(gameBoard);

      Puzzle puzzle = GameBoard.current.getPuzzle();
      Type type = puzzle.getType();
      int[] pieceOrder = GameBoard.current.getPieceOrder();

      Log.d(TAG, "Saving Current Game Board with id" + id);
      Log.d(TAG, GameBoard.current.toString());

      Gson gson = new Gson();
      SharedPreferences sharedPreferences = context.getSharedPreferences("GAME_BOARDS", Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt("ID", id);
      editor.putString("TYPE" + id, gson.toJson(type));
      editor.putString("PIECE_ORDER" + id, gson.toJson(pieceOrder));
      saveBitmap(editor, puzzle.getImage(), id);
      editor.apply();
    }

    public static void updatePreviousGameBoard(){
      GameBoard current = GameBoard.current;
      GameBoard oldBoard = findBoardWithId(current.getId());
      gameBoards.remove(oldBoard);
      saveGameBoard(current, current.getId());
    }

  private static GameBoard findBoardWithId(int id) {
    for (GameBoard gameBoard : gameBoards) {
      if (gameBoard.getId() == id) return gameBoard;
    }
    return null;
  }

  private static void saveBitmap(SharedPreferences.Editor editor, Bitmap image, int index) {
    FileOutputStream fileOutputStream = null;
    try {
      fileOutputStream = context.openFileOutput("IMAGE" + index, Context.MODE_PRIVATE);
      image.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<GameBoard> getSavedGames() {
        return gameBoards;
    }

  public static void init(Context context) {
      LocalStorage.context= context;
      SharedPreferences sharedPreferences = context.getSharedPreferences("GAME_BOARDS", Context.MODE_PRIVATE);
      if (sharedPreferences == null) return; //we dont have one yet
      int count = sharedPreferences.getInt("COUNT", 0);

      for (int index = 0; index < count; index++) {
        GameBoard gameBoard = readGameBoard(sharedPreferences, index + 1);
        gameBoards.add(gameBoard);
      }
  }

  private static GameBoard readGameBoard(SharedPreferences sharedPreferences, int index) {
    String typeGson= sharedPreferences.getString("TYPE" + index, "");
    String pieceOrderGson = sharedPreferences.getString("PIECE_ORDER" + index, "");
    int id = sharedPreferences.getInt("ID", 0);
    Bitmap image = null;
    try {
      FileInputStream inputStream = context.openFileInput("IMAGE" + index);
      image = BitmapFactory.decodeStream(inputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    Gson gson = new Gson();
    Type type = gson.fromJson(typeGson, Type.class);
    int[] pieceOrder = gson.fromJson(pieceOrderGson, int[].class);

    Puzzle puzzle = PuzzleBuilder.start()
        .setType(type)
        .setImage(image)
        .build();

    GameBoard gameBoard = new GameBoard(puzzle);
    gameBoard.setPieceOrder(pieceOrder);
    gameBoard.setId(id);
    Log.d(TAG, "Loading current Game Board with id" + index);
    Log.d(TAG, gameBoard.toString());
    return gameBoard;
  }

  public static void deletePreviousGame() {

  }
}
