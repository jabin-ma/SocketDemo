package majipeng.utils;

import java.net.URISyntaxException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import majipeng.protocol.Header;
import majipeng.protocol.Protocol;
import majipeng.protocol.Protocol.MimeType;
import majipeng.protocol.RequestLine;
import majipeng.protocol.RequestLine.Mothed;
import majipeng.protocol.UriUtils;

public class RequestUtils {

	public static void send(ChannelHandlerContext context, String mothed, java.net.URI url, String content,
			Header... headers) {
		final Protocol request = Protocol.obtain();
		request.setRequestLine(new RequestLine(mothed, url.toString()));
		if (headers != null) {
			for (Header s : headers) {
				request.addHeader(s);
			}
		}
		request.setContent(content);
		send(context, request);
	}

	public static void sendPing(ChannelHandlerContext context) throws URISyntaxException {
		send(context, Mothed.OPTIONS, UriUtils.create(Protocol.PATH_PING), null,
				new Header(Protocol.HEADER_TYPE, MimeType.NULL));
	}

	public static void send(ChannelHandlerContext context, final Protocol request) {
		send(context.channel(), request);
	}

	public static void send(Channel ch, final Protocol request) {
		ch.writeAndFlush(request.toJson()).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture arg0) throws Exception {
				request.recycle();
			}
		});
	}
}
