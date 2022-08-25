package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.utils.HttpUtils;

import java.net.URI;
import java.util.*;

public abstract class AbstractAuthSigner {
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

    protected String getQueryParametersToCanonical(Map<String, List<String>> parameters){
        if (parameters == null){
            return "";
        }
        final SortedMap<String, List<String>> sorted = getSortedParameters(parameters);

        final StringBuilder result = new StringBuilder();
        for(Map.Entry<String, List<String>> entry : sorted.entrySet()) {
            for(String value : entry.getValue()) {
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
