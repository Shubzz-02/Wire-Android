package com.shubzz.wire;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

public class UnhideBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if (phoneNumber.equals("*0000#")){
            unhideApp(context);
            Intent intent2 = new Intent();
            intent2.setClass(context, MainActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent2);
        }
        Log.d(UnhideBroadcastReceiver.class.getSimpleName(), phoneNumber);
    }

    private void unhideApp(Context context){
        PackageManager p = context.getPackageManager();
        ComponentName componentName = new ComponentName("com.shubzz.wire", "com.shubzz.wire.MainActivity.class");
        p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

    }
}
