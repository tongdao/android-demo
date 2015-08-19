package com.tongdao.demo;

import com.baidu.frontia.FrontiaApplication;
import com.tongdao.sdk.ui.TongDaoUiCore;

public class TongDaoShowApplication extends FrontiaApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        TongDaoUiCore.init(getApplicationContext(), DataTool.APP_KEY);
    }

}
