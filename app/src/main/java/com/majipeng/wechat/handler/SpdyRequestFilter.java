package com.majipeng.wechat.handler;

import android.os.Messenger;

import com.majipeng.wechat.service.MessengerService;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by majipeng on 4/9/17.
 */

public class SpdyRequestFilter extends IChannelInboundHandlerAdapter {

    public SpdyRequestFilter(Messenger callback) {
        super(callback);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        sendToCallBack(MessengerService.EVENT_CHANNEL_READ,msg);
    }

}
