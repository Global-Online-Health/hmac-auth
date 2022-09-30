package ai.mypulse.hmacauth.utils;

import java.util.Locale;

/**
 * Utilities for string computations.
 */
public class StringUtils {
    private static final Locale LOCALE_ENGLISH = Locale.ENGLISH;

    /**
     * Asserts if a given string is null or empty.
     * @param value Input to be verified.
     * @return A boolean representing the assertion result.
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Transforms a given string characters to uppercase using the English locale,
     * or returns the initial value if given string is null or empty.
      * @param str Input to be transformed.
     * @return The input converted to uppercase.
     */
    public static String upperCase(String str) {
        if (isNullOrEmpty(str)) {
            return str;
        }
        return str.toUpperCase(LOCALE_ENGLISH);
    }
}
