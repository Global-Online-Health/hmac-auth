package ai.mypulse.hmacauth.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class HmacCanonicalRequestTest {
    private static final String hashFromNoContent = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    @Test
    public void createCanonicalRequestReturnsCanonicalRequest() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "GET\n/foo\n\n" + hashFromNoContent;
        HttpRequest request = HttpRequest.builder()
                .method("GET")
                .path("/foo")
                .build();

        var result = canonicalRequest.createCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void createCanonicalRequestWithQueryParamsReturnsCanonicalRequest() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        HttpRequest request = HttpRequest.builder()
                .method("GET")
                .path("/foo")
                .queryString("paramC=valueC&paramB=valueB&paramA=valueA")
                .build();
        var expectedResult = "GET\n/foo\nparamA=valueA&paramB=valueB&paramC=valueC\n" +
                hashFromNoContent;

        var result = canonicalRequest.createCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void createCanonicalRequestWithMultipleQueryParamsValuesReturnsCanonicalRequest() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        HttpRequest request = HttpRequest.builder()
                .method("GET")
                .path("/foo")
                .queryString("paramB=valueB&paramA=[\"valueAB\", \"valueAA\"]")
                .build();
        var expectedResult = "GET\n/foo\nparamA=valueAA&paramA=valueAB&paramB=valueB\n" +
                hashFromNoContent;

        var result = canonicalRequest.createCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void createCanonicalRequestWithRequestBodyReturnsCanonicalRequest() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        var values = new HashMap<String, String>() {{
            put("fieldA", "valueA");
            put("fieldB", "valueB");
        }};
        byte[] requestBody = new ObjectMapper().writeValueAsBytes(values);
        HttpRequest request = HttpRequest.builder()
                .method("POST")
                .path("/foo")
                .body(requestBody)
                .build();
        var expectedResult = "POST\n/foo\n\nf4bdef762a687446d6e44db2c986ce8ab52ee26eafcd86ea70035754b9b60d19";

        var result = canonicalRequest.createCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void hashCanonicalRequestReturnsHashedContent() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "e1688f15ba88ed1fdf3279a044ad4d99a301fd14257f0b4dd2b986de4f2edfc8";
        HttpRequest request = HttpRequest.builder()
                .method("GET")
                .path("/foo")
                .build();

        var result = canonicalRequest.hashCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void hashCanonicalRequestWithQueryParametersReturnsHashedContent() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "dae7e91d46b1a622ae941b98b736f8a312c03099ec39a5f59e53d55f1f302194";
        HttpRequest request = HttpRequest.builder()
                .method("GET")
                .path("/foo")
                .queryString("paramC=valueC&paramB=valueB&paramA=valueA")
                .build();

        var result = canonicalRequest.hashCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void hashCanonicalRequestWithPayloadReturnsHashedContent() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "31867acd1a6abe32891473dcc0069c92da04d7af4db8479d9426cd52629dfa0a";
        var values = new HashMap<String, String>() {{
            put("fieldA", "valueA");
            put("fieldB", "valueB");
        }};
        byte[] requestBody = new ObjectMapper().writeValueAsBytes(values);
        HttpRequest request = HttpRequest.builder()
                .method("POST")
                .path("/foo")
                .body(requestBody)
                .build();

        var result = canonicalRequest.hashCanonicalRequest(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalRequestPrependsSlashWhenNoPathGiven() {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "/";
        var requestPath = "";

        var result = canonicalRequest.getResourcePathToCanonical(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalRequestPrependsSlashWhenPathWithoutSlash() {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "/foo";
        var requestPath = "foo";

        var result = canonicalRequest.getResourcePathToCanonical(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalRequestLeavesUnchangedWhenPathWithSlash() {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "/foo";
        var requestPath = "/foo";

        var result = canonicalRequest.getResourcePathToCanonical(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalRequestLeavesUnchangedWhenPathWithTrailingSlash() {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "/foo/bar/";
        var requestPath = "/foo/bar/";

        var result = canonicalRequest.getResourcePathToCanonical(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenNoParametersDoesNothing() {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "";

        var result = canonicalRequest.getQueryParametersToCanonical(null);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenUnorderedSortsSuccessfully() {
        var canonicalRequest = new HmacCanonicalRequest();
        var queryString = "paramB=valueB&param1=value1&paramA=valueA";
        var expectedResult = "param1=value1&paramA=valueA&paramB=valueB";


        var result = canonicalRequest.getQueryParametersToCanonical(queryString);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenMultipleValuesAddsThemToParameter() {
        var canonicalRequest = new HmacCanonicalRequest();
        var queryString = "paramA=[\"valueAA\", \"valueAB\"]";
        var expectedResult = "paramA=valueAA&paramA=valueAB";

        var result = canonicalRequest.getQueryParametersToCanonical(queryString);

        assertEquals(expectedResult, result);
    }

    @Test
    public void canonicalQueryStringWhenReservedCharactersEncodesSuccessfully() {
        var canonicalRequest = new HmacCanonicalRequest();
        var expectedResult = "paramA=value%24&paramA=value%40";
        var queryString = "paramA=[\"value$\", \"value@\"]";

        var result = canonicalRequest.getQueryParametersToCanonical(queryString);

        assertEquals(expectedResult, result);
    }

    @Test
    public void getContentHashWhenEmptyStreamReturnsDefault() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        var inputStream = new ByteArrayInputStream("".getBytes());

        var result = canonicalRequest.getContentStreamHash(inputStream);

        assertEquals(hashFromNoContent, result);
    }

    @Test
    public void getContentHashWhenStreamGivenReturnsEncodedStream() throws IOException {
        var canonicalRequest = new HmacCanonicalRequest();
        var inputStream = new ByteArrayInputStream("{\"fieldA\": \"valueA\", \"fieldB\": \"valueB\"}".getBytes());
        var expected = "bdc504f94212e01e375ee51333dfe51ac43a536c8c2f966b9eff0e34474f784a";

        var result = canonicalRequest.getContentStreamHash(inputStream);

        assertEquals(expected, result);
    }
}
