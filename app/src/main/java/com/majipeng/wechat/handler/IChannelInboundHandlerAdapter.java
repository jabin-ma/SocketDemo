package com.majipeng.wechat.handler;

import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by majipeng on 2017/1/15.
 */

public class IChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private Messenger callback;

    public IChannelInboundHandlerAdapter(Messenger callback) {
        this.callback = callback;
    }


    public void sendToCallBack(int what){
        sendToCallBack(what,null);
    }


    public void sendToCallBack(int what,Object obj){
        Message msg=Message.obtain(null,what,obj);
        sendToCallBack(msg);
    }

    public void sendToCallBack(Message msg){
        try {
            callback.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();

        }
    }
}
