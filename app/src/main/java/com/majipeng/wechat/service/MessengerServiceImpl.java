package com.majipeng.wechat.service;

import android.os.Message;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.net.URISyntaxException;

import majipeng.protocol.Protocol;

/**
 * Created by majipeng on 2017/1/16.
 */

public class MessengerServiceImpl extends MessengerService {
    public static final String TAG = "MessengerServiceImpl";

    public static final int EVENT_LOGON = EVENT_LAST + 0x000001;

    @Override
    public void handleFromClientMessage(Message msg) {
     switch (msg.what){
         case EVENT_LOGON://User.class
             try {
                 postToSocket(Protocol.PATH_LOGON, JSON.toJSONString(msg.obj), Protocol.MimeType.JSON);
             } catch (URISyntaxException e) {
                 e.printStackTrace();
             }
             break;
          }
    }

    @Override
    public void handleFromProcessMessage(Message msg) {
        Log.d(TAG, "HandleFromPro:" + msg.obj);
    }

    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public void handleFromFilterMessage(Message msg) {
     Log.d(TAG,"HandlerFromeFilter"+msg);
    }
}
