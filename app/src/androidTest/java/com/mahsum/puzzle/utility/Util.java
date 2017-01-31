package com.mahsum.puzzle.utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.mahsum.puzzle.DummyPermissionActivity;

/**
 * Created by mahsum on 27.01.2017.
 */

public class Util {
    public static boolean grantPermission(Context appContext, String permission){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return PackageManager.PERMISSION_GRANTED == appContext.checkCallingOrSelfPermission(permission);
        }
        else
        {
            Intent intent = new Intent(DummyPermissionActivity.class);
            appContext.startActivity();
        }
    }
}
