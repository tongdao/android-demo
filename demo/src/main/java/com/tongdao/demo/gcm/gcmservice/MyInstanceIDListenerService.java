package com.tongdao.demo.gcm.gcmservice;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;
import com.tongdao.demo.gcm.RegistrationIntentService;


public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static String TAG = MyInstanceIDListenerService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
}