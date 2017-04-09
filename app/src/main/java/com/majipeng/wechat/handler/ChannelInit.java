package com.majipeng.wechat.handler;

import android.os.Messenger;
import android.util.Log;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.spdy.SpdyFrameCodec;
import io.netty.handler.codec.spdy.SpdyHttpDecoder;
import io.netty.handler.codec.spdy.SpdyHttpEncoder;
import io.netty.handler.codec.spdy.SpdyVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;


public class ChannelInit extends ChannelInitializer<SocketChannel> {
	static final String TAG="ChanelInit";
    EventExecutorGroup mThreadPool=new DefaultEventExecutorGroup(1);//16线程处理

    private static final int MAX_SPDY_CONTENT_LENGTH=1024*1024;//1mb



    private  Messenger callback;


    public ChannelInit(Messenger callback) {
        this.callback = callback;
    }


    @Override
	protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline ppl=sc.pipeline();
        //self
        /*ppl.addLast(new StringDecoder(Charset.forName("UTF-8")));//字符串解码
        ppl.addLast(new ProtocolDecoder(false));//解码
        ppl.addLast(new StringEncoder(Charset.forName("UTF-8")));//编码
        ppl.addLast(new ProtocolEncoder());*/

        //spdy
        ppl.addLast(new LoggingHandler(LogLevel.DEBUG));
        ppl.addLast(new SpdyFrameCodec(SpdyVersion.SPDY_3_1));
        ppl.addLast(new SpdyHttpEncoder(SpdyVersion.SPDY_3_1));
        ppl.addLast(new SpdyHttpDecoder(SpdyVersion.SPDY_3_1,MAX_SPDY_CONTENT_LENGTH));

        ppl.addLast(new ServerStateFilter(callback));
     	ppl.addLast(new IdleStateHandler(25,20,25));//检测心跳超时
        ppl.addLast(new KeepAliveFilter());
        ppl.addLast(mThreadPool, new SpdyRequestFilter(callback));//消息处理器
        Log.d(TAG, "Channel is open:"+sc);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.d(TAG,"exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }
}
