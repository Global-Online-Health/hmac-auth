package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;
import ai.mypulse.hmacauth.utils.QueryParamsUtils;
import ai.mypulse.hmacauth.utils.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;

import static ai.mypulse.hmacauth.utils.Constants.SEPARATOR;
import static ai.mypulse.hmacauth.utils.EncodingUtils.*;

/**
 * {@link CanonicalRequest} implementation that generates HMAC canonical requests
 * using a given HttpRequest.
 */
public class HmacCanonicalRequest implements CanonicalRequest {

    @Override
    public String hashCanonicalRequest(HttpRequest request) throws IOException {
        String canonicalRequest = createCanonicalRequest(request);
        return getContentStringHash(canonicalRequest);
    }

    private String getContentStringHash(String content) {
        return hex(hashString(content));
    }

    protected String createCanonicalRequest(HttpRequest request) throws IOException {
        final String path = HttpUtils.appendUri(request.getPath());

        // ensure that the inputStream is retrieved before getting the query parameters
        var inputStream = request.getBody();

        return request.getMethod() +
                SEPARATOR +
                getResourcePathToCanonical(path) +
                SEPARATOR +
                getQueryParametersToCanonical(request.getQueryString()) +
                SEPARATOR +
                getContentStreamHash(inputStream);
    }

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

    protected String getContentStreamHash(InputStream stream) throws IOException {
        return hex(hash(stream));
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
}
