package com.majipeng.wechat.handler;


import android.os.Messenger;
import android.util.Log;

import com.majipeng.wechat.service.MessengerService;

import io.netty.channel.ChannelHandlerContext;

public class ProtocolFilter extends IChannelInboundHandlerAdapter {
    static final String TAG = "MessageHandler";

    //必须传入一个callback,否则数据不知道往哪里发送
    public ProtocolFilter(Messenger callback) {
        super(callback);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        sendToCallBack(MessengerService.EVENT_CHANNEL_READ,msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG, "channelActive");
        sendToCallBack(MessengerService.EVENT_SOCKET_CONNECTED,ctx);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG, "channelInactive");
        sendToCallBack(MessengerService.EVENT_SOCKET_DISCONNECTED,ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.d(TAG,"exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}
