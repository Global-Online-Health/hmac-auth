package ai.mypulse.hmacauth.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class HmacAuthenticationSignerTest {

    @Test
    public void calculateSignatureAsHexadecimalWhenGetRequestCreatesSignature() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var signatureRequest = createSignatureRequest("GET", null, null);
        var expectedResult = "c0e6d40fea26e46704865ef16a8d1400b0d888884c9702a89e2d81a008279968";

        var result = auth.calculateSignatureAsHexadecimal(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateSignatureAsHexadecimalWhenPostRequestCreatesSignature() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var values = new HashMap<String, String>() {{
            put("fieldA", "valueA");
            put("fieldB", "valueB");
        }};
        byte[] requestBody = new ObjectMapper().writeValueAsBytes(values);
        var signatureRequest = createSignatureRequest("POST", requestBody, null);

        var expectedResult = "2469d915454c0e0524b3360c0aaf32afb7d224d4171c01c505a7e55828bf5be6";
        var result = auth.calculateSignatureAsHexadecimal(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateSignatureAsHexadecimalWhenGetWithQueryStringRequestCreatesSignature() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var queryString = "paramC=valueC&paramB=valueB&paramA=valueA";
        var signatureRequest = createSignatureRequest("GET", null, queryString);

        var expectedResult = "dd6d442b47489554a6df3558789fb81e4dd99da2915b1e59f43cd813d06876d5";
        var result = auth.calculateSignatureAsHexadecimal(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateSignatureAsBase64WhenGetRequestCreatesSignature() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var signatureRequest = createSignatureRequest("GET", null, null);
        var expectedResult = "wObUD+om5GcEhl7xao0UALDYiIhMlwKoni2BoAgnmWg=";

        var result = auth.calculateSignatureAsBase64(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateSignatureAsBase64WhenPostRequestCreatesSignature() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var values = new HashMap<String, String>() {{
            put("fieldA", "valueA");
            put("fieldB", "valueB");
        }};
        byte[] requestBody = new ObjectMapper().writeValueAsBytes(values);
        var signatureRequest = createSignatureRequest("POST", requestBody, null);

        var expectedResult = "JGnZFUVMDgUkszYMCq8yr7fSJNQXHAHFBaflWCi/W+Y=";
        var result = auth.calculateSignatureAsBase64(signatureRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void calculateSignatureAsBase64WhenGetWithQueryStringRequestCreatesSignature() throws IOException {
        var auth = new HmacAuthenticationSigner();
        var queryString = "paramC=valueC&paramB=valueB&paramA=valueA";
        var signatureRequest = createSignatureRequest("GET", null, queryString);

        var expectedResult = "3W1EK0dIlVSm3zVYeJ+4Hk3ZnaKRWx5Z9DzYE9BodtU=";
        var result = auth.calculateSignatureAsBase64(signatureRequest);

        assertEquals(expectedResult, result);
    }

    private AuthSignerRequest createSignatureRequest(String method, byte[] requestBody, String queryString){
        var signatureTimestamp = Instant.now(Clock.fixed(Instant.parse("2022-01-01T14:00:00Z"),
                ZoneOffset.UTC)).getEpochSecond();
        var accessKey = "test_key";
        var signatureRequest = new AuthSignerRequest();
        HttpRequest request = HttpRequest.builder()
                .method(method)
                .path("/events")
                .timestamp(signatureTimestamp)
                .accessKeyId(accessKey)
                .queryString(queryString)
                .body(requestBody)
                .build();

        String secretAccessKey = Base64.getEncoder().encodeToString("test-secret-access-key".getBytes());
        signatureRequest.setHttpRequest(request);
        signatureRequest.setSecretAccessKey(secretAccessKey);

        return signatureRequest;
    }
}
