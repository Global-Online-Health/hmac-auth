package ai.mypulse.hmacauth.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class HmacAuthenticationSignerTest {

    @Test
    public void calculateSignatureWhenGetRequestCreatesSignature() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var signatureRequest = createSignatureRequest("GET", null);
        var expectedResult = "947bd0fa6994f6ddf7ccbddeff6968a8f011187b88864f1f8ba4f3fb48a2d9f0";

        var result = auth.calculateSignature(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateSignatureWhenPostRequestCreatesSignature() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var values = new HashMap<String, String>() {{
            put("fieldA", "valueA");
            put("fieldB", "valueB");
        }};
        byte[] requestBody = new ObjectMapper().writeValueAsBytes(values);
        var signatureRequest = createSignatureRequest("POST", requestBody);

        var expectedResult = "d67b4f69c9373d224f9415dce5cdbeba484435b40b523d2b2d6f192b0bef333a";
        var result = auth.calculateSignature(signatureRequest);

        assertEquals(expectedResult, result);
    }

    private AuthSignerRequest createSignatureRequest(String method, byte[] requestBody){
        var signatureTimestamp = Instant.now(Clock.fixed(Instant.parse("2022-01-01T14:00:00Z"),
                ZoneOffset.UTC)).getEpochSecond();
        var accessKey = "test-access-key";
        var signatureRequest = new AuthSignerRequest();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("example");
        request.setRequestURI("example.ai");
        request.setMethod(method);
        request.setPathInfo("/foo");
        request.addHeader("X-MP-TIMESTAMP", signatureTimestamp);
        request.addHeader("X-MP-ACCESS-KEY", accessKey);

        if (requestBody != null){
            request.setContent(requestBody);
        }

        signatureRequest.setHttpRequest(request);
        signatureRequest.setSecretAccessKey("test-secret-access-key");

        return signatureRequest;
    }
}
