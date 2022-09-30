package ai.mypulse.hmacauth.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Utilities for query string parameter computations.
 */
public class QueryParamsUtils {
    /**
     * Converts a given query string into a map of key value pairs,
     * where the key represents the query field name and the value is a list of one or more query values of that field.
     *
     * @param query Query string input to be converted.
     * @return A map of key value pairs of the query parameters.
     */
    public static Map<String, List<String>> convertQueryStringToMap(String query) {
        if (StringUtils.isNullOrEmpty(query)) {
            return null;
        }

        final Map<String, List<String>> queryPairs = new LinkedHashMap<>();
        final String[] pairs = query.split("&");

        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ?
                    URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8) :
                    pair;
            if (!queryPairs.containsKey(key)) {
                queryPairs.put(key, new LinkedList<>());
            }
            addValueToQueryPair(queryPairs, pair, idx, key);
        }

        return queryPairs;
    }

    private static void addValueToQueryPair(Map<String, List<String>> query_pairs, String pair, int idx, String key) {
        String value = idx > 0 && pair.length() > idx + 1 ?
                URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8) :
                null;
        List<String> parsedValue = parseParameterValue(value);

        if (parsedValue.size() > 1) {
            parsedValue.forEach((val) ->
                    query_pairs.get(key).add(val.replaceAll("\"", "").trim()));
        } else {
            query_pairs.get(key).add(parsedValue.get(0));
        }
    }

    private static List<String> parseParameterValue(String value) {
        if (value != null) {
            String listValues = value.replaceAll("[\\[\\]]", "");

            return new ArrayList<>(Arrays.asList(listValues.split(",")));
        }

        return new ArrayList<>(List.of(""));
    }
}
