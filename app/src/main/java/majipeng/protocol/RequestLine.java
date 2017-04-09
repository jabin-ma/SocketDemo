package majipeng.protocol;

import java.net.URISyntaxException;

public class RequestLine {
	// 请求方式
	String mothed;
	// 请求的url
	String url;

	public RequestLine(String mothed, String url) {
		super();
		this.mothed = mothed;
		this.url = url;
	}

	public String getMothed() {
		return mothed;
	}

	public void setMothed(String mothed) {
		this.mothed = mothed;
	}

	public String getUrl() {
		return url;
	}
	
	public java.net.URI parserURI(){
		try {
			return new java.net.URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public RequestLine() {
		super();
	}

	public static final class Mothed {
		//标准请求
		public static final String POST = "POST";
		
		public static final String OPTIONS="OPTIONS";
	}
}