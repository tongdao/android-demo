package com.tongdao.demo.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;
import android.util.JsonWriter;
import android.util.Log;

import com.tongdao.sdk.ui.TongDaoUiCore;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by agonch on 9/30/16.
 */

public class MyPushMessageReceiver extends PushMessageReceiver {

    private String mRegId;
    private long mResultCode = -1;
    private String mReason;
    private String mCommand;
    private String mMessage;
    private String mTopic;
    private String mAlias;
    private String mUserAccount;
    private String mStartTime;
    private String mEndTime;
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {

    }

    /**
     * User clicked the notification message. Open the corresponding deeplink/link
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        JSONObject customContent = new JSONObject(message.getExtra());
        String customContentString = customContent.toString();
        TongDaoUiCore.trackOpenPushMessage(customContentString);
        TongDaoUiCore.openPage(context, customContentString);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {

    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {

    }

    /**
     * User registration result received. Forward to TongDao server
     * @param context
     * @param message
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                TongDaoUiCore.identifyPushToken(mRegId);
            }
        }
    }

    /**
     * This call is here for debug purposes. When called it will print out the class variables.
     */
    private void printDebugInfo(){
        Log.d(getClass().getSimpleName(),
                "mMessage: " + mMessage + "\n" +
                "mReason: " + mReason + "\n" +
                "mCommand: " + mCommand + "\n" +
                "mRegId: " + mRegId + "\n" +
                "mTopic: " + mTopic + "\n" +
                "mAlias: " + mAlias + "\n" +
                "mUserAccount: " + mUserAccount + "\n" +
                "mStartTime: " + mStartTime + "\n" +
                "mEndTime: " + mEndTime + "\n");
    }

}
