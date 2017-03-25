package com.mahsum.puzzle.loadImage;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.ui.GameBoard;

public class PickImageActivity extends AppCompatActivity implements Contract.View {

  private static final int PICK_PHOTO = 1;
  private ImageView mImageView;

  private Contract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pick_image_activity);
    presenter = new MainActivityPresenter(this);

    mImageView = (ImageView) findViewById(R.id.imageView);
    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        presenter.startImageChoosing();
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data != null){
      Intent intent = new Intent(getApplicationContext(), GameBoard.class);
      intent.putExtra(GameBoard.ORIGINAL_IMAGE_FILE_PATH, data.getData());
      intent.putExtra(GameBoard.PIECES_X, 10);
      intent.putExtra(GameBoard.PIECES_Y, 10);
      intent.putExtra(GameBoard.RESOLUTION_X, 800);
      intent.putExtra(GameBoard.RESOLUTION_Y, 1400);
      startActivity(intent);
    }
  }

  @Override
  public void showImage(Bitmap image) {
    mImageView.setImageBitmap(image);
  }
}
