package database;

public class GameBoardTable {


  interface Entry{
    String ID = "id";
    String IMAGE = "image";
    String PIECE_ORDER = "pieceOrder";
    String PIECESX = "piecesX";
    String PIECESY = "piecesY";
  }

  public static final String TABLE_NAME = "gameboard";
  public static final String CREATE =
      "CREATE TABLE " + TABLE_NAME + "( " +
          Entry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
          Entry.IMAGE + " BLOB," +
          Entry.PIECE_ORDER + " STRING," +
          Entry.PIECESX + " INTEGER," +
          Entry.PIECESY + " INTEGER" + ")";

  public static final String DELETE = "DROP TABLE IF EXIST " + TABLE_NAME;


}
