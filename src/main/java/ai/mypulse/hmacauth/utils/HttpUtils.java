package ai.mypulse.hmacauth.utils;

import ai.mypulse.hmacauth.core.SignRequest;
import ai.mypulse.hmacauth.http.HttpMethod;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Regex which matches any of the sequences that we need to fix up after
     * URLEncoder.encode().
     */
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

    public static String appendUri(final String baseUri, String path) {
        String resultUri = baseUri;
        if (path != null && path.length() > 0) {
            if (path.startsWith("/")) {
                if (resultUri.endsWith("/")) {
                    resultUri = resultUri.substring(0, resultUri.length() - 1);
                }
            } else if (!resultUri.endsWith("/")) {
                resultUri += "/";
            }

            resultUri += path;
        } else if (!resultUri.endsWith("/")) {
            resultUri += "/";
        }

        return resultUri;
    }

    public static boolean usePayloadForQueryParameters(SignRequest request) {
        boolean requestIsPOST = HttpMethod.POST.equals(request.getHttpMethod());
        boolean requestHasNoPayload = (request.getContent() == null);

        return requestIsPOST && requestHasNoPayload;
    }
}