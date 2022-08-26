package ai.mypulse.hmacauth.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ai.mypulse.hmacauth.utils.Constants.DEFAULT_ENCODING;

public class HttpUtils {

    private static final Pattern ENCODED_CHARS_PATTERN;

    static {
        String pattern = Pattern.quote("+") +
                "|" +
                Pattern.quote("*") +
                "|" +
                Pattern.quote("%7E") +
                "|" +
                Pattern.quote("%2F");

        ENCODED_CHARS_PATTERN = Pattern.compile(pattern);
    }

    public static String urlEncode(final String value, final boolean path) {
        if (value == null) {
            return "";
        }

        try {
            String encoded = URLEncoder.encode(value, DEFAULT_ENCODING);

            Matcher matcher = ENCODED_CHARS_PATTERN.matcher(encoded);
            StringBuilder buffer = new StringBuilder(encoded.length());

            while (matcher.find()) {
                String replacement = matcher.group(0);

                if ("+".equals(replacement)) {
                    replacement = "%20";
                } else if ("*".equals(replacement)) {
                    replacement = "%2A";
                } else if ("%7E".equals(replacement)) {
                    replacement = "~";
                } else if (path && "%2F".equals(replacement)) {
                    replacement = "/";
                }

                matcher.appendReplacement(buffer, replacement);
            }

            matcher.appendTail(buffer);
            return buffer.toString();

        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String appendUri(String path) {
        String resultUri = "";
        if (path != null && path.length() > 0) {
            if (!path.startsWith("/")) {
                resultUri += "/";
            }
            resultUri += path;
        } else {
            resultUri += "/";
        }

        return resultUri;
    }
}
