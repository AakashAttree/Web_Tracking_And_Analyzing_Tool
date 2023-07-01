package web.tracking.core;


public class StringUtils {
	public static boolean isBlank(String value){
		if(value == null ||value.trim().length() <= 0){
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(String value){
		return !isBlank(value);
	}
}
