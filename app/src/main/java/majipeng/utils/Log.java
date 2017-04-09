package majipeng.utils;

public class Log {
	public static void d(String tag, String message) {
		out("d", tag,formatMessage(message));
	}

	public static void w(String tag, String message) {
		out("w", tag, formatMessage(message));
	}

	public static void e(String tag, String message) {
		out("e", tag,formatMessage(message));
	}

	public static void i(String tag, String message) {
		out("i", tag, formatMessage(message));
	}

	
	
	
	
	private static String formatMessage(String message){
		StringBuilder sb=new StringBuilder();
		StackTraceElement caller=new Throwable().getStackTrace()[2];
		sb.append(" <");
		sb.append(caller.getMethodName());
		sb.append("-");
		sb.append(caller.getLineNumber());
		sb.append("> ");
		sb.append(message);
		return sb.toString();
	}
	
	
	public static void d(String tag, String message, Exception e) {
		out("d", tag, message);
	}

	public static void w(String tag, String message, Exception e) {
		out("w", tag, message);
	}

	public static void e(String tag, String message, Exception e) {
		out("e", tag, message);
	}

	public static void i(String tag, String message, Exception e) {
		out("i", tag, message);
	}

	private static void out(String level, String tag, String message) {
		System.out.println(String.format("(%s) %s : %s", level, tag, message));
	}

	public static String getTag(Class cla) {
		return cla.getSimpleName();
	}
}
