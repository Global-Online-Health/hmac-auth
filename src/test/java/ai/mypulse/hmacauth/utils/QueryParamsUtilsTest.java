package ai.mypulse.hmacauth.utils;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class QueryParamsUtilsTest {
    @Test
    public void convertQueryStringToMapWhenNoQueryDoesNothing() {
        var result = QueryParamsUtils.convertQueryStringToMap(null);

        assertNull(result);
    }

    @Test
    public void convertQueryStringToMapWhenMissingParameterValueSetsEmpty() {
        var queryString = "paramA=";
        Map<String, List<String>> expectedResult = new HashMap<>();
        expectedResult.put("paramA", List.of(""));

        var result = QueryParamsUtils.convertQueryStringToMap(queryString);

        expectedResult.keySet().forEach((key) ->
                assertEquals(result.get(key), expectedResult.get(key)));
    }

    @Test
    public void convertQueryStringToMapWhenMultipleParameterValuesIsSuccessful() {
        var queryString = "paramA=[\"valueAA\", \"valueAB\"]";
        Map<String, List<String>> expectedResult = new HashMap<>();
        expectedResult.put("paramA", List.of("valueAA", "valueAB"));

        var result = QueryParamsUtils.convertQueryStringToMap(queryString);

        expectedResult.keySet().forEach((key) ->
                assertEquals(result.get(key), expectedResult.get(key)));
    }

    @Test
    public void convertQueryStringToMapWhenEncodedParameterValuesIsSuccessful() {
        var queryString = "paramA=%5B%22valueAA%22%2C%20%22valueAB%22%5D";
        Map<String, List<String>> expectedResult = new HashMap<>();
        expectedResult.put("paramA", List.of("valueAA", "valueAB"));

        var result = QueryParamsUtils.convertQueryStringToMap(queryString);

        expectedResult.keySet().forEach((key) ->
                assertEquals(result.get(key), expectedResult.get(key)));
    }

    @Test
    public void convertQueryStringToMapWhenDifferentParametersIsSuccessful() {
        var queryString = "paramB=valueB&paramA=valueA";
        Map<String, List<String>> expectedResult = new HashMap<>();
        expectedResult.put("paramA", List.of("valueA"));
        expectedResult.put("paramB", List.of("valueB"));

        var result = QueryParamsUtils.convertQueryStringToMap(queryString);

        expectedResult.keySet().forEach((key) ->
                assertEquals(result.get(key), expectedResult.get(key)));
    }
}

