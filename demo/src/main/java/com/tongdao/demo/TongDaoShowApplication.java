package com.tongdao.demo;

import android.app.Application;

import com.tongdao.sdk.TongDao;

public class TongDaoShowApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TongDao.registerApplication(this);
    }

}
