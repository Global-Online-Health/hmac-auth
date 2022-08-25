package ai.mypulse.hmacauth.core;

import ai.mypulse.hmacauth.http.HttpMethod;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class AuthSignerTest {

    @Test
    public void createCanonicalRequestReturnsHostAndPath() throws URISyntaxException {
        var auth = new AuthSigner();
        var expectedResult = "GET\n/foo";
        var request = new AuthSignRequest();
        request.setEndpoint(new URI("https://example.ai"));
        request.setHttpMethod(HttpMethod.GET);
        request.setResourcePath("foo");

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
    public void CanonicalRequestPrependsSlashWhenPathWithoutSlash() {
        var auth = new AuthSigner();
        var expectedResult = "/foo";
        var requestPath = "foo";

        var result = auth.getResourcePathToCanonicalResource(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void CanonicalRequestLeavesUnchangedWhenPathWithSlash() {
        var auth = new AuthSigner();
        var expectedResult = "/foo";
        var requestPath = "/foo";

        var result = auth.getResourcePathToCanonicalResource(requestPath);

        assertEquals(expectedResult, result);
    }

    @Test
    public void CanonicalRequestLeavesUnchangedWhenPathWithTrailingSlash() {
        var auth = new AuthSigner();
        var expectedResult = "/foo/bar/";
        var requestPath = "/foo/bar/";

        var result = auth.getResourcePathToCanonicalResource(requestPath);

        assertEquals(expectedResult, result);
    }
}
