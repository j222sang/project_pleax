package www.dream.com.framework.util;

public class ToStringSuperHelp {
	public static String trimSuperString(String str) {
		return str.substring(str.indexOf('[') + 1 , str.lastIndexOf(']'));
	}
}
