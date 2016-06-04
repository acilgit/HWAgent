package com.housingonitoringagent.homeworryagent.utils;


import android.content.pm.PackageManager;

import com.housingonitoringagent.homeworryagent.App;

public class PermissionUtil {

    public static boolean check(String permission) {
        App context = App.getInstance();

        int value = context.checkCallingOrSelfPermission(permission);

        return value == PackageManager.PERMISSION_GRANTED;
    }
}
