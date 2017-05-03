package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.google.gson.Gson;
import com.mahsum.puzzle.core.GameBoard;
import com.mahsum.puzzle.core.Puzzle;
import com.mahsum.puzzle.core.PuzzleBuilder;
import com.mahsum.puzzle.core.Type;
import database.GameBoardTable.Entry;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseInterface {

  public static void removeListener(DataListener listener) {
    listeners.remove(listener);
  }

  public static boolean hasData() {
    return hasData;
  }

  public interface DataListener{
    void onDataChanges(ArrayList<GameBoard> newDataSet);
  }

  private static final String TAG = "DatabaseInterface";
  private static DatabaseHelper databaseHelper;
  private static HashMap<Integer, GameBoard> runTimeGameBoards, databaseGameBoards;
  private static ArrayList<DataListener> listeners = new ArrayList<>();
  private static boolean hasData = false;

  public static void addDataListener(DataListener listener){
    listeners.add(listener);
  }

  private static ArrayList<Integer> added = new ArrayList<>();
  private static ArrayList<Integer> updated = new ArrayList<>();
  private static ArrayList<Integer> deleted = new ArrayList<>();

  public static void onCreate(Context context){
    hasData = true;
    databaseHelper = new DatabaseHelper(context);
    databaseGameBoards = readGameBoards();
    runTimeGameBoards = copyHasMaps(databaseGameBoards);
    notifyListeners();
  }

  private static HashMap<Integer, GameBoard> copyHasMaps(HashMap<Integer, GameBoard> source) {
    HashMap<Integer, GameBoard> target = new HashMap<>();
    for (Map.Entry<Integer, GameBoard> sourceEntry : source.entrySet()) {
      target.put(sourceEntry.getKey(), GameBoard.copy(sourceEntry.getValue()));
    }
    return target;
  }

  private static HashMap<Integer, GameBoard> readGameBoards() {
    SQLiteDatabase database = databaseHelper.getReadableDatabase();
    //read everything
    Cursor cursor = database.query(GameBoardTable.TABLE_NAME, null, null, null, null, null, null);
    HashMap<Integer, GameBoard> gameBoards = new HashMap<>();
    while (cursor.moveToNext()){
      //read datas
      int piecesX = cursor.getInt(cursor.getColumnIndexOrThrow(Entry.PIECESX));
      int piecesY = cursor.getInt(cursor.getColumnIndexOrThrow(Entry.PIECESY));
      byte[] imageByte = cursor.getBlob(cursor.getColumnIndexOrThrow(Entry.IMAGE));
      String pieceOrderGson = cursor.getString(cursor.getColumnIndexOrThrow(Entry.PIECE_ORDER));
      Bitmap image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
      int[] pieceOrder = new Gson().fromJson(pieceOrderGson, int[].class);
      int id = cursor.getInt(cursor.getColumnIndexOrThrow(Entry.ID));

      Puzzle puzzle = PuzzleBuilder.start()
          .setType(new Type(piecesX, piecesY))
          .setImage(image)
          .build();

      GameBoard gameBoard = new GameBoard(puzzle);
      gameBoard.setId(id);
      gameBoard.setPieceOrder(pieceOrder);
      gameBoards.put(gameBoard.getId(), gameBoard);
      Log.d(TAG, "readed: " + gameBoard.toString());
    }
    database.close();
    return gameBoards;
  }

  public static void addGameBoard(GameBoard board){
    Log.d(TAG, "Adding to Runtime data");
    Log.d(TAG, board.toString());
    runTimeGameBoards.put(board.getId(), board);
    added.add(board.getId());
    notifyListeners();
  }

  private static void notifyListeners() {
    for (DataListener listener : listeners) {
      listener.onDataChanges(getGameBoards());
    }
  }

  public static void updataGameBoard(GameBoard board){
    Log.d(TAG, "Updating on Runtime data");
    Log.d(TAG, "OLD: " + runTimeGameBoards.get(board.getId()).toString() );
    Log.d(TAG, "NEW: " +board.toString() );

    runTimeGameBoards.remove(board.getId());
    runTimeGameBoards.put(board.getId(), board);
    updated.add(board.getId());
    notifyListeners();
  }

  public static void deleteGameBoard(GameBoard board){
    Log.d(TAG, "Deleting from Runtime data");
    Log.d(TAG, runTimeGameBoards.get(board.getId()).toString());
    runTimeGameBoards.remove(board.getId());
    deleted.add(board.getId());
    notifyListeners();
  }

  public static void onDestroy(){
    //sync array list modified on run time and database
    SQLiteDatabase database = databaseHelper.getWritableDatabase();
    executeDeletes(database);
    executeUpdates(database);
    executeAdds(database);
    database.close();
    cleanFields();
  }

  private static void cleanFields() {
    added.clear();
    updated.clear();
    deleted.clear();
  }

  private static void executeAdds(SQLiteDatabase database) {
    Gson gson = new Gson();
    for (Integer gameBoardId : added) {
      Log.d(TAG, "Adding " + runTimeGameBoards.get(gameBoardId).toString());
      GameBoard gameBoard = runTimeGameBoards.get(gameBoardId);
      ContentValues values = new ContentValues();
      Type type = gameBoard.getPuzzle().getType();
      values.put(Entry.PIECESX, type.getXPieceNumber());
      values.put(Entry.PIECESY, type.getYPieceNumber());
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      gameBoard.getPuzzle().getImage().compress(CompressFormat.PNG, 100, outputStream);
      values.put(Entry.IMAGE, outputStream.toByteArray());
      values.put(Entry.PIECE_ORDER, gson.toJson(gameBoard.getPieceOrder()));
      database.insert(GameBoardTable.TABLE_NAME, null, values);
    }
  }

  private static void executeUpdates(SQLiteDatabase database) {
    Gson gson = new Gson();
    for (Integer gameBoardId : updated) {
      Log.d(TAG, "Updating Game Board");
      Log.d(TAG, "NEW VALUE: " + runTimeGameBoards.get(gameBoardId).toString());
      GameBoard gameBoard = runTimeGameBoards.get(gameBoardId);
      ContentValues values = new ContentValues();
      values.put(Entry.PIECE_ORDER, gson.toJson(gameBoard.getPieceOrder()));
      database.update(GameBoardTable.TABLE_NAME, values, Entry.ID + " LIKE ?",
                      new String[]{gameBoardId.toString()});
    }
  }

  private static void executeDeletes(SQLiteDatabase database) {
    for (Integer gameBoardId : deleted) {
      Log.d(TAG, "Deleting " + databaseGameBoards.get(gameBoardId).toString());
      String selection = Entry.ID + " LIKE ?";
      String[] selectionArgs = new String[]{gameBoardId.toString()};
      database.delete(GameBoardTable.TABLE_NAME, selection, selectionArgs);
    }
  }

  public static ArrayList<GameBoard> getGameBoards(){
    ArrayList<GameBoard> gameBoards = new ArrayList<>();
    for (Map.Entry<Integer, GameBoard> integerGameBoardEntry : runTimeGameBoards.entrySet()) {
      gameBoards.add(integerGameBoardEntry.getValue());
    }
    return gameBoards;
  }
}
