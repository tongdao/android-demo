package com.tongdao.getuidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.igexin.sdk.PushManager;
import com.tongdao.sdk.beans.TdRewardBean;
import com.tongdao.sdk.interfaces.ui.OnRewardUnlockedListener;
import com.tongdao.sdk.ui.TongDaoUiCore;

import org.json.JSONException;

import java.util.ArrayList;

public class DemoPage1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().setIcon(R.drawable.ic_launcher);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getSupportActionBar().setDisplayShowTitleEnabled(true);
        this.getSupportActionBar().setTitle("Deep linking");
        this.getSupportActionBar().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.bar));
        this.setContentView(R.layout.page);
        ((TextView) this.findViewById(R.id.link_tv)).setText("demo://page1");

        this.registerListeners();
        TongDaoUiCore.displayAdvertisement(this);
        PushManager.getInstance().initialize(this.getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        TongDaoUiCore.onSessionStart(this);
        TongDaoUiCore.displayInAppMessage(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TongDaoUiCore.onSessionEnd(this);
    }

    private void registerListeners() {
        TongDaoUiCore
                .registerOnRewardUnlockedListener(new OnRewardUnlockedListener() {
                    @Override
                    public void onSuccess(ArrayList<TdRewardBean> rewards) {
                        if (rewards != null && rewards.size() > 0) {
                            try {
                                DataTool.saveTempRewards(DemoPage1.this, rewards);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}