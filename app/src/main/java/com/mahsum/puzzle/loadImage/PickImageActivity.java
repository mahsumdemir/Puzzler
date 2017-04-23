package com.mahsum.puzzle.loadImage;

import android.Manifest.permission;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import com.mahsum.puzzle.R;
import com.mahsum.puzzle.ui.PermissionFragment;
import com.mahsum.puzzle.ui.PuzzlePropertiesDialog;
import com.yalantis.ucrop.UCrop;

public class PickImageActivity extends AppCompatActivity implements PermissionFragment.PermissionCallbacks{

  private static final int PICK_PHOTO = 1;
  private static final String[] NEEDED_PERMISSIONS = new String[]{permission.WRITE_EXTERNAL_STORAGE,
      permission.READ_EXTERNAL_STORAGE};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pick_image_activity);

    if (hasNeededPermission()) {
      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      transaction.add(R.id.root, PickImageFragment.newInstance(), null);
      transaction.commit();
    }
    else{
      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      transaction.add(R.id.root, PermissionFragment.newInstance(NEEDED_PERMISSIONS), null);
      transaction.commit();
    }

  }

  private boolean hasNeededPermission() {
    for (String neededPermission : NEEDED_PERMISSIONS) {
      if (ActivityCompat.checkSelfPermission(this, neededPermission)
          != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }
    return true;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
      final Uri resultUri = UCrop.getOutput(data);
      PuzzlePropertiesDialog dialog = new PuzzlePropertiesDialog();
      dialog.setImageUri(resultUri);
      dialog.show(getFragmentManager(), "MY DIALOG");
    }
    else if (resultCode == UCrop.RESULT_ERROR) {
      final Throwable cropError = UCrop.getError(data);
      cropError.printStackTrace();
    }
  }

  @Override
  public void onPermissionsGranted() {
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    transaction.replace(R.id.root, PickImageFragment.newInstance());
    transaction.commit();
  }
}
