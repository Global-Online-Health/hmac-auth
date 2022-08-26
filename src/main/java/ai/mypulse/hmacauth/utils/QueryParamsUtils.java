package ai.mypulse.hmacauth.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QueryParamsUtils {
    public static Map<String, List<String>> convertQueryStringToMap(String query) {
        if (StringUtils.isNullOrEmpty(query)) {
            return null;
        }
        final Map<String, List<String>> query_pairs = new LinkedHashMap<>();
        final String[] pairs = query.split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ?
                    URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8) :
                    pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<>());
            }
            addValueToQueryPair(query_pairs, pair, idx, key);
        }

        return query_pairs;
    }

    private static void addValueToQueryPair(Map<String, List<String>> query_pairs, String pair, int idx, String key) {
        String value = idx > 0 && pair.length() > idx + 1 ?
                URLDecoder.decode(pair.substring(idx + 1), StandardCharsets.UTF_8) :
                null;

            List<String> parsedValue = parseParameterValue(value);
            if (parsedValue.size() > 1) {
                parsedValue.forEach((val) -> {
                    query_pairs.get(key).add(val.replaceAll("\"", "").trim());
                });
            } else {
                query_pairs.get(key).add(parsedValue.get(0));
            }
    }

    private static List<String> parseParameterValue(String value) {
        if (value != null){
            String listValues = value.replaceAll("[\\[\\]]", "");
            return new ArrayList<>(Arrays.asList(listValues.split(",")));
        }
        return new ArrayList<>(Arrays.asList(""));
    }
}
