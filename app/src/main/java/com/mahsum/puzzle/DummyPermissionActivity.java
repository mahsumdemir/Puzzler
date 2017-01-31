package com.mahsum.puzzle;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class DummyPermissionActivity extends Activity{

    private static final int SUCCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String PERMISSION = getIntent().getStringExtra("PERMISSION");
        ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, SUCCESS);
    }
}
