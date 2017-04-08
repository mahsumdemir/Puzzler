package com.mahsum.puzzle.loadImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.ui.PuzzlePropertiesDialog;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class PickImageActivity extends AppCompatActivity implements Contract.View {

  @BindView(R.id.button) Button button;
  @BindView(R.id.imageView) ImageView imageView;

  private static final int PICK_PHOTO = 1;
  private Contract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pick_image_activity);
    ButterKnife.bind(this);
    presenter = new MainActivityPresenter(this);
  }

  @OnClick(R.id.button)
  public void buttonClicked(View v){
    presenter.startImageChoosing();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) return;
    Observable<Intent> imageUri = Observable.just(data);

    imageUri.subscribe(new Consumer<Intent>() {
      @Override
      public void accept(@NonNull Intent data) throws Exception {
        PuzzlePropertiesDialog dialog = new PuzzlePropertiesDialog();
        dialog.setImageUri(data.getData());
        dialog.show(getFragmentManager(), "MY DIALOG");
      }
    });
  }

  @Override
  public void showImage(Bitmap image) {
    imageView.setImageBitmap(image);
  }
}
