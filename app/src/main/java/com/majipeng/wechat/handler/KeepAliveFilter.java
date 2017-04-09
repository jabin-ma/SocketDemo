package com.majipeng.wechat.handler;

import android.util.Log;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import majipeng.utils.RequestUtils;

public class KeepAliveFilter extends ChannelDuplexHandler {
    static final String TAG = KeepAliveFilter.class.getSimpleName();

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                Log.e(TAG, "超时!!!!!!");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                Log.d(TAG,"发送ping");
                RequestUtils.sendPing(ctx);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
