package com.tongdao.demo;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class DataRealm extends RealmObject {

    private String btnJsonString;

    private String bkString;

    private String rewardJsonString;

    public String getBtnJsonString() {
        return btnJsonString;
    }

    public void setBtnJsonString(String btnJsonString) {
        this.btnJsonString = btnJsonString;
    }

    public String getBkString() {
        return bkString;
    }

    public void setBkString(String bkString) {
        this.bkString = bkString;
    }

    public String getRewardJsonString() {
        return rewardJsonString;
    }

    public void setRewardJsonString(String rewardJsonString) {
        this.rewardJsonString = rewardJsonString;
    }

}
