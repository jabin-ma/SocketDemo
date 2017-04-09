package majipeng.protocol;

public class ResponseLine {
	private int code;
	private String state;

	public ResponseLine() {
		super();
	}

	public ResponseLine(int code, String state) {
		super();
		this.code = code;
		this.state = state;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
