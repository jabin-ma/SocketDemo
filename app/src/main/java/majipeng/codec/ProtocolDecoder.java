package majipeng.codec;

import java.util.List;

import com.alibaba.fastjson.JSON;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import majipeng.protocol.Protocol;
import majipeng.utils.Log;

public class ProtocolDecoder extends MessageToMessageDecoder<String> {
	public static final String TAG = "RequestDecoder";
	public boolean addHeader=false;
	
	public ProtocolDecoder(boolean addHeader) {
		super();
		this.addHeader = addHeader;
	}



	@Override
	protected void decode(ChannelHandlerContext context, String msg, List<Object> out) throws Exception {
		try {
			Protocol request = JSON.parseObject(msg, Protocol.class);
			if(addHeader)request.addHeader(Protocol.HEADER_FROM,context.channel().id().asLongText());//消息来自哪里
			out.add(request);
		} catch (Exception e) {
			Log.d(TAG, "解码失败,消息将被舍弃:" + msg);
			out.add(msg);
		}
	}
}
