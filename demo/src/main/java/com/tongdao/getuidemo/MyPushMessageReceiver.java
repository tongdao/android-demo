package com.tongdao.getuidemo;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.tongdao.sdk.ui.TongDaoUiCore;


public class MyPushMessageReceiver extends BroadcastReceiver {

    public static final String TAG = "GetUISdkReceiver";

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
//    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");

                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");

                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                System.out.println(TAG + "第三方回执接口调用" + (result ? "成功" : "失败"));

                if (payload != null) {
                    String data = new String(payload);
                    TongDaoUiCore.trackOpenPushMessage(data);
                    TongDaoUiCore.openPage(context, data);
                }
                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                Log.d(TAG,"CID: " + cid);
                TongDaoUiCore.identifyPushToken(cid);
                break;
            case PushConsts.GET_SDKONLINESTATE:
                boolean online = bundle.getBoolean("onlineState");
                Log.d(TAG, "online = " + online);
                break;

            case PushConsts.SET_TAG_RESULT:
                String sn = bundle.getString("sn");
                String code = bundle.getString("code");

                String text = "设置标签失败, 未知异常";
                switch (Integer.valueOf(code)) {
                    case PushConsts.SETTAG_SUCCESS:
                        text = "设置标签成功";
                        break;

                    case PushConsts.SETTAG_ERROR_COUNT:
                        text = "设置标签失败, tag数量过大, 最大不能超过200个";
                        break;

                    case PushConsts.SETTAG_ERROR_FREQUENCY:
                        text = "设置标签失败, 频率过快, 两次间隔应大于1s";
                        break;

                    case PushConsts.SETTAG_ERROR_REPEAT:
                        text = "设置标签失败, 标签重复";
                        break;

                    case PushConsts.SETTAG_ERROR_UNBIND:
                        text = "设置标签失败, 服务未初始化成功";
                        break;

                    case PushConsts.SETTAG_ERROR_EXCEPTION:
                        text = "设置标签失败, 未知异常";
                        break;

                    case PushConsts.SETTAG_ERROR_NULL:
                        text = "设置标签失败, tag 为空";
                        break;

                    case PushConsts.SETTAG_NOTONLINE:
                        text = "还未登陆成功";
                        break;

                    case PushConsts.SETTAG_IN_BLACKLIST:
                        text = "该应用已经在黑名单中,请联系售后支持!";
                        break;

                    case PushConsts.SETTAG_NUM_EXCEED:
                        text = "已存 tag 超过限制";
                        break;

                    default:
                        break;
                }

                Log.d(TAG, "settag result sn = " + sn + ", code = " + code);
                Log.d(TAG, "settag result sn = " + text);
                break;
            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 *
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }









