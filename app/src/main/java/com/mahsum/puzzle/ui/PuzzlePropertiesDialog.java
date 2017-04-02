package com.mahsum.puzzle.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.mahsum.puzzle.R;


public class PuzzlePropertiesDialog extends DialogFragment {

  public void setImageUri(Uri imageUri) {
    this.imageUri = imageUri;
  }

  private Uri imageUri;
  @BindView(R.id.piecesX) EditText piecesX;
  @BindView(R.id.piecesY) EditText piecesY;
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    final LayoutInflater inflater = getActivity().getLayoutInflater();
    final View layout = inflater.inflate(R.layout.puzzle_properties_dialog, null);
    ButterKnife.bind(this, layout);

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setView(layout);
    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(inflater.getContext(), GameBoard.class);
        intent.putExtra(GameBoard.ORIGINAL_IMAGE_FILE_PATH, imageUri);
        intent.putExtra(GameBoard.PIECES_X, Integer.valueOf(piecesX.getText().toString()));
        intent.putExtra(GameBoard.PIECES_Y, Integer.valueOf(piecesY.getText().toString()));
        intent.putExtra(GameBoard.RESOLUTION_X, 1000);
        intent.putExtra(GameBoard.RESOLUTION_Y, 1000);
        startActivity(intent);
      }
    });
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    return builder.create();
  }

}
