package pp.game.utils;

public class StringUtils {
	private StringUtils() {
		
	}
	
	public static String appendSpaces(String src, int desiredLength) {
		StringBuilder builder = new StringBuilder(src);
		while (builder.length() != desiredLength) {
			builder.append(" ");
		}
		return builder.toString();
	}
}
