package com.majipeng.wechat.handler;

import android.os.Messenger;
import android.util.Log;

import java.nio.charset.Charset;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import majipeng.codec.ProtocolDecoder;
import majipeng.codec.ProtocolEncoder;


public class ChannelInit extends ChannelInitializer<SocketChannel> {
	static final String TAG="ChanelInit";
    EventExecutorGroup mThreadPool=new DefaultEventExecutorGroup(1);//16线程处理

    private  Messenger callback;


    public ChannelInit(Messenger callback) {
        this.callback = callback;
    }


    @Override
	protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline ppl=sc.pipeline();

        ppl.addLast(new StringDecoder(Charset.forName("UTF-8")));//字符串解码
        ppl.addLast(new ProtocolDecoder(false));//解码
        ppl.addLast(new StringEncoder(Charset.forName("UTF-8")));//编码
        ppl.addLast(new ProtocolEncoder());

     	ppl.addLast(new IdleStateHandler(25,20,25));//检测心跳超时
        ppl.addLast(new KeepAliveFilter());
        ppl.addLast(mThreadPool, new ProtocolFilter(callback));//消息处理器
        Log.d(TAG, "Channel is open:"+sc);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.d(TAG,"exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}
