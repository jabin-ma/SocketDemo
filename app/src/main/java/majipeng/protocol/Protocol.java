package majipeng.protocol;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;

import majipeng.utils.Log;

// 协议基类
public class Protocol {

	public static final String HEADER_TYPE = "content-type";

	public static final String HEADER_FROM = "content-from";
	public static final String HEADER_TO = "content-to";

	public static final String PATH_LOGON = "/logon";

	public static final String PATH_PING = "/ping";

	private HashMap<String, String> mHeaders = new HashMap<>();
	private String content;

	public void addHeader(String name, String value) {
		mHeaders.put(name, value);
	}

	public void addHeader(Header header) {
		addHeader(header.name, header.value);
	}

	public void setHeaders(HashMap<String, String> requestHeader) {
		this.mHeaders.putAll(requestHeader);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public HashMap<String, String> getHeaders() {
		return mHeaders;
	}

	public void clearHeaders() {
		mHeaders.clear();
	}

	public String toJson() {
		return JSON.toJSONString(this);
	}

	public String toString() {
		return toJson();
	}

	public Header getHeader(String key) {
		Header resu = null;
		if (mHeaders.containsKey(key)) {
			resu = new Header();
			resu.name = key;
			resu.value = mHeaders.get(key);
		}
		return resu;
	}

	public String getHeaderValue(String key) {
		if (!mHeaders.containsKey(key))
			return null;
		return mHeaders.get(key);
	}

	public void setRequestLine(RequestLine reqline) {
		addHeader("request-line", JSON.toJSONString(reqline));
	}

	public RequestLine findRequestLine() {
		String reqline = getHeaderValue("request-line");
		if (reqline == null)
			return null;
		return JSON.parseObject(reqline, RequestLine.class);
	}

	public void setResponseLine(ResponseLine reqline) {
		addHeader("response-line", JSON.toJSONString(reqline));
	}

	public ResponseLine findResponseLine() {
		String reqline = getHeaderValue("response-line");
		if (reqline == null)
			return null;
		return JSON.parseObject(reqline, ResponseLine.class);
	}

	// 复用
	private static Protocol sPool;
	private static int sPoolSize = 0;
	private static final int MAX_POOL_SIZE = 50;
	private Protocol next;
	private static final Object sPoolSync = new Object();

	public static Protocol obtain() {
		synchronized (sPoolSync) {
			if (sPool != null) {
				Log.d("Message", "obtain");
				Protocol m = sPool;
				sPool = m.next;
				m.next = null;
				sPoolSize--;
				return m;
			}
		}
		Log.d("Message", "not obtain");
		return new Protocol();
	}

	public void recycle() {
		Log.d("Message", "recycle");
		setContent(null);
		clearHeaders();
		synchronized (sPoolSync) {
			if (sPoolSize < MAX_POOL_SIZE) {
				next = sPool;
				sPool = this;
				sPoolSize++;
			}
		}
	}

	public static final class MimeType {
		// 空数据
		public static final String NULL = "server/null";

		public static final String JSON = "text/json";
	}

}
