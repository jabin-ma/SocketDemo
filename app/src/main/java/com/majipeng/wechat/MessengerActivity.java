package com.majipeng.wechat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.majipeng.wechat.service.MessengerService;
import com.majipeng.wechat.service.MessengerServiceImpl;

/**
 * Created by majipeng on 2017/1/15.
 */

public abstract class MessengerActivity extends Activity implements ServiceConnection {

    private Messenger messengerService;


    private Messenger replyTo = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindMessengerService();//先绑定service
    }


    void bindMessengerService() {
        Intent intent = new Intent();
        intent.setClass(this, MessengerServiceImpl.class);
        bindService(intent, this, BIND_AUTO_CREATE);
    }


    /**
     * Called when a connection to the Service has been established, with
     * the {@link IBinder} of the communication channel to the
     * Service.
     *
     * @param name    The concrete component name of the service that has
     *                been connected.
     * @param service The IBinder of the Service's communication channel,
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        messengerService = new Messenger(service);//messenger已经创建
        if (service != null) {
            Message initMessage = Message.obtain();
//            initMessage.what= MessengerService.CLIENT.ACTION_SETCLIENT;
//            send(initMessage);
            onMessengerServiceCreated();
        }
    }


    /**
     * Called when a connection to the Service has been lost.  This typically
     * happens when the process hosting the service has crashed or been killed.
     * This does <em>not</em> remove the ServiceConnection itself -- this
     * binding to the service will remain active, and you will receive a call
     * to {@link #onServiceConnected} when the Service is next running.
     *
     * @param name The concrete component name of the service whose
     *             connection has been lost.
     */
    @Override
    public void onServiceDisconnected(ComponentName name) {
        onMessengerServiceDestroy();
        messengerService = null;
    }

    public abstract void onMessengerServiceCreated();


    public abstract void onMessengerServiceDestroy();

    public abstract void handleServiceReply(Message message);



    public void sendToService(Message message) {
        message.replyTo = replyTo;
        try {
            messengerService.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e(this.getClass().getSimpleName(), "send msg fail!!!!!");
            throw new NullPointerException();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(this);
    }
}
