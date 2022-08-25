package ai.mypulse.hmacauth.utils;

import java.util.Locale;

public class StringUtils {
    private static final Locale LOCALE_ENGLISH = Locale.ENGLISH;

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static String upperCase(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.toUpperCase(LOCALE_ENGLISH);
    }
}