//    /**
//     * TAG to Log
//     */
//    public static final String TAG = "TongDao Message";
//
//    /**
//     * 调用PushManager.startWork后，sdk将对push
//     * server发起绑定请求，这个过程是异步的。绑定请求的结果通过onBind返回。 如果您需要用单播推送，需要把这里获取的channel
//     * id和user id上传到应用server中，再调用server接口用channel id和user id给单个手机或者用户推送。
//     *
//     * @param context   BroadcastReceiver的执行Context
//     * @param errorCode 绑定接口返回值，0 - 成功
//     * @param appid     应用id。errorCode非0时为null
//     * @param userId    应用user id。errorCode非0时为null
//     * @param channelId 应用channel id。errorCode非0时为null
//     * @param requestId 向服务端发起的请求id。在追查问题时有用；
//     * @return none
//     */
//    @Override
//    public void onBind(Context context, int errorCode, String appid,
//                       String userId, String channelId, String requestId) {
//        String responseString = "onBind errorCode=" + errorCode + " appid="
//                + appid + " userId=" + userId + " channelId=" + channelId
//                + " requestId=" + requestId;
//        Log.e(TAG, responseString);
//
//        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        // updateContent(context, responseString);
//
//        if (channelId != null) {
//            TongDaoUiCore.identifyPushToken(channelId);
//            Log.e("Channel id", channelId);
//        }
//    }
//
//    /**
//     * 接收透传消息的函数。
//     *
//     * @param context             上下文
//     * @param message             推送的消息
//     * @param customContentString 自定义内容,为空或者json字符串
//     */
//    @Override
//    public void onMessage(Context context, String message,
//                          String customContentString) {
//        String messageString = "透传消息 message=\"" + message
//                + "\" customContentString=" + customContentString;
//        Log.e(TAG, messageString);
//        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        // updateContent(context, messageString);
//    }
//
//    /**
//     * 接收通知点击的函数。注：推送通知被用户点击前，应用无法通过接口获取通知的内容。
//     *
//     * @param context             上下文
//     * @param title               推送的通知的标题
//     * @param description         推送的通知的描述
//     * @param customContentString 自定义内容，为空或者json字符串
//     */
//    @Override
//    public void onNotificationClicked(Context context, String title,
//                                      String description, String customContentString) {
////		String notifyString = "通知点击 title=\"" + title + "\" description=\""
////				+ description + "\" customContent=" + customContentString;
////		Log.d(TAG, notifyString);
//
//        TongDaoUiCore.trackOpenPushMessage(customContentString);
//        TongDaoUiCore.openPage(context, customContentString);
//        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        // updateContent(context, notifyString);
//    }
//
//    /**
//     * setTags() 的回调函数。
//     *
//     * @param context     上下文
//     * @param errorCode   错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
//     * @param successTags 设置成功的tag
//     * @param failTags    设置失败的tag
//     * @param requestId   分配给对云推送的请求的id
//     */
//    @Override
//    public void onSetTags(Context context, int errorCode,
//                          List<String> sucessTags, List<String> failTags, String requestId) {
////		String responseString = "onSetTags errorCode=" + errorCode
////				+ " sucessTags=" + sucessTags + " failTags=" + failTags
////				+ " requestId=" + requestId;
////		Log.d(TAG, responseString);
//
//        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        // updateContent(context, responseString);
//    }
//
//    /**
//     * delTags() 的回调函数。
//     *
//     * @param context     上下文
//     * @param errorCode   错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
//     * @param successTags 成功删除的tag
//     * @param failTags    删除失败的tag
//     * @param requestId   分配给对云推送的请求的id
//     */
//    @Override
//    public void onDelTags(Context context, int errorCode,
//                          List<String> sucessTags, List<String> failTags, String requestId) {
////		String responseString = "onDelTags errorCode=" + errorCode
////				+ " sucessTags=" + sucessTags + " failTags=" + failTags
////				+ " requestId=" + requestId;
////		Log.d(TAG, responseString);
//
//        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        // updateContent(context, responseString);
//    }
//
//    /**
//     * listTags() 的回调函数。
//     *
//     * @param context   上下文
//     * @param errorCode 错误码。0表示列举tag成功；非0表示失败。
//     * @param tags      当前应用设置的所有tag。
//     * @param requestId 分配给对云推送的请求的id
//     */
//    @Override
//    public void onListTags(Context context, int errorCode, List<String> tags,
//                           String requestId) {
////		String responseString = "onListTags errorCode=" + errorCode + " tags="
////				+ tags;
////		Log.d(TAG, responseString);
//
//        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        // updateContent(context, responseString);
//    }
//
//    /**
//     * PushManager.stopWork() 的回调函数。
//     *
//     * @param context   上下文
//     * @param errorCode 错误码。0表示从云推送解绑定成功；非0表示失败。
//     * @param requestId 分配给对云推送的请求的id
//     */
//    @Override
//    public void onUnbind(Context context, int errorCode, String requestId) {
//        String responseString = "onUnbind errorCode=" + errorCode
//                + " requestId = " + requestId;
//        Log.d(TAG, responseString);
//
//        // 解绑定成功，设置未绑定flag，
//        // if (errorCode == 0) {
//        // Utils.setBind(context, false);
//        // }
//        // Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
//        // updateContent(context, responseString);
//    }
//
//    @Override
//    public void onNotificationArrived(Context context, String title,
//                                      String description, String customContentString) {
//        String notifyString = "onNotificationArrived  title=\"" + title
//                + "\" description=\"" + description + "\" customContent="
//                + customContentString;
//        Log.e(TAG, notifyString);
//
//    }

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
