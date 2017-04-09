package majipeng.protocol;

import java.net.URI;
import java.net.URISyntaxException;

public class UriUtils {
	public static final String SCHEME = "http";
	public static final String HOST = "theshy.xyz";
	
	public static URI create(String path){
		try {
			return new URI(SCHEME, HOST, path, null);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
