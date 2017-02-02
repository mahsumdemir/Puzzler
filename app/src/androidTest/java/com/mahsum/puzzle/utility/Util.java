package com.mahsum.puzzle.utility;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.mahsum.puzzle.DummyPermissionActivity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class Util {
    private static final int GRANT_BUTTON_INDEX = 1;

    public static boolean grantPermission(Context appContext, String permission){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return checkPermission(appContext, permission);
        }
        else
        {
            startActivityToHostPermissionDialog(appContext, permission);
            return checkPermission(appContext, permission);
        }
    }

    private static void startActivityToHostPermissionDialog(Context appContext, String permission) {
        Intent intent = new Intent(appContext, DummyPermissionActivity.class);
        intent.putExtra("PERMISSION", permission);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clickAllowPermission();
    }

    private static void clickAllowPermission() {
        try
        {
            UiDevice device = UiDevice.getInstance(getInstrumentation());
            UiObject allowPermissions = device.findObject(new UiSelector()
                    .clickable(true)
                    .checkable(false)
                    .index(GRANT_BUTTON_INDEX));
            if (allowPermissions.exists()) {
                allowPermissions.click();
            }
        }
        catch (UiObjectNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static boolean checkPermission(Context appContext, String permission) {
        return PackageManager.PERMISSION_GRANTED == appContext.checkCallingOrSelfPermission(permission);
    }
}
