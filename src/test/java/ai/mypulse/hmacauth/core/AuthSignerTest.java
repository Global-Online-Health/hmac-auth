package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.http.HttpMethod;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AuthSignerTest {

    @Test
    public void createCanonicalRequestReturnsCanonicalRequest() throws URISyntaxException {
        var auth = new AuthSigner();
        var expectedResult = "GET\n/foo\n";
        var request = new AuthSignRequest();
        request.setEndpoint(new URI("https://example.ai"));
        request.setHttpMethod(HttpMethod.GET);
        request.setResourcePath("foo");

        var result = auth.createCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void createCanonicalRequestWithQueryParamsReturnsCanonicalRequest() throws URISyntaxException {
        var auth = new AuthSigner();
        var request = new AuthSignRequest();

        request.setEndpoint(new URI("https://example.ai"));
        request.setHttpMethod(HttpMethod.GET);
        request.setResourcePath("foo");
        var params = new HashMap<String, List<String>>();
        params.put("paramC", List.of("valueC"));
        params.put("paramB", List.of("valueB"));
        params.put("paramA", List.of("valueA"));
        request.setParameters(params);
        var expectedResult = "GET\n/foo\nparamA=valueA&paramB=valueB&paramC=valueC";

        var result = auth.createCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void createCanonicalRequestWithMultipleQueryParamsValuesReturnsCanonicalRequest() throws URISyntaxException {
        var auth = new AuthSigner();
        var request = new AuthSignRequest();

        request.setEndpoint(new URI("https://example.ai"));
        request.setHttpMethod(HttpMethod.GET);
        request.setResourcePath("foo");
        var params = new HashMap<String, List<String>>();
        params.put("paramB", List.of("valueB"));
        params.put("paramA", Arrays.asList("valueAB", "valueAA"));
        request.setParameters(params);
        var expectedResult = "GET\n/foo\nparamA=valueAA&paramA=valueAB&paramB=valueB";

        var result = auth.createCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalRequestPrependsSlashWhenNoPathGiven() {
        var auth = new AuthSigner();
        var expectedResult = "/";
        var requestPath = "";

        var result = auth.getResourcePathToCanonicalResource(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalRequestPrependsSlashWhenPathWithoutSlash() {
        var auth = new AuthSigner();
        var expectedResult = "/foo";
        var requestPath = "foo";

        var result = auth.getResourcePathToCanonicalResource(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalRequestLeavesUnchangedWhenPathWithSlash() {
        var auth = new AuthSigner();
        var expectedResult = "/foo";
        var requestPath = "/foo";

        var result = auth.getResourcePathToCanonicalResource(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalRequestLeavesUnchangedWhenPathWithTrailingSlash() {
        var auth = new AuthSigner();
        var expectedResult = "/foo/bar/";
        var requestPath = "/foo/bar/";

        var result = auth.getResourcePathToCanonicalResource(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenNoParametersDoesNothing() {
        var auth = new AuthSigner();
        var expectedResult = "";

        var result = auth.getQueryParametersToCanonicalQueryString(null);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenParametersReturnsCanonicalQueryString() {
        var auth = new AuthSigner();
        var expectedResult = "paramA=valueA";
        var params = new HashMap<String, List<String>>();
        params.put("paramA", List.of("valueA"));

        var result = auth.getQueryParametersToCanonicalQueryString(params);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenParametersUnorderedReturnsCanonicalQueryString() {
        var auth = new AuthSigner();
        var expectedResult = "param1=value1&paramA=valueA&paramB=valueB";
        var params = new HashMap<String, List<String>>();
        params.put("paramB", List.of("valueB"));
        params.put("paramA", List.of("valueA"));
        params.put("param1", List.of("value1"));

        var result = auth.getQueryParametersToCanonicalQueryString(params);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenParameterWithMultipleValuesReturnsCanonicalQueryString() {
        var auth = new AuthSigner();
        var expectedResult = "paramA=valueAA&paramA=valueAB";
        var params = new HashMap<String, List<String>>();
        params.put("paramA", Arrays.asList("valueAB", "valueAA"));

        var result = auth.getQueryParametersToCanonicalQueryString(params);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenParameterWithValueThatContainsReservedCharactersReturnsCanonicalQueryString() {
        var auth = new AuthSigner();
        var expectedResult = "paramA=value%24&paramA=value%40";
        var params = new HashMap<String, List<String>>();
        params.put("paramA", Arrays.asList("value$", "value@"));

        var result = auth.getQueryParametersToCanonicalQueryString(params);

        assertEquals(expectedResult, result);
    }
}
