package com.mahsum.puzzle.loadImage;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.mahsum.puzzle.R;

public class PickImageActivity extends AppCompatActivity {
  private static final int PICK_PHOTO = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pick_image_activity);
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.add(R.id.root, PickImageFragment.newInstance(), null);
    transaction.commit();
  }
}
