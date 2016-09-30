package com.tongdao.demo;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.tongdao.sdk.ui.TongDaoUiCore;

/**
 * Push消息处理receiver。请编写您需要的回调函数， 一般来说： onBind是必须的，用来处理startWork返回值；
 * onMessage用来接收透传消息； onSetTags、onDelTags、onListTags是tag相关操作的回调；
 * onNotificationClicked在通知被点击时回调； onUnbind是stopWork接口的返回值回调
 * <p/>
 * 返回值中的errorCode，解释如下： 0 - Success 10001 - Network Problem 30600 - Internal
 * Server Error 30601 - Method Not Allowed 30602 - Request Params Not Valid
 * 30603 - Authentication Failed 30604 - Quota Use Up Payment Required 30605 -
 * Data Required Not Found 30606 - Request Time Expires Timeout 30607 - Channel
 * Token Timeout 30608 - Bind Relation Not Found 30609 - Bind Number Too Many
 * <p/>
 * 当您遇到以上返回错误时，如果解释不了您的问题，请用同一请求的返回值requestId和errorCode联系我们追查问题。
 */
public class MyPushMessageReceiver {
    /**
     * TAG to Log
     */
    public static final String TAG = "TongDao Message";

    /**
     * 调用PushManager.startWork后，sdk将对push
     * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
     * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
     *
     * @param context   BroadcastReceiver的执行Context
     * @param errorCode 绑定接口返回值，0 - 成功
     * @param appid     应用id。errorCode非0时为null
     * @param userId    应用user id。errorCode非0时为null
     * @param channelId 应用channel id。errorCode非0时为null
     * @param requestId 向服务端发起的请求id。在追查问题时有用；
     * @return none
     */
//    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.e(TAG, responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        // updateContent(context, responseString);

        if (channelId != null) {
            TongDaoUiCore.identifyPushToken(channelId);
            Log.e("Channel id", channelId);
        }
    }

    /**
     * 接收透传消息的函数。
     *
     * @param context             上下文
     * @param message             推送的消息
     * @param customContentString 自定义内容,为空或者json字符串
     */
//    @Override
    public void onMessage(Context context, String message,
                          String customContentString) {
        String messageString = "透传消息 message=\"" + message
                + "\" customContentString=" + customContentString;
        Log.e(TAG, messageString);
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        // updateContent(context, messageString);
    }

    /**
     * 接收通知点击的函数。注：推送通知被用户点击前，应用无法通过接口获取通知的内容。
     *
     * @param context             上下文
     * @param title               推送的通知的标题
     * @param description         推送的通知的描述
     * @param customContentString 自定义内容，为空或者json字符串
     */
//    @Override
    public void onNotificationClicked(Context context, String title,
                                      String description, String customContentString) {
//		String notifyString = "通知点击 title=\"" + title + "\" description=\""
//				+ description + "\" customContent=" + customContentString;
//		Log.d(TAG, notifyString);

        TongDaoUiCore.trackOpenPushMessage(customContentString);
        TongDaoUiCore.openPage(context, customContentString);
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        // updateContent(context, notifyString);
    }

    /**
     * setTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
     * @param failTags    设置失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
//    @Override
    public void onSetTags(Context context, int errorCode,
                          List<String> sucessTags, List<String> failTags, String requestId) {
//		String responseString = "onSetTags errorCode=" + errorCode
//				+ " sucessTags=" + sucessTags + " failTags=" + failTags
//				+ " requestId=" + requestId;
//		Log.d(TAG, responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        // updateContent(context, responseString);
    }

    /**
     * delTags() 的回调函数。
     *
     * @param context     上下文
     * @param errorCode   错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
     * @param failTags    删除失败的tag
     * @param requestId   分配给对云推送的请求的id
     */
//    @Override
    public void onDelTags(Context context, int errorCode,
                          List<String> sucessTags, List<String> failTags, String requestId) {
//		String responseString = "onDelTags errorCode=" + errorCode
//				+ " sucessTags=" + sucessTags + " failTags=" + failTags
//				+ " requestId=" + requestId;
//		Log.d(TAG, responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        // updateContent(context, responseString);
    }

    /**
     * listTags() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示列举tag成功；非0表示失败。
     * @param tags      当前应用设置的所有tag。
     * @param requestId 分配给对云推送的请求的id
     */
//    @Override
    public void onListTags(Context context, int errorCode, List<String> tags,
                           String requestId) {
//		String responseString = "onListTags errorCode=" + errorCode + " tags="
//				+ tags;
//		Log.d(TAG, responseString);

        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        // updateContent(context, responseString);
    }

    /**
     * PushManager.stopWork() 的回调函数。
     *
     * @param context   上下文
     * @param errorCode 错误码。0表示从云推送解绑定成功；非0表示失败。
     * @param requestId 分配给对云推送的请求的id
     */
//    @Override
    public void onUnbind(Context context, int errorCode, String requestId) {
        String responseString = "onUnbind errorCode=" + errorCode
                + " requestId = " + requestId;
        Log.d(TAG, responseString);

        // 解绑定成功，设置未绑定flag，
        // if (errorCode == 0) {
        // Utils.setBind(context, false);
        // }
        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
        // updateContent(context, responseString);
    }

//    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.e(TAG, notifyString);

    }

    // private void updateContent(Context context, String content) {
    // Log.d(TAG, "updateContent");
    // String logText = "" + Utils.logStringCache;
    //
    // if (!logText.equals("")) {
    // logText += "\n";
    // }
    //
    // SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss");
    // logText += sDateFormat.format(new Date()) + ": ";
    // logText += content;
    //
    // Utils.logStringCache = logText;
    //
    // Intent intent = new Intent();
    // intent.setClass(context.getApplicationContext(), PushDemoActivity.class);
    // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    // context.getApplicationContext().startActivity(intent);
    // }

}
