package majipeng.codec;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import majipeng.protocol.Protocol;

public class ProtocolEncoder extends MessageToMessageEncoder<Protocol> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Protocol msg, List<Object> out) throws Exception {
		out.add(msg.toJson());//已经解码
	}
}
