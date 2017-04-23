package com.mahsum.puzzle.ui;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.mahsum.puzzle.R;

public class PermissionFragment extends Fragment{

  private String[] neededPermissions;
  private PermissionCallbacks callbacks;
  public PermissionFragment(){

  }

  public interface PermissionCallbacks{
    void onPermissionsGranted();
  }

  public static Fragment newInstance(String[] neededPermissions) {
    PermissionFragment fragment = new PermissionFragment();
    fragment.neededPermissions = neededPermissions;
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           Bundle savedInstanceState) {
    callbacks = (PermissionCallbacks) getActivity();
    View view = inflater.inflate(R.layout.fragment_permission, container, false);
    Button button = (Button) view.findViewById(R.id.grantPermission);
    button.setOnClickListener(new OnClickListener() {

      @RequiresApi(api = VERSION_CODES.M)
      @Override
      public void onClick(View v) {
        requestPermissions(neededPermissions, 0);
      }
    });
    return view;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    if (grantResults.length > 0){
      boolean granted = true;
      for (int grantResult : grantResults) {
        if (grantResult != PackageManager.PERMISSION_GRANTED)
          granted = false;
      }

      if (granted) callbacks.onPermissionsGranted();

    }
  }
}
