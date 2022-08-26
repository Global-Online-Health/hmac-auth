package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;
import ai.mypulse.hmacauth.utils.QueryParamsUtils;
import ai.mypulse.hmacauth.utils.StringUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public abstract class AbstractAuthSigner {
    private static final int BUFFER_SIZE = 1024 * 1024;
    protected String getResourcePathToCanonical(String resourcePath) {
        String value;

        value = HttpUtils.urlEncode(resourcePath, true);

        URI normalize = URI.create(value).normalize();
        value = normalize.getRawPath();

        if (!resourcePath.endsWith("/") && value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        if (!value.startsWith("/")) {
            value = "/" + value;
        }
        return value;
    }

    protected String getQueryParametersToCanonical(String query) {
        if (StringUtils.isNullOrEmpty(query)) {
            return "";
        }
        var parameters = QueryParamsUtils.convertQueryStringToMap(query);
        final SortedMap<String, List<String>> sorted = getSortedParameters(parameters);

        final StringBuilder result = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : sorted.entrySet()) {
            for (String value : entry.getValue()) {
                if (result.length() > 0) {
                    result.append("&");
                }
                result.append(entry.getKey())
                        .append("=")
                        .append(value);
            }
        }

        return result.toString();
    }

    protected String calculateHmac(String secretAccessKey, String stringToSign) throws NoSuchAlgorithmException {
        final HmacUtils hmacHelper = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secretAccessKey);

        final byte[] raw = hmacHelper.hmac(stringToSign);
        return hex(raw);
    }

    protected String getContentStreamHash(InputStream stream) throws IOException {
        return hex(hash(stream));
    }

    protected String getContentStringHash(String content) throws IOException {
        return hex(hashString(content));
    }

    private SortedMap<String, List<String>> getSortedParameters(Map<String, List<String>> parameters) {
        final SortedMap<String, List<String>> sorted = new TreeMap<>();

        parameters.forEach((key, paramValues) -> {
            final String encodedParamName = HttpUtils.urlEncode(
                    key, false);
            final List<String> encodedValues = new ArrayList<>(
                    paramValues.size());
            for (String value : paramValues) {
                encodedValues.add(HttpUtils.urlEncode(value, false));
            }
            Collections.sort(encodedValues);
            sorted.put(encodedParamName, encodedValues);

        });
        return sorted;
    }

    private static byte[] hash(final InputStream inputStream) throws IOException, IllegalArgumentException {
        Objects.requireNonNull(inputStream);

        try {
            final byte[] buffer = new byte[BUFFER_SIZE];
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");

            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 hashing algorithm unknown.", e);
        }
    }

    public static byte[] hashString(String text) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(text.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 hashing algorithm unknown.", e);
        }
    }

    private static String hex(byte[] bytes) {
        char[] result = Hex.encodeHex(bytes);
        return new String(result);
    }
}
