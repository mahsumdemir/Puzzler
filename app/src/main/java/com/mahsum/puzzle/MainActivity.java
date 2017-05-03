package com.mahsum.puzzle;

import android.Manifest.permission;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import com.mahsum.puzzle.loadImage.PickImageFragment;
import com.mahsum.puzzle.ui.PermissionFragment;
import com.mahsum.puzzle.ui.PuzzlePropertiesDialog;
import com.yalantis.ucrop.UCrop;
import database.DatabaseInterface;


public class MainActivity extends Activity implements PermissionFragment.PermissionCallbacks{

  private static final String[] NEEDED_PERMISSIONS = new String[]{permission.WRITE_EXTERNAL_STORAGE,
      permission.READ_EXTERNAL_STORAGE};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.pick_image_activity);
    DatabaseInterface.init(getApplicationContext());

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
    //Handle Image Cropping Library Response
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

  @Override
  protected void onDestroy() {
    super.onDestroy();
    DatabaseInterface.end();
  }
}
