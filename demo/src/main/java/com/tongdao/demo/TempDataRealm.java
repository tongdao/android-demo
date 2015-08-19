package com.tongdao.demo;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class TempDataRealm extends RealmObject{
	
	private String rewardJsonString;
	
	public String getRewardJsonString() {
		return rewardJsonString;
	}

	public void setRewardJsonString(String rewardJsonString) {
		this.rewardJsonString = rewardJsonString;
	}

}
