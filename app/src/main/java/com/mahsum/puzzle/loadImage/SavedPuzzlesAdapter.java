package com.mahsum.puzzle.loadImage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.core.GameBoard;
import com.mahsum.puzzle.gameboard.GameBoardActivity;
import com.mahsum.puzzle.gameboard.GameBoardPresenter;
import java.util.ArrayList;

class SavedPuzzlesAdapter extends RecyclerView.Adapter {

  private ArrayList<GameBoard> savedGames;
  private static Context context;

  public SavedPuzzlesAdapter(Context context, ArrayList<GameBoard> savedGames) {
    this.context = context;
    this.savedGames = savedGames;
  }

  public void setSavedGames(ArrayList<GameBoard> savedGames) {
    this.savedGames = savedGames;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View listItem = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.saved_puzzles_list_item, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ViewHolder viewHolder = (ViewHolder) holder;
    GameBoard gameBoard = savedGames.get(position);
    viewHolder.setGameBoard(gameBoard);
    viewHolder.setImage(gameBoard.getPuzzle().getImage());
  }

  @Override
  public int getItemCount() {
    return savedGames.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    private View listItem;
    private ImageButton imageButton;
    private GameBoard gameBoard;

    public ViewHolder(View listItem) {
      super(listItem);
      this.listItem = listItem;
      imageButton = (ImageButton) listItem.findViewById(R.id.image);
      imageButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          GameBoard.current = gameBoard;
          Intent intent = new Intent(SavedPuzzlesAdapter.context, GameBoardActivity.class);
          intent.putExtra("GAME_TYPE", GameBoardPresenter.PREVIOUS_GAME);
          context.startActivity(intent);
        }
      });
    }

    private void setImage(Bitmap image) {
      imageButton.setImageBitmap(image);
    }

    public void setGameBoard(GameBoard gameBoard) {
      this.gameBoard = gameBoard;
    }
  }
}
